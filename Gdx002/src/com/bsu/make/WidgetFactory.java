package com.bsu.make;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.bsu.tools.Configure;
import com.bsu.tools.GameTextureClass;

public class WidgetFactory {
	private static WidgetFactory instance = null;

	private WidgetFactory() {
	}

	public static WidgetFactory getInstance() {
		if (instance == null)
			instance = new WidgetFactory();
		return instance;
	}

	/**
	 * 创建一个文字按钮
	 * 
	 * @return
	 */
	public TextButton makeOneTextButton(String text, Stage s, float x, float y) {
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
		s.addActor(tb);
		return tb;
	}

	/**
	 * 该函数同makeImageButton类似，只不过返回的是Image对象，可以通过一个图片控制按钮的 两种微小调整状态
	 * 
	 * @param bname
	 *            按钮的名字
	 * @return 返回按钮的图片
	 */
	Image img_nomal = null;

	public Image makeImageButton(String bname,Stage s,int x,int y) {

		if (bname.equals(Configure.screen_equip))
			img_nomal = new Image(
					GameTextureClass.getInstance().textureatlas_mbutton
							.findRegion("mb_equip"));
		else if (bname.equals(Configure.screen_fight))
			img_nomal = new Image(
					GameTextureClass.getInstance().textureatlas_mbutton
							.findRegion("mb_fight"));
		else if (bname.equals(Configure.screen_role))
			img_nomal = new Image(
					GameTextureClass.getInstance().textureatlas_mbutton
							.findRegion("mb_role"));
		else if (bname.equals(Configure.screen_update))
			img_nomal = new Image(
					GameTextureClass.getInstance().textureatlas_mbutton
							.findRegion("mb_update"));
		else if (bname.equals(Configure.screen_skill))
			img_nomal = new Image(
					GameTextureClass.getInstance().textureatlas_mbutton
							.findRegion("mb_skill"));
		else if (bname.equals(Configure.screen_shop))
			img_nomal = new Image(
					GameTextureClass.getInstance().textureatlas_mbutton
							.findRegion("mb_shop"));
		else if (bname.equals(Configure.button_back))
			img_nomal = new Image(
					GameTextureClass.getInstance().textureatlas_mbutton
							.findRegion("back"));
		else if (bname.equals(Configure.Img_head_back))
			img_nomal = new Image(
					GameTextureClass.getInstance().textureatlas_mbutton
							.findRegion("frame_head"));
		else if (bname.equals(Configure.Img_skill_back))
			img_nomal = new Image(
					GameTextureClass.getInstance().textureatlas_mbutton
							.findRegion("frame_skill"));

		img_nomal
				.setOrigin(img_nomal.getWidth() / 2, img_nomal.getHeight() / 2);
		img_nomal.setPosition(x, y);
		s.addActor(img_nomal);
		return img_nomal;
	}

	/**
	 * 将一个label 初始化并做好设置，加入指定stage
	 * 
	 * @param ls
	 *            文字
	 * @param s
	 *            stage
	 * @param v
	 *            position
	 */
	public Label makeLabel(String ls, Stage s, int x, int y) {
		Label l = new Label(ls, Configure.get_sytle());
		l.setPosition(x, y);
		s.addActor(l);
		return l;
	}

	/**
	 * 将一个image初始化，并加入指定stage
	 * 
	 * @param tr
	 * @param s
	 * @param scaleValue
	 * @param x
	 * @param y
	 * @return
	 */
	public Image makeImg(TextureRegion tr, Stage s, float scaleValue, int x,
			int y) {
		Image img;
		if (tr == null) {
			img = new Image();
		} else {
			img = new Image(tr);
		}
		img.setScale(scaleValue);
		img.setPosition(x, y);
		s.addActor(img);
		return img;
	}
}
