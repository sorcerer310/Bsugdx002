package com.bsu.screen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.bsu.effect.RoleHead;
import com.bsu.head.CubocScreen;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.obj.TipsWindows;
import com.bsu.tools.GC;
import com.bsu.tools.GC.QUALITY;
import com.bsu.tools.GTC;
import com.bsu.tools.MessageObject;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

public class CPanelMainScreen extends CubocScreen implements Observer,
		GestureListener {
	private TextureRegion timg;
	private Image background;
	private Stage stage;
	private Stage roleStage;// 双舞台，画出战人物头像
	private Image ib_mb_fight;
	private Image ib_mb_role;
	private Image ib_mb_shop;
	private Image ib_mb_update;
	private boolean initFLag;// 是否初始化了

	public CPanelMainScreen(Game game) {
		super(game);
		stage = new Stage(GC.rect_width, GC.rect_height, false);
		roleStage = new Stage(GC.rect_width, GC.rect_height, false);
	}

	public void initScreen() {
		stage.clear();
		if (!initFLag) {
			timg = GTC.getInstance().mPanel;
			background = new Image(timg);
			ib_mb_update = WidgetFactory.getInstance().makeImageButton(
					GC.screen_update, 46, 124, 1);
			ib_mb_role = WidgetFactory.getInstance().makeImageButton(
					GC.screen_role, 270, 116, 1);
			ib_mb_fight = WidgetFactory.getInstance().makeImageButton(
					GC.screen_rd, 52, 50, 1);
			ib_mb_shop = WidgetFactory.getInstance().makeImageButton(
					GC.screen_shop, 270, 52, 1);
			initFLag = true;
			setListener();
		}
		// 增加上阵英雄头像
		setFightRoles();
		stage.addActor(background);
		stage.addActor(ib_mb_update);
		stage.addActor(ib_mb_role);
		stage.addActor(ib_mb_fight);
		stage.addActor(ib_mb_shop);
	}

	private void setFightRoles() {
		roleStage.clear();
		int frlength = 0, max = 5;																		//上场参加战斗人物的数量,最多显示人物数量
		int x = 48, y = 230, w = 76;																		
		Array<Role> playerRole = Player.getInstance().getPlayerFightRole();			
		frlength = playerRole.size;
		for (int i = 0; i < max; i++) {
			Vector2 v = new Vector2(x+w*i, y);
			RoleHead photo = null;
			//如果当前索引小于上场参加战斗的人物数量,绘制正常头象
			if(i<frlength){
				final Role r = playerRole.get(i);
				photo = new RoleHead(r, false);
				photo.addListener(new InputListener() {
					@Override
					public boolean touchDown(InputEvent event, float x, float y,
							int pointer, int button) {
						// setChanged();
						// notifyObservers(new MessageObject(r,
						// GC.screen_selectRole));
						return super.touchDown(event, x, y, pointer, button);
					}
				});
			}
			//否则绘制空头像框
			else
				photo = new RoleHead();
			photo.setScale(1.5f);
//			r.roleIcon.showEffect(true);
			roleStage.addActor(photo);
			photo.setPosition(v.x, v.y);

		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		roleStage.act(Gdx.graphics.getDeltaTime());
		roleStage.draw();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(null);
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(roleStage);// 必须先加这个。。。。
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(new GestureDetector(this));
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		roleStage.clear();
	}

	@Override
	public void update(Observable o, Object arg) {
	}

	private void setListener() {
		ib_mb_shop.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_mb_shop.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// setChanged();
				// notifyObservers(new MessageObject(null,
				// Configure.screen_shop));
				TipsWindows.getInstance().showTips("暂未开放，敬请期待", roleStage,
						Color.GRAY);
				ib_mb_shop.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		ib_mb_fight.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_mb_fight.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if (Player.getInstance().getPlayerFightRole().size > 0) {
					setChanged();
					notifyObservers(new MessageObject(null, GC.screen_rd));
				} else {
					TipsWindows.getInstance().showTips("请选择出战卡片", roleStage,
							Color.GRAY);
				}
				ib_mb_fight.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		ib_mb_role.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_mb_role.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				setChanged();
				notifyObservers(new MessageObject(null, GC.screen_role));
				ib_mb_role.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		ib_mb_update.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_mb_update.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if(Player.getInstance().getPlayerFightRole().size>0){
				setChanged();
				notifyObservers(new MessageObject(null, GC.screen_update));
				ib_mb_update.setScale(1f);
				}else{
					TipsWindows.getInstance().showTips("选择至少一个角色出战，以便进行强化", roleStage, Color.RED);
				}
				super.touchUp(event, x, y, pointer, button);
			}
		});
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
