package com.bsu.obj;

import com.badlogic.gdx.utils.Array;
import com.bsu.make.RoleFactory;
import com.bsu.obj.Role.BATLESTATE;
import com.bsu.obj.Role.Type;
import com.bsu.tools.GC.QUALITY;
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
	
	/**
	 * 此函数只能在加载游戏时使用，其他情况严禁使用
	 * @param p
	 */
	public static void loadPlayer(Player p){
		instance = p;
	}
	
	private int money;// 玩家金钱
	private Array<Role> playerRole = new Array<Role>();// 玩家拥有的role
	public int crystal_blue = 300;//蓝色技能碎片(普通)
	public int crystal_purple = 300;// 紫色技能碎片数量（高级）
	public int crystal_orange = 300;// 橙色技能碎片数量（史诗）

	private Player() {
		initPlayerRole();
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
	private Array<Role> initPlayerRole() {
		if (playerRole.size == 0) {
			RoleFactory rf = RoleFactory.getInstance();
			playerRole.add(rf.getFighter("很好", Type.HERO, QUALITY.green,
					"zyc_photo",new int[]{2,1}));
			playerRole.add(rf.getFighter("很好", Type.HERO, QUALITY.green,
					"zyc_photo",new int[]{101,102}));
			playerRole.add(rf.getFighter("很好", Type.HERO, QUALITY.green,
					"zyc_photo",new int[]{1,40}));
			playerRole.add(rf.getFighter("很好", Type.HERO, QUALITY.green,
					"zyc_photo",new int[]{1,40}));
		}
		return playerRole;
	}
	//取得总卡片
	public Array<Role> getRole(){
		orderRoleArray(playerRole);
		return playerRole;
	}
	/**
	 * 获得出战role数组
	 * @return
	 */
	public Array<Role> getPlayerFightRole() {
		Array<Role> fightRole = new Array<Role>();
		Array<Role> arr=getRole();
		for(int i=0;i<arr.size;i++){
			Role r = arr.get(i);
			if(r.bstate==BATLESTATE.FIGHT)
				fightRole.add(r);
		}
		return fightRole;
	}
	/**
	 * 获得闲置的role数组
	 * @return
	 */
	public Array<Role> getPlayerIdelRole(){
		Array<Role> idleRole = new Array<Role>();
		Array<Role> arr=getRole();
		for(int i=0;i<arr.size;i++){
			Role r = arr.get(i);
			if(r.bstate==BATLESTATE.IDLE)
				idleRole.add(r);
		}
		return idleRole;
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
			if(q==QUALITY.all){
				qa.add(r);
				continue;
			}
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
	public void orderRoleArray(Array<Role> roles) {
		int i, j;
		// n个元素的数组进行n-1轮排序
		for (i = 0; i < roles.size - 1; i++) {
			// 因为每一轮循环将确定一个数组元素的位置,
			// 所以每一轮的比较次数将会递减
			for (j = 0; j < roles.size - i - 1; j++) {
				if (U.QualityInde(roles.get(j)) < U.QualityInde(roles.get(j + 1))) {
					// 如果第j个元素比它后面的相邻的元素小的话就交换
					roles.swap(j, j + 1);
				} else {
					if (U.QualityInde(roles.get(j)) == U.QualityInde(roles
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
	 * 给玩家增加一个物品
	 * @param itemid	物品id
	 */
	public void giveItem(int itemid){
		switch(itemid){
		case 1:			
			crystal_blue++;				//蓝色品质碎片
			break;
		case 2:
			crystal_purple++;			//紫色品质碎片
			break;
		case 3:
			crystal_orange++;			//橙色品质碎片
			break;
		case 4:
			money++;					//金币
			break;
		case 101://绿色战士卡片
			this.playerRole.add(RoleFactory.getInstance().getFighter("战士", Type.HERO, QUALITY.green, "zyc_photo"));
			break; 
		case 102://蓝色战士卡片
			this.playerRole.add(RoleFactory.getInstance().getFighter("战士", Type.HERO, QUALITY.blue, "zyc_photo"));
			break;
		case 103://紫色战士卡片
			this.playerRole.add(RoleFactory.getInstance().getFighter("战士", Type.HERO, QUALITY.purple, "zyc_photo"));
			break;
		case 104://橙色战士卡片
			this.playerRole.add(RoleFactory.getInstance().getFighter("战士", Type.HERO, QUALITY.orange, "zyc_photo"));
			break;
		case 105:
			this.playerRole.add(RoleFactory.getInstance().getArcher("射手", Type.HERO, QUALITY.green, "zyc_photo"));
			break;
		case 106:
			this.playerRole.add(RoleFactory.getInstance().getArcher("射手", Type.HERO, QUALITY.blue, "zyc_photo"));
			break;
		case 107:
			this.playerRole.add(RoleFactory.getInstance().getArcher("射手", Type.HERO, QUALITY.purple, "zyc_photo"));
			break;
		case 108:
			this.playerRole.add(RoleFactory.getInstance().getArcher("射手", Type.HERO, QUALITY.orange, "zyc_photo"));
			break;
		case 109:
			this.playerRole.add(RoleFactory.getInstance().getWizard("元素法师", Type.HERO, QUALITY.green, "zyc_photo"));
			break;
		case 110:
			this.playerRole.add(RoleFactory.getInstance().getWizard("元素法师", Type.HERO, QUALITY.blue, "zyc_photo"));
			break;
		case 111:
			this.playerRole.add(RoleFactory.getInstance().getWizard("元素法师", Type.HERO, QUALITY.purple, "zyc_photo"));
			break;
		case 112:
			this.playerRole.add(RoleFactory.getInstance().getWizard("元素法师", Type.HERO, QUALITY.orange, "zyc_photo"));
			break;
		case 113:
			this.playerRole.add(RoleFactory.getInstance().getSorcerer("黑暗法师", Type.HERO, QUALITY.green, "zyc_photo"));
			break;
		case 114:
			this.playerRole.add(RoleFactory.getInstance().getSorcerer("黑暗法师", Type.HERO, QUALITY.blue, "zyc_photo"));
			break;
		case 115:
			this.playerRole.add(RoleFactory.getInstance().getSorcerer("黑暗法师", Type.HERO, QUALITY.purple, "zyc_photo"));
			break;
		case 116:
			this.playerRole.add(RoleFactory.getInstance().getSorcerer("黑暗法师", Type.HERO, QUALITY.orange, "zyc_photo"));
			break;
		case 117:
			this.playerRole.add(RoleFactory.getInstance().getSorcerer("牧师", Type.HERO, QUALITY.green, "zyc_photo"));
			break;
		case 118:
			this.playerRole.add(RoleFactory.getInstance().getSorcerer("牧师", Type.HERO, QUALITY.blue, "zyc_photo"));
			break;
		case 119:
			this.playerRole.add(RoleFactory.getInstance().getSorcerer("牧师", Type.HERO, QUALITY.purple, "zyc_photo"));
			break;
		case 120:
			this.playerRole.add(RoleFactory.getInstance().getSorcerer("牧师", Type.HERO, QUALITY.orange, "zyc_photo"));
			break;
		}
	}
}
