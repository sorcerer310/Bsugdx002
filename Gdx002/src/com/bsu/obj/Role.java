package com.bsu.obj;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.bsu.make.SkillFactory;
import com.bsu.make.SkillListBase;
import com.bsu.tools.AttackWeaponBase;
import com.bsu.tools.BsuEvent;
import com.bsu.tools.Configure;
import com.bsu.tools.DefendWeaponBase;
import com.bsu.tools.HeroAnimationClass;
import com.bsu.tools.Configure.FACE;
import com.bsu.tools.Configure.STATE;
import com.bsu.tools.HeroEffectClass;
import com.bsu.tools.RoleHP;
import com.bsu.tools.RoleValue;
import com.sun.tools.internal.xjc.reader.gbind.Sequence;

public class Role extends Actor {
	public static enum Type {
		HERO, ENEMY
	}; // 英雄还是NPC
	private AttackWeaponBase attack_weapon;//人物武器
	private DefendWeaponBase defend_weapon;//人物护甲
	private Array<Skill> skillList;//人物技能树
	
	private BsuEvent bevent = null; // 用来通知一些消息
	public String name = ""; // 记录这个角色的名字
	public int maxHp = 100; // 总血量
	public int currentHp = 30; // 当前血量
	private int attack_value; // 自身攻击力
	private int defend_value;//自身防御力
	
	private float time_state; // 行动状态时间
	public float time_effect; // 技能特效时间
	public STATE state; // 英雄的当前状态
	private Skill cskill; // 英雄当前的攻击技能
	public Array<Skill> array_skill = new Array<Skill>(); // 英雄此关卡携带的技能
	private Type type = null; // ָ指定当前角色是英雄还是 NPC
	private Animation ani_idle; // 站立动画
	private Animation ani_move; // 移动动画
	private Animation ani_disapper;// 角色消失
	private Animation ani_apper;// 角色出现
	private boolean loop_flag;

	private Animation ani_current; // 当前人物动画
	private TextureRegion current_action_frame;// 当前人物动画所对应的TextureRegion
	private Animation ani_effect; // 效果动画
	private TextureRegion current_effect_frame; // 当前效果动画对应的某一帧
	private boolean selected; // 被选中等待操作？
	private boolean controlled;// 此轮是否被操作过
	private Array<Vector2> pass_array = new Array<Vector2>(); // 人物可以移动的格子数组
	private Array<Vector2> attack_array = new Array<Vector2>();//人物可以攻击的格子

	/**
	 * 角色初始化
	 * 
	 * @param t
	 *            表示当前角色的类型
	 * @param n
	 *            该角色的名字
	 */
	public Role(Type t, String n) {
		// TODO Auto-generated constructor stub
		name = n;
		type = t;
		time_state = 0;
		currentHp = 100;
		maxHp = 100;
		attack_value = 5;
		set_actor_base(type);
		cark_box = this.card_region();
		//array_skill.add(SkillFactory.getInstance().getSkillByName("atk"));
		//setCskill(SkillFactory.getInstance().getSkillByName("atk"));
	}

	public Role(RoleValue rv) {
		// TODO Auto-generated constructor stub
		time_state = 0;
		name = rv.name;
		maxHp=rv.roleHp;
		currentHp=maxHp;
		attack_value=rv.attackValue;
		defend_value=rv.defendValue;
		attack_weapon=rv.attackWeapon;
		defend_weapon=rv.defendWeapon;
		this.skillList=rv.skillList;
		cskill=skillList.get(0);
		this.type=rv.type;
		set_actor_base(type);
		cark_box = this.card_region();
	}

	/**
	 * 根据类型获得资源
	 * 
	 * @param type
	 */
	private void set_actor_base(Type type) {
		this.type = type;
		// int actor_type = type == Type.HERO ? 2 : 5;
		/*
		 * ani_idle = HeroAnimationClass.getAnimationIdle(actor_type); ani_move
		 * = HeroAnimationClass.getAnimationMove(actor_type);
		 */
		int actor_type = type == Type.HERO ? 0 : 1;
		ani_idle = HeroAnimationClass.getAnimation(actor_type);
		ani_move = HeroAnimationClass.getAnimation(actor_type);
		ani_disapper = HeroEffectClass.get_effect(99);
		ani_apper = HeroEffectClass.get_effect(98);
		set_ani_from_state(STATE.idle);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		Role_logic();
		if (current_action_frame != null) {
			batch.draw(current_action_frame, getX(), getY(), getOriginX(),
					getOriginY(), 32, 32, getScaleX(), getScaleY(),
					getRotation());
			batch.draw(cark_box, getX(), getY());
		}
		if (current_effect_frame != null) {
			batch.draw(current_effect_frame, getX(), getY());
		}
	}

	public void hero_disapper(BsuEvent be) {
		set_ani_from_state(STATE.disapper);
		bevent = be;
	}

	public void hero_apper(int mx, int my, BsuEvent be) {
		set_ani_from_state(STATE.apper);
		setPosition(mx * Configure.map_box_value, my * Configure.map_box_value);
		bevent = be;
	}

