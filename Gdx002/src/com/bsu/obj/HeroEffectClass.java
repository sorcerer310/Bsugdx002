package com.bsu.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HeroEffectClass {
	private Texture effect_texture;
	public TextureRegion[][] effect_spilt;
	private TextureRegion[][] effect_miror;
	private static Animation ani_effect_0;
	private static Animation ani_effect_1;
	private static Animation ani_effect_2;
	private static Animation ani_effect_3;

	public HeroEffectClass() {
		// TODO Auto-generated constructor stub
		init_effect_base();
	}

	private void init_effect_base() {
		effect_texture = new Texture(Gdx.files.internal("data/hero/effect.png"));
		effect_spilt = TextureRegion.split(effect_texture, 32, 32);
		effect_miror = TextureRegion.split(effect_texture, 32, 32);
		// effect_0
		TextureRegion[] region_effect_0 = new TextureRegion[3];
		region_effect_0[0] = effect_spilt[0][0];
		region_effect_0[1] = effect_spilt[0][1];
		region_effect_0[2] = effect_spilt[0][2];
		ani_effect_0 = new Animation(0.2f, region_effect_0);

		// effect_1
		TextureRegion[] region_effect_1 = new TextureRegion[3];
		region_effect_1[0] = effect_spilt[1][0];
		region_effect_1[1] = effect_spilt[1][1];
		region_effect_1[2] = effect_spilt[1][2];
		ani_effect_1 = new Animation(0.2f, region_effect_1);

		// effect_2
		TextureRegion[] region_effect_2 = new TextureRegion[3];
		region_effect_2[0] = effect_spilt[4][0];
		region_effect_2[1] = effect_spilt[4][1];
		region_effect_2[2] = effect_spilt[4][2];
		ani_effect_2 = new Animation(0.2f, region_effect_2);

		// effect_3
		TextureRegion[] region_effect_3 = new TextureRegion[3];
		region_effect_3[0] = effect_spilt[4][3];
		region_effect_3[1] = effect_spilt[4][4];
		region_effect_3[2] = effect_spilt[4][5];
		ani_effect_3 = new Animation(0.2f, region_effect_3);
	}

	public static Animation get_effect(Animation ani, int index) {
		switch (index) {
		case 0:
			ani = ani_effect_0;
			break;

		case 1:
			ani = ani_effect_1;
			break;

		case 2:
			ani = ani_effect_2;
			break;

		case 3:
			ani = ani_effect_3;
			break;
		}
		return ani;
	}
}
