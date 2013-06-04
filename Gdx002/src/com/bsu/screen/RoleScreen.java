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
import com.bsu.effect.RoleEffect;
import com.bsu.effect.SkillEffect;
import com.bsu.head.CubocScreen;
import com.bsu.make.TipsWindows;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.Configure;
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
			Vector2 v = new Vector2(20 + 60 * i, 50);
			final RoleEffect photo = new RoleEffect(r, sRoleStage, v, false);
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
		if (roleArray.size > 0) {
			showRoleInfo(roleArray.get(0));
		} else {
			RoleInfoStage.clear();
			TipsWindows.getInstance().showTips("没有相应品质卡片，通过可收集", RoleInfoStage,
					Color.GRAY);
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
		final Array<Image> skillImg = new Array<Image>();
		for (int i = 0; i < 2; i++) {
			final int index = i;
			Skill skill = r.skill_array.get(index);
			SkillEffect se = new SkillEffect(skill, RoleInfoStage, new Vector2(
					40 + i * 60, 170), true);
			skill.skillEffect = se;
			final Image img = se.skillImg;
			final Vector2 v = new Vector2(img.getX(), img.getY());
			skillImg.add(img);
			skillImg.add(se.skillImgEffect);
			if (i == 0) {
				U.setApha(img, 1);
			}
			if (r.skill_array.get(index).enable) {
				img.addListener(new InputListener() {
					@Override
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {

						TipsWindows.getInstance().showSkillInfo(
								r.skill_array.get(index), v, RoleInfoStage);
						U.setSkillImg(skillImg, img);
						return true;
					}

					@Override
					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						skillIndex = index;
						super.touchUp(event, x, y, pointer, button);
					}
				});
			}
		}
		// wfy.makeImg(r.weapon.texture, RoleInfoStage, 1f, 40, 100);
		// wfy.makeImg(r.armor.texture, RoleInfoStage, 1f, 100, 100);
		showSkillTree(r, skillImg);
	}

	/**
	 * 基本信息
	 * 
	 * @param r
	 */
	private void showRoleBaseInfo(Role r) {
		wfy.makeLabel(r.name, RoleInfoStage, 0.6f, 40, 240,
				U.getQualityColor(r.quality));
		wfy.makeLabel("等级:" + r.level, RoleInfoStage, 0.6f, 100, 240);
		wfy.makeLabel("生命:" + r.maxHp, RoleInfoStage, 0.6f, 40, 220);
		wfy.makeLabel("经验:" + r.exp + "/" + r.expUp, RoleInfoStage, 0.6f, 100,
				220);
		wfy.makeLabel("攻击:" + r.getAttack(), RoleInfoStage, 0.6f, 40, 200);
		wfy.makeLabel("防御:" + r.getDefend(), RoleInfoStage, 0.6f, 100, 200);
	}

	/**
	 * 设置显示技能树
	 * 
	 * @param r
	 *            角色
	 */
	public void showSkillTree(final Role r, final Array<Image> imgAarray) {
		int numsGreen = 0;
		int numsBlue = 0;
		int numsPur = 0;
		int numsOra = 0;
		int ix = 200, iy = 125;
		final Array<Image> skillImg = new Array<Image>();
		for (final Skill s : r.skill_tree) {
			SkillEffect se = null;
			if (s.quality == QUALITY.green) {
				se = new SkillEffect(s, RoleInfoStage, new Vector2(ix
						+ numsGreen * 40, iy), true);
				numsGreen++;
			}
			if (s.quality == QUALITY.blue) {
				se = new SkillEffect(s, RoleInfoStage, new Vector2(ix
						+ numsBlue * 40, iy + 35), true);
				numsBlue++;
			}
			if (s.quality == QUALITY.purple) {
				se = new SkillEffect(s, RoleInfoStage, new Vector2(ix + numsPur
						* 40, iy + 70), true);
				numsPur++;
			}
			if (s.quality == QUALITY.orange) {
				se = new SkillEffect(s, RoleInfoStage, new Vector2(ix + numsOra
						* 40, iy + 105), true);
				numsOra++;
			}
			final Image skill_img = se.skillImg;
			s.skillEffect = se;
			skillImg.add(skill_img);
			final Vector2 vs = new Vector2(skill_img.getX(), skill_img.getY());
			if (s.enable) {
				se.skillImg.addListener(new InputListener() {
					@Override
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						TipsWindows.getInstance().showSkillInfo(s, vs,
								RoleInfoStage);
						U.setSkillImg(skillImg, skill_img);
						return true;
					}

					@Override
					public void touchUp(InputEvent event, float x, float y,
							int pointer, int button) {
						if ((selectSkill == null) || selectSkill != s) {
							selectSkill = s;
						} else {
							setAnotherSkill(r, skillIndex, s, imgAarray);
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
	private void setAnotherSkill(Role r, int index, Skill s,
			Array<Image> imgArray) {
		r.skill_array.set(index, s);
		r.cskill = r.skill_array.get(0);
		imgArray.get(index * 2).setDrawable(
				s.skillEffect.skillImg.getDrawable());
		imgArray.get(index * 2 + 1).setDrawable(
				s.skillEffect.skillImgEffect.getDrawable());
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
			cameraWidth = 60 * Player.getInstance().playerRole.size + 20;
			int maxW = (int) (cameraWidth - Configure.rect_width < 0 ? 0
					: cameraWidth - Configure.rect_width);
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
		sRoleStage.clear();
		RoleInfoStage.clear();
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
