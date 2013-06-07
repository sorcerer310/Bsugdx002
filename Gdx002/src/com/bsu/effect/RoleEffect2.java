package com.bsu.effect;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.Role;
import com.bsu.tools.GTC;
import com.bsu.tools.U;

/**
 * 头像图标改进类
 * @author fengchong
 *
 */
public class RoleEffect2 extends WidgetGroup{
	Image[] role_effect1= new Image[]{new Image(GTC.getInstance().start_zero)};
	Image[] role_effect2= new Image[]{new Image(GTC.getInstance().start_zero)};
	Image[] role_effect3= new Image[]{new Image(GTC.getInstance().start_zero)};
	Image[] role_effect4= new Image[]{new Image(GTC.getInstance().start_zero)};
	Image img_head = null;	//头像图片
	Image img_frame = null;
	Image img_classes = null;
	public RoleEffect2(Role r,boolean b){
		img_head = new Image(r.roleTexture);	//头像图片
		img_head.setScale(0.5f);
		img_frame = new Image(WidgetFactory.getInstance().getTexture(
				48, 48, U.getQualityColor(r.quality), Color.CLEAR, 0.1f));	//边框
		img_classes = GTC.getInstance().getClassesIconImg(r);
		img_classes.setPosition(img_head.getWidth()/2-img_classes.getWidth(), 0);

		this.addActor(img_head);
		this.addActor(img_classes);
		this.addActor(img_frame);
		for(int i=0;i<role_effect1.length;i++){
			if(role_effect1[i]!=null)
				this.addActor(role_effect1[i]);
			if(role_effect2[i]!=null)
				this.addActor(role_effect2[i]);
			if(role_effect3[i]!=null)
				this.addActor(role_effect3[i]);
			if(role_effect4[i]!=null)
				this.addActor(role_effect4[i]);
		}
		r.photo2 = this;
		showEffect(b);
	}

	public void showEffect(boolean b) {
		if (b) {
			float moveDura = 0.35f;
			SequenceAction seq1 = sequence(moveBy(0, 50, moveDura), moveBy(50, 0, moveDura),
					moveBy(0, -50, moveDura), moveBy(-50, 0, moveDura));
			setEffectAction(role_effect1,b,moveDura,-16,-14,seq1);
			
			SequenceAction seq2 = sequence(moveBy(50, 0, moveDura), moveBy(0, -50, moveDura),
					moveBy(-50, 0, moveDura), moveBy(0, 50, moveDura));
			setEffectAction(role_effect2,b,moveDura,-16,36,seq2);
			
			SequenceAction seq3 = sequence(moveBy(0, -50, moveDura),moveBy(-50, 0, moveDura), 
					moveBy(0, 50, moveDura),moveBy(50, 0, moveDura));
			setEffectAction(role_effect3,b,moveDura,34,36,seq3);

			SequenceAction seq4 = sequence(moveBy(-50, 0, moveDura),moveBy(0, 50, moveDura), 
					moveBy(50, 0, moveDura),moveBy(0, -50, moveDura));
			setEffectAction(role_effect4,b,moveDura,34,-14,seq4);
			
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
	 * @param reffect	效果图片
	 * @param b			是否显示
	 * @param moveDura	移动一个边所花费的时间
	 */
	private void setEffectAction(Image[] reffect,boolean b,float moveDura,int offsetX,int offsetY,SequenceAction seq){
		for (int i=0;i<reffect.length;i++) {
			reffect[i].setVisible(b);
			reffect[i].getActions().clear();
			reffect[i].setPosition(offsetX, offsetY);
			MoveByAction mba=moveBy(0,0,moveDura/5*i);
			RepeatAction ra = new RepeatAction();
			ra.setAction(seq);
			ra.setCount(RepeatAction.FOREVER);
			reffect[i].addAction(sequence(mba,ra));
		}
	}
}
