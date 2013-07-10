package com.bsu.effect;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.Role;
import com.bsu.obj.Role.BATLESTATE;
import com.bsu.tools.GTC;
import com.bsu.tools.U;
import com.bsu.tools.GC.QUALITY;

/**
 * 头像图标改进类
 * 
 * @author fengchong
 * 
 */
public class RoleIcon extends WidgetGroup {
	Image[] role_effect1 = new Image[] { new Image(GTC.getInstance().start_zero) };
	Image[] role_effect2 = new Image[] { new Image(GTC.getInstance().start_zero) };
	Image[] role_effect3 = new Image[] { new Image(GTC.getInstance().start_zero) };
	Image[] role_effect4 = new Image[] { new Image(GTC.getInstance().start_zero) };
	public Image img_head = null; // 头像图片
	public Image img_frame = null;
	public Image img_classes = null;
	public MyParticle particle;

	public RoleIcon(Role r, boolean b,boolean flag) {
		img_head = new Image(GTC.getInstance().hm_headItemIcon.get(r.roleTextureName)); // 头像图片
		img_head.setScale(0.5f);
		img_frame = GTC.getInstance().getImageFrame(r.quality);
		img_classes = GTC.getInstance().getClassesIconImg(r.classes);
		img_classes.setPosition(
				img_head.getWidth() / 2 - img_classes.getWidth() - 2, 2);
		this.addActor(img_head);
		this.addActor(img_classes);
		this.addActor(img_frame);
		if((r.bstate==BATLESTATE.FIGHT)&&flag){
			Image img_fight=new Image(img_classes.getDrawable());
			this.addActor(img_fight);
		}
		for (int i = 0; i < role_effect1.length; i++) {
			if (role_effect1[i] != null)
				this.addActor(role_effect1[i]);
			if (role_effect2[i] != null)
				this.addActor(role_effect2[i]);
			if (role_effect3[i] != null)
				this.addActor(role_effect3[i]);
			if (role_effect4[i] != null)
				this.addActor(role_effect4[i]);
		}
		r.roleIcon = this;
		showEffect(b);
	}
	/**
	 * 显示被选中效果
	 * 
	 * @param b
	 */
	public void showEffect(boolean b) {
		if (b) {
			float moveDura = 0.35f;
			SequenceAction seq1 = sequence(moveBy(0, 50, moveDura),
					moveBy(50, 0, moveDura), moveBy(0, -50, moveDura),
					moveBy(-50, 0, moveDura));
			setEffectAction(role_effect1, b, moveDura, -16, -14, seq1);

			SequenceAction seq2 = sequence(moveBy(50, 0, moveDura),
					moveBy(0, -50, moveDura), moveBy(-50, 0, moveDura),
					moveBy(0, 50, moveDura));
			setEffectAction(role_effect2, b, moveDura, -16, 36, seq2);

			SequenceAction seq3 = sequence(moveBy(0, -50, moveDura),
					moveBy(-50, 0, moveDura), moveBy(0, 50, moveDura),
					moveBy(50, 0, moveDura));
			setEffectAction(role_effect3, b, moveDura, 34, 36, seq3);

			SequenceAction seq4 = sequence(moveBy(-50, 0, moveDura),
					moveBy(0, 50, moveDura), moveBy(50, 0, moveDura),
					moveBy(0, -50, moveDura));
			setEffectAction(role_effect4, b, moveDura, 34, -14, seq4);

		} else {
			for (Image img : role_effect1)
				img.setVisible(b);
			for (Image img : role_effect2)
				img.setVisible(b);
			for (Image img : role_effect3)
				img.setVisible(b);
			for (Image img : role_effect4)
				img.setVisible(b);
		}
	}

	/**
	 * 设置效果的动作
	 * 
	 * @param reffect
	 *            效果图片
	 * @param b
	 *            是否显示
	 * @param moveDura
	 *            移动一个边所花费的时间
	 */
	private void setEffectAction(Image[] reffect, boolean b, float moveDura,
			int offsetX, int offsetY, SequenceAction seq) {
		for (int i = 0; i < reffect.length; i++) {
			reffect[i].setVisible(b);
			reffect[i].getActions().clear();
			reffect[i].setPosition(offsetX, offsetY);
			MoveByAction mba = moveBy(0, 0, moveDura / 5 * i);
			RepeatAction ra = Actions.repeat(RepeatAction.FOREVER, seq);
			reffect[i].addAction(sequence(mba, ra));
		}
	}
}
