package com.bsu.tools;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Role;

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
} 
