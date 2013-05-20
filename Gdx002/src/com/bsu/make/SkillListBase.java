package com.bsu.make;

import com.badlogic.gdx.utils.Array;
import com.bsu.make.CardFactory.SUBTYPE;
import com.bsu.obj.skilltree.Skill;
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
		skillArrayEnemy.add("atk");//
	}

	private Array<String> skillArrayFC = new Array<String>();
	private Array<String> skillArrayZYC = new Array<String>();
	private Array<String> skillArrayEnemy = new Array<String>();

	public Array<Skill> getValue(SUBTYPE p, QUALITY q) {
		Array<Skill> rv = new Array<Skill>();
		Array<String> skillV = null;
		int stopNum = 0;
		if (p == SUBTYPE.fc) {
			skillV = skillArrayFC;
		}
		if (p == SUBTYPE.zyc) {
			skillV = skillArrayZYC;
		}
		if (p == SUBTYPE.enemy) {//NPC 目前只有一个技能
			skillV = skillArrayEnemy;
			stopNum=1;
		}
		if (p != SUBTYPE.enemy) {
			if (q == QUALITY.white) {
				stopNum = 3;
			}
			if (q == QUALITY.green) {
				stopNum = 5;
			}
			if (q == QUALITY.blue) {
				stopNum = 9;
			}
			if (q == QUALITY.purple) {
				stopNum = 11;
			}
			if (q == QUALITY.orange) {
				stopNum = skillV.size;
			}
		}
		for (int i = 0; i < stopNum; i++) {
			rv.add(SkillFactory.getInstance().getSkillByName(skillV.get(i)));
		}
		return rv;
	}

}
