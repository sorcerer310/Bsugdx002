package com.bsu.make;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.bsu.make.AttackWeaponFactory.ATTACK;
import com.bsu.make.CardFactory.SUBTYPE;
import com.bsu.make.DefendWeaponFactory.DEFEND;
import com.bsu.obj.Skill;
import com.bsu.tools.AttackWeaponBase;
import com.bsu.tools.DefendWeaponBase;
import com.bsu.tools.RoleValue;
import com.bsu.tools.Configure.QUALITY;

/**
 * 技能树
 * 
 * @author zhangyongchen
 * 
 */
public class SkillListBase {

	public SkillListBase() {
		// TODO Auto-generated constructor stub
	}

	private static Array<Skill> skillFCnormal = new Array<Skill>();
	private static Array<Skill> skillFCgood = new Array<Skill>();
	private static Array<Skill> skillFCbest = new Array<Skill>();
	private static Array<Skill> skillFCperfect = new Array<Skill>();
	private static Array<Array> skillArrayFC = new Array<Array>();
	private static Array<RoleValue> skillArrayZYC = new Array<RoleValue>();
	private static Array skillArray = new Array();

	static TextureRegion tr;
	static SkillListBase sl;
	static AttackWeaponBase aw;
	static DefendWeaponBase dw;
	static {
		skillFCnormal.add(SkillFactory.getInstance().getSkillByName("atk"));// 绿色(normal)
		skillFCnormal.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillFCnormal.add(SkillFactory.getInstance().getSkillByName("atk"));//

		skillFCgood.add(SkillFactory.getInstance().getSkillByName("atk"));// 蓝色(good)
		skillFCgood.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillFCgood.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillFCgood.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillFCgood.add(SkillFactory.getInstance().getSkillByName("atk"));//

		skillFCbest.add(SkillFactory.getInstance().getSkillByName("atk"));// 紫色(best)
		skillFCbest.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillFCbest.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillFCbest.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillFCbest.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillFCbest.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillFCbest.add(SkillFactory.getInstance().getSkillByName("atk"));//

		skillFCperfect.add(SkillFactory.getInstance().getSkillByName("atk"));// 橙色(perfect)
		skillFCperfect.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillFCperfect.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillFCperfect.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillFCperfect.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillFCperfect.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillFCperfect.add(SkillFactory.getInstance().getSkillByName("atk"));
		skillFCperfect.add(SkillFactory.getInstance().getSkillByName("atk"));//
		skillFCperfect.add(SkillFactory.getInstance().getSkillByName("atk"));//

		skillArrayFC.addAll(skillFCnormal);
		skillArrayFC.addAll(skillFCgood);
		skillArrayFC.addAll(skillFCbest);
		skillArrayFC.addAll(skillFCperfect);
		skillArray.addAll(skillArrayFC);
		skillArray.addAll(skillArrayZYC);
	}

	public static Array<Skill> getValue(SUBTYPE p, QUALITY q) {
		Array<Skill> rv = null;
		int ax = 0;
		int ay = 0;
		if (p == SUBTYPE.fc) {
			ax = 0;
		}
		if (p == SUBTYPE.zyc) {
			// ax = 1;
		}
		if (q == QUALITY.normal) {
			ay = 0;
		}
		if (q == QUALITY.good) {
			ay = 1;
		}
		if (q == QUALITY.best) {
			ay = 2;
		}
		if (q == QUALITY.perfect) {
			ay = 3;
		}
		Array<Array> temp = (Array<Array>) skillArray.get(ax);
		rv = temp.get(ay);
		return rv;
	}

}
