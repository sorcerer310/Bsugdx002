package com.bsu.screen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.Observable;
import java.util.Observer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.bsu.head.CubocScreen;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.obj.RolePhoto;
import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.Configure;
import com.bsu.tools.TipsWindows;
import com.bsu.tools.Configure.QUALITY;
import com.bsu.tools.GameTextureClass;
import com.bsu.tools.Configure.QualityS;
import com.bsu.tools.U;

public class RoleScreen extends CubocScreen implements Observer,
		GestureListener {
	private Texture timg;

	private Stage stage;// 基本舞台
	private Stage sRoleStage;// Role舞台
	private Stage RoleInfoStage;// Role信息舞台
	private Image background;
	private Image ib_back;
	private Image allButton;
	private Image greenButton;
	private Image blueButton;
	private Image purpleButton;
	private Image orangeButton;
	private QualityS quality;// 当前选择显示的品质
	private Role selectRole;// 选择显示的Role
	private Skill selectSkill;// 选择要更换的skill
	private int clingX;// 地图移动位移
	private OrthographicCamera c;
	private int cameraWidth;// 显示人物时的界面宽度
	private WidgetFactory wfy;// 界面工厂类
	private int skillIndex;// 希望改变的技能

	public RoleScreen(Game game) {
		super(game);
		stage = new Stage(Configure.rect_width, Configure.rect_height, false);
		sRoleStage = new Stage(Configure.rect_width, Configure.rect_height,
				false);
		RoleInfoStage = new Stage(Configure.rect_width, Configure.rect_height,
				false);
		wfy = WidgetFactory.getInstance();
		c = (OrthographicCamera) sRoleStage.getCamera();
		background = new Image(GameTextureClass.getInstance().rolePanel);
		stage.addActor(background);
		ib_back = wfy.makeImageButton(Configure.button_back, stage, 375, 272);
		allButton = WidgetFactory.getInstance().makeImageButton(
				Configure.button_all, stage, 20, 20);
		greenButton = WidgetFactory.getInstance().makeImageButton(
				Configure.button_green, stage, 83, 20);
		blueButton = WidgetFactory.getInstance().makeImageButton(
				Configure.button_blue, stage, 146, 20);
		purpleButton = WidgetFactory.getInstance().makeImageButton(
				Configure.button_purple, stage, 209, 20);
		orangeButton = WidgetFactory.getInstance().makeImageButton(
				Configure.button_orange, stage, 272, 20);
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
		RoleInfoStage.clear();
		clingX = 0;
		c.position.x = Configure.rect_width / 2;
		for (int i = 0; i < roleArray.size; i++) {
			final Role r = roleArray.get(i);
			Vector2 v = new Vector2(20 + 70 * i, 50);
			final RolePhoto photo = new RolePhoto(r.roleTexture, sRoleStage,
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
					showRoleInfo(r);
					super.touchUp(event, x, y, pointer, button);
				}
			});
		}
	}

	/**
	 * 显示人物信息
	 */
	private void showRoleInfo(final Role r) {
		RoleInfoStage.clear();
		selectRole = r;
		if (selectRole == null) {
			return;
		}
		U.showRoleSelect(Player.getInstance().playerRole, r);
		skillIndex = 0;
		showRoleBaseInfo(r);
		Image[] skillImg = new Image[2];
		final Image skillImgEffect = new Image(
				WidgetFactory
						.getInstance()
						.getTexture(
								36,
								Color.BLACK,
								Configure.getQualityColor(r.skill_array.get(0).quality),
								1));
		RoleInfoStage.addActor(skillImgEffect);
		for (int i = 0; i < 2; i++) {
			final int index = i;
			final Image img = wfy.makeImg(r.skill_array.get(0).icon,
					RoleInfoStage, 1f, 40 + i * 60, 140);
			final Vector2 v = new Vector2(img.getX(), img.getY());
			skillImg[i] = img;
			img.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					TipsWindows.getInstance().showSkillInfo(
							r.skill_array.get(index), v, RoleInfoStage);
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					skillIndex = index;
					skillImgEffect.setPosition(img.getX() - 2, img.getY() - 2);
					super.touchUp(event, x, y, pointer, button);
				}
			});
		}
		// wfy.makeImg(r.weapon.texture, RoleInfoStage, 1f, 40, 100);
		// wfy.makeImg(r.armor.texture, RoleInfoStage, 1f, 100, 100);
		showSkillTree(r, skillImg[0], skillImg[1]);
		skillImgEffect.setPosition(skillImg[0].getX() - 2,
				skillImg[0].getY() - 2);
	}

	private void showRoleBaseInfo(Role r) {
		wfy.makeLabel(r.name, RoleInfoStage, 40, 240,
				Configure.getQualityColor(r.quality));
		wfy.makeLabel(Configure.getQualityName(r.quality), RoleInfoStage, 100,
				240);
		wfy.makeLabel(r.maxHp + "", RoleInfoStage, 40, 220);
		wfy.makeLabel(r.exp + "/" + r.expUp, RoleInfoStage, 100, 220);
		wfy.makeLabel("" + r.getAttack(), RoleInfoStage, 40, 180);
		wfy.makeLabel("" + r.getDefend(), RoleInfoStage, 100, 180);
		wfy.makeLabel("" + r.level, RoleInfoStage, 40, 200);
		wfy.makeLabel("" + U.getClasses(r), RoleInfoStage, 100, 200);
		wfy.makeImg(r.roleTexture, RoleInfoStage, 0.5f, 40, 260);
	}

	/**
	 * 设置显示技能树
	 * 
	 * @param r
	 *            角色
	 * @param img0
	 *            第一个携带技能图标
	 * @param img1
	 *            第二个携带技能图标
	 */
	public void showSkillTree(final Role r, final Image img0, final Image img1) {
		int numsGreen = 0;
		int numsBlue = 0;
		int numsPur = 0;
		int numsOra = 0;
		int ix = 180, iy = 110;
		for (final Skill s : r.skill_tree) {
			Image skill_img = null;
			if (s.quality == QUALITY.green) {
				skill_img = wfy.makeImg(s.icon, RoleInfoStage, 1, ix
						+ numsGreen * 40, iy);
				numsGreen++;
			}
			if (s.quality == QUALITY.blue) {
				skill_img = wfy.makeImg(s.icon, RoleInfoStage, 1, ix + numsBlue
						* 40, iy + 40);
				numsBlue++;
			}
			if (s.quality == QUALITY.purple) {
				skill_img = wfy.makeImg(s.icon, RoleInfoStage, 1, ix + numsPur
						* 40, iy + 80);
				numsPur++;
			}
			if (s.quality == QUALITY.orange) {
				skill_img = wfy.makeImg(s.icon, RoleInfoStage, 1, ix + numsOra
						* 40, iy + 120);
				numsOra++;
			}
			final Vector2 vs = new Vector2(skill_img.getX(), skill_img.getY());
			if (!s.enable) {
				skill_img.addListener(new InputListener() {
					@Override
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						TipsWindows.getInstance().showSkillInfo(s, vs,
								RoleInfoStage);
						return true;
					}

					@Override
					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						if ((selectSkill == null) || selectSkill != s) {
							selectSkill = s;
						} else {
							setAnotherSkill(r, skillIndex, s, img0, img1);
						}
						super.touchUp(event, x, y, pointer, button);
					}
				});
			}
		}
	}

	/**
	 * 重新给指定技能更换新技能
	 * 
	 * @param s
	 * @param img
	 */
	private void setAnotherSkill(Role r, int index, Skill s, Image img,
			Image img1) {
		System.out.println(r.skill_array.get(index).name + "change to" + s.name
				+ "inf" + s.info);
		r.skill_array.set(index, s);
		Skin skin = new Skin();
		skin.add("Img", new TextureRegion(s.icon));
		if (index == 0) {
			img.setDrawable(skin.getDrawable("Img"));
		} else {
			img1.setDrawable(skin.getDrawable("Img"));
		}
		skin.dispose();
		skin = null;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(null);
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(RoleInfoStage);
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
		RoleInfoStage.act(Gdx.graphics.getDeltaTime());
		RoleInfoStage.draw();
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		selectRole = null;
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
				showRoleInfo(null);
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
				showRoleInfo(null);
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
				showRoleInfo(null);
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
				showRoleInfo(null);
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
				showRoleInfo(null);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		clingX = 0;
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
