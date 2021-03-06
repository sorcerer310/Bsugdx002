package com.bsu.obj.skilltree;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bsu.effect.SkillIcon;
import com.bsu.obj.Commander;
import com.bsu.obj.Role;
import com.bsu.obj.data.SkillData;
import com.bsu.obj.skilltree.ContinuedSkillState.CSType;
import com.bsu.tools.GAC;
import com.bsu.tools.GTC;
import com.bsu.tools.U;
import com.bsu.tools.GC.CLASSES;
import com.bsu.tools.GC.QUALITY;

/**
 * 技能对象
 * 
 * @author fengchong
 * 
 */
public class Skill {
	// 前缀解释
	// f:fixed固定的，fdot:固定持续伤害，pdot百分比持续伤害，p:percent百分比,pbuff:百分比buff,prob:机率
	// 后缀解释
	// damage:伤害，healing:治疗,hp:血上限，def:防御上限，atk：攻击上限，nude:碎甲，dizzy：眩晕，blind:致盲，atkbeat:造成伤害并击退，assault:冲锋攻击，shifhp:转移生命,speed:游戏速度,reward:奖励,box:格子
	public static enum Type {
		f_damage, f_healing, f_shifhp, f_box, p_damage, p_healing, p_lucky, p_atkbeat, p_assault,p_reward, p_speed, pbuff_hp, pbuff_healing, pbuff_atk, pbuff_def, pdot_damage, prob_dizzy, prob_nude, prob_blind
	};

	public static enum ObjectType {
		single, multi, all
	}; // 技能作用对象类型，单体，群体，全图

	public static enum DrivingPassive {driving,passive};	//指示主动技能还是被动技能
	
	public static enum SpecialEffect {shock,dark,none};	//特殊的大型技能效果 shock:震动 dark:全屏黑暗 none:无
	
	public int id = 0; // 技能索引
	public String name = ""; // 技能名称唯一索引
	public QUALITY quality = QUALITY.green; // 技能品质，默认为绿色
	public Type type = Type.f_damage; // 设置默认类型为攻击类型
	public ObjectType otype = ObjectType.single; // 指定技能为单体还是群体
	public HashMap<Integer,SpecialEffect> specialEffect = new HashMap<Integer,SpecialEffect>();		//指定当前技能哪些帧有特殊效果,默认没有
	public DrivingPassive drivingpassive = DrivingPassive.driving;
	private float val = 0; // 技能效果值ֵ
	private float uval = 0; // 下级升级递增值
	public int lev = 1; // 技能级别，默认为1级，满级6级
	private CLASSES[] classes = null; // 技能适用的职业范围
	public String info = ""; // 技能描述
	private Array<Vector2> range = new Array<Vector2>(); // 技能释放范围
	public TextureRegion icon = null; // 技能图标

	public Animation ani_self = null; // 技能释放者动画效果
	public Vector2 offset_ani_self = null; // 技能释放者动画偏移量
	public Animation ani_object = null; // 技能承受者动画效果
	public Vector2 offset_ani_object = null; // 技能承受者动画偏移量
	// public Animation ani_conself = null; //持续技能释放者动画效果
	// public Animation ani_conobject = null; //持续技能承受者动画效果
	public Animation ani_continue = null; // 持续技能承受者动画效果，暂时只有承受者有动画效果。
	public Vector2 offset_ani_continue = null; // 持续技能承受者动画偏移量

