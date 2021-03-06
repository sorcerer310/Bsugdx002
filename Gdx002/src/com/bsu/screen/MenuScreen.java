package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bsu.head.CubocScreen;
import com.bsu.make.WidgetFactory;
import com.bsu.tools.GC;
import com.bsu.tools.GameMap;

public class MenuScreen extends CubocScreen implements Observer {
	private Image backgroundImage;// backgroud
	private Image play_image;
	private Image set_image;
	private Image fight_button; // 进入战斗场景，测试用
	Stage stage;
	TextureAtlas atlas;
	GameMap map;

	public MenuScreen(Game mxg) {
		super(mxg);
		stage = new Stage(GC.rect_width, GC.rect_height, false);
		atlas = new TextureAtlas(Gdx.files.internal("data/menu/pack")); // ���pack�ļ���ȡ����ͼƬ
//		backgroundImage = new Image(atlas.findRegion("mainMenu")); // ��ȡ��ΪmainMenu��ͼƬ��������һ��Image����
//		backgroundImage.setScale(0.5f, 1);
//		backgroundImage = new Image(GTC.getInstance().start);
		backgroundImage = new Image(new Texture(Gdx.files.internal("data/menu/start.png")));
		play_image = new Image(atlas.findRegion("startButton"));
		play_image.setPosition(100, 200);
		set_image = new Image(atlas.findRegion("settingsButton"));
		set_image.setPosition(100, 120);
		
	}
	public void initScreen(){
//		Player player = Player.getInstance();
		stage.clear();
		stage.addActor(backgroundImage);
		stage.addActor(play_image);
		stage.addActor(set_image);
		if(fight_button==null)
		fight_button = WidgetFactory.getInstance().makeImageButton(
				GC.screen_rd, 100, 50,1);
		stage.addActor(fight_button);
		play_image.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				setChanged();
				notifyObservers(GC.screen_mpanel);
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchDown(event, x, y, pointer, button);
				return true;
			}
		});
		set_image.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub

				setChanged();
				notifyObservers(GC.screen_setting);
				super.touchUp(event, x, y, pointer, button);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub

				return true;
			}
		});
		fight_button.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				setChanged();
				notifyObservers(GC.screen_game);
				fight_button.setScale(1.0f);
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				fight_button.setScale(0.95f);
				return true;
			}
		});
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
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
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

}
