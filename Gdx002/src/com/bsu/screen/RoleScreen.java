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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.bsu.effect.MyParticle;
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
	private boolean initFlag;
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
	private Vector2 particleVec = new Vector2();// 粒子应该出现位置
	private QUALITY quality;// 当前选择显示的品质
	private Label skillPartBlue, skillPartPurple, skillPartOrange;// 普通，高级，史诗精华文本
	private TextButton up, use;
	private Array<SkillIcon> skillIconArray = new Array<SkillIcon>();

	public RoleScreen(Game game) {
		super(game);
		stage = new Stage(GC.rect_width, GC.rect_height, false);
		sRoleStage = new Stage(GC.rect_width, GC.rect_height, false);
		RoleInfoStage = new Stage(GC.rect_width, GC.rect_height, false);
	}

	public void initScreen() {
		stage.clear();
		bImg.clear();
		if (!initFlag) {
			up = WidgetFactory.getInstance().makeOneTextButton("update..", 400,
					120);
			use = WidgetFactory.getInstance().makeOneTextButton("used..", 400,
					120);
			wfy = WidgetFactory.getInstance();
			background = new Image(GTC.getInstance().rolePanel);
			ib_back = wfy.makeImageButton(GC.button_back, 375, 272, 1);
			allImg = WidgetFactory.getInstance().makeImageButton(GC.button_all,
					20, 20, 0.5f);
			greenImg = WidgetFactory.getInstance().makeImageButton(
					GC.button_green, 83, 20, 0.5f);
			blueImg = WidgetFactory.getInstance().makeImageButton(
					GC.button_blue, 146, 20, 0.5f);
			purpleImg = WidgetFactory.getInstance().makeImageButton(
					GC.button_purple, 209, 20, 0.5f);
			orangeImg = WidgetFactory.getInstance().makeImageButton(
					GC.button_orange, 272, 20, 0.5f);
			setListener();
			initFlag = true;
		}
		bImg.add(allImg);
		bImg.add(greenImg);
		bImg.add(blueImg);
		bImg.add(purpleImg);
		bImg.add(orangeImg);
		quality = null;
		stage.addActor(background);
		stage.addActor(ib_back);
		stage.addActor(allImg);
		stage.addActor(purpleImg);
		stage.addActor(blueImg);
		stage.addActor(orangeImg);
		stage.addActor(greenImg);
		addRoleToStage(QUALITY.all);
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
		ScrollPane sp = new ScrollPane(table, U.get_skin().get(
				ScrollPaneStyle.class));
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
		skillIconArray.clear();
		selectRole = r;
		up.remove();
		use.remove();
		if (selectRole == null) {
			return;
		}
		U.showRoleSelect(Player.getInstance().playerRole, r);
		skillIndex = 0;
		showRoleBaseInfo(r);
		int sindex = 0;
		final Array<Image> skillImg = new Array<Image>();
		for (final Skill s : r.skill_array) {
			final int index = sindex;
			sindex++;
			SkillIcon se = new SkillIcon(s, RoleInfoStage, new Vector2(
					40 + index * 60, 160), true);
			skillIconArray.add(se);
			final Image img = se.skillImg;
			skillImg.add(img);
			final Vector2 v = new Vector2(img.getX(), img.getY());
			if (index == 0) {
				U.setAlpha(img, 1);
			}
			img.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {

					TipsWindows.getInstance()
							.showSkillInfo(s, v, RoleInfoStage);
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
		showSkillTree(r);
	}

	/**
	 * 基本信息
	 * 
	 * @param r
	 */
	private void showRoleBaseInfo(Role r) {
		Label name = wfy.makeLabel(r.name, 1f, 40, 240,
				U.getQualityColor(r.quality));
		Label lv = wfy.makeLabel("等级:" + r.level, 0.5f, 100, 240);
		Label hp = wfy.makeLabel("生命:" + r.maxHp, 0.5f, 40, 220);
		Label exp = wfy
				.makeLabel("经验:" + r.exp + "/" + r.expUp, 0.5f, 100, 220);
		Label attack = wfy.makeLabel("攻击:" + r.getAttack(), 0.5f, 40, 200);
		Label defend = wfy.makeLabel("防御:" + r.getDefend(), 0.5f, 100, 200);
		RoleInfoStage.addActor(name);
		RoleInfoStage.addActor(lv);
		RoleInfoStage.addActor(hp);
		RoleInfoStage.addActor(exp);
		RoleInfoStage.addActor(attack);
		RoleInfoStage.addActor(defend);
	}

	/**
	 * 设置显示技能树
	 * 
	 * @param r
	 *            角色
	 */
	public void showSkillTree(final Role r) {
		int numsGreen = 0;
		int numsBlue = 0;
		int numsPur = 0;
		int numsOra = 0;
		int spx = 400, spy = 220, sph = 30, spw = 40;
		for (int i = 0; i < 3; i++) {
			Image spImg = new Image(GTC.getInstance().getSkillIcon(0));
			spImg.setPosition(spx, spy - i * sph);
			String s = "";
			Color c = null;
			Label ln = null;
			if (i == 0) {
				s = Player.getInstance().crystal_blue + "";
				skillPartBlue = WidgetFactory.getInstance().makeLabel(s, 1,
						spx + spw, spy - i * sph, c);
				c = new Color(0, 0, 255, 1);
				ln = skillPartBlue;
			}
			if (i == 1) {
				s = Player.getInstance().crystal_purple + "";
				skillPartPurple = WidgetFactory.getInstance().makeLabel(s, 1,
						spx + spw, spy - i * sph, c);
				c = new Color(255, 0, 255, 1);
				ln = skillPartPurple;
			}
			if (i == 2) {
				s = Player.getInstance().crystal_orange + "";
				skillPartOrange = WidgetFactory.getInstance().makeLabel(s, 1,
						spx + spw, spy - i * sph, c);
				c = Color.ORANGE;
				ln = skillPartOrange;
			}

			RoleInfoStage.addActor(spImg);
			RoleInfoStage.addActor(ln);
		}
		int ix = 200, iy = 125, height = 40, sw = 45;
		final Array<Image> skillImg = new Array<Image>();
		for (final Skill s : r.skill_tree) {
			SkillIcon se = null;
			if (s.quality == QUALITY.green) {
				se = new SkillIcon(s, RoleInfoStage, new Vector2(ix + numsGreen
						* sw, iy), true);
				numsGreen++;
			}
			if (s.quality == QUALITY.blue) {
				se = new SkillIcon(s, RoleInfoStage, new Vector2(ix + numsBlue
						* sw, iy + height), true);
				numsBlue++;
			}
			if (s.quality == QUALITY.purple) {
				se = new SkillIcon(s, RoleInfoStage, new Vector2(ix + numsPur
						* sw, iy + height * 2), true);
				numsPur++;
			}
			if (s.quality == QUALITY.orange) {
				se = new SkillIcon(s, RoleInfoStage, new Vector2(ix + numsOra
						* sw, iy + height * 3), true);
				numsOra++;
			}
			final Image skill_img = se.skillImg;
			skillImg.add(skill_img);
			final Vector2 vs = new Vector2(skill_img.getX(), skill_img.getY());
			skill_img.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					if (s.enable) {
						TipsWindows.getInstance().showSkillInfo(s, vs,
								RoleInfoStage);
						U.setSelectImg(skillImg, skill_img);
					}
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					if (!s.enable) {
						selectSkill = s;
						isReadyToUp(s);
					} else {
						if ((selectSkill == null) || selectSkill != s) {
							selectSkill = s;
						} else {
							setAnotherSkill(r, skillIndex, s);
						}
						isReadyToUp(s);
					}
					particleVec.x = vs.x + skill_img.getWidth() / 2;
					particleVec.y = vs.y + skill_img.getHeight() / 2;
					super.touchUp(event, x, y, pointer, button);
				}
			});
		}
	}

	/**
	 * 检测是否可以开启或者升级
	 * 
	 * @param s
	 */
	private void isReadyToUp(Skill s) {
		up.remove();
		use.remove();
		if ((s.quality == QUALITY.green) || (s.quality == QUALITY.blue)) {
			if (Player.getInstance().crystal_blue >= 6) {
				if (s.enable) {
					RoleInfoStage.addActor(up);
				} else {
					RoleInfoStage.addActor(use);
				}
			}
		}
		if (s.quality == QUALITY.purple) {
			if (Player.getInstance().crystal_purple >= 6) {
				if (s.enable) {
					RoleInfoStage.addActor(up);
				} else {
					RoleInfoStage.addActor(use);
				}
			}
		}
		if (s.quality == QUALITY.orange) {
			if (Player.getInstance().crystal_orange >= 6) {
				if (s.enable) {
					RoleInfoStage.addActor(up);
				} else {
					RoleInfoStage.addActor(use);
				}
			}
		}
	}

	/**
	 * 开启或者升级技能
	 * 
	 * @param b
	 *            是否升级
	 */
	private void upSkill(boolean b) {
		if ((selectSkill.quality == QUALITY.green)
				|| (selectSkill.quality == QUALITY.blue)) {
			Player.getInstance().crystal_blue -= 6;
		}
		if (selectSkill.quality == QUALITY.purple) {
			Player.getInstance().crystal_purple -= 6;
		}
		if (selectSkill.quality == QUALITY.orange) {
			Player.getInstance().crystal_orange -= 6;
		}
		skillPartBlue.setText(Player.getInstance().crystal_blue + "");
		skillPartPurple.setText(Player.getInstance().crystal_purple + "");
		skillPartOrange.setText(Player.getInstance().crystal_orange + "");
		selectSkill.enable = true;
		String tipsString = "";
		showEnabledSkill(selectSkill);
		if (!b) {
			selectSkill.skillEffect.skillImg
					.setDrawable(selectSkill.skillEffect.enableImg
							.getDrawable());
			tipsString = "开启新技能" + selectSkill.name;
		} else {
			tipsString = "技能" + selectSkill.name + "升级";
			selectSkill.levUp();
//			selectSkill.lev++;
//			selectSkill.val+=selectSkill.uval;
		}
		MyParticle mpe = new MyParticle(GTC.getInstance().particleEffect, 1,
				particleVec, RoleInfoStage, tipsString,
				U.getQualityColor(selectSkill.quality));
		RoleInfoStage.addActor(mpe);
		selectSkill.skillEffect.lv.setText(selectSkill.lev + "");
		for (int i = 0; i < selectRole.skill_array.size; i++) {
			if (selectRole.skill_array.get(i).equals(selectSkill)) {
				skillIconArray.get(i).skillImg
						.setDrawable(selectSkill.skillEffect.skillImg
								.getDrawable());
				skillIconArray.get(i).skillImgEffect
						.setDrawable(selectSkill.skillEffect.skillImgEffect
								.getDrawable());
				skillIconArray.get(i).lv.setText(selectSkill.lev + "");
			}
		}
		isReadyToUp(selectSkill);
	}

	/**
	 * 显示刚开启的技能
	 */
	private void showEnabledSkill(Skill ss) {
		for (Skill s : selectRole.skill_tree) {
			if (s != ss) {
				U.setAlpha(s.skillEffect.skillImg, 0.5f);
			} else {
				U.setAlpha(ss.skillEffect.skillImg, 1);
			}
		}
	}

	/**
	 * 重新给指定技能更换新技能
	 * 
	 * @param s
	 * @param img
	 */
	private void setAnotherSkill(Role r, int index, Skill s) {
		skillIconArray.get(index).skillImg.setDrawable(s.skillEffect.skillImg
				.getDrawable());
		skillIconArray.get(index).skillImgEffect
				.setDrawable(s.skillEffect.skillImgEffect.getDrawable());
		skillIconArray.get(index).lv.setText(s.lev + "");
		r.skill_array.set(index, s);
		r.cskill = r.skill_array.get(0);
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
		up.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				upSkill(true);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		use.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				upSkill(false);
				super.touchUp(event, x, y, pointer, button);
			}
		});
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
