package com.bsu.obj;

import java.util.Observable;
import com.bsu.tools.MessageObject;

public class RoleObsever extends Observable{
	public RoleObsever() {
		
	}
	
	public void changeSkill(Role r){
		setChanged();
		notifyObservers(new MessageObject(r, "changeSkill"));
	}
	public void updateSkill(Role r){
		setChanged();
		notifyObservers(new MessageObject(r, "updateSkill"));
	}
}
