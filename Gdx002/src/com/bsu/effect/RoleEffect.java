package com.bsu.effect;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.Role;
import com.bsu.tools.Configure.CLASSES;
import com.bsu.tools.Configure.QUALITY;
import com.bsu.tools.Configure;
import com.bsu.tools.GameTextureClass;
import com.bsu.tools.U;

/**
 * 绘制头像类，专门处理头像，因为用到的地方太多了
 * 
 * @author zhangyongchen
 * 
 */
public class RoleEffect {

	public Image role;// 角色图像
	private Image[] role_effect1 = new Image[1];// 选中角色后的特效图片
	private Image[] role_effect2 = new Image[1];// 选中角色后的特效图片
	private Image[] role_effect3 = new Image[1];
	private Image[] role_effect4 = new Image[1];
	public Image role_k;// 边框
	public Vector2 role_v;// 坐标
	public Image role_classes;// 角色职业图标

	public RoleEffect(Role r, Stage s, Vector2 v, boolean b) {
		role = new Image(r.roleTexture);
		role_k = new Image(WidgetFactory.getInstance().getTexture(48, 48,
				U.getQualityColor(r.quality), Color.BLACK, 0.2f));
		role_classes = getClassesImg(r);
		role.setScale(0.5f);
		role_effect1[0] = new Image(GameTextureClass.getInstance().start_zero);
		role_effect2[0] = new Image(
				GameTextureClass.getInstance().start_zero);
		
		role_effect3[0] = new Image(GameTextureClass.getInstance().start_zero);
		role_effect4[0] = new Image(GameTextureClass.getInstance().start_zero);
		
		s.addActor(role_k);
		s.addActor(role);
		s.addActor(role_classes);
		for (Image img : role_effect1) {
			s.addActor(img);
		}
		for (Image img : role_effect2) {
			s.addActor(img);
		}
		for (Image img : role_effect3) {
			s.addActor(img);
		}
		for (Image img : role_effect4) {
			s.addActor(img);
		}
		role_k.setPosition(v.x - 1, v.y - 1);
		role.setPosition(v.x, v.y);
		role_v = v;
		role_classes.setPosition(v.x + 32, v.y);
		r.photo=this;
		showEffect(b);
	}

	/**
	 * 无头像
	 * 
	 * @param s
	 * @param q
	 * @param v
	 */
	public RoleEffect(Stage s, QUALITY q, Vector2 v) {
		role_k = new Image(WidgetFactory.getInstance().getTexture(50, 50,
				U.getQualityColor(q), Color.BLACK, 0.2f));
		s.addActor(role_k);
		role_k.setPosition(v.x - 1, v.y - 1);
	}

	/**
	 * 显示被选中效果
	 * 
	 * @param b
	 */
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
			reffect[i].setPosition(role_v.x +offsetX, role_v.y +offsetY);
			MoveByAction mba=moveBy(0,0,moveDura/5*i);
			RepeatAction ra = new RepeatAction();
			ra.setAction(seq);
			ra.setCount(RepeatAction.FOREVER);
			reffect[i].addAction(sequence(mba,ra));
		}
	}
	/**
	 * 根据role 返回角色职业图标
	 * 
	 * @param r
	 * @return
	 */
	private Image getClassesImg(Role r) {
		Image img = null;
		if (r.classes == CLASSES.fighter) {
			img = new Image(GameTextureClass.getInstance().fight_texture);
		}
		if (r.classes == CLASSES.wizard) {
			img = new Image(GameTextureClass.getInstance().wizard_texture);
		}
		if (r.classes == CLASSES.archer) {
			img = new Image(GameTextureClass.getInstance().archer_texture);
		}
		if (r.classes == CLASSES.cleric) {
			img = new Image(GameTextureClass.getInstance().cleric_texture);
		}
		if (r.classes == CLASSES.sorcerer) {
			img = new Image(GameTextureClass.getInstance().sorcerer_texture);
		}
		return img;
	}
}
