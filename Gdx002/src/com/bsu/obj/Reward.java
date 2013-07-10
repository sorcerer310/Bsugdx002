package com.bsu.obj;

import com.bsu.make.ItemFactory;

/**
 * 奖励对象，一个奖励对象包含一种物品，与物品产生的机率
 * @author fengchong
 *
 */
public class Reward {
	public Item item = null;			//奖励物品
	public float probability = .0f;	//出现机率
	
	public Reward(Item i,float p){
		item = i;
		probability = p;
	}
	/**
	 * 构造函数
	 * @param i	奖励物品的id
	 * @param p	物品出现机率
	 */
	public Reward(int i,float p){
		item = ItemFactory.getInstance().getItemById(i);
		probability = p;
	}
}
