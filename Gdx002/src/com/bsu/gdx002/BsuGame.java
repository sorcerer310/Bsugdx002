package com.bsu.gdx002;

import java.util.Observable;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Rectangle;
import com.bsu.head.HeadScreen;
import com.bsu.screen.GameScreen;
import com.bsu.screen.MenuScreen;
import com.bsu.screen.SettingScreen;
import com.bsu.tools.Configure;


public class BsuGame extends Game {
	@Override
	public void create() {

		Rectangle rect = new Rectangle(0,0,Configure.rect_width,Configure.rect_height);
		//定义第一个界面
		HeadScreen hs_logo1 = new HeadScreen(this,Configure.logo_0_texture_string,Configure.logo_0_sound_string,rect);
		HeadScreen hs_logo2 = new HeadScreen(this,Configure.logo_1_texture_string,Configure.logo_1_sound_string,rect){
			@Override
			public void update(Observable o, Object arg) {
				BsuGame.this.setScreen(this);
			}
		};
//		HeadScreen hs_logo3 = new HeadScreen(this,"data/BsuLogo480-320.png","data/snd/chicken.wav",rect){
//			@Override
//			public void update(Observable o, Object arg) {
//				BsuGame.this.setScreen(this);
//			}
//		};
		SettingScreen ss = new SettingScreen(this){
			@Override
			public void update(Observable o,Object arg){
				if(arg.toString().equals(Configure.screen_setting))
				BsuGame.this.setScreen(this);
			}
		};
		 GameScreen gs = new GameScreen(this){
			@Override
			public void update(Observable o,Object arg){
				if(arg.toString().equals(Configure.screen_game))
				BsuGame.this.setScreen(this);
				this.init_game();
			}
		};
		MenuScreen ms = new MenuScreen(this){
			@Override
			public void update(Observable o,Object arg){
				BsuGame.this.setScreen(this);
				
			}
		};

		setScreen(hs_logo1);
		hs_logo1.addObserver(hs_logo2);
		hs_logo2.addObserver(ms);
		ms.addObserver(gs);
		ms.addObserver(ss);
		ss.addObserver(ms);
		gs.addObserver(ms);
	}
}
