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
	public TextureAtlas texture_atlas_button;
	
	public TextureRegion fc_photo, zyc_photo, h0_photo, h1_photo, h2_photo,
			h3_photo, h4_photo, h5_photo;
	public TextureRegion tmpskillicon;				//临时用图标
	public Texture tipsPanel;
	public TextureRegion role_effect;//人物头像效果

	private GameTextureClass() {
		// TODO Auto-generated constructor stub
		effect_texture = new Texture(
				Gdx.files.internal("data/game/hero/effect.png"));
		mPanel = new Texture(Gdx.files.internal("data/menu/mpanel.png"));
		equipPanel = new Texture(Gdx.files.internal("data/menu/equippanel.png"));
		fightPanel = new Texture(Gdx.files.internal("data/menu/fightpanel.png"));
		rolePanel = new Texture(Gdx.files.internal("data/menu/rolespanel.png"));
		tipsPanel=new Texture(Gdx.files.internal("data/menu/wback.png"));
		selectRolePanel = new Texture(
				Gdx.files.internal("data/menu/selectrolepanel.png"));
		shopPanel = new Texture(Gdx.files.internal("data/menu/shoppanel.png"));
		skillPanel = new Texture(Gdx.files.internal("data/menu/skillpanel.png"));
		updatePanel = new Texture(
				Gdx.files.internal("data/menu/updatepanel.png"));
		
		texture_atlas_mbutton = new TextureAtlas(Gdx.files.internal("data/menu/mbutton.txt"));
		texture_atlas_button = new TextureAtlas(Gdx.files.internal("data/button/pack"));
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
		role_effect=new TextureRegion(effect_texture, 224, 160,32,32);
//		Texture tmpicon = new Texture(Gdx.files.internal("data/game/icon/11.png"));
//		tmpskillicon = new TextureRegion(tmpicon,0,0,32,32);
		
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
			tr = texture_skills_icon.findRegion("si1-");
			break;
		case 2:
			tr = texture_skills_icon.findRegion("si2-");
			break;
		case 3:
			tr = texture_skills_icon.findRegion("si3-");
			break;
		case 4:
			tr = texture_skills_icon.findRegion("si4-");
			break;
		case 5:
			tr = texture_skills_icon.findRegion("si5-");
			break;
		case 6:
			tr = texture_skills_icon.findRegion("si6-");
			break;
		case 7:
			tr = texture_skills_icon.findRegion("si7-");
			break;
		case 8:
			tr = texture_skills_icon.findRegion("si8-");
			break;
		case 9:
			tr = texture_skills_icon.findRegion("si9-");
			break;
		case 10:
			tr = texture_skills_icon.findRegion("si10-");
			break;
		case 31:
			tr = texture_skills_icon.findRegion("si31-");
			break;
		case 32:
			tr = texture_skills_icon.findRegion("si32-");
			break;
		case 33:
			tr = texture_skills_icon.findRegion("si33-");
			break;
		case 34:
			tr = texture_skills_icon.findRegion("si34-");
			break;
		case 35:
			tr = texture_skills_icon.findRegion("si35-");
			break;
		case 36:
			tr = texture_skills_icon.findRegion("si36-");
			break;
		case 37:
			tr = texture_skills_icon.findRegion("si37-");
			break;
		case 38:
			tr = texture_skills_icon.findRegion("si38-");
			break;
		case 39:
			tr = texture_skills_icon.findRegion("si39-");
			break;
		case 40:
			tr = texture_skills_icon.findRegion("si40-");
			break;
		case 41:
			tr = texture_skills_icon.findRegion("si41-");
			break;
		case 42:
			tr = texture_skills_icon.findRegion("si42-");
			break;
		case 61:
			tr = texture_skills_icon.findRegion("si61-");
			break;
		case 62:
			tr = texture_skills_icon.findRegion("si62-");
			break;
		case 63:
			tr = texture_skills_icon.findRegion("si63-");
			break;
		case 64:
			tr = texture_skills_icon.findRegion("si64-");
			break;
		case 65:
			tr = texture_skills_icon.findRegion("si65-");
			break;
		case 66:
			tr = texture_skills_icon.findRegion("si66-");
			break;
		case 67:
			tr = texture_skills_icon.findRegion("si67-");
			break;
		case 68:
			tr = texture_skills_icon.findRegion("si68-");
			break;
		case 69:
			tr = texture_skills_icon.findRegion("si69-");
			break;
		case 70:
			tr = texture_skills_icon.findRegion("si70-");
			break;
		case 71:
			tr = texture_skills_icon.findRegion("si71-");
			break;
		case 72:
			tr = texture_skills_icon.findRegion("si72-");
			break;
		case 73:
			tr = texture_skills_icon.findRegion("si73-");
			break;
		case 74:
			tr = texture_skills_icon.findRegion("si74-");
			break;
		case 95:
			tr = texture_skills_icon.findRegion("si95-");
			break;
		case 96:
			tr = texture_skills_icon.findRegion("si96-");
			break;
		case 97:
			tr = texture_skills_icon.findRegion("si97-");
			break;
		case 98:
			tr = texture_skills_icon.findRegion("si98-");
			break;
		case 99:
			tr = texture_skills_icon.findRegion("si99-");
			break;
		case 100:
			tr = texture_skills_icon.findRegion("si100-");
			break;
		case 101:
			tr = texture_skills_icon.findRegion("si101-");
			break;
		case 102:
			tr = texture_skills_icon.findRegion("si102-");
			break;
		case 103:
			tr = texture_skills_icon.findRegion("si103-");
			break;
		default:
			tr = texture_skills_icon.findRegion("siq-");
			break;
		}
		if(tr==null)
			tr = texture_skills_icon.findRegion("siq-");
		return tr;
	}
}
