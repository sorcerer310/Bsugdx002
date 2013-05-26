package com.bsu.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.bsu.obj.Equip;
import com.bsu.obj.Role;
import com.bsu.obj.skilltree.Skill;

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
				GameTextureClass.getInstance().rolePanel, 100, 100));
		Window.WindowStyle ws = new WindowStyle(Configure.get_font(),
				Color.BLACK, skin.getDrawable("draw"));
		tipsWindows = new Window("INOF", ws);
	}

	public void showRoleInfo(Role r, Stage s) {
		if (tipsWindows.getStage() != null) {
			tipsWindows.getParent().removeActor(tipsWindows);
		}
		tipsWindows.clear();
		tipsWindows.setPosition(100, 100);
		tipsWindows.defaults().spaceBottom(10);
		tipsWindows.defaults().spaceRight(20);
		tipsWindows.add(new Label(r.name, Configure.get_sytle()));
		tipsWindows.add(new Label(U.getClasses(r), Configure.get_sytle()));
		tipsWindows.add(new Label(r.level + "", Configure.get_sytle()));
		tipsWindows.row();
		tipsWindows.add(new Label(r.name, Configure.get_sytle()));
		tipsWindows.add(new Label(U.getClasses(r), Configure.get_sytle()));
		tipsWindows.add(new Label(r.level + "", Configure.get_sytle()));
		tipsWindows.row();
		tipsWindows.add(new Label(r.name, Configure.get_sytle()));
		tipsWindows.add(new Label(U.getClasses(r), Configure.get_sytle()));
		tipsWindows.add(new Label(r.level + "", Configure.get_sytle()));
		tipsWindows.row();
		tipsWindows.add(new Label(r.name, Configure.get_sytle()));
		tipsWindows.add(new Label(U.getClasses(r), Configure.get_sytle()));
		tipsWindows.add(new Label(r.level + "", Configure.get_sytle()));
		tipsWindows.pack();
		s.addActor(tipsWindows);
	}

	private void showSkillInfo(Skill s) {

	}

	private void showEquipInfo(Equip e) {

	}
}
