package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.bsu.head.CubocScreen;
import com.bsu.make.WidgetFactory;
import com.bsu.tools.GC;
import com.bsu.tools.GTC;

public class ShopScreen extends CubocScreen implements Observer {
	private Texture timg;
	private Image background;
	private Stage stage;
	private Image ib_back;
	public ShopScreen(Game game) {
		super(game);
		stage = new Stage(GC.rect_width,GC.rect_height,false);
	}
	public void initScreen(){
		stage.clear();
		background = new Image(GTC.getInstance().shopPanel);
		stage.addActor(background);
		ib_back = WidgetFactory.getInstance().makeImageButton(GC.button_back,360,262,1);
		stage.addActor(ib_back);
		ib_back.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_back.setScale(0.95f);
				return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				setChanged();
				notifyObservers(GC.button_back);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}

	@Override
	public void show(){
		Gdx.input.setInputProcessor(null);
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	
	@Override
	public void hide(){
		Gdx.input.setInputProcessor(null);
	}
	
	@Override
	public void update(Observable o, Object arg) {
	}

}
