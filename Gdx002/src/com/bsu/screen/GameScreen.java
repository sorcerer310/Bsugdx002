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
import com.bsu.obj.MyHero;
import com.bsu.tools.Configure;

public class GameScreen extends CubocScreen implements Observer {
	Stage stage;
	GameMap map;
	MyHero hero;
	MyHero enemy;

	public GameScreen(Game mxg) {
		// TODO Auto-generated constructor stub
		super(mxg);
		stage = new Stage(Configure.rect_width, Configure.rect_height, false);
	
	}
	public void init_game(){
		actor_init();
		stage.addActor(hero);
		stage.addActor(enemy);
	}
	
	private void actor_init(){
		map = new GameMap(0);
		hero=new MyHero(0,2);
		enemy=new MyHero(1,3);
		setBornPosition(GameMap.map,hero,Configure.object_layer_hero);
		setBornPosition(GameMap.map,enemy,Configure.object_layer_enemy);
		hero.set_position(100, 150);
		enemy.set_position(200, 150);
	}
	
	//设置角色出生地
	private void setBornPosition(TiledMap map,MyHero hero,String s) {
		for (TiledObjectGroup group : map.objectGroups) {
			for (TiledObject object : group.objects) {
				if (s.equals(object.name)) {
					hero.set_position(object.x, object.y);
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
