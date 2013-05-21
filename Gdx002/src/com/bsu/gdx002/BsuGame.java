package com.bsu.gdx002;

import java.util.Observable;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Rectangle;
import com.bsu.head.HeadScreen;
import com.bsu.make.GameScreenConfigure;
import com.bsu.obj.Player;
import com.bsu.screen.CPanelMainScreen;
import com.bsu.screen.EquipScreen;
import com.bsu.screen.FightScreen;
import com.bsu.screen.GameScreen;
import com.bsu.screen.MenuScreen;
import com.bsu.screen.RoleScreen;
import com.bsu.screen.SelectRoleScreen;
import com.bsu.screen.SettingScreen;
import com.bsu.screen.ShopScreen;
import com.bsu.screen.SkillScreen;
import com.bsu.screen.UpdateScreen;
import com.bsu.tools.Configure;
import com.bsu.tools.GameTextureClass;
import com.bsu.tools.MessageObject;

public class BsuGame extends Game {
	@Override
	public void create() {
		GameTextureClass.getInstance();//执行一次加载所有资源
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
					this.game_init(0, Player.getInstance().playerFightRole);
				}
			}
		};
		GameScreenConfigure.getInstance().makeGameScreen2(gs);	
//		GameScreenConfigure.getInstance().makeGameScreen1(gs);					//配置游戏关卡为第一关
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
				if(arg.toString().equals(Configure.screen_mpanel) 
						|| arg.toString().equals(Configure.button_back))
					BsuGame.this.setScreen(this);
			}
		};
		//升级界面
		UpdateScreen us = new UpdateScreen(this){
			@Override
			public void update(Observable o,Object arg){
				MessageObject mo=(MessageObject)arg;
				if(mo.message.equals(Configure.screen_update))
					BsuGame.this.setScreen(this);
			}
		};
		//战斗选择副本界面
		FightScreen fs = new FightScreen(this){
			@Override
			public void update(Observable o,Object arg){
				MessageObject mo=(MessageObject)arg;
				if(mo.message.equals(Configure.screen_fight))
					BsuGame.this.setScreen(this);
			}
		};
		//人物界面
		RoleScreen rs = new RoleScreen(this){
			@Override
			public void update(Observable o,Object arg){
				MessageObject mo=(MessageObject)arg;
				if(mo.message.equals(Configure.screen_role))
					BsuGame.this.setScreen(this);
			}
		};
		//商店界面
		ShopScreen shops = new ShopScreen(this){
			@Override
			public void update(Observable o,Object arg){
				MessageObject mo=(MessageObject)arg;
				if(mo.message.equals(Configure.screen_shop))
					BsuGame.this.setScreen(this);
			}
		};
		//队伍更换界面
		SelectRoleScreen srs=new SelectRoleScreen(this){
			@Override
			public void update(Observable o,Object arg){
				MessageObject mo=(MessageObject)arg;
				if(mo.message.equals(Configure.screen_selectRole))
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
