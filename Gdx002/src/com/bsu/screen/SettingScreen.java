package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.bsu.head.CubocScreen;
import com.bsu.tools.Configure;

public class SettingScreen extends CubocScreen implements Observer {
	Stage stage;
	Image back_image;
	Label label1;
	TextureAtlas atlas;
	private Image backgroundImage;

	public SettingScreen(Game mxg) {
		// TODO Auto-generated constructor stub
		super(mxg);
		stage = new Stage(Configure.rect_width, Configure.rect_height, false);
		actor_init();
		stage.addActor(backgroundImage);
		stage.addActor(back_image);
		stage.addActor(label1);

		back_image.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub

				setChanged();
				notifyObservers(this);
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
		backgroundImage = new Image(atlas.findRegion("bground2"));
		back_image = new Image(atlas.findRegion("backarrow"));
		back_image.setPosition(100, 100);
		label1 = new Label("Hello everyone\n I am nunuge \n  Potato", Configure.get_sytle());
		label1.setAlignment(1);
		label1.setPosition(50, 150);
		label1.setFontScale(1);
		label1.setColor(Color.GREEN);
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
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
