package com.bsu.make;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.bsu.make.AttackWeaponFactory.ATTACK;
import com.bsu.tools.AttackWeaponBase;
import com.bsu.tools.Configure.QUALITY;
import com.bsu.tools.DefendWeaponBase;

public class DefendWeaponFactory {
	private static DefendWeaponFactory instance = null;
	public static DefendWeaponFactory getInstance(){
		if(instance==null)
			instance = new DefendWeaponFactory();
		return instance;
	}
	public DefendWeaponFactory() {
		// TODO Auto-generated constructor stub
		defendArrayBujia.add(new DefendWeaponBase("布甲", tr, 1, 10, "基本的，遍地都是"));// 绿色(normal)
		defendArrayBujia.add(new DefendWeaponBase("布甲", tr, 1, 10, "基本的，遍地都是"));// 绿色(normal)
		defendArrayBujia.add(new DefendWeaponBase("天禅甲", tr, 1, 20, "这个还不错"));// 蓝色(good)
		defendArrayBujia.add(new DefendWeaponBase("软猬甲", tr, 1, 30,"真是幸运，极品"));// 紫色(best)
		defendArrayBujia.add(new DefendWeaponBase("什么甲", tr, 1, 40, "橙色装备，无敌了"));// 橙色(perfect)
		defendArrayChangqun.add(new DefendWeaponBase("blade", tr, 100, 10, ""));// 绿色(normal)
		defendArrayChangqun.add(new DefendWeaponBase("blade", tr, 100, 10, ""));// 绿色(normal)
		defendArrayChangqun.add(new DefendWeaponBase("blade", tr, 200, 10, ""));// 蓝色(good)
		defendArrayChangqun.add(new DefendWeaponBase("blade", tr, 300, 10, ""));// 紫色(best)
		defendArrayChangqun.add(new DefendWeaponBase("blade", tr, 400, 10, ""));// 橙色(perfect)

	}
	public static enum DEFEND{
		bujia,changqun
	}

	private  Array<DefendWeaponBase> defendArrayBujia = new Array<DefendWeaponBase>();
	private  Array<DefendWeaponBase> defendArrayChangqun = new Array<DefendWeaponBase>();

	
	 TextureRegion tr;
	

	public DefendWeaponBase getValue(DEFEND p, QUALITY q) {
		DefendWeaponBase rv = null;
		Array<DefendWeaponBase> temp=null;
		int ay = 0;
		if (p == DEFEND.bujia) {
			temp=defendArrayBujia;
		}
		if (p == DEFEND.changqun) {
			temp=defendArrayChangqun;
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
