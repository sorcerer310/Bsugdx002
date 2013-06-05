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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.bsu.head.CubocScreen;
import com.bsu.make.WidgetFactory;
import com.bsu.tools.CG;
import com.bsu.tools.GTC;
import com.bsu.tools.U;

public class FightScreen extends CubocScreen implements Observer {
	private Image background;
	private Stage stage;
	private Image ib_back;
	private Image level1;
	public FightScreen(Game game) {
		super(game);
		stage = new Stage(CG.rect_width,CG.rect_height,false);
		background = new Image(GTC.getInstance().fightPanel);
		stage.addActor(background);
		ib_back = WidgetFactory.getInstance().makeImageButton(CG.button_back,stage,360,262,1);
		level1=WidgetFactory.getInstance().makeImageButton(CG.button_level,stage, 140, 230,1);
		setListener();
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
	private void setListener(){
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
				notifyObservers(CG.button_back);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		level1.addListener(new InputListener(){
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
				notifyObservers(CG.screen_game);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}
}
