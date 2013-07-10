package com.bsu.obj;

import java.util.Observable;
import com.bsu.tools.MessageObject;

public class RoleObsever extends Observable{
	public RoleObsever() {
		
	}
	//更换技能
	public void changeSkill(Role r){
		setChanged();
		notifyObservers(new MessageObject(r, "changeSkill"));
	}
	//开启or升级技能
	public void updateSkill(Role r){
		setChanged();
		notifyObservers(new MessageObject(r, "updateSkill"));
	}
	//是否锁定
	public void lockRole(Role r){
		setChanged();
		notifyObservers(new MessageObject(r, "lockRole"));
	}
}
