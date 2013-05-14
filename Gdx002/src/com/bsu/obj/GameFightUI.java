package com.bsu.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * GAME游戏战斗场景UI
 * @author zhangyongchen
 *
 */
public class GameFightUI {
	Stage stage;
	Image role_photo;
	Texture photo_texture;
	public GameFightUI(Stage s) {
		// TODO Auto-generated constructor stub
		stage=s;
		gameUI();
	}
	private void gameUI(){
		photo_texture=new Texture(Gdx.files.internal("data/game/ui/Actor1.png"));
		TextureRegion tr=new TextureRegion(photo_texture,0,0,96,96);
		role_photo=new Image(tr);
		role_photo.setScale(0.5f);
		stage.addActor(role_photo);
	}
}
