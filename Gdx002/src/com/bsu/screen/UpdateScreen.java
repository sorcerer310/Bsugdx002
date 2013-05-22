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
import com.bsu.make.ButtonFactory;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.tools.Configure;
import com.bsu.tools.Configure.QUALITY;
import com.bsu.tools.Configure.QualityS;
import com.bsu.tools.GameTextureClass;

public class UpdateScreen extends CubocScreen implements Observer,
		GestureListener {
	private Texture timg;
	private Image background;
	private Stage stage;
	private Stage sRoleStage;
	private Image ib_back;
	private Array<Image> RoleSelectImg = new Array<Image>();// 用来升级的卡片

	private Role selectUpdateRole;
	private Image roleImg;
	private Array<Role> eatRoles = new Array<Role>();
	private QualityS quality;// 当前选择显示的品质
	private TextButton allButton;
	private TextButton greenButton;
	private TextButton blueButton;
	private TextButton purpleButton;
	private TextButton orangeButton;
	private TextButton eatButton;
	private TextButton eatAllButton;// 一键吞噬所有某种品质

	public UpdateScreen(Game game) {
		super(game);
		stage = new Stage(Configure.rect_width, Configure.rect_height, false);
		sRoleStage = new Stage(Configure.rect_width, Configure.rect_height,
				false);
		background = new Image(GameTextureClass.getInstance().updatePanel);
		quality = QualityS.gselect;
		selectUpdateRole = Player.getInstance().playerFightRole.get(0);
		ib_back = ButtonFactory.getInstance().makeImageButton(
				Configure.button_back);
		ib_back.setPosition(360, 40);
		stage.addActor(background);
		stage.addActor(ib_back);
		Label updateLabel = new Label("Role1", Configure.get_sytle());
		Label expLabel = new Label("addExp", Configure.get_sytle());
		Label upLabel = new Label("up", Configure.get_sytle());
		updateLabel.setPosition(140, 240);
		expLabel.setPosition(260, 240);
		upLabel.setPosition(320, 260);
		stage.addActor(updateLabel);
		stage.addActor(expLabel);
		stage.addActor(upLabel);
		allButton = ButtonFactory.getInstance().makeOneTextButton("all", 140,
				30);
		greenButton = ButtonFactory.getInstance().makeOneTextButton("green",
				180, 30);
		blueButton = ButtonFactory.getInstance().makeOneTextButton("blue", 220,
				30);
		purpleButton = ButtonFactory.getInstance().makeOneTextButton("purple",
				260, 30);
		orangeButton = ButtonFactory.getInstance().makeOneTextButton("orange",
				300, 30);
		eatButton = ButtonFactory.getInstance().makeOneTextButton("eat", 360,
				30);
		eatAllButton = ButtonFactory.getInstance().makeOneTextButton("eatall",
				390, 30);
		stage.addActor(allButton);
		stage.addActor(greenButton);
		stage.addActor(blueButton);
		stage.addActor(purpleButton);
		stage.addActor(orangeButton);
		stage.addActor(eatButton);
		stage.addActor(eatAllButton);
		getRoles();
		setListener();
		addRoleToStage(QualityS.allselect);
	}

	private void getRoles() {
		RoleSelectImg.clear();
		Array<Role> playerRols = Player.getInstance().playerFightRole;
		for (int i = 0; i < playerRols.size; i++) {
			final Role r = playerRols.get(i);
			final Image roleImg = new Image(playerRols.get(i).roleTexture);
			Image backImg = ButtonFactory.getInstance().makeImageButton(
					Configure.Img_head_back);
			if (i == 0) {
				roleImg.setScale(0.4f);
			} else {
				roleImg.setScale(0.5f);
			}
			stage.addActor(backImg);
			stage.addActor(roleImg);
			roleImg.setPosition(48, 236 - 55 * i);
			backImg.setPosition(40, 230 - 55 * i);
			RoleSelectImg.add(roleImg);
			roleImg.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					resetAllSelectImg(roleImg);
					showSelectImg(r);
					return super.touchDown(event, x, y, pointer, button);
				}
			});
		}
	}

	/**
	 * 重设所有图片效果，以便正确显示被选中对象
	 * 
	 * @param img
	 */
	private void resetAllSelectImg(Image img) {
		for (int i = 0; i < RoleSelectImg.size; i++) {
			RoleSelectImg.get(i).setScale(0.5f);
			if (RoleSelectImg.get(i).equals(img)) {
				img.setScale(0.4f);
			}
		}
	}

	/**
	 * 显示人物信息
	 */
	private void showRoleInfo() {
		sRoleStage.clear();
		Label nameLabel = new Label("name:" + selectUpdateRole.name,
				Configure.get_sytle());
		Label qualityLabel = new Label("quality:"
				+ getQualityName(selectUpdateRole.quality),
				Configure.get_sytle());
		Label attackValueLabel = new Label("attack:"
				+ selectUpdateRole.attack_value + "", Configure.get_sytle());
		Label defendValueLabel = new Label("defend:"
				+ selectUpdateRole.defend_value + "", Configure.get_sytle());
		sRoleStage.addActor(nameLabel);
		sRoleStage.addActor(qualityLabel);
		sRoleStage.addActor(attackValueLabel);
		sRoleStage.addActor(defendValueLabel);
		nameLabel.setPosition(140, 240);
		qualityLabel.setPosition(250, 240);
		attackValueLabel.setPosition(140, 220);
		defendValueLabel.setPosition(250, 220);
	}

	/**
	 * 取得品质对应文字
	 * 
	 * @param q
	 * @return
	 */
	private String getQualityName(QUALITY q) {
		String s = null;
		if (q == QUALITY.green) {
			s = "green";
		}
		if (q == QUALITY.blue) {
			s = "blue";
		}
		if (q == QUALITY.purple) {
			s = "pruple";
		}
		if (q == QUALITY.orange) {
			s = "orange";
		}
		return s;
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
			final Image roleImg = new Image(roleArray.get(i).roleTexture);
			final Role r = roleArray.get(i);
			roleImg.setScale(0.5f);
			sRoleStage.addActor(roleImg);
			roleImg.setPosition(140 + i % 5 * 70, 200 - i / 5 * 70);
			roleImg.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					resetEatRole(r, roleImg);
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
			for(Role e:eatRoles){
				Player.getInstance().playerRole.removeValue(e, false);
			}
		} else {
			for (Role r : Player.getInstance().playerIdelRole) {
				for (Role e : eatRoles) {
					if (e.equals(r)) {
						Player.getInstance().playerRole.removeValue(r, false);
					}
				}
			}
		}
		eatRoles.clear();
		Player.getInstance().getPlayerPackageRole();
		addRoleToStage(quality);
	}

	/**
	 * 设置被吞噬或者取消吞噬
	 * 
	 * @param r
	 * @param img
	 */
	private void resetEatRole(Role r, Image img) {
		boolean flag = false;
		for (Role e : eatRoles) {
			if (e.equals(r)) {
				flag = true;
			}
		}
		if (flag) {
			eatRoles.removeValue(r, false);
			img.setScale(0.5f);
		} else {
			eatRoles.add(r);
			img.setScale(0.4f);
		}
	}

	/**
	 * 显示想要升级的role头像
	 */
	private void showSelectImg(Role r) {
		selectUpdateRole = r;
		if (roleImg != null) {
			roleImg.getParent().removeActor(roleImg);
		}
		roleImg = new Image(r.roleTexture);
		roleImg.setPosition(150, 250);
		roleImg.setScale(0.5f);
		stage.addActor(roleImg);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(null);
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(sRoleStage);// 必须先加这个。。。。
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(new GestureDetector(this));
		Gdx.input.setInputProcessor(inputMultiplexer);
		getRoles();
		showSelectImg(Player.getInstance().playerFightRole.get(0));
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
