package com.bsu.make;

import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ImageFactory {
	private static ImageFactory instance = null;
	private ImageFactory(){}
	public static ImageFactory getInstance(){
		if(instance==null)
			instance = new ImageFactory();
		return instance;
	}
	public Skin skin;
	public Image mb_equip;					//主界面装备按钮
	public Image mb_fight;				//主界面战斗按钮
	public Image mb_role;					//主角色按钮 
	public Image mb_selectrole;			//选择角色按钮
	public Image mb_shop;					//商店按钮
	public Image mb_skill;				//技能按钮 
	public Image mb_update;				//升级按钮
	
	/**
	 * 加载Pack文件同时加载pack中的资源
	 * @param packpath
	 */
	public void loadPack(){
		TextureAtlas mb_atlas = new TextureAtlas(Gdx.files.internal("data/menu/mbutton.txt"));
		skin = new Skin();
		skin.addRegions(mb_atlas);

		mb_equip = new Image(mb_atlas.findRegion("mb_equip"));
//		mb_equip_down = new Image(mb_atlas.findRegion("mb_equip"));
		mb_fight = new Image(mb_atlas.findRegion("mb_fight"));
		mb_role = new Image(mb_atlas.findRegion("mb_role"));
		mb_shop = new Image(mb_atlas.findRegion("mb_shop"));
		mb_skill = new Image(mb_atlas.findRegion("mb_skill"));
		mb_update = new Image(mb_atlas.findRegion("mb_update"));
//		mb_selectrole = new Image(mb_atlas.findRegion("mb_selectrole"));
//		mb_fight = skin.get("mb_fight",Texture.class);
//		mb_role = skin.get("mb_role",Texture.class);
//		mb_shop = skin.get("mb_shop",Texture.class);
//		mb_skill = skin.get("mb_skill",Texture.class);
//		mb_update = skin.get("mb_update",Texture.class);
	}

}
