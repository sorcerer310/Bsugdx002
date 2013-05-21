package com.bsu.obj.skilltree;

import com.badlogic.gdx.utils.Array;
import com.bsu.make.SkillFactory;
import com.bsu.tools.Configure;
import com.bsu.tools.Configure.QUALITY;
/**
 * 保存技能树的基本信息。st_forGreenHero中Array<Integer>保存的是改数所有技能的索引。
 * 其他配置数据同上
 * @author fengchong
 *
 */
public class SkillTree {
	private Array<Array<Integer>> st_forGreenHero = new Array<Array<Integer>>();		//绿色英雄技能树配置
	private Array<Array<Integer>> st_forBlueHero = new Array<Array<Integer>>();			//蓝色英雄技能树配置
	private Array<Array<Integer>> st_forPurpleHero = new Array<Array<Integer>>();		//紫色英雄技能树配置
	private Array<Array<Integer>> st_forOrangeHero = new Array<Array<Integer>>();		//橙色英雄技能树配置
	
	public SkillTree(){
		st_forGreenHero.add(new Array<Integer>(new Integer[]{6,9,7,16,10,15}));
		st_forGreenHero.add(new Array<Integer>(new Integer[]{1,6,7,17,15,19}));
		st_forGreenHero.add(new Array<Integer>(new Integer[]{4,9,6,15,12,13}));
		st_forGreenHero.add(new Array<Integer>(new Integer[]{5,6,3,11,12,14}));
		st_forGreenHero.add(new Array<Integer>(new Integer[]{5,3,2,10,14,13}));
		st_forGreenHero.add(new Array<Integer>(new Integer[]{3,8,4,13,15,11}));
		st_forGreenHero.add(new Array<Integer>(new Integer[]{3,2,6,10,19,13}));
		st_forGreenHero.add(new Array<Integer>(new Integer[]{4,2,9,10,15,16}));
		st_forGreenHero.add(new Array<Integer>(new Integer[]{8,9,7,13,20,10}));
		st_forGreenHero.add(new Array<Integer>(new Integer[]{2,8,6,15,16,12}));
		
		st_forBlueHero.add(new Array<Integer>(new Integer[]{6,1,9,10,13,20,11,17,31}));
		st_forBlueHero.add(new Array<Integer>(new Integer[]{9,2,3,15,20,17,10,18,24}));
		st_forBlueHero.add(new Array<Integer>(new Integer[]{9,2,3,15,20,17,10,18,24}));
		st_forBlueHero.add(new Array<Integer>(new Integer[]{1,6,5,13,15,12,18,17,26}));
		st_forBlueHero.add(new Array<Integer>(new Integer[]{7,1,8,14,20,17,19,13,31}));
		st_forBlueHero.add(new Array<Integer>(new Integer[]{3,8,4,17,11,10,20,16,23}));
		st_forBlueHero.add(new Array<Integer>(new Integer[]{8,2,5,11,12,15,14,20,26}));
		st_forBlueHero.add(new Array<Integer>(new Integer[]{3,5,6,10,12,18,16,19,26}));
		st_forBlueHero.add(new Array<Integer>(new Integer[]{9,7,3,17,15,16,18,10,24}));
		st_forBlueHero.add(new Array<Integer>(new Integer[]{6,5,9,19,20,12,11,15,23}));
		
		st_forPurpleHero.add(new Array<Integer>(new Integer[]{3,8,5,11,13,10,15,20,33,27,23,36}));
		st_forPurpleHero.add(new Array<Integer>(new Integer[]{5,4,6,12,18,15,11,13,25,29,23,38}));
		st_forPurpleHero.add(new Array<Integer>(new Integer[]{5,3,9,18,17,19,15,11,26,21,30,40}));
		st_forPurpleHero.add(new Array<Integer>(new Integer[]{7,6,8,19,11,14,12,18,30,29,26,39}));
		st_forPurpleHero.add(new Array<Integer>(new Integer[]{3,6,7,19,14,10,20,12,22,29,23,40}));
		st_forPurpleHero.add(new Array<Integer>(new Integer[]{7,3,8,18,16,14,19,15,25,30,32,40}));
		st_forPurpleHero.add(new Array<Integer>(new Integer[]{5,1,7,20,16,18,19,11,21,27,28,38}));
		st_forPurpleHero.add(new Array<Integer>(new Integer[]{5,6,4,13,17,19,20,12,21,29,26,39}));
		st_forPurpleHero.add(new Array<Integer>(new Integer[]{4,2,5,10,20,15,18,13,34,24,32,36}));
		st_forPurpleHero.add(new Array<Integer>(new Integer[]{8,3,2,16,13,12,11,19,21,24,32,37}));
		
		st_forOrangeHero.add(new Array<Integer>(new Integer[]{3,10,19,15,17,20,25,33,28,32,31,35,41,36}));
		st_forOrangeHero.add(new Array<Integer>(new Integer[]{3,12,13,15,14,16,34,29,26,23,27,35,38,41}));
		st_forOrangeHero.add(new Array<Integer>(new Integer[]{6,10,16,14,17,19,24,29,30,27,31,38,36,39}));
		st_forOrangeHero.add(new Array<Integer>(new Integer[]{8,15,16,11,18,12,28,29,23,34,31,40,36,37}));
		st_forOrangeHero.add(new Array<Integer>(new Integer[]{7,14,10,17,15,19,23,25,34,29,32,36,35,37}));
		st_forOrangeHero.add(new Array<Integer>(new Integer[]{8,20,16,10,14,12,31,30,23,33,32,36,39,35}));
		st_forOrangeHero.add(new Array<Integer>(new Integer[]{7,16,15,14,17,20,22,23,33,30,25,35,36,39}));
		st_forOrangeHero.add(new Array<Integer>(new Integer[]{9,10,19,16,17,14,28,32,34,26,30,39,35,40}));
		st_forOrangeHero.add(new Array<Integer>(new Integer[]{7,19,17,15,14,20,31,25,29,26,22,40,41,36}));
		st_forOrangeHero.add(new Array<Integer>(new Integer[]{6,17,16,12,10,13,21,33,32,24,28,38,37,36}));
	}
	/**
	 * 通过英雄品质和配置的索引获得一颗技能树配置
	 * @param h_quality	指定英雄品质参数
	 * @param cfgidx	配置索引
	 * @return
	 */
	public Array<Skill> getSkillTree(Configure.QUALITY h_quality,int cfgidx){
		Array<Integer> st = null;
		if(h_quality==QUALITY.green){
			st = st_forGreenHero.get(cfgidx);
		}else if(h_quality==QUALITY.blue){
			st = st_forBlueHero.get(cfgidx);
		}else if(h_quality==QUALITY.purple){
			st = st_forPurpleHero.get(cfgidx);
		}else if(h_quality==QUALITY.orange){
			st = st_forOrangeHero.get(cfgidx);
		}
		
		if(st==null)
			return null;
		
		Array<Skill> skills = new Array<Skill>();
		SkillFactory sf = SkillFactory.getInstance();
		for(int i=0;i<st.size;i++)
			skills.add(sf.getSkillByIdx(st.get(i)));
		return skills;
	}
	
}
