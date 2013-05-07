package com.bsu.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bsu.tools.Configure;
import com.bsu.tools.CustomerTiledAlisa;


public class GameMap {
	public static TiledMap map;
	TileAtlas atlas;
	public TileMapRenderer map_render;
	public OrthographicCamera cam;
	Vector3 camDirection = new Vector3(1, 1, 0);   
    Vector2 maxCamPosition = new Vector2(0, 0);  
	public GameMap(int level) {
		// TODO Auto-generated constructor stub
		get_map(level);
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Configure.rect_width, Configure.rect_height);
	}
	
	public void get_map(int level){
		FileHandle mapHandle = Gdx.files.internal("data/map/"+Configure.game_map_path_string[level]+".tmx");
		map = TiledLoader.createMap(mapHandle);
		FileHandle packages=Gdx.files.internal("data/map");   
        atlas = new CustomerTiledAlisa(map, packages); 
		map_render = new TileMapRenderer(map, atlas, 10, 10);
		maxCamPosition.set(map_render.getMapWidthUnits(), map_render .getMapHeightUnits());
	}
}
