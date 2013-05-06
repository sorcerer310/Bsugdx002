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
import com.bsu.obj.Hero;
import com.bsu.obj.Mario;
import com.bsu.obj.Mario.STATE;
import com.bsu.tools.Configure;

public class GameScreen extends CubocScreen implements Observer {
	Stage stage;
	Mario mario;
	private ImageButton buttonL;
	private ImageButton buttonR;
	
	GameMap map;
	Hero hero;
	Hero enemy;

	public GameScreen(Game mxg) {
		// TODO Auto-generated constructor stub
		super(mxg);
		stage = new Stage(Configure.rect_width, Configure.rect_height, false);
		actor_init();
		stage.addActor(mario);
		stage.addActor(hero);
		stage.addActor(enemy);
		stage.addActor(buttonL);
		stage.addActor(buttonR);

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
	private void actor_init(){
		mario = new Mario();
		map = new GameMap(0);
		setMario(GameMap.map);
		buttonL = new ImageButton(new TextureRegionDrawable(mario.spilt[1][0]),
				new TextureRegionDrawable(mario.spilt[1][1]));
		buttonR = new ImageButton(new TextureRegionDrawable(mario.miror[1][0]),
				new TextureRegionDrawable(mario.miror[1][1]));
		buttonL.setPosition(20, 20);
		buttonR.setPosition(100, 20);
		
		hero=new Hero(0,2);
		enemy=new Hero(1,3);
		hero.set_position(100, 150);
		enemy.set_position(300, 150);
	}
	
	//设置角色出生地
	private void setMario(TiledMap map) {
		for (TiledObjectGroup group : map.objectGroups) {
			for (TiledObject object : group.objects) {
				if (Configure.object_layer_mario.equals(object.name)) {
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
