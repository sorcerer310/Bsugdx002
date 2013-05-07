package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bsu.head.CubocScreen;
import com.bsu.obj.ButtonFactory;
import com.bsu.obj.Commander;
import com.bsu.obj.GameMap;
<<<<<<< HEAD
import com.bsu.obj.Role;
=======
import com.bsu.obj.HeroEffectClass;
import com.bsu.obj.MyHero;
>>>>>>> anmation and effects
import com.bsu.tools.Configure;

public class GameScreen extends CubocScreen implements Observer {
	Stage stage;
	GameMap map;
	Role hero;
	Role enemy;
	Commander commander;
	TextButton bt_endround;
	public GameScreen(Game mxg) {
		// TODO Auto-generated constructor stub
		super(mxg);
		stage = new Stage(Configure.rect_width, Configure.rect_height, false);
	
	}
	public void init_game(){
		actor_init();
		stage.addActor(hero);
		stage.addActor(enemy);
<<<<<<< HEAD
		stage.addActor(bt_endround);
		commander = new Commander(stage);
=======
>>>>>>> anmation and effects
	}
	
	private void actor_init(){
		map = new GameMap(0);
<<<<<<< HEAD
		hero=new Role(Role.Type.HERO,2);
		enemy=new Role(Role.Type.ENEMY,3);
=======
		new HeroEffectClass();
		hero=new MyHero(0,2);
		enemy=new MyHero(1,3);
>>>>>>> anmation and effects
		setBornPosition(GameMap.map,hero,"h2");
		setBornPosition(GameMap.map,enemy,"n2");
		bt_endround = ButtonFactory.getInstance().getOneTextButton("end round", 10, 10);
		bt_endround.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				commander.roundEnd();
			}
		});
	}
	
	//设置角色出生地
	private void setBornPosition(TiledMap map,Role hero,String s) {
		for (TiledObjectGroup group : map.objectGroups) {
			for (TiledObject object : group.objects) {
				if (s.equals(object.name)) { 
					hero.setPosition(object.x, this.map.map_render.getMapHeightUnits()-object.y-32);
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
