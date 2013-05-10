package com.bsu.make;

import com.bsu.obj.Role;

/**
 * 角色工厂，用于生成己方英雄或敌人NPC
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
	 * 获得一个英雄类型的Role,可以在函数中对Hero进行一些初始化工作
	 * @param n	英雄的名称
	 * @return	返回英雄对象
	 */
	public Role getHeroRole(String n){
		Role r = new Role(Role.Type.HERO,n);
		//做一些英雄初始化的工作
		return r;
	}
	
	public Role getEnemyRole(String n){
		Role r = new Role(Role.Type.ENEMY,n);
		//做一些敌人初始化的工作
		
		return r;
	}
}
