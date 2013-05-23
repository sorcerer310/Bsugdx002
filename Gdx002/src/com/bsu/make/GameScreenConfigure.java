package com.bsu.make;

import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.obj.Role.Type;
import com.bsu.screen.GameScreen;
import com.bsu.tools.Configure.QUALITY;
import com.bsu.tools.GameTextureClass;

public class GameScreenConfigure {
	private static GameScreenConfigure instance = null;

	private GameScreenConfigure() {
	}

	public static GameScreenConfigure getInstance() {
		if (instance == null)
			instance = new GameScreenConfigure();
		return instance;
	}
	private Array<Role> rols=new Array<Role>(); 
	private Array<Role> heroRoles=new Array<Role>();
	private Array<Role> npcRoles=new Array<Role>();
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
		rols.clear();
		heroRoles.clear();
		npcRoles.clear();
		for(Role h:Player.getInstance().playerFightRole){
			heroRoles.add(h);
		}
		npcRoles.add(RoleFactory.getInstance().getFighter("enemy1", Type.ENEMY, QUALITY.green,GameTextureClass.getInstance().h5_photo));
		rols.addAll(heroRoles);
		rols.addAll(npcRoles);
		gs.game_init(0, rols);
	}

	public Array<Role> getRols() {
		rols.clear();
		rols.addAll(heroRoles);
		rols.addAll(npcRoles);
		return rols;
	}

	public Array<Role> getHeroRoles() {
		return heroRoles;
	}

	public void setHeroRoles(Array<Role> heroRoles) {
		this.heroRoles = heroRoles;
	}
}
