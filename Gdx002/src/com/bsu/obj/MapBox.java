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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Role.Type;
import com.bsu.tools.Configure;

/**
 * 地图上所有带属性的块
 * 
 * @author fengchong
 * 
 */
public class MapBox extends Actor {

	private TextureRegion map_pass; // 人物移动显示的图像
	private TextureRegion map_block; // 地图上障碍图像

	public static Array<Vector2> pass_array = new Array<Vector2>(); // 人物可以移动的格子数组
	public static Array<Vector2> block_array = new Array<Vector2>(); // 地图上障碍格子数组
	public static Array<Vector2> box_array = new Array<Vector2>(); // 宝箱格子
	public static Array<Vector2> hpRaise_array = new Array<Vector2>(); // 加血格子
	public static Array<Vector2> enemy_array = new Array<Vector2>(); // 地图上所有NPC所在位置的格子数组
	public static Array<Vector2> hero_array = new Array<Vector2>(); // 角色人物所在格子数组

	private static int raw_max = 7; // 可以移动的最高格子数，应该定义在MAP TXM文件里，以下同
	private static int raw_min = 3; // 可以移动的最低格子数，靠近屏幕下方
	private static int coll_max = 14; // 可以移动的最远格子数，屏幕右侧
	private static int coll_min = 0; // 可以移动的最左格子数

	public static enum BOX {
		PASS, BLOCK, EVENT
	};

	/**
	 * 地图格子类，处理地图上所有格子，包括动态的Role所在格子，障碍格子等
	 */
	public MapBox() {
		// TODO Auto-generated constructor stub
		draw_map_box(BOX.PASS);
		draw_map_box(BOX.BLOCK);
		pass_array = new Array<Vector2>();
		get_box_value();
	}

	/**
	 * 根据角色及NPC判断，生成PASS数组，2层循环先处理纵向
	 * 
	 * @param hero
	 *            角色方当前被选定操作角色
	 * @param enemy
	 *            NPC角色，理论应该为NPC数组集合，目前为单体。
	 */
	public	static void set_hero_pass_box(Role hero,Array<Actor> enemyArray,Array<Actor> heroArray) {
		pass_array.clear();
		 enemy_array = get_role_block(enemyArray, enemy_array);
		 hero_array = get_role_block(heroArray, hero_array);
		int hero_coll = hero.getBoxX();
		int hero_raw = hero.getBoxY();
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

	/**
	 * 根据行数列数判断是否可以移动，可以则加入到PASS数组，里面循环处理横向
	 * 
	 * @param index
	 *            格子行数
	 * @param coll
	 *            格子列数
	 */
	private static void set_h(int index, int coll) {

		for (int i = 0; i < 5; i++) {
			if (i + coll - 2 >= coll_min) {
				int temp_index = MathUtils.clamp(i + coll - 2, coll_min,
						coll_max);
				if (blocked(index, temp_index)) {
					break;
				}
				pass_array.add(new Vector2(temp_index, index));
				if (temp_index >= coll_max) {
					break;
				}
			}
		}
	}

	/**
	 * draw方法，一个是绘画人物选定可以移动的图像 一个是绘画障碍物
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub

		for (int i = 0; i < pass_array.size; i++) {
			batch.draw(map_pass, pass_array.get(i).x * Configure.map_box_value,
					(pass_array.get(i).y) * Configure.map_box_value); // ����
		}
		for (int i = 0; i < block_array.size; i++) {
			batch.draw(map_block, block_array.get(i).x
					* Configure.map_box_value, (block_array.get(i).y)
					* Configure.map_box_value);
		}
	}

	/**
	 * 绘画可以移动图像，障碍图像（障碍图像以后应该要使用图片）
	 * 
	 * @param box
	 *            所属类型，pass 或者 block
	 */
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
				Format.RGBA8888);
		pixmap.setColor(Color.BLACK);
		pixmap.drawRectangle(0, 0, Configure.map_box_value,
				Configure.map_box_value);
		pixmap.setColor(temp_c);
		pixmap.fillRectangle(1, 1, Configure.map_box_value - 2,
				Configure.map_box_value - 2);
		Texture pixmaptex = new Texture(pixmap);
		temp_box = new TextureRegion(pixmaptex, Configure.map_box_value,
				Configure.map_box_value);
		if (box == BOX.PASS) {
			map_pass = temp_box;
		} else {
			map_block = temp_box;
		}
		pixmap.dispose();
	}

	/**
	 * 取得地图图层中对象元素，并返回给目标数组
	 * 
	 * @return
	 */
	private void get_box_value() {
		for (TiledObjectGroup group : GameMap.map.objectGroups) {
			for (TiledObject object : group.objects) {
				if (object.type == null)
					continue;
				int x = (object.x) / Configure.map_box_value;
				int y = (GameMap.map_render.getMapHeightUnits()
						- Configure.map_box_value - object.y)
						/ Configure.map_box_value;
				Vector2 v = new Vector2(x, y);
				if (object.type.equals(Configure.map_type_block))
					block_array.add(v);
				else if (object.type.equals(Configure.map_type_box))
					box_array.add(v);
				else if (object.type.equals(Configure.map_type_hp_rarise))
					hpRaise_array.add(v);
			}
		}
	}

	/**
	 * 根据角色方位置，返回角色行数列数的格子数组
	 * 
	 * @param role
	 *            需要取得位置的角色
	 * @param mbv
	 *            角色格子数组集合
	 * @return
	 */
	public static Array<Vector2> get_role_block(Array<Actor> role, Array<Vector2> mbv) {
		if (mbv == null) {
			mbv = new Array<Vector2>();
		}
		mbv.clear();
		for (int i = 0; i < role.size; i++) {
			Role r = (Role) role.get(i);
			int role_coll = r.getBoxX();
			int role_raw = r.getBoxY();
			mbv.add(new Vector2(role_coll, role_raw));
			r = null;
		}
		return mbv;
	}
