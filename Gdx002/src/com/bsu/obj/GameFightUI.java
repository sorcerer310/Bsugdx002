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
import com.bsu.make.ButtonFactory;
import com.bsu.tools.Configure;

/**
 * GAME游戏战斗场景UI
 * @author zhangyongchen
 *
 */
public class GameFightUI{

	Stage stage;
	Image role_photo;
	Texture photo_texture;
	TextButton bt_endround;
	Commander c;
	public GameFightUI(Stage s,Commander cm) {
		// TODO Auto-generated constructor stub
		stage = s;
		c=cm;
		gameUI();
	}
	private void gameUI(){
		photo_texture=new Texture(Gdx.files.internal("data/game/ui/Actor1.png"));
		TextureRegion tr=new TextureRegion(photo_texture,0,0,96,96);
		role_photo=new Image(tr);
		role_photo.setScale(0.5f);
	
		bt_endround = ButtonFactory.getInstance().getOneTextButton("end", 200,
				60);
		bt_endround.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				c.roundEnd();
			}
		});
		stage.addActor(role_photo);
		stage.addActor(bt_endround);
	}
}
