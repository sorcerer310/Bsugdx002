package com.bsu.obj;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bsu.effect.ItemIcon;

/**
 * 道具类
 * @author fengchong
 *
 */
public class Item {
	private String name = "默认道具";					//道具名称
	public enum Type{skillpart,rolecard};			//道具类型 技能碎片 人物卡片
	private Type type;
	public TextureRegion tr_item;					//物品图片
	public TextureRegion tr_frame;					//边框图片
	
	public Item(String n,Type t,TextureRegion tr_i,TextureRegion tr_f){
		name = n;
		type = t;
		tr_item = tr_i;
		tr_frame = tr_f;
	}
	
	public ItemIcon getItemIcon(){
		return new ItemIcon(this);
	}
}
