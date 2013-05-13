package com.bsu.make;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Skill;
import com.bsu.tools.HeroEffectClass;
/**
 * 技能工厂，用来初始化所有技能，并保存在hashmap中
 * @author fengchong
 *
 */
public class SkillFactory {
	private static SkillFactory instance = null;
	public static SkillFactory getInstance(){
		if(instance==null)
			instance = new SkillFactory();
		return instance;
	}
	private HashMap<String,Skill> store = new HashMap<String,Skill>();			//保存所有的技能对象
	
	/**
	 * 初始化所有技能，并保存到hashmap中，此处数据全部为配置数据，不能再改变
	 */
	private SkillFactory(){
		//普通攻击1
		store.put("atk", 
				new Skill("攻击",Skill.Type.ATK,10.0f,HeroEffectClass.get_effect(0),HeroEffectClass.get_effect(1),
						"普通攻击，没什么稀奇的。",new Vector2[]{new Vector2(1,0),new Vector2(2,0)}
				)
		);
		//普通攻击2
		store.put("atk2",
				new Skill("攻击",Skill.Type.ATK,20.0f,HeroEffectClass.get_effect(0),HeroEffectClass.get_effect(2),
						"普通攻击，没什么稀奇的。",new Vector2[]{new Vector2(1,0),new Vector2(2,0)}
				)
		);
		//普通攻击3
		store.put("atk3",
				new Skill("攻击",Skill.Type.ATK,20.0f,HeroEffectClass.get_effect(0),HeroEffectClass.get_effect(3),
						"普通攻击，没什么稀奇的。",new Vector2[]{new Vector2(1,0),new Vector2(2,0)}
				)
		);
	}
	
	/**
	 * 通过名字获得技能对象
	 * @param k	技能名称
	 * @return	返回技能对象
	 */
	public Skill getSkillByName(String k){
		return store.get(k);
	}
}
