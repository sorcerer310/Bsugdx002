package com.bsu.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Configure {

	public static int rect_width=480;
	public static int rect_height=320;
	
	public static String logo_0_texture_string="data/BsuLogo480-320.png";
	public static String logo_0_sound_string="data/snd/chicken.wav";
	
	public static String logo_1_texture_string="data/intro.png";
	public static String logo_1_sound_string="data/snd/highjump.wav";
	
	public static String screen_setting="setting";
	public static String screen_menu="menu";
	public static String screen_game="game";
	
	public static String[] game_level_string={"marioMap","22"};
	
	public static String object_layer_hero="hero";
	public static String object_layer_enemy="enemy";
	public static String object_layer_mario="mario";
	
	static LabelStyle style;
	static BitmapFont font;
	
	
	
	
	private static BitmapFont get_font(){
		font = new BitmapFont(Gdx.files.internal("data/menu/normal.fnt"),
				Gdx.files.internal("data/menu/normal.png"), false);
		return font;
	}
	
	public static LabelStyle get_sytle(){
		style = new LabelStyle(get_font(), get_font().getColor());
		return style;
	}
}
