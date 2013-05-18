package com.bsu.tools;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bsu.tools.Configure.QUALITY;

/**
 * 防御装备，护甲
 * @author zhangyongchen
 *
 */
public class DefendWeaponBase {
	public String name;
	public String info;
	public int level;
	public int defendValue;
	public TextureRegion texture;
	public DefendWeaponBase(String s,TextureRegion tr,int l,int dv,String info) {
		// TODO Auto-generated constructor stub
		name=s;
		texture=tr;
		level=l;
		defendValue=dv;
		this.info=info;
	}
}
