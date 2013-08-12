package com.bsu.make;

import java.io.File;
import java.util.Observable;

import com.badlogic.gdx.math.Rectangle;
import com.bsu.gdx002.BsuGame;
import com.bsu.head.HeadScreen;
import com.bsu.screen.BattleScreen;
import com.bsu.screen.CPanelMainScreen;
import com.bsu.screen.GameScreen;
import com.bsu.screen.MenuScreen;
import com.bsu.screen.RoleScreen;
import com.bsu.screen.SettingScreen;
import com.bsu.screen.ShopScreen;
import com.bsu.screen.UpdateScreen;
import com.bsu.tools.GC;
import com.bsu.tools.MessageObject;
import com.bsu.tools.Saver;

/**
 * 管理所有的UI Screen
 * @author fengchong
 *
 */
public class UIScreenManager {
	private static UIScreenManager instance =null;
	public static UIScreenManager getInstance(){
		if(instance==null)
			instance = new UIScreenManager();
		return instance;
	}
	
	private Rectangle rect = new Rectangle(0, 0, GC.rect_width, GC.rect_height);
	private BsuGame bsugame = null;
	
	private HeadScreen hs_logo1  = null;			//logo1界面
	private HeadScreen hs_logo2 = null;				//logo2界面
	private MenuScreen ms = null;					//主菜单界面
	private SettingScreen ss = null;				//设置界面
	private CPanelMainScreen cpms = null;			//主面板界面
	private GameScreen gs = null;					//游戏界面
	private UpdateScreen us = null;					//升级界面
	private BattleScreen bs = null;					//战斗界面
	private RoleScreen rs = null;					//人物界面
	private ShopScreen shops = null;				//商店界面 
	
	private UIScreenManager(){

		
	}
	public void setBsugame(BsuGame bsugame) {
		this.bsugame = bsugame;
	}
	/**
	 * 返回hs_logo1界面
	 * @return
	 */
	public HeadScreen getLogo1Screen(){
		if(hs_logo1==null){
//			初始化logo1界面
			hs_logo1 = new HeadScreen(bsugame, GC.logo_0_texture_string,
					GC.logo_0_sound_string, rect);
			initLogo2Screen();
			hs_logo1.addObserver(hs_logo2);
		}
		return hs_logo1;
	}
	
	/**
	 * 返回logo2界面
	 * @return
	 */
	public void initLogo2Screen(){
		if(hs_logo2==null){
			hs_logo2 = new HeadScreen(bsugame, GC.logo_1_texture_string,
				GC.logo_1_sound_string, rect) {
					@Override
					public void update(Observable o, Object arg) {
						bsugame.setScreen(this);
						this.addAssets();
						initMenuScreen();
//						hs_logo2.addObserver(ms);
					}
				};
			
		}
	}
	/**
	 * 主菜单界面
	 */
	public void initMenuScreen(){
		if(ms==null){
			ms = new MenuScreen(bsugame) {
				@Override
				public void update(Observable o, Object arg) {

					// 当转换到主菜单界面前，加载游戏数据
					File file = new File("save.bin");
					if (file.exists()) {
						Saver saver = Saver.getInstance();
						saver.load();
					}
					bsugame.setScreen(this);
					this.initScreen();
					//初始化设置界面，菜单界面，副本界面
//					if(arg.toString().equals(GC.screen_mpanel))
						initCPanelScreen();
//					if(arg.toString().equals(GC.screen_setting))
						initSettingScreen();
//					if(arg.toString().equals(GC.screen_game))
//						initGameScreen();
				}
			};
			hs_logo2.addObserver(ms);
		}
	}
	/**
	 * 初始化主面板界面
	 */
	public void initCPanelScreen(){
		if(cpms==null){
			// 主菜单面板
			 cpms = new CPanelMainScreen(bsugame) {
				@Override
				public void update(Observable o, Object arg) {
					if (arg.toString().equals(GC.screen_mpanel)
							|| arg.toString().equals(GC.button_back)) {
						bsugame.setScreen(this);
						this.initScreen();

						initUpgradeScreen();
						initRoleScreen();
						initBattleScreen();
						
					}
				}
			};
			ms.addObserver(cpms);
		}
	}
	
