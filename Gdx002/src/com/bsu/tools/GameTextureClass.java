package com.bsu.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.bsu.make.SkillFactory;

/**
 * 加载游戏中所有Texture，其他资源加载之前加载
 * 
 * @author zhangyongchen
 * 
 */
public class GameTextureClass {
	private static GameTextureClass instance = null;

	public static GameTextureClass getInstance() {
		if (instance == null)
			instance = new GameTextureClass();
		return instance;
	}

	public Texture logoCompany;
	public Texture logoGame;
	public Texture logo66Rpg;

	public Texture effect_texture;
	public Texture role_texture, new_role_texture;
	public Texture card_texture;
	public Texture mPanel;
	public Texture equipPanel;
	public Texture fightPanel, rolePanel, selectRolePanel, shopPanel,
			skillPanel, updatePanel;
	public TextureAtlas textureatlas_mbutton;
//	public Skin skin;					//暂时没用上，以后准备使用
//	public Image mb_equip;				//主界面装备按钮
//	public Image mb_fight;				//主界面战斗按钮
//	public Image mb_role;				//主角色按钮 
//	public Image mb_selectrole;			//选择角色按钮
//	public Image mb_shop;				//商店按钮
//	public Image mb_skill;				//技能按钮 
//	public Image mb_update;				//升级按钮
//	public Image mb_back;				//返回按钮
	

	private GameTextureClass() {
		// TODO Auto-generated constructor stub
		role_texture = new Texture(
				Gdx.files.internal("data/game/hero/hero.png"));
		effect_texture = new Texture(
				Gdx.files.internal("data/game/hero/effect.png"));
		mPanel = new Texture(Gdx.files.internal("data/menu/mpanel.png"));
		equipPanel = new Texture(Gdx.files.internal("data/menu/equippanel.png"));
		fightPanel = new Texture(Gdx.files.internal("data/menu/fightpanel.png"));
		rolePanel = new Texture(Gdx.files.internal("data/menu/rolespanel.png"));
		selectRolePanel = new Texture(
				Gdx.files.internal("data/menu/selectrolepanel.png"));
		shopPanel = new Texture(Gdx.files.internal("data/menu/shoppanel.png"));
		skillPanel = new Texture(Gdx.files.internal("data/menu/skillpanel.png"));
		updatePanel = new Texture(
				Gdx.files.internal("data/menu/updatepanel.png"));
		new_role_texture = new Texture(
				Gdx.files.internal("data/game/ui/Actor2.png"));
		card_texture = new Texture(Gdx.files.internal("data/game/ui/Actor2.png"));
		
		
		textureatlas_mbutton = new TextureAtlas(Gdx.files.internal("data/menu/mbutton.txt"));
//		mb_equip = new Image(mb_atlas.findRegion("mb_equip"));
//		mb_fight = new Image(mb_atlas.findRegion("mb_fight"));
//		mb_role = new Image(mb_atlas.findRegion("mb_role"));
//		mb_shop = new Image(mb_atlas.findRegion("mb_shop"));
//		mb_skill = new Image(mb_atlas.findRegion("mb_skill"));
//		mb_update = new Image(mb_atlas.findRegion("mb_update"));
//		mb_back = new Image(mb_atlas.findRegion("back"));
	}
}
