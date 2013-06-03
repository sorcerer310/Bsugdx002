package com.bsu.screen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.rotateBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.Observable;
import java.util.Observer;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.bsu.head.CubocScreen;
import com.bsu.make.WidgetFactory;
import com.bsu.make.GameScreenConfigure;
import com.bsu.make.RoleFactory;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.obj.RolePhoto;
import com.bsu.tools.Configure;
import com.bsu.tools.MessageObject;
import com.bsu.tools.TipsWindows;
import com.bsu.tools.Configure.QUALITY;
import com.bsu.tools.Configure.QualityS;
import com.bsu.tools.Configure.STATE;
import com.bsu.tools.GameTextureClass;

public class SelectRoleScreen extends CubocScreen implements Observer,
		GestureListener {

	private Stage stage;
	private Stage sRoleStage;
	private QualityS quality;
	private Image background;
	private Image ib_back;
	private Image allButton;
	private Image greenButton;
	private Image blueButton;
	private Image purpleButton;
	private Image orangeButton;
	private Role changeRole;
	private Role selectRole;

	public SelectRoleScreen(Game game) {
		super(game);
		stage = new Stage(Configure.rect_width, Configure.rect_height, false);
		sRoleStage = new Stage(Configure.rect_width, Configure.rect_height,
				false);
		background = new Image(GameTextureClass.getInstance().selectRolePanel);
		stage.addActor(background);
		ib_back = WidgetFactory.getInstance().makeImageButton(
				Configure.button_back, stage, 380, 272);
		allButton = WidgetFactory.getInstance().makeImageButton(
				Configure.button_all, stage, 100, 20);
		greenButton = WidgetFactory.getInstance().makeImageButton(
				Configure.button_green, stage, 163, 20);
		blueButton = WidgetFactory.getInstance().makeImageButton(
				Configure.button_blue, stage, 226, 20);
		purpleButton = WidgetFactory.getInstance().makeImageButton(
				Configure.button_purple, stage, 289, 20);
		orangeButton = WidgetFactory.getInstance().makeImageButton(
				Configure.button_orange, stage, 352, 20);
		quality = QualityS.allselect;
		addRoleToStage(QualityS.allselect);
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
	private void addRoleToStage(QualityS qs) {
		quality = qs;
		selectRole = null;
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
		if (quality == QualityS.allselect) {
			showQualityRole(Player.getInstance().playerIdelRole);
		}
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
		int x = 35;
		int y = 200;
		int w = 60;
		for (int i = 0; i < roleArray.size; i++) {
			final Role r = roleArray.get(i);
			Vector2 v = new Vector2(x + i % 7 * w, y - i / 7 * w);
			final RolePhoto photo = new RolePhoto(r, sRoleStage, v, false);
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
					if (selectRole != r) {
						selectRole = r;// 选定人物。。
						TipsWindows.getInstance().showRoleInfo(r, photo.role_v,
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
					notifyObservers(Configure.button_back);
					ib_back.setScale(1f);
					super.touchUp(event, x, y, pointer, button);
				}
			});
		}
		for (int i = frlength; i < 21; i++) {
			Vector2 v = new Vector2(x + i % 7 * w, y - i / 7 * w);
			RolePhoto photo = new RolePhoto(sRoleStage, QUALITY.orange, v);
			photo.role_k.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					setChanged();
					notifyObservers(new MessageObject(null,
							Configure.screen_selectRole));
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
			e.photo.showEffect(false);
		}
		for (Actor act : sRoleStage.getActors()) {
			if (act instanceof Image) {
				if (act.equals(r.photo.role)) {
					r.photo.showEffect(true);
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
		addRoleToStage(QualityS.allselect);
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
				notifyObservers(Configure.button_back);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		purpleButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QualityS.pselect);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		orangeButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QualityS.oselect);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		blueButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QualityS.bselect);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		greenButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QualityS.gselect);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		allButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QualityS.allselect);
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
