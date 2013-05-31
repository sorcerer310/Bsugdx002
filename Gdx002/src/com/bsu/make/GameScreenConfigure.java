package com.bsu.make;

import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.obj.Role.Type;
import com.bsu.obj.skilltree.SkillTree;
import com.bsu.screen.GameScreen;
import com.bsu.tools.Configure.CLASSES;
import com.bsu.tools.Configure.QUALITY;
import com.bsu.tools.GameTextureClass;
import com.bsu.tools.U;

public class GameScreenConfigure {
	private static GameScreenConfigure instance = null;

	private GameScreenConfigure() {
	}

	public static GameScreenConfigure getInstance() {
		if (instance == null)
			instance = new GameScreenConfigure();
		return instance;
	}
	private Array<Role> roles=new Array<Role>(); 				//出战的所有人物
	private Array<Role> heroRoles=new Array<Role>();			//出战的所有英雄
	private Array<Role> npcRoles=new Array<Role>();				//出战的所有敌人
	/**
	 * 教学关卡
	 * @param gs
	 */
	public void makeGameScreenTeaching(GameScreen gs){
		roles.clear();
		heroRoles.clear();
		npcRoles.clear();
		//加入3个英雄
		heroRoles.add(new Role(Role.Type.HERO,QUALITY.green,CLASSES.fighter,"粘思",U.getRandom(100, -6, 6),8,10
				,EquipFactory.getInstance().getWeaponByIdx(1),EquipFactory.getInstance().getArmorByIdx(1),
				new SkillTree().getSkillTreeFixedSkill(1)
				,GameTextureClass.getInstance().fc_photo));
		heroRoles.add(new Role(Role.Type.HERO,QUALITY.green,CLASSES.wizard,"呜思",U.getRandom(50, -3, 6),10,3
				,EquipFactory.getInstance().getWeaponByIdx(1),EquipFactory.getInstance().getArmorByIdx(1),
				new SkillTree().getSkillTreeFixedSkill(10),GameTextureClass.getInstance().h5_photo));
		heroRoles.add(new Role(Role.Type.HERO,QUALITY.green,CLASSES.archer,"色首",U.getRandom(80, -2, 2),10,16
				,EquipFactory.getInstance().getWeaponByIdx(1),EquipFactory.getInstance().getArmorByIdx(1),
				new SkillTree().getSkillTreeFixedSkill(38),GameTextureClass.getInstance().h3_photo));
		
		//加入3个敌人
		npcRoles.add(RoleFactory.getInstance().getFighter("撒手", Type.ENEMY, QUALITY.green, GameTextureClass.getInstance().h1_photo,1));
		npcRoles.add(RoleFactory.getInstance().getFighter("撒手", Type.ENEMY, QUALITY.green, GameTextureClass.getInstance().h1_photo,1));
		npcRoles.add(RoleFactory.getInstance().getFighter("撒手", Type.ENEMY, QUALITY.green, GameTextureClass.getInstance().h1_photo,1));
		
		roles.addAll(heroRoles);
		roles.addAll(npcRoles);
		
		gs.game_init(0, roles);
	}
	
	public void makeGameScreen1(GameScreen gs) {

		Array<Role> rols = new Array<Role>();
		rols.add(RoleFactory.getInstance().getFighter("hero1", Type.HERO, QUALITY.green,GameTextureClass.getInstance().h0_photo));
		rols.add(RoleFactory.getInstance().getFighter("hero2", Type.HERO, QUALITY.green,GameTextureClass.getInstance().h1_photo));
		rols.add(RoleFactory.getInstance().getFighter("hero3", Type.HERO, QUALITY.green,GameTextureClass.getInstance().h2_photo));
		rols.add(RoleFactory.getInstance().getFighter("enemy1", Type.ENEMY, QUALITY.green,GameTextureClass.getInstance().h3_photo));
		rols.add(RoleFactory.getInstance().getFighter("enemy2", Type.ENEMY, QUALITY.green,GameTextureClass.getInstance().h4_photo));
		rols.add(RoleFactory.getInstance().getFighter("enemy3", Type.ENEMY, QUALITY.green,GameTextureClass.getInstance().h5_photo));

		gs.game_init(0, rols);
	}

	public void makeGameScreen2(GameScreen gs) {
		roles.clear();
		heroRoles.clear();
		npcRoles.clear();
		
		Array<Role> pfr =  Player.getInstance().playerFightRole;
		for(int i=0;i<pfr.size;i++){
			heroRoles.add(pfr.get(i));
		}
//		heroRoles.add(Player.getInstance().playerFightRole.get(1));
//		npcRoles.add(RoleFactory.getInstance().getFighter("enemy1", Type.ENEMY, QUALITY.green,GameTextureClass.getInstance().h5_photo));
		npcRoles.add(new Role(Role.Type.ENEMY,QUALITY.green,CLASSES.fighter,"测试敌人1",U.getRandom(100, -6, 6),8,10
				,EquipFactory.getInstance().getWeaponByIdx(1),EquipFactory.getInstance().getArmorByIdx(1),
				new SkillTree().getSkillTreeFixedSkill(10)
				,GameTextureClass.getInstance().h3_photo));				//测试技能用
//		npcRoles.add(new Role(Role.Type.ENEMY,QUALITY.green,CLASSES.fighter,"测试敌人2",U.getRandom(100, -6, 6),8,10
//				,EquipFactory.getInstance().getWeaponByIdx(1),EquipFactory.getInstance().getArmorByIdx(1),
//				new SkillTree().getSkillTreeFixedSkill(10)
//				,GameTextureClass.getInstance().h3_photo));				//测试技能用
//		npcRoles.add(new Role(Role.Type.ENEMY,QUALITY.green,CLASSES.fighter,"测试敌人3",U.getRandom(100, -6, 6),8,10
//				,EquipFactory.getInstance().getWeaponByIdx(1),EquipFactory.getInstance().getArmorByIdx(1),
//				new SkillTree().getSkillTreeFixedSkill(10)
//				,GameTextureClass.getInstance().h3_photo));				//测试技能用
		roles.addAll(heroRoles);
		roles.addAll(npcRoles);
		gs.game_init(0, roles);
	}

	public Array<Role> getRols() {
		roles.clear();
		roles.addAll(heroRoles);
		roles.addAll(npcRoles);
		return roles;
	}

	public Array<Role> getHeroRoles() {
		return heroRoles;
	}

	public void setHeroRoles(Array<Role> heroRoles) {
		this.heroRoles = heroRoles;
	}
}
