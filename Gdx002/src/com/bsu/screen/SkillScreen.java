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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bsu.head.CubocScreen;
import com.bsu.tools.Configure;
import com.bsu.tools.GameTextureClass;

public class SkillScreen extends CubocScreen implements Observer {
	private Texture timg;
	private TextureRegion intro;
	private SpriteBatch batch;
	public SkillScreen(Game game) {
		super(game);
		timg = GameTextureClass.getInstance().getSkillPanel();
	}

	@Override
	public void show(){
		intro = new TextureRegion(timg,0,0,Configure.rect_width,Configure.rect_height);
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, Configure.rect_width, Configure.rect_height);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(intro, 0, 0);
		batch.end();
		
	}

	
	@Override
	public void hide(){
		batch.dispose();
		intro.getTexture().dispose();
	}
	
	@Override
	public void update(Observable o, Object arg) {
	}

}