/*
	private Array<Vector2> get_role_block(Role role, Array<Vector2> mbv) {
		if (mbv == null) {
			mbv = new Array<Vector2>();
		}
		mbv.clear();
		int role_coll = role.getBoxX();
		int role_raw = role.getBoxY();
		mbv.add(new Vector2(role_coll, role_raw));
		return mbv;
	}
/*
	/**
	 * 传入行数，列数，与BLOCK数组，ENEMY数组进行，判断此格子是否可以移动，此方法仅使用角色方，需调用多次
	 * 
	 * @param raw
	 * @param coll
	 * @return
	 */
	private static boolean blocked(int raw, int coll) {
		for (int i = 0; i < block_array.size; i++) {
			if (raw == block_array.get(i).y) {
				if (coll >= block_array.get(i).x) {
					return true;
				}
			}
		}
		for (int i = 0; i < enemy_array.size; i++) {
			if (raw == enemy_array.get(i).y) {
				if (coll >= enemy_array.get(i).x) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断角色是否可以移动，其中根据类型需要与BLOCK数组，ENEMY数组，HERO数组来进行多次判断
	 * 
	 * @param r
	 *            需要移动的角色
	 * @return
	 */
	public static boolean blocked(Role r) {
		int raw = r.getBoxY();
		int coll = r.getBoxX();

		if (r.getType() == Type.HERO) {
			for (int i = 0; i < block_array.size; i++) {
				if (raw == block_array.get(i).y) {
					if (coll >= block_array.get(i).x - 1) {
						return true;
					}
				}
			}
			for (int i = 0; i < enemy_array.size; i++) {
				if (raw == enemy_array.get(i).y) {
					if (coll >= enemy_array.get(i).x - 1) {
						return true;
					}
				}
			}
		} else {
			for (int i = 0; i < block_array.size; i++) {
				if (raw == block_array.get(i).y) {
					if (coll <= block_array.get(i).x + 1) {
						return true;
					}
				}
			}
			for (int i = 0; i < hero_array.size; i++) {
				if (raw == hero_array.get(i).y) {

					if (coll <= hero_array.get(i).x + 1) {

						return true;
					}
				}
			}
		}
		return false;
	}
}
