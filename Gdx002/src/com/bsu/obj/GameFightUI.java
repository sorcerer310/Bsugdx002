package com.bsu.obj;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bsu.make.WidgetFactory;

/**
 * GAME游戏战斗场景UI
 * 
 * @author zhangyongchen
 * 
 */
public class GameFightUI {

	Stage stage;
	Image role_photo;
	Texture photo_texture;
	TextButton bt_endround;
	Commander c;

	public GameFightUI(Stage s) {
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
		bt_endround = WidgetFactory.getInstance().makeOneTextButton("end",
				stage, 200, 70);
		bt_endround.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				c.roundEnd();
			}
		});
		for (int i = 0; i < Player.getInstance().playerFightRole.size; i++) {
			int x = 20 + i * 80;
			int y = 10;
			final Role r = Player.getInstance().playerFightRole.get(i);
			Vector2 v = new Vector2(x, y);
			final RolePhoto photo = new RolePhoto(r, stage, v, false);
			r.photo = photo;
			TextureRegion hp = WidgetFactory.getInstance().getTexture(4, 48,
					Color.BLACK, Color.RED, 1);
			WidgetFactory.getInstance().makeImg(hp, stage, 1, x - 10, y);
			WidgetFactory.getInstance().makeLabel(r.name, stage, 0.8f, x,
					y + 50);
			for (int j = 0; j < r.skill_array.size; j++) {
				final int tempIndex=j;
				Image skillImg = WidgetFactory.getInstance().showSkillImg(
						r.skill_array.get(j), stage,
						new Vector2(x + 50, y + 24-j*26));
				skillImg.addListener(new InputListener() {
					@Override
					public boolean touchDown(InputEvent event, float x, float y,
							int pointer, int button) {
						
						return true;
					}
					@Override
					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						r.cskill=r.skill_array.get(tempIndex);
						super.touchUp(event, x, y, pointer, button);
					}
				});
			}
		}
	}
}
