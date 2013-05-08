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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bsu.tools.Configure;
import com.bsu.tools.Configure.STATE;

public class Role extends Actor {
	private Texture texture;
	private TextureRegion current_frame;
	private float total_time;// ��ʱ��
	private float state_time;// ״̬ʱ��
	private float effect_time;// ��Чʱ��
	public STATE state;
	public static enum Type {HERO,ENEMY};
	private Type type = null;

	private Animation ani_current;// ��ǰ����
	private Animation ani_idle;
	private Animation ani_move;
	private Animation ani_attack_n;// ��������
	private Animation ani_attack_v;// ���򹥻�
	private Animation ani_attack_h;// ���򹥻�
	private boolean loop_flag;

	int maxHp = 100; // ��Ѫ��
	int currentHp = 30; // ��ǰѪ��
	int attack_value;// ��������
	int attacked_nums;// Ŀǰ��������ֵ
	int attack_type;//��ǰ��������
	private TextureRegion effect_current_frame;
	private Animation ani_effect;

	int margin = 2; // Ѫ��������֮��ļ��
	int pixHeight = 5; // Ѫ���߶�
	int titleWidth = 32;
	TextureRegion pix;

	private boolean isSelected;// ��ѡ�еȴ�������
	public Role(Type t, int index) {
		// TODO Auto-generated constructor stub
		type = t;
		this.state_time = 0;
		get_values(type, index);
		set_actor_base(type, index);
		set_listener();
	}

	// �������ͻ����Դ
	private void set_actor_base(Type type, int index) {
		this.type = type;
		int actor_type = type == Type.HERO ? 2 : 5;
		ani_idle = HeroAnimationClass.getAnimationIdle(actor_type, index);
		ani_move = HeroAnimationClass.getAnimationMove(actor_type, index);
		ani_attack_n = HeroAnimationClass
				.getAnimationAttackN(actor_type, index);
		ani_attack_v = HeroAnimationClass
				.getAnimationAttackV(actor_type, index);
		ani_attack_h = HeroAnimationClass
				.getAnimationAttackH(actor_type, index);
		
		state = STATE.idle;
		set_ani_from_state();
	}

	private void show_life_display() {
		pix = null;
		Pixmap pixmap;
		pixmap = new Pixmap(64, 8, Format.RGBA8888); // ����һ��64*8��ͼƬ
		pixmap.setColor(Color.BLACK); // ������ɫΪ��ɫ
		pixmap.drawRectangle(0, 0, titleWidth, pixHeight);
		pixmap.setColor(Color.RED); // ������ɫΪ��ɫ
		pixmap.fillRectangle(0, 1, titleWidth * currentHp / maxHp,
				pixHeight - 2); // ����Ѫ��
		Texture pixmaptex = new Texture(pixmap); // ����ͼƬ
		pix = new TextureRegion(pixmaptex, titleWidth, pixHeight); // �и�ͼƬ
		pixmap.dispose();
	}

	// ȡ�ý�ɫ��ʼ״̬����
	private void get_values(Type type, int index) {
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
			batch.draw(pix, this.getX(), this.getY() + this.margin + 32); // ����
	}

	// ��ɫ����,Ŀǰnpcֻ����ͨ����
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

	// ����������0-3
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

	// ���ݵ��˱�����������ù������� 0-->��ͨ 1-->1 2-->2 ;
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
		set_ani_from_state();
	}

	// ���ݽ�ɫ״̬ȡ�ý�ɫ����
	private void set_ani_from_state(){
		loop_flag = false;
		if (state == STATE.idle) {
			ani_current = ani_idle;
			loop_flag=true;
		}
		if (state == STATE.move) {
			ani_current = ani_move;
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
			state = STATE.idle;
			set_ani_from_state();
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
				state = STATE.move;
				// state = STATE.attack_normal;
				// state = STATE.attack_h;
				// state = STATE.attack_v;
				set_selected(true);
				// hero_isAttacked(2, 10);
				set_ani_from_state();
				return true;
			}
		});
	}
	public Type getType() {
		return type;
	}
}
