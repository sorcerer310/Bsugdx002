package com.bsu.obj;

import com.badlogic.gdx.utils.Array;
import com.bsu.make.EquipFactory;
import com.bsu.make.RoleFactory;
import com.bsu.obj.Role.Type;
import com.bsu.obj.skilltree.SkillTree;
import com.bsu.tools.GC.CLASSES;
import com.bsu.tools.GC.QUALITY;
import com.bsu.tools.GTC;
import com.bsu.tools.U;

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
	public Array<Role> playerFightRole = new Array<Role>();// 玩家拥有的出战英雄
	public Array<Role> playerIdelRole = new Array<Role>();// 玩家背�
	public int crystal_blue = 0;
	public int crystal_purple = 0;// 紫色技能碎片数量
	public int crystal_orange = 0;// 橙色技能碎片数量

	private Player() {
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
			RoleFactory rf = RoleFactory.getInstance();
			playerRole.add(rf.getFighter("fc", Role.Type.HERO, QUALITY.green,
					GTC.getInstance().fc_photo,new int[]{1,2}));
			playerRole.add(rf.getFighter("很好", Type.HERO, QUALITY.green,
					GTC.getInstance().zyc_photo,new int[]{1,2}));

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
		if (playerFightRole.size == 0){
			playerFightRole.add(playerRole.get(0));
			playerFightRole.add(playerRole.get(1));
		}
		return playerFightRole;
	}

	/**
	 * 添加新Role
	 * @param r
	 * @param index
	 */
	public void addRole(Array<Role> roles) {
		for(Role r:roles){
			playerRole.add(r);
		}
		resetRoleArray(playerRole);
		getPlayerPackageRole();
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
	 * 更换出战数组中元素
	 * 
	 * @param roles
	 * @param r
	 * @param index
	 */
	public void addRoleToFIght(Role r, int index) {
		for (Role e : playerFightRole) {
			if (e.equals(r)) {
				return;
			}
		}
		if (index > playerFightRole.size) {
			playerFightRole.add(r);
		} else {
			playerFightRole.insert(index, r);
		}
	}

	/**
	 * 取得某一个数组中的某一品质的  card
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
	 * 
	 * @param roles
	 *            带入的角色数组
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
