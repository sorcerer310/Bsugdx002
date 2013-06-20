package com.bsu.obj;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.utils.Array;
import com.bsu.make.ItemFactory;
import com.bsu.obj.Role.Type;
import com.bsu.obj.skilltree.ContinuedSkillState;
import com.bsu.obj.skilltree.ContinuedSkillState.CSType;
import com.bsu.obj.skilltree.Skill;
import com.bsu.obj.skilltree.Skill.ObjectType;
import com.bsu.screen.GameScreen;
import com.bsu.tools.BsuEvent;
import com.bsu.tools.CommandQueue;
import com.bsu.tools.GC;
import com.bsu.tools.GTC;
import com.bsu.tools.GameMap;
import com.bsu.tools.U;
import com.bsu.tools.GC.FACE;
import com.bsu.tools.GC.DIRECTION;
import com.bsu.tools.GC.STATE;

/**
 * 指挥官对象，用来指挥stage上所有的角色
 * 
 * @author fengchong
 * 
 */
public class Commander {
	private static Commander instance = null;
	//每次调用这这个函数时，重新初始化instance
	public static Commander initInstance(Stage s, GameScreen gs) {
//		if (instance == null)
		instance = new Commander(s, gs);
		return instance;
	}

	public static Commander getInstance() {
		return instance;
	}

	private Stage stage;
	private GameScreen gamescreen = null;
	private MapBox mb = null;
	private Array<Actor> lactor = null;
	public Array<Role> heros = new Array<Role>();
	public Array<Role> npcs = new Array<Role>();
	public Array<Role> allRoles = new Array<Role>();

	private Commander(Stage s, GameScreen gs) {
		gamescreen = gs;
		stage = s;
		// 从stage中获得mb
		Array<Actor> acts = stage.getActors();
		for (Actor act : acts) {
			if (act instanceof MapBox) {
				mb = (MapBox) act;
				break;
			}
		}
		
		commanderStart();
	}

