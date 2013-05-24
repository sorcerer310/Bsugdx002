package com.bsu.tools;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Role;
import com.bsu.tools.Configure.CLASSES;
import com.bsu.tools.Configure.QUALITY;

public class U {
//	public void waitWhile(boolean f) throws InterruptedException{
//		while (f) {
//			Thread.sleep(200);
//		}
//	}
	private static Random rnd = new Random();				
	/**
	 * 为一个固定值增加一部分浮动值
	 * @param fix_val			固定的数值
	 * @param floatval_top		浮动值上限
	 * @param floatval_bottom	浮动值下限
	 * @return
	 */
	public static int getRandom(int fix_val,int floatval_bottom,int floatval_top){
		int rndval = rnd.nextInt(floatval_top - floatval_bottom);
		return fix_val + (rndval-floatval_bottom);
	}
	/**
	 * 计算攻击后产生的实际伤害
	 * @param atkval	攻击方的攻击值
	 * @param defval	防御方的防御值
	 * @return			得出实际伤害
	 */
	public static int realDamage(int atkval,int defval){
		return atkval*100/(100+defval);
	}
	/**
	 * 判断rs中是否有Role在p坐标上
	 * @param rs	所有的Role
	 * @param p		指定的box坐标
	 * @return
	 */
	public static boolean hasRoleInPos(Array<Role> rs,Vector2 p){
		for(Role r:rs)
			if(r.getBoxX()==(int)p.x && r.getBoxY()==(int)p.y)
				return true;
		return false;
	}
	/**
	 * 角色升级后返回新的hp上限
	 * @param r 角色
	 * @return 新的等级生命上限
	 */
	public static int hpLevel(Role r){
		int maxhp=0;
		int basehp=0;
		if(r.quality==QUALITY.green)
			basehp=Configure.baseHpGreen;
		if(r.quality==QUALITY.blue)
			basehp=Configure.baseHpBlue;
		if(r.quality==QUALITY.purple)
			basehp=Configure.baseHpPurple;
		if(r.quality==QUALITY.orange)
			basehp=Configure.baseHpOrange;
		maxhp=basehp*r.level;
		return maxhp;
	}
	/**
	 * 返回升级后的角色基本攻击力
	 * @param r
	 * @return
	 */
	public static int attackLevel(Role r){
		int maxattack=0;
		int base=0;
		if(r.quality==QUALITY.green)
			base=Configure.baseAttackGreen;
		if(r.quality==QUALITY.blue)
			base=Configure.baseAttackBlue;
		if(r.quality==QUALITY.purple)
			base=Configure.baseAttackPurple;
		if(r.quality==QUALITY.orange)
			base=Configure.baseAttackOrange;
		maxattack=base*r.level;
		return maxattack;
	}
	/**
	 * 返回升级后的角色基本防御力
	 * @param r
	 * @return
	 */
	public static int defendLevel(Role r){
		int defend=0;
		int base=0;
		if(r.quality==QUALITY.green)
			base=Configure.baseDefendGreen;
		if(r.quality==QUALITY.blue)
			base=Configure.baseDefendBlue;
		if(r.quality==QUALITY.purple)
			base=Configure.baseDefendPurple;
		if(r.quality==QUALITY.orange)
			base=Configure.baseDefendOrange;
		defend=base*r.level;
		return defend;
	}
	/**
	 * 返回升级后的角色下级所需经验
	 * @param r
	 * @return
	 */
	public static int expLevel(Role r){
		int exp=0;
		int base=0;
		if(r.quality==QUALITY.green)
			base=Configure.baseExpUpGreen;
		if(r.quality==QUALITY.blue)
			base=Configure.baseExpUpBlue;
		if(r.quality==QUALITY.purple)
			base=Configure.baseExpUpPurple;
		if(r.quality==QUALITY.orange)
			base=Configure.baseExpUpOrange;
		exp=base*r.level;
		return exp;
	}
	/**
	 * 返回角色的职业字符串
	 * @param r
	 * @return
	 */
	public static String getClasses(Role r){
		String n=null;
		if(r.classes==CLASSES.fighter)
			n="zs";//战士
		if(r.classes==CLASSES.cleric)
			n="ms";//牧师
		if(r.classes==CLASSES.wizard)
			n="ys";//元素
		if(r.classes==CLASSES.sorcerer)
			n="ha";//黑暗
		if(r.classes==CLASSES.archer)
			n="ss";//弓手
		return n;
	}
} 
