package com.bsu.gdx002;

import java.util.Observable;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Rectangle;
import com.bsu.head.HeadScreen;
import com.bsu.make.GameScreenConfigure;
import com.bsu.make.ImageFactory;
import com.bsu.screen.CPanelMainScreen;
import com.bsu.screen.EquipScreen;
import com.bsu.screen.FightScreen;
import com.bsu.screen.GameScreen;
import com.bsu.screen.MenuScreen;
import com.bsu.screen.RoleScreen;
import com.bsu.screen.SettingScreen;
import com.bsu.screen.ShopScreen;
import com.bsu.screen.SkillScreen;
import com.bsu.screen.UpdateScreen;
import com.bsu.tools.Configure;

public class BsuGame extends Game {
	@Override
	public void create() {

		ImageFactory.getInstance().loadPack();
		Rectangle rect = new Rectangle(0, 0, Configure.rect_width,
				Configure.rect_height);
		//logo1界面
		HeadScreen hs_logo1 = new HeadScreen(this,
				Configure.logo_0_texture_string, Configure.logo_0_sound_string,
				rect);
		//logo2界面
		HeadScreen hs_logo2 = new HeadScreen(this,
				Configure.logo_1_texture_string, Configure.logo_1_sound_string,
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
				if (arg.toString().equals(Configure.screen_setting)) {
					BsuGame.this.setScreen(this);
				}
			}
		};
		//游戏界面
		GameScreen gs = new GameScreen(this) {
			@Override
			public void update(Observable o, Object arg) {
				if (arg.toString().equals(Configure.screen_game)) {
					BsuGame.this.setScreen(this);
				}
			}
		};
		GameScreenConfigure.getInstance().makeGameScreen1(gs);					//配置游戏关卡为第一关
		//菜单界面
		MenuScreen ms = new MenuScreen(this) {
			@Override
			public void update(Observable o, Object arg) {
				System.out.println(arg.toString());
				//				if(arg.toString().equals(Configure.screen_menu))
					BsuGame.this.setScreen(this);
			}
		};
		//主菜单面板
		CPanelMainScreen cpms = new CPanelMainScreen(this){
			@Override
			public void update(Observable o,Object arg){
				if(arg.toString().equals(Configure.screen_mpanel))
					BsuGame.this.setScreen(this);
			}
		};
		//装备界面
		EquipScreen es = new EquipScreen(this){
			@Override
			public void update(Observable o,Object arg){
				if(arg.toString().equals(Configure.screen_equip))
					BsuGame.this.setScreen(this);
			}
		};
		//升级界面
		UpdateScreen us = new UpdateScreen(this){
			@Override
			public void update(Observable o,Object arg){
				if(arg.toString().equals(Configure.screen_update))
					BsuGame.this.setScreen(this);
			}
		};
		//战斗选择副本界面
		FightScreen fs = new FightScreen(this){
			@Override
			public void update(Observable o,Object arg){
				if(arg.toString().equals(Configure.screen_fight))
					BsuGame.this.setScreen(this);
			}
		};
		//技能界面
		SkillScreen skls = new SkillScreen(this){
			@Override
			public void update(Observable o ,Object arg){
				if(arg.toString().equals(Configure.screen_skill))
					BsuGame.this.setScreen(this);
			}
		};
		//人物界面
		RoleScreen rs = new RoleScreen(this){
			@Override
			public void update(Observable o,Object arg){
				if(arg.toString().equals(Configure.screen_role))
					BsuGame.this.setScreen(this);
			}
		};
		//商店界面
		ShopScreen shops = new ShopScreen(this){
			@Override
			public void update(Observable o,Object arg){
				if(arg.toString().equals(Configure.screen_shop))
					BsuGame.this.setScreen(this);
			}
		};
		
		setScreen(hs_logo1);
		hs_logo1.addObserver(hs_logo2);
		hs_logo2.addObserver(ms);
		ms.addObserver(gs);
		ms.addObserver(ss);
		ms.addObserver(cpms);
//		ss.addObserver(ms);
		//gs.addObserver(ms);
		
		cpms.addObserver(es);
		cpms.addObserver(fs);
		cpms.addObserver(skls);
		cpms.addObserver(rs);
		cpms.addObserver(us);
		cpms.addObserver(shops);
	}
}
