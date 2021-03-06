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
			item = new Item("普通技能精华",Item.Type.skillpart,QUALITY.blue,"skill_question");
			break;
		case 2:
			item = new Item("高级技能精华",Item.Type.skillpart,QUALITY.purple,"skill_question");
			break;
		case 3:
			item = new Item("史诗级技能精华",Item.Type.skillpart,QUALITY.orange,"skill_question");
			break;
		case 101://绿色战士卡片
			item = new Item("普通战士",Item.Type.rolecard,"p_fighter",QUALITY.green,CLASSES.fighter);
			break; 
		case 102://蓝色战士卡片
			item = new Item("高级战士",Item.Type.rolecard,"p_fighter",QUALITY.blue,CLASSES.fighter);
			break;
		case 103://紫色战士卡片
			item = new Item("精良战士",Item.Type.rolecard,"p_fighter",QUALITY.green,CLASSES.fighter);
			break;
		case 104://橙色战士卡片
			item = new Item("英雄战士",Item.Type.rolecard,"p_fighter",QUALITY.green,CLASSES.fighter);
			break;
		case 105:
			item = new Item("绿色射手",Item.Type.rolecard,"p_archer",QUALITY.green,CLASSES.archer);
			break;
		case 106:
			item = new Item("蓝色射手",Item.Type.rolecard,"p_archer",QUALITY.blue,CLASSES.archer);
			break;
		case 107:
			item = new Item("紫色射手",Item.Type.rolecard,"p_archer",QUALITY.purple,CLASSES.archer);
			break;
		case 108:
			item = new Item("橙色射手",Item.Type.rolecard,"p_archer",QUALITY.orange,CLASSES.archer);
			break;
		case 109:
			item = new Item("绿色元素法师",Item.Type.rolecard,"p_wizard",QUALITY.green,CLASSES.wizard);
			break;
		case 110:
			item = new Item("蓝色元素法师",Item.Type.rolecard,"p_wizard",QUALITY.blue,CLASSES.wizard);
			break;
		case 111:
			item = new Item("紫色元素法师",Item.Type.rolecard,"p_wizard",QUALITY.purple,CLASSES.wizard);
			break;
		case 112:
			item = new Item("橙色元素法师",Item.Type.rolecard,"p_wizard",QUALITY.orange,CLASSES.wizard);
			break;
		case 113:
			item = new Item("绿色黑暗法师",Item.Type.rolecard,"p_sorcerer",QUALITY.green,CLASSES.sorcerer);
			break;
		case 114:
			item = new Item("蓝色黑暗法师",Item.Type.rolecard,"p_sorcerer",QUALITY.blue,CLASSES.sorcerer);
			break;
		case 115:
			item = new Item("紫色黑暗法师",Item.Type.rolecard,"p_sorcerer",QUALITY.purple,CLASSES.sorcerer);
			break;
		case 116:
			item = new Item("橙色黑暗法师",Item.Type.rolecard,"p_sorcerer",QUALITY.orange,CLASSES.sorcerer);
			break;
		case 117:
			item = new Item("绿色牧师",Item.Type.rolecard,"p_cleric",QUALITY.green,CLASSES.cleric);
			break;
		case 118:
			item = new Item("蓝色牧师",Item.Type.rolecard,"p_cleric",QUALITY.blue,CLASSES.cleric);
			break;
		case 119:
			item = new Item("紫色牧师",Item.Type.rolecard,"p_cleric",QUALITY.purple,CLASSES.cleric);
			break;
		case 120:
			item = new Item("橙色牧师",Item.Type.rolecard,"p_cleric",QUALITY.orange,CLASSES.cleric);
			break;
		}
		return item;
	}
	
}
