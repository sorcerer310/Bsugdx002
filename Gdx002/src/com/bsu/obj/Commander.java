package com.bsu.obj;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Role.Type;
import com.bsu.obj.skilltree.ContinuedSkillState;
import com.bsu.obj.skilltree.ContinuedSkillState.CSType;
import com.bsu.obj.skilltree.Skill;
import com.bsu.obj.skilltree.Skill.ObjectType;
import com.bsu.screen.GameScreen;
import com.bsu.tools.BsuEvent;
import com.bsu.tools.CommandQueue;
import com.bsu.tools.Configure;
import com.bsu.tools.U;
import com.bsu.tools.Configure.FACE;
import com.bsu.tools.FightRoleUI;
import com.bsu.tools.Configure.DIRECTION;
import com.bsu.tools.Configure.STATE;

/**
 * 指挥官对象，用来指挥stage上所有的角色
 * 
 * @author fengchong
 * 
 */
public class Commander {
	private static Commander instance = null;

	public static Commander getInstance(Stage s, GameScreen gs) {
		if (instance == null)
			instance = new Commander(s, gs);
		return instance;
	}

	public static Commander getInstance() {
		return instance;
	}

	private Stage stage;
	private GameScreen gamescreen = null;
	private Array<Actor> lactor = null;
	private Array<Role> heros = new Array<Role>();
	private Array<Role> npcs = new Array<Role>();
	private Array<Role> allRoles = new Array<Role>();

	private Commander(Stage s, GameScreen gs) {
		gamescreen = gs;
		stage = s;
		commanderStart();
	}

