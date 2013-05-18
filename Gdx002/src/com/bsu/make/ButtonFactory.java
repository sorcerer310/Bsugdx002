package com.bsu.make;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.bsu.tools.Configure;

public class ButtonFactory {
	private static ButtonFactory instance = null;

	private ButtonFactory() {
	}

	public static ButtonFactory getInstance() {
		if (instance == null)
			instance = new ButtonFactory();
		return instance;
	}

	/**
	 * ���һ��һ����ʽ�����ְ�ť
	 * 
	 * @return
	 */
	public TextButton makeOneTextButton(String text, float x, float y) {
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		Skin skin = new Skin();
		skin.add("white", new Texture(pixmap));
		skin.add("default", new BitmapFont());
		TextButtonStyle tbs = new TextButtonStyle();
		tbs.up = skin.newDrawable("white", Color.DARK_GRAY);
		tbs.down = skin.newDrawable("white", Color.DARK_GRAY);
		// tbs.checked = skin.newDrawable("white",Color.BLUE);
		tbs.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		tbs.font = skin.getFont("default");
		skin.add("default", tbs);
		TextButton tb = new TextButton(text, skin);
		tb.setPosition(x, y);
		return tb;
	}

	/**
	 * 制作一个ImageButton
	 * 
	 * @param img
	 *            带入Image参数
	 */
	public ImageButton makeImageButton(String bname){
//		TextureRegion nomal = ImageFactory.getInstance().skin.get("mb_equip",TextureRegion.class);
		Image img_nomal = null;
		if(bname.equals(Configure.screen_equip))
			img_nomal = ImageFactory.getInstance().mb_equip;
		else if(bname.equals(Configure.screen_fight))
			img_nomal = ImageFactory.getInstance().mb_fight;
		else if(bname.equals(Configure.screen_role))
			img_nomal = ImageFactory.getInstance().mb_role;
		else if(bname.equals(Configure.screen_update))
			img_nomal = ImageFactory.getInstance().mb_update;
		else if(bname.equals(Configure.screen_skill))
			img_nomal = ImageFactory.getInstance().mb_skill;
		else if(bname.equals(Configure.screen_shop))
			img_nomal = ImageFactory.getInstance().mb_shop;
		ImageButton ib = new ImageButton(img_nomal.getDrawable());
		return ib;
	}
}
