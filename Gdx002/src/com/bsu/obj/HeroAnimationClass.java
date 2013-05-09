package com.bsu.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HeroAnimationClass {
	
	private static Texture hero_texture;
	private static TextureRegion[][] spilt;
	private static TextureRegion[][] miror;


	private static void init_animation_base() {
		if (hero_texture == null) {
			hero_texture = new Texture(Gdx.files.internal("data/hero/hero.png"));
			spilt = TextureRegion.split(hero_texture, 32, 32);
			miror = TextureRegion.split(hero_texture, 32, 32);
			for (TextureRegion[] region1 : miror) {
				for (TextureRegion region2 : region1) {
					region2.flip(true, false);
				}
			}
			
		}
	}
	public static Animation getAnimationIdle(int actor_type, int index){
		init_animation_base();
		TextureRegion[] region_idle = new TextureRegion[1];
		region_idle[0] = spilt[actor_type][1];
		Animation ani_idle = new Animation(0.1f, region_idle);
		return ani_idle;
	}
	public static Animation getAnimationMove(int actor_type, int index){
		init_animation_base();
		TextureRegion[] region_move = new TextureRegion[3];
		region_move[0] = spilt[actor_type][0];
		region_move[1] = spilt[actor_type][1];
		region_move[2] = spilt[actor_type][2];
		Animation ani_move= new Animation(0.1f, region_move);
		return ani_move;
	}
	public static Animation getAnimationAttackN(int actor_type, int index){
		init_animation_base();
		TextureRegion[] region_attack_normal = new TextureRegion[3];
		region_attack_normal[0] = spilt[actor_type][3];
		region_attack_normal[1] = spilt[actor_type][4];
		region_attack_normal[2] = spilt[actor_type][5];
		Animation ani_attack_n = new Animation(0.1f, region_attack_normal);
		return ani_attack_n;
	}
	public static Animation getAnimationAttackV(int actor_type, int index){
		init_animation_base();
		TextureRegion[] region_attack_v = new TextureRegion[3];
		region_attack_v[0] = spilt[actor_type][9];
		region_attack_v[1] = spilt[actor_type][10];
		region_attack_v[2] = spilt[actor_type][11];
		Animation ani_attack_v = new Animation(0.1f, region_attack_v);
		return ani_attack_v;
	}
	public static Animation getAnimationAttackH(int actor_type, int index){
		init_animation_base();
		TextureRegion[]  region_attack_h = new TextureRegion[3];
		region_attack_h[0] = spilt[actor_type][6];
		region_attack_h[1] = spilt[actor_type][7];
		region_attack_h[2] = spilt[actor_type][8];
		Animation ani_attack_h = new Animation(0.1f, region_attack_h);
		return ani_attack_h;
	}
}