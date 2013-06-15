package com.bsu.make;

import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.obj.Role.Type;
import com.bsu.screen.GameScreen;
import com.bsu.tools.GC.QUALITY;
import com.bsu.tools.GTC;
import com.bsu.obj.GameScreenData;

public class GameScreenFactory {
	private static GameScreenFactory instance = null;

	private GameScreenFactory() {
	}

	public static GameScreenFactory getInstance() {
		if (instance == null)
			instance = new GameScreenFactory();
		return instance;
	}
	/**
	 * 根据索引生成一个关卡
	 * @param id
	 * @return
	 */
	public GameScreenData makeGameScreen(int id){
		GameScreenData gsd = null;
		switch(id){
		case 0:
			gsd = makeGameScreenTeaching();
			break;
		case 1:
			break;
		case 2:
			break;
		default:
			break;
		}
		return gsd;
	}
	
	/**
	 * 教学关卡
	 * @param gs
	 */
	private  GameScreenData makeGameScreenTeaching(){
		GameScreenData gsd = new GameScreenData();

		gsd.setId(0);
		gsd.setMapName("teaching");
		
		//加入玩家的上场英雄
		for(Role r:Player.getInstance().playerFightRole)
			gsd.getHeroRoles().add(r);
		//加入敌人
		gsd.getNpcRoles().add(RoleFactory.getInstance().getFighter("撒手1", Type.ENEMY, QUALITY.green, GTC.getInstance().zyc_photo,3));
		gsd.getNpcRoles().add(RoleFactory.getInstance().getFighter("撒手2", Type.ENEMY, QUALITY.green, GTC.getInstance().zyc_photo,5));
		gsd.getNpcRoles().add(RoleFactory.getInstance().getFighter("撒手3", Type.ENEMY, QUALITY.green, GTC.getInstance().zyc_photo,5));
		gsd.getNpcRoles().add(RoleFactory.getInstance().getFighter("撒手4", Type.ENEMY, QUALITY.green, GTC.getInstance().zyc_photo,5));
		gsd.getNpcRoles().add(RoleFactory.getInstance().getFighter("撒手5", Type.ENEMY, QUALITY.green, GTC.getInstance().zyc_photo,5));
		gsd.getNpcRoles().add(RoleFactory.getInstance().getFighter("撒手6", Type.ENEMY, QUALITY.green, GTC.getInstance().zyc_photo,5));
		gsd.getNpcRoles().add(RoleFactory.getInstance().getFighter("撒手7", Type.ENEMY, QUALITY.green, GTC.getInstance().zyc_photo,5));
		gsd.getNpcRoles().add(RoleFactory.getInstance().getFighter("撒手8", Type.ENEMY, QUALITY.green, GTC.getInstance().zyc_photo,5));
		gsd.getNpcRoles().add(RoleFactory.getInstance().getFighter("撒手9", Type.ENEMY, QUALITY.green, GTC.getInstance().zyc_photo,5));
		gsd.getNpcRoles().add(RoleFactory.getInstance().getFighter("撒手10", Type.ENEMY, QUALITY.green, GTC.getInstance().zyc_photo,5));
		gsd.getNpcRoles().add(RoleFactory.getInstance().getFighter("撒手11", Type.ENEMY, QUALITY.green, GTC.getInstance().zyc_photo,5));

		return gsd;
	}
	
	private void makeGameScreen1(GameScreen gs) {

		Array<Role> rols = new Array<Role>();
		rols.add(RoleFactory.getInstance().getFighter("hero1", Type.HERO, QUALITY.green,GTC.getInstance().zyc_photo));
		rols.add(RoleFactory.getInstance().getFighter("hero2", Type.HERO, QUALITY.green,GTC.getInstance().zyc_photo));
		rols.add(RoleFactory.getInstance().getFighter("hero3", Type.HERO, QUALITY.green,GTC.getInstance().zyc_photo));
		rols.add(RoleFactory.getInstance().getFighter("enemy1", Type.ENEMY, QUALITY.green,GTC.getInstance().zyc_photo));
		rols.add(RoleFactory.getInstance().getFighter("enemy2", Type.ENEMY, QUALITY.green,GTC.getInstance().zyc_photo));
		rols.add(RoleFactory.getInstance().getFighter("enemy3", Type.ENEMY, QUALITY.green,GTC.getInstance().zyc_photo));

//		gs.game_init(rols);
	}

	private void makeGameScreen2(GameScreen gs) {
//		roles.clear();
//		heroRoles.clear();
//		npcRoles.clear();
////		Array<Role> heroRoles = new Array<Role>();
////		Array<Role> npcRoles = new Array<Role>();
//		
//		Array<Role> pfr =  Player.getInstance().playerFightRole;
//		for(int i=0;i<pfr.size;i++){
//			heroRoles.add(pfr.get(i));
//		}
////		heroRoles.add(Player.getInstance().playerFightRole.get(1));
////		npcRoles.add(RoleFactory.getInstance().getFighter("enemy1", Type.ENEMY, QUALITY.green,GameTextureClass.getInstance().h5_photo));
//		npcRoles.add(new Role(Role.Type.ENEMY,QUALITY.green,CLASSES.fighter,"测试敌人1",U.getRandom(100, -6, 6),8,10
//				,EquipFactory.getInstance().getWeaponByIdx(1),EquipFactory.getInstance().getArmorByIdx(1),
//				new SkillTree().getSkillTreeFixedSkill(10)
//				,GTC.getInstance().zyc_photo));				//测试技能用
////		npcRoles.add(new Role(Role.Type.ENEMY,QUALITY.green,CLASSES.fighter,"测试敌人2",U.getRandom(100, -6, 6),8,10
////				,EquipFactory.getInstance().getWeaponByIdx(1),EquipFactory.getInstance().getArmorByIdx(1),
////				new SkillTree().getSkillTreeFixedSkill(10)
////				,GameTextureClass.getInstance().h3_photo));				//测试技能用
////		npcRoles.add(new Role(Role.Type.ENEMY,QUALITY.green,CLASSES.fighter,"测试敌人3",U.getRandom(100, -6, 6),8,10
////				,EquipFactory.getInstance().getWeaponByIdx(1),EquipFactory.getInstance().getArmorByIdx(1),
////				new SkillTree().getSkillTreeFixedSkill(10)
////				,GameTextureClass.getInstance().h3_photo));				//测试技能用
//		roles.addAll(heroRoles);
//		roles.addAll(npcRoles);
////		gs.game_init(heroRoles,npcRoles);
	}

}

