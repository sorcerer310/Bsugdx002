package com.bsu.obj;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bsu.effect.ItemIcon;
import com.bsu.tools.GC.CLASSES;
import com.bsu.tools.GC.QUALITY;

/**
 * 道具类
 * 
 * @author fengchong
 * 
 */
public class Item {
	public String name = "默认道具"; // 道具名称

	public enum Type {
		skillpart, rolecard
	}; // 道具类型 技能碎片 人物卡片

	public Type type;
//	public TextureRegion tr_item; // 物品图片
	public String tr_item;
	public QUALITY q; // 如果是卡片，则为卡片品质，如果为技能碎片，则为碎片品质
	public CLASSES classes;

	public Item(String n, Type t,QUALITY q, String tr_i) {
		name = n;
		type = t;
		this.q=q;
		tr_item = tr_i;
	}

	public Item(String n, Type t, String tr_i, QUALITY q, CLASSES es) {
		name = n;
		type = t;
		tr_item = tr_i;
		this.q = q;
		classes = es;
	}

	public ItemIcon getItemIcon() {
		return new ItemIcon(this);
	}
}
