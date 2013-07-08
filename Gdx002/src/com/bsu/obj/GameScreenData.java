package com.bsu.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.badlogic.gdx.utils.Array;
import com.bsu.make.ItemFactory;
import com.bsu.make.MapNpcsFactory;
import com.bsu.make.MapNpcsFactory.NpcsPlan;

/**
 * 关卡的数据 
 * @author fengchong
 *
 */
public class GameScreenData{
	private int id = 0;											//关卡的索引,0为教学关卡
	private Array<Role> roles=new Array<Role>(); 				//出战的所有人物
	private Array<Role> heroRoles=new Array<Role>();			//出战的所有英雄
	private Array<Role> npcRoles=new Array<Role>();				//出战的所有敌人
	private String mapName = "";								//对应地图名称 
	private Array<Reward> reward = new Array<Reward>();			//地图奖励
	private MapNpcsFactory.NpcsPlan nplan = NpcsPlan.PLAN1;		//出兵计划使用plan1
	
	public GameScreenData(){
		//构造函数中加入玩家上场的英雄
		Array<Role> fightRole = Player.getInstance().getPlayerFightRole();
		for (Role r : fightRole)
			heroRoles.add(r);
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

	public Array<Role> getNpcRoles() {
		return npcRoles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public Array<Reward> getReward() {
		return reward;
	}
	/**
	 * 设置通关奖励
	 * @param reward	通关的奖励对象 
	 */
	public void setReward(Array<Reward> reward) {
		this.reward = reward;
	}

	

	public MapNpcsFactory.NpcsPlan getNplan() {
		return nplan;
	}

	public void setNplan(MapNpcsFactory.NpcsPlan nplan) {
		this.nplan = nplan;
	}
}

