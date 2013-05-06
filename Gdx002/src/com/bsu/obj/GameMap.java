package com.bsu.obj;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;

public class GameMap {
	public static TiledMap map;
	TileAtlas atlas;
	public TileMapRenderer map_render;
	public OrthographicCamera cam;

	public GameMap() {
		// TODO Auto-generated constructor stub
		map = TiledLoader.createMap(Gdx.files.internal("data/map/marioMap.tmx"));

		atlas = new TileAtlas(map, Gdx.files.internal("data/map/"));

		map_render = new TileMapRenderer(map, atlas, 10, 10);
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 320, 480);

	}

}
