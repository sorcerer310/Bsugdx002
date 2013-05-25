package com.bsu.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
	public Texture role_texture;
	public Texture mPanel;
	public Texture equipPanel;
	public Texture fightPanel, rolePanel, selectRolePanel, shopPanel,
			skillPanel, updatePanel;
	public TextureAtlas texture_atlas_mbutton;
	public TextureAtlas texture_skills_effect;				//技能效果纹理
	public TextureAtlas texture_skills_icon;				//技能图标纹理
	
	public TextureRegion fc_photo, zyc_photo, h0_photo, h1_photo, h2_photo,
			h3_photo, h4_photo, h5_photo;
	public TextureRegion tmpskillicon;				//临时用图标
	

	private GameTextureClass() {
		// TODO Auto-generated constructor stub
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
		
		texture_atlas_mbutton = new TextureAtlas(Gdx.files.internal("data/menu/mbutton.txt"));

		role_texture = new Texture(
				Gdx.files.internal("data/game/hero/Actor1.png"));
		fc_photo=new TextureRegion(role_texture,0,0,96,96);
		zyc_photo=new TextureRegion(role_texture,96,0,96,96);
		h0_photo=new TextureRegion(role_texture,192,0,96,96);
		h1_photo=new TextureRegion(role_texture,288,0,96,96);
		h2_photo=new TextureRegion(role_texture,0,96,96,96);
		h3_photo=new TextureRegion(role_texture,96,96,96,96);
		h4_photo=new TextureRegion(role_texture,192,96,96,96);
		h5_photo=new TextureRegion(role_texture,288,96,96,96);

		Texture tmpicon = new Texture(Gdx.files.internal("data/game/icon/11.png"));
		tmpskillicon = new TextureRegion(tmpicon,0,0,32,32);
		
		texture_skills_effect = new TextureAtlas(Gdx.files.internal("data/game/effect/skilleffect.txt"));
		texture_skills_icon = new TextureAtlas(Gdx.files.internal("data/game/icon/skillicon.txt"));
	}
	/**
	 * 根据技能索引返回技能的纹理
	 * @param idx	技能索引
	 * @return
	 */
	public TextureRegion getSkillIcon(int idx){
		TextureRegion tr = null;
		switch(idx){
		case 1:
			tr = texture_skills_icon.findRegion("11");
			break;
		case 2:
			tr = texture_skills_icon.findRegion("11");
			break;
		case 3:
			tr = texture_skills_icon.findRegion("11");
			break;
		case 4:
			tr = texture_skills_icon.findRegion("11");
			break;
		default:
			break;
		}
		return tr;
	}
}
