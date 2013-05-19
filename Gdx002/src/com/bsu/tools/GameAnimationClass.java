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
	private TextureRegion[][] effect_spilt;
	private Animation ani_effect_0;
	private Animation ani_effect_1;
	private Animation ani_effect_2;
	private Animation ani_effect_3;
	private Animation ani_effect_disapper;
	private Animation ani_effect_apper;
	private Animation ani_hero_home;
	private Animation ani_enemy_home;

	private void gameAnimations() {
		effect_texture = GameTextureClass.getInstance().effect_texture;
		effect_spilt = TextureRegion.split(effect_texture, 32, 32);

		// effect_0
		TextureRegion[] region_effect_0 = new TextureRegion[3];
		region_effect_0[0] = effect_spilt[0][0];
		region_effect_0[1] = effect_spilt[0][1];
		region_effect_0[2] = effect_spilt[0][2];
		ani_effect_0 = new Animation(0.4f, region_effect_0);

		// effect_1
		TextureRegion[] region_effect_1 = new TextureRegion[3];
		region_effect_1[0] = effect_spilt[1][0];
		region_effect_1[1] = effect_spilt[1][1];
		region_effect_1[2] = effect_spilt[1][2];
		ani_effect_1 = new Animation(0.4f, region_effect_1);

		// effect_2
		TextureRegion[] region_effect_2 = new TextureRegion[3];
		region_effect_2[0] = effect_spilt[4][0];
		region_effect_2[1] = effect_spilt[4][1];
		region_effect_2[2] = effect_spilt[4][2];
		ani_effect_2 = new Animation(0.4f, region_effect_2);

		// effect_3
		TextureRegion[] region_effect_3 = new TextureRegion[3];
		region_effect_3[0] = effect_spilt[4][3];
		region_effect_3[1] = effect_spilt[4][4];
		region_effect_3[2] = effect_spilt[4][5];
		ani_effect_3 = new Animation(0.4f, region_effect_3);

		// effect_apper
		TextureRegion[] region_effect_apper = new TextureRegion[4];
		region_effect_apper[0] = effect_spilt[4][6];
		region_effect_apper[1] = effect_spilt[5][6];
		region_effect_apper[2] = effect_spilt[6][6];
		region_effect_apper[3] = effect_spilt[7][6];
		ani_effect_apper = new Animation(0.1f, region_effect_apper);

		// effect_disapper
		TextureRegion[] region_effect_disapper = new TextureRegion[4];
		region_effect_disapper[0] = effect_spilt[7][6];
		region_effect_disapper[1] = effect_spilt[6][6];
		region_effect_disapper[2] = effect_spilt[5][6];
		region_effect_disapper[3] = effect_spilt[4][6];
		ani_effect_disapper = new Animation(0.1f, region_effect_disapper);

		// effect_hero_home
		TextureRegion[] region_hero_home = new TextureRegion[4];
		region_hero_home[0] = effect_spilt[7][6];
		region_hero_home[1] = effect_spilt[6][6];
		region_hero_home[2] = effect_spilt[5][6];
		region_hero_home[3] = effect_spilt[4][6];
		ani_hero_home = new Animation(0.1f, region_hero_home);

		// effect_disapper
		TextureRegion[] region_enemy_home = new TextureRegion[4];
		region_enemy_home[0] = effect_spilt[7][6];
		region_enemy_home[1] = effect_spilt[6][6];
		region_enemy_home[2] = effect_spilt[5][6];
		region_enemy_home[3] = effect_spilt[4][6];
		ani_enemy_home = new Animation(0.1f, region_enemy_home);
	}

	public Animation getAni_effect_0() {
		return ani_effect_0;
	}

	public Animation getAni_effect_1() {
		return ani_effect_1;
	}

	public Animation getAni_effect_2() {
		return ani_effect_2;
	}

	public Animation getAni_effect_3() {
		return ani_effect_3;
	}

	public Animation getAni_effect_disapper() {
		return ani_effect_disapper;
	}

	public Animation getAni_effect_apper() {
		return ani_effect_apper;
	}

	public Animation getAni_hero_home() {
		return ani_hero_home;
	}

	public Animation getAni_enemy_home() {
		return ani_enemy_home;
	}

}
