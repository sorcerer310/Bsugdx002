package com.bsu.obj.skilltree;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Commander;
import com.bsu.obj.Role;
import com.bsu.tools.U;
import com.bsu.tools.Configure.CLASSES;
import com.bsu.tools.Configure.QUALITY;
/**
 * 技能对象
 * @author fengchong
 *
 */
public class Skill {
  	//前缀解释 f:fixed固定的，fdot:固定持续伤害，p:percent百分比,pbuff:百分比buff,prob:机率
	//后缀解释 damage:伤害，healing:治疗,hp:血上限，def:防御上限，atk：攻击上限，nude:碎甲，dizzy：眩晕，blind:致盲，atkbeat:造成伤害并击退，shifhp:转移生命,speed:游戏速度,reward:奖励,box:格子
	public static enum Type {f_damage,f_healing,f_shifhp,f_box,p_healing,p_damage,p_lucky,p_atkbeat,p_reward,p_speed,pbuff_hp,pbuff_healing,pbuff_atk,pbuff_def,pdot_damage,prob_dizzy,prob_nude,prob_blind};
	public static enum ObjectType{single,multi};			
	private int id = 0;										//技能索引
	public String name = "";								//技能名称唯一索引
	public QUALITY quality = QUALITY.green;					//技能品质，默认为绿色
	public Type type = Type.f_damage;						//设置默认类型为攻击类型
	public ObjectType otype = ObjectType.single;			//指定技能为单体还是群体
	public float val = 0;									//技能效果值ֵ
	public float uval = 0;									//下级升级递增值 
	public int lev = 1;										//技能级别，默认为1级，满级6级
	private CLASSES[] classes = null;						//技能适用的职业范围 
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
	/**
	 * 技能对目标的逻辑处理
	 * @param owen		技能释放者
	 * @param object	技能目标
	 * @return			返回一个布尔值，表示owner是否还继续向前移动
	 */
	public boolean skillLogic(Role owner,Role object){
		if(type==Skill.Type.f_damage ||type==Skill.Type.f_shifhp ||type==Skill.Type.p_atkbeat ||
				type==Skill.Type.p_damage ||type==Skill.Type.pdot_damage ||type==Skill.Type.prob_blind ||
				type==Skill.Type.prob_dizzy ||type==Skill.Type.prob_nude){
			//固定伤害处理
			if(type==Skill.Type.f_damage){
				object.currentHp = (int) (object.currentHp - U.realDamage((int)(owner.value_attack+val), object.value_defend) >= 0 
						? object.currentHp - U.realDamage((int)(owner.value_attack+val), object.value_defend): 0);
			//转移伤害至生命	
			}else if(type==Skill.Type.f_shifhp){
				object.currentHp = (int) (object.currentHp - U.realDamage((int)(owner.value_attack+val), object.value_defend) >= 0 
						? object.currentHp - U.realDamage((int)(owner.value_attack+val), object.value_defend): 0);	//伤害敌人
				owner.currentHp = (int)(owner.currentHp + U.realDamage((int)(owner.value_attack+val), object.value_defend)/2) >= owner.maxHp //转移生命至自己
						? owner.maxHp : (int)(owner.currentHp + U.realDamage((int)(owner.value_attack+val), object.value_defend)/2);
			//击退伤害
			}else if(type==Skill.Type.p_atkbeat){
				object.currentHp = (int) (object.currentHp - U.realDamage((int)(owner.value_attack*val), object.value_defend) >= 0 
						? object.currentHp - U.realDamage((int)(owner.value_attack*val), object.value_defend): 0);	//伤害敌人
				Commander.getInstance().heatAction(object);	//击退
			//百分比伤害
			}else if(type==Skill.Type.p_damage){
				object.currentHp = (int) (object.currentHp - U.realDamage((int)(owner.value_attack*val), object.value_defend) >= 0 
					? object.currentHp - U.realDamage((int)(owner.value_attack*val), object.value_defend): 0);	//伤害敌人
			//持续伤害，默认为3回合
			}else if(type==Skill.Type.pdot_damage){
				
			}
			object.currentHp = (int) (object.currentHp - this.val >= 0 ? object.currentHp
				- this.val
				: 0);
			return false;
		}
		else if(this.type==Skill.Type.f_healing  ||this.type==Skill.Type.pbuff_atk ||
				this.type==Skill.Type.pbuff_def ||this.type==Skill.Type.pbuff_healing ||this.type==Skill.Type.pbuff_hp){
			object.currentHp = (int)(object.currentHp + this.val >= object.maxHp ? object.currentHp
					:object.currentHp+this.val);
			if(owner== object)
				return true;
			else
				return false;
		}
		else return false;
//		if (object.currentHp == 0) {
//			npcthis.removeValue(object, true); // 从Commander逻辑计算队列中移除
//			object.getParent().removeActor(object); // 从父Actor中移除该Actor节点
//		}
	}
	
}
