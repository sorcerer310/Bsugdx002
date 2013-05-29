package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.bsu.head.CubocScreen;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.obj.RolePhoto;
import com.bsu.tools.Configure;
import com.bsu.tools.Configure.QUALITY;
import com.bsu.tools.Configure.QualityS;
import com.bsu.tools.GameTextureClass;
import com.bsu.tools.U;

public class UpdateScreen extends CubocScreen implements Observer,
		GestureListener {
	private Texture timg;
	private Image background;
	private Stage stage;
	private Stage sRoleStage;
	private Stage upRoleStage;
	private Image ib_back;
	private Role selectUpdateRole;
	private Array<Role> eatRoles = new Array<Role>();
	private QualityS quality;// 当前选择显示的品质
	private TextButton allButton;
	private TextButton greenButton;
	private TextButton blueButton;
	private TextButton purpleButton;
	private TextButton orangeButton;
	private TextButton eatButton;
	private TextButton eatAllButton;// 一键吞噬所有某种品质
	private Label name, exp, up;

	public UpdateScreen(Game game) {
		super(game);
		stage = new Stage(Configure.rect_width, Configure.rect_height, false);
		sRoleStage = new Stage(Configure.rect_width, Configure.rect_height,
				false);
		upRoleStage = new Stage(Configure.rect_width, Configure.rect_height,
				false);
		background = new Image(GameTextureClass.getInstance().updatePanel);
		stage.addActor(background);
		quality = QualityS.gselect;
		ib_back = WidgetFactory.getInstance().makeImageButton(
				Configure.button_back, stage, 360, 40);
		ib_back.setPosition(360, 40);

		name = WidgetFactory.getInstance().makeLabel("", stage, 200, 260);
		exp = WidgetFactory.getInstance().makeLabel("", stage, 320, 260);
		up = WidgetFactory.getInstance().makeLabel("up", stage, 380, 260);
		allButton = WidgetFactory.getInstance().makeOneTextButton("all", stage,
				140, 30);
		greenButton = WidgetFactory.getInstance().makeOneTextButton("green",
				stage, 180, 30);
		blueButton = WidgetFactory.getInstance().makeOneTextButton("blue",
				stage, 220, 30);
		purpleButton = WidgetFactory.getInstance().makeOneTextButton("purple",
				stage, 260, 30);
		orangeButton = WidgetFactory.getInstance().makeOneTextButton("orange",
				stage, 300, 30);
		eatButton = WidgetFactory.getInstance().makeOneTextButton("eat", stage,
				360, 30);
		eatAllButton = WidgetFactory.getInstance().makeOneTextButton("eatall",
				stage, 390, 30);
		getRoles();
		setListener();
		addRoleToStage(QualityS.allselect);
	}

	private void getRoles() {
		final Array<Role> playerRoles = Player.getInstance().playerFightRole;
		for (int i = 0; i < playerRoles.size; i++) {
			final Role r = playerRoles.get(i);
			Vector2 v = new Vector2(48, 246 - 55 * i);
			RolePhoto photo = new RolePhoto(r.roleTexture, upRoleStage,
					r.quality, v, false);
			r.photo = photo;
			photo.role.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					U.showRoleSelect(playerRoles,r);
					selectUpdateRole=r;
					return super.touchDown(event, x, y, pointer, button);
				}
			});
		}
		U.showRoleSelect(playerRoles,playerRoles.get(0));
		selectUpdateRole=playerRoles.get(0);
	}
	/**
	 * 显示要强化角色信息
	 */
	private void showUpRoleInfo(){
		name.setText(selectUpdateRole.name);
		exp.setText(selectUpdateRole.exp + "/" + selectUpdateRole.expUp);
		up.setVisible(selectUpdateRole.exp > selectUpdateRole.expUp ? true : false);
	}
	/**
	 * 当点击卡片按钮时添加背包中卡片到舞台，并根据当前所选类型显示
	 */
	private void addRoleToStage(QualityS q) {
		quality = q;
		if (quality == QualityS.allselect) {
			showQualityRole(Player.getInstance().playerIdelRole);
		}
		if (quality == QualityS.gselect) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerIdelRole, QUALITY.green));
		}
		if (quality == QualityS.bselect) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerIdelRole, QUALITY.blue));
		}
		if (quality == QualityS.pselect) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerIdelRole, QUALITY.purple));
		}
		if (quality == QualityS.oselect) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerIdelRole, QUALITY.orange));
		}
	}

	/**
	 * 显示某一品质的role
	 * 
	 * @param imgArray
	 *            某一品质的role Image数组
	 */
	private void showQualityRole(Array<Role> roleArray) {
		sRoleStage.clear();
		for (int i = 0; i < roleArray.size; i++) {
			final Role r = roleArray.get(i);
			Vector2 v = new Vector2(140 + i % 5 * 70, 200 - i / 5 * 70);
			RolePhoto photo = new RolePhoto(r.roleTexture, sRoleStage,
					r.quality, v, false);
			r.photo = photo;
			photo.role.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					resetEatRole(r);
					super.touchUp(event, x, y, pointer, button);
				}
			});
		}
	}

	/**
	 * 吞噬卡片升级
	 * 
	 * @param flag
	 *            是否一键吞噬
	 */
	private void eatRolesUpdate(boolean flag) {
		if (flag) {
			if (quality == QualityS.allselect) {
				eatRoles = Player.getInstance().playerIdelRole;
			}
			if (quality == QualityS.gselect) {
				eatRoles = Player.getInstance().getQualityRole(
						Player.getInstance().playerIdelRole, QUALITY.green);
			}
			if (quality == QualityS.bselect) {
				eatRoles = Player.getInstance().getQualityRole(
						Player.getInstance().playerIdelRole, QUALITY.blue);
			}
			if (quality == QualityS.pselect) {
				eatRoles = Player.getInstance().getQualityRole(
						Player.getInstance().playerIdelRole, QUALITY.purple);
			}
			if (quality == QualityS.oselect) {
				eatRoles = Player.getInstance().getQualityRole(
						Player.getInstance().playerIdelRole, QUALITY.orange);
			}
		}
		for (Role e : eatRoles) {
			selectUpdateRole.exp += e.exp;
			Player.getInstance().playerRole.removeValue(e, false);
		}
		eatRoles.clear();
		showUpRoleInfo();
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
		addRoleToStage(QualityS.allselect);
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
				notifyObservers(Configure.button_back);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		purpleButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QualityS.pselect);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		orangeButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QualityS.oselect);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		blueButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QualityS.bselect);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		greenButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QualityS.gselect);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		allButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QualityS.allselect);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		eatButton.addListener(new InputListener() {
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
		eatAllButton.addListener(new InputListener() {
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
		up.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				selectUpdateRole.levelUp();
				showUpRoleInfo();
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
