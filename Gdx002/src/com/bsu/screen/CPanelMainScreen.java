package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Array;
import com.bsu.head.CubocScreen;
import com.bsu.make.ButtonFactory;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.tools.Configure;
import com.bsu.tools.GameTextureClass;

public class CPanelMainScreen extends CubocScreen implements Observer {
	private Texture timg;
	private Image background;
	private Stage stage;

	private Image ib_mb_equip;
	private Image ib_mb_fight;
	private Image ib_mb_role;
	private Image ib_mb_shop;
	private Image ib_mb_skill;
	private Image ib_mb_update;
	private Array<Image> heroImg = new Array<Image>();

	public CPanelMainScreen(Game game) {
		super(game);
		stage = new Stage(Configure.rect_width, Configure.rect_height, false);
		timg = GameTextureClass.getInstance().mPanel;
		background = new Image(timg);

		ib_mb_update = ButtonFactory.getInstance().makeImageButton(
				Configure.screen_update);
		ib_mb_update.setPosition(135, 225);
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
				notifyObservers(Configure.screen_update);
				ib_mb_update.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});

		ib_mb_equip = ButtonFactory.getInstance().makeImageButton(
				Configure.screen_equip);
		ib_mb_equip.setPosition(300, 225);
		ib_mb_equip.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_mb_equip.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				setChanged();
				notifyObservers(Configure.screen_equip);
				ib_mb_equip.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});

		ib_mb_skill = ButtonFactory.getInstance().makeImageButton(
				Configure.screen_skill);
		ib_mb_skill.setPosition(135, 135);
		ib_mb_skill.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_mb_skill.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				setChanged();
				notifyObservers(Configure.screen_skill);
				ib_mb_skill.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});

		ib_mb_role = ButtonFactory.getInstance().makeImageButton(
				Configure.screen_role);
		ib_mb_role.setPosition(300, 135);
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
				notifyObservers(Configure.screen_role);
				ib_mb_role.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});

		ib_mb_fight = ButtonFactory.getInstance().makeImageButton(
				Configure.screen_fight);
		ib_mb_fight.setPosition(135, 50);
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
				notifyObservers(Configure.screen_fight);
				ib_mb_fight.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});

		ib_mb_shop = ButtonFactory.getInstance().makeImageButton(
				Configure.screen_shop);
		ib_mb_shop.setPosition(300, 50);
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
				notifyObservers(Configure.screen_shop);
				ib_mb_shop.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});

		stage.addActor(background);
		stage.addActor(ib_mb_update);
//		stage.addActor(ib_mb_equip);
//		stage.addActor(ib_mb_skill);
		stage.addActor(ib_mb_role);
		stage.addActor(ib_mb_fight);
		stage.addActor(ib_mb_shop);
		Array<Role> playerRols = Player.getInstance().getPlayerFightRole();
		for (int i = 0; i < playerRols.size; i++) {
			Image roleImg = new Image(playerRols.get(i).roleTexture);
			roleImg.setScale(0.5f);
			stage.addActor(roleImg);
			roleImg.setPosition(48, 246 - 55 * i);
			heroImg.add(roleImg);
			roleImg.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					getSelectRole((Image) event.getListenerActor());
					return super.touchDown(event, x, y, pointer, button);
				}
			});
		}
	}
	/**
	 * 通过点击Image取得相应的role
	 * @param img
	 */
	private void getSelectRole(Image img) {
		for(int i=0;i<heroImg.size;i++){
			if(heroImg.get(i).equals(img)){
				
			}
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
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
	public void update(Observable o, Object arg) {
	}

}
