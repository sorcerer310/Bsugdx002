package com.bsu.tools;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bsu.obj.Role;
import com.bsu.tools.GC.CLASSES;
import com.bsu.tools.GC.QUALITY;

/**
 * GameTextureClass 加载游戏中所有Texture，其他资源加载之前加载
 * 
 * @author zhangyongchen
 * 
 */
public class GTC {
	private static GTC instance = null;

	public static GTC getInstance() {
		if (instance == null)
			instance = new GTC();
		return instance;
	}
	public ParticleEffect particleEffect;
	
	public Texture logoCompany;																			//公司logo
	public Texture logoGame;																				//游戏logo
	public TextureAtlas head;																				//头像
	public TextureRegion p_fighter, p_archer,p_cleric,p_sorcerer,p_wizard;				//头像切割
	public TextureAtlas ui;																					//UI对象
	public TextureRegion mPanel;																			//UI界面背景图片	
	public TextureRegion upgrade_window;															//升级界面的窗口
	public TextureRegion role_window;																	//人物界面的窗口
	public TextureRegion battle_window;																//战场界面窗口
	public TextureRegion bt_zone1,bt_zone2,bt_level;												//大区按钮,关卡按钮

	
	public Texture effect;																						// 消失效果
	public Texture fightPanel, rolePanel, selectRolePanel, shopPanel,updatePanel;
//	public TextureAtlas atlas_mbutton;
	public TextureAtlas skills_effect; 																		// 技能效果纹理
	public TextureAtlas skills_icon; 																		// 技能图标纹理
//	public TextureAtlas atlas_button;


	public Texture tipsPanel;
	public TextureAtlas role_effect;																		// 人物头像效果
	private TextureAtlas role_classes;																	// 人物职业图像
	public TextureAtlas role_head_frame; 																// 人物头像边框
	public TextureAtlas battle_end; 																		// 战场结束使用图片效果
	public TextureRegion fight_texture, cleric_texture, wizard_texture,sorcerer_texture, archer_texture;
	public TextureRegion start_zero, start_one, start_two, start_three,start_four;
	public AssetManager assetManager;
	
	public HashMap<String,TextureRegion> hm_headItemIcon = new HashMap<String,TextureRegion>(); 

