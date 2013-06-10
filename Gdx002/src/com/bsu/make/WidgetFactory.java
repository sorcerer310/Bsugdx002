package com.bsu.make;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.CG;
import com.bsu.tools.GTC;
import com.bsu.tools.U;

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
	public Image makeImageButton(String bname, Stage s, int x, int y, float a) {
		Image img_nomal = null;
		if (bname.equals(CG.screen_fight))
			img_nomal = new Image(
					GTC.getInstance().atlas_mbutton
							.findRegion("mb_fight"));
		else if (bname.equals(CG.screen_role))
			img_nomal = new Image(
					GTC.getInstance().atlas_mbutton
							.findRegion("mb_role"));
		else if (bname.equals(CG.screen_update))
			img_nomal = new Image(
					GTC.getInstance().atlas_mbutton
							.findRegion("mb_update"));
		else if (bname.equals(CG.screen_shop))
			img_nomal = new Image(
					GTC.getInstance().atlas_mbutton
							.findRegion("mb_shop"));
		else if (bname.equals(CG.button_back))
			img_nomal = new Image(
					GTC.getInstance().atlas_mbutton.findRegion("back"));
		else if (bname.equals(CG.button_green))
			img_nomal = new Image(
					GTC.getInstance().atlas_button.findRegion("green"));
		else if (bname.equals(CG.button_blue))
			img_nomal = new Image(
					GTC.getInstance().atlas_button.findRegion("blue"));
		else if (bname.equals(CG.button_purple))
			img_nomal = new Image(
					GTC.getInstance().atlas_button.findRegion("purple"));
		else if (bname.equals(CG.button_orange))
			img_nomal = new Image(
					GTC.getInstance().atlas_button.findRegion("orange"));
		else if (bname.equals(CG.button_eat))
			img_nomal = new Image(
					GTC.getInstance().atlas_button.findRegion("eat"));
		else if (bname.equals(CG.button_eatall))
			img_nomal = new Image(
					GTC.getInstance().atlas_button.findRegion("eatall"));
		else if (bname.equals(CG.button_level))
			img_nomal = new Image(
					GTC.getInstance().atlas_button.findRegion("level"));
		else if (bname.equals(CG.button_up))
			img_nomal = new Image(
					GTC.getInstance().atlas_button.findRegion("up"));
		else if (bname.equals(CG.button_all))
			img_nomal = new Image(
					GTC.getInstance().atlas_button.findRegion("all"));
		img_nomal.setOrigin(img_nomal.getWidth() / 2, img_nomal.getHeight() / 2);	//设置原点为中心
		img_nomal.setPosition(x, y);												//设置位置
		img_nomal.addListener(new InputListener(){									//设置监听器，按下时候缩小按钮0.95倍，抬起时还原为1倍 
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				event.getListenerActor().setScale(0.95f);
				return super.touchDown(event, x, y, pointer, button);
			}
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				event.getListenerActor().setScale(1.0f);
				super.touchUp(event, x, y, pointer, button);
			}
			
		});
		s.addActor(img_nomal);
		U.setAlpha(img_nomal, a);													//设置按钮的alpha值
		return img_nomal;
	}
	/**
	 * 直接返回个Image按钮对象。
	 * @param bname	按钮名称
	 * @return
	 */
