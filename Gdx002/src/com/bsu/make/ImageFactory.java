package com.bsu.make;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
	public Image mb_back;				//返回按钮
	/**
	 * 加载Pack文件同时加载pack中的资源
	 * @param packpath
	 */
	public void loadPack(){
		TextureAtlas mb_atlas = new TextureAtlas(Gdx.files.internal("data/menu/mbutton.txt"));
		skin = new Skin();
		skin.addRegions(mb_atlas);

		mb_equip = new Image(mb_atlas.findRegion("mb_equip"));
		mb_fight = new Image(mb_atlas.findRegion("mb_fight"));
		mb_role = new Image(mb_atlas.findRegion("mb_role"));
		mb_shop = new Image(mb_atlas.findRegion("mb_shop"));
		mb_skill = new Image(mb_atlas.findRegion("mb_skill"));
		mb_update = new Image(mb_atlas.findRegion("mb_update"));
		mb_back = new Image(mb_atlas.findRegion("back"));
	}

}
