package com.bsu.tools;

import com.bsu.obj.Role;

/**
 * 参数类
 * @author zhangyongchen
 *	message 消息
 *	o 传入的object 
 */
public class MessageObject {

	public String message;
	public Role o;
	public MessageObject(Role r,String s) {
		// TODO Auto-generated constructor stub
		message=s;
		o=r;
	}
}
