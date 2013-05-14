package com.bsu.tools;

public class CommandQueue implements Runnable {
	private CommandQueue instance = null;
	private CommandQueue(){}
	public CommandQueue getInstance(){
		if(instance == null)
			instance = new CommandQueue();
		return instance;
	}
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
