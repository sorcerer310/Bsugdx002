package com.bsu.gdx002;

import java.util.Observable;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Rectangle;
import com.bsu.head.HeadScreen;
import com.bsu.make.GameScreenFactory;
import com.bsu.screen.CPanelMainScreen;
import com.bsu.screen.FightScreen;
import com.bsu.screen.GameScreen;
import com.bsu.screen.MenuScreen;
import com.bsu.screen.RoleScreen;
import com.bsu.screen.SelectRoleScreen;
import com.bsu.screen.SettingScreen;
import com.bsu.screen.ShopScreen;
import com.bsu.screen.UpdateScreen;
import com.bsu.tools.GC;
import com.bsu.tools.GTC;
import com.bsu.tools.MessageObject;

public class BsuGame extends Game {
	@Override
	public void create() {
		GTC.getInstance();//执行一次加载所有资源
		Rectangle rect = new Rectangle(0, 0, GC.rect_width,
				GC.rect_height);
		//logo1界面
		HeadScreen hs_logo1 = new HeadScreen(this,
				GC.logo_0_texture_string, GC.logo_0_sound_string,
				rect);
		//logo2界面
		HeadScreen hs_logo2 = new HeadScreen(this,
				GC.logo_1_texture_string, GC.logo_1_sound_string,
				rect) {
			@Override
			public void update(Observable o, Object arg) {
				BsuGame.this.setScreen(this);
			}
		};
		//设置界面
		SettingScreen ss = new SettingScreen(this) {
			@Override
			public void update(Observable o, Object arg) {
				if (arg.toString().equals(GC.screen_setting)) {
					BsuGame.this.setScreen(this);
				}
			}
		};
		//游戏界面
		GameScreen gs = new GameScreen(this) {
			@Override
			public void update(Observable o, Object arg) {
				if (arg.toString().equals(GC.screen_game)) {
					BsuGame.this.setScreen(this);
//					this.game_init(GameScreenConfigure.getInstance().getHeroRoles(),GameScreenConfigure.getInstance().getNpcRoles());
					this.game_init(GameScreenFactory.getInstance().makeGameScreen(GameScreen.lv));
				}
			}
		};
//		GameScreenConfigure.getInstance().makeGameScreen2(gs);	
//		GameScreenConfigure.getInstance().makeGameScreen1(gs);					//配置游戏关卡为第一关
//		GameScreenFactory.getInstance().makeGameScreenTeaching(gs);			//教学关卡
		//菜单界面
		MenuScreen ms = new MenuScreen(this) {
			@Override
			public void update(Observable o, Object arg) {
					BsuGame.this.setScreen(this);
			}
		};
		//主菜单面板
		CPanelMainScreen cpms = new CPanelMainScreen(this){
			@Override
			public void update(Observable o,Object arg){
				if(arg.toString().equals(GC.screen_mpanel) 
						|| arg.toString().equals(GC.button_back))
					BsuGame.this.setScreen(this);
			}
		};
		//升级界面
		UpdateScreen us = new UpdateScreen(this){
			@Override
			public void update(Observable o,Object arg){
				MessageObject mo=(MessageObject)arg;
				if(mo.message.equals(GC.screen_update))
					BsuGame.this.setScreen(this);
			}
		};
		//战斗选择副本界面
		FightScreen fs = new FightScreen(this){
			@Override
			public void update(Observable o,Object arg){
				MessageObject mo=(MessageObject)arg;
				if(mo.message.equals(GC.screen_fight))
					BsuGame.this.setScreen(this);
			}
		};
		//人物界面
		RoleScreen rs = new RoleScreen(this){
			@Override
			public void update(Observable o,Object arg){
				MessageObject mo=(MessageObject)arg;
				if(mo.message.equals(GC.screen_role))
					BsuGame.this.setScreen(this);
			}
		};
		//商店界面
		ShopScreen shops = new ShopScreen(this){
			@Override
			public void update(Observable o,Object arg){
				MessageObject mo=(MessageObject)arg;
				if(mo.message.equals(GC.screen_shop))
					BsuGame.this.setScreen(this);
			}
		};
		//队伍更换界面
		SelectRoleScreen srs=new SelectRoleScreen(this){
			@Override
			public void update(Observable o,Object arg){
				MessageObject mo=(MessageObject)arg;
				if(mo.message.equals(GC.screen_selectRole))
					BsuGame.this.setScreen(this);
					this.setChangeRole(mo.o);
			}
		};
		setScreen(hs_logo1);
		hs_logo1.addObserver(hs_logo2);
		hs_logo2.addObserver(ms);
		ms.addObserver(gs);
		ms.addObserver(ss);
		ms.addObserver(cpms);
		cpms.addObserver(fs);
		fs.addObserver(gs);
		fs.addObserver(cpms);
		cpms.addObserver(rs);
		rs.addObserver(cpms);
		cpms.addObserver(us);
		us.addObserver(cpms);
		cpms.addObserver(shops);
		shops.addObserver(cpms);
		cpms.addObserver(srs);
		srs.addObserver(cpms);
		gs.addObserver(cpms);
	}
}
