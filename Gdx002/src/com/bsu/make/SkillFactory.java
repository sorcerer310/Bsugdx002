package com.bsu.make;

import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.HeroEffectClass;
import com.bsu.obj.Skill;
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
		Array<Vector2> r = new Array<Vector2>();
		r.add(new Vector2(1,0));
		r.add(new Vector2(2,0));
		final Skill k = new Skill("攻击",Skill.Type.ATK,20.0f,HeroEffectClass.get_effect(0),HeroEffectClass.get_effect(1),"普通攻击，没什么稀奇的。",r);
		store.put("atk", k);
	}
	
	/**
	 * 通过名字获得技能对象
	 * @param k	技能名称
	 * @return	返回技能对象
	 */
	public Skill getSkillByName(String k){
		return store.get(k);
//		Array<Vector2> r = new Array<Vector2>();
//		r.add(new Vector2(1,0));
//		r.add(new Vector2(2,0));
//		return new Skill("攻击",Skill.Type.ATK,20.0f,HeroEffectClass.get_effect(0),HeroEffectClass.get_effect(1),"普通攻击，没什么稀奇的。",r);
	}
}
