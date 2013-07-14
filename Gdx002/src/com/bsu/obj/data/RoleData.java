package com.bsu.obj.data;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Role.BATLESTATE;
import com.bsu.tools.GC.CLASSES;
import com.bsu.tools.GC.QUALITY;

public class RoleData{
	public String weapon;						//武器索引
	public String armor;						//护甲索引
	
	public String name;							//人物名称
	public QUALITY quality;						//人物品质
	public CLASSES classes;						//人物职业
	public BATLESTATE bstate;					//是否加入战斗阵容
	public int level;							//等级
	public String roleTexture;					//人物纹理
	public float hp_talent;						//生命资质
	public float attack_talent;					//攻击资质
	public float defend_talent;					//防御资质
//	public int maxHp;							//最大血量
//	public int extMaxHp;						//额外最大血量
//	public int currentHp;						//当前血量
//	public int attack;							//攻击力
//	public int extAttack;						//额外的攻击力
//	public int defend;							//防御力
//	public int extDefend;						//额外的防御力
	public int exp;								//经验值
//	public int expUp;							//下级升级的经验值
	public boolean locked;						//是否被锁定
	public Array<SkillData> skill_tree=new Array<SkillData>();			//英雄的技能树
	public Array<SkillData> skill_array=new Array<SkillData>();			//英雄本关卡携带的技能
}
