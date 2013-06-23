package com.bsu.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bsu.effect.AttackEffect;
import com.bsu.tools.GAC;
/**
 * 界面顶层动画，用于显示一些需要显示在最顶层的动画。
 * @author fengchong
 *
 */
public class UITopAnimation extends Actor{
	private Animation ani_treasure;					//宝物效果动画
	private TextureRegion current_treasure_frame;	//宝物动画当前帧
	private float time_treasure=0;					//宝藏动画时间
	private boolean flag_play_treasure = false;		//设置动画是否播放标志
	
	public UITopAnimation(){
		ani_treasure = GAC.getInstance().getTreasure();
	}
	private Vector2 pos_treasure = new Vector2();
	/**
	 * 在制定位置播放宝藏动画
	 * @param x	指定位置的x坐标
	 * @param y	指定位置的y坐标
	 */
	public void playTreasure(float x,float y){
		pos_treasure.x = x;
		pos_treasure.y = y;
		time_treasure = 0;				//一定要重置时间为0，否则动画播放不正常
		flag_play_treasure = true;		//设置播放标志位true
	}
	
	@Override
	public void draw(SpriteBatch batch,float parentAlpha){
		time_treasure += Gdx.graphics.getDeltaTime();
		if(flag_play_treasure){
			current_treasure_frame = ani_treasure.getKeyFrame(time_treasure, false);
			batch.draw(current_treasure_frame, pos_treasure.x, pos_treasure.y);
			if(ani_treasure.isAnimationFinished(time_treasure))
				flag_play_treasure = false;
				TipsWindows.getInstance().showBoxItem(getStage());
		}
	}
}
