package com.bsu.make;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.skilltree.Skill;
import com.bsu.obj.skilltree.Skill.Type;
import com.bsu.tools.Configure.CLASSES;
import com.bsu.tools.Configure.QUALITY;
import com.bsu.tools.GameAnimationClass;

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

	/**
	 * 初始化所有技能，并保存到hashmap中，此处数据全部为配置数据，不能再改变
	 */
	private SkillFactory() {
		// 普通攻击1,测试用
		store.put("atk", new Skill(999,"攻击", QUALITY.green,Skill.Type.f_damage, 10,1, new CLASSES[]{CLASSES.fighter},1,"普通攻击，没什么稀奇的。",GameAnimationClass
				.getInstance().getEffect0(), GameAnimationClass.getInstance()
				.getEffect1(),  new Vector2[] {
				new Vector2(1, 0), new Vector2(2, 0) }));
		// 普通攻击2,测试用
		store.put("atk2", new Skill(998,"攻击", QUALITY.green,Skill.Type.f_damage, 20,1,new CLASSES[]{CLASSES.fighter},1,"普通攻击，没什么稀奇的。",
				GameAnimationClass.getInstance().getEffect1(),
				GameAnimationClass.getInstance().getEffect2(), 
				new Vector2[] { new Vector2(1, 0), new Vector2(2, 0) }));
		// 普通攻击3,测试用
		store.put("atk3", new Skill(997,"攻击", QUALITY.green,Skill.Type.f_damage, 30,1,new CLASSES[]{CLASSES.fighter},1,"普通攻击，没什么稀奇的。",
				GameAnimationClass.getInstance().getEffect2(),
				GameAnimationClass.getInstance().getEffect3(), 
				new Vector2[] { new Vector2(1, 0), new Vector2(2, 0) }));
		//增加所有技能对象为空，在取该技能时再建立对应的技能对象
		//store.put("", value)
	}

	public Skill getSkillByName(String k){
		return store.get(k);
	}
	/**
	 * 通过索引获得技能
	 * @param i		技能索引
	 * @return		返回技能对象
	 */
	public Skill getSkillByIdx(int i) {
		Skill skl = null;
		switch(i){
		case 1:
//			store.put("atk3", new Skill(999,"攻击", QUALITY.green,Skill.Type.f_damage, 10,1, new CLASSES[]{CLASSES.fighter},1,"普通攻击，没什么稀奇的。",
//									GameAnimationClass.getInstance().getEffect0(), GameAnimationClass.getInstance().getEffect1(), 
//									new Vector2[] {new Vector2(1, 0), new Vector2(2, 0) }));
			skl = new Skill(i,"重击",QUALITY.green,Type.prob_dizzy,0.2f,0.03f,new CLASSES[]{CLASSES.fighter},8,"重击对手，令对手有一定机率眩晕",
					GameAnimationClass.getInstance().getEffect2(),GameAnimationClass.getInstance().getEffect3(),
					new Vector2[] {new Vector2(1,0)});
			break;
		case 2:
			skl = new Skill(i,"直拳",QUALITY.green,Type.mul_damage,1.5f,0.3f,new CLASSES[]{CLASSES.fighter},1,"风驰电掣的直拳，令敌人猝防不及",
					GameAnimationClass.getInstance().getEffect2(),GameAnimationClass.getInstance().getEffect3(),
					new Vector2[] {new Vector2(1,0)});
			break;
		case 3:
			skl = new Skill(i,"勾拳",QUALITY.green,Type.mul_damage,1.2f,0.4f,new CLASSES[]{CLASSES.fighter},4,"目标为下巴的勾拳，攻击力成长要比直拳好",
					GameAnimationClass.getInstance().getEffect2(),GameAnimationClass.getInstance().getEffect3(),
					new Vector2[] {new Vector2(1,0)});
			break;
		case 4:
			skl = new Skill(i,"突刺",QUALITY.green,Type.box_count,2.0f,0.5f,new CLASSES[]{CLASSES.wizard,CLASSES.cleric,CLASSES.sorcerer},9,"攻击前方多个格子的敌人，对每个敌人都造成一定伤害",
					GameAnimationClass.getInstance().getEffect2(),GameAnimationClass.getInstance().getEffect3(),
					new Vector2[] {new Vector2(1,0),new Vector2(2,0)});
			break;
		case 5:
			skl = new Skill(i,"横扫",QUALITY.green,Type.box_count,2.0f,0.5f,new CLASSES[]{CLASSES.wizard,CLASSES.cleric,CLASSES.sorcerer},6,"横扫面前竖排敌人",
					GameAnimationClass.getInstance().getEffect2(),GameAnimationClass.getInstance().getEffect3(),
					new Vector2[] {new Vector2(1,0),new Vector2(1,1)});
			break;
		case 6:
			skl = new Skill(i,"马步",QUALITY.green,Type.pbuff_healing,0.05f,0.01f,new CLASSES[]{CLASSES.all},4,"一回合内恢复自身少量生命",
					GameAnimationClass.getInstance().getEffect2(),GameAnimationClass.getInstance().getEffect3(),
					new Vector2[] {new Vector2(0,0)});
			break;
		case 7:
			skl = new Skill(i,"弓步",QUALITY.green,Type.pbuff_def,0.07f,0.03f,new CLASSES[]{CLASSES.all},4,"一回合内增加一定防御力",
					GameAnimationClass.getInstance().getEffect2(),GameAnimationClass.getInstance().getEffect3(),
					new Vector2[] {new Vector2(0,0)});
			break;
		case 8:
			skl = new Skill(i,"蓄力",QUALITY.green,Type.pbuff_atk,0.05f,0.02f,new CLASSES[]{CLASSES.all},7,"一回合内增加一定攻击力",
					GameAnimationClass.getInstance().getEffect2(),GameAnimationClass.getInstance().getEffect3(),
					new Vector2[] {new Vector2(0,0)});
			break;
		case 9:
			skl = new Skill(i,"速射",QUALITY.green,Type.pbuff_atk,1.2f,0.4f,new CLASSES[]{CLASSES.archer,CLASSES.wizard,CLASSES.cleric,CLASSES.sorcerer},4,"增加远程职业的攻击力",
					GameAnimationClass.getInstance().getEffect2(),GameAnimationClass.getInstance().getEffect3(),
					new Vector2[] {new Vector2(0,0)});
			break;
		case 10:
			skl = new Skill(i,"连射",QUALITY.green,Type.pbuff_atk,1.5f,0.3f,new CLASSES[]{CLASSES.fighter},7,"远程职业连续射击2次，但第二发威力较弱",
					GameAnimationClass.getInstance().getEffect2(),GameAnimationClass.getInstance().getEffect3(),
					new Vector2[] {new Vector2(0,0)});
			break;
		case 11:
			break;
		case 12:
			break;
		case 13:
			break;
		case 14:
			break;
		case 15:
			break;
		case 16:
			break;
		case 17:
			break;
		case 18:
			break;

		default:
			break;
		}
			
		return skl;
		//return store.get(k);
	}
}
