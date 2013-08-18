								package com.bsu.obj;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.bsu.effect.SkillIcon;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.Role.BATLESTATE;
import com.bsu.obj.skilltree.Skill;
import com.bsu.obj.skilltree.Skill.Type;
import com.bsu.tools.GTC;
import com.bsu.tools.U;
import com.bsu.tools.GC.QUALITY;
//import com.sun.tools.hat.internal.parser.HprofReader;

public class RoleView {
	public static enum ViewType {
		hp, attack, defend, healing, effect
	}

	/**
	 * 基本信息
	 * @param r
	 */
	public static WidgetGroup showRoleBaseInfo(Role r) {
		float scale = 0.6f;
		WidgetGroup infoGroup = new WidgetGroup();// 角色基本信息容器
		Label name = WidgetFactory.getInstance().makeLabel(r.name, scale, 52,235, U.getQualityColor(r.quality));
		Label classes = WidgetFactory.getInstance().makeLabel(U.getClasses(r),	scale, 170, 235);
		
		Table hpTable = showInfoValue(r, ViewType.hp, 52, 227);
		Label lv = WidgetFactory.getInstance().makeLabel("等级:" + r.level,scale, 170, 213);

		Table attackTable = showInfoValue(r, ViewType.attack, 52, 207);
		Label exp = WidgetFactory.getInstance().makeLabel("经验:" + r.exp + "/" + U.getUpExp(r,r.level), scale, 170, 193);
		
		Table healingTable = showInfoValue(r, ViewType.defend, 52, 186);
		Table defendTable = showInfoValue(r, ViewType.healing, 170, 186);
		
		Table effectTable = showSkillEffect(r, 52, 165);
		infoGroup.addActor(name);
		infoGroup.addActor(classes);
		infoGroup.addActor(lv);
		infoGroup.addActor(hpTable);
		infoGroup.addActor(exp);
		infoGroup.addActor(attackTable);
		infoGroup.addActor(defendTable);
		infoGroup.addActor(healingTable);
		infoGroup.addActor(effectTable);
		return infoGroup;
	}

	/**
	 *  绘制锁定界面（图标）
	 * @param r
	 * @return
	 */
	public static WidgetGroup showLockImg(Role r) {
		WidgetGroup lockGroup = new WidgetGroup();
		Image unLockImg = new Image(GTC.getInstance().getSkillIcon(0));
		Image lockImg = GTC.getInstance().getClassesIconImg(r.classes);
		Image lockedImg = r.locked ? lockImg : unLockImg;
		lockedImg.setPosition(205, 110);
		lockGroup.addActor(lockedImg);
		return lockGroup;
	}

	/**
	 *  绘制出战状态界面(图标)
	 * @param r
	 * @return
	 */
	public static WidgetGroup showBatleImg(Role r) {
		WidgetGroup batleGroup = new WidgetGroup();
		Image unFightImg = new Image(GTC.getInstance().getSkillIcon(0));
		Image fightImg = GTC.getInstance().getClassesIconImg(r.classes);
		Image battleImg = r.bstate == BATLESTATE.FIGHT ? fightImg : unFightImg;
		battleImg.setPosition(170, 110);
		batleGroup.addActor(battleImg);
		return batleGroup;
	}

	// 绘制角色当前携带的2个技能
	public static WidgetGroup showRoleSkill(Role r, int skill_index) {
		WidgetGroup skillGroup = new WidgetGroup();
		Array<Skill> skillArray = r.getUseSkill();
		for (int i = 0; i < skillArray.size; i++) {
			Skill s = skillArray.get(i);
			Vector2 v = new Vector2(45 + i * 45, 110);
			SkillIcon se = new SkillIcon(s, v);
			s.skillIcon = se;
			if (i == skill_index) {
				U.setAlpha(se.skillImg, 1);
			}
			skillGroup.addActor(se);
		}
		return skillGroup;
	}

	/**
	 * 设置显示技能树
	 */
	public static WidgetGroup showSkillTree(Role r, Skill skill) {
		WidgetGroup skillGroup = new WidgetGroup();
		int numsGreen = 0;
		int numsBlue = 0;
		int numsPur = 0;
		int numsOra = 0;
		int ix = 280, iy = 110, height = 35, sw = 45;
		Vector2 vs = null;
		for (Skill s : r.skill_tree) {
			SkillIcon se = null;
			if (s.quality == QUALITY.green) {
				vs = new Vector2(ix + numsGreen * sw, iy);
				numsGreen++;
			}
			if (s.quality == QUALITY.blue) {
				vs = new Vector2(ix + numsBlue * sw, iy + height);
				numsBlue++;
			}
			if (s.quality == QUALITY.purple) {
				vs = new Vector2(ix + numsPur * sw, iy + height * 2);
				numsPur++;
			}
			if (s.quality == QUALITY.orange) {
				vs = new Vector2(ix + numsOra * sw, iy + height * 3);
				numsOra++;
			}
			se = new SkillIcon(s, vs);
			skillGroup.addActor(se);
			if (s != skill) {
				U.setAlpha(se.skillImg, 0.5f);
			} else {
				U.setAlpha(se.skillImg, 1.0f);
			}
		}
		return skillGroup;
	}

