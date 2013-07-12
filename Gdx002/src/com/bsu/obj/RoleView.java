package com.bsu.obj;

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
import com.sun.tools.hat.internal.parser.HprofReader;

public class RoleView {
	public static enum ViewType {
		hp, attack, defend, healing
	}

	/**
	 * 基本信息
	 * 
	 * @param r
	 */
	public static WidgetGroup showRoleBaseInfo(Role r) {
		float scale=0.6f;
		WidgetGroup infoGroup = new WidgetGroup();// 角色基本信息容器
		Label name = WidgetFactory.getInstance().makeLabel(r.name, 1f, 20, 240,
				U.getQualityColor(r.quality));
		Label lv = WidgetFactory.getInstance().makeLabel("等级:" + r.level, scale,
				140, 240);
		Label exp = WidgetFactory.getInstance().makeLabel(
				"经验:" + r.exp + "/" + r.expUp, scale, 80, 240);
		Table hpTable = showInfoValue(r, ViewType.hp, 20, 230);
		Table attackTable = showInfoValue(r, ViewType.attack, 140, 230);
		Table healingTable = showInfoValue(r, ViewType.defend, 140, 210);
		Table defendTable = showInfoValue(r, ViewType.healing, 20, 210);
		infoGroup.addActor(name);
		infoGroup.addActor(lv);
		infoGroup.addActor(hpTable);
		infoGroup.addActor(exp);
		infoGroup.addActor(attackTable);
		infoGroup.addActor(defendTable);
		infoGroup.addActor(healingTable);
		return infoGroup;
	}

	// 绘制锁定界面（图标）
	public static WidgetGroup showLockImg(Role r) {
		WidgetGroup lockGroup = new WidgetGroup();
		Image unLockImg = new Image(GTC.getInstance().getSkillIcon(0));
		Image lockImg = GTC.getInstance().getClassesIconImg(r.classes);
		Image lockedImg = r.locked ? lockImg : unLockImg;
		lockedImg.setPosition(160, 120);
		lockGroup.addActor(lockedImg);
		return lockGroup;
	}

	// 绘制出战状态界面(图标)
	public static WidgetGroup showBatleImg(Role r) {
		WidgetGroup batleGroup = new WidgetGroup();
		Image unFightImg = new Image(GTC.getInstance().getSkillIcon(0));
		Image fightImg = GTC.getInstance().getClassesIconImg(r.classes);
		Image battleImg = r.bstate == BATLESTATE.FIGHT ? fightImg : unFightImg;
		battleImg.setPosition(120, 120);
		batleGroup.addActor(battleImg);
		return batleGroup;
	}

	// 绘制角色当前携带的2个技能
	public static WidgetGroup showRoleSkill(Role r, int skill_index) {
		WidgetGroup skillGroup = new WidgetGroup();
		Array<Skill> skillArray = r.getUseSkill();
		for (int i = 0; i < skillArray.size; i++) {
			Skill s = skillArray.get(i);
			Vector2 v = new Vector2(40 + i * 40, 120);
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
		int ix = 240, iy = 125, height = 40, sw = 45;
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
		String sn = "";
		int value = 0;
		String sa = "";
		Array<Type> skillp = new Array<Type>();
		float n = 0;
		Table valueTable = new Table();
		valueTable.align(Align.left);
		valueTable.setPosition(x, y);
		if (p == ViewType.hp) {
			value = r.maxHp;
			skillp.add(Type.pbuff_hp);
			sa = "生命";
			n = 0;
		}
		if (p == ViewType.attack) {
			value = r.attack;
			skillp.add(Type.p_damage);
			skillp.add(Type.f_damage);
			skillp.add(Type.pbuff_atk);
			sa = "攻击";
		}
		if (p == ViewType.defend) {
			value = r.defend;
			skillp.add(Type.pbuff_def);
			sa = "防御";
			n = 0;
		}
		if (p == ViewType.healing) {
			value = r.maxHp;
			sa = "回复";
			skillp.add(Type.p_healing);
			n = 0;
		}
		sn += sa + ":" + value;
		Label title = new Label(sn,U.get_sytle());
		title.setFontScale(scale);
		valueTable.add(title);
		for (Skill s : r.getUseSkill()) {
			String hs = "";
			int sv = 0;
			boolean hasType=false;
			for(Type sp:skillp){
				if(s.type==sp){
					if(s.type==Type.p_damage){
						n=1;
						System.out.println(s.getVal());
					}
					if(s.type==Type.f_damage){
						System.out.println(s.getVal());
						n=s.getVal()-s.getVal()/value+1;
					}
					if(s.type==Type.pbuff_atk){
						n=0;
					}
					hasType=true;
				}
			}
			if (s.enable && hasType) {
				sv = (int) ((s.getVal() - n) * value);
			} else {
				sv = 0;
			}
			hs += sv;
			Label a = new Label("(", U.get_sytle());
			a.setFontScale(scale);
			a.setColor(U.getQualityColor(s.quality));
			sn += hs;
			Label hp_string = new Label(hs, U.get_sytle());
			hp_string.setFontScale(scale);
			hp_string.setColor(U.getQualityColor(s.quality));
			Label b = new Label(")", U.get_sytle());
			b.setFontScale(scale);
			b.setColor(U.getQualityColor(s.quality));
			valueTable.add(a);
			valueTable.add(hp_string);
			valueTable.add(b);
		}
		return valueTable;
	}
}
