package com.bsu.obj;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Role.Type;
import com.bsu.screen.GameScreen;
import com.bsu.tools.BsuEvent;
import com.bsu.tools.Configure;
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
	public void roundEnd() {
		resetHeroSelect();
		mapEvent(); // 地图事件
		commandHeros(); // 命令英雄
		commandNpcs(); // 命令NPC

		// for (Actor act : lactor) {
		// if (act instanceof Role) {
		// final Role r = (Role) act;
		// if (r.getType() == Role.Type.HERO) {
		// //if (!MapBox.blocked(r)) {
		// r.set_ani_from_state(STATE.move);
		// // 此种写法需要引入静态包import static
		// // com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
		// r.addAction(sequence(moveBy(Configure.map_box_value, 0,
		// Configure.duration), rotateBy(10),
		// run(new Runnable() {
		// @Override
		// public void run() {
		// r.set_ani_from_state(STATE.idle);
		// }
		// })));
		// //}
		// } else if (r.getType() == Role.Type.ENEMY) {
		// //if (!MapBox.blocked(r)) {
		// r.set_ani_from_state(STATE.move);
		// r.addAction(sequence(moveBy(-Configure.map_box_value, 0,
		// Configure.duration), rotateBy(10),
		// run(new Runnable() {
		// @Override
		// public void run() {
		// r.set_ani_from_state(STATE.idle);
		// }
		// })));
		// //}
		// }
		// }
		// }
	}

	/**
	 * 指定角色向左移动
	 * 
	 * @param r
	 *            要移动的角色，移动结束后将角色的状态转为站立状态
	 */
	public void leftAction(final Role r) {
		if (r.get_ani_from_state() != STATE.idle)
			return;
		r.set_ani_from_state(STATE.move);
		r.addAction(sequence(
				moveBy(-Configure.map_box_value, 0, Configure.duration),
				run(new Runnable() {
					@Override
					public void run() {
						r.set_ani_from_state(STATE.idle);
					}
				})));
	}

	/**
	 * 指定角色向右移动
	 * 
	 * @param r
	 *            要移动的角色，移动结束后将角色的状态转为站立状态 
	 */
	public void rightAction(final Role r) {
		if (r.get_ani_from_state() != STATE.idle)
			return;
		r.set_ani_from_state(STATE.move);
		r.addAction(sequence(
				moveBy(Configure.map_box_value, 0, Configure.duration),
				run(new Runnable() {
					@Override
					public void run() {
						r.set_ani_from_state(STATE.idle);
					}
				})));
	}

	/**
	 * 指定角色向上移动
	 * 
	 * @param r
	 *            要移动的角色，移动结束后将角色的状态转为站立状态 
	 */
	public void upAction(final Role r) {
		if (r.get_ani_from_state() != STATE.idle)
			return;
		r.set_ani_from_state(STATE.move);
		r.addAction(sequence(
				moveBy(0, Configure.map_box_value, Configure.duration),
				run(new Runnable() {
					@Override
					public void run() {
						r.set_ani_from_state(STATE.idle);
					}
				})));
	}

	/**
	 * 指定角色向下移动
	 * 
	 * @param r
	 *            要移动的角色，移动结束后将角色的状态转为站立状态 
	 */
	public void downAction(final Role r) {
		if (r.get_ani_from_state() != STATE.idle)
			return;
		r.set_ani_from_state(STATE.move);
		r.addAction(sequence(
				moveBy(0, -Configure.map_box_value, Configure.duration),
				run(new Runnable() {
					@Override
					public void run() {
						r.set_ani_from_state(STATE.idle);
					}
				})));
	}

	/**
	 * 处理地图块事件，检查地图上特殊属性的块是否有Role在 有则对Role对象进行处理
	 */

	private void mapEvent() {
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
	}

	/**
	 * 指挥英雄们进行行动
	 */
	boolean waitflag = false; // 等待标示，用来标记是否继续循环对下一英雄进行操作

	private void commandHeros() {
		new Thread() {
			@Override
			public void run() {
				try {
					Array<Role> moveRole = new Array<Role>(); // 不进行攻击，准备移动的英雄
					// 第一步所有人都进攻
					for (Role h : heros) {
						waitflag = true; // 初始化等待循环flag为false
						Role r = (Role) h; // 当前操作的英雄
						Array<Vector2> vs = r.getCurrSkillRange(); // 获得当前技能的攻击范围
						Array<Role> atkrs = getRolsInSkillRange(vs, npcs); // 获得攻击范围内的作用目标
						// 如果坐标目标数量为0，进行下一循环，对下一英雄进行判断，当前英雄加入移动队列
						if (atkrs.size == 0) {
							moveRole.add(r);
							continue;
						}
						// 命令当前英雄进攻所有范围内的敌人
						for (Role e : atkrs) {
							r.hero_attack_other(e, r.cskill, new BsuEvent() {
								@Override
								public void notify(Object obj, String msg) {
									System.out.println(msg);
									// 收到消息设置等待标示为true
									if (((Role) obj).name.equals(msg))
										waitflag = false;
								}
							});
						}
						// 循环中等待
						while (waitflag) {	
							Thread.sleep(200);
						}
					}
					// 第二部没进攻的人向前一步
					for (Role h : moveRole)
						Commander.this.rightAction(h);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

		// 攻击范围内没有敌人的英雄向前前进一步

	}

	/**
	 * 指挥敌人们进行行动
	 */
	private void commandNpcs() {
		// 判断攻击范围内是否有英雄，有则攻击英雄

		// 攻击范围内没有英雄的敌人向前前进一步

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
	 */
	public void moveAction(Role r, final Array<Action> a) {
		GameScreen.controlled = false;
		if (r.getType() == Role.Type.HERO) {
			r.set_ani_from_state(STATE.move);
			final int index = 0;
			this.act_action_group(r, index, a.size, a);

		}
	}

	private void act_action_group(final Role r, final int current_index,
			final int max_length, final Array<Action> action) {
		r.addAction(sequence(action.get(current_index), rotateBy(10),
				run(new Runnable() {
					@Override
					public void run() {
						if (current_index < max_length - 1) {
							act_action_group(r, current_index + 1, max_length,
									action);
						} else {
							r.set_ani_from_state(STATE.idle);
							GameScreen.controlled = true;
						}
					}
				})));
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
	private void resetHeroSelect() {
		for (Actor act : lactor) {
			if (act instanceof Role) {
				final Role r = (Role) act;
				if (r.getType() == Type.HERO) {
					r.setSelected(false);
					r.setControlled(false);
					r.getPass_array().clear();
				}
			}
		}
	}

	public Array<Role> getHeros() {
		return heros;
	}
}
