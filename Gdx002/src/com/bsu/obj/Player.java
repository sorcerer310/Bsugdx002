package com.bsu.obj;

import com.badlogic.gdx.utils.Array;
import com.bsu.make.RoleFactory;
import com.bsu.make.CardFactory.SUBTYPE;
import com.bsu.tools.Configure.QUALITY;
import com.bsu.tools.Configure.QualityS;

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
	public Array<Role> playerRole = new Array<Role>();// 玩家拥有的role
	public Array<Role> playerFightRole = new Array<Role>();// 玩家拥有的出战卡片
	public Array<Role> playerIdelRole = new Array<Role>();// 玩家背包中未出战卡片

	public Player() {
		// TODO Auto-generated constructor stub
		getPlayerRole();
		getPlayerFightRole();
		getPlayerPackageRole();
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * 取得所有卡片
	 * 
	 * @return
	 */
	private Array<Role> getPlayerRole() {
		if (playerRole.size == 0) {
			playerRole.add(new Role(new Card(SUBTYPE.fc, QUALITY.green)
					.getRoleValue()));
			playerRole.add(new Role(new Card(SUBTYPE.zyc, QUALITY.green)
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
			playerRole.add(new Role(new Card(SUBTYPE.zyc, QUALITY.orange)
					.getRoleValue()));
			playerRole.add(new Role(new Card(SUBTYPE.zyc, QUALITY.blue)
					.getRoleValue()));
			playerRole.add(new Role(new Card(SUBTYPE.zyc, QUALITY.blue)
					.getRoleValue()));
			playerRole.add(new Role(new Card(SUBTYPE.zyc, QUALITY.blue)
					.getRoleValue()));
			playerRole.add(new Role(new Card(SUBTYPE.zyc, QUALITY.green)
					.getRoleValue()));
			playerRole.add(new Role(new Card(SUBTYPE.fc, QUALITY.purple)
					.getRoleValue()));
		}
		resetRoleArray(playerRole);
		return playerRole;
	}

	/**
	 * 取得出战role数组
	 * 
	 * @return
	 */
	private Array<Role> getPlayerFightRole() {
		if (playerFightRole.size == 0) {
			playerFightRole.add(playerRole.get(0));
			playerFightRole.add(playerRole.get(1));
			playerFightRole.add(playerRole.get(2));
			playerFightRole.add(playerRole.get(3));
			playerFightRole.add(playerRole.get(4));
		}
		return playerFightRole;
	}

	/**
	 * 取得背包中的卡片
	 * 
	 * @return
	 */
	public Array<Role> getPlayerPackageRole() {
		playerIdelRole.clear();
		for (Role r : playerRole) {
			boolean flag = false;
			for (Role e : playerFightRole) {
				if (e.equals(r)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				playerIdelRole.add(r);
			}
		}
		return playerIdelRole;
	}

	/**
	 * 取得某一个数组中的某一品质的card
	 * 
	 * @param q
	 *            某品质
	 * @return
	 */
	public Array<Role> getQualityRole(Array<Role> ar, QUALITY q) {
		Array<Role> qa = new Array<Role>();
		for (Role r : ar) {
			if (r.quality == q) {
				qa.add(r);
			}
		}
		return qa;
	}

	/**
	 * 根据等级的优先顺序排列 参数为同品质的数组
	 */
	public void resetRoleArray(Array<Role> roles) {
		int i, j;
		// n个元素的数组进行n-1轮排序
		for (i = 0; i < roles.size - 1; i++) {
			// 因为每一轮循环将确定一个数组元素的位置,
			// 所以每一轮的比较次数将会递减
			for (j = 0; j < roles.size - i - 1; j++) {
				if (QualityInde(roles.get(j)) < QualityInde(roles.get(j + 1))) {
					// 如果第j个元素比它后面的相邻的元素小的话就交换
					roles.swap(j, j + 1);
				} else {
					if (QualityInde(roles.get(j)) == QualityInde(roles
							.get(j + 1))) {
						if (roles.get(j).level < roles.get(j + 1).level) {
							roles.swap(j, j + 1);
						}
					}
				}
			}
		}
	}

	/**
	 * 根据role品质返回一个int,品质越高，int 越高
	 * 
	 * @return
	 */
	private int QualityInde(Role r) {
		int index = 0;
		if (r.quality == QUALITY.green) {
			index = 0;
		}
		if (r.quality == QUALITY.blue) {
			index = 1;
		}
		if (r.quality == QUALITY.purple) {
			index = 2;
		}
		if (r.quality == QUALITY.orange) {
			index = 3;
		}

		return index;
	}
}
