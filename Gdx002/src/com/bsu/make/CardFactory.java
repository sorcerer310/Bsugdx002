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
import com.bsu.tools.GameTextureClass;
import com.bsu.tools.RoleValue;
import com.bsu.tools.Configure.QUALITY;

public class CardFactory {
	private static CardFactory instance = null;

	public static CardFactory getInstance() {
		if (instance == null)
			instance = new CardFactory();
		return instance;
	}

	public CardFactory() {
		// TODO Auto-generated constructor stub
		cardArrayFC
				.add(new RoleValue("fc", QUALITY.green, Type.HERO,
						GameTextureClass.getInstance().fc_photo, 100, 10, 10,
						SkillListBase.getInstance().getValue(SUBTYPE.fc,
								QUALITY.green), AttackWeaponFactory
								.getInstance().getValue(ATTACK.sword,
										QUALITY.green), DefendWeaponFactory
								.getInstance().getValue(DEFEND.bujia,
										QUALITY.green)));// 绿色(normal)
		cardArrayFC.add(new RoleValue("fc", QUALITY.blue, Type.HERO,
				GameTextureClass.getInstance().fc_photo, 200, 10, 10, sl, aw,
				dw));// 蓝色(good)
		cardArrayFC.add(new RoleValue("fc", QUALITY.purple, Type.HERO,
				GameTextureClass.getInstance().fc_photo, 300, 10, 10, sl, aw,
				dw));// 紫色(best)
		cardArrayFC.add(new RoleValue("fc", QUALITY.orange, Type.HERO,
				GameTextureClass.getInstance().fc_photo, 400, 10, 10, sl, aw,
				dw));// 橙色(perfect)

	
		cardArrayZYC.add(new RoleValue("zyc", QUALITY.green, Type.HERO,
				GameTextureClass.getInstance().zyc_photo, 100, 10, 10,
				SkillListBase.getInstance()
						.getValue(SUBTYPE.zyc, QUALITY.green),
				AttackWeaponFactory.getInstance().getValue(ATTACK.sword,
						QUALITY.green), DefendWeaponFactory.getInstance()
						.getValue(DEFEND.bujia, QUALITY.green)));// 绿色(normal)
		cardArrayZYC.add(new RoleValue("zyc", QUALITY.blue, Type.HERO,
				GameTextureClass.getInstance().fc_photo, 200, 10, 10, sl, aw,
				dw));// 蓝色(good)
		cardArrayZYC.add(new RoleValue("zyc", QUALITY.purple, Type.HERO,
				GameTextureClass.getInstance().fc_photo, 300, 10, 10, sl, aw,
				dw));// 紫色(best)
		cardArrayZYC.add(new RoleValue("zyc", QUALITY.orange, Type.HERO,
				GameTextureClass.getInstance().zyc_photo, 100, 10, 10,
				SkillListBase.getInstance().getValue(SUBTYPE.zyc,
						QUALITY.orange), AttackWeaponFactory.getInstance()
						.getValue(ATTACK.sword, QUALITY.orange),
				DefendWeaponFactory.getInstance().getValue(DEFEND.bujia,
						QUALITY.orange)));// 橙色(perfect)


		cardArrayEnemy.add(new RoleValue("npc", QUALITY.orange, Type.ENEMY,
				GameTextureClass.getInstance().h5_photo, 100, 10, 10,
				SkillListBase.getInstance().getValue(SUBTYPE.enemy,
						QUALITY.green), AttackWeaponFactory.getInstance()
						.getValue(ATTACK.sword, QUALITY.green),
				DefendWeaponFactory.getInstance().getValue(DEFEND.bujia,
						QUALITY.green)));// 绿色(normal)
		cardArrayEnemy.add(new RoleValue("zyc", QUALITY.orange, Type.ENEMY,
				GameTextureClass.getInstance().h5_photo, 200, 10, 10, sl, aw,
				dw));// 蓝色(good)
		cardArrayEnemy.add(new RoleValue("zyc", QUALITY.orange, Type.ENEMY,
				GameTextureClass.getInstance().fc_photo, 300, 10, 10, sl, aw,
				dw));// 紫色(best)
		cardArrayEnemy.add(new RoleValue("zyc", QUALITY.orange, Type.ENEMY,
				GameTextureClass.getInstance().fc_photo, 400, 10, 10, sl, aw,
				dw));// 橙色(perfect)
	}

	public static enum SUBTYPE {
		fc, zyc, enemy
	}

	private Array<RoleValue> cardArrayFC = new Array<RoleValue>();
	private Array<RoleValue> cardArrayZYC = new Array<RoleValue>();
	private Array<RoleValue> cardArrayEnemy = new Array<RoleValue>();
	Array<Skill> sl;
	AttackWeaponBase aw;
	DefendWeaponBase dw;

	public RoleValue getValue(SUBTYPE p, QUALITY q) {
		RoleValue rv = null;
		Array<RoleValue> temp = null;

		int ay = 0;
		if (p == SUBTYPE.fc) {
			temp = cardArrayFC;
		}
		if (p == SUBTYPE.zyc) {
			temp = cardArrayZYC;
		}
		if (p == SUBTYPE.enemy) {
			temp = cardArrayEnemy;
		}

		if (q == QUALITY.green) {
			ay = 0;
		}
		if (q == QUALITY.blue) {
			ay = 1;
		}
		if (q == QUALITY.purple) {
			ay = 2;
		}
		if (q == QUALITY.orange) {
			ay = 3;
		}
		rv = temp.get(ay);
		return rv;
	}
}
