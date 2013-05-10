package com.bsu.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Configure {

	public static int rect_width = 480;						//屏幕宽
	public static int rect_height = 320;					//屏幕高
	
	public static int map_box_value=32;
	public static float duration = 0.5f;					//移动一格需要的时间				
	
	public static String logo_0_texture_string = "data/logo/BsuLogo480-320.png";
	public static String logo_0_sound_string = "data/snd/chicken.wav";

	public static String logo_1_texture_string = "data/logo/intro.png";
	public static String logo_1_sound_string = "data/snd/highjump.wav";

	public static String screen_setting = "setting";		//设置String
	public static String screen_menu = "menu";
	public static String screen_game = "game";

	public static String[] game_map_path_string = { "map1", "marioMap" };//地图关卡名称（路径）

	public static String object_layer_hero = "hero";		//地图元素中对象层角色方名称
	public static String object_layer_enemy = "enemy";
	public static String object_layer_mario = "mario";

	static LabelStyle style;								//文字样式
	static BitmapFont font;									//font

	public static Image[] isAttackedImgGroup;				// Role所有被攻击的特效图像数组
	private static Texture isAttackedTexture;				// Role被攻击特效图形
	
	public static String map_type_block="block";			//地图对象层类型-->障碍
	public static String map_type_buff="buff";				//地图对象层类型-->BUFF
	public static String map_type_hp_rarise="hpRaise";		//地图对象层类型属性-->增加HP
	public static String map_type_hp_reduce="hpReduce";		//地图对象层类型属性-->减少HP
	public static String map_type_box = "box";				//地图对象层类型-->宝箱
	

	
	public static enum STATE {
		idle, attack_normal, move,attack_v,attack_h
	};
	
	/**
	 * 取得FONT
	 * @return
	 */
	private static BitmapFont get_font() {
		if (font == null) {
			font = new BitmapFont(Gdx.files.internal("data/menu/normal.fnt"),
					Gdx.files.internal("data/menu/normal.png"), false);
		}
		return font;
	}
	
	/**
	 * 取得样式
	 * @return
	 */
	public static LabelStyle get_sytle() {
		if (style == null) {
			style = new LabelStyle(get_font(), get_font().getColor());
		}
		return style;
	}

	/**
	 * 取得所有被攻击特效IMG
	 * @return
	 */
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
