package com.bsu.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
/**
 * 技能对象
 * @author fengchong
 *
 */
public class Skill {
	public static enum Type {ATK,HP};						//当前技能类型是攻击类型还是回复HP类型
	private Type type = Type.ATK;							//设置默认类型为攻击类型
	private String name = "";								//技能名称
	private float val = 0;									//技能效果值ֵ
	public Animation ani_self = null;						//技能自身动画效果
	public Animation ani_object = null;						//技能目标动画效果
	private String info = "";								//技能描述
	private Array<Vector2> range = new Array<Vector2>();	//技能释放范围
	

	/**
	 * 技能初始化构造函数
	 * @param n		技能名字
	 * @param t		技能类型
	 * @param v		技能值
	 * @param as	技能自身动画
	 * @param ao	技能目标动画
	 * @param i		技能描述信息
	 * @param r		技能攻击范围
	 */
	public Skill(String n,Type t,float v,Animation as,Animation ao,String i,Array<Vector2> r){
		name = n;
		type = t;
		val = v;
		ani_self = as;
		ani_object = ao;
		info = i;
		range = r;
	}

	public Array<Vector2> getRange() {
		return range;
	}


	public float getVal() {
		return val;
	}
}
