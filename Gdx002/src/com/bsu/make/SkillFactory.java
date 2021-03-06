package com.bsu.make;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.skilltree.Skill;
import com.bsu.obj.skilltree.Skill.ObjectType;
import com.bsu.obj.skilltree.Skill.Type;
import com.bsu.tools.GC.CLASSES;
import com.bsu.tools.GC.QUALITY;

/**
 * 技能工厂，用来初始化所有技能，并保存在hashmap中
 * 
 * @author fengchong
 * 
 */
public class SkillFactory {
	private static SkillFactory instance = null;

	public static SkillFactory getInstance() {
		if (instance == null)
			instance = new SkillFactory();
		return instance;
	}

	private HashMap<String, Skill> store = new HashMap<String, Skill>(); // 保存所有的技能对象
//	private Array<Skill> usedSkills = new Array<Skill>();					//收集已经实例化过的技能，用与技能加速
	/**
	 * 初始化所有技能，并保存到hashmap中，此处数据全部为配置数据，不能再改变
	 */
	private SkillFactory() {}

//	public Skill getSkillByName(String k){
//		return store.get(k);
//	}
	/**
	 * 通过索引获得技能
	 * @param i		技能索引
	 * @return		返回技能对象
	 */
	public Skill getSkillByIdx(int i) {
		Skill skl = null;
		switch(i){
		case 1:
			skl = new Skill(i,"重击",QUALITY.green,Type.prob_dizzy,0.2f,0.03f,new CLASSES[]{CLASSES.fighter,CLASSES.archer},"重击对手，令对手有一定机率眩晕",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0)});
			break;
		case 2:
			skl = new Skill(i,"直拳",QUALITY.green,Type.p_damage,1.5f,0.3f,new CLASSES[]{CLASSES.fighter,CLASSES.archer},"风驰电掣的直拳，令敌人猝防不及",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0)});
			break;
		case 3:
			skl = new Skill(i,"勾拳",QUALITY.green,Type.p_damage,1.2f,0.4f,new CLASSES[]{CLASSES.fighter,CLASSES.archer},"目标为下巴的勾拳，攻击力成长要比直拳好",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0)});
			break;
		case 4:
			skl = new Skill(i,"突刺",QUALITY.green,Type.f_box,2.0f,0.5f,new CLASSES[]{CLASSES.wizard,CLASSES.cleric,CLASSES.sorcerer},"攻击前方多个格子的敌人，对每个敌人都造成一定伤害",
					new Vector2[] {new Vector2(0,0)});
			skl.otype = ObjectType.multi;
			break;
		case 5:
			skl = new Skill(i,"横扫",QUALITY.green,Type.p_damage,0.8f,0.2f,new CLASSES[]{CLASSES.wizard,CLASSES.cleric,CLASSES.sorcerer},"横扫面前竖排敌人",
					new Vector2[] {new Vector2(1,0),new Vector2(1,1),new Vector2(1,-1)});
			skl.otype = ObjectType.multi;
			break;
		case 6:
			skl = new Skill(i,"马步",QUALITY.green,Type.p_healing,0.05f,0.02f,new CLASSES[]{CLASSES.all},"一回合内恢复自身少量生命",
					new Vector2[] {new Vector2(0,0)});
			break;
		case 7:
			skl = new Skill(i,"防御姿态",QUALITY.green,Type.pbuff_def,0.07f,0.03f,new CLASSES[]{CLASSES.all},"一回合内增加一定防御力",
					new Vector2[] {new Vector2(0,0)});
			break;
		case 8:
			skl = new Skill(i,"蓄力",QUALITY.green,Type.pbuff_atk,0.03f,0.02f,new CLASSES[]{CLASSES.all},"一回合内增加一定攻击力",
					new Vector2[] {new Vector2(0,0)});
			break;
		case 9:
			skl = new Skill(i,"速射",QUALITY.green,Type.p_damage,1.2f,0.4f,new CLASSES[]{CLASSES.archer,CLASSES.wizard,CLASSES.cleric,CLASSES.sorcerer},"增加远程职业的攻击力",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0)});
			break;
		case 10:
			skl = new Skill(i,"连射",QUALITY.green,Type.p_damage,1.5f,0.3f,new CLASSES[]{CLASSES.archer,CLASSES.wizard,CLASSES.cleric,CLASSES.sorcerer},"远程职业连续射击2次，但第二发威力较弱",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0)});
			break;
		case 31:
			skl = new Skill(i,"破甲",QUALITY.blue,Type.prob_nude,5f,2f,new CLASSES[]{CLASSES.fighter,CLASSES.archer},"以较强的攻击力击破敌人护甲",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0)});
			break;
		case 32:
			skl = new Skill(i,"蓄力一击",QUALITY.blue,Type.p_damage,1.5f,0.5f,new CLASSES[]{CLASSES.fighter,CLASSES.archer},"积累了一定力量，奋力一击，对敌人造成较大伤害",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0)});
			break;
		case 33:
			skl = new Skill(i,"火球术",QUALITY.blue,Type.f_damage,50f,25f,new CLASSES[]{CLASSES.wizard},"法师的基本法术，差不多所有元素法师第一课都要学习此技能",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0),new Vector2(3,0)});
			skl.offset_ani_object = new Vector2(-48,0);
			break;
		case 34:
			skl = new Skill(i,"疗伤",QUALITY.blue,Type.f_healing,50f,25f,new CLASSES[]{CLASSES.cleric},"牧师最基本的治疗术，可以为其他人恢复HP",
					new Vector2[] {new Vector2(1,0),new Vector2(1,1),new Vector2(1,-1)});
			break;
		case 35:
			skl = new Skill(i,"包扎",QUALITY.blue,Type.f_healing,5f,2f,new CLASSES[]{CLASSES.all},"使用绷带包扎自己，回复少量生命",
					new Vector2[] {new Vector2(0,0)});
			break;
		case 36:
			skl = new Skill(i,"吸星",QUALITY.blue,Type.f_shifhp,5f,2f,new CLASSES[]{CLASSES.sorcerer},"伤害敌人，并回复伤害一半的HP为自己",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0),new Vector2(3,0),new Vector2(4,0),new Vector2(4,1),new Vector2(4,-1)});
			skl.offset_ani_object = new Vector2(-32,0);
			break;
		case 37:
			skl = new Skill(i,"乱射",QUALITY.blue,Type.f_damage,5f,2f,new CLASSES[]{CLASSES.archer,CLASSES.wizard,CLASSES.cleric,CLASSES.sorcerer},"对前方3*3格子的敌人进行乱射攻击",
					new Vector2[] {new Vector2(1,0),new Vector2(1,1),new Vector2(1,-1),
										new Vector2(2,0),new Vector2(2,1),new Vector2(2,-1),
										new Vector2(3,0),new Vector2(3,1),new Vector2(3,-1)});
			skl.offset_ani_self = new Vector2(22,10);
			skl.otype = ObjectType.multi;
			break;
		case 38:
			skl = new Skill(i,"爆头",QUALITY.blue,Type.p_damage,1.8f,0.5f,new CLASSES[]{CLASSES.archer,CLASSES.wizard},"正中敌人头部，造成较大伤害",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0),new Vector2(3,0)});
			skl.offset_ani_self = new Vector2(22,10);
			skl.offset_ani_object = new Vector2(0,10);
			break;
		case 39:
			skl = new Skill(i,"血池",QUALITY.blue,Type.pdot_damage,1.5f,0.3f,new CLASSES[]{CLASSES.sorcerer},"在前方3*3格子敌人脚下召唤出血池，对他们造成持续伤害",
					new Vector2[] {new Vector2(1,0),new Vector2(1,1),new Vector2(1,-1),
						new Vector2(2,0),new Vector2(2,1),new Vector2(2,-1),
						new Vector2(3,0),new Vector2(3,1),new Vector2(3,-1)});
			skl.offset_ani_object = new Vector2(10,0);
			break;
		case 40:
			skl = new Skill(i,"蛮力",QUALITY.blue,Type.pbuff_atk,0.05f,0.05f,new CLASSES[]{CLASSES.all},"提升自己的攻击力",
					new Vector2[] {new Vector2(0,0)});
			skl.drivingpassive = Skill.DrivingPassive.passive;
			break;
		case 41:
			skl = new Skill(i,"生命天赋",QUALITY.blue,Type.pbuff_hp,0.05f,0.02f,new CLASSES[]{CLASSES.fighter,CLASSES.cleric},"只有战士与牧师对生命的意义理解到了一定高度，可额外提高自己的生命上限",
					new Vector2[] {new Vector2(0,0)});
			skl.drivingpassive = Skill.DrivingPassive.passive;
			break;
		case 42:
			skl = new Skill(i,"致盲",QUALITY.blue,Type.prob_blind,0.2f,0.05f,new CLASSES[]{CLASSES.sorcerer,CLASSES.cleric},"对敌人产生致盲效果，导致敌人下回合不能进行攻击",
					new Vector2[]{new Vector2(1,0),new Vector2(2,0),new Vector2(3,0)});
			skl.offset_ani_self = new Vector2(16,5);
			break;
		case 61:
			skl = new Skill(i,"碎甲",QUALITY.purple,Type.prob_nude,10f,5f,new CLASSES[]{CLASSES.fighter,CLASSES.archer},"对敌人的盔甲造成毁灭性打击，大幅降低敌人的防御力",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0)});
			break;
		case 62:
			skl = new Skill(i,"旋风斩",QUALITY.purple,Type.f_damage,50f,40f,new CLASSES[]{CLASSES.fighter},"挥舞巨大的武器轮向敌人，对前方2*3格子的敌人造成伤害",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0),
									new Vector2(1,1),new Vector2(2,1),
									new Vector2(1,-1),new Vector2(2,-1)});
			skl.otype = ObjectType.multi;
			break;
		case 63:
			skl = new Skill(i,"爆震",QUALITY.purple,Type.f_damage,50f,30f,new CLASSES[]{CLASSES.fighter},"对自己周围一圈的敌人造成伤害,冲到敌阵中央很好用",
					new Vector2[] {new Vector2(1,0),new Vector2(1,1),new Vector2(1,-1),new Vector2(0,1),new Vector2(0,-1)
									,new Vector2(-1,1),new Vector2(-1,0),new Vector2(-1,-1)});
			skl.offset_ani_self = new Vector2(-32,-32);
			skl.otype = ObjectType.multi;
			break;
		case 64:
			skl = new Skill(i,"击退",QUALITY.purple,Type.p_atkbeat,1.2f,0.5f,new CLASSES[]{CLASSES.fighter,CLASSES.archer},"对敌人造成一定伤害，并击退敌人一格",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0),new Vector2(3,0)});
			skl.offset_ani_self = new Vector2(32,0);
			break;
		case 65:
			skl = new Skill(i,"火球三连",QUALITY.purple,Type.p_damage,2f,0.3f,new CLASSES[]{CLASSES.wizard},"单体攻击3连发火球，威力较大",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0),new Vector2(3,0)});
			skl.offset_ani_object = new Vector2(-48,0);
			break;
		case 66:
			skl = new Skill(i,"冰锥推进",QUALITY.purple,Type.p_damage,1.5f,0.3f,new CLASSES[]{CLASSES.wizard},"对前方横向3个格子所有敌人进行冰锥攻击",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0),new Vector2(3,0)});
			skl.offset_ani_self = new Vector2(35,0);
			skl.otype = ObjectType.multi;
			break;
		case 67:
			skl = new Skill(i,"生命转移",QUALITY.purple,Type.f_shifhp,50f,30f,new CLASSES[]{CLASSES.cleric,CLASSES.sorcerer},"将前方3*3范围内的敌人的生命转移到己方",
					new Vector2[] {new Vector2(1,0),new Vector2(1,1),new Vector2(1,-1),
						new Vector2(2,0),new Vector2(2,1),new Vector2(2,-1),
						new Vector2(3,0),new Vector2(3,1),new Vector2(3,-1)});
			break;
		case 68:
			skl = new Skill(i,"持续恢复",QUALITY.purple,Type.pbuff_healing,20f,15f,new CLASSES[]{CLASSES.cleric},"恢复2*3格子己方英雄HP，持续3回合",
					new Vector2[] {new Vector2(1,0),new Vector2(1,1),new Vector2(1,-1),
						new Vector2(2,0),new Vector2(2,1),new Vector2(2,-1)
						});
			skl.otype = ObjectType.multi;
			break;
		case 69:
			skl = new Skill(i,"强健身躯",QUALITY.purple,Type.pbuff_hp,0.1f,0.03f,new CLASSES[]{CLASSES.all},"强效的增加生命上限技能",
					new Vector2[] {new Vector2(0,0)});
			skl.drivingpassive = Skill.DrivingPassive.passive;
			break;
		case 70:
			skl = new Skill(i,"黑暗降临",QUALITY.purple,Type.prob_blind,1f,0.05f,new CLASSES[]{CLASSES.sorcerer},"对3*3格子的敌人一定几率致盲，导致下回合不能攻击",
					new Vector2[] {new Vector2(1,0),new Vector2(1,1),new Vector2(1,-1),
						new Vector2(2,0),new Vector2(2,1),new Vector2(2,-1),
						new Vector2(3,0),new Vector2(3,1),new Vector2(3,-1)});
			skl.offset_ani_object = new Vector2(5,10);
			skl.offset_ani_self = new Vector2(16,5);
			skl.offset_ani_continue = new Vector2(5,10);
			skl.otype = ObjectType.multi;
			break;
		case 71:
			skl = new Skill(i,"穿透",QUALITY.purple,Type.f_damage,60f,20f,new CLASSES[]{CLASSES.archer},"对前方3个格子的敌人进行穿透攻击",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0),new Vector2(3,0)});
			skl.otype = ObjectType.multi;
			break;
		case 72:
			skl = new Skill(i,"好运降临",QUALITY.purple,Type.p_reward,0.03f,0.02f,new CLASSES[]{CLASSES.all},"通关后增加一定的奖励",
					new Vector2[] {new Vector2(0,0)});
			skl.drivingpassive = Skill.DrivingPassive.passive;
			break;
		case 73:
			skl = new Skill(i,"斜刺",QUALITY.purple,Type.f_damage,50f,30f,new CLASSES[]{CLASSES.fighter,CLASSES.archer},"对斜下方所有格子敌人进行攻击",
					new Vector2[] {new Vector2(1,-1),new Vector2(2,-2),new Vector2(3,-3),new Vector2(4,-4),new Vector2(5,-5)});
			skl.offset_ani_self = new Vector2(32,-96);
			skl.otype = ObjectType.multi;
			break;
		case 74:
			skl = new Skill(i,"魔法震退",QUALITY.purple,Type.p_atkbeat,1.3f,0.4f,new CLASSES[]{CLASSES.fighter,CLASSES.archer},"对前方3个格子的敌人进行震退攻击",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0),new Vector2(3,0)});
			skl.offset_ani_object = new Vector2(-16,0);
			break;
		case 95:
			skl = new Skill(i,"空中打击",QUALITY.orange,Type.p_damage,2f,0.5f,new CLASSES[]{CLASSES.fighter,CLASSES.archer},"对4*4格子敌人进行空中打击",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0),new Vector2(3,0),new Vector2(4,0),
									new Vector2(1,2),new Vector2(2,2),new Vector2(3,2),new Vector2(4,2),
									new Vector2(1,1),new Vector2(2,1),new Vector2(3,1),new Vector2(4,1),
									new Vector2(1,-1),new Vector2(2,-1),new Vector2(3,-1),new Vector2(4,-1)
									});
			skl.offset_ani_self = new Vector2(0,-32);
			skl.offset_ani_object = new Vector2(-48,0);
			skl.otype = ObjectType.multi;
			break;
		case 96:
			skl = new Skill(i,"群星陨落",QUALITY.orange,Type.p_damage,1.8f,0.4f,new CLASSES[]{CLASSES.wizard},"对全体敌人进行落星攻击",
					new Vector2[]{new Vector2(0,0)});
			skl.offset_ani_object  = new Vector2(-42,-16);
			skl.otype = ObjectType.all;
			skl.specialEffect.put(4, Skill.SpecialEffect.shock);
			skl.specialEffect.put(8, Skill.SpecialEffect.shock);
			skl.specialEffect.put(12, Skill.SpecialEffect.shock);
			break;
		case 97:
			skl = new Skill(i,"圣光术",QUALITY.orange,Type.p_healing,0.15f,0.04f,new CLASSES[]{CLASSES.cleric},"可以对己方全体队友大量回血",
					new Vector2[]{new Vector2(0,0)});
			skl.otype = ObjectType.all;
			break;
		case 98:
			skl = new Skill(i,"高级强身术",QUALITY.orange,Type.pbuff_hp,0.15f,0.04f,new CLASSES[]{CLASSES.all},"超大幅度提高自身的HP上限",
					new Vector2[] {new Vector2(0,0)});
			skl.drivingpassive = Skill.DrivingPassive.passive;
			break;
		case 99:
			skl = new Skill(i,"日蚀",QUALITY.orange,Type.prob_blind,0.25f,0.03f,new CLASSES[]{CLASSES.sorcerer},"较高机率对所有敌人造成致盲，导致下回合不能攻击",
					new Vector2[] {});
			skl.otype = ObjectType.multi;
			break;
		case 100:
			skl = new Skill(i,"游戏加速",QUALITY.orange,Type.p_speed,2f,0.15f,new CLASSES[]{CLASSES.all},"提高游戏速度",
					new Vector2[] {});
			skl.drivingpassive = Skill.DrivingPassive.passive;  
			break;
		case 101:
			skl = new Skill(i,"贯穿",QUALITY.orange,Type.p_damage,2f,0.05f,new CLASSES[]{CLASSES.archer},"攻击前面所有敌人，贯穿很长一段距离",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0),new Vector2(3,0),new Vector2(4,0),new Vector2(5,0),new Vector2(6,0),new Vector2(7,0),new Vector2(8,0)});
			skl.otype = ObjectType.multi;
			break;
		case 102:
			skl = new Skill(i,"法术折磨",QUALITY.orange,Type.prob_nude,0.2f,0.1f,new CLASSES[]{CLASSES.wizard,CLASSES.cleric,CLASSES.sorcerer},"用法术折磨敌人，使其防御大幅度降低",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0),new Vector2(3,0),new Vector2(4,0),new Vector2(5,0)});
			skl.offset_ani_object = new Vector2(-20,-16);
			skl.offset_ani_continue = new Vector2(-20,-16);
			break;
		case 103:
			skl = new Skill(i,"冲锋",QUALITY.orange,Type.p_assault,2.5f,0.8f,new CLASSES[]{CLASSES.fighter},"向前冲刺很长一段距离，直至撞到敌人或地图边缘，撞到敌人后对敌人造成大量伤害",
					new Vector2[] {new Vector2(1,0),new Vector2(2,0),new Vector2(3,0),new Vector2(4,0),new Vector2(5,0),new Vector2(6,0)});
			break;
		default:
			break;
		}
			
		return skl;
		//return store.get(k);
	}

//	public Array<Skill> getUsedSkills() {
//		return usedSkills;
//	}
}
