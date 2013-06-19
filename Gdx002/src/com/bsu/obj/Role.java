package com.bsu.obj;

import java.util.Observable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.utils.Array;
import com.bsu.effect.AttackEffect;
import com.bsu.effect.RoleIcon;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.skilltree.ContinuedSkillState;
import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.BsuEvent;
import com.bsu.tools.GC;
import com.bsu.tools.GC.CLASSES;
import com.bsu.tools.GC.QUALITY;
import com.bsu.tools.GAC;
import com.bsu.tools.GC.FACE;
import com.bsu.tools.GC.STATE;
import com.bsu.tools.U;

public class Role extends Actor {
	private RoleObservable ro = new RoleObservable();

	public static enum Type {
		HERO, ENEMY
	}; // 英雄还是NPC

	public RoleIcon roleIcon;
	public Equip weapon;// 人物武器
	public Equip armor;// 人物护甲

	public boolean isDead = false; // 角色死亡标识
	private BsuEvent bevent = null; // 用来通知一些消息
	public String name = ""; // 记录这个角色的名字
	public QUALITY quality;// 品质
	public Type type = null; // ָ指定当前角色是英雄还是 NPC
	public CLASSES classes = null;// 指定当前人物的职业
	public int level;// 等级
	public TextureRegion roleTexture;
	public int maxHp = 100; // 总血量
	public int extMaxHp = 0; // 额外的血量上限
	private int currentHp = 30; // 当前血量
	private int attack; // 自身攻击力
	public int extAttack = 0; // 额外的攻击力
	private int defend;// 自身防御力
	public int extDefend = 0; // 额外的防御力
	public int exp = 0; // 经验值
	public int expUp = 0;
	public Array<ContinuedSkillState> csstate = new Array<ContinuedSkillState>(); // 当前在人物身上的各种持续效果
	public boolean isRoundMove = true; // 本回合是否移动
	public boolean locked;// 是否被锁住
	private float time_state; // 行动状态时间
	private float time_effect; // 技能特效时间
	private float px, py;// 动画偏移量
	public STATE state; // 英雄的当前状态
	public Skill cskill; // 英雄当前的攻击技能
	public Array<Skill> skill_tree = new Array<Skill>(); // 英雄的技能树
	public Array<Skill> skill_array = new Array<Skill>(); // 英雄此关卡携带的技能

	private Animation ani_idle; // 站立动画
	private Animation ani_move; // 移动动画
	private Animation ani_disapper;// 角色消失
	private Animation ani_apper;// 角色出现
	private boolean loop_flag;

