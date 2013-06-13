package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.bsu.effect.RoleEffect;
import com.bsu.head.CubocScreen;
import com.bsu.make.TipsWindows;
import com.bsu.make.WidgetFactory;
import com.bsu.make.GameScreenConfigure;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.tools.CG;
import com.bsu.tools.MessageObject;
import com.bsu.tools.U;
import com.bsu.tools.CG.QUALITY;
import com.bsu.tools.GTC;

public class SelectRoleScreen extends CubocScreen implements Observer,
		GestureListener {

	private Stage stage;
	private Stage sRoleStage;
	private Image background;
	private Image ib_back;
	private Image allImg;
	private Image greenImg;
	private Image blueImg;
	private Image purpleImg;
	private Image orangeImg;
	private Role changeRole;
	private Role selectRole;
	private Array<Image> bImg = new Array<Image>();
	private QUALITY quality;// 当前选择显示的品质

	public SelectRoleScreen(Game game) {
		super(game);
		stage = new Stage(CG.rect_width, CG.rect_height, false);
		sRoleStage = new Stage(CG.rect_width, CG.rect_height, false);
		background = new Image(GTC.getInstance().selectRolePanel);
		stage.addActor(background);
		ib_back = WidgetFactory.getInstance().makeImageButton(CG.button_back,
				stage, 375, 266, 1f);
		allImg = WidgetFactory.getInstance().makeImageButton(CG.button_all,
				stage, 100, 20, 0.5f);
		greenImg = WidgetFactory.getInstance().makeImageButton(CG.button_green,
				stage, 163, 20, 0.5f);
		blueImg = WidgetFactory.getInstance().makeImageButton(CG.button_blue,
				stage, 226, 20, 0.5f);
		purpleImg = WidgetFactory.getInstance().makeImageButton(
				CG.button_purple, stage, 289, 20, 0.5f);
		orangeImg = WidgetFactory.getInstance().makeImageButton(
				CG.button_orange, stage, 352, 20, 0.5f);
		addRoleToStage(QUALITY.all);
		bImg.add(allImg);
		bImg.add(greenImg);
		bImg.add(blueImg);
		bImg.add(purpleImg);
		bImg.add(orangeImg);
		setListener();
	}

	/**
	 * 设置要更改队伍的角色
	 * 
	 * @param r
	 */
	public void setChangeRole(Role r) {
		changeRole = r;
	}

	/**
	 * 当点击卡片按钮时添加背包中卡片到舞台，并根据当前所选类型显示
	 */
	private void addRoleToStage(QUALITY q) {
		if(quality==q){
			return;
		}
		selectRole = null;
		Image simg = null;
		quality=q;
		if (q == QUALITY.green) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerIdelRole, QUALITY.green));
			simg = greenImg;
		}
		if (q == QUALITY.blue) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerIdelRole, QUALITY.blue));
			simg = blueImg;
		}
		if (q == QUALITY.purple) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerIdelRole, QUALITY.purple));
			simg = purpleImg;
		}
		if (q == QUALITY.orange) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerIdelRole, QUALITY.orange));
			simg = orangeImg;
		}
		if (q == QUALITY.all) {
			showQualityRole(Player.getInstance().playerIdelRole);
			simg = allImg;
		}
		U.setSelectImg(bImg, simg);
	}

	/**
	 * 显示某一品质的role
	 * 
	 * @param imgArray
	 *            某一品质的role Image数组
	 */
	private void showQualityRole(final Array<Role> roleArray) {
		sRoleStage.clear();
		int frlength = roleArray.size;
		int x = 40;
		int y = 200;
		int w = 68;
		int value=6;
		int max=18;
		for (int i = 0; i < roleArray.size; i++) {
			final Role r = roleArray.get(i);
			final Vector2 v = new Vector2(x + i % value * w, y - i / value* w);
			final RoleEffect photo = new RoleEffect(r, false);
			photo.setPosition(v.x, v.y);
			sRoleStage.addActor(photo);
			r.roleEffect=photo;
			photo.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					if (selectRole != r) {
						selectRole = r;// 选定人物。。
						TipsWindows.getInstance().showRoleInfo(r, v,
								sRoleStage);
						resetRole(r);
						return;
					}
					if (changeRole != null) {
						int index = 0;
						for (int i = 0; i < Player.getInstance().playerFightRole.size; i++) {
							if (Player.getInstance().playerFightRole.get(i) == changeRole) {
								index = i;
								break;
							}
						}
						Player.getInstance().playerFightRole.removeValue(
								changeRole, false);
						Player.getInstance().addRoleToFIght(r, index);
					} else {
						Player.getInstance().addRoleToFIght(r, 100);
					}
					Player.getInstance().getPlayerPackageRole();
					GameScreenConfigure.getInstance().setHeroRoles(
							Player.getInstance().playerFightRole);
					setChanged();
					notifyObservers(CG.button_back);
					ib_back.setScale(1f);
					super.touchUp(event, x, y, pointer, button);
				}
			});
		}
		for (int i = frlength; i < max; i++) {
			Vector2 v = new Vector2(x + i % value * w, y - i / value * w);
			Image img_frame = GTC.getInstance().getImageFrame(null);
			img_frame.setPosition(v.x, v.y);
			sRoleStage.addActor(img_frame);
			img_frame.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					setChanged();
					notifyObservers(new MessageObject(null,
							CG.screen_selectRole));
					return super.touchDown(event, x, y, pointer, button);
				}
			});
		}
	}

	/**
	 * 设置被选中的角色
	 * 
	 * @param r
	 */
	private void resetRole(Role r) {
		for (Role e : Player.getInstance().playerIdelRole) {
			e.roleEffect.showEffect(false);
		}
		for (Actor act : sRoleStage.getActors()) {
			if (act instanceof RoleEffect) {
				if (act.equals(r.roleEffect)) {
					r.roleEffect.showEffect(true);
				}
			}
		}
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(null);
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(sRoleStage);// 必须先加这个。。。。
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(new GestureDetector(this));
		Gdx.input.setInputProcessor(inputMultiplexer);
		addRoleToStage(QUALITY.all);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		sRoleStage.act(Gdx.graphics.getDeltaTime());
		sRoleStage.draw();

	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void update(Observable o, Object arg) {
	}

	private void setListener() {
		ib_back.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				setChanged();
				notifyObservers(CG.button_back);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		purpleImg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QUALITY.purple);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		orangeImg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QUALITY.orange);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		blueImg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QUALITY.blue);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		greenImg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QUALITY.green);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		allImg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QUALITY.all);
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}
}
