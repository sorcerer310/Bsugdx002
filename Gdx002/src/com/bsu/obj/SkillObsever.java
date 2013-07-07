package com.bsu.obj;

import java.util.Observable;

import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.GC.QUALITY;

public class SkillObsever extends Observable{
	public String name;
	public String info;
	public QUALITY q;
	public boolean enable;
	public SkillObsever(Skill s) {
		// TODO Auto-generated constructor stub
		name=s.name;
		info=s.info;
		q=s.quality;
		enable=s.enable;
	}
	
	public void changeSkill(Skill s){
		name=s.name;
		info=s.info;
		q=s.quality;
		enable=s.enable;
	}

}
