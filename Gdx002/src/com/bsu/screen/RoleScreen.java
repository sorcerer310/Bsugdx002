package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.bsu.head.CubocScreen;
import com.bsu.make.ButtonFactory;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.tools.Configure;
import com.bsu.tools.GameMap;
import com.bsu.tools.Configure.QUALITY;
import com.bsu.tools.GameTextureClass;
import com.bsu.tools.Configure.QualityS;

public class RoleScreen extends CubocScreen implements Observer,
		GestureListener {
	private Texture timg;
	private Image background;
	private Stage stage;
	private Stage sRoleStage;
	private Image ib_back;
	private QualityS quality;// 当前选择显示的品质
	private Role selectRole;
	private TextButton allButton;
	private TextButton greenButton;
	private TextButton blueButton;
	private TextButton purpleButton;
	private TextButton orangeButton;
	private Array<Image> imgArray = new Array<Image>();
	private int clingX;// 地图移动位移
	private OrthographicCamera c;
	private int cameraWidth;// 显示人物时的界面宽度
	Label nameLabel;
	Label qualityLabel;
	Label attackValueLabel;
	Label defendValueLabel;

	public RoleScreen(Game game) {
		super(game);
		stage = new Stage(Configure.rect_width, Configure.rect_height, false);
		sRoleStage = new Stage(Configure.rect_width, Configure.rect_height,
				false);
		c = (OrthographicCamera) sRoleStage.getCamera();
		background = new Image(GameTextureClass.getInstance().rolePanel);

		ib_back = ButtonFactory.getInstance().makeImageButton(
				Configure.button_back);
		ib_back.setPosition(375, 272);

		stage.addActor(background);
		stage.addActor(ib_back);
		allButton = ButtonFactory.getInstance()
				.makeOneTextButton("all", 20, 30);
		greenButton = ButtonFactory.getInstance().makeOneTextButton("green",
				50, 30);
		blueButton = ButtonFactory.getInstance().makeOneTextButton("blue", 90,
				30);
		purpleButton = ButtonFactory.getInstance().makeOneTextButton("purple",
				130, 30);
		orangeButton = ButtonFactory.getInstance().makeOneTextButton("orange",
				170, 30);
		stage.addActor(allButton);
		stage.addActor(greenButton);
		stage.addActor(blueButton);
		stage.addActor(purpleButton);
		stage.addActor(orangeButton);
		nameLabel = new Label("name:", Configure.get_sytle());
		qualityLabel = new Label("quality:", Configure.get_sytle());
		attackValueLabel = new Label("attack:", Configure.get_sytle());
		defendValueLabel = new Label("defend:" + "", Configure.get_sytle());
		stage.addActor(nameLabel);
		stage.addActor(qualityLabel);
		stage.addActor(attackValueLabel);
		stage.addActor(defendValueLabel);
		nameLabel.setPosition(60, 240);
		qualityLabel.setPosition(160, 240);
		attackValueLabel.setPosition(60, 220);
		defendValueLabel.setPosition(160, 220);
		setListener();
	}

	/**
	 * 当点击卡片按钮时添加背包中卡片到舞台，并根据当前所选类型显示
	 */
	private void addRoleToStage(QualityS q) {
		quality = q;
		if (quality == QualityS.allselect) {
			showQualityRole(Player.getInstance().playerRole);
		}
		if (quality == QualityS.gselect) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerRole, QUALITY.green));
		}
		if (quality == QualityS.bselect) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerRole, QUALITY.blue));
		}
		if (quality == QualityS.pselect) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerRole, QUALITY.purple));
		}
		if (quality == QualityS.oselect) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerRole, QUALITY.orange));
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
		imgArray.clear();
		clingX = 0;
		c.position.x = Configure.rect_width / 2;
		for (int i = 0; i < roleArray.size; i++) {
			final Image roleImg = new Image(roleArray.get(i).roleTexture);
			final Role r = roleArray.get(i);
			cameraWidth = (i + 1) * 70 + 10;
			roleImg.setScale(0.5f);
			sRoleStage.addActor(roleImg);
			imgArray.add(roleImg);
			roleImg.setPosition(20 + i * 70, 50);
			roleImg.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					resetAllSelectImg(roleImg);
					showRoleInfo(r);
					super.touchUp(event, x, y, pointer, button);
				}
			});
		}
	}

	/**
	 * 显示人物信息
	 */
	private void showRoleInfo(Role r) {
		selectRole = r;
		nameLabel.setText("name::"+r.name);
		qualityLabel.setText("quality::"+getQualityName(r.quality));
		attackValueLabel.setText("attack::"+""+r.value_attack);
		defendValueLabel.setText("defend::"+""+r.value_defend);
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
	 * 重设所有图片效果，以便正确显示被选中对象
	 * 
	 * @param img
	 */
	private void resetAllSelectImg(Image img) {
		clingX = 0;
		for (int i = 0; i < imgArray.size; i++) {
			imgArray.get(i).setScale(0.5f);
			if (imgArray.get(i).equals(img)) {
				img.setScale(0.4f);
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
		if (clingX != 0) {
			int mx = clingX > 0 ? -1 : 1;
			int maxW = cameraWidth - Configure.rect_width < 0 ? 0 : cameraWidth
					- Configure.rect_width;
			int w = Configure.rect_width / 2;
			if (c.position.x + mx >= w && c.position.x + mx <= maxW + w) {
				c.position.x += mx;
			}
		}
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
		clingX = velocityX > 0 ? Configure.rect_width / 2
				: -Configure.rect_width / 2;
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
