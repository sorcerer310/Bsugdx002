package com.bsu.effect;

import java.util.Random;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bsu.tools.U;

/**
 * 用于实现一些特效动画
 * 
 * @author fengchong
 * 
 */
public class EffectTool extends Actor {

	private static Camera camera = null; // 要操作的摄像机对象
	private static int currShockCount = 0; // 当前震动次数
	private static int shockCount = 10; // 震动次数
	private static boolean startShock = false; // 开始震动
	private static Vector2 cameraSourcePos = new Vector2();
	public static void shockScreen(Stage s) {
		camera = s.getCamera();
		cameraSourcePos.x = camera.position.x;
		cameraSourcePos.y = camera.position.y;
		// shockCount = 10;
		currShockCount = 0;
		startShock = true;
	}

	private float sumdelta = 0;
	private Random rnd = new Random();
	@Override
	public void act(float delta) {
		sumdelta += delta;
		if (sumdelta >= 0.05f) {

			if (startShock && camera != null) {
				camera.position.x += (rnd.nextInt(16)-8);
				camera.position.y += (rnd.nextInt(16)-8);
				currShockCount++;
				if(currShockCount>=shockCount){
					startShock = false;
					camera.position.x = cameraSourcePos.x;
					camera.position.y = cameraSourcePos.y;
				}
			}
			sumdelta = 0;
		}
	}

}
