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

<<<<<<< HEAD:Gdx002/src/com/bsu/obj/Role.java


public class Role extends Actor {
	private Texture texture;
=======
public class MyHero extends Actor {

>>>>>>> anmation and effects:Gdx002/src/com/bsu/obj/MyHero.java
	private TextureRegion current_frame;
	private float state_time;
	public STATE state;
<<<<<<< HEAD:Gdx002/src/com/bsu/obj/Role.java
	public static enum Type {HERO,ENEMY};
	private Type type = null;
	
	private int life_max;//�������ֵ
	private int life_current;//��ǰ����
	private int attack_value;//��������
	private boolean isAttacked;//��������
	private int isAttackedValue;//�ܵ����˺�
	private int isAttackedType;//�⵽ʲô���͵Ĺ���
	private Image isAttackedImg;//������Ҫ���ŵ�ͼƬ
	
	public Image heroImg;
	
	private boolean isSelected;//��ѡ�еȴ�������
=======
	private Animation ani_idle;
	private Animation ani_move;
	private Animation ani_attack_n;// ��������
	private Animation ani_attack_v;// ���򹥻�
	private Animation ani_attack_h;// ���򹥻�

	int maxHp = 100; // ��Ѫ��
	int currentHp = 30; // ��ǰѪ��
	private int attack_value;// ��������
	private TextureRegion effect_current_frame;
	private Animation ani_effect;
>>>>>>> anmation and effects:Gdx002/src/com/bsu/obj/MyHero.java

	int margin = 2; // Ѫ��������֮��ļ��
	int pixHeight = 5; // Ѫ���߶�
	int titleWidth = 32;
	TextureRegion pix;

	private boolean isSelected;// ��ѡ�еȴ�������

	public Role(Type t, int index) {
		// TODO Auto-generated constructor stub
		type = t;
		this.state_time = 0;
		state = STATE.idle;
		get_values(type, index);
		set_actor_base(type, index);
		set_listener();
	}
<<<<<<< HEAD:Gdx002/src/com/bsu/obj/Role.java
	// type 0-->1 index 0---->4 2�����ͣ���ɫ�� ���˷� ÿ��4��
		private void set_actor(Type type, int index) {
			int actor_type = type == Type.HERO ? 2 : 5;
			int actor_index = index * 3;

			for (TextureRegion[] region1 : miror) {
				for (TextureRegion region2 : region1) {
					region2.flip(true, false);
				}
			}
			// move
			TextureRegion[] regionR = new TextureRegion[3];
			regionR[0] = spilt[actor_type][actor_index];
			regionR[1] = spilt[actor_type][actor_index + 1];
			regionR[2] = spilt[actor_type][actor_index + 2];
			ani_move = new Animation(0.1f, regionR);

			// վ��
			TextureRegion[] region_idle = new TextureRegion[1];
			region_idle[0] = spilt[actor_type][actor_index + 1];
			ani_idle = new Animation(0.1f, region_idle);
	
			get_values(type,index);
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
					state=STATE.move;
					return true;
				}
			});
		}
		//ȡ�ý�ɫ��ʼ״̬����
		private void get_values(Type type,int index){
			life_max=100;
			life_current=life_max;
			attack_value=5;
			isAttacked=false;
			isAttackedImg=null;
		}
		
		@Override
		public void draw(SpriteBatch batch, float parentAlpha) {
			// TODO Auto-generated method stub
			state_time += Gdx.graphics.getDeltaTime();
			this.check();
			batch.draw(current_frame,getX(),getY());
			this.setSize(32, 32);
=======

	// �������ͻ����Դ
	private void set_actor_base(int type, int index) {
		int actor_type = type == 0 ? 2 : 5;
		ani_idle=HeroAnimationClass.getAnimationIdle(actor_type, index);
		ani_move=HeroAnimationClass.getAnimationMove(actor_type, index);
		ani_attack_n=HeroAnimationClass.getAnimationAttackN(actor_type, index);
		ani_attack_v=HeroAnimationClass.getAnimationAttackV(actor_type, index);
		ani_attack_h=HeroAnimationClass.getAnimationAttackH(actor_type, index);
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
	private void get_values(int type, int index) {
		currentHp = 100;
		maxHp = 100;
		attack_value = 5;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		state_time += Gdx.graphics.getDeltaTime();
		this.setSize(32, 32);
		this.check();
		show_life_display();
		if (current_frame != null)
			batch.draw(current_frame, getX(), getY());
		if (effect_current_frame != null) {
			if (ani_effect.isAnimationFinished(state_time)) {
				effect_current_frame = null;
				set_selected(false);
			} else {
				batch.draw(effect_current_frame, getX(), getY());
			}
		}

		if (isSelected)
			batch.draw(pix, this.getX(), this.getY() + this.margin + 32); // ����

	}

	// ����������0-3
	public void hero_isAttacked(int attack_type, int damage_value) {
		get_effect_state(attack_type);
		currentHp = currentHp - damage_value >= 0 ? currentHp - damage_value
				: 0;
		if (currentHp <= 0) {
			this.getParent().removeActor(this);
>>>>>>> anmation and effects:Gdx002/src/com/bsu/obj/MyHero.java
		}
	}

	public void set_selected(boolean b) {
		isSelected = b;
	}

	public boolean get_selected() {
		return isSelected;
	}

	// **********effect ani
	private void get_effect_state(int index) {
		switch (index) {
		case 0:
			ani_effect = HeroEffectClass.ani_effect_0;
			break;
		case 1:
			ani_effect = HeroEffectClass.ani_effect_1;
			break;
		case 2:
			ani_effect = HeroEffectClass.ani_effect_2;
			break;
		case 3:
			ani_effect = HeroEffectClass.ani_effect_3;
			break;
		}
		state_time = 0;
		effect_current_frame = ani_effect.getKeyFrame(state_time, false);
	}

	// ���ݽ�ɫ״̬ȡ�ý�ɫ����
	private void check() {
		if (state == STATE.idle) {
			current_frame = ani_idle.getKeyFrame(state_time, true);
		}
		if (state == STATE.move) {
			current_frame = ani_move.getKeyFrame(state_time, true);
		}
		if (state == STATE.attack_normal) {
			current_frame = ani_attack_n.getKeyFrame(state_time, true);
		}
		if (state == STATE.attack_h) {
			current_frame = ani_attack_h.getKeyFrame(state_time, true);
		}
		if (state == STATE.attack_v) {
			current_frame = ani_attack_v.getKeyFrame(state_time, true);
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
<<<<<<< HEAD:Gdx002/src/com/bsu/obj/Role.java
		}
		public Type getType() {
			return type;
		}
=======

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				state = STATE.move;
				// state = STATE.attack_normal;
				// state = STATE.attack_h;
				// state = STATE.attack_v;
				set_selected(true);
				hero_isAttacked(2, 10);
				return true;
			}
		});
	}
>>>>>>> anmation and effects:Gdx002/src/com/bsu/obj/MyHero.java
}
