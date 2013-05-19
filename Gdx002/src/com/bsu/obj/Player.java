package com.bsu.obj;

import com.badlogic.gdx.utils.Array;
import com.bsu.make.RoleFactory;
import com.bsu.make.CardFactory.SUBTYPE;
import com.bsu.tools.Configure.QUALITY;

/**
 * 玩家类，封装了玩家的所有信息
 * 
 * @author zhangyongchen
 * 
 */
public class Player {

	private static Player instance = null;

	public static Player getInstance() {
		if (instance == null)
			instance = new Player();
		return instance;
	}

	private int money;// 玩家金钱
	private Array<Role> playerRole = new Array<Role>();// 玩家拥有的role
	private Array<Card> playerCard = new Array<Card>();// 玩家拥有的卡片

	public Player() {
		// TODO Auto-generated constructor stub
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public Array<Role> getPlayerRole() {
		if (playerRole.size == 0) {
			playerRole.add(new Role(new Card(SUBTYPE.fc, QUALITY.normal)
					.getRoleValue()));
		}
		return playerRole;
	}

	public Array<Card> getPlayerCard() {
		return playerCard;
	}
}
