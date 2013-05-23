package com.bsu.tools;

import java.util.Random;

public class U {
//	public void waitWhile(boolean f) throws InterruptedException{
//		while (f) {
//			Thread.sleep(200);
//		}
//	}
	private static Random rnd = new Random();				
	/**
	 * 为一个固定值增加一部分浮动值
	 * @param fix_val			固定的数值
	 * @param floatval_top		浮动值上限
	 * @param floatval_bottom	浮动值下限
	 * @return
	 */
	public static int getRandom(int fix_val,int floatval_bottom,int floatval_top){
		int rndval = rnd.nextInt(floatval_top - floatval_bottom);
		return fix_val + (rndval-floatval_bottom);
	}
} 
