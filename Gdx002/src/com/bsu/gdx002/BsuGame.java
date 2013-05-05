package com.bsu.gdx002;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Rectangle;
import com.bsu.head.HeadScreen;

public class BsuGame extends Game {
	@Override
	public void create() {

		Rectangle rect = new Rectangle(0,0,480,320);
		//定义第一个界面
		HeadScreen hs_logo1 = new HeadScreen(this,"data/BsuLogo480-320.png","data/snd/chicken.wav",rect);
		//第一个界面点击任意建时切换到第二个界面
		hs_logo1.addObserver(new HeadScreen(this,"data/intro.png","data/snd/highjump.wav",rect){
			@Override
			public void update(Observable o, Object arg) {
				BsuGame.this.setScreen(this);
			}
		});
		 
		setScreen(hs_logo1);
	}


}
