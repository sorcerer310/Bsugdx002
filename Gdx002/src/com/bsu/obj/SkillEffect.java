package com.bsu.obj;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.Configure;
import com.bsu.tools.GameTextureClass;
import com.bsu.tools.U;

public class SkillEffect {
	private TextureRegion quality_frame;
	public Image skillImgEffect;
	public Image skillImg;
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
	public SkillEffect(Skill skill, Stage s, Vector2 v, boolean lvFlag) {
		if (skill.enable) {
			skillImg = new Image(skill.icon);
			quality_frame = WidgetFactory.getInstance().getTexture(26, 26,
					Configure.getQualityColor(skill.quality), Color.BLACK, 1);
			skillImgEffect = new Image(quality_frame);
			s.addActor(skillImgEffect);
			skillImgEffect.setPosition(v.x - 1, v.y - 1);
			if (lvFlag)
				WidgetFactory.getInstance().makeLabel("" + skill.lev, s, 0.5f,
						(int) (v.x) + 28, (int) (v.y) - 7);
		} else {
			skillImg = new Image(GameTextureClass.getInstance().getSkillIcon(0));
		}
		s.addActor(skillImg);
		skillImg.setPosition(v.x, v.y);
		U.setApha(skillImg, 0.5f);
		skill.skillEffect=this;
	}
	public void changeSkillEffect(SkillEffect se){
		skillImg.setDrawable(se.skillImg.getDrawable());
		skillImgEffect.setDrawable(se.skillImgEffect.getDrawable());
		U.setApha(skillImg, 1);
		U.setApha(se.skillImg, 1);
	}
}
