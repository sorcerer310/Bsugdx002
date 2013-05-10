package com.bsu.obj;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Role.Type;
import com.bsu.tools.Configure;

public class MapBox extends Actor {

	private TextureRegion map_pass;
	private TextureRegion map_block;

	public static Array<MapBoxValue> pass_array;
	public static Array<MapBoxValue> block_array;
	public static Array<MapBoxValue> enemy_array;
	public static Array<MapBoxValue> hero_array;

	private int raw_max = 7;// 顶部最大值
	private int raw_min = 3;// 顶部最小值
	private int coll_max = 14;// 右方最大值
	private int coll_min = 0;// 左方可以移动的最小值
	private static int extra_value = 10;// 判断位置时额外增加的距离，为了防止判断失误。

	public static enum BOX {
		PASS, BLOCK
	};

	// 处理地图显示效果，是否可以移动，不可移动
	public MapBox() {
		// TODO Auto-generated constructor stub
		draw_map_box(BOX.PASS);
		draw_map_box(BOX.BLOCK);
		pass_array = new Array<MapBoxValue>();
		block_array = this.get_box_value(Configure.map_type_block, block_array);
	}

	public void set_hero_pass_box(Role hero, Role enemy) {
		pass_array.clear();
		enemy_array = this.get_role_block(enemy, enemy_array);
		hero_array = this.get_role_block(hero, hero_array);
		int hero_coll = (int) ((hero.getX() + extra_value) / Configure.map_box_value);
		int hero_raw = (int) ((hero.getY() + extra_value) / Configure.map_box_value);
		for (int i = 0; i < 5; i++) {
			if (i + hero_raw - 2 >= raw_min) {
				int temp_index = MathUtils.clamp(i + hero_raw - 2, raw_min,
						raw_max);
				set_h(temp_index, hero_coll);
				if (temp_index >= raw_max) {
					break;
				}
			}
		}
	}

	private void set_h(int index, int coll) {

		for (int i = 0; i < 5; i++) {
			if (i + coll - 2 >= coll_min) {
				int temp_index = MathUtils.clamp(i + coll - 2, coll_min,
						coll_max);
				if (blocked(index, temp_index)) {
					break;
				}
				pass_array.add(new MapBoxValue(temp_index, index));
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
			batch.draw(map_pass, pass_array.get(i).getColl()
					* Configure.map_box_value, (pass_array.get(i).getRaw())
					* Configure.map_box_value); // 绘制
		}
		for (int i = 0; i < block_array.size; i++) {
			batch.draw(map_block, block_array.get(i).getColl()
					* Configure.map_box_value, (block_array.get(i).getRaw())
					* Configure.map_box_value);
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
		pixmap = new Pixmap(Configure.map_box_value, Configure.map_box_value,
				Format.RGBA8888); // 生成一张64*8的图片
		pixmap.setColor(Color.BLACK); // 设置颜色
		pixmap.drawRectangle(0, 0, Configure.map_box_value,
				Configure.map_box_value);
		pixmap.setColor(temp_c); // 设置颜色
		pixmap.fillRectangle(1, 1, Configure.map_box_value - 2,
				Configure.map_box_value - 2);
		Texture pixmaptex = new Texture(pixmap); // 生成图片
		temp_box = new TextureRegion(pixmaptex, Configure.map_box_value,
				Configure.map_box_value); // 切割图片
		if (box == BOX.PASS) {
			map_pass = temp_box;
		} else {
			map_block = temp_box;
		}
		pixmap.dispose();
	}

	private Array<MapBoxValue> get_box_value(String s, Array<MapBoxValue> mbv) {
		if (mbv == null) {
			mbv = new Array<MapBoxValue>();
		}
		mbv.clear();
		for (TiledObjectGroup group : GameMap.map.objectGroups) {
			for (TiledObject object : group.objects) {
				if (s.equals(object.type)) {
					mbv.add(new MapBoxValue((object.x)
							/ Configure.map_box_value,
							(GameMap.map_render.getMapHeightUnits()
									- Configure.map_box_value - object.y)
									/ Configure.map_box_value));
				}
			}
		}
		return mbv;
	}

	private Array<MapBoxValue> get_role_block(Role role,
			Array<MapBoxValue> mbv) {
		if (mbv == null) {
			mbv = new Array<MapBoxValue>();
		}
		mbv.clear();
		int role_coll = (int) ((role.getX() + extra_value) / Configure.map_box_value);
		int role_raw = (int) ((role.getY() + extra_value) / Configure.map_box_value);
		mbv.add(new MapBoxValue(role_coll, role_raw));
		return mbv;
	}

	private boolean blocked(int raw, int coll) {
		for (int i = 0; i < block_array.size; i++) {
			if (raw == block_array.get(i).getRaw()) {
				if (coll >= block_array.get(i).getColl()) {
					return true;
				}
			}
		}
		for (int i = 0; i < enemy_array.size; i++) {
			if (raw == enemy_array.get(i).getRaw()) {
				if (coll >= enemy_array.get(i).getColl()) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean blocked(Role r) {
		int raw = (int) ((r.getY() + extra_value) / Configure.map_box_value);
		int coll = (int) ((r.getX() + extra_value) / Configure.map_box_value);
	
		if (r.getType() == Type.HERO) {
			for (int i = 0; i < block_array.size; i++) {
				if (raw == block_array.get(i).getRaw()) {
					if (coll >= block_array.get(i).getColl() - 1) {
						return true;
					}
				}
			}
			for (int i = 0; i < enemy_array.size; i++) {
				if (raw == enemy_array.get(i).getRaw()) {
					if (coll >= enemy_array.get(i).getColl() - 1) {
						return true;
					}
				}
			}
		}else{
			for (int i = 0; i < block_array.size; i++) {
				if (raw == block_array.get(i).getRaw()) {
					if (coll <= block_array.get(i).getColl() + 1) {
						return true;
					}
				}
			}
			for (int i = 0; i < hero_array.size; i++) {
				if (raw == hero_array.get(i).getRaw()) {
					
					if (coll <= hero_array.get(i).getColl() + 1) {
						
						return true;
					}
				}
			}
		}
		return false;
	}

}
