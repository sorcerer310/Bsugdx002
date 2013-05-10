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
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.bsu.head.CubocScreen;
import com.bsu.make.ButtonFactory;
import com.bsu.obj.Commander;
import com.bsu.obj.GameMap;
import com.bsu.obj.MapBox;
import com.bsu.obj.Role;
import com.bsu.obj.Role.Type;

import com.bsu.obj.HeroEffectClass;
import com.bsu.tools.Configure;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class GameScreen extends CubocScreen implements Observer {
	Stage stage;
	GameMap map;
	Role hero;
	Role enemy;
	Commander commander;
	TextButton bt_endround;
	TextButton bt_up;
	TextButton bt_down;
	TextButton bt_left;
	TextButton bt_attack;
	MapBox mb;

	public GameScreen(Game mxg) {
		super(mxg);
		new HeroEffectClass();
		stage = new Stage(Configure.rect_width, Configure.rect_height, false);
	}

	public void init_game() {
		actor_init();
		stage.addActor(hero);
		stage.addActor(enemy);
		stage.addActor(bt_endround);
		stage.addActor(bt_up);
		stage.addActor(bt_down);
		stage.addActor(bt_left);
		stage.addActor(mb);
		commander = new Commander(stage);
		stage.addActor(bt_attack);
		this.addActorListener();
	}

	private void actor_init() {
		map = new GameMap(0);
		mb = new MapBox();
		new HeroEffectClass();
		hero = new Role(Type.HERO, 2);
		enemy = new Role(Type.ENEMY, 3);
		setBornPosition(GameMap.map, hero, "h2");
		setBornPosition(GameMap.map, enemy, "n2");
		bt_endround = ButtonFactory.getInstance().getOneTextButton("end", 200,
				50);
		bt_up = ButtonFactory.getInstance().getOneTextButton("up--", 150, 80);
		bt_down = ButtonFactory.getInstance().getOneTextButton("down", 150, 10);
		bt_left = ButtonFactory.getInstance().getOneTextButton("left", 100, 50);
		bt_attack = ButtonFactory.getInstance().getOneTextButton("attack", 150,
				50);
	}

	// 设置角色出生地
	private void setBornPosition(TiledMap map, Role hero, String s) {
		for (TiledObjectGroup group : map.objectGroups) {
			for (TiledObject object : group.objects) {
				if (s.equals(object.name)) {
					hero.setPosition(object.x,
							GameMap.map_render.getMapHeightUnits()-Configure.map_box_value - object.y);
				}
			}
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		GameMap.map_render.render(map.cam);
		mb.set_hero_pass_box(hero,enemy);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			setChanged();
			notifyObservers(this);
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(null);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void update(Observable o, Object arg) {

	}

	private void addActorListener() {
		bt_attack.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hero.hero_attack_other(enemy);
			}
		});

		bt_endround.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				commander.roundEnd();
			}
		});
		bt_left.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Array<Action> a = new Array<Action>();
				MoveByAction action = Actions.action(MoveByAction.class);
				action.setAmount(-Configure.map_box_value, 0);
				action.setDuration(1);
				a.add(action);
				commander.moveAction(a);
			}
		});
		bt_down.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Array<Action> a = new Array<Action>();
				MoveByAction action = Actions.action(MoveByAction.class);
				action.setAmount(0, -Configure.map_box_value);
				action.setDuration(1);
				a.add(action);
				commander.moveAction(a);
			}
		});
		bt_up.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Array<Action> a = new Array<Action>();
				MoveByAction action = Actions.action(MoveByAction.class);
				action.setAmount(0, Configure.map_box_value);
				action.setDuration(1);

				a.add(action);
				commander.moveAction(a);
			}
		});
		stage.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				int mx = (int) (x / Configure.map_box_value);
				int my = (int) (y / Configure.map_box_value);
				int hero_x = (int) ((hero.getX() + 10) / Configure.map_box_value);
				int hero_y = (int) ((hero.getY() + 10) / Configure.map_box_value);
				if (mx == hero_x) {
					if (my == hero_y) {
						return;
					}
				}
				for (int i = 0; i < MapBox.pass_array.size; i++) {
					int mbc = MapBox.pass_array.get(i).getColl();
					int mbr = MapBox.pass_array.get(i).getRaw();
					if ((mx == mbc) && (my == mbr)) {
						Array<Action> a = new Array<Action>();
						if (hero_x != mx) {
							MoveByAction action = Actions
									.action(MoveByAction.class);
							action.setAmount((mx - hero_x)
									* Configure.map_box_value, 0);
							action.setDuration(Math.abs(mx - hero_x));
							a.add(action);
						}
						if (hero_y != my) {
							MoveByAction action = Actions
									.action(MoveByAction.class);
							action.setAmount(0, (my - hero_y)
									* Configure.map_box_value);
							action.setDuration(Math.abs(my - hero_y));
							a.add(action);
						}

						commander.moveAction(a);
						break;
					}
				}
			}
		});
	}
}
