package com.bsu.make;

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
import com.bsu.obj.Equip;
import com.bsu.obj.Role;
import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.CG;
import com.bsu.tools.GTC;
import com.bsu.tools.U;
import com.bsu.tools.CG.QUALITY;

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
		skin.add("draw", new TextureRegion(GTC.getInstance().tipsPanel,
				windowWidth, 60));
		Window.WindowStyle ws = new WindowStyle(U.get_font(), Color.BLACK,
				skin.getDrawable("draw"));
		tipsWindows = new Window("", ws);
		tipsWindows.align(Align.left);
		tipsWindows.setWidth(200);
	}

	/**
	 * 显示更换的人物信息，位置动态
	 * 
	 * @param r
	 * @param s
	 */
	public void showRoleInfo(Role r, Vector2 v, Stage s) {
		removeFromStage();
		tipsWindows.align(Align.center);
		tipsWindows.padTop(10);
		tipsWindows.padBottom(10);
		tipsWindows.add(new Label("" + r.name, U.get_sytle()));
		tipsWindows.row();
		tipsWindows.add(new Label("" + U.getClasses(r), U.get_sytle()));
		tipsWindows.row();
		tipsWindows.add(new Label("lv:" + r.level, U.get_sytle()));
		tipsWindows.row();
		tipsWindows.add(new Label("" + r.exp + "/" + r.expUp, U.get_sytle()));
		tipsWindows.row();
		Table t = new Table();
		for (Skill skill : r.skill_array) {
			t.defaults().align(Align.center);
			t.add(new Image(skill.enable ? skill.icon : GTC.getInstance()
					.getSkillIcon(0)));
			String lvs = skill.enable ? skill.lev + "" : "  ";
			Label l = new Label(lvs, U.get_sytle());
			l.setFontScale(0.5f);
			t.defaults().align(Align.bottom);
			t.add(l);
		}
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
	 * 显示技能描述信息 位置动态
	 * 
	 * @param s
	 */
	public void showSkillInfo(Skill s, Vector2 v, Stage stage) {
		removeFromStage();
		tipsWindows.defaults().align(Align.center);
		tipsWindows.padTop(10);
		tipsWindows.padBottom(10);
		tipsWindows.defaults().padLeft(10);
		tipsWindows.defaults().padRight(10);
		Label nameLabel = new Label(s.name, U.get_sytle());
		nameLabel.setColor(U.getQualityColor(s.quality));
		tipsWindows.add(nameLabel);
		tipsWindows.defaults().align(Align.left);
		Array<String> infoArray = new Array<String>();
		float scaleValue = 0.7f;
		infoArray = U.getMuLabel(s.info, scaleValue, windowWidth);
		for (int i = 0; i < infoArray.size; i++) {
			tipsWindows.row();
			Label label = new Label(infoArray.get(i), U.get_sytle());
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
	public void showTips(String s, Stage stage, Color r) {
		removeFromStage();
		tipsWindows.clear();
		tipsWindows.defaults().align(Align.center);
		tipsWindows.padTop(10);
		tipsWindows.padBottom(10);
		tipsWindows.defaults().padLeft(10);
		tipsWindows.defaults().padRight(10);
		tipsWindows.setPosition(200, 140);
		Array<String> tipsArray = new Array<String>();
		float scaleValue = 0.5f;
		tipsArray = U.getMuLabel(s, scaleValue, windowWidth);
		for (String as : tipsArray) {
			tipsWindows.row();
			Label l = new Label(as, U.get_sytle());
			l.setFontScale(scaleValue);
			l.setColor(r);
			tipsWindows.add(l);
		}
		tipsWindows.pack();
		stage.addActor(tipsWindows);
		tipsWindows.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				tipsWindows.getParent().removeActor(tipsWindows);
			}
		});
	}

	/**
	 * 设置吞噬界面，被选择吞噬的role信息
	 * 
	 * @param r
	 * @param stage
	 */
	public void showEatInfo(Role r, Vector2 v, Stage stage) {
		removeFromStage();
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
		float fonts = 0.5f;
		for (final Skill s : r.skill_tree) {
			Image skill_img = null;
			Table t = null;
			if (s.quality == QUALITY.green) {
				skill_img = new Image(s.enable ? s.icon : GTC.getInstance()
						.getSkillIcon(0));
				t = tg;
			}
			if (s.quality == QUALITY.blue) {
				skill_img = new Image(s.enable ? s.icon : GTC.getInstance()
						.getSkillIcon(0));
				t = tb;
			}
			if (s.quality == QUALITY.purple) {
				skill_img = new Image(s.enable ? s.icon : GTC.getInstance()
						.getSkillIcon(0));
				t = tp;
			}
			if (s.quality == QUALITY.orange) {
				skill_img = new Image(s.enable ? s.icon : GTC.getInstance()
						.getSkillIcon(0));
				t = to;
			}
			String lvs = s.enable ? s.lev + "" : "  ";
			Label l = new Label(lvs, U.get_sytle());
			l.setFontScale(0.5f);
			t.add(skill_img);
			t.defaults().align(Align.bottom);
			t.add(l);
		}
		tipsWindows.add(new Label("lv:" + r.level, U.get_sytle()));
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
		if (tv.x <= CG.rect_width / 2) {
			ax = -1;
		}
		if (tv.y <= CG.rect_height / 2) {
			ay = -1;
		}
		v.x = ax < 0 ? tv.x + CG.map_box_value : tv.x - tipsWindows.getWidth();
		v.y = ay < 0 ? tv.y + CG.map_box_value : tv.y - tipsWindows.getHeight();
		return v;
	}

	/**
	 * 移除tips
	 */
	public void removeFromStage() {
		if (tipsWindows.getStage() != null) {
			tipsWindows.getParent().removeActor(tipsWindows);
		}
		tipsWindows.clear();
	}
}
