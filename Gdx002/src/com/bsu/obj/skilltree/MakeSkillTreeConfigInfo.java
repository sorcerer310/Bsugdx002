package com.bsu.obj.skilltree;

import java.util.Random;
import com.badlogic.gdx.utils.Array;
import com.bsu.tools.Configure;
import com.bsu.tools.Configure.QUALITY;

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
		greenskill.add(new SimpleSkill(1,QUALITY.green,8));
		greenskill.add(new SimpleSkill(2,QUALITY.green,1));
		greenskill.add(new SimpleSkill(3,QUALITY.green,4));
		greenskill.add(new SimpleSkill(4,QUALITY.green,9));
		greenskill.add(new SimpleSkill(5,QUALITY.green,6));
		greenskill.add(new SimpleSkill(6,QUALITY.green,4));
		greenskill.add(new SimpleSkill(7,QUALITY.green,4));
		greenskill.add(new SimpleSkill(8,QUALITY.green,7));
		greenskill.add(new SimpleSkill(9,QUALITY.green,4));
		blueskill.add(new SimpleSkill(10,QUALITY.blue,7));
		blueskill.add(new SimpleSkill(11,QUALITY.blue,16));
		blueskill.add(new SimpleSkill(12,QUALITY.blue,16));
		blueskill.add(new SimpleSkill(13,QUALITY.blue,7));
		blueskill.add(new SimpleSkill(14,QUALITY.blue,10));
		blueskill.add(new SimpleSkill(15,QUALITY.blue,9));
		blueskill.add(new SimpleSkill(16,QUALITY.blue,7));
		blueskill.add(new SimpleSkill(17,QUALITY.blue,8));
		blueskill.add(new SimpleSkill(18,QUALITY.blue,12));
		blueskill.add(new SimpleSkill(19,QUALITY.blue,10));
		blueskill.add(new SimpleSkill(20,QUALITY.blue,13));
		purpleskill.add(new SimpleSkill(21,QUALITY.purple,25));
		purpleskill.add(new SimpleSkill(22,QUALITY.purple,16));
		purpleskill.add(new SimpleSkill(23,QUALITY.purple,17));
		purpleskill.add(new SimpleSkill(24,QUALITY.purple,15));
		purpleskill.add(new SimpleSkill(25,QUALITY.purple,22));
		purpleskill.add(new SimpleSkill(26,QUALITY.purple,15));
		purpleskill.add(new SimpleSkill(27,QUALITY.purple,19));
		purpleskill.add(new SimpleSkill(28,QUALITY.purple,21));
		purpleskill.add(new SimpleSkill(29,QUALITY.purple,25));
		purpleskill.add(new SimpleSkill(30,QUALITY.purple,18));
		purpleskill.add(new SimpleSkill(31,QUALITY.purple,22));
		purpleskill.add(new SimpleSkill(32,QUALITY.purple,22));
		purpleskill.add(new SimpleSkill(33,QUALITY.purple,23));
		purpleskill.add(new SimpleSkill(34,QUALITY.purple,24));
		orangeskill.add(new SimpleSkill(35,QUALITY.orange,32));
		orangeskill.add(new SimpleSkill(36,QUALITY.orange,26));
		orangeskill.add(new SimpleSkill(37,QUALITY.orange,25));
		orangeskill.add(new SimpleSkill(38,QUALITY.orange,25));
		orangeskill.add(new SimpleSkill(39,QUALITY.orange,24));
		orangeskill.add(new SimpleSkill(40,QUALITY.orange,28));
		orangeskill.add(new SimpleSkill(41,QUALITY.orange,31));
	}
	
	public void makeSkillTree(Configure.QUALITY hqual){
		int greencount,bluecount,purplecount,orangecount;
		int useprice_green = 8,useprice_blue = 15,useprice_purple = 23,useprice_orange = 30;
		Array<SimpleSkill> useSkills = new Array<SimpleSkill>();
		Random rnd = new Random();
		if(hqual == QUALITY.green){
			greencount = 3;bluecount = 3;purplecount = 0;orangecount = 0;
			useSkills.addAll(getSameQualitySkill(rnd,greenskill,greencount,useprice_green));
			useSkills.addAll(getSameQualitySkill(rnd,blueskill,bluecount,useprice_blue));
		}else if(hqual == QUALITY.blue){
			greencount = 3;bluecount = 5;purplecount = 1;orangecount = 0;
			useSkills.addAll(getSameQualitySkill(rnd,greenskill,greencount,useprice_green));
			useSkills.addAll(getSameQualitySkill(rnd,blueskill,bluecount,useprice_blue));
			useSkills.addAll(getSameQualitySkill(rnd,purpleskill,purplecount,useprice_purple));
		}else if(hqual == QUALITY.purple){
			greencount = 3;bluecount = 5;purplecount = 3;orangecount = 1;
			useSkills.addAll(getSameQualitySkill(rnd,greenskill,greencount,useprice_green));
			useSkills.addAll(getSameQualitySkill(rnd,blueskill,bluecount,useprice_blue));
			useSkills.addAll(getSameQualitySkill(rnd,purpleskill,purplecount,useprice_purple));
			useSkills.addAll(getSameQualitySkill(rnd,orangeskill,orangecount,useprice_orange));
		}else if(hqual == QUALITY.orange){
			greencount = 1;bluecount = 5;purplecount = 5;orangecount = 3;
			useSkills.addAll(getSameQualitySkill(rnd,greenskill,greencount,useprice_green));
			useSkills.addAll(getSameQualitySkill(rnd,blueskill,bluecount,useprice_blue));
			useSkills.addAll(getSameQualitySkill(rnd,purpleskill,purplecount,useprice_purple));
			useSkills.addAll(getSameQualitySkill(rnd,orangeskill,orangecount,useprice_orange));
		}
		printSameQualitySkillInfo(useSkills);
	}
	/**
	 * 获得相同品质的技能
	 * @param rnd		//随机数对象
	 * @param ss		//相同品质技能队列
	 * @param sklcount	//取得技能的数量
	 * @param userprice	//所有技能不能超过的价值总和
	 * @return			//返回相同品质技能的数组
	 */
	private Array<SimpleSkill> getSameQualitySkill(Random rnd,Array<SimpleSkill> ss,int sklcount,int userprice){
		Array<SimpleSkill> retss = new Array<SimpleSkill>();
		Array<Integer> saveindex = new Array<Integer>();
		int cc = 0;
		while(true){
			if(cc>=sklcount)
				break;
			int i=rnd.nextInt(ss.size);
			if(!saveindex.contains(i, true)
					&& (ss.get(i).getPrice()+sumArrayInteger(saveindex)<userprice*sklcount)){
					retss.add(ss.get(i));
					saveindex.add(i);
					cc++;
					}
		}
		return retss;
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
				.append("\tquality:").append(s.getQuality()).append("\n");
			sbs.append(s.getId()).append(",");
		}
		System.out.print(sb.toString());
