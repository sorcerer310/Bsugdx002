package com.bsu.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.bsu.tools.Configure;
import com.bsu.tools.Configure.STATE;

public class Role extends Actor {
	private String name = "";						//记录这个角色的名字
	private TextureRegion current_frame;
	private float state_time;						// 状态时间
	private float effect_time;						// 特效时间
	public STATE state;
	public static enum Type {HERO,ENEMY};			//英雄还是NPC
	private Type type = null;						//指定当前角色是英雄还是NPC

	private Animation ani_current;					// 当前动画
	private Animation ani_idle;						//站立动画
	private Animation ani_move;						//移动动画
	private Animation ani_attack_n;					// 基本攻击
	private Animation ani_attack_v;					// 横向攻击
	private Animation ani_attack_h;					// 纵向攻击
	private boolean loop_flag;

	int maxHp = 100; 								// 总血量
	int currentHp = 30; 							// 当前血量
	int attack_value;								// 自身攻击力
	int attacked_nums;								// 目前被连击数值
	int attack_type;								//当前攻击类型
	private TextureRegion effect_current_frame;
	private Animation ani_effect;

	int margin = 2; 								// 血条和人物之间的间隔
	int pixHeight = 5; 								// 血条高度
	int titleWidth = 32;
	TextureRegion pix;

	private boolean isSelected;						// 被选中等待操作？
	/**
	 * 角色初始化
	 * @param t		表示当前角色的类型
	 * @param index	
	 */
	public Role(Type t,String name) {
		// TODO Auto-generated constructor stub
		type = t;
		this.state_time = 0;
		get_values(type);
		set_actor_base(type);
		set_listener();
	}

	// 根据类型获得资源
	private void set_actor_base(Type type ) {
		this.type = type;
		int actor_type = type == Type.HERO ? 2 : 5;
		ani_idle = HeroAnimationClass.getAnimationIdle(actor_type);
		ani_move = HeroAnimationClass.getAnimationMove(actor_type);
		ani_attack_n = HeroAnimationClass
				.getAnimationAttackN(actor_type);
		ani_attack_v = HeroAnimationClass
				.getAnimationAttackV(actor_type);
		ani_attack_h = HeroAnimationClass
				.getAnimationAttackH(actor_type);
		set_ani_from_state(STATE.idle);
	}

	private void show_life_display() {
		pix = null;
		Pixmap pixmap;
		pixmap = new Pixmap(64, 8, Format.RGBA8888); // 生成一张64*8的图片
		pixmap.setColor(Color.BLACK); // 设置颜色为黑色
		pixmap.drawRectangle(0, 0, titleWidth, pixHeight);
		pixmap.setColor(Color.RED); // 设置颜色为红色
		pixmap.fillRectangle(0, 1, titleWidth * currentHp / maxHp,
				pixHeight - 2); // 绘制血条
		Texture pixmaptex = new Texture(pixmap); // 生成图片
		pix = new TextureRegion(pixmaptex, titleWidth, pixHeight); // 切割图片
		pixmap.dispose();
	}

	// 取得角色初始状态属性
	private void get_values(Type type) {
		currentHp = 100;
		maxHp = 100;
		attack_value = 5;
		attacked_nums = 0;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		state_time += Gdx.graphics.getDeltaTime();
		effect_time += Gdx.graphics.getDeltaTime();
		this.setSize(32, 32);
		this.check_frame_finish();
		show_life_display();
		if (current_frame != null) {
			batch.draw(current_frame, getX(), getY());
		}
		if (effect_current_frame != null) {
			batch.draw(effect_current_frame, getX(), getY());
		}
		if (isSelected)
			batch.draw(pix, this.getX(), this.getY() + this.margin + 32); // 绘制
	}

	// 角色攻击,目前npc只有普通攻击
	public void hero_attack_other(Role enemy) {
		if(enemy==null){
			return;
		}
		if (type == Type.HERO) {
			set_attack_type(enemy.attacked_nums);
			enemy.hero_isAttacked(attack_type, attack_value);
		} else {
			enemy.hero_isAttacked(0, attack_value);
		}
	}

	// 被攻击类型0-3
	public void hero_isAttacked(int attack_type, int damage_value) {
		get_effect_state(attack_type);
		currentHp = currentHp - damage_value >= 0 ? currentHp - damage_value
				: 0;
		if (currentHp <= 0) {
			this.getParent().removeActor(this);
		}
		attacked_nums++;
	}

	public void set_selected(boolean b) {
		isSelected = b;
	}

	public boolean get_selected() {
		return isSelected;
	}

	// **********effect ani
	private void get_effect_state(int index) {
		ani_effect = HeroEffectClass.get_effect(ani_effect,index);
		effect_time = 0;
	}

	// 根据敌人被攻击标记设置攻击类型 0-->普通 1-->1 2-->2 ;
	private void set_attack_type(int enemy_attack_num) {
		switch (enemy_attack_num) {
		case 0:
			state = STATE.attack_normal;
			break;
		case 1:
			state = STATE.attack_v;
			break;
		case 2:
			state = STATE.attack_h;
			break;
		default:
			state = STATE.attack_h;
			break;
		}
		attack_type=enemy_attack_num>3?3:enemy_attack_num;
		set_ani_from_state(state);
	}

	// 根据角色状态取得角色动画
	public void set_ani_from_state(STATE s){
		loop_flag = false;
		state=s;
		if (state == STATE.idle) {
			ani_current = ani_idle;
			loop_flag=true;
		}
		if (state == STATE.move) {
			ani_current = ani_move;
			loop_flag=true;
		}
		if (state == STATE.attack_normal) {
			ani_current = ani_attack_n;
		}
		if (state == STATE.attack_h) {
			ani_current = ani_attack_h;
		}
		if (state == STATE.attack_v) {
			ani_current = ani_attack_v;
		}
		state_time = 0;
		
	}
	
	private void check_frame_finish() {
		current_frame = ani_current.getKeyFrame(state_time, loop_flag);
		if (ani_current.isAnimationFinished(state_time)) {
			set_ani_from_state(STATE.idle);
			set_selected(false);
		}
		if (ani_effect != null) {
			effect_current_frame = ani_effect.getKeyFrame(effect_time, false);
			if (ani_effect.isAnimationFinished(effect_time)) {
				effect_current_frame = null;
				ani_effect = null;
				set_selected(false);
			}
			
		}
	}

	private void set_listener() {
		addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				super.touchUp(event, x, y, pointer, button);
			}
		

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				set_selected(true);
				return true;
			}
		});
	}
	public Type getType() {
		return type;
	}
}
