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

		skillArrayFC.add(SkillFactory.getInstance().getSkillByName("atk"));// 橙色(perfect)
		skillArrayFC.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillArrayFC.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillArrayFC.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillArrayFC.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillArrayFC.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillArrayFC.add(SkillFactory.getInstance().getSkillByName("atk"));
		skillArrayFC.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillArrayFC.add(SkillFactory.getInstance().getSkillByName("atk"));//

		skillArrayZYC.add(SkillFactory.getInstance().getSkillByName("atk"));// 橙色(perfect)
		skillArrayZYC.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillArrayZYC.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillArrayZYC.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillArrayZYC.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillArrayZYC.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillArrayZYC.add(SkillFactory.getInstance().getSkillByName("atk"));
		skillArrayZYC.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillArrayZYC.add(SkillFactory.getInstance().getSkillByName("atk"));//

		skillArrayEnemy.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillArrayEnemy.add(SkillFactory.getInstance().getSkillByName("atk"));//
	}

	private Array<Skill> skillArrayFC = new Array<Skill>();
	private Array<Skill> skillArrayZYC = new Array<Skill>();
	private Array<Skill> skillArrayEnemy = new Array<Skill>();


	public Array<Skill> getValue(SUBTYPE p, QUALITY q) {
		Array<Skill> rv = null;
		if (p == SUBTYPE.fc) {
			rv = skillArrayFC;
		}
		if (p == SUBTYPE.zyc) {
			rv = skillArrayZYC;
		}
		if (p == SUBTYPE.enemy) {
			rv = skillArrayEnemy;
		}
		return rv;
	}

}