	private Animation ani_current; // 当前人物动画
	private TextureRegion current_action_frame;// 当前人物动画所对应的TextureRegion
	private Animation attack_effect; // 攻击效果动画
	private TextureRegion current_attack_frame; // 当前攻击效果动画对应的某一帧
	private Animation beAttack_effect; // 被攻击效果动画
	private TextureRegion current_beattack_frame; // 当前被攻击效果动画对应的某一帧
	private TextureRegion hp_back, hp;
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
		roleTexture = new TextureRegion(tr);
		cskill = skill_tree.get(0);
		skill_array.add(skill_tree.get(0));
		skill_array.add(skill_tree.get(0));
		exp = baseExp();
		isDead = false;
		this.setVisible(false);
		set_actor_base(type);
		levelUp();
	}

	public void gsstartinit() {
		setSelected(false);
		setControlled(false);
		setCurrentHp(maxHp);
		clearExtValue();
		getPass_array().clear();
		getAttack_array().clear();
		clearActions();
		cskill = skill_array.get(0);
		isDead = false;
	}

	/**
	 * 根据类型获得资源
	 * 
	 * @param type
	 */
	private void set_actor_base(Type type) {
		this.type = type;
		if (type == Type.HERO) {
			face = FACE.right;
		} else {
			face = FACE.left;
			roleTexture.flip(true, false);
		}
		ani_idle = GAC.getInstance().getRoleAnimation(roleTexture);
		ani_move = GAC.getInstance().getRoleAnimation(roleTexture);
		ani_disapper = GAC.getInstance().getEffectDisapper();
		ani_apper = GAC.getInstance().getEffectApper();
		hp_back = WidgetFactory.getInstance().getTextureFill(GC.map_box_value,
				2, Color.BLACK,1);
		hp = WidgetFactory.getInstance().getTextureFill(GC.map_box_value, 2,
				Color.RED,1);
		set_ani_from_state(STATE.idle);	
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		if (current_action_frame != null) {
			batch.draw(current_action_frame, getX(), getY(), getOriginX(),
					getOriginY(), GC.map_box_value, GC.map_box_value,
					getScaleX(), getScaleY(), getRotation());
		}
		if (current_beattack_frame != null) {
			batch.draw(current_beattack_frame, getX() + px, getY() + py);
		}
		if (currentHp > 0) {
			batch.draw(hp_back, getX(), getY() + GC.map_box_value - 2,
					getOriginX(), getOriginY(), GC.map_box_value, 2,
					getScaleX(), getScaleY(), getRotation());
			batch.draw(hp, getX(), getY() + GC.map_box_value - 2, getOriginX(),
					getOriginY(), GC.map_box_value * currentHp / maxHp, 2,
					getScaleX(), getScaleY(), getRotation());
		}
		Role_logic();
	}

	public void hero_disapper(BsuEvent be) {
		set_ani_from_state(STATE.disapper);
		bevent = be;
	}

	public void hero_apper(int mx, int my, BsuEvent be) {
		set_ani_from_state(STATE.apper);
		setPosition(mx * GC.map_box_value, my * GC.map_box_value);
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
			current_attack_frame = attack_effect
					.getKeyFrame(time_effect, false);
			AttackEffect.getInstance().startEffect(current_attack_frame, this,
					skl.offset_ani_self);
		}
		enemy.ani_role_isAttacked(skl.ani_object, skl.offset_ani_object, be);
	}

	/**
	 * 技能攻击群体敌人。用来处理群体技能。
	 * 
	 * @param enemys
	 *            攻击范围内的所有敌人
	 * @param skl
	 *            释放的技能
	 * @param be
	 *            事件对象
	 */
	public void ani_role_attack(Array<Role> enemys, Skill skl, BsuEvent be) {
		if (enemys.size <= 0)
			return;
		bevent = be;
		time_effect = 0;
		// 自身技能效果
		if (skl.ani_self == null) {
			if (bevent != null)
				bevent.notify(this, this.name);
		} else {
			attack_effect = skl.ani_self;
			current_attack_frame = attack_effect
					.getKeyFrame(time_effect, false);
			AttackEffect.getInstance().startEffect(current_attack_frame, this,
					skl.offset_ani_self);
		}
		// 目标动画效果
		for (Role e : enemys)
			e.ani_role_isAttacked(skl.ani_object, skl.offset_ani_object, be);
		// 位移效果
		if (skl.type == Skill.Type.p_assault) {
			Commander.getInstance().assaultCommand(this, be);
		} else {
			if (be != null)
				be.notify(this, "assaultAcion_finished");
		}
	}

	/**
	 * 英雄被攻击播放动画
	 * 
	 * @param ani
	 *            要播放的动画
	 */
	private void ani_role_isAttacked(Animation ani, Vector2 v, BsuEvent be) {
		time_effect = 0;
		if (ani != null) {
			bevent = be;
			beAttack_effect = ani;

			if (v != null) {
				if (type == Type.HERO) {
					px = 32 - v.x - ani.getKeyFrame(0).getRegionWidth();
				} else {
					px = v.x;
				}
				py = v.y;
			} else {
				if (type == Type.HERO) {
					px = 32 - ani.getKeyFrame(0).getRegionWidth();
				} else {
					px = 0;
				}
				py = 0;
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
			current_attack_frame = attack_effect
					.getKeyFrame(time_effect, false);
			AttackEffect.getInstance().setFrame(current_attack_frame);
			if (attack_effect.isAnimationFinished(time_effect)) {
				current_attack_frame = null;
				attack_effect = null;
				AttackEffect.getInstance().endEffect();
				// 如果event对象不为空，执行函数通知完成
				if (bevent != null) {
					System.out.println(this.name
							+ "attact_skill_effect_completed");
					bevent.notify(this, "ani_attack_finished");
				}
			}
		}
		if (beAttack_effect != null) {
			current_beattack_frame = new TextureRegion(
					beAttack_effect.getKeyFrame(time_effect, false));
			if (type == Type.HERO) {
				current_beattack_frame.flip(true, false);
			}
			if (beAttack_effect.isAnimationFinished(time_effect)) {
				current_beattack_frame = null;
				beAttack_effect = null;
				px = 0;
				py = 0;
				// 如果event对象不为空，执行函数通知完成
				if (bevent != null) {
					System.out.println(this.name
							+ "beattacked_effect_completed");
					bevent.notify(this, "ani_beattacked_finished");
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
		return (int) ((this.getX() + GC.extra_value) / GC.map_box_value);
	}

	/**
	 * 获得以32*32方格为单位的y坐标
	 * 
	 * @return
	 */
	public int getBoxY() {
		return (int) ((this.getY() + GC.extra_value) / GC.map_box_value);
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
		if (this.face == GC.FACE.right)
			rs = cskill.getRange();
		else if (this.face == GC.FACE.left)
			rs = cskill.flipRange();
		for (Vector2 v : rs) {
			realrange.add(new Vector2(this.getBoxX() + v.x, v.y
					+ this.getBoxY()));
		}
		return realrange;
	}

	/**
	 * 获得当前技能
	 * 
	 * @return
	 */
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
				//如果这个单位不是自己，并且在地图上存在，且没有死亡，返回true
				if ((r.getBoxY() == this.getBoxY())
						&& (r.getBoxX() == this.getBoxX() + num) && r.isVisible() && !r.isDead) {
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
			exp = GC.baseExpGreen;
		if (quality == QUALITY.blue)
			exp = GC.baseExpBlue;
		if (quality == QUALITY.purple)
			exp = GC.baseExpPurple;
		if (quality == QUALITY.orange)
			exp = GC.baseExpOrange;
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
	 * 移动函数，指定当前的role移动到x y 位置，移动完成后通过BsuEvent通知调用者
	 * 
	 * @param x
	 *            要移动位置的x坐标
	 * @param y
	 *            要移动位置的y坐标
	 * @param be
	 *            事件对象
	 */
	public void moveAction(int x, int y, final BsuEvent be) {
		float dur = GC.duration_ani / 2;
		if (get_ani_from_state() != STATE.idle)
			return;
		set_ani_from_state(STATE.move);
		if (face == FACE.right) {
			this.setOrigin(0, 0);
						addAction(sequence(parallel(
								sequence(rotateBy(15, dur), rotateBy(-15, dur),
										rotateBy(-10, dur), rotateBy(10, dur),
										rotateBy(15, dur), rotateBy(-15, dur),
										rotateBy(-10, dur), rotateBy(10, dur)
								// rotateBy(15, dur), rotateBy(-15, dur),rotateBy(-10, dur),
								// rotateBy(10, dur)
								), moveBy(x, y, GC.duration_move_box)), run(new Runnable() {
									@Override
									public void run() {
										if (be != null)
											be.notify(this, name);
										set_ani_from_state(STATE.idle);
									}
								})));
//			addAction(parallel(
//					sequence(rotateBy(15, dur), rotateBy(-15, dur),
//							rotateBy(-10, dur), rotateBy(10, dur),
//							rotateBy(15, dur), rotateBy(-15, dur),
//							rotateBy(-10, dur), rotateBy(10, dur)
//					), moveBy(x, y, GC.duration_move_box), run(new Runnable() {
//						@Override
//						public void run() {
//							if (be != null)
//								be.notify(this, name);
//							set_ani_from_state(STATE.idle);
//						}
//					})));
		} else if (face == FACE.left) {
			this.setOrigin(32, 0);
						addAction(sequence(parallel(
								sequence(rotateBy(-15, dur), rotateBy(15, dur),
										rotateBy(10, dur), rotateBy(-10, dur),
										rotateBy(-15, dur), rotateBy(15, dur),
										rotateBy(10, dur), rotateBy(-10, dur)
								// rotateBy(-15, dur), rotateBy(15, dur),rotateBy(10, dur),
								// rotateBy(-10, dur)
								), moveBy(x, y, GC.duration_move_box)), run(new Runnable() {
									@Override
									public void run() {
										if (be != null){
											be.notify(this, name);
										}
										set_ani_from_state(STATE.idle);
									}
								})));
//			addAction(parallel(
//					sequence(rotateBy(-15, dur), rotateBy(15, dur),
//							rotateBy(10, dur), rotateBy(-10, dur),
//							rotateBy(-15, dur), rotateBy(15, dur),
//							rotateBy(10, dur), rotateBy(-10, dur)
//					), moveBy(x, y, GC.duration_move_box), run(new Runnable() {
//						@Override
//						public void run() {
//							if (be != null){
//								be.notify(this, name);
//							}
//							set_ani_from_state(STATE.idle);
//						}
//					})));
		}
	}

	/**
	 * 移动过程中被阻挡执行此动作
	 */
	public void stopedAction() {
		float dur = GC.duration_ani / 1.6f;
		if (face == FACE.right) {
			addAction(sequence(moveBy(16, 0, dur), moveBy(-16, 0, dur),
					moveBy(12, 0, dur), moveBy(-12, 0, dur), moveBy(8, 0, dur),
					moveBy(-8, 0, dur), moveBy(4, 0, dur), moveBy(-4, 0, dur)));
		} else if (face == FACE.left) {
			addAction(sequence(moveBy(-16, 0, dur), moveBy(16, 0, dur),
					moveBy(-12, 0, dur), moveBy(12, 0, dur),
					moveBy(-8, 0, dur), moveBy(8, 0, dur), moveBy(-4, 0, dur),
					moveBy(4, 0, dur)));
		}

	}

	/**
	 * 受到攻击动画
	 */
	public void hitedAction() {
		float dur = GC.duration_ani / 2;
		if (face == FACE.right) {
			this.setOrigin(0, 0);
			addAction(sequence(
					parallel(sequence(rotateBy(15.0f, dur),
							moveBy(-10.0f, .0f, dur))),
					parallel(sequence(rotateBy(-15.0f, dur),
							moveBy(10.0f, .0f, dur)))));
		} else if (face == FACE.left) {
			this.setOrigin(32, 0);
			addAction(sequence(
					parallel(sequence(rotateBy(-15.0f, dur),
							moveBy(10.0f, .0f, dur))),
					parallel(sequence(rotateBy(15.0f, dur),
							moveBy(-10.0f, .0f, dur)))));
		}
	}

	/**
	 * 击退效果
	 * 
	 * @param x
	 *            击退到的位置x坐标
	 * @param y
	 *            击退到的位置y坐标
	 */
	public void heatAction(int x, int y) {
		addAction(moveBy(x, y, GC.duration_ani / 10));
	}

	/**
	 * 冲锋效果
	 * 
	 * @param x
	 *            冲锋到的位置x坐标
	 * @param y
	 *            冲锋到的位置y坐标
	 */
	public void assaultAction(float x, float y, final Role obj,
			final BsuEvent be) {
		if (face == FACE.left) {
			this.setOrigin(32, 0);
			addAction(sequence(
					// 后仰
					parallel(rotateBy(-15.0f, 0.3f), moveBy(10.0f, 0.0f, 0.3f)),
					// 冲锋
					parallel(rotateBy(15.0f, 0.0f), moveTo(x, y, 0.2f)),
					// 处理冲锋结束后的动作
					run(new Runnable() {
						@Override
						public void run() {
							Commander.getInstance().stopedCommand(obj);
							if (be != null)
								be.notify(this, "assaultAcion_finished");

						}
					})));
		} else if (face == FACE.right) {
			this.setOrigin(0, 0);
			addAction(sequence(
			// 后仰
					parallel(rotateBy(15.0f, 0.3f), moveBy(-10.0f, 0.0f, 0.3f)),
					// 冲锋
					parallel(rotateBy(-15.0f, 0.2f), moveTo(x, y, 0.3f)),
					// 处理冲锋结束后的动作
					run(new Runnable() {
						@Override
						public void run() {
							Commander.getInstance().stopedCommand(obj);
							if (be != null)
								be.notify(this, "assaultAcion_finished");

						}
					})));
		}
	}

	/**
	 * 死亡动作
	 */
	public void deadAction(final BsuEvent be) {
		if (face == FACE.right) {
			this.setOrigin(0, 0);
			addAction(sequence(
					parallel(rotateBy(45f, 0.3f), moveBy(-5f, 0f, 0.3f),
							moveBy(0f, 10f, 0.3f)),
					parallel(rotateBy(45f, 0.3f), moveBy(-5f, 0f, 0.3f),
							moveBy(0f, -10f, 0.3f)),
					alpha(0.0f, 0.5f),
					// fadeOut(0f),
					// 让头像回到初始位置
					parallel(rotateTo(0, .0f), moveBy(10f, 0f, 0f)),
					run(new Runnable() {
						@Override
						public void run() {
//							Role.this.isDead = true;
//							Role.this.setVisible(false);
							be.notify(this, "dead");

						}
					})));
		} else if (face == FACE.left) {
			this.setOrigin(32, 0);
			addAction(sequence(
					parallel(rotateBy(-45f, 0.3f), moveBy(5f, 0f, 0.3f),
							moveBy(0f, -10f, 0.3f)),
					parallel(rotateBy(-45f, 0.3f), moveBy(5f, 0f, 0.3f),
							moveBy(0f, 10f, 0.3f)),
					alpha(0.0f, 0.5f),
					// fadeOut(0f),
					// 让头像回到初始位置
					parallel(rotateTo(0, .0f), moveBy(-10f, 0f, 0f)),
					run(new Runnable() {
						@Override
						public void run() {
							be.notify(this, "dead");

						}
					})));
		}
	}

	/**
	 * 将角色由死亡状态转为站立状态
	 */
	public void deadToStand() {
		if (face == FACE.right) {
			addAction(parallel(rotateBy(-90, .0f), moveBy(10f, 0f, 0f)));
		} else if (face == FACE.left) {
			addAction(parallel(rotateBy(90, .0f), moveBy(-10f, 0f, 0f)));
		}
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
	 * 获得人物总hp上限
	 * 
	 * @return
	 */
	public int getMaxHp() {
		return maxHp + extMaxHp;
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
	 * 设置当前血量
	 * 
	 * @param currentHp
	 */
	public void setCurrentHp(int currentHp) {
		this.currentHp = currentHp;
		if (this.currentHp <= 0)
			// 命令commander 执行死亡命令
			Commander.getInstance().commandRoleDead(this);
		ro.notifyRoleObservers(this);

	}

	public int getCurrentHp() {
		return currentHp;
	}

	
	
	/**
	 * 获得被观察者对象
	 * 
	 * @return
	 */
	public RoleObservable getRoleObserable() {
		return ro;
	}

	public class RoleObservable extends Observable {
		public RoleObservable() {
		}

		// 通知所有的观察者
		public void notifyRoleObservers(Object args) {
			this.setChanged();
			this.notifyObservers(args);
		}
	}

}
