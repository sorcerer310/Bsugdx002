package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.bsu.effect.RoleEffect;
import com.bsu.head.CubocScreen;
import com.bsu.make.TipsWindows;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.tools.CG;
import com.bsu.tools.CG.QUALITY;
import com.bsu.tools.GTC;
import com.bsu.tools.MessageObject;
import com.bsu.tools.U;

public class UpdateScreen extends CubocScreen implements Observer,
		GestureListener {
	private Stage stage;
	private Stage sRoleStage;
	private Stage upRoleStage;
	private Image background;
	private Image ib_back;
	private Image allImg;
	private Image greenImg;
	private Image blueImg;
	private Image purpleImg;
	private Image orangeImg;
	private Image eatImg;
	private Image eatAllImg;// 一键吞噬所有某种品质
	private Image upButton;// 升级按钮
	private Role suRole;
	private Array<Role> eatRoles = new Array<Role>();
	private QUALITY quality;// 当前选择显示的品质
	private Array<Image> bImg = new Array<Image>();
	private Label infos;

	public UpdateScreen(Game game) {
		super(game);
		stage = new Stage(CG.rect_width, CG.rect_height, false);
		sRoleStage = new Stage(CG.rect_width, CG.rect_height,
				false);
		upRoleStage = new Stage(CG.rect_width, CG.rect_height,
				false);
		background = new Image(GTC.getInstance().updatePanel);
		stage.addActor(background);
		infos = WidgetFactory.getInstance().makeLabel("", stage, 1, 135, 280);
		upButton = WidgetFactory.getInstance().makeImageButton(
				CG.button_up, stage, 320, 274, 1);
		ib_back = WidgetFactory.getInstance().makeImageButton(
				CG.button_back, stage, 366, 267, 1f);
		allImg = WidgetFactory.getInstance().makeImageButton(
				CG.button_all, stage, 135, 50, 0.5f);
		greenImg = WidgetFactory.getInstance().makeImageButton(
				CG.button_green, stage, 198, 50, 0.5f);
		blueImg = WidgetFactory.getInstance().makeImageButton(
				CG.button_blue, stage, 261, 50, 0.5f);
		purpleImg = WidgetFactory.getInstance().makeImageButton(
				CG.button_purple, stage, 324, 50, 0.5f);
		orangeImg = WidgetFactory.getInstance().makeImageButton(
				CG.button_orange, stage, 387, 50, 0.5f);
		eatImg = WidgetFactory.getInstance().makeImageButton(
				CG.button_eat, stage, 250, 20, 1f);
		eatAllImg = WidgetFactory.getInstance().makeImageButton(
				CG.button_eatall, stage, 350, 15, 1);
		bImg.add(allImg);
		bImg.add(greenImg);
		bImg.add(blueImg);
		bImg.add(purpleImg);
		bImg.add(orangeImg);
		getRoles();
		setListener();
		addRoleToStage(QUALITY.all);
	}
	/**
	 * 显示要强化角色列表
	 */
	private void getRoles() {
		eatRoles.clear();
		upRoleStage.clear();
		final Array<Role> playerRoles = Player.getInstance().playerFightRole;
		for (int i = 0; i < playerRoles.size; i++) {
			final Role r = playerRoles.get(i);
			Vector2 v = new Vector2(48, 246 - 55 * i);
			RoleEffect photo = new RoleEffect(r, false);
			photo.setPosition(v.x, v.y);
			upRoleStage.addActor(photo);
			r.roleEffect=photo;
			photo.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					U.showRoleSelect(playerRoles, r);
					suRole = r;
					return super.touchDown(event, x, y, pointer, button);
				}
			});
		}
		U.showRoleSelect(playerRoles, playerRoles.get(0));
		suRole = playerRoles.get(0);
	}

	/**
	 * 显示要强化角色信息
	 */
	private void showUpRoleInfo() {
		infos.setFontScale(0.7f);
		infos.setText(suRole.name + "  lv:" + suRole.level
				+ "  exp:" + suRole.exp + "/"
				+ suRole.expUp);
		upButton.setColor(upButton.getColor().r, upButton.getColor().g,
				upButton.getColor().b,
				(suRole.exp > suRole.expUp ? 1 : 0f));
		if (suRole.exp >= suRole.expUp) {
			TipsWindows.getInstance().showTips(CG.roleUp, sRoleStage,
					Color.ORANGE);
		}
	}

	/**
	 * 当点击卡片按钮时添加背包中卡片到舞台，并根据当前所选类型显示
	 */
	private void addRoleToStage(QUALITY q) {
		quality = q;
		Image simg = null;
		if (quality == QUALITY.all) {
			showQualityRole(Player.getInstance().playerIdelRole);
			simg = allImg;
		}
		if (quality == QUALITY.green) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerIdelRole, QUALITY.green));
			simg = greenImg;
		}
		if (quality == QUALITY.blue) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerIdelRole, QUALITY.blue));
			simg = blueImg;
		}
		if (quality == QUALITY.purple) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerIdelRole, QUALITY.purple));
			simg = purpleImg;
		}
		if (quality == QUALITY.orange) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerIdelRole, QUALITY.orange));
			simg = orangeImg;
		}
		U.setSelectImg(bImg, simg);
	}

	/**
	 * 显示某一品质的role
	 * 
	 * @param imgArray
	 *            某一品质的role Image数组
	 */
	private void showQualityRole(Array<Role> roleArray) {
		sRoleStage.clear();
		int frlength = roleArray.size;
		int x = 150;
		int y = 210;
		int w = 60;
		for (int i = 0; i < roleArray.size; i++) {
			final Role r = roleArray.get(i);
			final Vector2 v = new Vector2(x + i % 5 * w, y - i / 5 * w);
			RoleEffect photo = new RoleEffect(r,false);
			r.roleEffect = photo;
			photo.setPosition(v.x, v.y);
			sRoleStage.addActor(photo);
			photo.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					resetEatRole(r);
					TipsWindows.getInstance().showEatInfo(r, v, sRoleStage);
					super.touchUp(event, x, y, pointer, button);
				}
			});
		}
		for (int i = frlength; i < 15; i++) {
			Vector2 v = new Vector2(x + i % 5 * w, y - i / 5 * w);
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
		if (roleArray.size < 1) {
			TipsWindows.getInstance().showTips("没有相应品质的卡片，请通关来收集", sRoleStage,
					Color.GRAY);
		}
		showUpRoleInfo();
	}

	/**
	 * 吞噬卡片升级
	 * 
	 * @param flag
	 *            是否一键吞噬
	 */
	private void eatRolesUpdate(boolean flag) {
		if (flag) {
			if (quality == QUALITY.all) {
				eatRoles = Player.getInstance().playerIdelRole;
			}
			if (quality == QUALITY.green) {
				eatRoles = Player.getInstance().getQualityRole(
						Player.getInstance().playerIdelRole, QUALITY.green);
			}
			if (quality == QUALITY.blue) {
				eatRoles = Player.getInstance().getQualityRole(
						Player.getInstance().playerIdelRole, QUALITY.blue);
			}
			if (quality == QUALITY.purple) {
				eatRoles = Player.getInstance().getQualityRole(
						Player.getInstance().playerIdelRole, QUALITY.purple);
			}
			if (quality == QUALITY.orange) {
				eatRoles = Player.getInstance().getQualityRole(
						Player.getInstance().playerIdelRole, QUALITY.orange);
			}
		}
		for (Role e : eatRoles) {
			suRole.exp += e.classes.equals(suRole.classes)?e.exp*1.2f:e.exp;
			Player.getInstance().playerRole.removeValue(e, false);
		}
		eatRoles.clear();
		Player.getInstance().getPlayerPackageRole();
		addRoleToStage(quality);
	}

	/**
	 * 设置被吞噬或者取消吞噬
	 * 
	 * @param r
	 */
	private void resetEatRole(Role r) {
		boolean flag = false;
		for (Role e : eatRoles) {
			if (e.equals(r)) {
				flag = true;
			}
		}
		if (flag) {
			eatRoles.removeValue(r, false);

		} else {
			eatRoles.add(r);
		}
		U.showRolesSelect(Player.getInstance().playerIdelRole, eatRoles);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(null);
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(upRoleStage);// 必须先加这个。。。。
		inputMultiplexer.addProcessor(sRoleStage);// 必须先加这个。。。。
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(new GestureDetector(this));
		Gdx.input.setInputProcessor(inputMultiplexer);
		getRoles();
		addRoleToStage(QUALITY.all);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		sRoleStage.act(Gdx.graphics.getDeltaTime());
		sRoleStage.draw();
		upRoleStage.act(Gdx.graphics.getDeltaTime());
		upRoleStage.draw();
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		sRoleStage.clear();
		upRoleStage.clear();
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
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QUALITY.purple);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		orangeImg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QUALITY.orange);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		blueImg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QUALITY.blue);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		greenImg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QUALITY.green);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		allImg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QUALITY.all);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		eatImg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				eatRolesUpdate(false);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		eatAllImg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				eatRolesUpdate(true);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		upButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if (suRole.exp > suRole.expUp) {
					suRole.levelUp();
					TipsWindows.getInstance().removeFromStage();
					showUpRoleInfo();
				}
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
