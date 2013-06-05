package com.bsu.obj;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bsu.tools.CG.QUALITY;

/**
 * 装备类，包括各种装备，目前只代表武器，防具
 * 
 * @author fengchong
 * 
 */
public class Equip {
	public enum Type {
		weapon, armor
	};// 武器或防具

	public String name;// 名称
	public Type type;	//	装备类型
	public QUALITY quality;	// 装备品质
	public int val;// 提供的值，武器代表攻击力，防具代表防御值
	public TextureRegion texture;// 图标
	public String info;// 描述信息
	public int level;// 等级 

	public Equip(String s, Type t,QUALITY q,TextureRegion tr, int l, int av, String info) {
		// TODO Auto-generated constructor stub
		name = s;
		type = t;
		quality = q;
		texture = tr;
		level = l;
		val = av;
		this.info = info;
	}
}
