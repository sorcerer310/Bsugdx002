package com.bsu.make;

import java.io.IOException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.bsu.make.MapNpcsFactory.NpcsPlan;
import com.bsu.obj.Player;
import com.bsu.obj.Reward;
import com.bsu.obj.Role;
import com.bsu.obj.Role.Type;
import com.bsu.obj.data.GameScreenData;
import com.bsu.tools.GC.CLASSES;
import com.bsu.tools.GC.QUALITY;
import com.bsu.tools.U;

public class GameScreenFactory {
	private static GameScreenFactory instance = null;
	private Element xroot = null;						//xml文件的根结点
	private HashMap<Integer,GameScreenData> hm_gsd = new HashMap<Integer,GameScreenData>(); 
	private GameScreenFactory() {
		XmlReader reader = new XmlReader();
		try {
			//解析xml文件获得根节点
			xroot = reader.parse(Gdx.files.internal("data/game/battle.xml"));
			//将xml解析为关卡数据
			Array<Element> maps = xroot.getChildrenByName("map");
			for(int i=0;i<maps.size;i++){
				GameScreenData gsd = new GameScreenData();
				Element map = maps.get(i);
				gsd.setId(map.getInt("id"));//地图ID
				gsd.setMapName(map.getAttribute("name"));					//地图名称
				gsd.setNplan(map.getAttribute("plan"));						//刷怪方案
				
				Array<Element> npcs = map.getChildrenByName("npcs");		//敌人
				for(int ni=0;ni<npcs.size;ni++){
					Element npc = npcs.get(ni);
					//获得该npc是否有count属性，如果有则增加该数量的该npc，否则默认增加1个
					int count = npc.getInt("count", 1);
					for(int c=0;c<count;c++)
						gsd.getNpcRoles().add(RoleFactory.getInstance().getRole(
							npc.getAttribute("classes"),
							npc.getAttribute("name"),
							Type.ENEMY,
							U.str2Quality(npc.getAttribute("quality")),
							npc.getAttribute("icon"), 
							npc.getInt("skill")
							));
				}
				
				Array<Element> rewards = map.getChildrenByName("reward");	//通关后的奖励
				for(int ri=0;ri<rewards.size;ri++){
					Element reward = rewards.get(ri);
					int count = reward.getInt("count", 1);
					for(int c=0;c<count;c++)
						gsd.getReward().add(new Reward(reward.getInt("itemid"),
							reward.getFloat("prob")
							));
				}
				hm_gsd.put(gsd.getId(), gsd);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static GameScreenFactory getInstance() {
		if (instance == null)
			instance = new GameScreenFactory();
		return instance;
	}

	/**
	 * 根据索引生成一个关卡
	 * 
	 * @param id
	 * @return
	 */
	public GameScreenData makeGameScreen(int id) {
		GameScreenData gsd = hm_gsd.get(id);		
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
		
		// 加入敌人
		gsd.getNpcRoles().add(RoleFactory.getInstance().getFighter("NPC", Type.ENEMY,QUALITY.green, "p_fighter", 3));
		gsd.getNpcRoles().add(RoleFactory.getInstance().getFighter("NPC", Type.ENEMY,QUALITY.green, "p_fighter", 5));

		//设置奖励物品及出现机率
		Array<Reward> reward = new Array<Reward>();
		reward.add(new Reward(1, 1.0f));
		reward.add(new Reward(1, 1.0f));
		reward.add(new Reward(101, 1.0f));
		gsd.setReward(reward);
		
		//设置敌人出现的方式
		gsd.setNplan(NpcsPlan.PLAN1);
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
		Array<Role> fightRole = Player.getInstance().getPlayerFightRole();
		for (Role r : fightRole)
			gsd.getHeroRoles().add(r);
		switch (lv) {
		case 2:
			setLv(gsd, reward, rf, fy, new int[] { 3, 0, 0, 0 }, null, null,
					null, null, new int[] { 2, 0, 0, 0 }, null, null, null,
					null, 2, 0, 0);
			break;
		case 3:
			setLv(gsd, reward, rf, fy, new int[] { 4, 0, 0, 0 }, null, null,
					null, null, new int[] { 3, 0, 0, 0 }, null, null, null,
					null, 3, 0, 0);
			break;
		case 4:
			setLv(gsd, reward, rf, fy, new int[] { 5, 0, 0, 0 }, null, null,
					null, null, new int[] { 3, 0, 0, 0 }, null, null, null,
					null, 3, 0, 0);
			break;
		case 5:
			setLv(gsd, reward, rf, fy, new int[] { 10, 0, 0, 0 }, null, null,
					null, null, new int[] { 5, 0, 0, 0 }, null, null, null,
					null, 3, 0, 0);
			break;
		case 6:
			setLv(gsd, reward, rf, fy, new int[] { 10, 0, 0, 0 }, new int[] {
					2, 0, 0, 0 }, null, null, null, new int[] { 5, 0, 0, 0 },
					new int[] { 1, 0, 0, 0 }, null, null, null, 4, 0, 0);
			break;
		case 7:
			setLv(gsd, reward, rf, fy, new int[] { 5, 0, 0, 0 }, new int[] { 2,
					0, 0, 0 }, new int[] { 3, 0, 0, 0 }, null, null, new int[] {
					5, 0, 0, 0 }, new int[] { 1, 0, 0, 0 }, new int[] { 2, 0,
					0, 0 }, null, null, 6, 0, 0);
			break;
		case 8:
			setLv(gsd, reward, rf, fy, new int[] { 5, 0, 0, 0 }, new int[] { 2,
					0, 0, 0 }, new int[] { 2, 0, 0, 0 },
					new int[] { 2, 0, 0, 0 }, null, new int[] { 2, 0, 0, 0 },
					new int[] { 1, 0, 0, 0 }, new int[] { 1, 0, 0, 0 },
					new int[] { 1, 0, 0, 0 }, null, 8, 0, 0);
			break;
		case 9:
			setLv(gsd, reward, rf, fy, new int[] { 5, 0, 0, 0 }, new int[] { 2,
					0, 0, 0 }, new int[] { 2, 0, 0, 0 },
					new int[] { 2, 0, 0, 0 }, new int[] { 2, 0, 0, 0 },
					new int[] { 2, 0, 0, 0 }, new int[] { 1, 0, 0, 0 },
					new int[] { 1, 0, 0, 0 }, new int[] { 1, 0, 0, 0 },
					new int[] { 1, 0, 0, 0 }, 10, 0, 0);
			break;
		case 10:
			setLv(gsd, reward, rf, fy, new int[] { 10, 0, 0, 0 }, new int[] {
					5, 0, 0, 0 }, new int[] { 5, 0, 0, 0 }, new int[] { 5, 0,
					0, 0 }, new int[] { 5, 0, 0, 0 }, new int[] { 5, 0, 0, 0 },
					new int[] { 2, 0, 0, 0 }, new int[] { 2, 0, 0, 0 },
					new int[] { 2, 0, 0, 0 }, new int[] { 2, 0, 0, 0 }, 15, 0,
					0);
			break;
		case 11:
		default:
			setLv(gsd, reward, rf, fy, new int[] { 5, 2, 0, 0 }, new int[] { 5,
					2, 0, 0 }, null, null, null, new int[] { 3, 1, 0, 0 },
					new int[] { 3, 1, 0, 0 }, null, null, null, 15, 2, 0);
			break;
		}
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
		ag = targetInt(ag);
		cg = targetInt(cg);
		getNpcRole(gsd, rf, fe, we, se, ae, ce);
		getRoleVictory(gsd, reward, fy, fg, wg, sg, ag, cg, pj, gj, sj);
	}

	// 取得出战NPC
	private void getNpcRole(GameScreenData gsd, RoleFactory rf, int[] fe,
			int[] we, int[] se, int[] ae, int[] ce) {
		for (int i = 0; i < 4; i++) {
			for (int k = 0; k < fe[i]; k++) {
				gsd.getNpcRoles().add(
						rf.getFighter("NPC", Type.ENEMY, getQuality(i),
								"p_fighter"));
			}
			for (int k = 0; k < we[i]; k++) {
				gsd.getNpcRoles().add(
						rf.getWizard("NPC", Type.ENEMY, getQuality(i),
								"p_wizard"));
			}
			for (int k = 0; k < se[i]; k++) {
				gsd.getNpcRoles().add(
						rf.getSorcerer("NPC", Type.ENEMY, getQuality(i),
								"p_sorcerer"));
			}
			for (int k = 0; k < ae[i]; k++) {
				gsd.getNpcRoles().add(
						rf.getArcher("NPC", Type.ENEMY, getQuality(i),
								"p_archer"));
			}
			for (int k = 0; k < ce[i]; k++) {
				gsd.getNpcRoles().add(
						rf.getCleric("NPC", Type.ENEMY, getQuality(i),
								"p_cleric"));
			}
		}
	}

	// 取得某种通关卡片,精华
	private void getRoleVictory(GameScreenData gsd, Array<Reward> reward,
			ItemFactory fy, int[] fg, int[] wg, int[] sg, int[] ag, int[] cg,
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
