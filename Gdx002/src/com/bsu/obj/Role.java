package com.bsu.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.skilltree.ContinuedSkillState;
import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.BsuEvent;
import com.bsu.tools.Configure;
import com.bsu.tools.Configure.CLASSES;
import com.bsu.tools.Configure.QUALITY;
import com.bsu.tools.GameAnimationClass;
import com.bsu.tools.Configure.FACE;
import com.bsu.tools.Configure.STATE;
import com.bsu.tools.U;

public class Role extends Actor {
	public static enum Type {
		HERO, ENEMY
	}; // 英雄还是NPC
	public RolePhoto photo;
	public Equip weapon;// 人物武器
	public Equip armor;// 人物护甲

	private BsuEvent bevent = null; // 用来通知一些消息
	public String name = ""; // 记录这个角色的名字
	public QUALITY quality;// 品质
	public Type type = null; // ָ指定当前角色是英雄还是 NPC
	public CLASSES classes = null;// 指定当前人物的职业
	public int level;// 等级
	public TextureRegion roleTexture;
	public int maxHp = 100; // 总血量
	public int extMaxHp = 0; // 额外的血量上限
	public int currentHp = 30; // 当前血量
	private int attack; // 自身攻击力
	public int extAttack = 0; // 额外的攻击力
	private int defend;// 自身防御力
	public int extDefend = 0; // 额外的防御力
	public int exp = 0; // 经验值
	public int expUp = 0;
	public Array<ContinuedSkillState> csstate = new Array<ContinuedSkillState>(); // 当前在人物身上的各种持续效果
	public boolean isRoundMove = true; // 本回合是否移动

	private float time_state; // 行动状态时间
	private float time_effect; // 技能特效时间
	private float px, py;// 动画偏移量
	public STATE state; // 英雄的当前状态
	public Skill cskill; // 英雄当前的攻击技能
	public Array<Skill> skill_tree = new Array<Skill>(); // 英雄的技能树
	public Array<Skill> skill_array = new Array<Skill>(); // 英雄此关卡携带的技能

	private Animation ani_idle; // 站立动画
	private Animation ani_move; // 移动动画
	private Animation ani_hited;//挨打动画
	private Animation ani_stoped;//被阻挡动画
	private Animation ani_disapper;// 角色消失
	private Animation ani_apper;// 角色出现
	private boolean loop_flag;

	private Animation ani_current; // 当前人物动画
	private TextureRegion current_action_frame;// 当前人物动画所对应的TextureRegion
	private Animation attack_effect; // 攻击效果动画
	private TextureRegion current_attack_frame; // 当前攻击效果动画对应的某一帧
	private Animation beAttack_effect; // 被攻击效果动画
	private TextureRegion current_beattack_frame; // 当前被攻击效果动画对应的某一帧
	private boolean selected; // 被选中等待操作？
	private boolean controlled;// 此轮是否被操作过
	private Array<Vector2> pass_array = new Array<Vector2>(); // 人物可以移动的格子数组
	private Array<Vector2> attack_array = new Array<Vector2>();// 人物可以攻击的格子

	/**
	 * 角色初始化
	 * 
	 * @param t
	 *            表示当前角色的类型
	 * @param n
	 *            该角色的名字
	 */
	public Role(Type t, QUALITY q, CLASSES c, String n, int mhp, int av,
			int dv, Equip w, Equip a, Array<Skill> as, TextureRegion tr) {
		name = n; // 名称
		type = t; // 类型，英雄还是敌人
		quality = q;
		classes = c;
		time_state = 0;
		maxHp = mhp;
		currentHp = mhp;
		attack = av;
		defend = dv;
		weapon = w;
		armor = a;
		skill_tree = as;
		roleTexture = tr;
		cskill = skill_tree.get(0);
		skill_array.add(skill_tree.get(0));
		skill_array.add(skill_tree.get(1));
		exp = baseExp();
		set_actor_base(type);
		levelUp();
	}

