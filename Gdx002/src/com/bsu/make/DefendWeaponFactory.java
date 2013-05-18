package com.bsu.make;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.bsu.make.AttackWeaponFactory.ATTACK;
import com.bsu.tools.AttackWeaponBase;
import com.bsu.tools.Configure.QUALITY;
import com.bsu.tools.DefendWeaponBase;

public class DefendWeaponFactory {

	public DefendWeaponFactory() {
		// TODO Auto-generated constructor stub
	}
	public static enum DEFEND{
		bujia,changqun
	}

	private static Array<DefendWeaponBase> attackArraySword = new Array<DefendWeaponBase>();
	private static Array<DefendWeaponBase> attackArrayblade = new Array<DefendWeaponBase>();
	private static Array defendArray = new Array();
	
	static TextureRegion tr;
	static{
		attackArraySword.add(new DefendWeaponBase("布甲", tr, 1, 10, "基本的，遍地都是"));// 绿色(normal)
		attackArraySword.add(new DefendWeaponBase("天禅甲", tr, 1, 20, "这个还不错"));// 蓝色(good)
		attackArraySword.add(new DefendWeaponBase("软猬甲", tr, 1, 30,"真是幸运，极品"));// 紫色(best)
		attackArraySword.add(new DefendWeaponBase("什么甲", tr, 1, 40, "橙色装备，无敌了"));// 橙色(perfect)

		attackArrayblade.add(new DefendWeaponBase("blade", tr, 100, 10, ""));// 绿色(normal)
		attackArrayblade.add(new DefendWeaponBase("blade", tr, 200, 10, ""));// 蓝色(good)
		attackArrayblade.add(new DefendWeaponBase("blade", tr, 300, 10, ""));// 紫色(best)
		attackArrayblade.add(new DefendWeaponBase("blade", tr, 400, 10, ""));// 橙色(perfect)

		defendArray.addAll(attackArraySword);
		defendArray.addAll(attackArrayblade);
	}
	

	public static DefendWeaponBase getValue(DEFEND p, QUALITY q) {
		DefendWeaponBase rv = null;
		int ax = 0;
		int ay = 0;
		if (p == DEFEND.bujia) {
			ax = 0;
		}
		if (p == DEFEND.changqun) {
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
		Array<DefendWeaponBase> temp = (Array<DefendWeaponBase>) defendArray.get(ax);
		rv = temp.get(ay);
		return rv;
	}

}
