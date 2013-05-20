package com.bsu.make;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.bsu.tools.AttackWeaponBase;
import com.bsu.tools.RoleValue;
import com.bsu.tools.Configure.QUALITY;

public class AttackWeaponFactory {
	private static AttackWeaponFactory instance = null;

	public static AttackWeaponFactory getInstance() {
		if (instance == null)
			instance = new AttackWeaponFactory();
		return instance;
	}

	public AttackWeaponFactory() {
		// TODO Auto-generated constructor stub
		attackArraySword.add(new AttackWeaponBase("sword", tr, 1, 10,
				"基本的，遍地都是"));// 绿色(normal)
		attackArraySword.add(new AttackWeaponBase("sword", tr, 1, 10,
				"基本的，遍地都是"));// 绿色(normal)
		attackArraySword.add(new AttackWeaponBase("sword", tr, 1, 20, "这个还不错"));// 蓝色(good)
		attackArraySword
				.add(new AttackWeaponBase("sword", tr, 1, 30, "真是幸运，极品"));// 紫色(best)
		attackArraySword.add(new AttackWeaponBase("sword", tr, 1, 40,
				"橙色装备，无敌了"));// 橙色(perfect)
		
		attackArrayblade.add(new AttackWeaponBase("blade", tr, 100, 10, ""));// 绿色(normal)
		attackArrayblade.add(new AttackWeaponBase("blade", tr, 100, 10, ""));// 绿色(normal)
		attackArrayblade.add(new AttackWeaponBase("blade", tr, 200, 10, ""));// 蓝色(good)
		attackArrayblade.add(new AttackWeaponBase("blade", tr, 300, 10, ""));// 紫色(best)
		attackArrayblade.add(new AttackWeaponBase("blade", tr, 400, 10, ""));// 橙色(perfect)

	}

	public static enum ATTACK {
		sword, blade
	}

	private Array<AttackWeaponBase> attackArraySword = new Array<AttackWeaponBase>();
	private Array<AttackWeaponBase> attackArrayblade = new Array<AttackWeaponBase>();

	TextureRegion tr;

	public AttackWeaponBase getValue(ATTACK p, QUALITY q) {
		AttackWeaponBase rv = null;
		Array<AttackWeaponBase> temp = null;
		int ay = 0;
		if (p == ATTACK.sword) {
			temp = attackArraySword;
		}
		if (p == ATTACK.blade) {
			temp = attackArrayblade;
		}
		if (q == QUALITY.white) {
			ay = 0;
		}
		if (q == QUALITY.green) {
			ay = 1;
		}
		if (q == QUALITY.blue) {
			ay = 2;
		}
		if (q == QUALITY.purple) {
			ay = 3;
		}
		if (q == QUALITY.orange) {
			ay = 4;
		}
		rv = temp.get(ay);
		return rv;
	}
}
