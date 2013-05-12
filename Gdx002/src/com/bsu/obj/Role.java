package com.bsu.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.bsu.make.SkillFactory;
import com.bsu.tools.Configure;
import com.bsu.tools.Configure.STATE;

public class Role extends Actor {
	private String name = ""; 				// 记录这个角色的名字
	private float time_state; 				// 状态时间
	public float time_effect; 				// 特效时间
	public STATE state; 					// 英雄的当前状态
	public Skill cskill;					//英雄当前的攻击技能
	public Array<Skill> array_skill = new Array<Skill>();	//英雄拥有的技能
	
	public static enum Type {
		HERO, ENEMY
	}; // 英雄还是NPC

	private Type type = null; // ָ指定当前角色是英雄还是 NPC

	private Animation ani_current; // 当前动画
	private TextureRegion current_frame;
	private Animation ani_idle; // 站立动画
	private Animation ani_move; // 移动动画
	private boolean loop_flag;

	private int maxHp = 100; 								// 总血量
	private int currentHp = 30; 							// 当前血量
	private int attack_value; 								// 自身攻击力
	private int attacked_nums; 								// 目前被连击数量ֵ
	private int attack_type; 								// 当前攻击类型
	private TextureRegion current_frame_effect;
	private Animation ani_effect;

	int margin = 2; 								// 血条和人物之间的间隔
	int pixHeight = 5; 								// 血条高度
	int titleWidth = 32;
	TextureRegion pix;

	private boolean isSelected; 					// 被选中等待操作？

	/**
	 * 角色初始化
	 * @param t		表示当前角色的类型
	 * @param n		该角色的名字
	 */
	public Role(Type t, String n) {
		// TODO Auto-generated constructor stub
		name = n;
		type = t;
		time_state = 0;
		array_skill.add(SkillFactory.getInstance().getSkillByName("atk"));
		cskill = array_skill.get(0);
		get_values(type);
		set_actor_base(type);
	}

	/**
	 * 根据类型获得资源
	 * @param type
	 */
	private void set_actor_base(Type type) {
		this.type = type;
		int actor_type = type == Type.HERO ? 2 : 5;
		ani_idle = HeroAnimationClass.getAnimationIdle(actor_type);
		ani_move = HeroAnimationClass.getAnimationMove(actor_type);
		set_ani_from_state(STATE.idle);
	}

	/**
	 * 设置人物生命血条
	 */
	private void draw_life_display() {
		pix = null;
		Pixmap pixmap;
		pixmap = new Pixmap(64, 8, Format.RGBA8888); // 生成一张64*8的图片
		pixmap.setColor(Color.BLACK); // 设置颜色为黑色。
		pixmap.drawRectangle(0, 0, titleWidth, pixHeight);
		pixmap.setColor(Color.RED); // 设置为黑色
		pixmap.fillRectangle(0, 1, titleWidth * currentHp / maxHp,
				pixHeight - 2);
		Texture pixmaptex = new Texture(pixmap);
		pix = new TextureRegion(pixmaptex, titleWidth, pixHeight);
		pixmap.dispose();
	}

	/**
	 * 取得角色初始状态属性
	 * 
	 * @param type
	 *            角色类型
	 */
	private void get_values(Type type) {
		currentHp = 100;
		maxHp = 100;
		attack_value = 5;
		attacked_nums = 0;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		time_state += Gdx.graphics.getDeltaTime();
		time_effect += Gdx.graphics.getDeltaTime();
		this.setSize(32, 32);
		draw_life_display();
		check_frame_finish();

		if (current_frame != null) {
			batch.draw(current_frame, getX(), getY());
		}
	
		if (current_frame_effect != null) {
			batch.draw(current_frame_effect, getX(), getY());
		}
		
		if (isSelected)
			batch.draw(pix, this.getX(), this.getY() + this.margin + 32); // 画血条
	}

	/**
	 * 角色攻击，目前npc只有普通攻击
	 * 
	 * @param enemy
	 *            攻击动作的角色
	 */
	public void hero_attack_other(Role enemy,Skill skl) {
		if (enemy == null) 
			return;
		this.ani_effect = skl.ani_self;
		time_effect=0;								//此处一定要设置time_effect为0，否则动画不会重新开始
		enemy.ani_effect = skl.ani_object;
		enemy.time_effect = 0;
		enemy.hero_isAttacked(skl.getVal());
	}

	/**
	 * 被攻击类型0-3
	 * Role被技能作用后数值变化
	 * @param damage_value
	 */
	public void hero_isAttacked(float damage_value) {
		currentHp = (int) (currentHp - damage_value >= 0 ? currentHp - damage_value
				: 0);
		if (currentHp <= 0) {
			//this.getParent().removeActor(this);
		}
		attacked_nums++;
	}

	public void set_selected(boolean b) {
		isSelected = b;
	}

	public boolean get_selected() {
		return isSelected;
	}

	/**
	 * 根据角色状态取得角色动画
	 * @param s
	 *            状态值
	 */
	public void set_ani_from_state(STATE s) {
		loop_flag = false;
		state = s;
		if (state == STATE.idle) {
			ani_current = ani_idle;
			loop_flag = true;
		}
		if (state == STATE.move) {
			ani_current = ani_move;
			loop_flag = true;
		}
		time_state = 0;
	}

	/**
	 * 返回角色状态
	 */
	public STATE get_ani_from_state() {
		return state;
	}

	private void check_frame_finish() {
		
			current_frame = ani_current.getKeyFrame(time_state, loop_flag);
			if (ani_current.isAnimationFinished(time_state)) {
				set_ani_from_state(STATE.idle);
				set_selected(false);
			}
			
		if (ani_effect != null) {
			current_frame_effect = ani_effect.getKeyFrame(time_effect, false);
			if (ani_effect.isAnimationFinished(time_effect)) {
				current_frame_effect = null;
				ani_effect = null;
				set_selected(false);
			}
		}
	}
	
	public Type getType() {
		return type;
	}
	
	/**
	 * 获得以32*32方格为单位的x坐标
	 * 
	 * @return
	 */
	public int getBoxX() {
		return (int) ((this.getX() + Configure.extra_value) / Configure.map_box_value);
	}

	/**
	 * 获得以32*32方格为单位的y坐标
	 * 
	 * @return
	 */
	public int getBoxY() {
		return (int) ((this.getY() + Configure.extra_value) / Configure.map_box_value);
	}


	/**
	 * 根据角色的位置获得地图上实际的技能范围
	 * @return	
	 */
	private Array<Vector2> realrange = new Array<Vector2>();
	public Array<Vector2> getCurrSkillRange(){
		realrange.clear();
		Array<Vector2> rs = cskill.getRange();
		for(Vector2 v:rs)
			realrange.add(new Vector2(v.x+this.getBoxX(),v.y+this.getBoxY()));
		return realrange;
	}
}
