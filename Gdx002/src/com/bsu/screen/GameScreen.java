package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bsu.head.CubocScreen;
import com.bsu.make.ButtonFactory;
import com.bsu.obj.Commander;
import com.bsu.obj.GameMap;
import com.bsu.obj.Role;
import com.bsu.obj.Role.Type;

import com.bsu.obj.HeroEffectClass;
import com.bsu.tools.Configure;

public class GameScreen extends CubocScreen implements Observer {
	private Stage stage;
	private GameMap map;
	private Role hero;
	private Role enemy;
	private Commander commander;
	private TextButton bt_endround;
	private TextureAtlas atlas;
	private Image fight_image;

	public GameScreen(Game mxg) {
		// TODO Auto-generated constructor stub
		super(mxg);
		new HeroEffectClass();
		stage = new Stage(Configure.rect_width, Configure.rect_height, false);
		atlas = new TextureAtlas(Gdx.files.internal("data/menu/pack")); // 根据pack文件获取所有图
		fight_image = new Image(atlas.findRegion("startButton"));
	}
	public void init_game(){
		actor_init();
		stage.addActor(hero);
		stage.addActor(enemy);
		stage.addActor(bt_endround);
		commander = new Commander(stage);
		stage.addActor(fight_image);
		this.addActorListener();
	}
	
	private void actor_init(){
		map = new GameMap(0);
		hero=new Role(Role.Type.HERO,2);
		enemy=new Role(Role.Type.ENEMY,3);
		new HeroEffectClass();
		hero=new Role(Type.HERO,2);
		enemy=new Role(Type.ENEMY,3);
		setBornPosition(GameMap.map,hero,"h2");
		setBornPosition(GameMap.map,enemy,"n2");
		bt_endround = ButtonFactory.getInstance().getOneTextButton("end round", 150, 10);
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
	
	private void addActorListener(){
		fight_image.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				hero.hero_attack_other(enemy);
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
}
