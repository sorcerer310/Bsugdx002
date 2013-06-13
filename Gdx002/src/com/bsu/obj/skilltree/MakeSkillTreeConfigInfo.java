package com.bsu.obj.skilltree;

import java.util.Random;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Role;
import com.bsu.tools.CG;
import com.bsu.tools.CG.CLASSES;
import com.bsu.tools.CG.QUALITY;

/**
 * 用于生成技能树的类，详细用法见main函数
 * @author fengchong
 *
 */
public class MakeSkillTreeConfigInfo {
	private Array<SimpleSkill> greenskill = new Array<SimpleSkill>();
	private Array<SimpleSkill> blueskill = new Array<SimpleSkill>();
	private Array<SimpleSkill> purpleskill = new Array<SimpleSkill>();
	private Array<SimpleSkill> orangeskill = new Array<SimpleSkill>();
	public MakeSkillTreeConfigInfo(){
		greenskill.add(new SimpleSkill(1,QUALITY.green,new CLASSES[]{CLASSES.fighter,CLASSES.archer},8));
		greenskill.add(new SimpleSkill(2,QUALITY.green,new CLASSES[]{CLASSES.fighter,CLASSES.archer},1));
		greenskill.add(new SimpleSkill(3,QUALITY.green,new CLASSES[]{CLASSES.fighter,CLASSES.archer},4));
		greenskill.add(new SimpleSkill(4,QUALITY.green,new CLASSES[]{CLASSES.wizard,CLASSES.cleric,CLASSES.sorcerer},9));
		greenskill.add(new SimpleSkill(5,QUALITY.green,new CLASSES[]{CLASSES.wizard,CLASSES.cleric,CLASSES.sorcerer},6));
		greenskill.add(new SimpleSkill(6,QUALITY.green,new CLASSES[]{CLASSES.all},4));
		greenskill.add(new SimpleSkill(7,QUALITY.green,new CLASSES[]{CLASSES.all},4));
		greenskill.add(new SimpleSkill(8,QUALITY.green,new CLASSES[]{CLASSES.all},7));
		greenskill.add(new SimpleSkill(9,QUALITY.green,new CLASSES[]{CLASSES.archer,CLASSES.wizard,CLASSES.cleric,CLASSES.sorcerer},4));
		greenskill.add(new SimpleSkill(10,QUALITY.blue,new CLASSES[]{CLASSES.archer,CLASSES.wizard,CLASSES.cleric,CLASSES.sorcerer},7));
		blueskill.add(new SimpleSkill(31,QUALITY.blue,new CLASSES[]{CLASSES.fighter,CLASSES.archer},16));
		blueskill.add(new SimpleSkill(32,QUALITY.blue,new CLASSES[]{CLASSES.fighter,CLASSES.archer},16));
		blueskill.add(new SimpleSkill(33,QUALITY.blue,new CLASSES[]{CLASSES.wizard},7));
		blueskill.add(new SimpleSkill(34,QUALITY.blue,new CLASSES[]{CLASSES.cleric},10));
		blueskill.add(new SimpleSkill(35,QUALITY.blue,new CLASSES[]{CLASSES.all},9));
		blueskill.add(new SimpleSkill(36,QUALITY.blue,new CLASSES[]{CLASSES.sorcerer},7));
		blueskill.add(new SimpleSkill(37,QUALITY.blue,new CLASSES[]{CLASSES.archer,CLASSES.wizard,CLASSES.cleric,CLASSES.sorcerer},8));
		blueskill.add(new SimpleSkill(38,QUALITY.blue,new CLASSES[]{CLASSES.archer,CLASSES.wizard},12));
		blueskill.add(new SimpleSkill(39,QUALITY.blue,new CLASSES[]{CLASSES.sorcerer},10));
		blueskill.add(new SimpleSkill(40,QUALITY.blue,new CLASSES[]{CLASSES.all},13));
		purpleskill.add(new SimpleSkill(61,QUALITY.purple,new CLASSES[]{CLASSES.fighter,CLASSES.archer},25));
		purpleskill.add(new SimpleSkill(62,QUALITY.purple,new CLASSES[]{CLASSES.fighter},16));
		purpleskill.add(new SimpleSkill(63,QUALITY.purple,new CLASSES[]{CLASSES.fighter,CLASSES.wizard,CLASSES.sorcerer},17));
		purpleskill.add(new SimpleSkill(64,QUALITY.purple,new CLASSES[]{CLASSES.fighter,CLASSES.archer},15));
		purpleskill.add(new SimpleSkill(65,QUALITY.purple,new CLASSES[]{CLASSES.wizard},22));
		purpleskill.add(new SimpleSkill(66,QUALITY.purple,new CLASSES[]{CLASSES.wizard},15));
		purpleskill.add(new SimpleSkill(67,QUALITY.purple,new CLASSES[]{CLASSES.cleric,CLASSES.sorcerer},19));
		purpleskill.add(new SimpleSkill(68,QUALITY.purple,new CLASSES[]{CLASSES.cleric},21));
		purpleskill.add(new SimpleSkill(69,QUALITY.purple,new CLASSES[]{CLASSES.all},25));
		purpleskill.add(new SimpleSkill(70,QUALITY.purple,new CLASSES[]{CLASSES.sorcerer},18));
		purpleskill.add(new SimpleSkill(71,QUALITY.purple,new CLASSES[]{CLASSES.archer},22));
		purpleskill.add(new SimpleSkill(72,QUALITY.purple,new CLASSES[]{CLASSES.all},22));
		purpleskill.add(new SimpleSkill(73,QUALITY.purple,new CLASSES[]{CLASSES.fighter,CLASSES.archer},23));
		purpleskill.add(new SimpleSkill(74,QUALITY.purple,new CLASSES[]{CLASSES.wizard,CLASSES.cleric,CLASSES.sorcerer},24));
		orangeskill.add(new SimpleSkill(95,QUALITY.orange,new CLASSES[]{CLASSES.fighter,CLASSES.archer},32));
		orangeskill.add(new SimpleSkill(96,QUALITY.orange,new CLASSES[]{CLASSES.wizard},26));
		orangeskill.add(new SimpleSkill(97,QUALITY.orange,new CLASSES[]{CLASSES.cleric},25));
		orangeskill.add(new SimpleSkill(98,QUALITY.orange,new CLASSES[]{CLASSES.all},25));
		orangeskill.add(new SimpleSkill(99,QUALITY.orange,new CLASSES[]{CLASSES.sorcerer},24));
		orangeskill.add(new SimpleSkill(100,QUALITY.orange,new CLASSES[]{CLASSES.all},28));
		orangeskill.add(new SimpleSkill(101,QUALITY.orange,new CLASSES[]{CLASSES.archer},31));
		orangeskill.add(new SimpleSkill(102,QUALITY.orange,new CLASSES[]{CLASSES.wizard,CLASSES.cleric,CLASSES.sorcerer},31));
		orangeskill.add(new SimpleSkill(103,QUALITY.orange,new CLASSES[]{CLASSES.fighter},27));
	}
	/**
	 * 根据英雄的品质与职业生成一棵随机的技能树
	 * @param hqual
	 * @param c
	 */
	public Array<SimpleSkill> makeSkillTree(QUALITY hqual,CLASSES c){
		int greencount,bluecount,purplecount,orangecount;
		int useprice_green = 8,useprice_blue = 15,useprice_purple = 23,useprice_orange = 30;
		Array<SimpleSkill> useSkills = new Array<SimpleSkill>();
		Random rnd = new Random();
		if(hqual == QUALITY.green){
			greencount = 2;bluecount = 2;purplecount = 0;orangecount = 0;
			useSkills.addAll(getSameQualitySkill(rnd,greenskill,greencount,useprice_green,c));
			useSkills.addAll(getSameQualitySkill(rnd,blueskill,bluecount,useprice_blue,c));
		}else if(hqual == QUALITY.blue){
			greencount = 2;bluecount = 2;purplecount = 1;orangecount = 0;
			useSkills.addAll(getSameQualitySkill(rnd,greenskill,greencount,useprice_green,c));
			useSkills.addAll(getSameQualitySkill(rnd,blueskill,bluecount,useprice_blue,c));
			useSkills.addAll(getSameQualitySkill(rnd,purpleskill,purplecount,useprice_purple,c));
		}else if(hqual == QUALITY.purple){
			greencount = 2;bluecount = 2;purplecount = 2;orangecount = 1;
			useSkills.addAll(getSameQualitySkill(rnd,greenskill,greencount,useprice_green,c));
			useSkills.addAll(getSameQualitySkill(rnd,blueskill,bluecount,useprice_blue,c));
			useSkills.addAll(getSameQualitySkill(rnd,purpleskill,purplecount,useprice_purple,c));
			useSkills.addAll(getSameQualitySkill(rnd,orangeskill,orangecount,useprice_orange,c));
		}else if(hqual == QUALITY.orange){
			greencount = 1;bluecount = 2;purplecount = 2;orangecount = 2;
			useSkills.addAll(getSameQualitySkill(rnd,greenskill,greencount,useprice_green,c));
			useSkills.addAll(getSameQualitySkill(rnd,blueskill,bluecount,useprice_blue,c));
			useSkills.addAll(getSameQualitySkill(rnd,purpleskill,purplecount,useprice_purple,c));
			useSkills.addAll(getSameQualitySkill(rnd,orangeskill,orangecount,useprice_orange,c));
		}
		//printSameQualitySkillInfo(useSkills);
		return useSkills;
	}
	/**
	 * 获得相同品质的技能
	 * @param rnd		//随机数对象
	 * @param ss		//相同品质技能队列
	 * @param sklcount	//取得技能的数量
	 * @param userprice	//所有技能不能超过的价值总和
	 * @param c			//限定的职业
	 * @return			//返回相同品质技能的数组
	 */
	private Array<SimpleSkill> getSameQualitySkill(Random rnd,Array<SimpleSkill> ss,int sklcount,int userprice,CLASSES c){
		Array<SimpleSkill> retss = new Array<SimpleSkill>();
		Array<Integer> saveindex = new Array<Integer>();
		int cc = 0;
		while(true){
			//如果总数超过技能数量直接 推处
			if(cc>=sklcount)
				break;
			//获得任意一个技能序号
			int i=rnd.nextInt(ss.size);
			if(!saveindex.contains(i, true)														//如果该序号使用过直接退出	
					&& (ss.get(i).getPrice()+sumArrayInteger(saveindex)<userprice*sklcount)		//如果总价值超过限制价值退出
					&& includeClasses(c,ss.get(i).getClasses())									//如果要指定的职业不包含在技能职业中退出
					){
					retss.add(ss.get(i));
					saveindex.add(i);
					cc++;
					}
		}
		return retss;
	}
	/**
	 * 判断pc是否包含在pca数组中，如果包含则返回true，否则返回false
	 * @param pc	要判断的职业
	 * @param pca	限定职业范围
	 * @return
	 */
	private boolean includeClasses(CLASSES pc,CLASSES[] pca){
		for(CLASSES c:pca)
			if(c==pc || c==CLASSES.all)
				return true;
		return false;
	}
	
