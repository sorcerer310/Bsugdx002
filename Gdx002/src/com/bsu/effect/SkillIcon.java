package com.bsu.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.GC;
import com.bsu.tools.GTC;
import com.bsu.tools.U;

public class SkillIcon {
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
	public SkillIcon(Skill skill, Stage s, Vector2 v, boolean lvFlag) {
		enableImg = new Image(skill.icon);
		notEnabledImg=new Image(GTC.getInstance().getSkillIcon(0));
		quality_frame = WidgetFactory.getInstance().getPixmapFrame(26, 26,
				U.getQualityColor(skill.quality), Color.BLACK, 1);
		skillImgEffect = new Image(quality_frame);
		skillImgEffect.setScale(1.2f);
		s.addActor(skillImgEffect);
		skillImgEffect.setPosition(v.x - 1, v.y - 1);
		if (lvFlag){
			lv= WidgetFactory.getInstance().makeLabel("" + skill.lev, 0.5f,
					(int) (v.x) + 34, (int) (v.y) - 7);
			s.addActor(lv);
		}
		if (!skill.enable) {
			lv.setText("");
			skillImg=notEnabledImg;
		}else{
			skillImg=enableImg;
		}
		s.addActor(skillImg);	
		skillImg.setScale(1.2f);
		skillImg.setPosition(v.x, v.y);
		U.setAlpha(skillImg, 0.5f);
		skill.skillIcon=this;
	}
}
