package com.bsu.obj.skilltree;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Role;
import com.bsu.tools.Configure.CLASSES;
import com.bsu.tools.Configure.QUALITY;

public class SkillTree {
	public SkillTree(){}
//	private Array<>
	public ArrayList<Array<Skill>> skills = new ArrayList<Array<Skill>>();		//保存技能的容器，ArrayList保存所有层，Array保存每层的所有技能
	
	
	public void makeSkillForGreenHero(Role role){
		QUALITY q =  role.getQuality();
		//如果品质不为绿色，直接返回
		if(q != QUALITY.green)
			return;
		CLASSES cls =  role.getClasses();
		
		
		
	}
	
	public void makeSkillForBlueHero(){}
	
	public void makeSkillForPurpleHero(){}
	
	public void makeSkillForOrangeHero(){}
}
