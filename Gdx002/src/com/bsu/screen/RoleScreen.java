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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.bsu.effect.RoleIcon;
import com.bsu.effect.SkillIcon;
import com.bsu.head.CubocScreen;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.obj.TipsWindows;
import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.GC;
import com.bsu.tools.GC.QUALITY;
import com.bsu.tools.GTC;
import com.bsu.tools.U;
import com.esotericsoftware.tablelayout.BaseTableLayout;

public class RoleScreen extends CubocScreen implements Observer,
		GestureListener {
	private Stage stage;// 基本舞台
	private Stage sRoleStage;// Role舞台
	private Stage RoleInfoStage;// Role信息舞台
	private Image background;
	private Image ib_back;
	private Image allImg;
	private Image greenImg;
	private Image blueImg;
	private Image purpleImg;
	private Image orangeImg;
	private Role selectRole;// 选择显示的Role
	private Skill selectSkill;// 选择要更换的skill
	private WidgetFactory wfy;// 界面工厂类
	private int skillIndex;// 希望改变的技能
	private Array<Image> bImg = new Array<Image>();
	private QUALITY quality;// 当前选择显示的品质

	public RoleScreen(Game game) {
		super(game);
		stage = new Stage(GC.rect_width, GC.rect_height, false);
		sRoleStage = new Stage(GC.rect_width, GC.rect_height, false);
		RoleInfoStage = new Stage(GC.rect_width, GC.rect_height, false);
		wfy = WidgetFactory.getInstance();
		background = new Image(GTC.getInstance().rolePanel);
		stage.addActor(background);
		ib_back = wfy.makeImageButton(GC.button_back, stage, 375, 272, 1);
		allImg = WidgetFactory.getInstance().makeImageButton(GC.button_all,
				stage, 20, 20, 0.5f);
		greenImg = WidgetFactory.getInstance().makeImageButton(GC.button_green,
				stage, 83, 20, 0.5f);
		blueImg = WidgetFactory.getInstance().makeImageButton(GC.button_blue,
				stage, 146, 20, 0.5f);
		purpleImg = WidgetFactory.getInstance().makeImageButton(
				GC.button_purple, stage, 209, 20, 0.5f);
		orangeImg = WidgetFactory.getInstance().makeImageButton(
				GC.button_orange, stage, 272, 20, 0.5f);
		bImg.add(allImg);
		bImg.add(greenImg);
		bImg.add(blueImg);
		bImg.add(purpleImg);
		bImg.add(orangeImg);
		setListener();
	}

	/**
	 * 当点击卡片按钮时添加背包中卡片到舞台，并根据当前所选类型显示
	 */
	private void addRoleToStage(QUALITY q) {
		if (quality == q) {
			return;
		}
		Image simg = null;
		quality = q;
		if (q == QUALITY.all) {
			showQualityRole(Player.getInstance().playerRole);
			simg = allImg;
		}
		if (q == QUALITY.green) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerRole, QUALITY.green));
			simg = greenImg;
		}
		if (q == QUALITY.blue) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerRole, QUALITY.blue));
			simg = blueImg;
		}
		if (q == QUALITY.purple) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerRole, QUALITY.purple));
			simg = purpleImg;
		}
		if (q == QUALITY.orange) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerRole, QUALITY.orange));
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
		RoleInfoStage.clear();
		/*
		 * 滑动容器
		 */
		Table table = new Table();
		ScrollPane sp = new ScrollPane(table, U.get_skin().get(ScrollPaneStyle.class));
		sp.setWidth(441);
		sp.setHeight(65);
		sp.setPosition(20, 45);
		sp.setScrollingDisabled(false, true);
		sp.setupFadeScrollBars(0f, 0f);
		sRoleStage.addActor(sp);
		for (int i = 0; i < roleArray.size; i++) {
			final Role r = roleArray.get(i);
			final RoleIcon photo = new RoleIcon(r, false);
			table.add(photo).width(photo.img_frame.getWidth())
					.height(photo.img_frame.getHeight()) // 设置photo宽度和高度
					.padTop(2f).align(Align.top)// 没起作用。。。
					.spaceLeft(10f).spaceRight(10f); // 设置各photo之间的边距
			photo.addListener(new InputListener() {
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
			SkillIcon se = new SkillIcon(skill, RoleInfoStage, new Vector2(
					40 + i * 60, 160), true);
			final Image img = se.skillImg;
			skill.skillEffect = se;
			final Vector2 v = new Vector2(img.getX(), img.getY());
			skillImg.add(img);
			skillImg.add(se.skillImgEffect);
			if (i == 0) {
				U.setAlpha(img, 1);
			}
			img.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					TipsWindows.getInstance().showSkillInfo(
							r.skill_array.get(index), v, RoleInfoStage);
					U.setCurrentSkillImg(skillImg, img);
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
		wfy.makeLabel(r.name, RoleInfoStage, 1f, 40, 240,
				U.getQualityColor(r.quality));
		wfy.makeLabel("等级:" + r.level, RoleInfoStage, 0.5f, 100, 240);
		wfy.makeLabel("生命:" + r.maxHp, RoleInfoStage, 0.5f, 40, 220);
		wfy.makeLabel("经验:" + r.exp + "/" + r.expUp, RoleInfoStage, 0.5f, 100,
				220);
		wfy.makeLabel("攻击:" + r.getAttack(), RoleInfoStage, 0.5f, 40, 200);
		wfy.makeLabel("防御:" + r.getDefend(), RoleInfoStage, 0.5f, 100, 200);
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
		int ix = 200, iy = 125, height = 35;
		final Array<Image> skillImg = new Array<Image>();
		for (final Skill s : r.skill_tree) {
			SkillIcon se = null;
			if (s.quality == QUALITY.green) {
				se = new SkillIcon(s, RoleInfoStage, new Vector2(ix
						+ numsGreen * 40, iy), true);
				numsGreen++;
			}
			if (s.quality == QUALITY.blue) {
				se = new SkillIcon(s, RoleInfoStage, new Vector2(ix
						+ numsBlue * 40, iy + height), true);
				numsBlue++;
			}
			if (s.quality == QUALITY.purple) {
				se = new SkillIcon(s, RoleInfoStage, new Vector2(ix + numsPur
						* 40, iy + height * 2), true);
				numsPur++;
			}
			if (s.quality == QUALITY.orange) {
				se = new SkillIcon(s, RoleInfoStage, new Vector2(ix + numsOra
						* 40, iy + height * 3), true);
				numsOra++;
			}
			final Image skill_img = se.skillImg;
			s.skillEffect = se;
			skillImg.add(skill_img);
			final Vector2 vs = new Vector2(skill_img.getX(), skill_img.getY());
			if (s.enable) {
				skill_img.addListener(new InputListener() {
					@Override
					public boolean touchDown(InputEvent event, float x,
							float y, int pointer, int button) {
						TipsWindows.getInstance().showSkillInfo(s, vs,
								RoleInfoStage);
						U.setSelectImg(skillImg, skill_img);
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
		addRoleToStage(QUALITY.all);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
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
		quality = null;
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
				notifyObservers(GC.button_back);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		purpleImg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				for (Image img : bImg)
					U.setAlpha(img, 0.5f);
				U.setAlpha(event.getListenerActor(), 1.0f);
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
				for (Image img : bImg)
					U.setAlpha(img, 0.5f);
				U.setAlpha(event.getListenerActor(), 1.0f);
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
				for (Image img : bImg)
					U.setAlpha(img, 0.5f);
				U.setAlpha(event.getListenerActor(), 1.0f);
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
				for (Image img : bImg)
					U.setAlpha(img, 0.5f);
				U.setAlpha(event.getListenerActor(), 1.0f);
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
				for (Image img : bImg)
					U.setAlpha(img, 0.5f);
				U.setAlpha(event.getListenerActor(), 1.0f);
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
