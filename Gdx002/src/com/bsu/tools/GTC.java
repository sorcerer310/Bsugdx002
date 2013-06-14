package com.bsu.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

	public Texture logoCompany;
	public Texture logoGame;
	public Texture logo66Rpg;

	public Texture effect;// 消失效果
	public Texture role;
	public Texture mPanel;
	public Texture equipPanel;
	public Texture fightPanel, rolePanel, selectRolePanel, shopPanel,
			skillPanel, updatePanel;
	public TextureAtlas atlas_mbutton;
	public TextureAtlas skills_effect; // 技能效果纹理
	public TextureAtlas skills_icon; // 技能图标纹理
	public TextureAtlas atlas_button;

	public TextureRegion fc_photo, zyc_photo;
	public Texture tipsPanel;
	public TextureAtlas role_effect;// 人物头像效果
	private TextureAtlas role_classes;// 人物职业图像
	public TextureAtlas role_head_frame; // 人物头像边框
	public TextureAtlas battle_end; // 战场结束使用图片效果
	public TextureRegion fight_texture, cleric_texture, wizard_texture,
			sorcerer_texture, archer_texture;
	public TextureRegion start_zero, start_one, start_two, start_three,
			start_four;

	private GTC() {
		// TODO Auto-generated constructor stub
		effect = new Texture(Gdx.files.internal("data/game/hero/effect.png"));
		role_classes = new TextureAtlas(
				Gdx.files.internal("data/game/icon/classes.txt"));
		fight_texture = role_classes.findRegion("fighter");
		cleric_texture = role_classes.findRegion("cleric");
		wizard_texture = role_classes.findRegion("wizard");
		sorcerer_texture = role_classes.findRegion("sorcerer");
		archer_texture = role_classes.findRegion("archer");
		mPanel = new Texture(Gdx.files.internal("data/menu/mpanel.png"));
		equipPanel = new Texture(Gdx.files.internal("data/menu/equippanel.png"));
		fightPanel = new Texture(Gdx.files.internal("data/menu/fightpanel.png"));
		rolePanel = new Texture(Gdx.files.internal("data/menu/rolespanel.png"));
		tipsPanel = new Texture(Gdx.files.internal("data/menu/wback.png"));
		selectRolePanel = new Texture(
				Gdx.files.internal("data/menu/selectrolepanel.png"));
		shopPanel = new Texture(Gdx.files.internal("data/menu/shoppanel.png"));
		skillPanel = new Texture(Gdx.files.internal("data/menu/skillpanel.png"));
		updatePanel = new Texture(
				Gdx.files.internal("data/menu/updatepanel.png"));

		atlas_mbutton = new TextureAtlas(
				Gdx.files.internal("data/menu/mbutton.txt"));
		atlas_button = new TextureAtlas(Gdx.files.internal("data/button/pack"));
		role = new Texture(Gdx.files.internal("data/game/hero/Actor1.png"));
		fc_photo = new TextureRegion(role, 0, 0, 96, 96);
		zyc_photo = new TextureRegion(role, 96, 0, 96, 96);
		role_effect = new TextureAtlas(
				Gdx.files.internal("data/game/effect/othereffect.txt"));
		start_zero = role_effect.findRegion("star", 1);
		start_one = role_effect.findRegion("star", 2);
		start_two = role_effect.findRegion("star", 3);
		start_three = role_effect.findRegion("star", 4);
		start_four = role_effect.findRegion("star", 5);
		skills_effect = new TextureAtlas(
				Gdx.files.internal("data/game/effect/skilleffect.txt"));
		skills_icon = new TextureAtlas(
				Gdx.files.internal("data/game/icon/skillicon.txt"));
		battle_end = new TextureAtlas(
				Gdx.files.internal("data/game/effect/battleend.txt"));
		role_head_frame = new TextureAtlas(
				Gdx.files.internal("data/game/hero/frame.txt"));
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
		if (q!= null) {
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