	private GTC() {
		if (assetManager == null) {
			assetManager = new AssetManager();
		}
	}
	/**
	 * 加载图片资源
	 */
	public void loadAssets() {
		assetManager.load("data/game/head/head.txt",TextureAtlas.class);						//头像
		assetManager.load("data/game/ui/ui.txt",TextureAtlas.class);									//界面UI
		
		
		assetManager.load("data/game/hero/effect.png", Texture.class);
		assetManager.load("data/menu/equippanel.png", Texture.class);
		assetManager.load("data/menu/fightpanel.png", Texture.class);
		assetManager.load("data/menu/fightpanel.png", Texture.class);
		assetManager.load("data/menu/rolespanel.png", Texture.class);
		assetManager.load("data/menu/wback.png", Texture.class);
		assetManager.load("data/menu/selectrolepanel.png", Texture.class);
		assetManager.load("data/menu/shoppanel.png", Texture.class);
		assetManager.load("data/menu/skillpanel.png", Texture.class);
		assetManager.load("data/menu/updatepanel.png", Texture.class);
		assetManager.load("data/menu/mbutton.txt", TextureAtlas.class);
//		assetManager.load("data/menu/start.png",Texture.class);
		assetManager.load("data/game/hero/Actor1.png", Texture.class);
		assetManager.load("data/game/icon/classes.txt", TextureAtlas.class);
		assetManager.load("data/game/effect/othereffect.txt",TextureAtlas.class);
		assetManager.load("data/game/effect/skilleffect.txt",TextureAtlas.class);
		assetManager.load("data/game/icon/skillicon.txt", TextureAtlas.class);
		assetManager.load("data/game/effect/battleend.txt", TextureAtlas.class);
		assetManager.load("data/game/hero/frame.txt", TextureAtlas.class);
		assetManager.load("data/button/pack", TextureAtlas.class);
		particleEffect=new ParticleEffect();
		particleEffect.load(Gdx.files.internal("data/particle/particle"), Gdx.files.internal("data/particle/"));
	}
	/**
	 * 设置各资源变量
	 */
	public void setAssets() {
		head = assetManager.get("data/game/head/head.txt");								//头像
		p_fighter =head.findRegion("fighter");
		p_archer =head.findRegion("archer");
		p_cleric =head.findRegion("cleric");
		p_sorcerer =head.findRegion("sorcerer");
		p_wizard =head.findRegion("wizard");
		ui = assetManager.get("data/game/ui/ui.txt");												//UI资源
		mPanel = ui.findRegion("mbg");																	//UI主背景
		upgrade_window = ui.findRegion("window_upgrade");									//升级界面的窗口
		role_window = ui.findRegion("window_role");												//角色窗口
		battle_window = ui.findRegion("window_battle");											//战场窗口
		bt_zone1 = ui.findRegion("bt_zone",1);															//副本区按钮抬起
		bt_zone2 = ui.findRegion("bt_zone",2);															//副本区按钮按下
		bt_level = ui.findRegion("bt_level");																//副本关卡按钮
//		p_fighter = new TextureRegion(head, 0, 0, 96, 96);
//		p_archer = new TextureRegion(head, 96, 0, 96, 96);
		
		
		effect = assetManager.get("data/game/hero/effect.png");
//		mPanel = assetManager.get("data/menu/mpanel.png");
//		start = assetManager.get("data/menu/start.png");
		fightPanel = assetManager.get("data/menu/fightpanel.png");
		rolePanel = assetManager.get("data/menu/rolespanel.png");
		tipsPanel = assetManager.get("data/menu/wback.png");
		selectRolePanel = assetManager.get("data/menu/selectrolepanel.png");
		shopPanel = assetManager.get("data/menu/shoppanel.png");
		updatePanel = assetManager.get("data/menu/updatepanel.png");
		role_classes = assetManager.get("data/game/icon/classes.txt");
		fight_texture = role_classes.findRegion("fighter");
		cleric_texture = role_classes.findRegion("cleric");
		wizard_texture = role_classes.findRegion("wizard");
		sorcerer_texture = role_classes.findRegion("sorcerer");
		archer_texture = role_classes.findRegion("archer");
//		atlas_mbutton = assetManager.get("data/menu/mbutton.txt");
//		atlas_button = assetManager.get("data/button/pack");
		role_effect = assetManager.get("data/game/effect/othereffect.txt");
		start_zero = role_effect.findRegion("star", 1);
		start_one = role_effect.findRegion("star", 2);
		start_two = role_effect.findRegion("star", 3);
		start_three = role_effect.findRegion("star", 4);
		start_four = role_effect.findRegion("star", 5);
		skills_effect = assetManager.get("data/game/effect/skilleffect.txt");
		skills_icon = assetManager.get("data/game/icon/skillicon.txt");
		battle_end = assetManager.get("data/game/effect/battleend.txt");
		role_head_frame = assetManager.get("data/game/hero/frame.txt");
		


		hm_headItemIcon.put("p_fighter", p_fighter);
		hm_headItemIcon.put("p_archer", p_archer);
		hm_headItemIcon.put("p_cleric", p_cleric);
		hm_headItemIcon.put("p_wizard", p_wizard);
		hm_headItemIcon.put("p_sorcerer", p_sorcerer);
		hm_headItemIcon.put("skill_question", skills_icon.findRegion("siq-"));
		
	}

