package com.bsu.make;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Equip;
import com.bsu.obj.Item;
import com.bsu.obj.Role;
import com.bsu.obj.Role.BATLESTATE;
import com.bsu.obj.Role.Type;
import com.bsu.obj.data.RoleData;
import com.bsu.obj.data.SkillData;
import com.bsu.obj.skilltree.Skill;
import com.bsu.obj.skilltree.SkillTree;
import com.bsu.tools.GC.CLASSES;
import com.bsu.tools.GTC;
import com.bsu.tools.U;
import com.bsu.tools.GC.QUALITY;

/**
 * 用来生成角色，现在只有英雄和NPC角色
 * 
 * @author fengchong
 * 
 */
public class RoleFactory {
	private static RoleFactory instance = null;

	private RoleFactory() {
	}

	public static RoleFactory getInstance() {
		if (instance == null)
			instance = new RoleFactory();
		return instance;
	}

	/**
	 * 获得一个战士
	 * 
	 * @param n
	 *            战士的名称
	 * @param t
	 *            战士的类型，英雄还是敌人
	 * @param q
	 *            战士的品质
	 * @return
	 */
	public Role getFighter(String n, Type t, QUALITY q, String tr) {
		return new Role(t, q, CLASSES.fighter, BATLESTATE.IDLE, n, EquipFactory
				.getInstance().getWeaponByIdx(1), EquipFactory.getInstance()
				.getArmorByIdx(1), new SkillTree().getRandomSkillTree(q,
				CLASSES.fighter), tr);
	}

	public Role getFighter(String n, Type t, QUALITY q, String tr, int sklidx) {
		return new Role(t, q, CLASSES.fighter, BATLESTATE.IDLE, n, EquipFactory
				.getInstance().getWeaponByIdx(1), EquipFactory.getInstance()
				.getArmorByIdx(1),
				new SkillTree().getSkillTreeFixedSkill(sklidx), tr);
	}

	/**
	 * 获得一个固定技能树的战士
	 * 
	 * @param n
	 *            战士名称
	 * @param t
	 *            战士的类型，英雄还是敌人
	 * @param q
	 *            战士的品质
	 * @param tr
	 *            使用的纹理
	 * @param sklidx
	 *            若干的技能id
	 * @return
	 */
	public Role getFighter(String n, Type t, QUALITY q, String tr, int[] sklidx) {
		return new Role(t, q, CLASSES.fighter, BATLESTATE.IDLE, n, EquipFactory
				.getInstance().getWeaponByIdx(1), EquipFactory.getInstance()
				.getArmorByIdx(1),
				new SkillTree().getSkillTreeFixedSkill(sklidx), tr);
	}

	/**
	 * 获得一个元素法师
	 * 
	 * @param n
	 * @param t
	 * @param q
	 * @return
	 */
	public Role getWizard(String n, Type t, QUALITY q, String tr) {
		return new Role(t, q, CLASSES.wizard, BATLESTATE.IDLE, n, EquipFactory
				.getInstance().getWeaponByIdx(1), EquipFactory.getInstance()
				.getArmorByIdx(1), new SkillTree().getRandomSkillTree(q,
				CLASSES.wizard), tr);
	}

	public Role getWizard(String n, Type t, QUALITY q, String tr, int sklidx) {
		return new Role(t, q, CLASSES.wizard, BATLESTATE.IDLE, n, EquipFactory
				.getInstance().getWeaponByIdx(1), EquipFactory.getInstance()
				.getArmorByIdx(1),
				new SkillTree().getSkillTreeFixedSkill(sklidx), tr);
	}

	/**
	 * 获得一个牧师
	 * 
	 * @param n
	 * @param t
	 * @param q
	 * @return
	 */
	public Role getCleric(String n, Type t, QUALITY q, String tr) {
		return new Role(t, q, CLASSES.cleric, BATLESTATE.IDLE, n, EquipFactory
				.getInstance().getWeaponByIdx(1), EquipFactory.getInstance()
				.getArmorByIdx(1), new SkillTree().getRandomSkillTree(q,
				CLASSES.cleric), tr);
	}

	public Role getCleric(String n, Type t, QUALITY q, String tr, int sklidx) {
		return new Role(t, q, CLASSES.cleric, BATLESTATE.IDLE, n, EquipFactory
				.getInstance().getWeaponByIdx(1), EquipFactory.getInstance()
				.getArmorByIdx(1),
				new SkillTree().getSkillTreeFixedSkill(sklidx), tr);
	}

	/**
	 * 获得一个黑暗法师
	 * 
	 * @param n
	 * @param t
	 * @param q
	 * @return
	 */
	public Role getSorcerer(String n, Type t, QUALITY q, String tr) {
		return new Role(t, q, CLASSES.sorcerer, BATLESTATE.IDLE, n,
				EquipFactory.getInstance().getWeaponByIdx(1), EquipFactory
						.getInstance().getArmorByIdx(1),
				new SkillTree().getRandomSkillTree(q, CLASSES.sorcerer), tr);
	}

