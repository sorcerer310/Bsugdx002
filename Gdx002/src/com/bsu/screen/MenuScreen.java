package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bsu.head.CubocScreen;
import com.bsu.obj.GameMap;
import com.bsu.obj.MyHero;
import com.bsu.tools.Configure;

public class MenuScreen extends CubocScreen implements Observer {
	private Image backgroundImage;// backgroud
	private Image play_image;
	private Image set_image;
	Stage stage; // 舞台
	TextureAtlas atlas;
	GameMap map;

	public MenuScreen(Game mxg) {
		super(mxg);
		// TODO Auto-generated constructor stub
		stage = new Stage(Configure.rect_width, Configure.rect_height, false);
		actor_init();
		stage.addActor(backgroundImage);
		stage.addActor(play_image);
		stage.addActor(set_image);

		play_image.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				setChanged();
				notifyObservers(Configure.screen_game);
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub

				return true;
			}
		});
		set_image.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub

				setChanged();
				notifyObservers(Configure.screen_setting);
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub

				return true;
			}
		});
	}

	private void actor_init() {
		atlas = new TextureAtlas(Gdx.files.internal("data/menu/pack")); // 根据pack文件获取所有图片
		backgroundImage = new Image(atlas.findRegion("mainMenu")); 		// 获取名为mainMenu的图片，并创建一个Image对象
		backgroundImage.setScale(0.5f, 1);
		play_image = new Image(atlas.findRegion("startButton"));
		play_image.setPosition(100, 200);
		set_image = new Image(atlas.findRegion("settingsButton"));
		set_image.setPosition(100, 120);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(null);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

}
