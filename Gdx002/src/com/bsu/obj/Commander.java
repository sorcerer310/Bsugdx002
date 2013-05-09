package com.bsu.obj;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*; 
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.bsu.tools.Configure.STATE;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * 指挥官对象，用来指挥stage上所有的角色
 * @author fengchong
 *
 */
public class Commander {
	private Stage stage = null;
	private Array<Actor> lactor = null; 
	public Commander(Stage s){
		stage = s;
		lactor = stage.getActors();
	}
	/**
	 * 回合结束，命令所有的角色行动
	 */
	public void roundEnd(){
		for(Actor act:lactor){
			if(act instanceof Role){
				final Role r = (Role)act;
				if(r.getType()==Role.Type.HERO){
<<<<<<< HEAD
					r.state = STATE.move;
					
					//此种写法需要引入静态包import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*; 
					r.addAction(sequence(moveBy(32,0,1),rotateBy(10),run(new Runnable(){
						@Override
						public void run() {
							System.out.println("Action is completed");
						}
					})));
					//另一种写法，此处不用引入静态包
//					Action:{
//						r.addAction(sequence(moveBy(32,0,1),rotateBy(10),run(new Runnable(){
//							@Override
//							public void run() {
//								System.out.println("Action is completed");
//							}
//						})));
//					}
					
				}else if(r.getType() == Role.Type.ENEMY){
					r.state = STATE.move;
					r.addAction(moveBy(-32, 0,1));
=======
					r.set_ani_from_state(STATE.move);
					r.addAction(sequence(moveBy(32,0,1),rotateBy(10),run(new Runnable(){  
					    @Override  
					    public void run() {  
					        r.set_ani_from_state(STATE.idle);
					    }  
					})));  
				}else if(r.getType() == Role.Type.ENEMY){
					r.set_ani_from_state(STATE.move);
					r.addAction(sequence(moveBy(-32,0,1),rotateBy(10),run(new Runnable(){  
					    @Override  
					    public void run() {  
					    	 r.set_ani_from_state(STATE.idle);
					    }  
					})));
				}
			}
		}
	}
	/**
	 * 回合结束，命令所有的角色行动
	 */
	public void upAction(){
		for(Actor act:lactor){
			if(act instanceof Role){
				final Role r = (Role)act;
				if(r.getType()==Role.Type.HERO){
					r.set_ani_from_state(STATE.move);
					r.addAction(sequence(moveBy(0,32,1),rotateBy(10),run(new Runnable(){  
					    @Override  
					    public void run() {  
					        r.set_ani_from_state(STATE.idle);
					    }  
					})));  
				}else if(r.getType() == Role.Type.ENEMY){
		
				}
			}
		}
	}
	/**
	 * 回合结束，命令所有的角色行动
	 */
	public void downAction(){
		for(Actor act:lactor){
			if(act instanceof Role){
				final Role r = (Role)act;
				if(r.getType()==Role.Type.HERO){
					r.set_ani_from_state(STATE.move);
					r.addAction(sequence(moveBy(0,-32,1),rotateBy(10),run(new Runnable(){  
					    @Override  
					    public void run() {  
					        r.set_ani_from_state(STATE.idle);
					    }  
					})));  
				}else if(r.getType() == Role.Type.ENEMY){
		
				}
			}
		}
	}
	/**
	 * 回合结束，命令所有的角色行动
	 */
	public void leftAction(){
		for(Actor act:lactor){
			if(act instanceof Role){
				final Role r = (Role)act;
				if(r.getType()==Role.Type.HERO){
					r.set_ani_from_state(STATE.move);
					r.addAction(sequence(moveBy(-32,0,1),rotateBy(10),run(new Runnable(){  
					    @Override  
					    public void run() {  
					        r.set_ani_from_state(STATE.idle);
					    }  
					})));  
				}else if(r.getType() == Role.Type.ENEMY){
		
>>>>>>> TIL
				}
			}
		}
	}
}
