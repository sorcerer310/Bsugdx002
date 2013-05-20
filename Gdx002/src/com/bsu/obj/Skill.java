package com.bsu.obj;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bsu.tools.Configure;
import com.bsu.tools.Configure.QUALITY;
/**
 * 技能对象
 * @author fengchong
 *
 */
public class Skill {
	public static enum Type {ATK,HP};						//当前技能类型是攻击类型还是回复HP类型
	private Type type = Type.ATK;							//设置默认类型为攻击类型
	public String name = "";								//技能名称唯一索引
	public QUALITY quality = QUALITY.green;					//技能品质，默认为绿色
	public int val = 0;										//技能效果值ֵ
	public int uval = 0;									//下级升级递增值 
	public int lev = 1;										//技能级别，默认为1级，满级6级
	public Animation ani_self = null;						//技能自身动画效果
	public Animation ani_object = null;						//技能目标动画效果
	private String info = "";								//技能描述
	private Array<Vector2> range = new Array<Vector2>();	//技能释放范围
	

	/**
	 * 技能初始化构造函数
	 * @param n		技能名字
	 * @param t		技能类型
	 * @param q		技能品质
	 * @param v		技能值
	 * @param uv	技能值下级升级递增值
	 * @param as	技能自身动画
	 * @param ao	技能目标动画
	 * @param i		技能描述信息
	 * @param r		技能攻击范围
	 */
	public Skill(String n,Type t,QUALITY q,int v,int uv,Animation as,Animation ao,String i,Vector2[] r){
		name = n;
		type = t;
		quality = q;
		val = v;
		uval = uv;
		ani_self = as;
		ani_object = ao;
		info = i;
		range.addAll(r);
	}

	public Array<Vector2> getRange() {
		return range;
	}

	/**
	 * 水平翻转技能作用范围
	 */
	public Array<Vector2> flipRange(){
		Array<Vector2> retv = new Array<Vector2>();
		for(Vector2 v:range)
			retv.add(new Vector2(-v.x,v.y));
		return retv;
	}
}
