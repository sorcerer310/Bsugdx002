package com.bsu.tools;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bsu.tools.Configure.QUALITY;

/**
 * 攻击装备，武器
 * @author zhangyongchen
 *
 */
public class AttackWeaponBase {
	public String name;//名称
	public int attackValue;//攻击力
	public TextureRegion texture;//图像
	public String info;//描述信息
	public int level;//等级
	public AttackWeaponBase(String s,TextureRegion tr,int l,int av,String info) {
		// TODO Auto-generated constructor stub
		name=s;
		texture=tr;
		level=l;
		attackValue=av;
		this.info=info;
	}
}
