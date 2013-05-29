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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
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
	private Image role_photo;// 选中角色后的特效图片
	private WidgetFactory() {
		role_photo = new Image(GameTextureClass.getInstance().role_effect);
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
	Image img_nomal = null;

	public Image makeImageButton(String bname, Stage s, int x, int y) {

		if (bname.equals(Configure.screen_equip))
			img_nomal = new Image(
					GameTextureClass.getInstance().texture_atlas_mbutton
							.findRegion("mb_equip"));
		else if (bname.equals(Configure.screen_fight))
			img_nomal = new Image(
					GameTextureClass.getInstance().texture_atlas_mbutton
							.findRegion("mb_fight"));
		else if (bname.equals(Configure.screen_role))
			img_nomal = new Image(
					GameTextureClass.getInstance().texture_atlas_mbutton
							.findRegion("mb_role"));
		else if (bname.equals(Configure.screen_update))
			img_nomal = new Image(
					GameTextureClass.getInstance().texture_atlas_mbutton
							.findRegion("mb_update"));
		else if (bname.equals(Configure.screen_skill))
			img_nomal = new Image(
					GameTextureClass.getInstance().texture_atlas_mbutton
							.findRegion("mb_skill"));
		else if (bname.equals(Configure.screen_shop))
			img_nomal = new Image(
					GameTextureClass.getInstance().texture_atlas_mbutton
							.findRegion("mb_shop"));
		else if (bname.equals(Configure.button_back))
			img_nomal = new Image(
					GameTextureClass.getInstance().texture_atlas_mbutton
							.findRegion("back"));
		else if (bname.equals(Configure.Img_head_back))
			img_nomal = new Image(
					GameTextureClass.getInstance().texture_atlas_mbutton
							.findRegion("frame_head"));
		else if (bname.equals(Configure.Img_skill_back))
			img_nomal = new Image(
					GameTextureClass.getInstance().texture_atlas_mbutton
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

	public Label makeLabel(String ls, Stage s, int x, int y, Color c) {
		Label l = new Label(ls, Configure.get_sytle());
		l.setPosition(x, y);
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
	 * 返回一个pixMap绘制的图像
	 * 
	 * @param maxValue
	 *            宽高
	 * @param c
	 *            颜色
	 * @param a
	 *            透明度
	 * @return
	 */
	public TextureRegion getTexture(int maxValue, Color dc,
			Color c, float a) {
		c.a = a;
		TextureRegion temp_box = null;
		Pixmap pixmap;
		pixmap = new Pixmap(maxValue, maxValue, Format.RGBA8888);
		pixmap.setColor(dc);
		pixmap.drawRectangle(0, 0, maxValue, maxValue);
		pixmap.setColor(c);
		pixmap.fillRectangle(1, 1, maxValue - 2, maxValue - 2);
		Texture pixmaptex = new Texture(pixmap);
		temp_box = new TextureRegion(pixmaptex, maxValue, maxValue);
		pixmap.dispose();
		return temp_box;
	}
	/**
	 * 设置人物选中后的效果
	 * @param role_effect
	 * @param s
	 * @param vs
	 */
	public void showRoleEffect(Stage s, Vector2 vs) {
		if (role_photo.getStage() != null) {
			role_photo.getParent().removeActor(role_photo);
		}
		role_photo.getActions().clear();
		s.addActor(role_photo);
		role_photo.setPosition(vs.x - 17, vs.y - 17);
		RepeatAction ra = new RepeatAction();
		ra.setAction(sequence(moveBy(0, 50, 1f), moveBy(50, 0, 1f),
				moveBy(0, -50, 1f), moveBy(-50, 0, 1f)));
		ra.setCount(RepeatAction.FOREVER);
		role_photo.addAction(ra);
	}
	
	public void showRoleEffect(Image role_photo,Stage s, Vector2 vs) {
		if (role_photo.getStage() != null) {
			role_photo.getParent().removeActor(role_photo);
		}
		role_photo.getActions().clear();
		s.addActor(role_photo);
		role_photo.setPosition(vs.x - 17, vs.y - 17);
		RepeatAction ra = new RepeatAction();
		ra.setAction(sequence(moveBy(0, 50, 1f), moveBy(50, 0, 1f),
				moveBy(0, -50, 1f), moveBy(-50, 0, 1f)));
		ra.setCount(RepeatAction.FOREVER);
		role_photo.addAction(ra);
	}
}
