package com.bsu.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bsu.tools.Configure;
import com.bsu.tools.Configure.STATE;



public class MyHero extends Actor {
	private Texture texture;
	private TextureRegion current_frame;
	public TextureRegion[][] spilt;
	private TextureRegion[][] miror;
	private Animation ani_idle;
	private Animation ani_move;
	private float state_time;
	public STATE state;
	
	
	private int life_max;//�������ֵ
	private int life_current;//��ǰ����
	private int attack_value;//��������
	private boolean isAttacked;//��������
	private int isAttackedValue;//�ܵ����˺�
	private int isAttackedType;//�⵽ʲô���͵Ĺ���
	private Image isAttackedImg;//������Ҫ���ŵ�ͼƬ
	
	public Image heroImg;
	
	private boolean isSelected;//��ѡ�еȴ�������


	public MyHero(int type, int index) {
		// TODO Auto-generated constructor stub
		this.state_time = 0;
		state = STATE.idle;
		texture = new Texture(Gdx.files.internal("data/hero/hero.png"));
		spilt = TextureRegion.split(texture, 32, 32);
		miror = TextureRegion.split(texture, 32, 32);
		set_actor(type, index);
	}
	// type 0-->1 index 0---->4 2�����ͣ���ɫ�� ���˷� ÿ��4��
		private void set_actor(int type, int index) {
			int actor_type = type == 0 ? 2 : 5;
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

			// ����idle
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
			private void get_values(int type,int index){
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
		}
		public void set_selected(boolean b){
			isSelected=b;
		}
		public boolean get_selected(){
			return isSelected;
		}

		// ����״̬ȡ�ö���
		private void check() {
			if (state == STATE.idle) {
				current_frame = ani_idle.getKeyFrame(state_time, true);
			} else if (state == STATE.move) {
				current_frame = ani_move.getKeyFrame(state_time, true);
			}
		}
}
