package com.bsu.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Configure {

	public static int rect_width = 480; // 屏幕宽
	public static int rect_height = 320; // 屏幕高
	public static int rect_box_width = 15;//屏幕宽格子数
	public static int rect_box_height = 7;//屏幕高格子数
	
	public static int map_box_value = 32;
	public static float duration_move_box = 0.5f; // 移动一格需要的时间
	public static float duration_ani = 0.1f;		//标准动画时间
	public static float duration_skill_effect = 0.08f; // 技能效果释放时间
	public static int extra_value = 10; // 根据坐标判断人物所在格子的额外数值，以免出现格子错误，因为太接近了。

	public static String logo_0_texture_string = "data/logo/BsuLogo.png";
	public static String logo_0_sound_string = "data/snd/chicken.wav";

	public static String logo_1_texture_string = "data/logo/intro.png";
	public static String logo_1_sound_string = "data/snd/highjump.wav";

	public static String screen_setting = "setting"; // 设置String
	public static String screen_menu = "menu";
	public static String screen_game = "game";
	public static String screen_mpanel = "mpanel";
	public static String screen_fight = "mfight";
	public static String screen_role = "mrole";
	public static String screen_update = "msupdate";
	public static String screen_shop = "mshop";
	public static String screen_selectRole = "mselect";
	public static String button_all = "btall";
	public static String button_back = "btback";
	public static String button_green="btgreen";
	public static String button_blue="btblue";
	public static String button_purple="btpurple";
	public static String button_orange="btorange";
	public static String button_up="btup";
	public static String button_eat="bteat";
	public static String button_eatall="bteatall";
	public static String button_level="btlevel";

	public static String[] game_map_path_string = { "teaching","map1", "map2" };// 地图关卡名称（路径）

	public static String map_raw_min_key = "raw_min";// 记录地图长宽数据key
	public static String map_raw_max_key = "raw_max";
	public static String map_coll_min_key = "coll_min";
	public static String map_coll_max_key = "coll_max";
	public static String object_layer_hero = "hero"; // 地图元素中对象层角色方名称
	public static String object_layer_enemy = "enemy";

	public static Image[] isAttackedImgGroup; // Role所有被攻击的特效图像数组
	private static Texture isAttackedTexture; // Role被攻击特效图形

	public static String map_type_block = "block"; // 地图对象层类型-->障碍
	public static String map_type_buff = "buff"; // 地图对象层类型-->BUFF
	public static String map_type_hp_rarise = "hpRaise"; // 地图对象层类型属性-->增加HP
	public static String map_type_hp_reduce = "hpReduce"; // 地图对象层类型属性-->减少HP
	public static String map_type_box = "box"; // 地图对象层类型-->宝箱
	public static String map_type_value = "map_value"; // 地图对象层类型-->长宽属性
	public static String map_type_hero_home = "hero_home"; // 地图对象层类型-->角色基地
	public static String map_type_enemy_home = "enemy_home"; // 地图对象层类型-->NPC基地

	public static int baseHpGreen = 100;// 绿色品质卡片初始基本生命
	public static int baseHpBlue = 200;// 蓝色品质卡片初始生命
	public static int baseHpPurple = 300;
	public static int baseHpOrange = 400;

	public static int baseAttackGreen = 10;// 绿色卡片初始基本攻击力
	public static int baseAttackBlue = 20;
	public static int baseAttackPurple = 30;
	public static int baseAttackOrange = 40;

	public static int baseDefendGreen = 5;// 绿色卡片初始基本防御力
	public static int baseDefendBlue = 10;
	public static int baseDefendPurple = 15;
	public static int baseDefendOrange = 20;

	public static int baseExpGreen = 10;// 绿色卡片初始经验
	public static int baseExpBlue = 50;
	public static int baseExpPurple = 200;
	public static int baseExpOrange = 500;

	public static int baseExpUpGreen = 50;// 绿色卡片升级所需基本经验
	public static int baseExpUpBlue = 200;
	public static int baseExpUpPurple = 500;
	public static int baseExpUpOrange = 1000;
	
	public static String noCard="没有相应品质卡片，请通关进行收集";
	public static String noOpen="功能暂未开放";
	public static String roleUp="可以升级了，升级可以增加属性，开启新技能";

	public static enum STATE { // 人物状态
		idle, attack_normal, move, attack_v, attack_h, disapper, apper,hited,stoped
	};

	public static enum QUALITY {
		green, blue, purple, orange
	}// 品质数据

	public enum QualityS {
		gselect, bselect, pselect, oselect, allselect
	}

	public static enum FACE {
		left, right
	}; // 人物朝向

	public static enum DIRECTION {
		left, right, up, down
	};// 4方向

	public static enum CLASSES {
		fighter, cleric, wizard, sorcerer, archer, all
	};// 人物职业，战士，牧师，元素法师，黑暗法师，射手

}