	/**
	 * 根据类型获得资源
	 * 
	 * @param type
	 */
	private void set_actor_base(Type type) {
		this.type = type;
		if (type == Type.HERO)
			face = FACE.right;
		else {
			face = FACE.left;
			roleTexture.flip(true, false);
		}
		ani_idle = GameAnimationClass.getInstance().getRoleAnimation(
				roleTexture);
		ani_move = GameAnimationClass.getInstance().getRoleAnimation(
				roleTexture);
		
		ani_disapper = GameAnimationClass.getInstance().getEffectDisapper();
		ani_apper = GameAnimationClass.getInstance().getEffectApper();
		set_ani_from_state(STATE.idle);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub

		if (current_action_frame != null) {
			batch.draw(current_action_frame, getX(), getY(), getOriginX(),
					getOriginY(), 32, 32, getScaleX(), getScaleY(),
					getRotation());
		}
		if (current_beattack_frame != null) {
			batch.draw(current_beattack_frame, getX() + px, getY() + py);
		}
		Role_logic();
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
	 * @param enemy
	 *            攻击动作的角色
	 */
	public void ani_role_attack(Role enemy, Skill skl, BsuEvent be) {
		if (enemy == null)
			return;
		bevent = be;
		time_effect = 0; // 此处一定要设置time_effect为0，否则动画不会重新开始
		// 如果动画为空直接通知动画播放结束事件
		if (skl.ani_self == null) {
			if (bevent != null)
				bevent.notify(this, this.name);
		} else {
			attack_effect = skl.ani_self;
			current_attack_frame = attack_effect.getKeyFrame(time_effect, false);
			AttackEffect.getInstance().startEffect(current_attack_frame, this,skl.offset_ani_self);
		}
		enemy.ani_role_isAttacked(skl.ani_object, skl.offset_ani_object,be);
	}

	/**
	 * 英雄被攻击播放动画
	 * 
	 * @param ani
	 *            要播放的动画
	 */
	private void ani_role_isAttacked(Animation ani, Vector2 v,BsuEvent be) {
		time_effect = 0;
		if (ani != null) {
			bevent = be;
			beAttack_effect = ani;
			if (v != null) {
				px = v.x;
				py = v.y;
				System.out.println(px+"@@"+py);
			}
		}
	}

	/**
	 * 播放人物持续动画
	 * 
	 * @param ani
	 *            要播放的动画参数
	 */
	public void ani_role_continue(ContinuedSkillState css) {
		/**
		 * 从css中取持续效果动画偏移量
		 */
		time_effect = 0;
		if (css.ani != null) {
			beAttack_effect = css.ani;
			if (css.offset != null) {
				px = css.offset.x;
				py = css.offset.y;
			}
		}
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
				if (ani_current == ani_apper) {
					set_ani_from_state(STATE.idle);
				}
				bevent.notify(this, this.name);
			}
		}
		if (attack_effect != null) {
			current_attack_frame = attack_effect.getKeyFrame(time_effect, false);
			AttackEffect.getInstance().setFrame(current_attack_frame);
			if (attack_effect.isAnimationFinished(time_effect)) {
				current_attack_frame = null;
				attack_effect = null;
				AttackEffect.getInstance().endEffect();
				// 如果event对象不为空，执行函数通知完成
				if (bevent != null) {
					System.out.println(this.name
							+ "attact_skill_effect_completed");
					//bevent.notify(this, this.name);
					bevent.notify(this,"ani_attack_finished");
				}
			}
		}//else{if(bevent!=null)bevent.notify(this,"ani_attack_finished");}
		
		if (beAttack_effect != null) {
			current_beattack_frame = beAttack_effect.getKeyFrame(time_effect,false);
			if (beAttack_effect.isAnimationFinished(time_effect)) {
				current_beattack_frame = null;
				beAttack_effect = null;
				px = 0;
				py = 0;
				// 如果event对象不为空，执行函数通知完成
				if (bevent != null) {
					System.out.println(this.name
							+ "beattacked_effect_completed");
//					bevent.notify(this, this.name);
					bevent.notify(this,"ani_beattacked_finished");
				}
			}
		}//else{if(bevent!=null)bevent.notify(this,"ani_beattacked_finished");}


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
	public FACE face;

	/**
	 * 获得英雄当前技能的作用范围
	 * 
	 * @return 返回坐标数组
	 */
	public Array<Vector2> getCurrSkillRange() {
		realrange.clear();
		Array<Vector2> rs = null;
		if (this.face == Configure.FACE.right)
			rs = cskill.getRange();
		else if (this.face == Configure.FACE.left)
			rs = cskill.flipRange();
		for (Vector2 v : rs) {
			realrange.add(new Vector2(this.getBoxX() + v.x, v.y
					+ this.getBoxY()));
		}
		return realrange;
	}

	public Skill getCskill() {
		return cskill;
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
				num = face == FACE.right ? 1 : -1;
				if ((r.getBoxY() == this.getBoxY())
						&& (r.getBoxX() == this.getBoxX() + num)) {
					return true;
				}
			}
		}
		return false;
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

	/**
	 * 返回角色基本exp
	 * 
	 * @return
	 */
	public int baseExp() {
		int exp = 0;
		if (quality == QUALITY.green)
			exp = Configure.baseExpGreen;
		if (quality == QUALITY.blue)
			exp = Configure.baseExpBlue;
		if (quality == QUALITY.purple)
			exp = Configure.baseExpPurple;
		if (quality == QUALITY.orange)
			exp = Configure.baseExpOrange;
		return exp;
	}

	/**
	 * 返回英雄升级后数据
	 */
	public void levelUp() {
		level++;
		exp -= expUp;
		maxHp = U.hpLevel(this);
		attack = U.attackLevel(this);
		defend = U.defendLevel(this);
		expUp = U.expLevel(this);
	}

	/**
	 * 返回英雄的职业数据
	 * 
	 * @return
	 */

	public CLASSES getClasses() {
		return classes;
	}

	/**
	 * 返回英雄的品质
	 * 
	 * @return
	 */
	public QUALITY getQuality() {
		return quality;
	}

	/**
	 * 返回人物总攻击力
	 * 
	 * @return
	 */
	public int getAttack() {
		return attack + extAttack;
	}

	/**
	 * 返回人物总防御力
	 * 
	 * @return
	 */
	public int getDefend() {
		return defend + extDefend;
	}

	/**
	 * 清除所有额外值
	 */
	public void clearExtValue() {
		extMaxHp = 0;
		extAttack = 0;
		extDefend = 0;
		isRoundMove = true;
	}

	/**
	 * 获得人物总hp上限
	 * 
	 * @return
	 */
	public int getMaxHp() {
		return maxHp + extMaxHp;
	}

}