	public boolean enable = false; // 该技能是否开启使用
	public SkillIcon skillIcon;//技能图标相关
	public int skill_index=-1;//-1未使用，0 第一个携带的技能，1第2个携带的技能
	
	
	/**
	 * 技能初始化构造函数，
	 * 
	 * @param n   技能名字
	 * @param q   技能品质
	 * @param t   技能类型
	 * @param v   技能值
	 * @param uv  技能值下级升级递增值
	 * @param ac  技能适用职业
	 * @param i   技能描述信息
	 * @param tr  技能图标
	 * @param as  技能自身动画
	 * @param ao  技能目标动画
	 * @param anic技能持续效果动画
	 * @param r   技能攻击范围
	 */
	public Skill(int pi, String n, QUALITY q, Type t, float v, float uv,
			CLASSES[] ac, String i, TextureRegion tr, Animation as,
			Animation ao, Animation anic, Vector2[] r) {
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

	/**
	 * 简化后的构造函数，内部根据技能索引自动带入动画图标等资源
	 * 
	 * @param pi
	 *            技能id
	 * @param n
	 *            技能名称
	 * @param q
	 *            技能品质
	 * @param t
	 *            技能类型
	 * @param v
	 *            技能值
	 * @param uv
	 *            技能升级值
	 * @param ac
	 *            技能所属职业
	 * @param i
	 *            技能信息
	 * @param r
	 *            技能作用范围
	 */
	public Skill(int pi, String n, QUALITY q, Type t, float v, float uv,
			CLASSES[] ac, String i, Vector2[] r) {
		id = pi;
		name = n;
		quality = q;
		type = t;
		val = v;
		uval = uv;
		classes = ac;
		info = i;
		icon = GTC.getInstance().getSkillIcon(pi);
		ani_self = GAC.getInstance().getSkillOwnerEffect(pi);
		ani_object = GAC.getInstance().getSkillObjectEffect(pi);
		ani_continue = GAC.getInstance().getContinuedEffect(pi);
		range.addAll(r);
	}

	public Array<Vector2> getRange() {
		return range;
	}

	/**
	 * 水平翻转技能作用范围
	 */
	public Array<Vector2> flipRange() {
		Array<Vector2> retv = new Array<Vector2>();
		for(int i=0;i<range.size;i++)
			retv.add(new Vector2(-range.get(i).x,range.get(i).y));
		return retv;
	}
	/**
	 * 判断接下来移动还是不移动 
	 * @param owner		技能的拥有者
	 * @param objects	作用对象
	 * @return			返回是否移动
	 */
	public boolean isMove(Role owner ,Array<Role> objects){
		if(objects.size==0)
			return true;
		if (type == Skill.Type.f_damage || type == Skill.Type.f_shifhp
				|| type == Skill.Type.f_box 
				|| type == Skill.Type.p_atkbeat
				|| type == Skill.Type.p_assault
				|| type == Skill.Type.p_damage
				|| type == Skill.Type.pdot_damage
				|| type == Skill.Type.prob_blind
				|| type == Skill.Type.prob_dizzy
				|| type == Skill.Type.prob_nude) {
			return false;
		}else if (type == Skill.Type.f_healing || type == Skill.Type.p_healing
				|| type == Skill.Type.pbuff_atk || type == Skill.Type.pbuff_def
				|| type == Skill.Type.pbuff_healing
				|| type == Skill.Type.pbuff_hp) {
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 技能对目标的逻辑处理
	 * 
	 * @param owen    技能释放者
	 * @param object  技能目标
	 * @return 返回一个布尔值，表示owner是否还继续向前移动
	 */
	public void skillLogic(Role owner, Array<Role> objects) {
		//如果攻击范围内没有敌人，返回true前进
		if(objects.size==0)
			return;
		if (type == Skill.Type.f_damage || type == Skill.Type.f_shifhp
				|| type == Skill.Type.f_box 
				|| type == Skill.Type.p_atkbeat
				|| type == Skill.Type.p_assault
				|| type == Skill.Type.p_damage
				|| type == Skill.Type.pdot_damage
				|| type == Skill.Type.prob_blind
				|| type == Skill.Type.prob_dizzy
				|| type == Skill.Type.prob_nude) {
			// 固定伤害处理
			if (type == Skill.Type.f_damage) {
				for (Role object : objects) {
					object.setCurrentHp((int) (object.getCurrentHp()
							- U.realDamage((int) (owner.getAttack() + getVal()),
									object.getDefend()) >= 0 ? object.getCurrentHp()
							- U.realDamage((int) (owner.getAttack() + getVal()),
									object.getDefend()) : 0));
					Commander.getInstance().hitedCommand(object);
				}
				// 扩展格子伤害
			} else if (type == Skill.Type.f_box) {
				for (Role object : objects) {
					object.setCurrentHp((int) (object.getCurrentHp()
							- U.realDamage((int) (owner.getAttack()),
									object.getDefend()) >= 0 ? object.getCurrentHp()
							- U.realDamage((int) (owner.getAttack()),
									object.getDefend()) : 0));
					Commander.getInstance().hitedCommand(object);
				}
				// 转移伤害至生命
			} else if (type == Skill.Type.f_shifhp) {
				for (Role object : objects) {
					object.setCurrentHp((int) (object.getCurrentHp()
							- U.realDamage((int) (owner.getAttack() + getVal()),
									object.getDefend()) >= 0 ? object.getCurrentHp()
							- U.realDamage((int) (owner.getAttack() + getVal()),
									object.getDefend()) : 0)); // 伤害敌人
					owner.setCurrentHp((int) (owner.getCurrentHp() + U
							.realDamage((int) (owner.getAttack() + getVal()),
									object.getDefend()) / 2) >= owner
							.getMaxHp() // 转移生命至自己
					? owner.getMaxHp()
							: (int) (owner.getCurrentHp() + U.realDamage(
									(int) (owner.getAttack() + getVal()),
									object.getDefend()) / 2));
					Commander.getInstance().hitedCommand(object);
				}
				// 击退伤害
			} else if (type == Skill.Type.p_atkbeat) {
				for (Role object : objects) {
					object.setCurrentHp((int) (object.getCurrentHp()
							- U.realDamage((int) (owner.getAttack() * getVal()),
									object.getDefend()) >= 0 ? object.getCurrentHp()
							- U.realDamage((int) (owner.getAttack() * getVal()),
									object.getDefend()) : 0)); // 伤害敌人
					Commander.getInstance().heatCommand(object); // 击退
				}
				//冲锋伤害,单体伤害
			}else if(type==Skill.Type.p_assault){
				for(Role object:objects){
					object.setCurrentHp((int)(object.getCurrentHp()-U.realDamage((int)(owner.getAttack()*getVal()), object.getDefend()) >= 0 ? object.getCurrentHp()
					- U.realDamage((int) (owner.getAttack() * getVal()),
							object.getDefend()) : 0)); // 伤害敌人
					//进行冲锋动作
//					Commander.getInstance().assaultCommand(owner, owner.cskill.getRange());
				}
				// 百分比伤害
			} else if (type == Skill.Type.p_damage) {
				for (Role object : objects) {
					object.setCurrentHp((int) (object.getCurrentHp()
							- U.realDamage((int) (owner.getAttack() * getVal()),
									object.getDefend()) >= 0 ? object.getCurrentHp()
							- U.realDamage((int) (owner.getAttack() * getVal()),
									object.getDefend()) : 0)); // 伤害敌人
					Commander.getInstance().hitedCommand(object);
				}
				// 持续伤害，默认为3回合
			} else if (type == Skill.Type.pdot_damage) {
				for (Role object : objects) {
					object.csstate.add(new ContinuedSkillState(3, owner
							.getAttack() * getVal(), CSType.dot, ani_continue,
							offset_ani_continue));
				}

				// 一定机率致盲
			} else if (type == Skill.Type.prob_blind) {
				for (Role object : objects) {
					// 此处增加机率代码
					if (U.probability(getVal()))
						object.csstate
								.add(new ContinuedSkillState(2, 0,
										CSType.blind, ani_continue,
										offset_ani_continue));
				}
				// 一定机率眩晕
			} else if (type == Skill.Type.prob_dizzy) {
				for (Role object : objects) {
					object.setCurrentHp((int) (object.getCurrentHp()
							- U.realDamage((int) (owner.getAttack()),
									object.getDefend()) >= 0 ? object.getCurrentHp()
							- U.realDamage((int) (owner.getAttack()),
									object.getDefend()) : 0));
					// 此处增加机率代码
					if (U.probability(getVal())){
						object.csstate.add(new ContinuedSkillState(2, 0,
										CSType.dizzy, ani_continue,
										offset_ani_continue));
					}
					Commander.getInstance().hitedCommand(object);
				}
				// 破甲效果
			} else if (type == Skill.Type.prob_nude) {
				for (Role object : objects) {
					object.csstate.add(new ContinuedSkillState(3, getVal(),
							CSType.debuff_def, ani_continue,
							offset_ani_continue));
					Commander.getInstance().hitedCommand(object);
				}
			}

		} else if (type == Skill.Type.f_healing || type == Skill.Type.p_healing
				|| type == Skill.Type.pbuff_atk || type == Skill.Type.pbuff_def
				|| type == Skill.Type.pbuff_healing
				|| type == Skill.Type.pbuff_hp) {
			// 固定治疗
			if (type == Skill.Type.f_healing) {
				for (Role object : objects) {
					object.setCurrentHp((int) (object.getCurrentHp() + this.getVal() >= object
							.getMaxHp() ? object.getMaxHp() : object.getCurrentHp()
							+ getVal()));
				}
				// 百分比治疗
			} else if (type == Skill.Type.p_healing) {
				for (Role object : objects) {
					object.setCurrentHp((int) (object.getCurrentHp()
							+ object.getMaxHp() * getVal() >= object.getMaxHp() ? object
							.getMaxHp() : object.getCurrentHp() + object.getMaxHp()
							* getVal()));
				}
				// 百分比攻击力buff
			} else if (type == Skill.Type.pbuff_atk) {
				for (Role object : objects) {
					object.csstate.add(new ContinuedSkillState(2, object
							.getAttack() * getVal(), CSType.buff_atk, ani_continue,
							offset_ani_continue));
				}
				// 百分比防御力buff
			} else if (type == Skill.Type.pbuff_def) {
				for (Role object : objects) {
					object.csstate.add(new ContinuedSkillState(2, object
							.getDefend() * getVal(), CSType.buff_def, ani_continue,
							offset_ani_continue));
				}
				// 百分比持续性治疗
			} else if (type == Skill.Type.pbuff_healing) {
				for (Role object : objects) {
					object.setCurrentHp((int) (object.getCurrentHp()
							+ object.getCurrentHp() * getVal() >= object.getMaxHp() ? object.getCurrentHp()
							: object.getCurrentHp() + object.getCurrentHp() * getVal()));
				}
				// 百分比生命最大值
			} else if (type == Skill.Type.pbuff_hp) {
				for (Role object : objects) {
					object.csstate.add(new ContinuedSkillState(2, object
							.getMaxHp() * getVal(), CSType.buff_hp, ani_continue,
							offset_ani_continue));
				}
			}
		} 
	}
	/**
	 * 升级函数，如果级别大于6返回false
	 * @return 返回升级是否成功
	 */
	public boolean levUp(){
		if(lev<6){
			lev++;
			return true;
		}
		return false;
	}
	/**
	 * 设置级别数，最大为6
	 * @param l	级别参数
	 */
	public boolean setLev(int l){
		if(l<=6){
			lev = l;
			return true;
		}
		return false;
	}
	/**
	 * 返回当前技能的值，每次根据级别计算值
	 * @return	返回计算后的值
	 */
	public float getVal() {
		return val+(lev-1)*uval;
	}
	/**
	 * 将Skill对象转换为SkillData数据
	 * @return
	 */
	public SkillData toSkillData(){
		SkillData sd = new SkillData();
		sd.id = id;
		sd.lev = lev;
		sd.enable = enable;
		sd.skill_index=skill_index;
		return sd;
	}

}
