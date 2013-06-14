package com.bsu.make;

import com.bsu.obj.Equip;
import com.bsu.obj.Equip.Type;
import com.bsu.tools.GC.QUALITY;

public class EquipFactory {
	private static EquipFactory instance = null;
	public static EquipFactory getInstance(){
		if(instance == null)
			instance = new EquipFactory();
		return instance;
	}
	private EquipFactory(){}
	/**
	 * 根据索引返回一个武器
	 * @param id	武器索引
	 * @return
	 */
	public Equip getWeaponByIdx(int id){
		Equip ep = null;
		switch(id){
		case 1:
			new Equip("短剑",Type.weapon,QUALITY.green,null,1,10,"基本武器，到处都是");
			break;
		case 2:
			new Equip("闪光的短剑",Type.weapon,QUALITY.blue,null,1,20,"这把短剑上泛着悠悠的蓝光");
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		}
		return ep;
	}
	/**
	 * 根据索引返回一个防具
	 * @param id	防具索引
	 * @return
	 */
	public Equip getArmorByIdx(int id){
		Equip ep = null;
		switch(id){
		case 1:
			new Equip("肚兜",Type.armor,QUALITY.green,null,1,5,"穿着可以保暖，防止肚子着凉");
			break;
		case 2:
			new Equip("木甲",Type.armor,QUALITY.green,null,1,10,"这个似乎可以穿上作战");
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		}
		return ep;
	}
}
