package com.bsu.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;


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
	public static void make_map(String mapName){
		FileHandle mapHandle = Gdx.files.internal("data/map/output/"+mapName+".tmx");
		map = TiledLoader.createMap(mapHandle);
		FileHandle packages=Gdx.files.internal("data/map");   
        atlas = new CTA(map, packages); 
		map_render = new TileMapRenderer(map, atlas, 10, 10);
	}
}
