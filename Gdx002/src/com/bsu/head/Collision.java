package com.bsu.head;

import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.bsu.obj.GameMap;

public class Collision {

	public Collision() {
		// TODO Auto-generated constructor stub
	}

	public static int tile[][];
	public static TiledMap map;

	static {
		map = GameMap.map;
		tile = map.layers.get(0).tiles;
	}

	public static boolean leftEnable(float x, float y, float width, float height) {
		int m1 = MathUtils.ceilPositive((x+2) / map.tileWidth); //
		int n1 = tile.length - MathUtils.ceilPositive((y+2) / map.tileHeight);

		int m7 = MathUtils.ceilPositive((x+2) / map.tileWidth);
		int n7 = tile.length
				- MathUtils.ceilPositive((y+2) / map.tileHeight);

		int m8 = MathUtils.ceilPositive((x+2) / map.tileWidth);
		int n8 = tile.length
				- MathUtils.ceilPositive((y+2) / map.tileHeight);

		if (tile[n1][m1 - 1] != 0 || tile[n7][m7 - 1] != 0
				|| tile[n8][m8 - 1] != 0) {
			return false;
		} else
			return true;
	}
	public static boolean rightEnable(float x, float y, float width, float height) {
		int m4 = MathUtils.ceilPositive((x+width-2) / map.tileWidth); //
		int n4 = tile.length - MathUtils.ceilPositive((y+2) / map.tileHeight);

		int m5 = MathUtils.ceilPositive((x+width-2) / map.tileWidth);
		int n5 = tile.length
				- MathUtils.ceilPositive((y+2) / map.tileHeight);

		int m6 = MathUtils.ceilPositive((x+width-2) / map.tileWidth);
		int n6 = tile.length
				- MathUtils.ceilPositive((y+2) / map.tileHeight);

		if (tile[n4][m4 - 1] != 0 || tile[n5][m5 - 1] != 0
				|| tile[n6][m6 - 1] != 0) {
			return false;
		} else
			return true;
	}
	public static boolean downEnable(float x, float y) {

		int m1 = MathUtils.ceilPositive((x + 12) / map.tileWidth);
		int n1 = tile.length - MathUtils.ceilPositive((y - 1) / map.tileHeight);

		int m2 = MathUtils.ceilPositive((x + 24) / map.tileWidth);
		int n2 = tile.length - MathUtils.ceilPositive((y - 1) / map.tileHeight);

		int m3 = MathUtils.ceilPositive((x + 40) / map.tileWidth);
		int n3 = tile.length - MathUtils.ceilPositive((y - 1) / map.tileHeight);

		if (tile[n3][m3 - 1] != 0 || tile[n2][m2 - 1] != 0
				|| tile[n1][m1 - 1] != 0) {
			return false;
		} else {
			return true;
		}
	}

}
