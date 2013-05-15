package com.bsu.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RoleHP {
	int pixHeight = 5; // 血条高度

	public static enum Type {
		BACK, FACE
	};

	public RoleHP() {
		// TODO Auto-generated constructor stub
	}

	/*s
	 * 设置人物生命血条
	 */
	public TextureRegion get_role_hp(Type p) {
		Pixmap pixmap;
		pixmap = new Pixmap(8, 8, Format.RGBA8888); // 生成一张64*8的图片
		if (p == Type.BACK) {
			pixmap.setColor(Color.BLACK); // 设置颜色为黑色。
			pixmap.drawRectangle(0, 0, Configure.map_box_value, pixHeight);
		}else{
			pixmap.setColor(Color.RED); // 设置颜色为黑色。
			pixmap.fillRectangle(1, 1, Configure.map_box_value-2, pixHeight-2);
		}
		Texture pixmaptex = new Texture(pixmap);
		TextureRegion pix_temp = new TextureRegion(pixmaptex,
				Configure.map_box_value, Configure.map_box_value);
		pixmap.dispose();
		return pix_temp;
	}

	public int get_role(int currentHp, int maxHp) {
		return Configure.map_box_value * currentHp / maxHp;
	}
}
