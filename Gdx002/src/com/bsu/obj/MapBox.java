package com.bsu.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.bsu.make.WidgetFactory;
import com.bsu.tools.GC;
import com.bsu.tools.GAC;
import com.bsu.tools.GTC;
import com.bsu.tools.GameMap;

/**
 * 地图上所有带属性的块
 * 
 * @author fengchong
 * 
 */
public class MapBox extends Actor {
	private TextureRegion map_block; // 地图上障碍图像
	private Animation ani_map_pass;	//人物可移动显示的动画
	private TextureRegion current_map_pass_frame;					//可通过地点当前帧
	private TextureRegion map_attack;// 人物攻击显示的图像
	private TextureRegion current_treasure_frame; // 宝物动画当前帧
	
	private float stateTime = .0f;

	public static Array<Vector2> pass_array = new Array<Vector2>();// 选择人物后，显示地图上可移动范围
	public static Array<Vector2> attack_array = new Array<Vector2>();// 选择人物后，显示人物可攻击范围
	private Array<TiledObject> block_array = new Array<TiledObject>(); // 地图上障碍格子数组
	public Array<TiledObject> box_array = new Array<TiledObject>(); // 宝箱格子
	public Array<TiledObject> hpRaise_array = new Array<TiledObject>(); // 加血格子
	public Array<Vector2> enemy_array = new Array<Vector2>(); // 地图上所有NPC所在位置的格子数组
	public Array<Vector2> hero_array = new Array<Vector2>(); // 角色人物所在格子数组

	private static int raw_max = 8; // 可以移动的最高格子数，应该定义在MAP TXM文件里，以下同
	private static int raw_min = 4; // 可以移动的最低格子数，靠近屏幕下方
	private static int coll_max = 13; // 可以移动的最远格子数，屏幕右侧
	private static int coll_min = 1; // 可以移动的最左格子数

	public static enum BOX {
		PASS, BLOCK, EVENT, ATTACK
	};
	Image imap_pass = null;
	/**
	 * 地图格子类，处理地图上所有格子，包括动态的Role所在格子，障碍格子等
	 */
	public MapBox() {
		ani_map_pass = new Animation(0.2f,new TextureRegion[]{GTC.getInstance().frame_mapbox1,GTC.getInstance().frame_mapbox2,GTC.getInstance().frame_mapbox3,
				GTC.getInstance().frame_mapbox4,GTC.getInstance().frame_mapbox5,GTC.getInstance().frame_mapbox4,GTC.getInstance().frame_mapbox3,
				GTC.getInstance().frame_mapbox2,GTC.getInstance().frame_mapbox1});
		ani_map_pass.setPlayMode(ani_map_pass.LOOP);
		stateTime = .0f;
		
		map_block = WidgetFactory.getInstance().getPixmapFrame(GC.map_box_value - 2, GC.map_box_value - 2, Color.BLACK,Color.RED, 0.5f);
		map_attack = WidgetFactory.getInstance().getPixmapFrame(GC.map_box_value - 2, GC.map_box_value - 2, Color.BLACK,Color.BLUE, 0.5f);
		current_treasure_frame = GTC.getInstance().skills_effect.findRegion("box", 1);
	}

	/**
	 * 根据角色及NPC判断，生成PASS数组，2层循环先处理纵向 
	 * @param hero            角色方当前被选定操作角色
	 * @param enemy         NPC角色，理论应该为NPC数组集合，目前为单体。
	 */
	public Array<Vector2> get_hero_pass_box(Role hero, Array<Role> enemyArray) {
		stateTime=0;
		pass_array.clear();
		enemy_array = get_role_block(enemyArray, enemy_array);
		int hero_coll = hero.getBoxX();
		int hero_raw = hero.getBoxY();
		for (int i = 0; i < 5; i++) {
			if (i + hero_raw - 2 >= raw_min) {
				int temp_index = MathUtils.clamp(i + hero_raw - 2, raw_min,	raw_max);
				set_h(temp_index, hero_coll, pass_array);
				if (temp_index >= raw_max) {
					break;
				}
			}
		}
		return pass_array;
	}

