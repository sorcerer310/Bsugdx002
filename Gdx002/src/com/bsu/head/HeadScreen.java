package com.bsu.head;

import java.util.Observable;
import java.util.Observer;

import com.apple.dnssd.TXTRecord;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.bsu.tools.GTC;

public class HeadScreen extends CubocScreen implements Observer {
	protected TextureRegion intro;
	protected SpriteBatch batch;
	protected float time = 0;
	protected Texture logo = null;
	protected Rectangle rect = null;
	protected Sound sound = null;
	protected boolean assetLoadFlag;// 资源加载进行
	protected BitmapFont font;
	protected AssetManager am;

	public HeadScreen(Game game, String bp, String sp, Rectangle r) {
		super(game);
		logo = new Texture(Gdx.files.internal(bp));
		rect = r;
		sound = Gdx.audio.newSound(Gdx.files.internal(sp));
	}

	public void addAssets() {
		if (font == null) {
			font = new BitmapFont(Gdx.files.internal("data/skin/default.fnt"),
					false);
		}
		GTC.getInstance().loadAssets();
		am=GTC.getInstance().assetManager;
		assetLoadFlag = true;
	}
	@Override
	public void show() {
		intro = new TextureRegion(logo, (int) rect.x, (int) rect.y,
				(int) rect.width, (int) rect.height);
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(rect.x, rect.y, rect.width,
				rect.height);
		sound.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(intro, 0, 0);
		if (assetLoadFlag) {
			if (am.update()) {
				assetLoadFlag = false;
				GTC.getInstance().setAssets();
			}
			font.draw(batch,
					"加载进度" + am.getProgress() * 100 + "%100", 200,
					200);
		}
		batch.end();
		Gdx.app.debug("HeadScreen", "render");
		time += delta;
		if (time > 1) {
			if (assetLoadFlag) {
				return;
			}
			if (Gdx.input.isKeyPressed(Keys.ANY_KEY) || Gdx.input.justTouched()) {
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
	}
}
