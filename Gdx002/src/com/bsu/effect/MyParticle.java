package com.bsu.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bsu.obj.TipsWindows;
import com.bsu.tools.U;
/**
 * 粒子效果类
 * @author zhangyongchen
 *
 */
public class MyParticle extends Actor {
	public ParticleEffect particle;
	private float dua_time;
	private float start_time = 0;
	public boolean start_flag;
	private Stage stage;
	private String info;
	private Color color;
/**
 * 粒子类构造函数
 * @param pe 粒子
 * @param t 粒子存在时间
 * @param v 粒子位置
 */
	public MyParticle(ParticleEffect pe, float t,Vector2 v,Stage s,String str,Color c) {
		particle = pe;
		dua_time = t;
		setPosition(v.x, v.y);
		start_flag = true;
		stage=s;
		info=str;
		color=c;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		if (start_flag) {
			particle.draw(batch, parentAlpha);
			particle.setPosition(getX(), getY());
		}
	}
/**
 * 当超过粒子设定时间，消除自身，从舞台移除
 */
	@Override
	public void act(float delta) {
		if (start_flag) {
			start_time += delta;
			if (start_time >= dua_time) {
				start_flag=false;
				TipsWindows.getInstance().showTips(info,
						stage, color);
				remove();
			}
		}
	}
}
