package com.bsu.effect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bsu.obj.Role;
import com.bsu.obj.Role.Type;
import com.bsu.screen.GameScreen;
import com.bsu.tools.GTC;

public class AttackEffect extends Actor {

	private TextureRegion currentFrame;
	private float cx;
	private float cy;
	private boolean flip_flag;

	private static AttackEffect instance = null;

	public static AttackEffect getInstance() {
		if (instance == null)
			instance = new AttackEffect();
		return instance;
	}

	private AttackEffect() {

	}

	/**
	 * 开始攻击动画
	 * @param tr
	 * @param r
	 */
	public void startEffect(TextureRegion tr, Role r, Vector2 v) {
		currentFrame = null;
		cx = 0;
		cy = 0;
		flip_flag = false;
		currentFrame = new TextureRegion(tr);
		if (r.type == Type.HERO) {
			flip_flag = false;
			if (v != null) {
				cx = r.getX() + v.x;
				cy = r.getY() + v.y;
			} else {
				cx = r.getX();
				cy = r.getY();
			}
		} else {
			flip_flag = true;
			currentFrame.flip(true, false);
			if (v != null) {
				cx = r.getX() + 32 - v.x
						- r.cskill.ani_self.getKeyFrame(0).getRegionWidth();
				cy = r.getY() + v.y;
			} else {
				cx = r.getX()+32-r.cskill.ani_self.getKeyFrame(0).getRegionWidth();
				cy = r.getY();
			}
		}
	}

	public void setFrame(TextureRegion tr) {
		currentFrame = new TextureRegion(tr);
		if (flip_flag) {
			currentFrame.flip(true, false);
		}
	}

	/**
	 * 结束攻击动画
	 */
	public void endEffect() {
		currentFrame = null;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		if (currentFrame != null) {
			batch.draw(currentFrame, cx, cy);
		}
	}
}
