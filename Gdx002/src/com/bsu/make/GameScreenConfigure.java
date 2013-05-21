package com.bsu.make;

import com.badlogic.gdx.utils.Array;
import com.bsu.make.CardFactory.SUBTYPE;
import com.bsu.obj.Card;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.screen.GameScreen;
import com.bsu.tools.Configure.QUALITY;

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
		rols.add(RoleFactory.getInstance().getHeroRole("hero1"));
		rols.add(RoleFactory.getInstance().getHeroRole2("hero2"));
		rols.add(RoleFactory.getInstance().getHeroRole2("hero3"));
		rols.add(RoleFactory.getInstance().getEnemyRole("enemy1"));
		rols.add(RoleFactory.getInstance().getEnemyRole("enemy2"));
		rols.add(RoleFactory.getInstance().getEnemyRole("enemy3"));
		gs.game_init(0, rols);
	}

	public void makeGameScreen2(GameScreen gs) {
		rols.clear();
		heroRoles.clear();
		npcRoles.clear();
		for(Role h:Player.getInstance().playerFightRole){
			heroRoles.add(h);
		}
		npcRoles.add(new Role(new Card(SUBTYPE.enemy, QUALITY.green)
				.getRoleValue()));
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
