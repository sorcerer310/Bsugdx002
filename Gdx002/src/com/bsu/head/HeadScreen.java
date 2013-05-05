package com.bsu.head;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class HeadScreen extends CubocScreen implements Observer{
	protected TextureRegion intro;
	protected SpriteBatch batch;
	protected float time = 0;
	protected Texture logo = null;
	protected Rectangle rect = null;
	protected Sound sound = null;
	
	public HeadScreen(Game game,String bp,String sp,Rectangle r) {
		super(game);		
		logo = new Texture(Gdx.files.internal(bp));
		rect = r;
		sound = Gdx.audio.newSound(Gdx.files.internal(sp));
		
	}
	
	@Override
	public void show() {
			//此处的rect尺寸一定要转成int型，否则图像不能正确显示
			intro = new TextureRegion(logo,(int)rect.x,(int)rect.y,(int)rect.width,(int)rect.height);
			batch = new SpriteBatch();
			batch.getProjectionMatrix().setToOrtho2D(rect.x, rect.y, rect.width, rect.height);
			sound.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(intro,0,0);
		batch.end();
		
		Gdx.app.debug("HeadScreen", "render");
		
		time += delta;
		if(time>1){
			if(Gdx.input.isKeyPressed(Keys.ANY_KEY) || Gdx.input.justTouched()){
				//发送消息通知外层执行动作
				setChanged();
				notifyObservers(this);
			}
		}
	}

	@Override
	public void hide() {
		batch.dispose();
		intro.getTexture().dispose();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
