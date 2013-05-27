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
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Equip;
import com.bsu.obj.Role;
import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.Configure.QUALITY;

public class TipsWindows {

	private static TipsWindows instance = null;
	private Skin skin;
	public Window tipsWindows;

	public static TipsWindows getInstance() {
		if (instance == null)

			instance = new TipsWindows();
		return instance;
	}

	private TipsWindows() {
		skin = new Skin();
		skin.add("draw", new TextureRegion(
				GameTextureClass.getInstance().tipsPanel ,120, 60));
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
		tipsWindows.defaults().space(0);
		tipsWindows.defaults().align(Align.left);
		tipsWindows.add(new Label("姓名:"+r.name, Configure.get_sytle()));
		tipsWindows.add(new Label("职业:"+U.getClasses(r), Configure.get_sytle()));
		tipsWindows.row();
		tipsWindows.add(new Label("等级:"+r.level, Configure.get_sytle()));
		tipsWindows.add(new Label("经验:"+r.exp+"/"+r.expUp, Configure.get_sytle()));
		tipsWindows.row();
		tipsWindows.add(new Image(r.skill_array.get(0).icon));
		tipsWindows.add(new Image(r.skill_array.get(1).icon));
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
		tipsWindows.defaults().space(0);
		Label nameLabel = new Label(s.name, Configure.get_sytle());
		nameLabel.setColor(Configure.getQualityColor(s.quality));
		tipsWindows.add(nameLabel);
		tipsWindows.defaults().align(Align.left);
		Array<String> infoArray = new Array<String>();
		float scaleValue = 0.7f;
		getMuLabel(infoArray, s.info, scaleValue);
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
		getMuLabel(tipsArray, s,scaleValue);
		for (String as : tipsArray) {
			tipsWindows.row();
			tipsWindows.add(new Label(as, Configure.get_sytle()));
		}
		tipsWindows.pack();
		stage.addActor(tipsWindows);
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

	/**
	 * 将一个字符串分成几行
	 * @param sv 缩放比
	 * @param s
	 * @return 字符串数组
	 */
	private void getMuLabel(Array<String> labelArray, String s, float sv) {
		int nums = (int) (s.length() * sv * Configure.fontSize / tipsWindows
				.getWidth());
		if (s.length() * sv * Configure.fontSize % tipsWindows.getWidth() == 0) {
			nums--;
		}
		int index = 0;
		if (nums > 0) {
			int startIndex = 0;
			while (index <= nums) {
				for (int i = startIndex; i <= s.length(); i++) {
					if (s.substring(startIndex, i).length() * sv
							* Configure.fontSize > tipsWindows.getWidth()) {
						labelArray.add(s.substring(startIndex, i));
						index++;
						startIndex = i;
						break;
					} else {
						if (i == s.length()) {
							labelArray.add(s.substring(startIndex, i));
							index++;
							break;
						}
					}
				}
			}
		} else {
			labelArray.add(s);
		}
	}
}
