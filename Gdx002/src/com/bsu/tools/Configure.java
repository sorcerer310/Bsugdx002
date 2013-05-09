package com.bsu.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Configure {

	public static int rect_width = 480;
	public static int rect_height = 320;
	
	public static int map_box_value=32;

	public static String logo_0_texture_string = "data/logo/BsuLogo480-320.png";
	public static String logo_0_sound_string = "data/snd/chicken.wav";

	public static String logo_1_texture_string = "data/logo/intro.png";
	public static String logo_1_sound_string = "data/snd/highjump.wav";

	public static String screen_setting = "setting";
	public static String screen_menu = "menu";
	public static String screen_game = "game";

	public static String[] game_map_path_string = { "map1", "marioMap" };

	public static String object_layer_hero = "hero";
	public static String object_layer_enemy = "enemy";
	public static String object_layer_mario = "mario";

	static LabelStyle style;
	static BitmapFont font;

	public static Image[] isAttackedImgGroup;// 所有被攻击的效果图片
	private static Texture isAttackedTexture;// 所有被攻击的贴图集合
	
	public static enum STATE {
		idle, attack_normal, move,attack_v,attack_h
	};
	
	//取得设置好的字体
	private static BitmapFont get_font() {
		if (font == null) {
			font = new BitmapFont(Gdx.files.internal("data/menu/normal.fnt"),
					Gdx.files.internal("data/menu/normal.png"), false);
		}
		return font;
	}
	
	//取得文本样式
	public static LabelStyle get_sytle() {
		if (style == null) {
			style = new LabelStyle(get_font(), get_font().getColor());
		}
		return style;
	}

	// 取得所有的被攻击效果图片
	public static Image[] get_isAttackedImg() {
		if (isAttackedImgGroup == null) {
			isAttackedTexture = new Texture(
					Gdx.files.internal("data/hero/hero.png"));
			TextureRegion[][] spilt = TextureRegion.split(isAttackedTexture,
					map_box_value, map_box_value);

			int total_num = 0;
			for (int i = 0; i < spilt.length; i++) {
				for (int j = 0; j < spilt[i].length; j++) {
					total_num++;
				}
			}
			isAttackedImgGroup = new Image[total_num];
			int temp_index = 0;
			for (int i = 0; i < spilt.length; i++) {
				for (int j = 0; j < spilt[i].length; j++) {
					isAttackedImgGroup[temp_index] = new Image(spilt[i][j]);
				}
			}
		}
		return isAttackedImgGroup;
	}
}