	private void resetRoles() {
		lactor = stage.getActors();
		heros.clear();
		npcs.clear();
		// 此处区分处英雄与敌人npc
		for (Actor act : lactor) {
			if (act instanceof Role) {
				if (((Role) act).getType() == Type.HERO) {
					heros.add((Role) act);
				} else if (((Role) act).getType() == Type.ENEMY)
					npcs.add((Role) act);
			}
		}
		allRoles.addAll(heros);
		allRoles.addAll(npcs);
	}
	/**
	 * commander对象开始线程，进行监视
	 */
	private void commanderStart() {
		resetRoles();
		/**
		 * 单独启动个线程，监视任务队列执行任务
		 */
		new Thread() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(100);
						CommandQueue.getInstance().runTask();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}.start();
	}

	/**
	 * 回合结束，命令所有的角色行动
	 */
	boolean waitNextCommand = true; // 等待下一命令标示，为true时，表示循环等待命令完成
	boolean roundEndCompleted = true; // 回合结束标示，为true时，表示当前回合结束

	public void roundEnd() {
		if (!roundEndCompleted)
			return;
		roundEndCompleted = false; // 回合操作开始设置完成标示为false
		resetRoles();
		resetHeroValue();
		
		//增加对站在特殊地图块上的英雄进行处理的任务
		CommandQueue.getInstance().put(new CommandTask() {
			@Override
			public void opTask(BsuEvent be) {
				System.out.println("map event optask");
				mapEvent(be);
			}
		});
		//检测Role是否有持续状态
		CommandQueue.getInstance().put(new CommandTask(){
			@Override
			public void opTask(BsuEvent be){
				roleContinuedSkillState(be);
			}
		});
		//增加命令英雄任务
		CommandQueue.getInstance().put(new CommandTask() {
			@Override
			public void opTask(BsuEvent be) {
				try {
					commandHeros(be);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		//检测Role是否有持续状态
		CommandQueue.getInstance().put(new CommandTask(){
			@Override
			public void opTask(BsuEvent be){
				roleContinuedSkillState(be);
			}
		});
		//增加命令敌人任务
		CommandQueue.getInstance().put(new CommandTask() {
			@Override
			public void opTask(BsuEvent be) {
				try {
					commandNpcs(be);
					roundEndCompleted = true;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 移动动作
	 * @param r	要移动的Role
	 * @param d	移动方向
	 * @param be	移动动作结束后从这里获得结束的消息
	 */
	public void directAction(final Role r, DIRECTION d, final BsuEvent be) {
		if (r.get_ani_from_state() != STATE.idle)
			return;
		r.set_ani_from_state(STATE.move);
		int x = 0, y = 0;
		if (d == DIRECTION.left)
			x = -Configure.map_box_value;
		else if (d == DIRECTION.right)
			x = Configure.map_box_value;
		else if (d == DIRECTION.up)
			y = Configure.map_box_value;
		else if (d == DIRECTION.down)
			y = -Configure.map_box_value;
		r.addAction(parallel(
				sequence(rotateBy(30, 0.1f), rotateBy(-30, 0.1f),
						rotateBy(-10, 0.1f), rotateBy(10, 0.1f)),
				moveBy(x, y, Configure.duration), run(new Runnable() {
					@Override
					public void run() {
						r.set_ani_from_state(STATE.idle);
						if (be != null)
							be.notify(r, r.name);
					}
				})));
	}
	/**
	 * 击退移动
	 * @param r	被击退的Role
	 */
	public void heatAction(Role r){
		if(r.face==FACE.left && !U.hasRoleInPos(allRoles, new Vector2(r.getBoxX()+1,r.getBoxY())))
			r.addAction(moveBy(r.getX()+Configure.map_box_value,r.getY(),0.01f));
		else if(r.face == FACE.right && !U.hasRoleInPos(allRoles, new Vector2(r.getBoxX()-1,r.getBoxY())))
			r.addAction(moveBy(r.getX()-Configure.map_box_value,r.getY(),0.01f));
	}
	/**
	 * 对所有的Role进行持续技能状态处理
	 * @param be	持续技能操作完成事件
	 */
	private void roleContinuedSkillState(BsuEvent be){
		for(Role r:allRoles){
			if(r.csstate.size==0)
				continue;
			r.clearExtValue();
			for(ContinuedSkillState css:r.csstate){
				r.ani_role_isAttacked(css.ani);			//播放持续技能效果动画
				if(css.cstype==CSType.buff_atk){
					r.extAttack += (int) css.val; 
				}else if(css.cstype==CSType.buff_def){
					r.extDefend += (int) css.val;
				}else if(css.cstype==CSType.buff_hp){
					r.extMaxHp += (int)css.val;
					r.currentHp += (int)css.val;
				}else if(css.cstype==CSType.debuff_atk){
					r.extAttack += (int) -css.val;
				}else if(css.cstype==CSType.debuff_def){
					r.extDefend += (int) -css.val;
				}else if(css.cstype==CSType.debuff_hp){
					r.extMaxHp += (int)-css.val;
					r.currentHp += (int)-css.val;
				}else if(css.cstype==CSType.dot){
					r.currentHp = (int) (r.currentHp-css.val >0 ?r.currentHp-css.val:0);
					if(r.currentHp==0)
						commandRoleDead(r);
				}else if(css.cstype==CSType.hot){
					r.currentHp = (int)(r.currentHp + css.val<(r.getMaxHp())?r.currentHp+css.val:r.getMaxHp());
				//致盲
				}else if(css.cstype==CSType.blind){
					r.isRoundMove = false;
				//眩晕
				}else if(css.cstype==CSType.dizzy){
					r.isRoundMove = false;
				}
				//如果持续效果动画不为空，播放动画
				if(css.ani!=null)
					r.ani_role_isAttacked(css.ani);
				//持续回合数减1
				css.remainRound -=1;
				//如果持续效果剩余回合数为0，从人物的持续效果队列中移除该持续效果
				if(css.remainRound<=0)
					r.csstate.removeValue(css, true);
			}
		}
		be.notify(this, "continue_state_completed");
	}
	
	/**
	 * 处理地图块事件，检查地图上特殊属性的块是否有Role在 有则对Role对象进行处理
	 * 
	 * @param be
	 *            地图操作完成事件
	 */
	private void mapEvent(BsuEvent be) {
		// 从stage中获得mb
		Array<Actor> acts = lactor;
		MapBox mb = null;
		for (Actor act : acts) {
			if (act instanceof MapBox) {
				mb = (MapBox) act;
				break;
			}
		}
		if (mb == null)
			return;

		// 处理一些地图块事件
		be.notify(this, "map_event_completed");
	}

	/**
	 * 指挥英雄们进行行动
	 */
	boolean waitRoleFlag = false; // 等待标示，用来标记是否继续循环对下一英雄进行操作
	boolean currTaskComFlag = false;	//当前任务是否完成标示
//	boolean moveAfterSkillFlag = false;	//指示释放技能后人物是否继续移动
	private void commandHeros(BsuEvent be) throws InterruptedException {
		for (int i = 0; i < heros.size; i++) {
			final Role h = heros.get(i);
			if(!h.isRoundMove) {h.isRoundMove=true;continue;}	//如果本回合该英雄不行动则跳过该英雄
			waitRoleFlag = true; 			// 初始化等待循环flag为true
			currTaskComFlag = false;		//只是当前处理技能任务是否完成
			final Array<Role> atkrs = getRolesInSkillRange(h);
			//1:执行技能命令
			if(atkrs.size!=0){
				// 命令当前英雄进攻所有范围内的敌人
				for (final Role e : atkrs) {
					h.ani_role_attack(e, h.getCskill(), new BsuEvent() {
						@Override
						public void notify(Object obj, String msg) {
							//如果技能逻辑函数返回true,清空技能目标队列继续移动该单位
							if(h.cskill.skillLogic(h, e))
								atkrs.clear();
							currTaskComFlag = true;
						}
					});
				}
			}else{currTaskComFlag = true;}
			
			while (!currTaskComFlag) {
				System.out.println(currTaskComFlag);
				Thread.sleep(500);
			}
			
			//2:执行移动命令
			if(atkrs.size == 0){
				if(!h.hasAnatherRole(heros)){
					Commander.this.directAction(h, DIRECTION.right, new BsuEvent(){
						@Override
						public void notify(Object obj, String msg) {
							waitRoleFlag = false;
						}
					});
				}else{waitRoleFlag = false;}
			}else{waitRoleFlag = false;}

			// 等待动作完成
			while (waitRoleFlag) {
				Thread.sleep(200);
			}
		}
		be.notify(this, "command_heros_completed");
	}

	/**
	 * 指挥敌人们进行行动
	 * 
	 * @throws InterruptedException
	 */
	private void commandNpcs(BsuEvent be) throws InterruptedException {
		for (int i = 0; i < npcs.size; i++) {
			final Role n = npcs.get(i);
			if(!n.isRoundMove) {n.isRoundMove=true;continue;}	//如果本回合该npc不行动则跳过该npc
			waitRoleFlag = true; 			// 初始化等待循环flag为true
			currTaskComFlag = false;		//只是当前处理技能任务是否完成
			final Array<Role> atkrs = getRolesInSkillRange(n);
			//1:执行技能命令
			if(atkrs.size!=0){
				// 命令当前npc进攻所有范围内的英雄
				for (final Role h : atkrs) {
					n.ani_role_attack(h, n.getCskill(), new BsuEvent() {
						@Override
						public void notify(Object obj, String msg) {
							//如果技能逻辑函数返回true,清空技能目标队列继续移动该单位
							if(n.cskill.skillLogic(n, h))
								atkrs.clear();
							currTaskComFlag = true;
						}
					});
				}
			}else{currTaskComFlag = true;}
			
			while (!currTaskComFlag) {
				System.out.println(currTaskComFlag);
				Thread.sleep(500);
			}
			
			//2:执行移动命令
			if(atkrs.size == 0){
				if(!n.hasAnatherRole(npcs)){
					Commander.this.directAction(n, DIRECTION.left, new BsuEvent(){
						@Override
						public void notify(Object obj, String msg) {
							waitRoleFlag = false;
						}
					});
				}else{waitRoleFlag = false;}
			}else{waitRoleFlag = false;}

			// 等待动作完成
			while (waitRoleFlag) {
				Thread.sleep(200);
			}
		}
		be.notify(this, "command_npcs_completed");
	}

	/**
	 * 获得技能范围内是否有Role存在
	 * 
	 * @param v
	 *            技能范围
	 * @return 返回符合类型的所有Role
	 */
	private Array<Role> getRolesInSkillRange(Role h) {
		Skill s = h.cskill;
		Array<Vector2> vs = s.getRange();					//技能作用范围
		//如果技能为f_box类型，重新计算技能攻击范围
		if(s.type==Skill.Type.f_box){
			vs.clear();
			for(int i=0;i<s.val;i++)
				vs.add(new Vector2(i+1,0));
		}
		
		Array<Role> retrs = new Array<Role>();				//返回符合类型的英雄
		Array<Role> checkrs = null;
		if(s.type==Skill.Type.f_damage ||s.type==Skill.Type.f_shifhp ||s.type==Skill.Type.f_box ||s.type==Skill.Type.p_atkbeat ||
				s.type==Skill.Type.p_damage ||s.type==Skill.Type.pdot_damage ||s.type==Skill.Type.prob_blind ||
				s.type==Skill.Type.prob_dizzy ||s.type==Skill.Type.prob_nude )
			checkrs = npcs;
		else if(s.type==Skill.Type.f_healing ||s.type==Skill.Type.p_healing  ||s.type==Skill.Type.pbuff_atk ||
				s.type==Skill.Type.pbuff_def ||s.type==Skill.Type.pbuff_healing ||s.type==Skill.Type.pbuff_hp )
			checkrs = heros;
			
		//如果没有指定检查Role直接返回retrs
		if(checkrs == null)
			return retrs;
		
		for (Vector2 v : vs) {
			for (Role r : checkrs) {
				if ((v.x+h.getBoxX()) == r.getBoxX() && (v.y+h.getBoxY()) == r.getBoxY()){
					retrs.add(r);
					//如果技能为单体技能，直接返回结果数组
					if(s.otype==ObjectType.single)
						return retrs;
				}
			}
		}
		return retrs;
	}

	/**
	 * 向role下命令，命令其如何移动,只针对hero方
	 * @param mx
	 * @param my
	 */
	public void moveAction(final Role r, final int mx, final int my) {
		// GameScreen.setControlled(false);
		gamescreen.setControlled(false);
		waitRoleFlag = true;
		boolean disapperFlag = true;// 默认闪现出现到指定位置
		DIRECTION d = null;
		if (mx == r.getBoxX()) {
			if (my - r.getBoxY() == 1) {
				d = DIRECTION.up;
				disapperFlag = false;
			}
			if (my - r.getBoxY() == -1) {
				d = DIRECTION.down;
				disapperFlag = false;
			}
		}
		if (my == r.getBoxY()) {
			if (mx - r.getBoxX() == 1) {
				d = DIRECTION.right;
				disapperFlag = false;
			}
			if (mx - r.getBoxX() == -1) {
				d = DIRECTION.left;
				disapperFlag = false;
			}
		}
		if (!disapperFlag) {
			this.directAction(r, d, new BsuEvent() {
				@Override
				public void notify(Object obj, String msg) {
					// 收到消息设置等待标示为false
					r.set_ani_from_state(STATE.idle);
					gamescreen.setControlled(true);
				}
			});
		} else {
			r.hero_disapper(new BsuEvent() {
				@Override
				public void notify(Object obj, String msg) {
					if (((Role) obj).name.equals(msg)) {
						r.hero_apper(mx, my, new BsuEvent() {
							@Override
							public void notify(Object obj, String msg) {
								if (((Role) obj).name.equals(msg)) {
									gamescreen.setControlled(true);
								}
							}
						});
					}
				}
			});
		}
	}

	/**
	 * 用来检查角色是否被本轮选择，若被选择，则其他不被选择，
	 * 
	 * @author 张永臣
	 */
	public void heroSelected(Role hero) {
		for (Actor act : lactor) {
			if (act instanceof Role) {
				final Role r = (Role) act;
				if (r.getType() == Type.HERO) {
					r.setSelected(false);
					if (hero == r) {
						hero.setSelected(true);
					}
				}
			}
		}
	}

	/**
	 * 判断此可移动方块是否有人存在
	 * 
	 * @param r
	 *            被选要移动的人
	 * @return
	 */
	public boolean isOtherHero(int inputX, int inputY) {

		for (Role hr : heros) {
			Vector2 hv = new Vector2(hr.getBoxX(), hr.getBoxY());
			if ((inputX == hv.x) && (inputY == hv.y)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 用来检查角色是否被本轮选择操作，如果第一次选择，则计算可移动范围
	 * 
	 * @author 张永臣
	 */
	public void heroControllor(Role r) {
		r.setControlled(true);
		if (r.getType() == Type.HERO) {
			r.setPass_array(MapBox.set_hero_pass_box(r, npcs));
		}
	}

	/**
	 * 每轮操作结束后，清空角色方可移动数组
	 */
	private void resetHeroValue() {
		for (Role r : heros) {
			if (r.getType() == Type.HERO) {
				r.setSelected(false);
				r.setControlled(false);
				r.getPass_array().clear();
				r.getAttack_array().clear();
				MapBox.pass_array.clear();
				MapBox.attack_array.clear();
			}
		}
	}
	/**
	 * 命令一个Role死亡
	 * @param r	要致死的Role
	 */
	public void commandRoleDead(Role r){
		//后续处理
		System.out.println("啊啊啊啊啊，我死了！！！");
	}
	
	public Array<Role> getHeros() {
		return heros;
	}
}
