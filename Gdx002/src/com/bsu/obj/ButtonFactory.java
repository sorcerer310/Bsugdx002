package com.bsu.obj;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class ButtonFactory {
	private static ButtonFactory instance = null;
	private ButtonFactory(){}
	public static ButtonFactory getInstance(){
		if(instance==null)
			instance = new ButtonFactory();
		return instance;
	}
	/**
	 * 获得一个一般样式的文字按钮
	 * @return
	 */
	public TextButton getOneTextButton(String text,float x,float y){
		Pixmap pixmap = new Pixmap(1,1,Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		Skin skin = new Skin();
		skin.add("white", new Texture(pixmap));
		skin.add("default", new BitmapFont());
		TextButtonStyle tbs = new TextButtonStyle();
		tbs.up = skin.newDrawable("white",Color.DARK_GRAY);
		tbs.down = skin.newDrawable("white",Color.DARK_GRAY);
		//tbs.checked = skin.newDrawable("white",Color.BLUE);
		tbs.over = skin.newDrawable("white",Color.LIGHT_GRAY);
		tbs.font = skin.getFont("default");
		skin.add("default", tbs);
		TextButton tb = new TextButton("end round",skin);
		tb.setX(x);
		tb.setY(y);
		return tb;
	}
}
