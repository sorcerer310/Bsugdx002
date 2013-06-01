package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.bsu.head.CubocScreen;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.obj.RolePhoto;
import com.bsu.tools.Configure;
import com.bsu.tools.Configure.QUALITY;
import com.bsu.tools.GameTextureClass;
import com.bsu.tools.MessageObject;

public class CPanelMainScreen extends CubocScreen implements Observer,
		GestureListener {
	private Texture timg;
	private Image background;
	private Stage stage;
	private Stage roleStage;// 双舞台，画出战人物头像
	private Image ib_mb_fight;
	private Image ib_mb_role;
	private Image ib_mb_shop;
	private Image ib_mb_update;

	public CPanelMainScreen(Game game) {
		super(game);
		stage = new Stage(Configure.rect_width, Configure.rect_height, false);
		roleStage = new Stage(Configure.rect_width, Configure.rect_height,
				false);
		timg = GameTextureClass.getInstance().mPanel;
		background = new Image(timg);
		stage.addActor(background);
		ib_mb_update = WidgetFactory.getInstance().makeImageButton(
				Configure.screen_update, stage, 135, 225);
		ib_mb_update.setPosition(135, 225);
		ib_mb_role = WidgetFactory.getInstance().makeImageButton(
				Configure.screen_role, stage, 300, 225);
		// ib_mb_role = WidgetFactory.getInstance().makeImageButton(
		// Configure.screen_role, stage, 300, 135);
		// ib_mb_role.setPosition(300, 135);
		ib_mb_fight = WidgetFactory.getInstance().makeImageButton(
				Configure.screen_fight, stage, 135, 50);
		ib_mb_fight.setPosition(135, 50);
		ib_mb_shop = WidgetFactory.getInstance().makeImageButton(
				Configure.screen_shop, stage, 300, 50);
		ib_mb_shop.setPosition(300, 50);
		// 增加上阵英雄头像
		setFightRoles();
		setListener();
	}

	private void setFightRoles() {
		roleStage.clear();
		int frlength = 0;
		Array<Role> playerRols = Player.getInstance().playerFightRole;
		frlength = playerRols.size;
		for (int i = 0; i < playerRols.size; i++) {
			final Role r = playerRols.get(i);
			Vector2 v = new Vector2(48, 246 - 55 * i);
			RolePhoto photo = new RolePhoto(r, roleStage, v, true);
			photo.role.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					setChanged();
					notifyObservers(new MessageObject(r,
							Configure.screen_selectRole));
					return super.touchDown(event, x, y, pointer, button);
				}
			});
		}
		if (frlength < 5) {
			for (int i = frlength; i < 5; i++) {
				Vector2 v = new Vector2(46, 244 - 55 * i);
				RolePhoto photo = new RolePhoto(roleStage, QUALITY.orange, v);
				photo.role_k.addListener(new InputListener() {
					@Override
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						setChanged();
						notifyObservers(new MessageObject(null,
								Configure.screen_selectRole));
						return super.touchDown(event, x, y, pointer, button);
					}
				});
			}
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
		setFightRoles();
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
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
				setChanged();
				notifyObservers(new MessageObject(null, Configure.screen_shop));
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
				setChanged();
				notifyObservers(new MessageObject(null, Configure.screen_fight));
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
				notifyObservers(new MessageObject(null, Configure.screen_role));
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
				setChanged();
				notifyObservers(new MessageObject(null, Configure.screen_update));
				ib_mb_update.setScale(1f);
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
