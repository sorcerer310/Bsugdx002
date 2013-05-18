package com.bsu.make;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.bsu.make.AttackWeaponFactory.ATTACK;
import com.bsu.make.DefendWeaponFactory.DEFEND;
import com.bsu.obj.Role;
import com.bsu.obj.Role.Type;
import com.bsu.obj.Skill;
import com.bsu.tools.AttackWeaponBase;
import com.bsu.tools.Configure;
import com.bsu.tools.DefendWeaponBase;
import com.bsu.tools.RoleValue;
import com.bsu.tools.Configure.QUALITY;

public class CardFactory {
	public CardFactory() {
		// TODO Auto-generated constructor stub
	}

	public static enum SUBTYPE {
		fc, zyc
	}

	private static Array<RoleValue> cardArrayFC = new Array<RoleValue>();
	private static Array<RoleValue> cardArrayZYC = new Array<RoleValue>();
	private static Array heroArray = new Array();

	static TextureRegion tr;
	static Array<Skill> sl;
	static AttackWeaponBase aw;
	static DefendWeaponBase dw;
	static {
		cardArrayFC.add(new RoleValue("fc", tr, 100, 10, 10, SkillListBase
				.getValue(SUBTYPE.fc, QUALITY.normal), AttackWeaponFactory
				.getValue(ATTACK.sword, QUALITY.normal), DefendWeaponFactory
				.getValue(DEFEND.bujia, QUALITY.normal)));// 绿色(normal)
		cardArrayFC.add(new RoleValue("fc", tr, 200, 10, 10, sl, aw, dw));// 蓝色(good)
		cardArrayFC.add(new RoleValue("fc", tr, 300, 10, 10, sl, aw, dw));// 紫色(best)
		cardArrayFC.add(new RoleValue("fc", tr, 400, 10, 10, sl, aw, dw));// 橙色(perfect)

		cardArrayZYC.add(new RoleValue("zyc", tr, 100, 10, 10, sl, aw, dw));// 绿色(normal)
		cardArrayZYC.add(new RoleValue("zyc", tr, 200, 10, 10, sl, aw, dw));// 蓝色(good)
		cardArrayZYC.add(new RoleValue("zyc", tr, 300, 10, 10, sl, aw, dw));// 紫色(best)
		cardArrayZYC.add(new RoleValue("zyc", tr, 400, 10, 10, sl, aw, dw));// 橙色(perfect)

		heroArray.addAll(cardArrayFC);
		heroArray.addAll(cardArrayZYC);
	}

	public static RoleValue getValue(SUBTYPE p, QUALITY q) {
		RoleValue rv = null;
		int ax = 0;
		int ay = 0;
		if (p == SUBTYPE.fc) {
			ax = 0;
		}
		if (p == SUBTYPE.zyc) {
			ax = 1;
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
		Array<RoleValue> temp = (Array<RoleValue>) heroArray.get(ax);
		rv = temp.get(ay);
		return rv;
	}
}
