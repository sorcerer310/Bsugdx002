package com.bsu.head;

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

public class SettingScreen extends CubocScreen implements Observer {
	Stage stage;
	Image back_image;
	LabelStyle style;
	BitmapFont font;
	private Image backgroundImage;

	public SettingScreen(Game mxg) {
		// TODO Auto-generated constructor stub
		super(mxg);
		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("data/menu/pack")); // 根据pack文件获取所有图片
		backgroundImage = new Image(atlas.findRegion("bground2"));
		back_image = new Image(atlas.findRegion("backarrow"));
		back_image.setPosition(100, 100);
		stage = new Stage(320, 480, false);

		font = new BitmapFont(Gdx.files.internal("data/menu/normal.fnt"),
				Gdx.files.internal("data/menu/normal.png"), false);
		style = new LabelStyle(font, font.getColor());

		Label label1 = new Label("Hello everyone\n I am nunuge \n  Potato",
				style);
		label1.setAlignment(1);
		label1.setPosition(50, 150);

		label1.setFontScale(1);

		label1.setColor(Color.GREEN);

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
