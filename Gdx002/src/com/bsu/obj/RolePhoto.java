package com.bsu.obj;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bsu.make.WidgetFactory;
import com.bsu.tools.Configure.CLASSES;
import com.bsu.tools.Configure.QUALITY;
import com.bsu.tools.Configure;
import com.bsu.tools.GameTextureClass;

/**
 * 绘制头像类，专门处理头像，因为用到的地方太多了
 * 
 * @author zhangyongchen
 * 
 */
public class RolePhoto {

	public Image role;// 角色图像
	private Image[] role_effect = new Image[3];// 选中角色后的特效图片
	private Image[] role_effect_another = new Image[3];// 选中角色后的特效图片
	public Image role_k;// 边框
	public Vector2 role_v;// 坐标
	public Image role_classes;// 角色职业图标

	public RolePhoto(Role r, Stage s, Vector2 v, boolean b) {
		role = new Image(r.roleTexture);
		role_k = new Image(WidgetFactory.getInstance().getTexture(50, 50,
				Configure.getQualityColor(r.quality), Color.BLACK, 0.2f));
		role_classes = getClassesImg(r);
		role.setScale(0.5f);
		role_effect[0] = new Image(GameTextureClass.getInstance().start_zero);
		role_effect[1] = new Image(GameTextureClass.getInstance().start_one);
		role_effect[2] = new Image(GameTextureClass.getInstance().start_two);
		role_effect_another[0] = new Image(
				GameTextureClass.getInstance().start_zero);
		role_effect_another[1] = new Image(
				GameTextureClass.getInstance().start_one);
		role_effect_another[2] = new Image(
				GameTextureClass.getInstance().start_two);
		s.addActor(role_k);
		s.addActor(role);
		s.addActor(role_classes);
		for (Image img : role_effect) {
			s.addActor(img);
		}
		for (Image img : role_effect_another) {
			s.addActor(img);
		}
		role_k.setPosition(v.x - 1, v.y - 1);
		role.setPosition(v.x, v.y);
		role_v = v;
		role_classes.setPosition(v.x + 34, v.y);
		showEffect(b);
	}

	/**
	 * 无头像
	 * 
	 * @param s
	 * @param q
	 * @param v
	 */
	public RolePhoto(Stage s, QUALITY q, Vector2 v) {
		role_k = new Image(WidgetFactory.getInstance().getTexture(50, 50,
				Configure.getQualityColor(q), Color.BLACK, 0.2f));
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
			for (int i=0;i<role_effect.length;i++) {
				role_effect[i].setVisible(b);
				role_effect[i].getActions().clear();
				role_effect[i].setPosition(role_v.x - 16, role_v.y - 14);
				MoveByAction mba=moveBy(0,0,0.5f*i);
				RepeatAction ra = new RepeatAction();
				ra.setAction(sequence(moveBy(0, 50, 2f), moveBy(50, 0, 2f),
						moveBy(0, -50, 2f), moveBy(-50, 0, 2f)));
				ra.setCount(RepeatAction.FOREVER);
				role_effect[i].addAction(sequence(mba,ra));
			}
			for (int i=0;i< role_effect_another.length;i++) {
				role_effect_another[i].setVisible(b);
				role_effect_another[i].getActions().clear();
				role_effect_another[i].setPosition(role_v.x + 34, role_v.y + 36);
				MoveByAction mba=moveBy(0,0,0.5f*i);
				RepeatAction ra_other = new RepeatAction();
				ra_other.setAction(sequence(moveBy(0, -50, 2f),
						moveBy(-50, 0, 2f), moveBy(0, 50, 2f),
						moveBy(50, 0, 2f)));
				ra_other.setCount(RepeatAction.FOREVER);
				role_effect_another[i].addAction(sequence(mba,ra_other));
			}
		} else {
			for (Image img : role_effect) {
				img.setVisible(b);
			}
			for (Image img : role_effect_another) {
				img.setVisible(b);
			}
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
