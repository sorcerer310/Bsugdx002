package com.bsu.tools;

public class U {
	public void waitWhile(boolean f) throws InterruptedException{
		while (f) {
			Thread.sleep(200);
		}
	}
} 
