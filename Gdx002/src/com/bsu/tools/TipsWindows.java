package com.bsu.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
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
				GameTextureClass.getInstance().rolePanel, 200, 20));
		Window.WindowStyle ws = new WindowStyle(Configure.get_font(),
				Color.BLACK, skin.getDrawable("draw"));
		tipsWindows = new Window("INOF", ws);
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
		tipsWindows.defaults().spaceBottom(10);
		tipsWindows.defaults().spaceRight(20);
		tipsWindows.add(new Label(r.name, Configure.get_sytle(Color.ORANGE)));
		tipsWindows.add(new Label(U.getClasses(r), Configure.get_sytle(Color.ORANGE)));
		tipsWindows.add(new Label(r.level + "", Configure.get_sytle(Color.ORANGE)));
		tipsWindows.row();
		tipsWindows.add(new Label(r.name, Configure.get_sytle(Color.ORANGE)));
		tipsWindows.add(new Label(U.getClasses(r), Configure.get_sytle(Color.ORANGE)));
		tipsWindows.add(new Label(r.level + "", Configure.get_sytle(Color.ORANGE)));
		tipsWindows.row();
		tipsWindows.add(new Label(r.name, Configure.get_sytle(Color.ORANGE)));
		tipsWindows.add(new Label(U.getClasses(r), Configure.get_sytle(Color.ORANGE)));
		tipsWindows.add(new Label(r.level + "", Configure.get_sytle(Color.ORANGE)));
		tipsWindows.row();
		tipsWindows.add(new Label(r.name, Configure.get_sytle(Color.ORANGE)));
		tipsWindows.add(new Label(U.getClasses(r), Configure.get_sytle(Color.ORANGE)));
		tipsWindows.add(new Label(r.level + "", Configure.get_sytle(Color.ORANGE)));
		tipsWindows.pack();
		tipsWindows.setPosition(getPosition(v).x, getPosition(v).y);
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
		tipsWindows.defaults().spaceBottom(10);
		tipsWindows.row();
		tipsWindows.add(new Label(s.name, Configure.get_sytle(Color.ORANGE)));
		Array<String> infoArray = new Array<String>();
		getMuLabel(infoArray, s.info);

		for (String as : infoArray) {
			tipsWindows.row();
			Label label=new Label(as, Configure.get_sytle(Color.ORANGE));
			tipsWindows.add(label);
		}
		tipsWindows.pack();
		tipsWindows.setPosition(getPosition(v).x, getPosition(v).y);
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
		getMuLabel(tipsArray, s);
		for (String as : tipsArray) {
			tipsWindows.row();
			tipsWindows.add(new Label(as, Configure.get_sytle(Color.GREEN)));
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
	 * 
	 * @param s
	 * @return 字符串数组
	 */
	private void getMuLabel(Array<String> labelArray, String s) {
		int nums = (int) (s.length() * Configure.fontSize / tipsWindows
				.getWidth());
		if (s.length() * Configure.fontSize % tipsWindows.getWidth() == 0) {
			nums--;
		}
		int index = 0;
		if (nums > 0) {
			int startIndex = 0;
			while (index <= nums) {
				for (int i = startIndex; i <= s.length(); i++) {
					if (s.substring(startIndex, i).length()
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
