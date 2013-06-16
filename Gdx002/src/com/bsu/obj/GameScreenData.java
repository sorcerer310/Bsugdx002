package com.bsu.obj;

import com.badlogic.gdx.utils.Array;

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

	public void setReward(Array<Reward> reward) {
		this.reward = reward;
	}
}

