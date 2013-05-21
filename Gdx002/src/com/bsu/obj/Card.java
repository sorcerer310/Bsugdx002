package com.bsu.obj;


import com.bsu.make.CardFactory;
import com.bsu.make.CardFactory.SUBTYPE;
import com.bsu.tools.Configure.QUALITY;
import com.bsu.tools.RoleValue;

/**
 * 卡片类
 * @author zhangyongchen
 *
 */
public class Card {
	private SUBTYPE type;//类型，是谁？？
	private QUALITY quality;//品质
	private RoleValue roleValue;
	public Card(SUBTYPE p,QUALITY q) {
		// TODO Auto-generated constructor stub
		type=p;
		quality=q;
		roleValue=CardFactory.getInstance().getValue(type, quality);
	}
	public RoleValue getRoleValue() {
		return roleValue;
	}
}
