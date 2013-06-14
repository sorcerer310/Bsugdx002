package com.bsu.obj;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bsu.make.WidgetFactory;
import com.bsu.tools.GC;
import com.bsu.tools.GTC;
import com.bsu.tools.U;
import com.bsu.tools.GC.LEVELYTPE;

public class LevelState {
	public int level;// 关卡
	public Image icon;
	public LEVELYTPE state;
	public boolean enable;

	/**
	 * 设置关卡图标
	 * 
	 * @param s
	 *            标识
	 * @param stage
	 *            舞台
	 * @param lv
	 *            第几关
	 * @param x
	 * @param y
	 * @param a
	 *            透明度，代表关卡是否开启
	 */
	public LevelState(LEVELYTPE s, int lv, boolean b) {
		state = s;
		level = lv;
		icon = new Image(GTC.getInstance().atlas_button.findRegion("level"));
		enable = b;
		U.setAlpha(icon, 0.4f);
	}

	/**
	 * 开启关卡
	 */
	public void enableLevel() {
		enable = true;
		U.setAlpha(icon, 1);
	}
}
