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
import com.bsu.make.ButtonFactory;
import com.bsu.tools.Configure;
import com.bsu.tools.GameTextureClass;

public class UpdateScreen extends CubocScreen implements Observer {
	private Texture timg;
	private Image background;
	private Stage stage;
	private ImageButton ib_back;
	public UpdateScreen(Game game) {
		super(game);
		stage = new Stage(Configure.rect_width,Configure.rect_height,false);
		background = new Image(GameTextureClass.getInstance().updatePanel);
		
		ib_back = ButtonFactory.getInstance().makeImageButton(Configure.button_back);
		ib_back.setPosition(360,262);
		ib_back.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				setChanged();
				notifyObservers(Configure.button_back);
				return super.touchDown(event, x, y, pointer, button);
			}
			
		});
		
		stage.addActor(background);
		stage.addActor(ib_back);
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
