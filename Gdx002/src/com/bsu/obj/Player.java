package com.bsu.obj;

import com.badlogic.gdx.utils.Array;
import com.bsu.make.RoleFactory;
import com.bsu.make.CardFactory.SUBTYPE;
import com.bsu.screen.UpdateScreen.QualityS;
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
	private Array<Role> playerWhiteRole = new Array<Role>();// 玩家拥有的role
	private Array<Role> playerGreenRole = new Array<Role>();// 玩家拥有的role
	private Array<Role> playerBlueRole = new Array<Role>();// 玩家拥有的role
	private Array<Role> playerPurpleRole = new Array<Role>();// 玩家拥有的role
	private Array<Role> playerOrangeRole = new Array<Role>();// 玩家拥有的role
	private Array<Role> playerFightRole = new Array<Role>();// 玩家拥有的卡片

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
			playerRole.add(new Role(new Card(SUBTYPE.fc, QUALITY.green)
					.getRoleValue()));
			playerRole.add(new Role(new Card(SUBTYPE.zyc, QUALITY.white)
					.getRoleValue()));
			playerRole.add(new Role(new Card(SUBTYPE.fc, QUALITY.green)
					.getRoleValue()));
			playerRole.add(new Role(new Card(SUBTYPE.zyc, QUALITY.orange)
					.getRoleValue()));
			playerRole.add(new Role(new Card(SUBTYPE.fc, QUALITY.green)
					.getRoleValue()));
			playerRole.add(new Role(new Card(SUBTYPE.fc, QUALITY.green)
					.getRoleValue()));
			playerRole.add(new Role(new Card(SUBTYPE.fc, QUALITY.green)
					.getRoleValue()));
			playerRole.add(new Role(new Card(SUBTYPE.fc, QUALITY.green)
					.getRoleValue()));
			playerRole.add(new Role(new Card(SUBTYPE.fc, QUALITY.green)
					.getRoleValue()));
		}
		RoleGetQuality(playerWhiteRole, playerRole, QUALITY.white);
		RoleGetQuality(playerGreenRole, playerRole, QUALITY.green);
		RoleGetQuality(playerBlueRole, playerRole, QUALITY.blue);
		RoleGetQuality(playerPurpleRole, playerRole, QUALITY.purple);
		RoleGetQuality(playerOrangeRole, playerRole, QUALITY.orange);
		resetRoleArray(playerRole);
		return playerRole;
	}

	/**
	 * 取得出战role数组
	 * 
	 * @return
	 */
	public Array<Role> getPlayerFightRole() {
		if (playerFightRole.size == 0) {
			playerFightRole.add(getPlayerRole().get(0));
			playerFightRole.add(getPlayerRole().get(1));
			playerFightRole.add(getPlayerRole().get(2));
			playerFightRole.add(getPlayerRole().get(3));
			playerFightRole.add(getPlayerRole().get(4));
		}
		return playerFightRole;
	}

	/**
	 * 根据等级的优先顺序排列 参数为同品质的数组
	 */
	private void resetRoleArray(Array<Role> roles) {
		int i, j;
		// n个元素的数组进行n-1轮排序
		for (i = 0; i < roles.size - 1; i++) {
			// 因为每一轮循环将确定一个数组元素的位置,
			// 所以每一轮的比较次数将会递减
			for (j = 0; j < roles.size - i - 1; j++) {
				if (QualityInde(roles.get(j)) < QualityInde(roles.get(j + 1))) {
					// 如果第j个元素比它后面的相邻的元素小的话就交换
					roles.swap(j, j+1);
				}else{
					if (QualityInde(roles.get(j)) == QualityInde(roles.get(j + 1))) {
						if(roles.get(j).level<roles.get(j+1).level){
							roles.swap(j, j+1);
						}
					}
				}
			}
		}
	}

	/**
	 * 根据role品质添加到品质数组
	 * 
	 * @param r
	 */
	private void RoleGetQuality(Array<Role> targetArray,
			Array<Role> sourceAarray, QUALITY q) {
		for (Role e : sourceAarray) {
			if (e.quality == q) {
				targetArray.add(e);
			}
		}
	}
	/**
	 * 根据role品质返回一个int,品质越高，int 越高
	 * @return
	 */
	private int QualityInde(Role r){
		int index=0;
		if(r.quality==QUALITY.white){
			index=0;
		}
		if(r.quality==QUALITY.green){
			index=1;
		}
		if(r.quality==QUALITY.blue){
			index=2;
		}
		if(r.quality==QUALITY.purple){
			index=3;
		}
		if(r.quality==QUALITY.orange){
			index=4;
		}
	
		return index;
	}
}
