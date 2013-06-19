package com.bsu.make;

import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Player;
import com.bsu.obj.Reward;
import com.bsu.obj.Role;
import com.bsu.obj.Role.Type;
import com.bsu.screen.GameScreen;
import com.bsu.tools.GC.QUALITY;
import com.bsu.tools.GTC;
import com.bsu.obj.GameScreenData;

public class GameScreenFactory {
	private static GameScreenFactory instance = null;

	private GameScreenFactory() {
	}

	public static GameScreenFactory getInstance() {
		if (instance == null)
			instance = new GameScreenFactory();
		return instance;
	}

	public GameScreenData gsd = null;

	/**
	 * 根据索引生成一个关卡
	 * 
	 * @param id
	 * @return
	 */
	public GameScreenData makeGameScreen(int id) {
		switch (id) {
		case 0:
			gsd = makeGameScreenTeaching();
			break;
		default:
			gsd = makeGameScreenLv(id);
			break;
		}
		return gsd;
	}

	/**
	 * 教学关卡
	 * 
	 * @param gs
	 */
	private GameScreenData makeGameScreenTeaching() {
		GameScreenData gsd = new GameScreenData();
		gsd.setId(0);
		gsd.setMapName("teaching");
		// 加入玩家的上场英雄
		for (Role r : Player.getInstance().playerFightRole)
			gsd.getHeroRoles().add(r);
		// 加入敌人
		gsd.getNpcRoles().add(
				RoleFactory.getInstance().getFighter("NPC", Type.ENEMY,
						QUALITY.green, GTC.getInstance().zyc_photo, 3));
		gsd.getNpcRoles().add(
				RoleFactory.getInstance().getFighter("NPC", Type.ENEMY,
						QUALITY.green, GTC.getInstance().zyc_photo, 5));
		Array<Reward> reward = new Array<Reward>();
		reward.add(new Reward(ItemFactory.getInstance().getItemById(1), 1.0f));
		reward.add(new Reward(ItemFactory.getInstance().getItemById(101), 1.0f));
		gsd.setReward(reward);
		return gsd;
	}

	private GameScreenData makeGameScreenLv(int lv) {
		GameScreenData gsd = new GameScreenData();
		Array<Reward> reward = new Array<Reward>();
		RoleFactory rf = RoleFactory.getInstance();
		ItemFactory fy = ItemFactory.getInstance();
		gsd.setId(lv + 1);
		gsd.setMapName("" + (lv + 1));
		// 加入玩家的上场英雄
		for (Role r : Player.getInstance().playerFightRole)
			gsd.getHeroRoles().add(r);
		if (lv == 1)
			setLv(gsd, reward, rf, fy, new int[] { 3, 0, 0, 0 }, null, null,
					null, null, new int[] { 2, 0, 0, 0 }, null, null, null,
					null, 1, 0, 0);
		if (lv == 2)
			setLv(gsd, reward, rf, fy, new int[] { 4, 0, 0, 0 }, null, null,
					null, null, new int[] { 3, 0, 0, 0 }, null, null, null,
					null, 1, 0, 0);
		if (lv == 3)
			setLv(gsd, reward, rf, fy, new int[] { 5, 0, 0, 0 }, null, null,
					null, null, new int[] { 3, 0, 0, 0 }, null, null, null,
					null, 1, 0, 0);
		if (lv == 4)
			setLv(gsd, reward, rf, fy, new int[] { 10, 0, 0, 0 }, null, null,
					null, null, new int[] { 5, 0, 0, 0 }, null, null, null,
					null, 1, 0, 0);
		gsd.setReward(reward);
		return gsd;
	}

