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
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.bsu.head.CubocScreen;
import com.bsu.make.RoleFactory;
import com.bsu.obj.Commander;
import com.bsu.obj.GameFightUI;
import com.bsu.obj.MapBox;
import com.bsu.obj.Role;
import com.bsu.obj.Role.Type;
import com.bsu.tools.Configure;
import com.bsu.tools.GameMap;
import com.bsu.tools.HeroEffectClass;


public class GameScreen extends CubocScreen implements Observer?GestureListener  {
	private Stage stage; // 场景对象
	private Stage UIStage; // UI场景对象
//	private GameMap gmap; // 游戏地图
	private Commander commander; // 指挥官对象，指挥所有对象交互
	private MapBox mb; // 地图块对象
	private GameFightUI gfu;
	private OrthographicCamera c;
	private boolean action_start; // 是否回合开始
	private boolean controlled;

	public GameScreen(Game mxg) {
		super(mxg);
		stage = new Stage(Configure.rect_width, Configure.rect_height, false);
		UIStage = new Stage(Configure.rect_width, Configure.rect_height, false);
				init_game();
	}

	private void init_game() {
		actor_init();
		stage.addActor(mb);
		stage.addActor(hero);
		stage.addActor(hero1);
		stage.addActor(enemy1);
		stage.addActor(enemy2);
		stage.addActor(enemy3);
		commander = Commander.getInstance(stage);
		this.addActorListener();
		setBornPosition(GameMap.map, Type.HERO, Configure.object_layer_hero);
		setBornPosition(GameMap.map, Type.ENEMY, Configure.object_layer_enemy);
		gfu = new GameFightUI(UIStage, commander);
		c = (OrthographicCamera) stage.getCamera();
	}

	private void actor_init() {
		new HeroEffectClass();
		map = new GameMap(0);
		mb = new MapBox();
		setAction_start(true);
		setControlled(true);
		hero = RoleFactory.getInstance().getHeroRole("hero1");
		hero1 = RoleFactory.getInstance().getHeroRole2("hero2");
		enemy1 = RoleFactory.getInstance().getEnemyRole("enemy1");
		enemy2 = RoleFactory.getInstance().getEnemyRole("enemy2");
		enemy3 = RoleFactory.getInstance().getEnemyRole("enemy3");
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
		// c.position.add(new Vector3(1,0,0));
		GameMap.map_render.render(c);
		if (cling_x != 0) {
			cling_x += cling_x > 0 ? -1 : 1;
			int cx = cling_x > 0 ? -1 : 1;
			int max_x = map.map_render.getMapWidthUnits()
					- Configure.rect_width;
			if (c.position.x + cx >= Configure.rect_width / 2
					&& c.position.x + cx <= max_x + Configure.rect_width / 2){
				c.position.x += cx;
			}
		}
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		UIStage.act(Gdx.graphics.getDeltaTime());
		UIStage.draw();
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
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(UIStage);// �
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

	int cling_x, cling_y;// 移动剩余数量

	/**
	 * 手势控制地图移动 ，每次移动均1/4屏幕，与手势数据无关
	 * 
	 * @param clingX
	 *            水平移动量
	 * @param clingY
	 *            竖直移动量
	 */
	private void stage_camera_move(int clingX, int clingY) {
		cling_x = clingX > 0 ? Configure.rect_width / 4
				: -Configure.rect_width / 4;
		cling_y = clingY;
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
		stage_camera_move((int) velocityX, (int) velocityY);
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
}