	public Role getSorcerer(String n, Type t, QUALITY q, String tr, int sklidx) {
		return new Role(t, q, CLASSES.sorcerer, BATLESTATE.IDLE, n,
				EquipFactory.getInstance().getWeaponByIdx(1), EquipFactory
						.getInstance().getArmorByIdx(1),
				new SkillTree().getSkillTreeFixedSkill(sklidx), tr);
	}

	/**
	 * 获得一个射手
	 * 
	 * @param n
	 * @param t
	 * @param q
	 * @return
	 */
	public Role getArcher(String n, Type t, QUALITY q, String tr) {
		return new Role(t, q, CLASSES.archer, BATLESTATE.IDLE, n, EquipFactory
				.getInstance().getWeaponByIdx(1), EquipFactory.getInstance()
				.getArmorByIdx(1), new SkillTree().getRandomSkillTree(q,
				CLASSES.archer), tr);
	}

	public Role getArcher(String n, Type t, QUALITY q, String tr, int sklidx) {
		return new Role(t, q, CLASSES.archer, BATLESTATE.IDLE, n, EquipFactory
				.getInstance().getWeaponByIdx(1), EquipFactory.getInstance()
				.getArmorByIdx(1),
				new SkillTree().getSkillTreeFixedSkill(sklidx), tr);
	}

	/**
	 * 根据5个参数获得指定的英雄
	 * 
	 * @param c
	 *            英雄职业
	 * @param n
	 *            英雄名字
	 * @param t
	 *            英雄类别
	 * @param q
	 *            英雄品质
	 * @param tr
	 *            英雄使用的纹理图片
	 * @return 返回英雄对象
	 */
	public Role getRole(CLASSES c, String n, Type t, QUALITY q, String tr) {
		Role r = null;
		switch (c) {
		case fighter:
			r = this.getFighter(n, t, q, tr);
			break;
		case wizard:
			r = this.getWizard(n, t, q, tr);
			break;
		case cleric:
			r = this.getCleric(n, t, q, tr);
			break;
		case sorcerer:
			r = this.getSorcerer(n, t, q, tr);
			break;
		case archer:
			r = this.getArcher(n, t, q, tr);
			break;
		}
		return r;
	}

	public Role getRole(String c, String n, Type t, QUALITY q, String tr,
			int idx) {
		Role r = null;
		if (c.equals("fight"))
			r = this.getFighter(n, t, q, tr, idx);
		else if (c.equals("wizard"))
			r = this.getWizard(n, t, q, tr, idx);
		else if (c.equals("cleric"))
			r = this.getCleric(n, t, q, tr, idx);
		else if (c.equals("sorcerer"))
			r = this.getSorcerer(n, t, q, tr, idx);
		else if (c.equals("archer"))
			r = this.getArcher(n, t, q, tr, idx);
		return r;
	}

	/**
	 * 获得一个指定参数的英雄Role,一般用于加载存档使用
	 * 
	 * @param c
	 *            英雄职业
	 * @param n
	 *            英雄名字
	 * @param q
	 *            英雄品质
	 * @param trname
	 *            使用纹理的名字
	 * @return 返回一个英雄角色
	 */
	public Role getHeroRole(RoleData rd) {
		Role r = null;
		Array<Skill> sks_tree = new Array<Skill>();
		for (SkillData sd : rd.skill_tree) {
			Skill sk = SkillFactory.getInstance().getSkillByIdx(sd.id);
			sk.setLev(sd.lev);
			sk.enable = sd.enable;
			sk.skill_index = sd.skill_index;
			sks_tree.add(sk);
		}
		r = new Role(Type.HERO, rd.quality, rd.classes, rd.bstate, rd.name,
				EquipFactory.getInstance().getWeaponByIdx(1), EquipFactory
						.getInstance().getArmorByIdx(1), sks_tree,
				rd.roleTexture);
		r.exp = rd.exp;
		r.hp_talent=rd.hp_talent;
		r.attack_talent=rd.attack_talent;
		r.defend_talent=rd.defend_talent;
		r.locked = rd.locked;
		return r;
	}

	public Role getRole(Item item) {
		Role e = null;
		if (item.classes == CLASSES.fighter) {
			e = getFighter(item.name, Type.HERO, item.q, "fc_photo");
		}
		if (item.classes == CLASSES.archer) {
			e = getArcher(item.name, Type.HERO, item.q, "fc_photo");
		}
		if (item.classes == CLASSES.cleric) {
			e = getCleric(item.name, Type.HERO, item.q, "fc_photo");
		}
		if (item.classes == CLASSES.sorcerer) {
			e = getSorcerer(item.name, Type.HERO, item.q, "fc_photo");
		}
		if (item.classes == CLASSES.wizard) {
			e = getWizard(item.name, Type.HERO, item.q, "fc_photo");
		}
		return e;
	}
}
