package com.bsu.obj;
/**
 * 奖励对象，一个奖励对象包含一种物品，与物品产生的机率
 * @author fengchong
 *
 */
public class Reward {
	private Item item = null;			//奖励物品
	private float probability = .0f;	//出现机率
	
	public Reward(Item i,float p){
		item = i;
		probability = p;
	}
}
