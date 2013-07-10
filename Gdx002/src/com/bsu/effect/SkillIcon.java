package com.bsu.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.GC;
import com.bsu.tools.GTC;
import com.bsu.tools.U;

public class SkillIcon extends WidgetGroup {
	private TextureRegion quality_frame;
	public Image skillImgEffect;
	public Image skillImg;
	public Image notEnabledImg;
	public Image enableImg;
	public Label lv;

	/**
	 * 将一个技能图标添加到某一个舞台
	 * 
	 * @param skill
	 *            技能
	 * @param s
	 *            舞台
	 * @param v
	 *            坐标
	 */
	public SkillIcon(Skill skill, Stage s, Vector2 v) {
		enableImg = new Image(skill.icon);
		notEnabledImg = new Image(GTC.getInstance().getSkillIcon(0));
		quality_frame = WidgetFactory.getInstance().getPixmapFrame(26, 26,
				U.getQualityColor(skill.quality), Color.BLACK, 1);
		skillImgEffect = new Image(quality_frame);
		skillImgEffect.setScale(1.2f);
		addActor(skillImgEffect);
		skillImgEffect.setPosition(v.x - 1, v.y - 1);
		lv = WidgetFactory.getInstance().makeLabel("" + skill.lev, 0.5f,
				(int) (v.x) + 34, (int) (v.y) - 7);
		addActor(lv);
		if (!skill.enable) {
			lv.setText("");
			skillImg = notEnabledImg;
		} else {
			skillImg = enableImg;
		}
		addActor(skillImg);
		skillImg.setScale(1.2f);
		skillImg.setPosition(v.x, v.y);
		U.setAlpha(skillImg, 0.5f);
		skill.skillIcon = this;
		s.addActor(this);
	}

	/*
	 * 空技能，不同于上个构造函数，虽然上面也有空的技能显示，但是他只是显示为空， 其他都存在，这个就是一个空技能图片
	 */
	public SkillIcon(Stage s, Vector2 v) {
		Image emptyImg = new Image(GTC.getInstance().getSkillIcon(0));
		U.setAlpha(emptyImg, 0.5f);
		addActor(emptyImg);
		emptyImg.setPosition(v.x, v.y);
		s.addActor(this);
	}
}
