package com.bsu.obj;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Skill {
	public static enum Type {ATK,HP};		//�������ͣ��ݶ������ͼ�Ѫ����
	private Type type = Type.ATK;			//�������ͱ���
	private String name = "";				//������
	private float val = 0;					//����Ч��ֵ
	private Animation ani_self = null;		//������
	private Animation ani_object = null;	//�ͷŶ��󶯻�
	private String info = "";				//���ܽ�����Ϣ
	
	public Skill(String n,Type t,float v,Animation as,Animation ao,String i){
		name = n;
		type = t;
		val = v;
		ani_self = as;
		ani_object = ao;
		info = i;
	}
	/**
	 * ���ż���������
	 * @param a		�����ͷ���
	 */
	public void playSelfAni(Actor a){
		float x = a.getX();
		float y = a.getY();
		
	}
	/**
	 * ���ż���Ŀ�궯�� 
	 * @param a		���ܽ�����
	 */
	public void playObjAni(Actor a){
		float x = a.getX();
		float y = a.getY();
	}

}
