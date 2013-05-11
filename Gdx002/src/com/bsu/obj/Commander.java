package com.bsu.obj;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.bsu.make.SkillFactory;
import com.bsu.obj.Role.Type;
import com.bsu.tools.Configure;
import com.bsu.tools.Configure.STATE;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

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
		//此处区分处英雄与敌人npc
		for(Actor act:lactor){
			if(act instanceof Role){
				if(((Role) act).getType()==Type.HERO)
					heros.add((Role) act);
				else if(((Role) act).getType()==Type.ENEMY)
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

	private void mapEvent(){
		//从stage中获得mb
		Array<Actor> acts = stage.getActors();
		MapBox mb = null;
		for(Actor act:acts){
			if(act instanceof MapBox){
				mb = (MapBox) act;
				break;
			}
		}
		if(mb==null)
			return;
		
		//处理一些地图块事件
	}

	/**
	 * 指挥英雄们进行行动
	 */

	private void commandHeros(){
		//判断攻击范围内是否有敌人,有则攻击敌人
		for(Role h:heros){
			Role r = (Role)h;
			Array<Vector2> vs = r.getCurrSkillRange();				//获得当前技能的攻击范围
			Array<Role> atkrs = getRolsInSkillRange(vs,npcs);		//获得攻击范围内的作用目标
			//如果坐标目标数量为0，进行下一循环，对下一英雄进行判断
			if(atkrs.size==0)
				continue;
			for(Role e:atkrs)
				r.hero_attack_other(e,SkillFactory.getInstance().getSkillByName("atk"));
		}
		//攻击范围内没有敌人的英雄向前前进一步
		
	}

	/**
	 * 指挥敌人们进行行动
	 */
	private void commandNpcs(){
		//判断攻击范围内是否有英雄，有则攻击英雄
		
		//攻击范围内没有英雄的敌人向前前进一步
		
	}
	/**
	 * 获得技能范围内是否有Role存在
	 * @param v	技能范围
	 * @return	返回符合类型的所有Role
	 */
	private Array<Role> getRolsInSkillRange(Array<Vector2> vs, Array<Role> rs){
		Array<Role> retrs = new Array<Role>();
		for(Vector2 v:vs){
			for(Role r:rs){
				if(v.x == r.getBoxX() && v.y == r.getBoxY())
					retrs.add(r);
			}
		}
		return retrs;
	}
	
	/**
	 * 向role下命令，命令其如何移动,只针对hero方
	 */
	public void moveAction(final Array<Action> a) {
		for (Actor act : lactor) {
			if (act instanceof Role) {
				final Role r = (Role) act;
				if (r.getType() == Role.Type.HERO) {
					r.set_ani_from_state(STATE.move);
					final int index = 0;
					this.act_action_group(r, index, a.size, a);
				}
			}
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
						}
					}
				})));
	}

	/**
	 * 向Role下命令，命令其攻击
	 * 
	 * @param r
	 */
	private void attackAction(Role r) {
		r.set_ani_from_state(STATE.attack_normal);
	}

	/**
	 * 判断Role是否可以攻击
	 */
	public void checkAttackAction(Type type) {
		for (Actor act : lactor) {
			if (act instanceof Role) {
				final Role r = (Role) act;
				if (r.getType() == type) {
					if (r.canAttack()) {
						attackAction(r);
					} else {
						if (type == Type.HERO) {
							rightAction(r);
						} else {
							leftAction(r);
						}
					}
				}
			}
		}
	}
/**
 * 用来检查角色是否被本轮选择移动，如果被选择，则计算可移动范围
 * 我写在这里的方法可以随意删除，仅为个人测试用
 * @author 张永臣
 */
	public void checkHeroSelect() {
		for (Actor act : lactor) {
			if (act instanceof Role) {
				final Role r = (Role) act;
				if (r.getType() == Type.HERO) {
					if(r.get_selected()){
						MapBox.set_hero_pass_box(r,npcs,heros);
					}
				}
			}
		}
	}
	/**
	 * 每轮操作结束后，清空角色方可移动数组
	 */
	private void resetHeroSelect(){
		for (Actor act : lactor) {
			if (act instanceof Role) {
				final Role r = (Role) act;
				if (r.getType() == Type.HERO) {
					r.set_selected(false);
					MapBox.pass_array.clear();
				}
			}
		}
	}

	public Array<Role> getHeros() {
		return heros;
	}
}