	/**
	 * 关卡数据,int数组大小为4
	 * 
	 * @param fe本关卡NPC战士数量
	 * @param we本关卡NPC元素数量
	 * @param se本关卡NPC黑暗数量
	 * @param ae本关卡NPC弓手数量
	 * @param ce本关卡NPC牧师数量
	 * @param fg本关通关获得战士数量
	 * @param wg本关通关获得元素数量
	 * @param sg本关通关获得黑暗数量
	 * @param ag本关通关获得弓手数量
	 * @param cg本关通关获得牧师数量
	 * @param pj本关通关或者普通精华数量
	 * @param gj本关通关或者高级精华数量
	 * @param sj本关通关或者史诗精华数量
	 */
	private void setLv(GameScreenData gsd, Array<Reward> reward,
			RoleFactory rf, ItemFactory fy, int[] fe, int[] we, int[] se,
			int[] ae, int[] ce, int[] fg, int[] wg, int[] sg, int[] ag,
			int[] cg, int pj, int gj, int sj) {
		fe = targetInt(fe);
		we = targetInt(we);
		se = targetInt(se);
		ae = targetInt(ae);
		ce = targetInt(ce);
		fg = targetInt(fg);
		wg = targetInt(wg);
		sg = targetInt(sg);
		cg = targetInt(cg);
		ag = targetInt(ag);
		getNpcRole(gsd, rf, fe, we, se, ae, ce);
		getRoleVictory(gsd, reward, fy, fg, wg, sg, cg, ag, pj, gj, sj);
	}

	// 取得出战NPC
	private void getNpcRole(GameScreenData gsd, RoleFactory rf, int[] fe,
			int[] we, int[] se, int[] ae, int[] ce) {
		for (int i = 0; i < 4; i++) {
			for (int k = 0; k < fe[i]; k++) {
				gsd.getNpcRoles().add(
						rf.getFighter("NPC", Type.ENEMY, getQuality(i),
								GTC.getInstance().zyc_photo));
			}
			for (int k = 0; k < we[i]; k++) {
				gsd.getNpcRoles().add(
						rf.getWizard("NPC", Type.ENEMY, getQuality(i),
								GTC.getInstance().zyc_photo));
			}
			for (int k = 0; k < se[i]; k++) {
				gsd.getNpcRoles().add(
						rf.getSorcerer("NPC", Type.ENEMY, getQuality(i),
								GTC.getInstance().zyc_photo));
			}
			for (int k = 0; k < ae[i]; k++) {
				gsd.getNpcRoles().add(
						rf.getArcher("NPC", Type.ENEMY, getQuality(i),
								GTC.getInstance().zyc_photo));
			}
			for (int k = 0; k < ce[i]; k++) {
				gsd.getNpcRoles().add(
						rf.getCleric("NPC", Type.ENEMY, getQuality(i),
								GTC.getInstance().zyc_photo));
			}
		}
	}

	// 取得某种通关卡片,精华
	private void getRoleVictory(GameScreenData gsd, Array<Reward> reward,
			ItemFactory fy, int[] fg, int[] wg, int[] sg, int[] cg, int[] ag,
			int a, int b, int c) {
		for (int i = 0; i < 4; i++) {
			for (int k = 0; k < fg[i]; k++) {
				reward.add(new Reward(fy.getItemById(101 + i), 1.0f));
			}
			for (int k = 0; k < wg[i]; k++) {
				reward.add(new Reward(fy.getItemById(109 + i), 1.0f));
			}
			for (int k = 0; k < sg[i]; k++) {
				reward.add(new Reward(fy.getItemById(113 + i), 1.0f));
			}
			for (int k = 0; k < cg[i]; k++) {
				reward.add(new Reward(fy.getItemById(117 + i), 1.0f));
			}
			for (int k = 0; k < ag[i]; k++) {
				reward.add(new Reward(fy.getItemById(105 + i), 1.0f));
			}
		}
		for (int i = 0; i < a; i++) {
			reward.add(new Reward(fy.getItemById(1), 1.0f));
		}
		for (int i = 0; i < b; i++) {
			reward.add(new Reward(fy.getItemById(2), 1.0f));
		}
		for (int i = 0; i < c; i++) {
			reward.add(new Reward(fy.getItemById(3), 1.0f));
		}
		gsd.setReward(reward);
	}

	// 返回品质
	private QUALITY getQuality(int index) {
		QUALITY q = null;
		if (index == 0)
			q = QUALITY.green;
		if (index == 1)
			q = QUALITY.blue;
		if (index == 2)
			q = QUALITY.purple;
		if (index == 3)
			q = QUALITY.orange;
		return q;
	}

	// 返回数组
	private int[] targetInt(int[] a) {
		int[] b = null;
		if (a != null) {
			b = a;
		} else {
			b = new int[] { 0, 0, 0, 0 };
		}
		return b;
	}
}
