package com.bsu.make;

import com.bsu.obj.Item;
import com.bsu.tools.GTC;

public class ItemFactory {
	private static ItemFactory instance = null;
	public static ItemFactory getInstance(){
		if(instance == null)
			instance = new ItemFactory();
		return instance;
	}
	private ItemFactory(){}
		
	/**
	 * 根据索引返回一样道具
	 * @param id	道具索引
	 */
	private Item getItemById(int id){
		Item item = null;
		switch(id){
		case 1:
			item = new Item("普通技能精华",Item.Type.skillpart,GTC.getInstance().getSkillIcon(0),GTC.getInstance().role_head_frame.findRegion("frame_yellow"));
			break;
		case 2:
			item = new Item("高级技能精华",Item.Type.skillpart,GTC.getInstance().getSkillIcon(0),GTC.getInstance().role_head_frame.findRegion("frame_purple"));
			break;
		case 3:
			item = new Item("史诗级技能精华",Item.Type.skillpart,GTC.getInstance().getSkillIcon(0),GTC.getInstance().role_head_frame.findRegion("frame_orange"));
			break;
		case 101:
			item = new Item("战士卡片",Item.Type.rolecard,GTC.getInstance().fight_texture,GTC.getInstance().role_head_frame.findRegion("frame_yellow"));
			break;
		}
		return item;
	}
	
}
