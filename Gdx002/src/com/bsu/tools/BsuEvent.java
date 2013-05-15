package com.bsu.tools;
/**
 * 事件接口用于通知其他对象一些消息
 * @author fengchong
 *
 */
public abstract class BsuEvent {
	public abstract void notify(Object obj,String msg);
}
