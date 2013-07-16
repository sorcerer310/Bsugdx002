package com.bsu.obj;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.bsu.make.WidgetFactory;
import com.bsu.tools.GC;
import com.bsu.tools.GTC;
import com.bsu.tools.U;
import com.bsu.tools.GC.LEVELYTPE;

public class LevelState {
	public int level;// 关卡
	//public Image icon;
	public TextButton icon;
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
	public LevelState(int lv, boolean b) {
		level = lv+1;
		icon=new TextButton("第"+(level)+"关", U.get_normal_button_style());
		icon.getLabel().setFontScale(0.8f);
		enable = b;
		U.setAlpha(icon, 0.6f);
	}

	/**
	 * 开启关卡
	 */
	public void enableLevel() {
		enable = true;
		icon.getLabel().setColor(255, 0, 255, 1);//开启的关卡设置颜色。。
		//U.setAlpha(icon, 1);
	}
}
