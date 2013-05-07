package com.bsu.obj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.bsu.tools.Configure;


public class GameMap {
	public static TiledMap map;
	TileAtlas atlas;
	public TileMapRenderer map_render;
	public OrthographicCamera cam;

	public GameMap(int level) {
		// TODO Auto-generated constructor stub
		get_map(level);
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Configure.rect_width, Configure.rect_height);
	}
	
	public void get_map(int level){
		map = TiledLoader.createMap(Gdx.files.internal("data/map/"+Configure.game_map_path_string[level]+".tmx"));
		atlas = new TileAtlas(map, Gdx.files.internal("data/map/"));
		map_render = new TileMapRenderer(map, atlas, 10, 10);
	}
}
