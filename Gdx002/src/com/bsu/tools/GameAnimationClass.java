package com.bsu.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * 加载游戏中所有动画，在贴图加载之后
 * 
 * @author zhangyongchen
 * 
 */
public class GameAnimationClass {
	private static GameAnimationClass instance = null;

	public static GameAnimationClass getInstance() {
		if (instance == null)
			instance = new GameAnimationClass();
		return instance;
	}

	public GameAnimationClass() {
		// TODO Auto-generated constructor stub
		gameAnimations();
	}

	private Texture effect_texture;
	private Texture card_texture;
	private TextureRegion[][] effect_spilt;
	private TextureRegion[][] role_spilt;

	private void gameAnimations() {
		effect_texture = GameTextureClass.getInstance().effect_texture;
		effect_spilt = TextureRegion.split(effect_texture, 32, 32);
		card_texture = GameTextureClass.getInstance().role_texture;
		role_spilt = TextureRegion.split(card_texture, 96, 96);
	}
	public Animation getRoleAnimation(TextureRegion tr){
		TextureRegion[] region_idle = new TextureRegion[1];
		region_idle[0] = tr;
		Animation ani_role_idle = new Animation(0.1f, region_idle);
		return ani_role_idle;
	}
	public Animation getEffectApper(){
		TextureRegion[] region_effect_apper = new TextureRegion[4];
		region_effect_apper[0] = effect_spilt[4][6];
		region_effect_apper[1] = effect_spilt[5][6];
		region_effect_apper[2] = effect_spilt[6][6];
		region_effect_apper[3] = effect_spilt[7][6];
		Animation ani_effect_apper = new Animation(0.1f, region_effect_apper);
		return ani_effect_apper;
	}
	public Animation getEffectDisapper(){
		TextureRegion[] region_effect_disapper = new TextureRegion[4];
		region_effect_disapper[0] = effect_spilt[7][6];
		region_effect_disapper[1] = effect_spilt[6][6];
		region_effect_disapper[2] = effect_spilt[5][6];
		region_effect_disapper[3] = effect_spilt[4][6];
		Animation ani_effect_disapper = new Animation(0.1f, region_effect_disapper);
		return ani_effect_disapper;
	}
	public Animation getEffect0(){
		TextureRegion[] region_effect = new TextureRegion[3];
		region_effect[0] = effect_spilt[0][0];
		region_effect[1] = effect_spilt[0][1];
		region_effect[2] = effect_spilt[0][2];
		Animation ani_effect = new Animation(0.4f, region_effect);
		return ani_effect;
	}
	public Animation getEffect1(){
		TextureRegion[] region_effect = new TextureRegion[3];
		region_effect[0] = effect_spilt[1][0];
		region_effect[1] = effect_spilt[1][1];
		region_effect[2] = effect_spilt[1][2];
		Animation ani_effect = new Animation(0.4f, region_effect);
		return ani_effect;
	}
	public Animation getEffect2(){
		TextureRegion[] region_effect = new TextureRegion[3];
		region_effect[0] = effect_spilt[4][0];
		region_effect[1] = effect_spilt[4][1];
		region_effect[2] = effect_spilt[4][2];
		Animation ani_effect = new Animation(0.4f, region_effect);
		return ani_effect;
	}
	public Animation getEffect3(){
		TextureRegion[] region_effect = new TextureRegion[3];
		region_effect[0] = effect_spilt[4][3];
		region_effect[1] = effect_spilt[4][4];
		region_effect[2] = effect_spilt[4][5];
		Animation ani_effect = new Animation(0.4f, region_effect);
		return ani_effect;
	}
}