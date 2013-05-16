package com.bsu.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;


public class GameMap {
	public static TiledMap map;										
	private static TileAtlas atlas;						//
	public static TileMapRenderer map_render; 			//
  /**
   * 
   * @param level 关卡索引，根据索引加载不同地图
   */
//	public GameMap(int level) {
//		// TODO Auto-generated constructor stub
//		get_map(level);
//	}
	/**
	 * 
	 * @param level 关卡索引，此方法不使用packfield
	 */
	public static void make_map(int level){
		FileHandle mapHandle = Gdx.files.internal("data/map/"+Configure.game_map_path_string[level]+".tmx");
		map = TiledLoader.createMap(mapHandle);
		FileHandle packages=Gdx.files.internal("data/map");   
        atlas = new CustomerTiledAlisa(map, packages); 
		map_render = new TileMapRenderer(map, atlas, 10, 10);
	}
}
