package com.bsu.obj;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*; 
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.bsu.tools.Configure.STATE;

/**
 * ָ�ӹٶ�������ָ��stage�����еĽ�ɫ
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
	 * �غϽ������������еĽ�ɫ�ж�
	 */
	public void roundEnd(){
		for(Actor act:lactor){
			if(act instanceof Role){
				Role r = (Role)act;
				if(r.getType()==Role.Type.HERO){
					r.state = STATE.move;
					
					//����д����Ҫ���뾲̬��import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*; 
					r.addAction(sequence(moveBy(32,0,1),rotateBy(10),run(new Runnable(){
						@Override
						public void run() {
							System.out.println("Action is completed");
						}
					})));
					//��һ��д�����˴��������뾲̬��
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
				}
			}
		}
	}
}
