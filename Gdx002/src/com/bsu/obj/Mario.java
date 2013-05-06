package com.bsu.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bsu.head.Collision;

public class Mario extends Actor {
	public float x;
	public float y;
	private float state_time;

	Texture texture;
	TextureRegion current_frame;

	Animation ani_idle_left;
	Animation ani_idle_right;
	Animation ani_left;
	Animation ani_right;

	public TextureRegion[][] spilt;
	public TextureRegion[][] miror;

	public enum STATE {
		idle_left, idle_right, left, right
	};

	public STATE state;

	public Mario(float x, float y) {
		this.x = x;
		this.y = y;
		this.state_time = 0;
		state = STATE.idle_right;
		set_actor();
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub

		state_time += Gdx.graphics.getDeltaTime();

		this.update_state();

		this.check();

		batch.draw(current_frame, x, y);

	}

	// 数据需要定义变量，数值需要讨论,控制边界,往左因为镜像翻转导致中心位置不准。
	public void update_state() {
		 if(Collision.downEnable(this.x, this.y)) {
	           this.y -=2;
	       }
		if ((state == STATE.left)&&Collision.leftEnable(this.x, this.y, 64, 64)) {
			this.x -= 1.5f;
			if (this.x < 0)
				this.x = 0;
		} else if ((state == STATE.right)&&Collision.rightEnable(this.x, this.y, 64, 64)) {
			this.x += 1.5f;
			if (this.x > 320 - 60)
				this.x = 320 - 60;// not is Gdx.graphics.getWidth()????
		}
		this.x = x;
	}

	// 根据状态取得动画
	public void check() {
		if (state == STATE.left) {
			current_frame = ani_left.getKeyFrame(state_time, true);
		} else if (state == STATE.right) {
			current_frame = ani_right.getKeyFrame(state_time, true);
		} else if (state == STATE.idle_left) {
			current_frame = ani_idle_left.getKeyFrame(state_time, true);
		} else {
			current_frame = ani_idle_right.getKeyFrame(state_time, true);
		}
	}

	// 设置演员 马里奥
	private void set_actor() {
		texture = new Texture(Gdx.files.internal("data/mla.png"));
		spilt = TextureRegion.split(texture, 64, 64);
		miror = TextureRegion.split(texture, 64, 64);

		for (TextureRegion[] region1 : miror) {
			for (TextureRegion region2 : region1) {
				region2.flip(true, false);
			}
		}
		// 右
		TextureRegion[] regionR = new TextureRegion[3];
		regionR[0] = spilt[0][1];
		regionR[1] = spilt[0][2];
		regionR[2] = spilt[0][0];
		ani_right = new Animation(0.1f, regionR);
		// 左
		TextureRegion[] regionL = new TextureRegion[3];
		regionL[0] = miror[0][1];
		regionL[1] = miror[0][2];
		regionL[2] = miror[0][0];
		ani_left = new Animation(0.1f, regionL);
		// 空闲right
		TextureRegion[] region_idle_right = new TextureRegion[1];
		region_idle_right[0] = spilt[0][0];
		ani_idle_right = new Animation(0.1f, region_idle_right);
		// idle left
		TextureRegion[] region_idle_left = new TextureRegion[1];
		region_idle_left[0] = miror[0][0];

		ani_idle_left = new Animation(0.1f, region_idle_left);

	}
}
