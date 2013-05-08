package com.bsu.obj;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Skill {
	public static enum Type {ATK,HP};		//技能类型，暂定攻击和加血技能
	private Type type = Type.ATK;			//技能类型变量
	private String name = "";				//技能命
	private float val = 0;					//技能效果值
	private Animation ani_self = null;		//自身动画
	private Animation ani_object = null;	//释放对象动画
	private String info = "";				//技能介绍信息
	
	public Skill(String n,Type t,float v,Animation as,Animation ao,String i){
		name = n;
		type = t;
		val = v;
		ani_self = as;
		ani_object = ao;
		info = i;
	}
	/**
	 * 播放技能自身动画
	 * @param a		技能释放者
	 */
	public void playSelfAni(Actor a){
		float x = a.getX();
		float y = a.getY();
		
	}
	/**
	 * 播放技能目标动画 
	 * @param a		技能接受者
	 */
	public void playObjAni(Actor a){
		float x = a.getX();
		float y = a.getY();
	}

}
