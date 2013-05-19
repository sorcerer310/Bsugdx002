package com.bsu.make;

import com.badlogic.gdx.utils.Array;
import com.bsu.make.CardFactory.SUBTYPE;
import com.bsu.obj.Skill;
import com.bsu.tools.Configure.QUALITY;

/**
 * 技能树
 * 
 * @author zhangyongchen
 * 
 */
public class SkillListBase {
	private static SkillListBase instance = null;

	public static SkillListBase getInstance() {
		if (instance == null)
			instance = new SkillListBase();
		return instance;
	}

	public SkillListBase() {
		// TODO Auto-generated constructor stub

		skillArrayFC.add("atk");// 橙色(perfect)
		skillArrayFC.add("atk");//
		skillArrayFC.add("atk");//
		skillArrayFC.add("atk");//
		skillArrayFC.add("atk");//
		skillArrayFC.add("atk");//
		skillArrayFC.add("atk");//
		skillArrayFC.add("atk");//
		skillArrayFC.add("atk");//
		
		skillArrayZYC.add("atk");// 橙色(perfect)
		skillArrayZYC.add("atk");// 橙色(perfect)
		skillArrayZYC.add("atk");// 橙色(perfect)
		skillArrayZYC.add("atk");// 橙色(perfect)
		skillArrayZYC.add("atk");// 橙色(perfect)
		skillArrayZYC.add("atk");// 橙色(perfect)
		skillArrayZYC.add("atk");// 橙色(perfect)
		skillArrayZYC.add("atk");// 橙色(perfect)
		skillArrayZYC.add("atk");// 橙色(perfect)

		skillArrayEnemy.add("atk");//
		skillArrayEnemy.add("atk");//
	}

	private Array<String> skillArrayFC = new Array<String>();
	private Array<String> skillArrayZYC = new Array<String>();
	private Array<String> skillArrayEnemy = new Array<String>();


	public Array<Skill> getValue(SUBTYPE p, QUALITY q) {
		Array<Skill> rv = new Array<Skill>();
		Array<String> skillV=null;
		if (p == SUBTYPE.fc) {
			skillV = skillArrayFC;
		}
		if (p == SUBTYPE.zyc) {
			skillV = skillArrayZYC;
		}
		if (p == SUBTYPE.enemy) {
			skillV = skillArrayEnemy;
		}
		for(String s:skillV){
			rv.add(SkillFactory.getInstance().getSkillByName(s));
		}
		return rv;
	}

}