	/**
	 * 角色攻击，目前npc只有普通攻击
	 * 
	 * @param enemy
	 *            攻击动作的角色
	 */
	public void hero_attack(Role enemy, Skill skl, BsuEvent be) {
		if (enemy == null)
			return;
		bevent = be;
		time_effect = 0; // 此处一定要设置time_effect为0，否则动画不会重新开始
		ani_effect = skl.ani_self;
		enemy.hero_isAttacked(skl.ani_object, skl.getVal());
	}

	/**
	 * @param damage_value
	 */
	public void hero_isAttacked(Animation ani, float damage_value) {
		time_effect = 0;
		ani_effect = ani;
	}

	/**
	 * 根据角色状态取得角色动画
	 * 
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
		if (state == STATE.apper) {
			ani_current = ani_apper;
			loop_flag = false;
		}
		if (state == STATE.disapper) {
			ani_current = ani_disapper;
			loop_flag = false;
		}
		time_state = 0;
	}

	/**
	 * 返回角色状态
	 */
	public STATE get_ani_from_state() {
		return state;
	}

	/*
	 * Role 逻辑判断
	 */
	private void Role_logic() {
		time_state += Gdx.graphics.getDeltaTime();
		time_effect += Gdx.graphics.getDeltaTime();
		current_action_frame = ani_current.getKeyFrame(time_state, loop_flag);
		if (ani_current.isAnimationFinished(time_state)) {
			if (ani_current == ani_move) {
				set_ani_from_state(STATE.idle);
			}
			if (bevent != null) {
				set_ani_from_state(STATE.idle);
				bevent.notify(this, this.name);
			}
		}

		if (ani_effect != null) {
			current_effect_frame = ani_effect.getKeyFrame(time_effect, false);
			if (ani_effect.isAnimationFinished(time_effect)) {
				current_effect_frame = null;
				ani_effect = null;
				// 如果event对象不为空，执行函数通知完成
				if (bevent != null) {
					System.out.println(this.name + "skill_effect_completed");
					bevent.notify(this, this.name);
				}
			}
		}
		if (isSelected()) {
			if (state == STATE.idle) {
				MapBox.attack_array.clear();
				for (Vector2 v : getAttack_array()) {
					Vector2 tempV = new Vector2();
					tempV.x = v.x;
					tempV.y = v.y;
					MapBox.attack_array.add(tempV);
				}
			} else {
				MapBox.attack_array.clear();
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
	 * 
	 * @return
	 */
	private Array<Vector2> realrange = new Array<Vector2>();

	/**
	 * 获得英雄当前技能的作用范围
	 * 
	 * @return 返回坐标数组
	 */
	public Array<Vector2> getCurrSkillRange() {
		realrange.clear();
		Array<Vector2> rs = cskill.getRange();
		for (Vector2 v : rs) {
			realrange.add(new Vector2(this.getBoxX() + v.x, v.y
					+ this.getBoxY()));
		}
		return realrange;
	}

	public Skill getCskill() {
		return cskill;
	}

	public void setCskill(Skill cskill) {
		this.cskill = cskill;
		if (type == Type.ENEMY)
			cskill.flip();
	}

	/**
	 * 判断移动路径上是否有自己人阻挡
	 * 
	 * @param rs
	 *            每次调用需要重新检测生成RS... ROLE数组
	 * @return
	 */
	public boolean hasAnatherRole(Array<Role> rs) {
		int num = 0;
		for (Role r : rs) {
			if (this != r) {
				num = r.getType() == Type.HERO ? 1 : -1;
				if ((r.getBoxY() == this.getBoxY())
						&& (r.getBoxX() == this.getBoxX() + num)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 以下卡片所涉及的方法
	 */
	private TextureRegion cark_box;

	private TextureRegion card_region() {
		Pixmap pixmap;
		pixmap = new Pixmap(32, 32, Format.RGBA8888); // 生成一张64*8的图片
		pixmap.setColor(Color.GREEN); // 设置颜色为黑色。
		pixmap.drawRectangle(0, 0, 32, 32);

		Texture pixmaptex = new Texture(pixmap);
		TextureRegion pix_temp = new TextureRegion(pixmaptex, 32, 32);
		pixmap.dispose();
		return pix_temp;
	}

	public boolean isControlled() {
		return controlled;
	}

	public void setControlled(boolean controlled) {
		this.controlled = controlled;
	}

	public Array<Vector2> getPass_array() {
		return pass_array;
	}

	public void setPass_array(Array<Vector2> array) {
		this.pass_array.clear();
		for (Vector2 v : array) {
			Vector2 tempV = new Vector2();
			tempV.x = v.x;
			tempV.y = v.y;
			this.pass_array.add(tempV);
		}
	}

	public Array<Vector2> getAttack_array() {
		attack_array.clear();
		Array<Vector2> vs = this.getCurrSkillRange();
		for (Vector2 v : vs) {
			attack_array.add(v);
		}
		return attack_array;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
