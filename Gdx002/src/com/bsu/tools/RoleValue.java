package com.bsu.tools;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.bsu.make.SkillListBase;
import com.bsu.obj.Role.Type;
import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.Configure.QUALITY;

/**
 * 卡片对应的属性。
 * 
 * @author zhangyongchen
 * 
 */
public class RoleValue {
	public TextureRegion roleTexture;
	public String name;
	public QUALITY quality;
	public int roleHp;// 生命值
	public int defendValue;// 防御值
	public int attackValue;// 攻击值
	public Array<Skill> skillList;// 自身的技能树
	public AttackWeaponBase attackWeapon;// 人物武器
	public DefendWeaponBase defendWeapon;// 人物防具
	public Type type;

	public RoleValue(String s,QUALITY q,Type p,TextureRegion tr, int ph, int dv, int av, Array<Skill> sl,
			AttackWeaponBase aw, DefendWeaponBase dw) {
		// TODO Auto-generated constructor stub
		name=s;
		quality=q;
		roleTexture=tr;
		roleHp=ph;
		defendValue=dv;
		attackValue=av;
		skillList=sl;
		attackWeapon=aw;
		defendWeapon=dw;
		type=p;
	}
}
