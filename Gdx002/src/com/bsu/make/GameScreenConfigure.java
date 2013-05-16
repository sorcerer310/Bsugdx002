package com.bsu.make;

import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Role;
import com.bsu.screen.GameScreen;

public class GameScreenConfigure {
	private static GameScreenConfigure instance = null;
	private GameScreenConfigure(){}
	public static GameScreenConfigure getInstance(){
		if(instance==null)
			instance = new GameScreenConfigure();
		return instance;
	}
	
	public void makeGameScreen1(GameScreen gs){		
		Array<Role>  rols = new Array<Role>();
		rols.add(RoleFactory.getInstance().getHeroRole("hero1"));
		rols.add(RoleFactory.getInstance().getHeroRole2("hero2"));
		rols.add(RoleFactory.getInstance().getHeroRole2("hero3"));
		rols.add(RoleFactory.getInstance().getEnemyRole("enemy1"));
		rols.add(RoleFactory.getInstance().getEnemyRole("enemy2"));
		rols.add(RoleFactory.getInstance().getEnemyRole("enemy3"));
		gs.game_init(0,rols);
	}
}