//		System.out.println("Array skltree1 = new Array<Integer>(){"+sbs.substring(0, sbs.length()-1)+"};\n");
		System.out.println("new Array<Integer>(new Integer[]{"+sbs.substring(0,sbs.length()-1)+"})");
	}
	
	public static void main(String[] args){
		MakeSkillTreeConfigInfo stci = new MakeSkillTreeConfigInfo();
		System.out.println("green hero skill tree:");
		stci.makeSkillTree(QUALITY.green);
		
		System.out.println("blue hero skill tree:");
		stci.makeSkillTree(QUALITY.blue);
		
		System.out.println("purple hero skill tree:");
		stci.makeSkillTree(QUALITY.purple);
		
		System.out.println("orange hero skill tree:");
		stci.makeSkillTree(QUALITY.orange);
	}
}
/**
 * 简单技能类信息，只包括三种数据，技能ID，技能品质，技能价值
 * @author fengchong
 *
 */
class SimpleSkill{
	private int id = 0;
	private Configure.QUALITY quality = QUALITY.green;
	private int price = 0;
	public SimpleSkill(int pid,QUALITY q,int p){
		id = pid;
		quality = q;
		price = p;
	}
	public int getPrice() {
		return price;
	}
	public int getId() {
		return id;
	}
	public Configure.QUALITY getQuality() {
		return quality;
	}
}