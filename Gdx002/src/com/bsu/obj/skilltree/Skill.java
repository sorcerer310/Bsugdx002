package com.bsu.obj.skilltree;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bsu.tools.Configure;
import com.bsu.tools.Configure.CLASSES;
import com.bsu.tools.Configure.QUALITY;
/**
 * 技能对象
 * @author fengchong
 *
 */
public class Skill {
	//技能值类型分为固定伤害，固定回血，百分比回血，攻击力倍数伤害，方格数，hp百分比，防御百分比，攻击百分比，护甲为0触发机率，致盲触发机率，通关奖励，游戏运行速度
  	//前缀解释 f:fixed固定的，fdot:固定持续伤害，p:percent百分比,pbuff:百分比buff,prob:机率
	//后缀解释 damage:伤害，healing:治疗,hp:血上限，def:防御上限，atk：攻击上限，nude:碎甲，dizzy：眩晕，blind:致盲，atkbeat:造成伤害并击退，shifhp:转移生命,speed:游戏速度,reward:奖励,box:格子
	public static enum Type {f_damage,f_healing,f_shifhp,f_box,p_healing,p_damage,p_lucky,p_atkbeat,p_reward,p_speed,pbuff_hp,pbuff_healing,pbuff_atk,pbuff_def,pdot_damage,prob_dizzy,prob_nude,prob_blind};
	private int id = 0;										//技能索引
	public String name = "";								//技能名称唯一索引
	public QUALITY quality = QUALITY.green;					//技能品质，默认为绿色
	private Type type = Type.f_damage;						//设置默认类型为攻击类型
	public float val = 0;									//技能效果值ֵ
	public float uval = 0;									//下级升级递增值 
	public int lev = 1;										//技能级别，默认为1级，满级6级
	private CLASSES[] classes = null;						//技能适用的职业范围 
	//private int price = 1;									//技能的价值，用于技能树生成
	private String info = "";								//技能描述
	private Array<Vector2> range = new Array<Vector2>();	//技能释放范围
	
	public Animation ani_self = null;						//技能自身动画效果
	public Animation ani_object = null;						//技能目标动画效果


	

	/**
	 * 技能初始化构造函数
	 * @param n		技能名字
	 * @param q		技能品质
	 * @param t		技能类型
	 * @param v		技能值
	 * @param uv	技能值下级升级递增值
	 * @param ac 	技能适用职业
	 * @param i		技能描述信息
	 * @param as	技能自身动画
	 * @param ao	技能目标动画
	 * @param r		技能攻击范围
	 */
	public Skill(int pi,String n,QUALITY q,Type t,float v,float uv,CLASSES[] ac,String i,Animation as,Animation ao,Vector2[] r){
		id = pi;
		name = n;
		quality = q;
		type = t;
		val = v;
		uval = uv;
		classes = ac;
		info = i;
		ani_self = as;
		ani_object = ao;
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