	// 显示角色各项信息（包含携带技能）
	private static Table showInfoValue(Role r, ViewType p, int x, int y) {
		float scale = 0.6f;
		int value = 0;
		String sa = "";
		String sn="";
		Color c=Color.GREEN;
		Array<Type> skillp = new Array<Type>();
		float n = 0;
		Table valueTable = new Table();
		valueTable.defaults().padRight(1);
		valueTable.align(Align.left);
		valueTable.setPosition(x, y);
		if (p == ViewType.hp) {
			value = U.getCurrentBaseHp(r,r.level);
			skillp.add(Type.pbuff_hp);
			sa = "生命";
			sn=":"+value;
			c=U.getColorFromTalent(r, "hp");
		}
		if (p == ViewType.attack) {
			value = U.getCurrentBaseAttack(r,r.level);
			skillp.add(Type.p_damage);
			skillp.add(Type.f_damage);
			skillp.add(Type.pbuff_atk);
			skillp.add(Type.p_atkbeat);
			skillp.add(Type.pdot_damage);
			skillp.add(Type.p_assault);
			sa = "攻击";
			sn=":"+value;
			c=U.getColorFromTalent(r, "attack");
		}
		if (p == ViewType.defend) {
			value = U.getCurrentBaseDefend(r,r.level);
			skillp.add(Type.pbuff_def);
			sa = "防御";
			sn=":"+value;
			c=U.getColorFromTalent(r, "defend");
		}
		if (p == ViewType.healing) {
			value = 1;
			sa = "回复";
			sn=":0";
			skillp.add(Type.p_healing);// 百分比恢复
			skillp.add(Type.f_healing);// 固定恢复
			skillp.add(Type.pbuff_healing);// 持续恢复
			c=new Color(0,255,0,1);
		}
		Label title = new Label(sa, U.get_Label_sytle());
		title.setFontScale(scale);
		title.setColor(c);
		Label tn=new Label(sn,U.get_Label_sytle());
		tn.setFontScale(scale);
		valueTable.add(title);
		valueTable.add(tn);
		for (Skill s : r.getUseSkill()) {
			String hs = "";
			boolean hasType = false;
			for (Type sp : skillp) {
				if (s.type == sp) {
					if (s.type == Type.p_damage || s.type == Type.p_atkbeat || s.type == Type.pdot_damage||s.type==Type.p_assault) {
						n = 1;
					}
					if (s.type == Type.f_damage) {
						n = s.getVal() - s.getVal() / value + 1;
					}
					if (s.type == Type.pbuff_atk || s.type == Type.pbuff_def
							|| s.type == Type.f_healing
							|| s.type == Type.pbuff_hp) {
						n = 0;
					}
					if (s.type == Type.p_healing) {
						n = 0;
						value = U.getCurrentBaseHp(r,r.level);
					}
					if (s.type == Type.pbuff_healing) {
						n = s.getVal() - s.getVal() / value;
					}
					hasType = true;
				}
			}
			if (s.enable)
				if (hasType) {
					hs += MathUtils.round((s.getVal() - n) * value);
				} else {
					hs += 0;
				}
			else {
				hs += "-";
			}
			Label a = new Label("(", U.get_Label_sytle());
			a.setFontScale(scale);
			a.setColor(U.getQualityColor(s.quality));
			Label hp_string = new Label(hs, U.get_Label_sytle());
			hp_string.setFontScale(scale);
			hp_string.setColor(U.getQualityColor(s.quality));
			Label b = new Label(")", U.get_Label_sytle());
			b.setFontScale(scale);
			b.setColor(U.getQualityColor(s.quality));
			valueTable.add(a);
			valueTable.add(hp_string);
			valueTable.add(b);
		}
		return valueTable;
	}

	private static Table showSkillEffect(Role r, int x, int y) {
		float scale = 0.6f;
		String sa = "特效:";
		Table valueTable = new Table();
		valueTable.align(Align.left);
		valueTable.defaults().padRight(1);
		valueTable.setPosition(x, y);
		Label title = new Label(sa, U.get_Label_sytle());
		title.setFontScale(scale);
		title.setColor(Color.ORANGE);
		valueTable.add(title);
		for (Skill s : r.getUseSkill()) {
			String hs = "-";
			if (s.type == Type.prob_blind) {
				hs = "致盲";
			}
			if (s.type == Type.prob_dizzy) {
				hs = "眩晕";
			}
			if (s.type == Type.prob_nude) {
				hs = "碎甲";
			}
			if (s.type == Type.p_atkbeat) {
				hs = "击退";
			}
			if (s.type == Type.f_shifhp) {
				hs = "吸血";
			}
			if (s.type == Type.f_box) {
				hs = "多重";
			}
			if (s.type == Type.p_speed) {
				hs = "加速";
			}
			if (s.type == Type.p_reward) {
				hs = "幸运";
			}
			if (!s.enable) {
				hs = "-";
			}
			Label a = new Label("(", U.get_Label_sytle());
			a.setFontScale(scale);
			a.setColor(U.getQualityColor(s.quality));
			Label hp_string = new Label(hs, U.get_Label_sytle());
			hp_string.setFontScale(scale);
			hp_string.setColor(U.getQualityColor(s.quality));
			Label b = new Label(")", U.get_Label_sytle());
			b.setFontScale(scale);
			b.setColor(U.getQualityColor(s.quality));
			valueTable.add(a);
			valueTable.add(hp_string);
			valueTable.add(b);
		}
		return valueTable;

	}
}
