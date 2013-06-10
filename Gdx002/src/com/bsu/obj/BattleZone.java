package com.bsu.obj;

import com.badlogic.gdx.utils.Array;

/**
 * 记录战场区域信息
 * @author fengchong
 *
 */
public class BattleZone {
	private Array<Battle> battles = new Array<Battle>();	//一个战场区域包括多个战场
}

/**
 * 记录战场信息
 * @author fengchong
 *
 */
class Battle{
	private Array<Role> npcs = new Array<Role>();			//该战场的敌人
	private String map = "";								//地图名称
	private int reward_gold;								//通关后的奖励金钱
	private Array<Role> reward_cards = new Array<Role>();	//通关后的奖励人物卡片
	
}