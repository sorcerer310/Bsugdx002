package com.bsu.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.Equip;
import com.bsu.obj.Role;
import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.Configure.QUALITY;

public class TipsWindows {

	private static TipsWindows instance = null;
	private Skin skin;
	private int windowWidth = 120;
	public Window tipsWindows;

	public static TipsWindows getInstance() {
		if (instance == null)

			instance = new TipsWindows();
		return instance;
	}

	private TipsWindows() {
		skin = new Skin();
		skin.add("draw", new TextureRegion(
				GameTextureClass.getInstance().tipsPanel, windowWidth, 60));
		Window.WindowStyle ws = new WindowStyle(Configure.get_font(),
				Color.BLACK, skin.getDrawable("draw"));
		tipsWindows = new Window("", ws);
		tipsWindows.align(Align.left);
		tipsWindows.setWidth(200);
	}

	/**
	 * 显示人物信息，位置动态
	 * 
	 * @param r
	 * @param s
	 */
	public void showRoleInfo(Role r, Vector2 v, Stage s) {
		if (tipsWindows.getStage() != null) {
			tipsWindows.getParent().removeActor(tipsWindows);
		}
		tipsWindows.clear();
		tipsWindows.align(Align.center);
		tipsWindows.padTop(10);
		tipsWindows.padBottom(10);
		tipsWindows.add(new Label("" + r.name, Configure.get_sytle()));
		tipsWindows.row();
		tipsWindows.add(new Label("" + U.getClasses(r), Configure.get_sytle()));
		tipsWindows.row();
		tipsWindows.add(new Label("lv:" + r.level, Configure.get_sytle()));
		tipsWindows.row();
		tipsWindows.add(new Label("" + r.exp + "/" + r.expUp, Configure
				.get_sytle()));
		tipsWindows.row();
		Table t = new Table();
		t.defaults().padLeft(10);
		t.add(new Image(r.skill_array.get(0).icon));
		t.defaults().padRight(10);
		t.add(new Image(r.skill_array.get(1).icon));
		tipsWindows.add(t);
		tipsWindows.pack();
		tipsWindows.setPosition(getPosition(v).x, getPosition(v).y);
		tipsWindows.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				tipsWindows.getParent().removeActor(tipsWindows);
			}
		});
		s.addActor(tipsWindows);
	}

	/**
	 * 显示技能信息 位置动态
	 * 
	 * @param s
	 */
	public void showSkillInfo(Skill s, Vector2 v, Stage stage) {
		if (tipsWindows.getStage() != null) {
			tipsWindows.getParent().removeActor(tipsWindows);
		}
		tipsWindows.clear();
		tipsWindows.defaults().align(Align.center);
		tipsWindows.padTop(10);
		tipsWindows.padBottom(10);
		tipsWindows.defaults().padLeft(10);
		tipsWindows.defaults().padRight(10);
		Label nameLabel = new Label(s.name, Configure.get_sytle());
		nameLabel.setColor(Configure.getQualityColor(s.quality));
		tipsWindows.add(nameLabel);
		tipsWindows.defaults().align(Align.left);
		Array<String> infoArray = new Array<String>();
		float scaleValue = 0.7f;
		infoArray = U.getMuLabel(s.info, scaleValue, windowWidth);
		for (int i = 0; i < infoArray.size; i++) {
			tipsWindows.row();
			Label label = new Label(infoArray.get(i), Configure.get_sytle());
			label.setFontScale(scaleValue);
			tipsWindows.add(label);
		}
		tipsWindows.pack();
		tipsWindows.setPosition(getPosition(v).x, getPosition(v).y);
		tipsWindows.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				tipsWindows.getParent().removeActor(tipsWindows);
			}
		});
		stage.addActor(tipsWindows);
	}

	/**
	 * 显示装备信息 位置动态
	 * 
	 * @param e
	 */
	private void showEquipInfo(Equip e, Stage stage) {

	}

	/**
	 * 显示一条基本信息，tips 固定位置，屏幕中间
	 */
	private void showTips(String s, Stage stage) {
		if (tipsWindows.getStage() != null) {
			tipsWindows.getParent().removeActor(tipsWindows);
		}
		tipsWindows.clear();
		tipsWindows.setPosition(100, 100);
		tipsWindows.defaults().spaceBottom(10);
		tipsWindows.defaults().spaceRight(20);
		Array<String> tipsArray = new Array<String>();
		float scaleValue = 0.7f;
		tipsArray = U.getMuLabel(s, scaleValue, tipsWindows.getWidth());
		for (String as : tipsArray) {
			tipsWindows.row();
			tipsWindows.add(new Label(as, Configure.get_sytle()));
		}
		tipsWindows.pack();
		stage.addActor(tipsWindows);
	}

	/**
	 * 设置吞噬界面，被选择吞噬的role信息
	 * 
	 * @param r
	 * @param stage
	 */
	public void showEatInfo(Role r, Vector2 v, Stage stage) {

		if (tipsWindows.getStage() != null) {
			tipsWindows.getParent().removeActor(tipsWindows);
		}
		tipsWindows.clear();
		tipsWindows.setHeight(10);
		tipsWindows.align(Align.center);
		tipsWindows.defaults().spaceBottom(5);
		tipsWindows.defaults().spaceTop(5);
		tipsWindows.padBottom(10);
		Table to = new Table();
		to.align(Align.center);
		Table tp = new Table();
		tp.align(Align.center);
		Table tb = new Table();
		tb.align(Align.center);
		Table tg = new Table();
		tg.align(Align.center);

		for (final Skill s : r.skill_tree) {
			Image skill_img = null;
			if (s.quality == QUALITY.green) {
				skill_img = new Image(s.icon);
				tg.add(skill_img);
				Label l=new Label(""+s.lev,Configure.get_sytle());
				l.setFontScale(0.5f);
				tg.defaults().align(Align.bottom);
				tg.add(l);
			}
			if (s.quality == QUALITY.blue) {
				skill_img = new Image(s.icon);
				tb.add(skill_img);
				Label l=new Label(""+s.lev,Configure.get_sytle());
				l.setFontScale(0.5f);
				tb.defaults().align(Align.bottom);
				tb.add(l);
			}
			if (s.quality == QUALITY.purple) {
				skill_img = new Image(s.icon);
				tp.add(skill_img);
				Label l=new Label(""+s.lev,Configure.get_sytle());
				l.setFontScale(0.5f);
				tp.defaults().align(Align.bottom);
				tp.add(l);
			}
			if (s.quality == QUALITY.orange) {
				skill_img = new Image(s.icon);
				to.add(skill_img);
				Label l=new Label(""+s.lev,Configure.get_sytle());
				l.setFontScale(0.5f);
				to.defaults().align(Align.bottom);
				to.add(l);
			}
		}
		tipsWindows.add(new Label("lv:" + r.level, Configure.get_sytle()));
		tipsWindows.row();
		tipsWindows.add(to);
		tipsWindows.row();
		tipsWindows.add(tp);
		tipsWindows.row();
		tipsWindows.add(tb);
		tipsWindows.row();
		tipsWindows.add(tg);
		tipsWindows.pack();
		tipsWindows.setPosition(getPosition(v).x, getPosition(v).y);
		stage.addActor(tipsWindows);
		tipsWindows.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				tipsWindows.getParent().removeActor(tipsWindows);
			}
		});
	}

	/**
	 * 根据传入坐标，返回tips应该所在位置
	 * 
	 * @param f
	 * @param g
	 */
	private Vector2 getPosition(Vector2 tv) {
		Vector2 v = new Vector2();
		int ax = 0, ay = 0;
		if (tv.x <= Configure.rect_width / 2) {
			ax = -1;
		}
		if (tv.y <= Configure.rect_height / 2) {
			ay = -1;
		}
		v.x = ax < 0 ? tv.x + Configure.map_box_value : tv.x
				- tipsWindows.getWidth();
		v.y = ay < 0 ? tv.y + Configure.map_box_value : tv.y
				- tipsWindows.getHeight();
		return v;
	}
}