	/**
	 * 根据技能索引返回技能图标的纹理
	 * 
	 * @param idx
	 *            技能索引
	 * @return
	 */
	public TextureRegion getSkillIcon(int idx) {
		TextureRegion tr = null;
		switch (idx) {
		case 1:
			tr = skills_icon.findRegion("si1-");
			break;
		case 2:
			tr = skills_icon.findRegion("si2-");
			break;
		case 3:
			tr = skills_icon.findRegion("si3-");
			break;
		case 4:
			tr = skills_icon.findRegion("si4-");
			break;
		case 5:
			tr = skills_icon.findRegion("si5-");
			break;
		case 6:
			tr = skills_icon.findRegion("si6-");
			break;
		case 7:
			tr = skills_icon.findRegion("si7-");
			break;
		case 8:
			tr = skills_icon.findRegion("si8-");
			break;
		case 9:
			tr = skills_icon.findRegion("si9-");
			break;
		case 10:
			tr = skills_icon.findRegion("si10-");
			break;
		case 31:
			tr = skills_icon.findRegion("si31-");
			break;
		case 32:
			tr = skills_icon.findRegion("si32-");
			break;
		case 33:
			tr = skills_icon.findRegion("si33-");
			break;
		case 34:
			tr = skills_icon.findRegion("si34-");
			break;
		case 35:
			tr = skills_icon.findRegion("si35-");
			break;
		case 36:
			tr = skills_icon.findRegion("si36-");
			break;
		case 37:
			tr = skills_icon.findRegion("si37-");
			break;
		case 38:
			tr = skills_icon.findRegion("si38-");
			break;
		case 39:
			tr = skills_icon.findRegion("si39-");
			break;
		case 40:
			tr = skills_icon.findRegion("si40-");
			break;
		case 41:
			tr = skills_icon.findRegion("si41-");
			break;
		case 42:
			tr = skills_icon.findRegion("si42-");
			break;
		case 61:
			tr = skills_icon.findRegion("si61-");
			break;
		case 62:
			tr = skills_icon.findRegion("si62-");
			break;
		case 63:
			tr = skills_icon.findRegion("si63-");
			break;
		case 64:
			tr = skills_icon.findRegion("si64-");
			break;
		case 65:
			tr = skills_icon.findRegion("si65-");
			break;
		case 66:
			tr = skills_icon.findRegion("si66-");
			break;
		case 67:
			tr = skills_icon.findRegion("si67-");
			break;
		case 68:
			tr = skills_icon.findRegion("si68-");
			break;
		case 69:
			tr = skills_icon.findRegion("si69-");
			break;
		case 70:
			tr = skills_icon.findRegion("si70-");
			break;
		case 71:
			tr = skills_icon.findRegion("si71-");
			break;
		case 72:
			tr = skills_icon.findRegion("si72-");
			break;
		case 73:
			tr = skills_icon.findRegion("si73-");
			break;
		case 74:
			tr = skills_icon.findRegion("si74-");
			break;
		case 95:
			tr = skills_icon.findRegion("si95-");
			break;
		case 96:
			tr = skills_icon.findRegion("si96-");
			break;
		case 97:
			tr = skills_icon.findRegion("si97-");
			break;
		case 98:
			tr = skills_icon.findRegion("si98-");
			break;
		case 99:
			tr = skills_icon.findRegion("si99-");
			break;
		case 100:
			tr = skills_icon.findRegion("si100-");
			break;
		case 101:
			tr = skills_icon.findRegion("si101-");
			break;
		case 102:
			tr = skills_icon.findRegion("si102-");
			break;
		case 103:
			tr = skills_icon.findRegion("si103-");
			break;
		default:
			tr = skills_icon.findRegion("siq-");
			break;
		}
		if (tr == null)
			tr = skills_icon.findRegion("siq-");
		return tr;
	}

	/**
	 * 根据角色获得职业图标
	 * 
	 * @param r
	 *            角色
	 * @return 返回该角色对应的图标
	 */
	public Image getClassesIconImg(CLASSES es) {
		Image img = null;
		if (es == CLASSES.fighter) {
			img = new Image(GTC.getInstance().fight_texture);
		}
		if (es == CLASSES.wizard) {
			img = new Image(GTC.getInstance().wizard_texture);
		}
		if (es == CLASSES.archer) {
			img = new Image(GTC.getInstance().archer_texture);
		}
		if (es == CLASSES.cleric) {
			img = new Image(GTC.getInstance().cleric_texture);
		}
		if (es == CLASSES.sorcerer) {
			img = new Image(GTC.getInstance().sorcerer_texture);
		}
		return img;
	}

	/**
	 * 返回图片边框
	 * 
	 * @param r
	 *            角色对象
	 * @return 返回对应品质的边框
	 */
	public Image getImageFrame(QUALITY q) {
		Image img = null;
		if (q != null) {
			if (q == QUALITY.green)
				img = new Image(role_head_frame.findRegion("frame_green"));
			else if (q == QUALITY.blue)
				img = new Image(role_head_frame.findRegion("frame_blue"));
			else if (q == QUALITY.purple)
				img = new Image(role_head_frame.findRegion("frame_purple"));
			else if (q == QUALITY.orange)
				img = new Image(role_head_frame.findRegion("frame_orange"));
		} else {
			img = new Image(role_head_frame.findRegion("frame_yellow"));
		}
		return img;
	}
}