//	public Image makeImageButton(String bname, int x, int y, float a) {
	public Image makeImageButton(String bname) {
		Image img_nomal = null;
		if (bname.equals(CG.screen_fight))
			img_nomal = new Image(
					GTC.getInstance().atlas_mbutton
							.findRegion("mb_fight"));
		else if (bname.equals(CG.screen_role))
			img_nomal = new Image(
					GTC.getInstance().atlas_mbutton
							.findRegion("mb_role"));
		else if (bname.equals(CG.screen_update))
			img_nomal = new Image(
					GTC.getInstance().atlas_mbutton
							.findRegion("mb_update"));
		else if (bname.equals(CG.screen_shop))
			img_nomal = new Image(
					GTC.getInstance().atlas_mbutton
							.findRegion("mb_shop"));
		else if (bname.equals(CG.button_back))
			img_nomal = new Image(
					GTC.getInstance().atlas_mbutton.findRegion("back"));
		else if (bname.equals(CG.button_green))
			img_nomal = new Image(
					GTC.getInstance().atlas_button.findRegion("green"));
		else if (bname.equals(CG.button_blue))
			img_nomal = new Image(
					GTC.getInstance().atlas_button.findRegion("blue"));
		else if (bname.equals(CG.button_purple))
			img_nomal = new Image(
					GTC.getInstance().atlas_button.findRegion("purple"));
		else if (bname.equals(CG.button_orange))
			img_nomal = new Image(
					GTC.getInstance().atlas_button.findRegion("orange"));
		else if (bname.equals(CG.button_eat))
			img_nomal = new Image(
					GTC.getInstance().atlas_button.findRegion("eat"));
		else if (bname.equals(CG.button_eatall))
			img_nomal = new Image(
					GTC.getInstance().atlas_button.findRegion("eatall"));
		else if (bname.equals(CG.button_level))
			img_nomal = new Image(
					GTC.getInstance().atlas_button.findRegion("level"));
		else if (bname.equals(CG.button_up))
			img_nomal = new Image(
					GTC.getInstance().atlas_button.findRegion("up"));
		else if (bname.equals(CG.button_all))
			img_nomal = new Image(
					GTC.getInstance().atlas_button.findRegion("all"));
		img_nomal.setOrigin(img_nomal.getWidth() / 2, img_nomal.getHeight() / 2);
//		img_nomal.setPosition(x, y);
//		U.setApha(img_nomal, a);
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
	public Label makeLabel(String ls, Stage s, float scaleValue, int x, int y) {
		Label l = new Label(ls, U.get_sytle());
		l.setPosition(x, y);
		l.setFontScale(scaleValue);
		s.addActor(l);
		return l;
	}

	public Label makeLabel(String ls, Stage s, float scaleValue, int x, int y,
			Color c) {
		Label l = new Label(ls, U.get_sytle());
		l.setPosition(x, y);
		l.setFontScale(scaleValue);
		if (c != null) {
			l.setColor(c);
		}
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
			return null;
		} else {
			img = new Image(tr);
		}
		img.setScale(scaleValue);
		img.setPosition(x, y);
		s.addActor(img);
		return img;
	}

	/**
	 * 返回一个pixMap绘制的矩形图像，用于头像技能边框及其填充。
	 * 
	 * @param maxValue
	 *            宽高
	 * @param dc
	 *            背景色
	 * @param c
	 *            边框色
	 * @param a
	 *            透明度
	 * @return
	 */
	public TextureRegion getPixmapFrame(int w, int h, Color dc, Color c, float a) {
		c.a = a;
		TextureRegion temp_box = null;
		Pixmap pixmap;
		pixmap = new Pixmap(w + 2, h + 2, Format.RGBA8888);
		pixmap.setColor(dc);
		pixmap.drawRectangle(0, 0, w + 2, h + 2);
		pixmap.setColor(c);
		pixmap.fillRectangle(1, 1, w, h);
		Texture pixmaptex = new Texture(pixmap);
		temp_box = new TextureRegion(pixmaptex);
		pixmap.dispose();
		return temp_box;
	}
	
	/**
	 * 取得一个填充的图像
	 * 
	 * @param w
	 * @param h
	 * @param c
	 * @return
	 */
	public TextureRegion getTextureFill(int w, int h, Color c, float a) {
		TextureRegion temp_box = null;
		Pixmap pixmap;
		c.a = a;
		pixmap = new Pixmap(w, h, Format.RGBA8888);
		pixmap.setColor(c);
		pixmap.fillRectangle(0, 0, w, h);
		Texture pixmaptex = new Texture(pixmap);
		temp_box = new TextureRegion(pixmaptex, w, h);
		pixmap.dispose();
		return temp_box;
	}
}