	/**
	 * 计算整形数组中所有数字的和
	 * @param a	带入的整形数组
	 * @return	计算的和
	 */
	private int sumArrayInteger(Array<Integer> a){
		int sum = 0;
		for(Integer i:a)
			sum+=i;
		return sum;
	}
	/**
	 * 打印当前技能队列中所有技能的信息
	 * @param ss
	 */
	private void printSameQualitySkillInfo(Array<SimpleSkill> ss){
		StringBuffer sb = new StringBuffer();
		StringBuffer sbs = new StringBuffer();
		for(SimpleSkill s:ss){
			sb.append("id:").append(s.getId())
				.append("\tprice:").append(s.getPrice()).append(" ")
				.append("\tquality:").append(s.getQuality()).append(" ")
				.append("\tclasses:");
			for(CLASSES c:s.getClasses())
				sb.append(c).append(" ");	
			sb.append("\n");
			
			
			sbs.append(s.getId()).append(",");
		}
		System.out.print(sb.toString());
		System.out.println("new Array<Integer>(new Integer[]{"+sbs.substring(0,sbs.length()-1)+"})");
	}
	
//	public static void main(String[] args){
//		MakeSkillTreeConfigInfo stci = new MakeSkillTreeConfigInfo();
//		System.out.println("green hero skill tree:");
//		stci.makeSkillTree(QUALITY.green,CLASSES.fighter);
//		
//		System.out.println("blue hero skill tree:");
//		stci.makeSkillTree(QUALITY.blue,CLASSES.fighter);
//		
//		System.out.println("purple hero skill tree:");
//		stci.makeSkillTree(QUALITY.purple,CLASSES.fighter);
//		
//		System.out.println("orange hero skill tree:");
//		stci.makeSkillTree(QUALITY.orange,CLASSES.fighter);
////		Array<Integer> arr = new Array<Integer>();
////		arr.add(1);arr.add(2);arr.add(3);
////		for(int j=0;j<5;j++){
////			for(Integer i:arr)
////				System.out.println(i);
////			System.out.println("-------------");
////		}
//		
//	}
}
/**
 * 简单技能类信息，只包括三种数据，技能ID，技能品质，技能价值
 * @author fengchong
 *
 */
class SimpleSkill{
	private int id = 0;
	private QUALITY quality = QUALITY.green;
	private CLASSES[] classes = null; 
	private int price = 0;
	public SimpleSkill(int pid,QUALITY q,CLASSES[] c,int p){
		id = pid;
		quality = q;
		classes = c;
		price = p;
	}
	public int getPrice() {
		return price;
	}
	public int getId() {
		return id;
	}
	public QUALITY getQuality() {
		return quality;
	}
	public CLASSES[] getClasses() {
		return classes;
	}
}