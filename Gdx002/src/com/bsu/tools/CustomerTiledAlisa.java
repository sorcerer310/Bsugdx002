package com.bsu.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileSet;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;

public class CustomerTiledAlisa extends TileAtlas {

	public CustomerTiledAlisa() {
		// TODO Auto-generated constructor stub
	}

	public CustomerTiledAlisa(TiledMap map, FileHandle inputDir) {
		// TODO Auto-generated constructor stub
		for (TileSet set : map.tileSets) {
			if (set.imageName == null) {
				continue;
			}
			FileHandle imageFile = getRelativeFileHandle(inputDir,
					(set.imageName));
			List<AtlasRegion> atlasRegions = new ArrayList<AtlasRegion>();
			Texture texture = new Texture(imageFile);
			for (int j = 0; j < texture.getHeight() / set.tileHeight; j++) {
				int wn = texture.getWidth() / set.tileWidth;
				for (int i = 0; i < wn; i++) {
					AtlasRegion atlasRegion = new AtlasRegion(texture, i
							* set.tileWidth, j * set.tileHeight, set.tileWidth,
							set.tileHeight);
					atlasRegion.index = j * wn + i;
					atlasRegions.add(atlasRegion);
				}
			}
			for (AtlasRegion reg : atlasRegions) {
				regionsMap.put(reg.index + set.firstgid, reg);
				if (!textures.contains(reg.getTexture())) {
					textures.add(reg.getTexture());
				}
			}
		}
	}

	private static FileHandle getRelativeFileHandle(FileHandle path,
			String relativePath) {
		if (relativePath.trim().length() == 0) {
			return path;
		}

		FileHandle child = path;

		StringTokenizer tokenizer = new StringTokenizer(relativePath, "\\/");
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (token.equals("..")) {
				child = child.parent();
			} else {
				child = child.child(token);
			}
		}

		return child;
	}

}
