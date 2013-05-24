package com.bsu.obj.skilltree;

import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * 一些在人物身上持久存在的状态，包括buff debuff dot hot
 * @author fengchong
 *
 */
public class ContinuedSkillState {
	public int remainRound	 = 0;		//残留回合数
	public float val = 0;				//每回合的作用值
	//增益攻击，增益防御，增益HP上限，减益攻击，减益防御，减益HP，持续伤害，持续治疗
	public enum CSType {none,buff_atk,buff_def,buff_hp,debuff_atk,debuff_def,debuff_hp,dot,hot};
	public CSType cstype = CSType.none;
	public Animation ani = null;		//持续效果每回合的动画
	
	public ContinuedSkillState(int rr,float v,CSType cst,Animation pa){
		remainRound = rr;
		val = v;
		cstype = cst;
		ani = pa;
	}
}
