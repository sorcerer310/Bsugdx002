package com.bsu.make;

import com.bsu.obj.Role;

/**
 * 用来生成角色，现在只有英雄和NPC角色
 * @author fengchong
 *
 */
public class RoleFactory {
	private static RoleFactory instance = null;
	private RoleFactory(){}
	public static RoleFactory getInstance(){
		if(instance==null)
			instance = new RoleFactory();
		return instance;
	}
	/**
	 * 获得一个英雄类型的角色
	 * @param n	改英雄的名字
	 * @return	返回英雄类型的角色对象
	 */
	public Role getHeroRole(String n){
		Role r = new Role(Role.Type.HERO,n);
		//初始化英雄类型角色要做的事写在这里。
		return r;
	}
	
	public Role getEnemyRole(String n){
		Role r = new Role(Role.Type.ENEMY,n);
		//初始化敌人类型角色要做的事写在这里
		
		return r;
	}
}