package com.bsu.obj;

import com.badlogic.gdx.utils.Array;

/**
 * 玩家类，封装了玩家的所有信息
 * 
 * @author zhangyongchen
 * 
 */
public class Player {
	private int money;//玩家金钱
	Array<Role> playerRole = new Array<Role>();//玩家拥有的role
	Array<Card> playerCard = new Array<Card>();//玩家拥有的卡片

	public Player() {
		// TODO Auto-generated constructor stub
	}
}
