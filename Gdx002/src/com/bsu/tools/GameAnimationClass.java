package com.bsu.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * 加载游戏中所有动画，在贴图加载之后
 * 
 * @author zhangyongchen
 * 
 */
public class GameAnimationClass {
	private static GameAnimationClass instance = null;

	public static GameAnimationClass getInstance() {
		if (instance == null)
			instance = new GameAnimationClass();
		return instance;
	}

	public GameAnimationClass() {
		// TODO Auto-generated constructor stub
		gameAnimations();
	}

	private Texture effect_texture;
	private Texture card_texture;
	private TextureRegion[][] effect_spilt;
	private TextureRegion[][] role_spilt;
	
	private TextureAtlas texture_effect_skills;

	private void gameAnimations() {
		effect_texture = GameTextureClass.getInstance().effect_texture;
		effect_spilt = TextureRegion.split(effect_texture, 32, 32);
		card_texture = GameTextureClass.getInstance().role_texture;
		role_spilt = TextureRegion.split(card_texture, 96, 96);
		texture_effect_skills = GameTextureClass.getInstance().texture_skills_effect;
	}
	public Animation getRoleAnimation(TextureRegion tr){
		TextureRegion[] region_idle = new TextureRegion[1];
		region_idle[0] = tr;
		Animation ani_role_idle = new Animation(0.1f, region_idle);
		return ani_role_idle;
	}
	public Animation getEffectApper(){
		TextureRegion[] region_effect_apper = new TextureRegion[4];
		region_effect_apper[0] = effect_spilt[4][6];
		region_effect_apper[1] = effect_spilt[5][6];
		region_effect_apper[2] = effect_spilt[6][6];
		region_effect_apper[3] = effect_spilt[7][6];
		Animation ani_effect_apper = new Animation(0.1f, region_effect_apper);
		return ani_effect_apper;
	}
	public Animation getEffectDisapper(){
		TextureRegion[] region_effect_disapper = new TextureRegion[4];
		region_effect_disapper[0] = effect_spilt[7][6];
		region_effect_disapper[1] = effect_spilt[6][6];
		region_effect_disapper[2] = effect_spilt[5][6];
		region_effect_disapper[3] = effect_spilt[4][6];
		Animation ani_effect_disapper = new Animation(0.1f, region_effect_disapper);
		return ani_effect_disapper;
	}
	public Animation getEffect0(){
		TextureRegion[] region_effect = new TextureRegion[3];
		region_effect[0] = effect_spilt[0][0];
		region_effect[1] = effect_spilt[0][1];
		region_effect[2] = effect_spilt[0][2];
		Animation ani_effect = new Animation(0.4f, region_effect);
		return ani_effect;
	}
	
