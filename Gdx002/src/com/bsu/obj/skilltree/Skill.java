package com.bsu.obj.skilltree;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Commander;
import com.bsu.obj.Role;
import com.bsu.obj.skilltree.ContinuedSkillState.CSType;
import com.bsu.tools.U;
import com.bsu.tools.Configure.CLASSES;
import com.bsu.tools.Configure.QUALITY;
/**
 * 技能对象
 * @author fengchong
 *
 */
public class Skill {
  	//前缀解释 f:fixed固定的，fdot:固定持续伤害，pdot百分比持续伤害，p:percent百分比,pbuff:百分比buff,prob:机率
	//后缀解释 damage:伤害，healing:治疗,hp:血上限，def:防御上限，atk：攻击上限，nude:碎甲，dizzy：眩晕，blind:致盲，atkbeat:造成伤害并击退，shifhp:转移生命,speed:游戏速度,reward:奖励,box:格子
	public static enum Type {f_damage,f_healing,f_shifhp,f_box,p_damage,p_healing,p_lucky,p_atkbeat,p_reward,p_speed,pbuff_hp,pbuff_healing,pbuff_atk,pbuff_def,pdot_damage,prob_dizzy,prob_nude,prob_blind};
	public static enum ObjectType{single,multi};			
	public int id = 0;										//技能索引
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
	public TextureRegion icon = null;						//技能图标
	
	public Animation ani_self = null;						//技能释放者动画效果
	public Animation ani_object = null;						//技能承受者动画效果
//	public Animation ani_conself = null;					//持续技能释放者动画效果
//	public Animation ani_conobject = null;					//持续技能承受者动画效果
	public Animation ani_continue = null;					//持续技能承受者动画效果，暂时只有承受者有动画效果。
	
	public boolean enable = false;							//该技能是否开启使用


	

	/**
	 * 技能初始化构造函数
	 * @param n		技能名字
	 * @param q		技能品质
	 * @param t		技能类型
	 * @param v		技能值
	 * @param uv	技能值下级升级递增值
	 * @param ac 	技能适用职业
	 * @param i		技能描述信息
	 * @param tr	技能图标
	 * @param as	技能自身动画
	 * @param ao	技能目标动画
	 * @param anic	技能持续效果动画
	 * @param r		技能攻击范围
	 */
	public Skill(int pi,String n,QUALITY q,Type t,float v,float uv,CLASSES[] ac,String i,TextureRegion tr,Animation as,Animation ao,Animation anic,Vector2[] r){
		id = pi;
		name = n;
		quality = q;
		type = t;
		val = v;
		uval = uv;
		classes = ac;
		info = i;
		icon = tr;
		ani_self = as;
		ani_object = ao;
		ani_continue = anic;
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
		if(type==Skill.Type.f_damage ||type==Skill.Type.f_shifhp ||type==Skill.Type.f_box||type==Skill.Type.p_atkbeat ||
				type==Skill.Type.p_damage ||type==Skill.Type.pdot_damage ||type==Skill.Type.prob_blind ||
				type==Skill.Type.prob_dizzy ||type==Skill.Type.prob_nude){
			//固定伤害处理
			if(type==Skill.Type.f_damage){
				object.currentHp = (int) (object.currentHp - U.realDamage((int)(owner.getAttack()+val), object.getDefend()) >= 0 
						? object.currentHp - U.realDamage((int)(owner.getAttack()+val), object.getDefend()): 0);
			//扩展格子伤害
			}else if(type==Skill.Type.f_box){
				object.currentHp = (int) (object.currentHp - U.realDamage((int)(owner.getAttack()), object.getDefend()) >= 0 
						? object.currentHp - U.realDamage((int)(owner.getAttack()), object.getDefend()): 0);
			//转移伤害至生命	
			}else if(type==Skill.Type.f_shifhp){
				object.currentHp = (int) (object.currentHp - U.realDamage((int)(owner.getAttack()+val), object.getDefend()) >= 0 
						? object.currentHp - U.realDamage((int)(owner.getAttack()+val), object.getDefend()): 0);	//伤害敌人
				owner.currentHp = (int)(owner.currentHp + U.realDamage((int)(owner.getAttack()+val), object.getDefend())/2) >= owner.getMaxHp() //转移生命至自己
						? owner.getMaxHp() : (int)(owner.currentHp + U.realDamage((int)(owner.getAttack()+val), object.getDefend())/2);
			//击退伤害
			}else if(type==Skill.Type.p_atkbeat){
				object.currentHp = (int) (object.currentHp - U.realDamage((int)(owner.getAttack()*val), object.getDefend()) >= 0 
						? object.currentHp - U.realDamage((int)(owner.getAttack()*val), object.getDefend()): 0);	//伤害敌人
				Commander.getInstance().heatAction(object);	//击退
			//百分比伤害
			}else if(type==Skill.Type.p_damage){
				object.currentHp = (int) (object.currentHp - U.realDamage((int)(owner.getAttack()*val), object.getDefend()) >= 0 
					? object.currentHp - U.realDamage((int)(owner.getAttack()*val), object.getDefend()): 0);	//伤害敌人
			//持续伤害，默认为3回合
			}else if(type==Skill.Type.pdot_damage){
				object.csstate.add(new ContinuedSkillState(3,owner.getAttack()*val,CSType.dot,ani_continue));
			//一定机率致盲
			}else if(type==Skill.Type.prob_blind){
				//此处增加机率代码
				if(U.probability(val))
					object.csstate.add(new ContinuedSkillState(1,0,CSType.blind,ani_continue));
			//一定机率眩晕
			}else if(type==Skill.Type.prob_dizzy){
				//此处增加机率代码
				if(U.probability(val))
					object.csstate.add(new ContinuedSkillState(1,0,CSType.dizzy,ani_continue));
			//破甲效果
			}else if(type==Skill.Type.prob_nude){
				object.csstate.add(new ContinuedSkillState(3,val,CSType.debuff_def,ani_continue));
			}
			
			return false;
		}
		else if(type==Skill.Type.f_healing  ||type==Skill.Type.pbuff_atk ||
				type==Skill.Type.pbuff_def ||type==Skill.Type.pbuff_healing ||type==Skill.Type.pbuff_hp){
			//固定治疗
			if(type==Skill.Type.f_healing){
				object.currentHp = (int)(object.currentHp+this.val>=object.getMaxHp()?object.currentHp:object.currentHp+val);
			//百分比攻击力buff
			}else if(type==Skill.Type.pbuff_atk){
				object.csstate.add(new ContinuedSkillState(2,object.getAttack()*val,CSType.buff_atk,ani_continue));
			//百分比防御力buff
			}else if(type==Skill.Type.pbuff_def){
				object.csstate.add(new ContinuedSkillState(2,object.getDefend()*val,CSType.buff_def,ani_continue));
			//百分比持续性治疗
			}else if(type==Skill.Type.pbuff_healing){
				object.currentHp = (int)(object.currentHp+object.currentHp*val>=object.getMaxHp()?object.currentHp:object.currentHp+object.currentHp*val);
			//百分比生命最大值
			}else if(type==Skill.Type.pbuff_hp){
				object.csstate.add(new ContinuedSkillState(2,object.getMaxHp()*val,CSType.buff_hp,ani_continue));
			}
			if(owner== object)
				return true;
			else
				return false;
		}
		else return false;
	}
	
}
