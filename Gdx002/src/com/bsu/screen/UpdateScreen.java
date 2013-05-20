package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
import com.bsu.tools.GameTextureClass;

public class UpdateScreen extends CubocScreen implements Observer {
	private Texture timg;
	private Image background;
	private Stage stage;
	private Stage sRoleStage;
	private Image ib_back;
	private Array<Image> RoleSelectImg = new Array<Image>();// 用来升级的卡片

	private Role selectUpdateRole;
	private Array<Role> eatRoles = new Array<Role>();
	private QualityS quality;// 当前选择显示的品质

	private TextButton infoButton;// role信息
	private TextButton skillButton;// 技能按钮
	private TextButton equipButton;// 装备按钮
	private TextButton cardButton;// 卡片按钮，进入卡片包裹

	public enum QualityS {
		gselect, bselect, pselect, oselect
	}

	public UpdateScreen(Game game) {
		super(game);
		stage = new Stage(Configure.rect_width, Configure.rect_height, false);
		sRoleStage = new Stage(Configure.rect_width, Configure.rect_height,
				false);
		background = new Image(GameTextureClass.getInstance().updatePanel);
		quality = QualityS.gselect;
		selectUpdateRole = Player.getInstance().getPlayerFightRole().get(0);
		ib_back = ButtonFactory.getInstance().makeImageButton(
				Configure.button_back);
		ib_back.setPosition(360, 262);
		infoButton = ButtonFactory.getInstance().makeOneTextButton("info", 240,
				40);
		skillButton = ButtonFactory.getInstance().makeOneTextButton("skill",
				290, 40);
		equipButton = ButtonFactory.getInstance().makeOneTextButton("equip",
				340, 40);
		cardButton = ButtonFactory.getInstance().makeOneTextButton("card", 400,
				40);
		stage.addActor(background);
		stage.addActor(ib_back);
		stage.addActor(infoButton);
		stage.addActor(skillButton);
		stage.addActor(equipButton);
		stage.addActor(cardButton);
		Array<Role> playerRols = Player.getInstance().getPlayerFightRole();
		for (int i = 0; i < playerRols.size; i++) {
			Image roleImg = new Image(playerRols.get(i).roleTexture);
			Image backImg = ButtonFactory.getInstance().makeImageButton(
					Configure.Img_head_back);
			roleImg.setScale(0.5f);
			stage.addActor(backImg);
			stage.addActor(roleImg);
			roleImg.setPosition(48, 246 - 55 * i);
			backImg.setPosition(40, 240 - 55 * i);
			RoleSelectImg.add(roleImg);
			roleImg.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					getSelectRole((Image) event.getListenerActor());
					return super.touchDown(event, x, y, pointer, button);
				}
			});
		}
		setListener();
		showRoleInfo();
	}

	/**
	 * 显示人物信息
	 */
	private void showRoleInfo() {
		sRoleStage.clear();
		Label nameLabel = new Label("name:"+selectUpdateRole.name,
				Configure.get_sytle());
		Label qualityLabel = new Label("quality:"+getQualityName(selectUpdateRole.quality),
				Configure.get_sytle());
		Label attackValueLabel = new Label("attack:"+selectUpdateRole.attack_value+"",
				Configure.get_sytle());
		Label defendValueLabel = new Label("defend:"+selectUpdateRole.defend_value+"",
				Configure.get_sytle());
		sRoleStage.addActor(nameLabel);
		sRoleStage.addActor(qualityLabel);
		sRoleStage.addActor(attackValueLabel);
		sRoleStage.addActor(defendValueLabel);
		nameLabel.setPosition(140, 240);
		qualityLabel.setPosition(250, 240);
		attackValueLabel.setPosition(140, 220);
		defendValueLabel.setPosition(250, 220);
	}

	private String getQualityName(QUALITY q) {
		String s = null;
		if (q == QUALITY.green) {
			s="green";
		}
		if (q == QUALITY.blue) {
			s="blue";
		}
		if (q == QUALITY.purple) {
			s="pruple";
		}
		if (q == QUALITY.orange) {
			s="orange";
		}
		return s;
	}

	/**
	 * 通过点击Image取得相应的role
	 * 
	 * @param img
	 */
	private void getSelectRole(Image img) {
		for (int i = 0; i < RoleSelectImg.size; i++) {
			if (RoleSelectImg.get(i).equals(img)) {
				selectUpdateRole=Player.getInstance().getPlayerFightRole().get(i);
				showRoleInfo();
			}
		}
	}

	/**
	 * 当点击卡片按钮时添加背包中卡片到舞台，并根据当前所选类型显示
	 */
	private void addRoleToStage() {
		if (quality == QualityS.gselect) {
			showQualityRole(Player.getInstance().playerGreenRole);
		}
		if (quality == QualityS.bselect) {
			showQualityRole(Player.getInstance().playerBlueRole);
		}
		if (quality == QualityS.pselect) {
			showQualityRole(Player.getInstance().playerPurpleRole);
		}
		if (quality == QualityS.oselect) {
			showQualityRole(Player.getInstance().playerOrangeRole);
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
		System.out.println(roleArray.size + "????");
		for (int i = 0; i < roleArray.size; i++) {
			Image roleImg = new Image(roleArray.get(i).roleTexture);
			roleImg.setScale(0.5f);
			sRoleStage.addActor(roleImg);
			roleImg.setPosition(140 + i % 5 * 70, 200 - i / 5 * 70);
		}
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(null);
		Gdx.input.setInputProcessor(stage);
		selectUpdateRole=Player.getInstance().getPlayerFightRole().get(0);
		showRoleInfo();
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
		infoButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				showRoleInfo();
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});

		skillButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {

				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		equipButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {

				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		cardButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage();
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}
}
