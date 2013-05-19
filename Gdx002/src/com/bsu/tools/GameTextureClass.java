package com.bsu.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	private Texture logoCompany;
	private Texture logoGame;
	private Texture logo66Rpg;
	
	private  Texture effect_texture;
	private  Texture role_texture,new_role_texture;
	private Texture mPanel;
	private Texture equipPanel;
	private Texture fightPanel,rolePanel,selectRolePanel,shopPanel,skillPanel,updatePanel;
	
	public GameTextureClass() {
		// TODO Auto-generated constructor stub
		role_texture = new Texture(Gdx.files.internal("data/game/hero/hero.png"));
		effect_texture = new Texture(Gdx.files.internal("data/game/hero/effect.png"));
		mPanel=new Texture(Gdx.files.internal("data/menu/mpanel.png"));
		equipPanel=new Texture(Gdx.files.internal("data/menu/equippanel.png"));
		fightPanel=new Texture(Gdx.files.internal("data/menu/fightpanel.png"));
		rolePanel=new Texture(Gdx.files.internal("data/menu/rolespanel.png"));
		selectRolePanel=new Texture(Gdx.files.internal("data/menu/selectrolepanel.png"));
		shopPanel=new Texture(Gdx.files.internal("data/menu/shoppanel.png"));
		skillPanel=new Texture(Gdx.files.internal("data/menu/skillpanel.png"));
		updatePanel=new Texture(Gdx.files.internal("data/menu/updatepanel.png"));
		new_role_texture = new Texture(Gdx.files.internal("data/game/ui/Actor2.png"));
	}
	
	public Texture getRole_texture() {
		return role_texture;
	}

	public Texture getEffect_texture() {
		return effect_texture;
	}

	public Texture getmPanel() {
		return mPanel;
	}

	public Texture getEquipPanel() {
		return equipPanel;
	}

	public Texture getFightPanel() {
		return fightPanel;
	}

	public Texture getRolePanel() {
		return rolePanel;
	}

	public Texture getSelectRolePanel() {
		return selectRolePanel;
	}

	public Texture getShopPanel() {
		return shopPanel;
	}

	public Texture getSkillPanel() {
		return skillPanel;
	}

	public Texture getUpdatePanel() {
		return updatePanel;
	}

	public Texture getNew_role_texture() {
		return new_role_texture;
	}

}