	/**
	 * 获得技能拥有者效果
	 * @param idx	技能id
	 * @return
	 */
	public Animation getSkillOwnerEffect(int idx){
		Animation retani = null;
		Array<TextureRegion> tr = new Array<TextureRegion>();
		switch(idx){
		case 1:
			tr.add(texture_effect_skills.findRegion("heavyBeet",1));
			tr.add(texture_effect_skills.findRegion("heavyBeet",2));
			tr.add(texture_effect_skills.findRegion("heavyBeet",3));			
			break;
		case 2:
			tr.add(texture_effect_skills.findRegion("s-2-",1));
			tr.add(texture_effect_skills.findRegion("s-2-",2));
			tr.add(texture_effect_skills.findRegion("s-2-",3));
			break;
		case 3:
			tr.add(texture_effect_skills.findRegion("s-3-",1));
			tr.add(texture_effect_skills.findRegion("s-3-",2));
			tr.add(texture_effect_skills.findRegion("s-3-",3));
			break;
		case 4:
			tr.add(texture_effect_skills.findRegion("stab",1));
			tr.add(texture_effect_skills.findRegion("stab",2));
			tr.add(texture_effect_skills.findRegion("stab",3));
			break;
		case 5:
			tr.add(texture_effect_skills.findRegion("s-5-",1));
			tr.add(texture_effect_skills.findRegion("s-5-",2));
			tr.add(texture_effect_skills.findRegion("s-5-",3));
			break;
		case 6:
			tr.add(texture_effect_skills.findRegion("s-6-",1));
			tr.add(texture_effect_skills.findRegion("s-6-",2));
			tr.add(texture_effect_skills.findRegion("s-6-",3));
			break;
		case 7:
			tr.add(texture_effect_skills.findRegion("s-7-",1));
			tr.add(texture_effect_skills.findRegion("s-7-",2));
			tr.add(texture_effect_skills.findRegion("s-7-",3));
			break;
		case 8:
			tr.add(texture_effect_skills.findRegion("s-8-",1));
			tr.add(texture_effect_skills.findRegion("s-8-",2));
			tr.add(texture_effect_skills.findRegion("s-8-",3));
			break;
		case 9:
			tr.add(texture_effect_skills.findRegion("s-9-",1));
			tr.add(texture_effect_skills.findRegion("s-9-",2));
			break;
		case 10:
			tr.add(texture_effect_skills.findRegion("s-9-",1));
			tr.add(texture_effect_skills.findRegion("s-9-",2));
			tr.add(texture_effect_skills.findRegion("s-9-",1));
			tr.add(texture_effect_skills.findRegion("s-9-",2));
			break;			
		case 31:
			tr.add(texture_effect_skills.findRegion("s-2-",1));
			tr.add(texture_effect_skills.findRegion("s-2-",2));
			tr.add(texture_effect_skills.findRegion("s-2-",3));
			break;
		case 32:
			tr.add(texture_effect_skills.findRegion("s-32-",1));
			tr.add(texture_effect_skills.findRegion("s-32-",2));
			tr.add(texture_effect_skills.findRegion("s-32-",1));
			tr.add(texture_effect_skills.findRegion("s-32-",2));
			tr.add(texture_effect_skills.findRegion("s-32-",1));
			tr.add(texture_effect_skills.findRegion("s-32-",2));
			tr.add(texture_effect_skills.findRegion("s-32-",3));
			tr.add(texture_effect_skills.findRegion("s-32-",4));
			tr.add(texture_effect_skills.findRegion("s-32-",5));
			break;
		case 33:
			break;
		case 34:
			tr.add(texture_effect_skills.findRegion("s-34-",1));
			tr.add(texture_effect_skills.findRegion("s-34-",2));
			break;
		case 35:
			tr.add(texture_effect_skills.findRegion("s-6-",1));
			tr.add(texture_effect_skills.findRegion("s-6-",2));
			tr.add(texture_effect_skills.findRegion("s-6-",3));
			tr.add(texture_effect_skills.findRegion("s-6-",1));
			tr.add(texture_effect_skills.findRegion("s-6-",2));
			tr.add(texture_effect_skills.findRegion("s-6-",3));
			break;
		case 36:
			tr.add(texture_effect_skills.findRegion("s-34-",1));
			tr.add(texture_effect_skills.findRegion("s-34-",2));
			tr.add(texture_effect_skills.findRegion("s-34-",3));
			tr.add(texture_effect_skills.findRegion("s-34-",4));
			break;
		case 37:
			tr.add(texture_effect_skills.findRegion("s-37-",1));
			tr.add(texture_effect_skills.findRegion("s-37-",2));
			tr.add(texture_effect_skills.findRegion("s-37-",3));
			tr.add(texture_effect_skills.findRegion("s-37-",4));
			tr.add(texture_effect_skills.findRegion("s-37-",5));
			tr.add(texture_effect_skills.findRegion("s-37-",6));
			tr.add(texture_effect_skills.findRegion("s-37-",7));
			tr.add(texture_effect_skills.findRegion("s-37-",8));
			tr.add(texture_effect_skills.findRegion("s-37-",9));
			tr.add(texture_effect_skills.findRegion("s-37-",4));
			tr.add(texture_effect_skills.findRegion("s-37-",5));
			tr.add(texture_effect_skills.findRegion("s-37-",6));
			break;
		case 38:
			tr.add(texture_effect_skills.findRegion("s-37-",4));
			tr.add(texture_effect_skills.findRegion("s-37-",5));
			tr.add(texture_effect_skills.findRegion("s-37-",6));
			break;
		case 42:
			tr.add(texture_effect_skills.findRegion("s-42-",5));
			tr.add(texture_effect_skills.findRegion("s-42-",6));
			tr.add(texture_effect_skills.findRegion("s-42-",7));
			tr.add(texture_effect_skills.findRegion("s-42-",8));
			break;
		case 61:
			tr.add(texture_effect_skills.findRegion("s-2-",1));
			tr.add(texture_effect_skills.findRegion("s-2-",2));
			tr.add(texture_effect_skills.findRegion("s-2-",3));
			break;
		case 62:
			tr.add(texture_effect_skills.findRegion("s-62-",1));
			tr.add(texture_effect_skills.findRegion("s-62-",2));
			tr.add(texture_effect_skills.findRegion("s-62-",3));
			break;
		case 63:
			tr.add(texture_effect_skills.findRegion("s-63-",1));
			tr.add(texture_effect_skills.findRegion("s-63-",2));
			tr.add(texture_effect_skills.findRegion("s-63-",3));
			tr.add(texture_effect_skills.findRegion("s-63-",4));
			tr.add(texture_effect_skills.findRegion("s-63-",2));
			tr.add(texture_effect_skills.findRegion("s-63-",3));
			tr.add(texture_effect_skills.findRegion("s-63-",4));
			break;
		case 64:
			tr.add(texture_effect_skills.findRegion("stab",1));
			tr.add(texture_effect_skills.findRegion("stab",2));
			tr.add(texture_effect_skills.findRegion("stab",3));
			break;
		case 66:
			tr.add(texture_effect_skills.findRegion("s-66-",1));
			tr.add(texture_effect_skills.findRegion("s-66-",2));
			tr.add(texture_effect_skills.findRegion("s-66-",3));
			tr.add(texture_effect_skills.findRegion("s-66-",4));
			tr.add(texture_effect_skills.findRegion("s-66-",5));
			tr.add(texture_effect_skills.findRegion("s-66-",6));
			break;
		case 67:
			tr.add(texture_effect_skills.findRegion("s-34-",1));
			tr.add(texture_effect_skills.findRegion("s-34-",2));
			tr.add(texture_effect_skills.findRegion("s-34-",3));
			tr.add(texture_effect_skills.findRegion("s-34-",4));
			tr.add(texture_effect_skills.findRegion("s-34-",1));
			tr.add(texture_effect_skills.findRegion("s-34-",2));
			tr.add(texture_effect_skills.findRegion("s-34-",3));
			tr.add(texture_effect_skills.findRegion("s-34-",4));
			break;
		case 70:
			tr.add(texture_effect_skills.findRegion("s-42-",5));
			tr.add(texture_effect_skills.findRegion("s-42-",6));
			tr.add(texture_effect_skills.findRegion("s-42-",7));
			tr.add(texture_effect_skills.findRegion("s-42-",8));
			break;
		case 71:
			tr.add(texture_effect_skills.findRegion("s-71-",1));
			tr.add(texture_effect_skills.findRegion("s-71-",2));
			tr.add(texture_effect_skills.findRegion("s-71-",3));
			tr.add(texture_effect_skills.findRegion("s-71-",4));
			break;
		case 73:
			tr.add(texture_effect_skills.findRegion("s-73-",1));
			tr.add(texture_effect_skills.findRegion("s-73-",2));
			tr.add(texture_effect_skills.findRegion("s-73-",1));
			tr.add(texture_effect_skills.findRegion("s-73-",2));
			tr.add(texture_effect_skills.findRegion("s-73-",3));
			tr.add(texture_effect_skills.findRegion("s-73-",4));
			tr.add(texture_effect_skills.findRegion("s-73-",5));
			tr.add(texture_effect_skills.findRegion("s-73-",5));
			break;
		case 95:
			tr.add(texture_effect_skills.findRegion("s-95-",1));
			tr.add(texture_effect_skills.findRegion("s-95-",1));
			tr.add(texture_effect_skills.findRegion("s-95-",2));
			tr.add(texture_effect_skills.findRegion("s-95-",2));
			tr.add(texture_effect_skills.findRegion("s-95-",3));
			tr.add(texture_effect_skills.findRegion("s-95-",3));
			tr.add(texture_effect_skills.findRegion("s-95-",4));
			tr.add(texture_effect_skills.findRegion("s-95-",5));
			tr.add(texture_effect_skills.findRegion("s-95-",6));
			tr.add(texture_effect_skills.findRegion("s-95-",4));
			tr.add(texture_effect_skills.findRegion("s-95-",5));
			tr.add(texture_effect_skills.findRegion("s-95-",6));
			tr.add(texture_effect_skills.findRegion("s-95-",7));
			tr.add(texture_effect_skills.findRegion("s-95-",7));
			break;
		default:
			break;
		}
		if(tr.size>0)
			retani = new Animation(Configure.duration_skill_effect,tr);
		return retani;
	}
	/**
	 * 获得技能承受者效果
	 * @param idx	技能id
	 * @return
	 */
	public Animation getSkillObjectEffect(int idx){
		Animation retani = null;
		Array<TextureRegion> tr = new Array<TextureRegion>();
		switch(idx){
		case 1:
			tr.add(texture_effect_skills.findRegion("isAttacked",1));
			tr.add(texture_effect_skills.findRegion("isAttacked",2));
			tr.add(texture_effect_skills.findRegion("isAttacked",3));	
			break;
		case 2:
			tr.add(texture_effect_skills.findRegion("s-3-",4));
			tr.add(texture_effect_skills.findRegion("s-3-",4));
			tr.add(texture_effect_skills.findRegion("s-3-",5));
			tr.add(texture_effect_skills.findRegion("s-3-",5));
			break;
		case 3:
			tr.add(texture_effect_skills.findRegion("s-3-",4));
			tr.add(texture_effect_skills.findRegion("s-3-",4));
			tr.add(texture_effect_skills.findRegion("s-3-",5));
			tr.add(texture_effect_skills.findRegion("s-3-",5));
			break;
		case 4:
			tr.add(texture_effect_skills.findRegion("isAttacked",1));
			tr.add(texture_effect_skills.findRegion("isAttacked",2));
			tr.add(texture_effect_skills.findRegion("isAttacked",3));	
			break;
		case 5:
			tr.add(texture_effect_skills.findRegion("s-3-",4));
			tr.add(texture_effect_skills.findRegion("s-3-",4));
			tr.add(texture_effect_skills.findRegion("s-3-",5));
			tr.add(texture_effect_skills.findRegion("s-3-",5));
			break;
		case 33:
			tr.add(texture_effect_skills.findRegion("s-33-",1));
			tr.add(texture_effect_skills.findRegion("s-33-",2));
			tr.add(texture_effect_skills.findRegion("s-33-",3));
			tr.add(texture_effect_skills.findRegion("s-33-",4));
			tr.add(texture_effect_skills.findRegion("s-33-",5));
			tr.add(texture_effect_skills.findRegion("s-33-",6));
			tr.add(texture_effect_skills.findRegion("s-33-",7));
			tr.add(texture_effect_skills.findRegion("s-33-",8));
			tr.add(texture_effect_skills.findRegion("s-33-",7));
			tr.add(texture_effect_skills.findRegion("s-33-",8));
			tr.add(texture_effect_skills.findRegion("s-33-",7));
			tr.add(texture_effect_skills.findRegion("s-33-",8));
			break;
		case 34:
			tr.add(texture_effect_skills.findRegion("s-34-",1));
			tr.add(texture_effect_skills.findRegion("s-34-",2));
			tr.add(texture_effect_skills.findRegion("s-34-",3));
			tr.add(texture_effect_skills.findRegion("s-34-",4));
			break;
		case 36:
			tr.add(texture_effect_skills.findRegion("s-36-",1));
			tr.add(texture_effect_skills.findRegion("s-36-",2));
			tr.add(texture_effect_skills.findRegion("s-36-",3));
			tr.add(texture_effect_skills.findRegion("s-36-",4));
			tr.add(texture_effect_skills.findRegion("s-36-",5));
			tr.add(texture_effect_skills.findRegion("s-36-",6));
			break;
		case 38:
			tr.add(texture_effect_skills.findRegion("s-38-",1));
			tr.add(texture_effect_skills.findRegion("s-38-",2));
			tr.add(texture_effect_skills.findRegion("s-38-",3));
			tr.add(texture_effect_skills.findRegion("s-38-",4));
			tr.add(texture_effect_skills.findRegion("s-38-",2));
			tr.add(texture_effect_skills.findRegion("s-38-",1));
			break;
		case 39:
			tr.add(texture_effect_skills.findRegion("s-39-",1));
			tr.add(texture_effect_skills.findRegion("s-39-",2));
			tr.add(texture_effect_skills.findRegion("s-39-",3));
			tr.add(texture_effect_skills.findRegion("s-39-",4));
			tr.add(texture_effect_skills.findRegion("s-39-",5));
			tr.add(texture_effect_skills.findRegion("s-39-",6));
			break;
		case 42:
			tr.add(texture_effect_skills.findRegion("s-42-",1));
			tr.add(texture_effect_skills.findRegion("s-42-",2));
			tr.add(texture_effect_skills.findRegion("s-42-",3));
			tr.add(texture_effect_skills.findRegion("s-42-",4));
			tr.add(texture_effect_skills.findRegion("s-42-",4));
			tr.add(texture_effect_skills.findRegion("s-42-",4));
			break;
		case 61:
			tr.add(texture_effect_skills.findRegion("s-61-",1));
			tr.add(texture_effect_skills.findRegion("s-61-",2));
			tr.add(texture_effect_skills.findRegion("s-61-",3));
			tr.add(texture_effect_skills.findRegion("s-61-",4));
			tr.add(texture_effect_skills.findRegion("s-61-",5));
			break;
		case 64:
			tr.add(texture_effect_skills.findRegion("isAttacked",1));
			tr.add(texture_effect_skills.findRegion("isAttacked",2));
			tr.add(texture_effect_skills.findRegion("isAttacked",3));	
			break;
		case 65:
			tr.add(texture_effect_skills.findRegion("s-33-",1));
			tr.add(texture_effect_skills.findRegion("s-33-",2));
			tr.add(texture_effect_skills.findRegion("s-33-",3));
			tr.add(texture_effect_skills.findRegion("s-33-",4));
			tr.add(texture_effect_skills.findRegion("s-33-",5));
			tr.add(texture_effect_skills.findRegion("s-33-",6));
			tr.add(texture_effect_skills.findRegion("s-33-",1));
			tr.add(texture_effect_skills.findRegion("s-33-",2));
			tr.add(texture_effect_skills.findRegion("s-33-",3));
			tr.add(texture_effect_skills.findRegion("s-33-",4));
			tr.add(texture_effect_skills.findRegion("s-33-",5));
			tr.add(texture_effect_skills.findRegion("s-33-",6));
			tr.add(texture_effect_skills.findRegion("s-33-",1));
			tr.add(texture_effect_skills.findRegion("s-33-",2));
			tr.add(texture_effect_skills.findRegion("s-33-",3));
			tr.add(texture_effect_skills.findRegion("s-33-",4));
			tr.add(texture_effect_skills.findRegion("s-33-",5));
			tr.add(texture_effect_skills.findRegion("s-33-",6));
			tr.add(texture_effect_skills.findRegion("s-33-",7));
			tr.add(texture_effect_skills.findRegion("s-33-",8));
			tr.add(texture_effect_skills.findRegion("s-33-",7));
			tr.add(texture_effect_skills.findRegion("s-33-",8));
			tr.add(texture_effect_skills.findRegion("s-33-",7));
			tr.add(texture_effect_skills.findRegion("s-33-",8));
			break;
		case 67:
			tr.add(texture_effect_skills.findRegion("s-36-",1));
			tr.add(texture_effect_skills.findRegion("s-36-",2));
			tr.add(texture_effect_skills.findRegion("s-36-",3));
			tr.add(texture_effect_skills.findRegion("s-36-",4));
			tr.add(texture_effect_skills.findRegion("s-36-",5));
			tr.add(texture_effect_skills.findRegion("s-36-",6));
			tr.add(texture_effect_skills.findRegion("s-36-",1));
			tr.add(texture_effect_skills.findRegion("s-36-",2));
			tr.add(texture_effect_skills.findRegion("s-36-",3));
			tr.add(texture_effect_skills.findRegion("s-36-",4));
			tr.add(texture_effect_skills.findRegion("s-36-",5));
			tr.add(texture_effect_skills.findRegion("s-36-",6));
			break;
		case 68:
			tr.add(texture_effect_skills.findRegion("s-34-",1));
			tr.add(texture_effect_skills.findRegion("s-34-",2));
			tr.add(texture_effect_skills.findRegion("s-34-",3));
			tr.add(texture_effect_skills.findRegion("s-34-",4));
			break;
		case 70:
			tr.add(texture_effect_skills.findRegion("s-70-",1));
			tr.add(texture_effect_skills.findRegion("s-70-",2));
			tr.add(texture_effect_skills.findRegion("s-70-",1));
			tr.add(texture_effect_skills.findRegion("s-70-",2));
			break;
		case 74:
			tr.add(texture_effect_skills.findRegion("s-74-",1));
			tr.add(texture_effect_skills.findRegion("s-74-",2));
			tr.add(texture_effect_skills.findRegion("s-74-",3));
			tr.add(texture_effect_skills.findRegion("s-74-",4));
			tr.add(texture_effect_skills.findRegion("s-74-",5));
			tr.add(texture_effect_skills.findRegion("s-74-",6));
			tr.add(texture_effect_skills.findRegion("s-74-",7));
			tr.add(texture_effect_skills.findRegion("s-74-",8));
			break;
		case 95:
			tr.add(texture_effect_skills.findRegion("s-33-",7));
			tr.add(texture_effect_skills.findRegion("s-33-",8));
			tr.add(texture_effect_skills.findRegion("s-33-",7));
			tr.add(texture_effect_skills.findRegion("s-33-",8));
			tr.add(texture_effect_skills.findRegion("s-33-",7));
			tr.add(texture_effect_skills.findRegion("s-33-",8));
			break;
		case 96:
			tr.add(texture_effect_skills.findRegion("s-96-",1));
			tr.add(texture_effect_skills.findRegion("s-96-",2));
			tr.add(texture_effect_skills.findRegion("s-96-",3));
			tr.add(texture_effect_skills.findRegion("s-96-",4));
			tr.add(texture_effect_skills.findRegion("s-96-",5));
			tr.add(texture_effect_skills.findRegion("s-96-",6));
			tr.add(texture_effect_skills.findRegion("s-96-",7));
			tr.add(texture_effect_skills.findRegion("s-96-",8));
			tr.add(texture_effect_skills.findRegion("s-96-",9));
			tr.add(texture_effect_skills.findRegion("s-96-",10));
			tr.add(texture_effect_skills.findRegion("s-96-",11));
			tr.add(texture_effect_skills.findRegion("s-96-",12));
			tr.add(texture_effect_skills.findRegion("s-96-",13));
			tr.add(texture_effect_skills.findRegion("s-96-",14));
			tr.add(texture_effect_skills.findRegion("s-96-",15));
			break;
		case 102:
			tr.add(texture_effect_skills.findRegion("s-102-",1));
			tr.add(texture_effect_skills.findRegion("s-102-",2));
			tr.add(texture_effect_skills.findRegion("s-102-",3));
			tr.add(texture_effect_skills.findRegion("s-102-",4));
			tr.add(texture_effect_skills.findRegion("s-102-",1));
			tr.add(texture_effect_skills.findRegion("s-102-",2));
			tr.add(texture_effect_skills.findRegion("s-102-",3));
			tr.add(texture_effect_skills.findRegion("s-102-",4));
			tr.add(texture_effect_skills.findRegion("s-102-",1));
			tr.add(texture_effect_skills.findRegion("s-102-",2));
			tr.add(texture_effect_skills.findRegion("s-102-",3));
			tr.add(texture_effect_skills.findRegion("s-102-",4));
			break;
		default:
			break;
		}
		if(tr.size>0)
			retani = new Animation(Configure.duration_skill_effect,tr);
		return retani;
	}
	/**
	 * 返回持续性状态动画
	 * @param idx	对应的技能ID
	 * @return
	 */
	public Animation getContinuedEffect(int idx){
		Animation retani = null;
		Array<TextureRegion> tr = new Array<TextureRegion>();
		switch(idx){
		case 1:
			tr.clear();
			tr.add(texture_effect_skills.findRegion("dizzy",1));
			tr.add(texture_effect_skills.findRegion("dizzy",2));
			tr.add(texture_effect_skills.findRegion("dizzy",3));
			tr.add(texture_effect_skills.findRegion("dizzy",1));
			tr.add(texture_effect_skills.findRegion("dizzy",2));
			tr.add(texture_effect_skills.findRegion("dizzy",3));
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 31:
			tr.add(texture_effect_skills.findRegion("s-31-",1));
			tr.add(texture_effect_skills.findRegion("s-31-",2));
			tr.add(texture_effect_skills.findRegion("s-31-",3));
			break;
		case 39:
			tr.add(texture_effect_skills.findRegion("s-39-",1));
			tr.add(texture_effect_skills.findRegion("s-39-",2));
			tr.add(texture_effect_skills.findRegion("s-39-",3));
			tr.add(texture_effect_skills.findRegion("s-39-",4));
			tr.add(texture_effect_skills.findRegion("s-39-",5));
			tr.add(texture_effect_skills.findRegion("s-39-",6));
			break;
		case 42:
			tr.add(texture_effect_skills.findRegion("s-42-",1));
			tr.add(texture_effect_skills.findRegion("s-42-",2));
			tr.add(texture_effect_skills.findRegion("s-42-",3));
			tr.add(texture_effect_skills.findRegion("s-42-",4));
			break;
		case 68:
			tr.add(texture_effect_skills.findRegion("s-34-",1));
			tr.add(texture_effect_skills.findRegion("s-34-",2));
			tr.add(texture_effect_skills.findRegion("s-34-",3));
			tr.add(texture_effect_skills.findRegion("s-34-",4));
			break;
		case 70:
			tr.add(texture_effect_skills.findRegion("s-70-",1));
			tr.add(texture_effect_skills.findRegion("s-70-",2));
			tr.add(texture_effect_skills.findRegion("s-70-",1));
			tr.add(texture_effect_skills.findRegion("s-70-",2));
			tr.add(texture_effect_skills.findRegion("s-70-",1));
			tr.add(texture_effect_skills.findRegion("s-70-",2));
			break;
		case 102:
			tr.add(texture_effect_skills.findRegion("s-102-",1));
			tr.add(texture_effect_skills.findRegion("s-102-",2));
			tr.add(texture_effect_skills.findRegion("s-102-",3));
			tr.add(texture_effect_skills.findRegion("s-102-",4));
			break;
		default:
			break;
		}
		if(tr.size>0)
			retani = new Animation(Configure.duration_skill_effect,tr);
		return retani;
	}
}
