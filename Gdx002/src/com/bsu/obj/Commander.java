package com.bsu.obj;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Role.Type;
import com.bsu.screen.GameScreen;
import com.bsu.tools.BsuEvent;
import com.bsu.tools.Configure;
import com.bsu.tools.Configure.DIRECTION;
import com.bsu.tools.Configure.STATE;

/**
 * 指挥官对象，用来指挥stage上所有的角色
 * 
 * @author fengchong
 * 
 */
public class Commander {
	private Stage stage = null;
	private Array<Actor> lactor = null;
	private Array<Role> heros = new Array<Role>();
	private Array<Role> npcs = new Array<Role>();

	public Commander(Stage s) {
		stage = s;
		lactor = stage.getActors();
		heros.clear();
		npcs.clear();
		// 此处区分处英雄与敌人npc
		for (Actor act : lactor) {
			if (act instanceof Role) {
				if (((Role) act).getType() == Type.HERO)
					heros.add((Role) act);
				else if (((Role) act).getType() == Type.ENEMY)
					npcs.add((Role) act);
			}
		}
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
		resetHeroValue();
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					mapEvent(new BsuEvent() {
						@Override
						public void notify(Object obj, String msg) {
							waitNextCommand = false;
						}
					}); // 地图事件
					while (waitNextCommand)
						Thread.sleep(200);
					waitNextCommand = true;
					commandHeros(new BsuEvent() {
						@Override
						public void notify(Object obj, String msg) {
							waitNextCommand = false;
						}
					}); // 命令英雄
					while (waitNextCommand)
						Thread.sleep(200);
					waitNextCommand = true;

					commandNpcs(new BsuEvent() {
						@Override
						public void notify(Object obj, String msg) {
							waitNextCommand = false;
						}
					}); // 命令NPC
					while (waitNextCommand)
						Thread.sleep(200);
					waitNextCommand = true;
					roundEndCompleted = true;

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();

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

		r.addAction(sequence(moveBy(x, y, Configure.duration),
				run(new Runnable() {
					@Override
					public void run() {
						r.set_ani_from_state(STATE.idle);
						if (be != null)
							be.notify(r, r.name);
					}
				})));
	}

	/**
	 * 处理地图块事件，检查地图上特殊属性的块是否有Role在 有则对Role对象进行处理
	 * 
	 * @param be
	 *            地图操作完成事件
	 */
	private void mapEvent(BsuEvent be) {
		// 从stage中获得mb
		Array<Actor> acts = stage.getActors();
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

	private void commandHeros(BsuEvent be) throws InterruptedException {
		// 第一步所有人都进攻
		for (Role h : heros) {
			waitRoleFlag = true; // 初始化等待循环flag为true
			Role r = (Role) h; // 当前操作的英雄
			Array<Vector2> vs = r.getCurrSkillRange(); // 获得当前技能的攻击范围
			Array<Role> atkrs = getRolsInSkillRange(vs, npcs); // 获得攻击范围内的作用目标
			// 如果坐标目标数量为0，进行下一循环，对下一英雄进行判断，当前英雄加入移动队列
			if (atkrs.size == 0) { 
				if (!r.hasAnatherRole(heros)) {
					Commander.this.directAction(r, DIRECTION.right,
							new BsuEvent() {
								@Override
								public void notify(Object obj, String msg) {
									System.out.println("move:" + msg);
									// 收到消息设置等待标示为false
									waitRoleFlag = false;
								}
							});
				} else{
					System.out.println("hasAnatherRole");
//					waitRoleFlag = false;
				}
			} else {
				// 命令当前英雄进攻所有范围内的敌人
				for (Role e : atkrs) {
					r.hero_attack_other(e, r.getCskill(), new BsuEvent() {
						@Override
						public void notify(Object obj, String msg) {
							System.out.println("attack:" + msg);
							// 收到消息设置等待标示为false
							waitRoleFlag = false;
						}
					});
				}
			}
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
		// 第一步所有敌人都进攻
		for (Role e : npcs) {
			waitRoleFlag = true; // 初始化等待循环flag为false
			Array<Vector2> vs = e.getCurrSkillRange(); // 获得当前技能的攻击范围
			Array<Role> atkrs = getRolsInSkillRange(vs, heros); // 获得攻击范围内的作用目标
			// 如果坐标目标数量为0，进行下一循环，对下一英雄进行判断，当前英雄加入移动队列
			if (atkrs.size == 0) {
				if (!e.hasAnatherRole(npcs)) {
					Commander.this.directAction(e, DIRECTION.left,
							new BsuEvent() {
								@Override
								public void notify(Object obj, String msg) {
									// 收到消息设置等待标示为false
									waitRoleFlag = false;
								}
							});
					
				}else {
					waitRoleFlag = false;
				}
			} else {
				// 命令当前英雄进攻所有范围内的敌人
				for (Role h : atkrs) {
					e.hero_attack_other(h, e.getCskill(), new BsuEvent() {
						@Override
						public void notify(Object obj, String msg) {
							if (((Role) obj).name.equals(msg))
								waitRoleFlag = false;
						}
					});
				}
			}
			// 循环中等待
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
	private Array<Role> getRolsInSkillRange(Array<Vector2> vs, Array<Role> rs) {
		Array<Role> retrs = new Array<Role>();
		for (Vector2 v : vs) {
			for (Role r : rs) {
				if (v.x == r.getBoxX() && v.y == r.getBoxY())
					retrs.add(r);
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
		GameScreen.setControlled(false);
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
					GameScreen.setControlled(true);
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
									r.set_ani_from_state(STATE.idle);
									GameScreen.setControlled(true);
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
		for (Actor act : lactor) {
			if (act instanceof Role) {
				final Role r = (Role) act;
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
	}

	public Array<Role> getHeros() {
		return heros;
	}
}
