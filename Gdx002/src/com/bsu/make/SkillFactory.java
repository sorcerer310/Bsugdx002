package com.bsu.make;

import java.util.HashMap;

import com.bsu.obj.Skill;

public class SkillFactory {
	private static SkillFactory instance = null;
	private SkillFactory(){}
	public static SkillFactory getInstance(){
		if(instance==null)
			instance = new SkillFactory();
		return instance;
	}
	private HashMap<String,Skill> store = new HashMap<String,Skill>();			//存储所有的技能类
}
