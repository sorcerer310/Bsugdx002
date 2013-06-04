package com.bsu.obj;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.bsu.make.WidgetFactory;
import com.bsu.screen.GameScreen;
import com.bsu.tools.U;

/**
 * GAME游戏战斗场景UI
 * 
 * @author zhangyongchen
 * 
 */
public class UIRoleState implements Observer{

	Stage stage;
	TextButton bt_endround;
	Commander c;
	Array<roleUIInfo> hpArray = new Array<roleUIInfo>();

	public UIRoleState(Stage s) {
		// TODO Auto-generated constructor stub
		stage = s;
		stage.clear();
		c = Commander.getInstance();
		show_hero_state();
	}

	/**
	 * 绘画每个角色状态
	 * 
	 * @param roleIndex
	 */
	public void show_hero_state() {
		stage.clear();
		hpArray.clear();
		bt_endround = WidgetFactory.getInstance().makeOneTextButton("end",
				stage, 200, 70);
		bt_endround.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				c.roundEnd();
			}
		});
		for (int i = 0; i < Player.getInstance().playerFightRole.size; i++) {
			int x = 20 + i * 90;
			int y = 10;
			final Role r = Player.getInstance().playerFightRole.get(i);
			Vector2 v = new Vector2(x, y);
			RolePhoto photo = new RolePhoto(r, stage, v, false);
			roleUIInfo rui = new roleUIInfo(r, stage, x, y);
			rui.photoImg = photo.role;
			rui.role_classes=photo.role_classes;
			hpArray.add(rui);
			final Array<Image> imgArray=new Array<Image>();
			for (int j = 0; j < r.skill_array.size; j++) {
				final int tempIndex = j;
				final Image skillImg = WidgetFactory.getInstance().showSkillImg(
						r.skill_array.get(j), stage,
						new Vector2(x + 50, y + 24 - j * 26),false);
				imgArray.add(skillImg);
				if(j==0){
					U.setApha(skillImg, 1);
				}
				if (r.skill_array.get(tempIndex).enable) {
					skillImg.addListener(new InputListener() {
						@Override
						public boolean touchDown(InputEvent event, float x,
								float y, int pointer, int button) {
							return true;
						}

						@Override
						public void touchUp(InputEvent event, float x, float y,
								int pointer, int button) {
							r.cskill = r.skill_array.get(tempIndex);
							U.setSkillImg(imgArray, skillImg);
							super.touchUp(event, x, y, pointer, button);
						}
					});
				}
			}
		}
	}

	/**
	 * 设置UI HP变化
	 * 
	 * @param r
	 */
	public void changeRoleHp(Role r) {
		for (int i = 0; i < Player.getInstance().playerFightRole.size; i++) {
			if (Player.getInstance().playerFightRole.get(i).equals(r)) {
				roleUIInfo rui = hpArray.get(i);
				if (r.getCurrentHp() > 0) {
					rui.hpImg.setScaleY((float) (r.getCurrentHp())
							/ (float) (r.maxHp));
				} else {
					//头像变暗
					rui.hpImg.setScaleY(0);
					U.setApha(rui.photoImg, 0.2f);
					U.setApha(rui.role_classes, 0.2f);
					U.setApha(rui.nameLabel, 0.2f);
					//命令commander 执行死亡命令
					Commander.getInstance().commandRoleDead(r);
				}
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		changeRoleHp((Role)arg);				
	}
}

class roleUIInfo {
	Image hpImg;
	Image photoImg,role_classes;
	Label nameLabel;

	public roleUIInfo(Role r, Stage stage, int x, int y) {
		TextureRegion hpBack = WidgetFactory.getInstance().getTextureFill(4,
				48, Color.BLACK);
		TextureRegion hp = WidgetFactory.getInstance().getTextureFill(4, 48,
				new Color(255, 0, 0, 1));
		WidgetFactory.getInstance().makeImg(hpBack, stage, 1, x - 10, y);
		hpImg = WidgetFactory.getInstance().makeImg(hp, stage, 1, x - 10, y);
		nameLabel = WidgetFactory.getInstance().makeLabel(r.name, stage, 0.8f,
				x, y + 50);
	}
}
