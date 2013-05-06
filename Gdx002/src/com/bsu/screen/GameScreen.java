package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bsu.head.CubocScreen;
import com.bsu.obj.GameMap;
import com.bsu.obj.Mario;
import com.bsu.obj.Mario.STATE;

public class GameScreen extends CubocScreen implements Observer {
	Stage stage;
	Mario mario;
	private ImageButton buttonL;
	private ImageButton buttonR;
	LabelStyle style;
	BitmapFont font;
	GameMap map;

	public GameScreen(Game mxg) {
		// TODO Auto-generated constructor stub
		super(mxg);

		stage = new Stage(320, 480, false);
		mario = new Mario(100, 100);
		map = new GameMap();
		setMario(GameMap.map);
		stage.addActor(mario);

		buttonL = new ImageButton(new TextureRegionDrawable(mario.spilt[1][0]),
				new TextureRegionDrawable(mario.spilt[1][1]));
		buttonR = new ImageButton(new TextureRegionDrawable(mario.miror[1][0]),
				new TextureRegionDrawable(mario.miror[1][1]));

		buttonL.setPosition(20, 20);
		buttonR.setPosition(100, 20);
		stage.addActor(buttonL);
		stage.addActor(buttonR);

		font = new BitmapFont(Gdx.files.internal("data/menu/normal.fnt"),
				Gdx.files.internal("data/menu/normal.png"), false);
		style = new LabelStyle(font, font.getColor());
		Label label1 = new Label("press escape to menu", style);
		label1.setPosition(10, 300);
		label1.setFontScale(0.6f);
		label1.setColor(Color.RED);
		stage.addActor(label1);

		buttonL.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				mario.state = STATE.idle_left;
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				mario.state = STATE.left;
				return true;
			}

		});
		buttonR.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				mario.state = STATE.idle_right;
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				mario.state = STATE.right;
				return true;
			}
		});
	}

	public void setMario(TiledMap map) {
		for (TiledObjectGroup group : map.objectGroups) {
			for (TiledObject object : group.objects) {
				if ("mario".equals(object.name)) {
					mario.x = object.x;
					mario.y = object.y;
				}
			}
		}
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		map.map_render.render(map.cam);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			setChanged();
			notifyObservers(this);
		}
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