	/**
	 * 根据行数列数判断是否可以移动，可以则加入到PASS数组，里面循环处理横向 
	 * @param index   		         格子行数
	 * @param coll		    	     格子列数
	 */
	private void set_h(int index, int coll, Array<Vector2> array) {

		for (int i = 0; i < 5; i++) {
			if (i + coll - 2 >= coll_min) {
				int temp_index = MathUtils.clamp(i + coll - 2, coll_min,
						coll_max);
				if (blocked(index, temp_index)) {
					break;
				}
				array.add(new Vector2(temp_index, index));
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
//		stateTime += Gdx.graphics.getDeltaTime();
		// 绘制宝箱
		for (int i = 0; i < box_array.size; i++) {
			if(box_array.get(i).type.equals("show")){
				batch.draw(current_treasure_frame, box_array.get(i).x,GameMap.map_render.getMapHeightUnits()-GC.map_box_value-box_array.get(i).y);
			}
		}
		// 障碍格绘制
		for (int i = 0; i < block_array.size; i++) {
			batch.draw(map_block,block_array.get(i).x,GameMap.map_render.getMapHeightUnits()-GC.map_box_value-block_array.get(i).y);
			}
		
//		drawMapPassBox(stateTime);
		// 通过格绘制
		for (int i = 0; i < pass_array.size; i++) {
			if(current_map_pass_frame!=null){
//				System.out.println("current_map_pass_frame is not null");
				batch.draw(current_map_pass_frame, pass_array.get(i).x * GC.map_box_value,(pass_array.get(i).y) * GC.map_box_value);
			}
		}
//		for(int i=0;i<pass_array.size;i++)
//			batch.draw(GTC.getInstance().frame_mapbox1, pass_array.get(i).x * GC.map_box_value,(pass_array.get(i).y) * GC.map_box_value);

		
		// 攻击范围绘制
		for (int i = 0; i < attack_array.size; i++) {
			batch.draw(map_attack, attack_array.get(i).x * GC.map_box_value,(attack_array.get(i).y) * GC.map_box_value);
		}
	}
	/**
	 * 取得地图图层中对象元素，并返回给目标数组
	 * @return
	 */
	public void init_box_value() {
		for (TiledObjectGroup group : GameMap.map.objectGroups) {
			if (group.name.equals("box")) {
				// 当object组为box组时遍历改组的所有对象
				for (TiledObject object : group.objects)
					box_array.add(object);
			} else {
				for (TiledObject object : group.objects) {
					if (object.type == null)
						continue;
					if (object.type.equals(GC.map_type_block))
						block_array.add(object);
					else if (object.type.equals(GC.map_type_hp_rarise))
						hpRaise_array.add(object);
					else if (object.type.equals("show")) {
						box_array.add(object);
					}
				}
			}
		}
	}

	/**
	 * 根据角色方位置，返回角色行数列数的格子数组
	 * @param role            需要取得位置的角色
	 * @param mbv           角色格子数组集合
	 * @return
	 */
	public static Array<Vector2> get_role_block(Array<Role> role,
			Array<Vector2> mbv) {
		if (mbv == null) {
			mbv = new Array<Vector2>();
		}
		mbv.clear();
		for (int i = 0; i < role.size; i++) {
			Role r = (Role) role.get(i);
			if (!r.isDead) {
				int role_coll = r.getBoxX();
				int role_raw = r.getBoxY();
				mbv.add(new Vector2(role_coll, role_raw));
			}
			r = null;
		}
		return mbv;
	}

	/**
	 * 判断hero角色是否可以向前移动
	 * @param raw
	 * @param coll
	 * @return
	 */
	private boolean blocked(int raw, int coll) {
		for (int i = 0; i < enemy_array.size; i++) {
			if (raw == enemy_array.get(i).y) {
				if (coll >= enemy_array.get(i).x) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		stateTime += Gdx.graphics.getDeltaTime();
		current_map_pass_frame = this.ani_map_pass.getKeyFrame(stateTime,true);
		super.act(delta);
	}

}
