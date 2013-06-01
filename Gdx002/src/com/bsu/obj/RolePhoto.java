package com.bsu.obj;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bsu.make.WidgetFactory;
import com.bsu.tools.Configure.QUALITY;
import com.bsu.tools.Configure;
import com.bsu.tools.GameTextureClass;
/**
 * 绘制头像类，专门处理头像，因为用到的地方太多了
 * @author zhangyongchen
 *
 */
public class RolePhoto {

	public Image role;// 角色图像
	private Image role_effect;// 选中角色后的特效图片
	private Image role_effect_another;// 选中角色后的特效图片
	public Image role_k;// 边框
	public Vector2 role_v;//坐标

	public RolePhoto(TextureRegion tr,Stage s, QUALITY q,Vector2 v,boolean b) {
		role = new Image(tr);
		role_effect = new Image(GameTextureClass.getInstance().role_effect);
		role_effect_another = new Image(GameTextureClass.getInstance().role_effect);
		role_k = new Image(WidgetFactory.getInstance()
				.getTexture(50,50, Configure.getQualityColor(q),
						Color.BLACK, 0.2f));
		role.setScale(0.5f);
		s.addActor(role_k);
		s.addActor(role);
		s.addActor(role_effect);
		s.addActor(role_effect_another);
		role_k.setPosition(v.x-1, v.y-1);
		role.setPosition(v.x, v.y);
		role_v=v;
		showEffect(b);
	}
	/**
	 * 无头像
	 * @param s
	 * @param q
	 * @param v
	 */
	public RolePhoto(Stage s, QUALITY q,Vector2 v) {
		role_k = new Image(WidgetFactory.getInstance()
				.getTexture(50,50, Configure.getQualityColor(q),
						Color.BLACK, 0.2f));
		s.addActor(role_k);
		role_k.setPosition(v.x-1, v.y-1);
	}

	public void showEffect(boolean b) {
		role_effect.setVisible(b);
		role_effect_another.setVisible(b);
		if (b) {
			role_effect.getActions().clear();
			role_effect.setPosition(role_v.x - 17, role_v.y - 17);
			RepeatAction ra = new RepeatAction();
			ra.setAction(sequence(moveBy(0, 50, 1f), moveBy(50, 0, 1f),
					moveBy(0, -50, 1f), moveBy(-50, 0, 1f)));
			ra.setCount(RepeatAction.FOREVER);
			role_effect.addAction(ra);
			
			role_effect_another.getActions().clear();
			role_effect_another.setPosition(role_v.x+32, role_v.y +32);
			RepeatAction ra_other = new RepeatAction();
			ra_other.setAction(sequence(moveBy(0, -50, 1f), moveBy(-50, 0, 1f),
					moveBy(0, 50, 1f), moveBy(50, 0, 1f)));
			ra_other.setCount(RepeatAction.FOREVER);
			role_effect_another.addAction(ra_other);
		}
	}
}