	/**
	 * 初始化设置界面
	 */
	public void initSettingScreen(){
		if(ss==null){
			ss = new SettingScreen(bsugame) {
				@Override
				public void update(Observable o, Object arg) {
					if (arg.toString().equals(GC.screen_setting)) {
						bsugame.setScreen(this);
						// this.initScreen();
					}
				}
			};
			ms.addObserver(ss);
		}
		
	}
	/**
	 * 初始化游戏界面
	 */
	public void initGameScreen(){
		if(gs==null){
			// 游戏界面
			 gs = new GameScreen(bsugame) {
				@Override
				public void update(Observable o, Object arg) {
					if (arg.toString().equals(GC.screen_game)) {
						bsugame.setScreen(this);
						this.game_init(GameScreenFactory.getInstance()
								.makeGameScreen(GameScreen.lv-1));
					}
				}
			};
			ms.addObserver(gs);
			bs.addObserver(gs);
			gs.addObserver(cpms);
		}
	}
	/**
	 * 升级界面
	 */
	public void initUpgradeScreen(){
		if(us==null){
			// 升级界面
			 us = new UpdateScreen(bsugame) {
				@Override
				public void update(Observable o, Object arg) {
					MessageObject mo = (MessageObject) arg;
					if (mo.message.equals(GC.screen_update)) {
						bsugame.setScreen(this);
						this.initScreen();
					}
				}
			};
			us.addObserver(cpms);
			cpms.addObserver(us);
		}
	}
	/**
	 * 战斗副本选择界面
	 */
	public void initBattleScreen(){
		if(bs==null){
			// 战斗选择副本界面
			 bs = new BattleScreen(bsugame) {
				@Override
				public void update(Observable o, Object arg) {
					MessageObject mo = (MessageObject) arg;
					if (mo.message.equals(GC.screen_rd)) {
						bsugame.setScreen(this);
						this.initScreen();
						
						initGameScreen();
					}
				}
			};
			bs.addObserver(cpms);
			cpms.addObserver(bs);
		}
	}
	/**
	 * 初始化人物界面
	 */
	public void initRoleScreen(){
		if(rs==null){
			// 人物界面
			rs = new RoleScreen(bsugame) {
				@Override
				public void update(Observable o, Object arg) {
					MessageObject mo = (MessageObject) arg;
					if (mo.message.equals(GC.screen_role)) {
						bsugame.setScreen(this);
						initScreen();
					}
					if (mo.message.equals(role_fight)
							|| mo.message.equals(role_idle)) {
						showQualityRole(quality);
						set_role_batle(mo.o);
					}
					if (mo.message.equals(role_locked)
							|| mo.message.equals(role_unlock)) {
						set_role_lock(mo.o);
					}
					if (mo.message.equals(role_enable_skill)
							|| mo.message.equals(role_level_skill)) {
						set_role_info(mo.o);
						set_role_skill_use(mo.o);
						set_role_skill_tree(mo.o);
						set_player_crystal();
					}
					if (mo.message.equals(role_change_skill)) {
						set_role_info(mo.o);
						set_role_skill_use(mo.o);
					}
				}
			};
			rs.addObserver(cpms);
			cpms.addObserver(rs);
		}
	}
	/**
	 * 初始化商店界面
	 */
	public void initShopScreen(){
		if(shops==null){
			shops = new ShopScreen(bsugame) {
				@Override
				public void update(Observable o, Object arg) {
					MessageObject mo = (MessageObject) arg;
					if (mo.message.equals(GC.screen_shop)) {
						bsugame.setScreen(this);
						this.initScreen();
					}
				}
			};
			shops.addObserver(cpms);
			cpms.addObserver(shops);
		}
	}
}
