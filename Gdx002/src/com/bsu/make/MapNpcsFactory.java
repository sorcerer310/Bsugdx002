package com.bsu.make;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Role;

/**
 * 用于保存战场上出现npc的规则
 * 
 * @author fengchong
 * 
 */
public class MapNpcsFactory {
	private static MapNpcsFactory instance = null;

	public static MapNpcsFactory getInstance() {
		if (instance == null)
			instance = new MapNpcsFactory();
		return instance;
	}

	private MapNpcsFactory(){}
	
	public static enum NpcsPlan {PLAN1,PLAN2,PLAN3,PLAN4};
	public NpcsPlan nplan = NpcsPlan.PLAN1;							//指定的当前使用哪个方案
	/**
	 * 设置方案1
	 * 
	 * @param v
	 *            所有敌人的出生地
	 * @param rs
	 *            所有敌人
	 * @param cr
	 *            当前回合数
	 */
	private void plan1(Array<Vector2> v, Array<Role> rs, int rc) {
		int viscount = 0; // 计算还有多少敌人在场上
		Array<Role> livednpc = new Array<Role>(); // 保存活着但未上场的npc
		for (int i = 0; i < rs.size; i++) {
			if (!rs.get(i).isDead && !rs.get(i).isVisible())
				livednpc.add(rs.get(i));
			if (rs.get(i).isVisible())
				viscount++;
		}

		if (viscount > 0)
			return;

		int lnsize = livednpc.size; // 活着但没上场npc的数量
		int bvsize = v.size; // 出生地数量

		for (int i = 0; i < (lnsize <= bvsize ? lnsize : bvsize); i++) {
			Role r = livednpc.get(i);
			r.setPosition(v.get(i).x, v.get(i).y);
			r.setVisible(true);
		}
	}

	/**
	 * 设置方案2 ，每个一个回合新增一批敌人,目前最大容量是30个npc 同时存在
	 * 
	 * @param v
	 *            所有敌人的出生地
	 * @param rs
	 *            所有敌人
	 * @param cr
	 *            当前回合数
	 */
	private void plan2(Array<Vector2> v, Array<Role> rs, int rc) {
		Array<Role> livednpc = new Array<Role>(); // 保存活着但未上场的npc
		for (int i = 0; i < rs.size; i++) {
			if (!rs.get(i).isDead && !rs.get(i).isVisible())
				livednpc.add(rs.get(i));
		}
		int lnsize = livednpc.size; // 活着但没上场npc的数量
		int bvsize = v.size; // 出生地数量
		for (int i = 0; i < (lnsize <= bvsize ? lnsize : bvsize); i++) {
			if(hasNpc(v.get(i),rs)){//此处有NPC 存在  则不添加。防止重叠
				break;
			}
			Role r = livednpc.get(i);
			r.setPosition(v.get(i).x, v.get(i).y);
			r.setVisible(true);
		}
	}

	/**
	 * 设置方案3 保持NPC 数量为5个。
	 * 
	 * @param v
	 *            所有敌人的出生地
	 * @param rs
	 *            所有敌人
	 * @param cr
	 *            当前回合数
	 */
	private void plan3(Array<Vector2> v, Array<Role> rs, int rc) {

	}

	/**
	 * 刷新敌人
	 */
	public void refreshNpcs(Array<Vector2> v,Array<Role> rs,int rc){
		if(this.nplan==NpcsPlan.PLAN1){
			plan1(v,rs,rc);
		}else if(this.nplan==NpcsPlan.PLAN2){
			plan2(v,rs,rc);
		}else if(this.nplan==NpcsPlan.PLAN3){
			plan3(v,rs,rc);
		}else if(this.nplan==NpcsPlan.PLAN4){
		
		}
	}
	//某个点是否有NPC存在
	private boolean hasNpc(Vector2 v,Array<Role> rs){
		for(Role e:rs){
			if((e.getX()==v.x)&&(e.getY()==v.y)){
				return true;
			}
		}
		return false;
	}
}
