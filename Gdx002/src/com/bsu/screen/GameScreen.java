package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.bsu.effect.AttackEffect;
import com.bsu.effect.ItemIcon;
import com.bsu.effect.UIRoleEffect;
import com.bsu.head.CubocScreen;
import com.bsu.make.RoleFactory;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.Commander;
import com.bsu.obj.Item;
import com.bsu.make.*;
import com.bsu.obj.GameScreenData;
import com.bsu.obj.MapBox;
import com.bsu.obj.Player;
import com.bsu.obj.Reward;
import com.bsu.obj.Role;
import com.bsu.obj.Role.Type;
import com.bsu.obj.UITopAnimation;
import com.bsu.tools.GC;
import com.bsu.tools.GTC;
import com.bsu.tools.GameMap;
import com.bsu.tools.Saver;
import com.bsu.tools.U;
import com.bsu.tools.GC.QUALITY;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

public class GameScreen extends CubocScreen implements Observer,
		GestureListener {
	private Stage stage; // 场景对象
	private Stage UIStage; // UI场景对象
	private Stage endStage;// 结束场景
	private Commander commander; // 指挥官对象，指挥所有对象交互
	private MapBox mb; // 地图块对象
	private UITopAnimation uita; // 顶层的一些动画效果
	private UIRoleEffect fightUI;
	private OrthographicCamera c;
	private boolean action_start; // 是否回合开始,未开始为人物操作阶段
	private boolean controlled;
	private boolean battleEndFlag = false; // 用来标识当前战役是否结束
	public static int lv;// 关卡索引
	public static int LvMax;// 开启的最大关卡
	private int clingX;// 地图移动位移
	private AttackEffect attack_effect;
	private Label fpsLabel;
	private Image endBackImg;
	public Array<Role> heros = new Array<Role>(); // 该图所有英雄
	public Array<Role> npcs = new Array<Role>(); // 该图所有npc
	private Array<Role> roles = new Array<Role>(); // 所有的角色
	public Array<Vector2> heroArisePos = new Array<Vector2>(); // 英雄出生地
	public Array<Vector2> npcArisePos = new Array<Vector2>(); // 敌人出生地
	private boolean initFlag;
	public enum BattleState{DOING,VICTORY,DEFEAT};
	public BattleState bstate = BattleState.DOING;

	public GameScreen(Game mxg) {
		super(mxg);
		U.get_skin();
		U.get_font();
		U.get_sytle();
		stage = new Stage(GC.rect_width, GC.rect_height, false);
		UIStage = new Stage(GC.rect_width, GC.rect_height, false);
		endStage = new Stage(GC.rect_width, GC.rect_height, false);
	}

	/**
	 * 游戏关卡初始化
	 * 
	 * @param mindex
	 *            关卡索引数，系统会根据这个参数判断载入哪个地图
	 * @param roles
	 *            关卡初始化英雄与敌人的数组，出生地点在地图中已经设置好
	 */
	public void game_init(GameScreenData gsd) {
		if (!initFlag) {
			uita = new UITopAnimation();
			mb = new MapBox();
			c = (OrthographicCamera) stage.getCamera();
			fightUI = new UIRoleEffect();
			attack_effect = AttackEffect.getInstance();
			endBackImg = new Image(WidgetFactory.getInstance().getTextureFill(
					GC.rect_width, GC.rect_height, Color.GRAY, 0.3f));
			fpsLabel = WidgetFactory.getInstance().makeLabel(
					"" + Gdx.graphics.getFramesPerSecond(), 1, 100, 70,
					Color.RED);
			addActorListener();
			initFlag=true;
		}
		this.heros = gsd.getHeroRoles();
		this.npcs = gsd.getNpcRoles();
		// 将所有role放入同一Role中便于操作
		roles.clear();
		roles.addAll(heros);
		roles.addAll(npcs);
		stage.clear();
		GameMap.make_map(gsd.getMapName());
		setAction_start(false);
		setControlled(true);
		mb.init_box_value();
		stage.addActor(mb); // 增加地图方格显示
		// 增加敌人
		for (Role r : npcs)
			stage.addActor(r);
		// 增加英雄
		for (Role r : heros)
			stage.addActor(r);
		stage.addActor(uita);
		commander = Commander.initInstance(stage, this);
		commander.resetRoles();
		fightUI.initUI(UIStage, this);
		setBornPosition(GameMap.map, Type.HERO, GC.object_layer_hero);
		setBornPosition(GameMap.map, Type.ENEMY, GC.object_layer_enemy);
		// 为role增加观察者
		for (int i = 0; i < roles.size; i++)
			roles.get(i).getRoleObserable().addObserver(fightUI);
		initRoles(roles);
		stage.addActor(fpsLabel);
		stage.addActor(attack_effect);
		

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
							GameMap.map_render.getMapHeightUnits() - object.y);
					v.add(tv);
				}
			}
		}
		if (p == Type.HERO)
			heroArisePos.addAll(v);
		else
			npcArisePos.addAll(v);

		Array<Role> rs = p == Type.HERO ? heros : npcs;
		for (int i = 0; i < (rs.size < v.size ? rs.size : v.size); i++) {
			Role r = rs.get(i);
			r.setVisible(true);
			r.setPosition(v.get(i).x, v.get(i).y);
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		if (clingX != 0) {
			int mx = clingX > 0 ? -1 : 1;
			int maxW = GameMap.map_render.getMapWidthUnits() - GC.rect_width;
			int w = GC.rect_width / 2;
			if (c.position.x + mx >= w && c.position.x + mx <= maxW + w) {
				c.position.x += mx;
			}
		}
		GameMap.map_render.render(c);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		UIStage.act(Gdx.graphics.getDeltaTime());
		UIStage.draw();
		// 如果当前战役结束，显示结束画面
		if (battleEndFlag) {
			endStage.act(Gdx.graphics.getDeltaTime());
			endStage.draw();
		}
		
		fpsLabel.setText("fps:" + Gdx.graphics.getFramesPerSecond()+"@@"+ ((Gdx.app.getJavaHeap() * 10) >> 20) / 10f + " MB"
				+ "--N:" + ((Gdx.app.getNativeHeap() * 10) >> 20) / 10f
				+ " MB");
		
		//判断战斗是否胜利，如果为DOING不做任何操作
		if(bstate==BattleState.VICTORY){
			battleEnd(true);
			Saver.getInstance().save();
			bstate=BattleState.DOING;
		}else if(bstate==BattleState.DEFEAT){
			battleEnd(false);
			Saver.getInstance().save();
			bstate=BattleState.DOING;
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		setBattleEndFlag(false);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		endStage.clear();
		UIStage.clear();
		stage.clear();
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
				if (!isAction_start() && (isControlled())) {
					int input_x = (int) (x / GC.map_box_value);
					int input_y = (int) (y / GC.map_box_value);
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

		endStage.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// 通知切换到主界面
				setChanged();
				notifyObservers(GC.screen_mpanel);
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
		if (r.isDead) {
			return;
		}
		int hero_x = r.getBoxX();
		int hero_y = r.getBoxY();
		if (mx == hero_x && my == hero_y) {
			if (!r.isSelected()) {
				heroSelected(r);
				if (!r.isControlled()) {
					heroControllor(r);
				}
				set_map_value(r);
				return;
			}
		}
		if (r.isSelected()) {
			if (isOtherHero(mx, my)) {
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

	/**
	 * 初始化role
	 */
	private void initRoles(Array<Role> roles) {
		for (Role r : roles) {
			r.gsstartinit();
			MapBox.attack_array.clear();
			MapBox.pass_array.clear();
			mb.block_array.clear();
		}
		newRound();
	}

	/**
	 * 新回合开始
	 */
	public void newRound() {
		setAction_start(false);
		for (Role r : commander.heros) {
			if (!r.isDead) {
				heroSelected(r);
				heroControllor(r);
				set_map_value(r);
				break;
			}
		}
	}

	Array<Item> itemArray = new Array<Item>();// 通关获得的道具数组

	/**
	 * 战斗结束
	 */
	public void battleEnd(boolean victflag) {
		String endname = "victory";
		if (victflag) {
			endname = "victory";
			if (lv == LvMax) 
				LvMax++;
		} else {
			endname = "defeat";
		}
		TextureRegion tr = GTC.getInstance().battle_end.findRegion(endname);
		Image img = new Image(tr);
		img.setPosition((GC.rect_width - img.getWidth()) / 2,
				(GC.rect_height - img.getHeight()) / 2 + 100);
		itemArray.clear();
		endStage.clear();
		endStage.addActor(endBackImg);
		endStage.addActor(img);
		//如果战斗胜利
		if (victflag) {
			
			Table tableRole = new Table();
			ScrollPane spRole = new ScrollPane(tableRole, U.get_skin().get(
					ScrollPaneStyle.class));
			spRole.setWidth(400);
			spRole.setHeight(70);
			spRole.setPosition(20, 150);
			spRole.setScrollingDisabled(false, true);
			spRole.setupFadeScrollBars(0, 0);
			for (Reward r : GameScreenFactory.getInstance().gsd.getReward()) {
				if (r.item.type == com.bsu.obj.Item.Type.rolecard) {
					ItemIcon icon = new ItemIcon(r.item);
					tableRole.add(icon).width(icon.img_frame.getWidth())
							.height(icon.img_frame.getHeight())
							// 设置photo宽度和高度
							.padTop(2f).align(Align.top).spaceLeft(10f)
							.spaceRight(10f); // 设置各photo之间的边距
					itemArray.add(r.item);
				}
			}
			Table table = new Table();
			ScrollPane sp = new ScrollPane(table, U.get_skin().get(
					ScrollPaneStyle.class));
			sp.setWidth(400);
			sp.setHeight(70);
			sp.setPosition(20, 90);
			sp.setScrollingDisabled(false, true);
			sp.setupFadeScrollBars(0, 0);
			for (Reward r : GameScreenFactory.getInstance().gsd.getReward()) {
				if (r.item.type == com.bsu.obj.Item.Type.skillpart) {
					table.defaults().padRight(10);
					table.defaults().padTop(10);
					table.add(new Image(GTC.getInstance().hm_headItemIcon.get(r.item.tr_item)));
					itemArray.add(r.item);
				}
			}
			//将奖励数据增加到player上
			if (itemArray.size > 0) {
				Array<Role> roles = new Array<Role>();
				for (Item item : itemArray) {
					if (item.type == Item.Type.rolecard) {
						Role r = RoleFactory.getInstance().getRole(item);
						roles.add(r);
					}
					if (item.type == Item.Type.skillpart) {
						if (item.q == QUALITY.blue) {
							Player.getInstance().crystal_blue++;
						}
						if (item.q == QUALITY.purple) {
							Player.getInstance().crystal_purple++;
						}
						if (item.q == QUALITY.orange) {
							Player.getInstance().crystal_orange++;
						}
					}
				}
				Player.getInstance().addRole(roles);
			}
			
			
			endStage.addActor(spRole);
			endStage.addActor(sp);
		}
		battleEndFlag = true;
		setBattleEndFlag(battleEndFlag);
	}

	/**
	 * 用来检查角色是否被本轮选择，若被选择，则其他不被选择，
	 * 
	 * @author 张永臣
	 */
	public void heroSelected(Role hero) {
		for (Role r : commander.heros) {
			if (r.isDead) {
				continue;
			}
			if (r.getType() == Type.HERO) {
				r.setSelected(false);
				r.roleIcon.showEffect(false);
				if (hero == r) {
					hero.setSelected(true);
					hero.roleIcon.showEffect(true);
				}
			}
		}
	}

	/**
	 * 判断此可移动方块是否有人存在
	 * 
	 * @param r
	 *            被选要移动的人
	 * @return
	 */
	public boolean isOtherHero(int inputX, int inputY) {
		for (Role r : commander.allRoles) {
			if (r.isDead) {
				return false;
			}
			Vector2 hv = new Vector2(r.getBoxX(), r.getBoxY());
			if ((inputX == hv.x) && (inputY == hv.y)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 用来检查角色是否被本轮选择操作，如果第一次选择，则计算可移动范围
	 * 
	 * @author 张永臣
	 */
	public void heroControllor(Role r) {
		if (r.isDead) {
			return;
		}
		r.setControlled(true);
		if (r.getType() == Type.HERO) {
			r.setPass_array(mb.set_hero_pass_box(r, commander.npcs));
		}
	}

	/*
	 * 根据Role对象，设置相应的MapBox的pass数组
	 */
	public void set_map_value(Role r) {
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
		clingX = velocityX > 0 ? GC.rect_width / 2 : -GC.rect_width / 2;
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

	public UIRoleEffect getFightUI() {
		return fightUI;
	}

	public Stage getStage() {
		return stage;
	}

	/**
	 * 设置游戏是否结束标识
	 * 
	 * @param battleEnd
	 */
	public void setBattleEndFlag(boolean flag) {
		this.battleEndFlag = flag;
		if (battleEndFlag) {
			Gdx.input.setInputProcessor(null);
			Gdx.input.setInputProcessor(endStage);
		} else {
			Gdx.input.setInputProcessor(null);
			InputMultiplexer inputMultiplexer = new InputMultiplexer();
			inputMultiplexer.addProcessor(UIStage);// 必须先加这个。。。。
			inputMultiplexer.addProcessor(stage);
			inputMultiplexer.addProcessor(new GestureDetector(this));
			Gdx.input.setInputProcessor(inputMultiplexer);
		}
	}
}
