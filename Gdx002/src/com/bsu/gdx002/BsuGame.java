package com.bsu.gdx002;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Rectangle;
import com.bsu.head.GameScreen;
import com.bsu.head.HeadScreen;
import com.bsu.head.MenuScreen;
import com.bsu.head.SetScreen;

public class BsuGame extends Game {
	@Override
	public void create() {

		Rectangle rect = new Rectangle(0,0,480,320);
		//定义第一个界面
		HeadScreen hs_logo1 = new HeadScreen(this,"data/BsuLogo480-320.png","data/snd/chicken.wav",rect);
		HeadScreen hs_logo2 = new HeadScreen(this,"data/intro.png","data/snd/highjump.wav",rect){
			@Override
			public void update(Observable o, Object arg) {
				BsuGame.this.setScreen(this);
			}
		};
		HeadScreen hs_logo3 = new HeadScreen(this,"data/BsuLogo480-320.png","data/snd/chicken.wav",rect){
			@Override
			public void update(Observable o, Object arg) {
				BsuGame.this.setScreen(this);
			}
		};
		SetScreen ss = new SetScreen(this){
			@Override
			public void update(Observable o,Object arg){
				BsuGame.this.setScreen(this);
			}
		};
		GameScreen gs = new GameScreen(this){
			@Override
			public void update(Observable o,Object arg){
				
				BsuGame.this.setScreen(this);
			}
		};
		MenuScreen ms = new MenuScreen(this){
			@Override
			public void update(Observable o,Object arg){
				//BsuGame.this.setScreen(this);
				System.out.print(arg+"");
			}
		};

		setScreen(hs_logo1);
		hs_logo1.addObserver(hs_logo2);
		hs_logo2.addObserver(hs_logo3);
		hs_logo3.addObserver(ms);
		ms.addObserver(gs);
		ms.addObserver(ss);
		
		
	}
}
