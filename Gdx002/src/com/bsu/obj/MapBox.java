package com.bsu.obj;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.bsu.tools.Configure;

public class MapBox extends Actor {

	private TextureRegion map_pass;
	private TextureRegion map_block;

	public static Array<MapBoxValue> pass_array;

	private int raw_max = 10;//底部最大值
	private int raw_min = 6;//顶部最大值
	private int coll_max = 9;//右方最大值
	private int coll_min = 0;//左方可以移动的最小值

	public static enum BOX {
		PASS, BLOCK
	};

	// 处理地图显示效果，是否可以移动，不可移动
	public MapBox() {
		// TODO Auto-generated constructor stub
		draw_map_box(BOX.PASS);
		draw_map_box(BOX.BLOCK);
		pass_array = new Array<MapBoxValue>();
	}

	public void set_pass_box(Role hero, Role enemy) {
		pass_array.clear();
		int hero_coll = (int) ((hero.getX() + 10) / Configure.map_box_value);
		int hero_raw = (int) ((GameMap.map_render.getMapHeightUnits()
				- hero.getY() + 10 - Configure.map_box_value) / Configure.map_box_value);
		for (int i = 0; i < 5; i++) {
			if (i + hero_raw - 2 >= raw_min) {
				int temp_index = MathUtils.clamp(i + hero_raw - 2, raw_min,
						raw_max);
				set_h(temp_index, hero_coll, enemy);
				if (temp_index >= raw_max) {
					break;
				}
			}
		}
	}

	private void set_h(int index, int coll, Role enemy) {
		int enemy_coll = (int) ((enemy.getX() + 10) / Configure.map_box_value);
		int enemy_raw = (int) ((GameMap.map_render.getMapHeightUnits()
				- enemy.getY() + 10 - Configure.map_box_value) / Configure.map_box_value);
		for (int i = 0; i < 5; i++) {
			if (i + coll - 2 >= coll_min) {
				int temp_index = MathUtils.clamp(i + coll - 2, coll_min, coll_max);
				if(enemy_raw==index){
					if(temp_index>=enemy_coll){
						break;
					}
				}
				pass_array.add(new MapBoxValue( temp_index ,GameMap.map_render.getMapHeightUnits() / 32 -index-1));
				if (temp_index >= coll_max) {
					break;
				}
			}
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub

		for (int i = 0; i < pass_array.size; i++) {
			batch.draw(map_pass, pass_array.get(i).getColl() * Configure.map_box_value,
					(pass_array.get(i).getRaw()) * Configure.map_box_value); // 绘制
		}
	}

	private void draw_map_box(BOX box) {
		TextureRegion temp_box = null;
		Color temp_c;
		if (box == BOX.PASS) {
			temp_c = Color.GREEN;
		} else {
			temp_c = Color.RED;
		}
		temp_c.a = 0.8f;
		Pixmap pixmap;
		pixmap = new Pixmap(Configure.map_box_value, Configure.map_box_value, Format.RGBA8888); // 生成一张64*8的图片
		pixmap.setColor(Color.BLACK); // 设置颜色
		pixmap.drawRectangle(0, 0, Configure.map_box_value, Configure.map_box_value);
		pixmap.setColor(temp_c); // 设置颜色
		pixmap.fillRectangle(1, 1, Configure.map_box_value - 2,Configure. map_box_value - 2);
		Texture pixmaptex = new Texture(pixmap); // 生成图片
		temp_box = new TextureRegion(pixmaptex, Configure.map_box_value, Configure.map_box_value); // 切割图片
		if (box == BOX.PASS) {
			map_pass = temp_box;
		} else {
			map_block = temp_box;
		}
		pixmap.dispose();
	}
}
