package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.bsu.head.CubocScreen;
import com.bsu.obj.Commander;
import com.bsu.obj.GameFightUI;
import com.bsu.obj.MapBox;
import com.bsu.obj.Role;
import com.bsu.obj.Role.Type;
import com.bsu.tools.Configure;
import com.bsu.tools.GameMap;

public class GameScreen extends CubocScreen implements Observer,
		GestureListener {
	private Stage stage; // 场景对象
	private Stage UIStage; // UI场景对象
	private Commander commander; // 指挥官对象，指挥所有对象交互
	private MapBox mb; // 地图块对象
	private GameFightUI fightUI;
	private OrthographicCamera c;
	private boolean action_start; // 是否回合开始
	private boolean controlled;
	private int clingX;// 地图移动位移

	public GameScreen(Game mxg) {
		super(mxg);
		stage = new Stage(Configure.rect_width, Configure.rect_height, false);
		UIStage = new Stage(Configure.rect_width, Configure.rect_height, false);
		commander = Commander.getInstance(stage,this);
	}

	/**
	 * 游戏关卡初始化
	 * 
	 * @param mindex
	 *            关卡索引数，系统会根据这个参数判断载入哪个地图
	 * @param rols
	 *            关卡初始化英雄与敌人的数组，出生地点在地图中已经设置好
	 */
	public void game_init(int mindex, Array<Role> rols) {
		stage.clear();
		GameMap.make_map(mindex);
		setAction_start(true);
		setControlled(true);
		if (mb == null) {
			mb = new MapBox();
		}
		stage.addActor(mb); // 增加地图方格显示
		for (int i = 0; i < rols.size; i++) {
			stage.addActor(rols.get(i));
		}
		this.addActorListener();
		setBornPosition(GameMap.map, Type.HERO, Configure.object_layer_hero);
		setBornPosition(GameMap.map, Type.ENEMY, Configure.object_layer_enemy);
		if (fightUI == null) {
			fightUI = new GameFightUI(UIStage);
		}else{
			fightUI.show_hero_state();
		}
		c = (OrthographicCamera) stage.getCamera();
	}

	/**
	 * 设置出生点
	 * 
	 * @param map
	 *            地图对象
	 * @param hero
	 *            要设置的角色对象
	 * @param s
	 *            地图对象层出生点名称
	 */

	private void setBornPosition(TiledMap map, Type p, String s) {
		Array<Vector2> v = new Array<Vector2>();
		for (TiledObjectGroup group : map.objectGroups) {
			for (TiledObject object : group.objects) {
				if (s.equals(object.type)) {
					Vector2 tv = new Vector2(object.x,
							GameMap.map_render.getMapHeightUnits() - object.y
									- Configure.map_box_value);
					v.add(tv);
				}
			}
		}
		int index = 0;
		for (Actor act : stage.getActors()) {
			if (act instanceof Role) {
				Role r = (Role) act;
				if (r.getType().equals(p)) {
					r.setPosition(v.get(index).x, v.get(index).y);
					index++;
				}
			}
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		if (clingX != 0) {
			int mx = clingX > 0 ? -1 : 1;
			int maxW = GameMap.map_render.getMapWidthUnits()
					- Configure.rect_width;
			int w = Configure.rect_width / 2;
			if (c.position.x + mx >= w && c.position.x + mx <= maxW + w) {
				c.position.x += mx;
			}
		}
		GameMap.map_render.render(c);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		UIStage.act(Gdx.graphics.getDeltaTime());
		UIStage.draw();
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			setChanged();
			notifyObservers(Configure.screen_mpanel);
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(null);
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(UIStage);// 必须先加这个。。。。
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(new GestureDetector(this));
		Gdx.input.setInputProcessor(inputMultiplexer);
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
		stage.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (isAction_start() && (isControlled())) {
					int input_x = (int) (x / Configure.map_box_value);
					int input_y = (int) (y / Configure.map_box_value);
					for (Actor act : stage.getActors()) {
						if (act instanceof Role) {
							Role r = (Role) act;
							if (r.getType() == Role.Type.HERO) {
								hero_controll_rule(input_x, input_y, r);
							}
						}
					}
				}
			}
		});
	}

	/**
	 * 角色控制规则，判断是不是有角色在此位置，然后处理选择，设置角色pass，attack数组
	 * 
	 * @param mx
	 * @param my
	 * @param r
	 */
	private void hero_controll_rule(int mx, int my, Role r) {
		int hero_x = r.getBoxX();
		int hero_y = r.getBoxY();
		if (mx == hero_x) {
			if (my == hero_y) {
				if (!r.isSelected()) {
					commander.heroSelected(r);
					if (!r.isControlled()) {
						commander.heroControllor(r);
					}
				}
				set_map_value(r);
				return;
			}
		}
		if (r.isSelected()) {
			if (commander.isOtherHero(mx, my)) {
				return;
			}
			for (int i = 0; i < r.getPass_array().size; i++) {
				int mbc = (int) r.getPass_array().get(i).x;
				int mbr = (int) r.getPass_array().get(i).y;
				if ((mx == mbc) && (my == mbr)) {
					commander.moveAction(r, mx, my);
					break;
				}
			}
		}
	}

	/*
	 * 根据Role对象，设置相应的MapBox的pass数组
	 */
	private void set_map_value(Role r) {
		MapBox.pass_array.clear();
		for (Vector2 v : r.getPass_array()) {
			Vector2 tempV = new Vector2();
			tempV.x = v.x;
			tempV.y = v.y;
			MapBox.pass_array.add(tempV);
		}
	}

	public boolean isControlled() {
		return controlled;
	}

	public void setControlled(boolean c) {
		controlled = c;
	}

	public boolean isAction_start() {
		return action_start;
	}

	public void setAction_start(boolean as) {
		action_start = as;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		clingX = velocityX > 0 ? Configure.rect_width / 2
				: -Configure.rect_width / 2;
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

	public GameFightUI getFightUI() {
		return fightUI;
	}

	public Stage getStage() {
		return stage;
	}
}
