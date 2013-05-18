package com.bsu.make;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.bsu.tools.AttackWeaponBase;
import com.bsu.tools.RoleValue;
import com.bsu.tools.Configure.QUALITY;


public class AttackWeaponFactory {

	public AttackWeaponFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static enum ATTACK{
		sword,blade
	}

	private static Array<AttackWeaponBase> attackArraySword = new Array<AttackWeaponBase>();
	private static Array<AttackWeaponBase> attackArrayblade = new Array<AttackWeaponBase>();
	private static Array attackArray = new Array();
	
	static TextureRegion tr;
	static{
		attackArraySword.add(new AttackWeaponBase("sword", tr, 1, 10, "基本的，遍地都是"));// 绿色(normal)
		attackArraySword.add(new AttackWeaponBase("sword", tr, 1, 20, "这个还不错"));// 蓝色(good)
		attackArraySword.add(new AttackWeaponBase("sword", tr, 1, 30,"真是幸运，极品"));// 紫色(best)
		attackArraySword.add(new AttackWeaponBase("sword", tr, 1, 40, "橙色装备，无敌了"));// 橙色(perfect)

		attackArrayblade.add(new AttackWeaponBase("blade", tr, 100, 10, ""));// 绿色(normal)
		attackArrayblade.add(new AttackWeaponBase("blade", tr, 200, 10, ""));// 蓝色(good)
		attackArrayblade.add(new AttackWeaponBase("blade", tr, 300, 10, ""));// 紫色(best)
		attackArrayblade.add(new AttackWeaponBase("blade", tr, 400, 10, ""));// 橙色(perfect)

		attackArray.addAll(attackArraySword);
		attackArray.addAll(attackArrayblade);
	}
	

	public static AttackWeaponBase getValue(ATTACK p, QUALITY q) {
		AttackWeaponBase rv = null;
		int ax = 0;
		int ay = 0;
		if (p == ATTACK.sword) {
			ax = 0;
		}
		if (p == ATTACK.blade) {
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
		Array<AttackWeaponBase> temp = (Array<AttackWeaponBase>) attackArray.get(ax);
		rv = temp.get(ay);
		return rv;
	}
}
