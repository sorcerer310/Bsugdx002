package com.bsu.obj;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.bsu.make.WidgetFactory;
import com.bsu.tools.Configure;
import com.bsu.tools.FightRoleUI;
import com.bsu.tools.RoleHP;

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
	Array<FightRoleUI> role_state_array = new Array<FightRoleUI>();

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
		bt_endround = WidgetFactory.getInstance().makeOneTextButton("end",stage, 200,
				80);
		bt_endround.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				c.roundEnd();
			}
		});
		int roleIndex = 0;
		for (Role e : Player.getInstance().playerFightRole) {
			role_state_array.add(new FightRoleUI(stage, e, roleIndex));
			roleIndex++;
		}
	}

	public Array<FightRoleUI> getRole_state_array() {
		return role_state_array;
	}
}
