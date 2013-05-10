package com.bsu.make;

import com.bsu.obj.Role;

/**
 * ��ɫ�������������ɼ���Ӣ�ۻ����NPC
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
	 * ���һ��Ӣ�����͵�Role,�����ں����ж�Hero����һЩ��ʼ������
	 * @param n	Ӣ�۵�����
	 * @return	����Ӣ�۶���
	 */
	public Role getHeroRole(String n){
		Role r = new Role(Role.Type.HERO,n);
		//��һЩӢ�۳�ʼ���Ĺ���
		return r;
	}
	
	public Role getEnemyRole(String n){
		Role r = new Role(Role.Type.ENEMY,n);
		//��һЩ���˳�ʼ���Ĺ���
		
		return r;
	}
}
