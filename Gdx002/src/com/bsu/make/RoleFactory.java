package com.bsu.make;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bsu.obj.Item;
import com.bsu.obj.Role;
import com.bsu.obj.Role.Type;
import com.bsu.obj.skilltree.SkillTree;
import com.bsu.tools.GC.CLASSES;
import com.bsu.tools.U;
import com.bsu.tools.GC.QUALITY;

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
	 * 获得一个战士
	 * @param n	战士的名称
	 * @param t	战士的类型，英雄还是敌人
	 * @param q	战士的品质
	 * @return
	 */
	public Role getFighter(String n,Type t,QUALITY q,TextureRegion tr){
		return new Role(t,q,CLASSES.fighter,n,U.getRandom(100, -6, 6),8,10
				,EquipFactory.getInstance().getWeaponByIdx(1),EquipFactory.getInstance().getArmorByIdx(1),
				new SkillTree().getRandomSkillTree(q, CLASSES.fighter),tr);
	}
	
	public Role getFighter(String n,Type t,QUALITY q,TextureRegion tr,int sklidx){
		return new Role(t,q,CLASSES.fighter,n,U.getRandom(100, -6, 6),8,10
				,EquipFactory.getInstance().getWeaponByIdx(1),EquipFactory.getInstance().getArmorByIdx(1),
				new SkillTree().getSkillTreeFixedSkill(sklidx),tr);
	}
	/**
	 * 获得一个固定技能树的战士
	 * @param n			战士名称 
	 * @param t			战士的类型，英雄还是敌人
	 * @param q			战士的品质 
	 * @param tr		使用的纹理
	 * @param sklidx	若干的技能id
	 * @return
	 */
	public Role getFighter(String n,Type t,QUALITY q,TextureRegion tr,int[] sklidx){
		return new Role(t,q,CLASSES.fighter,n,U.getRandom(100, -6, 6),8,10
				,EquipFactory.getInstance().getWeaponByIdx(1),EquipFactory.getInstance().getArmorByIdx(1),
				new SkillTree().getSkillTreeFixedSkill(sklidx),tr);
	}
	
	/**
	 * 获得一个元素法师
	 * @param n	
	 * @param t
	 * @param q
	 * @return
	 */
	public Role getWizard(String n,Type t,QUALITY q,TextureRegion tr){
		return new Role(t,q,CLASSES.wizard,n,U.getRandom(50, -3, 6),10,3
				,EquipFactory.getInstance().getWeaponByIdx(1),EquipFactory.getInstance().getArmorByIdx(1),
				new SkillTree().getRandomSkillTree(q, CLASSES.wizard),tr);
	}
	/**
	 * 获得一个牧师
	 * @param n
	 * @param t
	 * @param q
	 * @return
	 */
	public Role getCleric(String n,Type t,QUALITY q,TextureRegion tr){
		return new Role(t,q,CLASSES.cleric,n,U.getRandom(70, -4, 4),3,5
				,EquipFactory.getInstance().getWeaponByIdx(1),EquipFactory.getInstance().getArmorByIdx(1),
				new SkillTree().getRandomSkillTree(q, CLASSES.cleric),tr);
	}
	/**
	 * 获得一个黑暗法师
	 * @param n
	 * @param t
	 * @param q
	 * @return
	 */
	public Role getSorcerer(String n,Type t,QUALITY q,TextureRegion tr){
		return new Role(t,q,CLASSES.sorcerer,n,U.getRandom(70, -6, 3),5,5
				,EquipFactory.getInstance().getWeaponByIdx(1),EquipFactory.getInstance().getArmorByIdx(1),
				new SkillTree().getRandomSkillTree(q, CLASSES.sorcerer),tr);
	}
	/**
	 * 获得一个射手
	 * @param n
	 * @param t
	 * @param q
	 * @return
	 */
	public Role getArcher(String n,Type t,QUALITY q,TextureRegion tr){
		return new Role(t,q,CLASSES.archer,n,U.getRandom(80, -2, 2),10,16
				,EquipFactory.getInstance().getWeaponByIdx(1),EquipFactory.getInstance().getArmorByIdx(1),
				new SkillTree().getRandomSkillTree(q, CLASSES.archer),tr);
	}
	public Role getRole(Item item){
		Role e=null;
		if(item.classes==CLASSES.fighter){
			e=getFighter(item.name, Type.HERO, item.q, item.tr_item);
		}
		if(item.classes==CLASSES.archer){
			e=getArcher(item.name, Type.HERO, item.q, item.tr_item);
		}
		if(item.classes==CLASSES.cleric){
			e=getCleric(item.name, Type.HERO, item.q, item.tr_item);
		}
		if(item.classes==CLASSES.sorcerer){
			e=getSorcerer(item.name, Type.HERO, item.q, item.tr_item);
		}
		if(item.classes==CLASSES.wizard){
			e=getWizard(item.name, Type.HERO, item.q, item.tr_item);
		}
		return e;
	}
}
