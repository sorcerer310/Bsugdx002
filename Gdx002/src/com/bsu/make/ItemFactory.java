package com.bsu.make;

import com.bsu.obj.Item;
import com.bsu.tools.GC.CLASSES;
import com.bsu.tools.GC.QUALITY;
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
	public Item getItemById(int id){
		Item item = null;
		switch(id){
		case 1:
			item = new Item("普通技能精华",Item.Type.skillpart,GTC.getInstance().getSkillIcon(0),QUALITY.blue,CLASSES.archer);
			break;
		case 2:
			item = new Item("高级技能精华",Item.Type.skillpart,GTC.getInstance().getSkillIcon(0),QUALITY.green,CLASSES.cleric);
			break;
		case 3:
			item = new Item("史诗级技能精华",Item.Type.skillpart,GTC.getInstance().getSkillIcon(0),QUALITY.blue,CLASSES.cleric);
			break;
		case 101:
			item = new Item("绿色战士卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.green,CLASSES.fighter);
			break; 
		case 102:
			item = new Item("蓝色战士卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.blue,CLASSES.fighter);
			break;
		case 103:
			item = new Item("紫色战士卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.green,CLASSES.fighter);
			break;
		case 104:
			item = new Item("橙色战士卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.green,CLASSES.fighter);
			break;
		case 105:
			item = new Item("绿色射手卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.green,CLASSES.archer);
			break;
		case 106:
			item = new Item("蓝色射手卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.blue,CLASSES.archer);
			break;
		case 107:
			item = new Item("紫色射手卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.purple,CLASSES.archer);
			break;
		case 108:
			item = new Item("橙色射手卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.orange,CLASSES.archer);
			break;
		case 109:
			item = new Item("绿色元素法师卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.green,CLASSES.wizard);
			break;
		case 110:
			item = new Item("蓝色元素法师卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.blue,CLASSES.wizard);
			break;
		case 111:
			item = new Item("紫色元素法师卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.purple,CLASSES.wizard);
			break;
		case 112:
			item = new Item("橙色元素法师卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.orange,CLASSES.wizard);
			break;
		case 113:
			item = new Item("绿色黑暗法师卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.green,CLASSES.sorcerer);
			break;
		case 114:
			item = new Item("蓝色黑暗法师卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.blue,CLASSES.sorcerer);
			break;
		case 115:
			item = new Item("紫色黑暗法师卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.purple,CLASSES.sorcerer);
			break;
		case 116:
			item = new Item("橙色黑暗法师卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.orange,CLASSES.sorcerer);
			break;
		case 117:
			item = new Item("绿色牧师法师卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.green,CLASSES.wizard);
			break;
		case 118:
			item = new Item("蓝色牧师法师卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.blue,CLASSES.wizard);
			break;
		case 119:
			item = new Item("紫色牧师法师卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.purple,CLASSES.wizard);
			break;
		case 120:
			item = new Item("橙色牧师法师卡片",Item.Type.rolecard,GTC.getInstance().zyc_photo,QUALITY.orange,CLASSES.wizard);
			break;
		}
		return item;
	}
	
}
