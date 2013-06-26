package com.bsu.test;

import javax.swing.text.TextAction;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.bsu.make.RoleFactory;
import com.bsu.make.SkillFactory;
import com.bsu.obj.Role;
import com.bsu.obj.Role.Type;
import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.GC.QUALITY;
import com.bsu.tools.GTC;

public class TestObject {
	public int intvar = 0;
	public String strvar = "string";
	public Double doublevar = 0.0;
	public Array<String> arr = new Array<String>();
	public TestObject(){
		arr.add("str1");
		arr.add("str2");
	}
	public Skill skill = SkillFactory.getInstance().getSkillByIdx(1);
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("intvar:").append(String.valueOf(intvar))
			.append(" doublevar:").append(String.valueOf(doublevar))
			.append(" strvar:").append(strvar)
			.append(" tr:").append(arr);
			;
		return sb.toString();
	}
	
	
}
