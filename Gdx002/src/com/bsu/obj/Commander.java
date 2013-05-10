package com.bsu.obj;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.bsu.tools.Configure.STATE;
import com.sun.tools.internal.xjc.reader.gbind.Sequence;

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

	public Commander(Stage s) {
		stage = s;
		lactor = stage.getActors();
	}

	/**
	 * 回合结束，命令所有的角色行动
	 */
	public void roundEnd() {
		for (Actor act : lactor) {
			if (act instanceof Role) {
				final Role r = (Role) act;
				if (r.getType() == Role.Type.HERO) {
					if (!MapBox.blocked(r)) {
						r.set_ani_from_state(STATE.move);
						// 此种写法需要引入静态包import static
						// com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
						r.addAction(sequence(moveBy(32, 0, 1), rotateBy(10),
								run(new Runnable() {
									@Override
									public void run() {

									}
								})));
						// 另一种写法，此处不用引入静态包
						// Action:{
						// r.addAction(sequence(moveBy(32,0,1),rotateBy(10),run(new
						// Runnable(){
						// @Override
						// public void run() {
						// System.out.println("Action is completed");
						// }
						// })));
						// }
					}
				} else if (r.getType() == Role.Type.ENEMY) {
					if (!MapBox.blocked(r)) {
						r.set_ani_from_state(STATE.move);
						r.addAction(sequence(moveBy(-32, 0, 1), rotateBy(10),
								run(new Runnable() {
									@Override
									public void run() {
										r.set_ani_from_state(STATE.idle);
									}
								})));
					}
				}
			}
		}
	}

	/**
	 * 回合结束，命令所有的角色行动
	 */
	public void moveAction(final Array<Action> a) {
		for (Actor act : lactor) {
			if (act instanceof Role) {
				final Role r = (Role) act;
				if (r.getType() == Role.Type.HERO) {
					r.set_ani_from_state(STATE.move);
					final int index = 0;
					this.act_action_group(r, index, a.size, a);
				} else if (r.getType() == Role.Type.ENEMY) {

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

}
