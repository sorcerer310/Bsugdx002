package com.bsu.obj;

public class MapBoxValue {

	int coll;

	int raw;
	/**
	 * 
	 * @param coll 所在格子横列
	 * @param raw 所在格子纵列
	 */
	public MapBoxValue(int coll, int raw) {
		// TODO Auto-generated constructor stub
		this.coll = coll;
		this.raw = raw;
	}

	public int getColl() {
		return coll;
	}

	public int getRaw() {
		return raw;
	}
}
