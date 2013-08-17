package com.bsu.obj;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.bsu.make.WidgetFactory;
import com.bsu.tools.GC;
import com.bsu.tools.GTC;
import com.bsu.tools.U;
import com.bsu.tools.GC.LEVELYTPE;

public class LevelButton {
	public int level;// 关卡
	private Image bt_background;
	private TextButton bt_text;
	private WidgetGroup wg = new WidgetGroup();
	public boolean enable;

	/**
	 * 设置关卡图标
	 * 
	 * @param s	        标识
	 * @param stage   舞台
	 * @param lv         第几关
	 * @param x
	 * @param y
	 * @param a         透明度，代表关卡是否开启
	 */
	public LevelButton(int lv, boolean b) {
		level = lv+1;
		bt_background = new Image(GTC.getInstance().bt_level);
		bt_text=new TextButton("第"+(level)+"关", U.get_normal_button_style());
		bt_text.getLabel().setFontScale(0.8f);
		bt_text.setPosition((bt_background.getWidth()-bt_text.getWidth())/2, 16);
		wg.addActor(bt_background);
		wg.addActor(bt_text);
		enable = b;
		bt_text.getLabel().setColor(0.03f, 0.35f, 0.44f, 1.0f);
		U.setAlpha(bt_text, 0.6f);
	}

	/**
	 * 开启关卡
	 */
	public void enableLevel() {
		enable = true;
		bt_text.getLabel().setColor(1, 1, 1, 1);//开启的关卡设置颜色。。
		//U.setAlpha(icon, 1);
	}

	public WidgetGroup getWg() {
		return wg;
	}

	public TextButton getBt_text() {
		return bt_text;
	}
	
	
}