	public void resetRoles() {
		lactor = stage.getActors();
		heros.clear();
		npcs.clear();
		allRoles.clear();
		// 此处区分处英雄与敌人npc
		for (Actor act : lactor) {
			if (act instanceof Role) {
//				((Role)act).isDead = false;
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
						Thread.sleep(200);
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
		if (!roundEndCompleted||gamescreen.isAction_start())
			return;
		roundEndCompleted = false; // 回合操作开始设置完成标示为false
		gamescreen.setAction_start(true);
		resetRoles();
		resetHeroValue();

		// 增加对站在特殊地图块上的英雄进行处理的任务
		CommandQueue.getInstance().put(new CommandTask() {
			@Override
			public void opTask(BsuEvent be) {
				System.out.println("map event optask");
				mapEvent(be);
			}
		});
		// 检测Role是否有持续状态
		CommandQueue.getInstance().put(new CommandTask() {
			@Override
			public void opTask(BsuEvent be) {
				roleContinuedSkillState(be);
			}
		});
		// 增加命令英雄任务
		CommandQueue.getInstance().put(new CommandTask() {
			@Override
			public void opTask(BsuEvent be) {
				try {
					// commandHeros(be);
					commandRoles(be, Role.Type.HERO);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// 增加命令敌人任务
		CommandQueue.getInstance().put(new CommandTask() {
			@Override
			public void opTask(BsuEvent be) {
				try {
					commandRoles(be, Role.Type.ENEMY);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		//回合结束，为新一回合准备，
		CommandQueue.getInstance().put(new CommandTask(){
			@Override
			public void opTask(BsuEvent be){
				be.notify(this, "roundEndCompleted");
				roundEndCompleted = true;
				gamescreen.newRound();
				decideGameEnd();							//判断游戏是否结束

			}
		});
	}

	/**
	 * 移动动作
	 * 
	 * @param r
	 *            要移动的Role
	 * @param d
	 *            移动方向
	 * @param be
	 *            移动动作结束后从这里获得结束的消息
	 */
	public void moveDirectCommand(Role r, DIRECTION d, BsuEvent be) {
		int x = 0, y = 0;
		if (d == DIRECTION.left)
			x = -GC.map_box_value;
		else if (d == DIRECTION.right)
			x = GC.map_box_value;
		else if (d == DIRECTION.up)
			y = GC.map_box_value;
		else if (d == DIRECTION.down)
			y = -GC.map_box_value;
		r.moveAction(x, y, be);
	}

	/**
	 * 击退移动
	 * 
	 * @param r
	 *            被击退的Role
	 */
	public void heatCommand(Role r) {
		if (r.face == FACE.left
				&& !U.hasRoleInPos(allRoles,
						new Vector2(r.getBoxX() + 1, r.getBoxY())))
			r.heatAction(GC.map_box_value, 0);
		else if (r.face == FACE.right
				&& !U.hasRoleInPos(allRoles,
						new Vector2(r.getBoxX() - 1, r.getBoxY())))
			r.heatAction(-GC.map_box_value, 0);
	}

	/**
	 * 冲锋动作
	 * @param r		要冲锋的Role
	 */
	public void assaultCommand(Role r,BsuEvent be){
			Array<Vector2> path = r.cskill.getRange();
			Array<Role> obj = getRolesInSkillRange(r);
			int x = ((int) (r.type==Type.HERO ? (
					(int)(r.getBoxX()+path.get(path.size-1).x)>15?15:(r.getBoxX()+path.get(path.size-1).x)
					):(int)(r.getBoxX()-path.get(path.size-1).x)<0?0:(r.getBoxX()-path.get(path.size-1).x)
							))*GC.map_box_value; 
			if(obj.size>0){
				x = (int) (r.type==Type.HERO ?obj.get(0).getX()-GC.map_box_value:obj.get(0).getX()+GC.map_box_value);
				r.assaultAction((float)x,r.getY(),obj.get(0),be);
			}
			
	}
	
	/**
	 * 被攻击命令，命令一个单位被攻击
	 * 
	 * @param r
	 */
	public void hitedCommand(Role r) {
		r.hitedAction();
	}

	/**
	 * 被阻挡命令
	 * 
	 * @param r
	 *            受到阻挡的人
	 */
	public void stopedCommand(Role r) {
		r.stopedAction();
	}
	/**
	 * 对所有的Role进行持续技能状态处理
	 * 
	 * @param be
	 *            持续技能操作完成事件
	 */
	private void roleContinuedSkillState(BsuEvent be) {
		for (Role r : allRoles) {
			// for(int i=0;i<allRoles.size)
			if (r.csstate.size == 0)
				continue;
			r.clearExtValue();
			for (ContinuedSkillState css : r.csstate) {
				if (css.ani != null)
					r.ani_role_continue(css); // 播放持续技能效果动画
				if (css.cstype == CSType.buff_atk) {
					r.extAttack += (int) css.val;
				} else if (css.cstype == CSType.buff_def) {
					r.extDefend += (int) css.val;
				} else if (css.cstype == CSType.buff_hp) {
					r.extMaxHp += (int) css.val;
					r.setCurrentHp(r.getCurrentHp()+(int) css.val); 
				} else if (css.cstype == CSType.debuff_atk) {
					r.extAttack += (int) -css.val;
				} else if (css.cstype == CSType.debuff_def) {
					r.extDefend += (int) -css.val;
				} else if (css.cstype == CSType.debuff_hp) {
					r.extMaxHp += (int) -css.val;
					r.setCurrentHp(r.getCurrentHp()-(int)css.val);
				} else if (css.cstype == CSType.dot) {
					r.setCurrentHp((int) (r.getCurrentHp() - css.val > 0 ? r.getCurrentHp()- css.val: 0));
					if (r.getCurrentHp() == 0)
						commandRoleDead(r);
				} else if (css.cstype == CSType.hot) {
					r.setCurrentHp((int) (r.getCurrentHp() + css.val < (r.getMaxHp()) ? r.getCurrentHp()+ css.val: r.getMaxHp()));
					// 致盲
				} else if (css.cstype == CSType.blind) {
					r.isRoundMove = false;
					// 眩晕
				} else if (css.cstype == CSType.dizzy) {
					r.isRoundMove = false;
				}
				// 如果持续效果动画不为空，播放动画
				// if(css.ani!=null)
				// r.ani_role_continue(css);
				// r.ani_role_isAttacked(css.ani);
				// 持续回合数减1
				css.remainRound -= 1;
				// 如果持续效果剩余回合数为0，从人物的持续效果队列中移除该持续效果
				if (css.remainRound <= 0)
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
		for(TiledObject obj:mb.box_array){
			for(Role r :heros){
//				int ox = (obj.x) / GC.map_box_value;
//				int oy = (GameMap.map_render.getMapHeightUnits()
//						- GC.map_box_value - obj.y)
//						/ GC.map_box_value;
				Vector2 v = U.TiledPos2GdxBoxPos(obj.x,obj.y);
				if(v.x==r.getBoxX() && v.y==r.getBoxY()){
					String itemid = obj.properties.get("itemid");					//获得该位置的物品id
					Player.getInstance().giveItem(Integer.parseInt(itemid));		//为宝箱位置的英雄增加对应物品
					System.out.println("add a item:"+itemid);
					//如果宝箱类型为显式类型
					if(obj.type.equals("show")){
						
					//如果宝箱类型为隐式类型
					}else if(obj.type.equals("hide")){
						
					}
					mb.box_array.removeValue(obj, true);							//将该宝箱从地图上移除
				}
			}
		}
		
		// 处理一些地图块事件
//		mb.box_array
//		
		be.notify(this, "map_event_completed");
	}

	/**
	 * 指挥英雄们进行行动
	 */
	boolean waitRoleFlag = false; // 等待标示，用来标记是否继续循环对下一英雄进行操作
	boolean currTaskComFlag = false; // 当前任务是否完成标示
	boolean loopCompleted = false;
	int skillAniCompletedCount = 0; // 技能动画完成数量
	// boolean moveAfterSkillFlag = false; //指示释放技能后人物是否继续移动

	private void commandRoles(BsuEvent be, Role.Type rt)
			throws InterruptedException {
		Array<Role> attackRoles = null;
		// Array<Role> beAttackedRoles = null;
		DIRECTION direct = DIRECTION.right;
		// 判断进攻方为英雄还是npc
		if (rt == Type.HERO) {
			attackRoles = heros;
			direct = DIRECTION.right;
		} else if (rt == Type.ENEMY) {
			attackRoles = npcs;
			direct = DIRECTION.left;
		}

		for (int i = 0; i < attackRoles.size; i++) {
			final Role r = attackRoles.get(i);
			if(r.isDead || !r.isVisible()) continue;			//如果当前role死亡，不进行操作
			if(!r.isRoundMove) {r.isRoundMove=true;continue;}	//如果本回合该英雄不行动则跳过该英雄
			waitRoleFlag = true; 			// 初始化等待循环flag为true
			currTaskComFlag = false;		//只是当前处理技能任务是否完成
			gamescreen.getFightUI().actingRole(r);//ui显示当前执行动作人物。。
			final Array<Role> atkrs = getRolesInSkillRange(r);
			
			//处理技能逻辑，处理技能逻辑时会对攻击的对象进行数值运算，处理结束后返回boolean值，指示是否继续移动			
			boolean isMove = r.cskill.skillLogic(r, atkrs);
			//1:执行技能命令
//			if(atkrs.size!=0){
			if(!isMove){

				r.ani_role_attack(atkrs,r.cskill,new BsuEvent(){
					boolean ani_attack_finished = false;			//判断攻击动画是否完成
					boolean ani_beattacked_finished = false;		//判断被攻击动画是否完成
					boolean ani_assault_finished = false;			//判断位移动画是否完成
					boolean skill_logic = false;					//判断技能逻辑函数返回值，返回true为自身释放技能，返回false为对其他单位释放技能
					@Override
					public void notify(Object obj, String msg) {
//						if (!skill_logic && r.cskill.skillLogic(r, atkrs))
//							atkrs.clear();
						if(!skill_logic)
							atkrs.clear();
						skill_logic = true;
						// 判断攻击动画是否完成
						if (msg.equals("ani_attack_finished")
								|| r.getCskill().ani_self == null)
							ani_attack_finished = true;

						// 判断被攻击动画是否完成
						if (msg.equals("ani_beattacked_finished")
								|| r.getCskill().ani_object == null)
							ani_beattacked_finished = true;
						
						//判断位移动画是否完成
						if(msg.equals("assaultAcion_finished"))
							ani_assault_finished = true;
						
						//技能两处动画都完成进行下步任务
						if(ani_attack_finished && ani_beattacked_finished && ani_assault_finished){
							currTaskComFlag = true;
						}
					}
				});
			} else {
				currTaskComFlag = true;
			}

			while (!currTaskComFlag) {
				System.out.println("currTaskComFlag");
				Thread.sleep(200);
			}
			//2:执行移动命令
			if(isMove){
				if(!r.hasAnatherRole(allRoles)){
					Commander.this.moveDirectCommand(r, direct, new BsuEvent(){
						@Override
						public void notify(Object obj, String msg) {
//							System.out.println("msg:"+msg+" waitRoleFlag1:"+waitRoleFlag+" role:"+r.name);
							waitRoleFlag = false;
						}
					});
				}else{stopedCommand(r);waitRoleFlag = false;}
			}else{waitRoleFlag = false;}


			// 等待动作完成
			while (waitRoleFlag) {
//				System.out.println("thread waitRoleFlag:"+waitRoleFlag);
				Thread.sleep(200);
			}
		}
		be.notify(this, "command_roles_completed");
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
		Array<Vector2> vs = h.type == Type.HERO ? s.getRange() : s.flipRange(); // 技能作用范围
		// 如果技能为f_box类型，重新计算技能攻击范围
		if (s.type == Skill.Type.f_box) {
			vs.clear();
			int offset = h.type == Type.HERO ? 1 : -1;
			for (int i = 0; i < s.val; i++)
				vs.add(new Vector2(i + offset, 0));
		}
		Array<Role> retrs = new Array<Role>();				//返回符合类型的英雄
		Array<Role> checkrs = null;							//要检查的单位群体类型，伤害类技能检查敌人，回血buff类检查hero
		if(s.type==Skill.Type.f_damage ||s.type==Skill.Type.f_shifhp ||s.type==Skill.Type.f_box ||s.type==Skill.Type.p_atkbeat ||
				s.type==Skill.Type.p_assault||s.type==Skill.Type.p_damage ||s.type==Skill.Type.pdot_damage ||s.type==Skill.Type.prob_blind ||
				s.type==Skill.Type.prob_dizzy ||s.type==Skill.Type.prob_nude )
			checkrs = h.type==Type.HERO?npcs:heros;
		else if(s.type==Skill.Type.f_healing ||s.type==Skill.Type.p_healing  ||s.type==Skill.Type.pbuff_atk ||
				s.type==Skill.Type.pbuff_def ||s.type==Skill.Type.pbuff_healing ||s.type==Skill.Type.pbuff_hp )
			checkrs = h.type==Type.HERO?heros:npcs;
		//如果没有指定检查Role直接返回retrs
		if(checkrs == null)
			return retrs;
		for (Vector2 v : vs) {
			for (Role r : checkrs) {
				if(r.isDead || !r.isVisible()) continue;	//如果单位死亡或未启用跳过检查
				// 如果技能为全图技能，加入作用单位继续循环
				if (s.otype == ObjectType.all) {
					retrs.add(r);
					continue;
				}

				if ((v.x + h.getBoxX()) == r.getBoxX()
						&& (v.y + h.getBoxY()) == r.getBoxY()) {
					retrs.add(r);
					// 如果技能为单体技能，直接返回结果数组
					if (s.otype == ObjectType.single)
						return retrs;
				}
			}
		}
		return retrs;
	}

	/**
	 * 向role下命令，命令其如何移动,只针对hero方
	 * 
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
			this.moveDirectCommand(r, d, new BsuEvent() {
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
	 * 
	 * @param r
	 *            要致死的Role
	 */
	public void commandRoleDead(final Role r) {
//		deadRole = r;
		// 后续处理
//		System.out.println(r.name+"说:啊啊啊啊啊，我死了！！！");
		r.isDead = true;					//设置人物死亡

		r.deadAction(new BsuEvent(){
			@Override
			public void notify(Object obj,String msg){
				r.setVisible(false);				//设置人物不显示
			}
		});
	}
	
	/**
	 * 回合结束后判断战斗是否结束，胜利或失败
	 */
	private void decideGameEnd(){
		int heroRemainCount = 0;						//英雄剩余数量
		int npcRemainCount = 0;							//敌人剩余数量
		int npcVisibleCount = 0;						//敌人上场数量
		for(Role r:heros)
			if(!r.isDead)
				heroRemainCount ++;
		
		if(heroRemainCount==0){gamescreen.battleEnd(false);}
			//战斗失败
		
		
		for(Role r:npcs){
			if(!r.isDead)
				npcRemainCount ++;
			if(r.isVisible())
				npcVisibleCount++;
		}
		//如果剩余的npc为0，游戏胜利
		if(npcRemainCount==0){gamescreen.battleEnd(true);}
		else{
			//否则剩余npc数量不为0，并且npcVisibleCount为0了，命令加入其他的敌人
			if(npcVisibleCount==0)
				commandNpcArise(gamescreen.npcArisePos,npcs);
		}
	}
	/**
	 * 敌人出现函数
	 * @param pos	出生地参数
	 * @param npcs	所有敌人
	 */
//	private Random rnd = new Random();
	private void commandNpcArise(Array<Vector2> v,Array<Role> rs){
		Array<Role> npcsIsLived = new Array<Role>();		//要加入的敌人
		for(Role r:rs){
			if(!r.isDead && !r.isVisible())
				npcsIsLived.add(r);
		}
		
		for(int i=0;i<(npcsIsLived.size<v.size?npcsIsLived.size:v.size);i++){
			Role r = npcsIsLived.get(i);
			r.setPosition(v.get(i).x, v.get(i).y);
//			r.set_ani_from_state(STATE.idle);			
			r.setVisible(true);
		}
		
		
//		Vector2 pos = ppos.get(rnd.nextInt(ppos.size));				//获得一个随机位置
//		if(npcs.size<ppos.size){
//			Role r = pnpcs.pop();
//			r.setPosition(pos.x*GC.rect_box_width, pos.y*GC.rect_box_height);
//			r.setVisible(true);
//		}
	}
	
	
	public Array<Role> getHeros() {
		return heros;
	}
}
