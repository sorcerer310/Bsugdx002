package com.bsu.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class RoleHP {
	int pixWidth = 5; // 血条宽
	int pixHeight=48;
	public RoleHP() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * s 设置人物生命血条
	 */
	public Image get_hp_image() {
		Pixmap pixmap;
		pixmap = new Pixmap(8, 8, Format.RGBA8888); // 生成一张64*8的图片

		pixmap.setColor(Color.BLACK); // 设置颜色为黑色。
		pixmap.drawRectangle(0, 0, pixWidth, pixHeight);

		pixmap.setColor(Color.RED); // 设置颜色为黑色。
		pixmap.fillRectangle(1, 1, pixWidth - 2, pixHeight - 2);

		Texture pixmaptex = new Texture(pixmap);
		TextureRegion pix_temp = new TextureRegion(pixmaptex,
				pixWidth, pixHeight);
		pixmap.dispose();
		Image hpImage = new Image(pix_temp);
		return hpImage;
	}
}
