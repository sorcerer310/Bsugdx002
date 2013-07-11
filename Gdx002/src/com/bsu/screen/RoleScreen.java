package com.bsu.screen;

import java.awt.Component.BaselineResizeBehavior;
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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.bsu.effect.MyParticle;
import com.bsu.effect.RoleIcon;
import com.bsu.effect.SkillIcon;
import com.bsu.head.CubocScreen;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.obj.Role.BATLESTATE;
import com.bsu.obj.RoleObsever;
import com.bsu.obj.TipsWindows;
import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.GC;
import com.bsu.tools.GC.QUALITY;
import com.bsu.tools.GTC;
import com.bsu.tools.U;

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
	private TextButton up, use;
	private Array<SkillIcon> skillTreeIconArray = new Array<SkillIcon>();// 人物技能树Icon数组
	private Array<SkillIcon> skillIconArray = new Array<SkillIcon>();// 人物头像Icon数组
	private Array<Label> skillCysLabelArray = new Array<Label>();// 玩家技能碎片文本数组
	private Array<Image> skillCysImgArray = new Array<Image>();// 玩家技能碎片图像数组
	private WidgetGroup infoGroup = new WidgetGroup();// 角色基本信息容器，其他数组也可以使用widgetGroup容器
	private RoleObsever ro;;

	public RoleScreen(Game game) {
		super(game);
		stage = new Stage(GC.rect_width, GC.rect_height, false);
		sRoleStage = new Stage(GC.rect_width, GC.rect_height, false);
		RoleInfoStage = new Stage(GC.rect_width, GC.rect_height, false);
		ro = new RoleObsever();
		ro.addObserver(this);
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
		selectRole = null;
		Image simg = null;
		quality = q;
		if (q == QUALITY.all) {
			simg = allImg;
		}
		if (q == QUALITY.green) {
			simg = greenImg;
		}
		if (q == QUALITY.blue) {
			simg = blueImg;
		}
		if (q == QUALITY.purple) {
			simg = purpleImg;
		}
		if (q == QUALITY.orange) {
			simg = orangeImg;
		}
		showQualityRole(Player.getInstance().getQualityRole(
				Player.getInstance().playerRole, quality));
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
			final RoleIcon photo = new RoleIcon(r, true);
			table.add(photo).width(photo.img_frame.getWidth())
					.height(photo.img_frame.getHeight()) // 设置photo宽度和高度
					.padTop(2f).align(Align.top)// 没起作用。。。
					.spaceLeft(10f).spaceRight(10f); // 设置各photo之间的边距
			addListenerForActor(2, r, null, photo, null, "role_info");
		}
		if (roleArray.size <= 0) {
			RoleInfoStage.clear();
			TipsWindows.getInstance().showTips("没有相应品质卡片，通过可收集", RoleInfoStage,
					Color.GRAY);
		} else {
			showRoleInfo(selectRole == null ? roleArray.get(0) : selectRole);
		}
	}

	/**
	 * 显示人物信息,人物为空，或者与之前相同不处理
	 */
	public void showRoleInfo(final Role r) {
		if (r == null) {
			return;
		}
		if (selectRole == r) {
			selectRole.roleIcon.showEffect(true);
			return;
		}
		RoleInfoStage.clear();
		skillIconArray.clear();
		selectRole = r;
		up.remove();
		use.remove();
		U.showRoleSelect(Player.getInstance().playerRole, r);
		skillIndex = 0;
		selectSkill = null;
		showRoleSkill(r);
		showRoleBaseInfo(r);
		showSkillTree(r);
		showCrystal();
	}

	// 绘制角色当前携带的2个技能
	public void showRoleSkill(Role r) {
		for (SkillIcon si : skillIconArray) {
			si.remove();
		}
		skillIconArray.clear();
		int sindex = 0;
		Vector2 v = null;
		Array<Skill> skillArray = r.getUseSkill();
		for (int i = 0; i < skillArray.size; i++) {
			final int index = sindex;
			final Skill s = skillArray.get(i);
			sindex++;
			v = new Vector2(40 + index * 60, 160);
			SkillIcon se = new SkillIcon(s, RoleInfoStage, v);
			skillIconArray.add(se);
			if (index == skillIndex) {
				U.setAlpha(s.skillIcon.skillImg, 1);
			}
			addListenerForActor(1, r, s, se.skillImg, v, "current_skill");
		}
	}

	/**
	 * 基本信息
	 * 
	 * @param r
	 */
	private void showRoleBaseInfo(Role r) {
		infoGroup.clear();
		infoGroup.remove();
		Label name = wfy.makeLabel(r.name, 1f, 40, 240,
				U.getQualityColor(r.quality));
		Label lv = wfy.makeLabel("等级:" + r.level, 0.5f, 100, 240);
		Label hp = wfy.makeLabel("生命:" + r.maxHp, 0.5f, 40, 220);
		Label exp = wfy
				.makeLabel("经验:" + r.exp + "/" + r.expUp, 0.5f, 100, 220);
		Label attack = wfy.makeLabel("攻击:" + r.getAttack(), 0.5f, 40, 200);
		Label defend = wfy.makeLabel("防御:" + r.getDefend(), 0.5f, 100, 200);
		Image unLockImg = new Image(GTC.getInstance().getSkillIcon(0));
		Image lockImg = GTC.getInstance().getClassesIconImg(r.classes);
		Image lockedImg = r.locked ? lockImg : unLockImg;
		lockedImg.setPosition(40, 120);
		Image unFightImg = new Image(GTC.getInstance().getSkillIcon(0));
		Image fightImg = GTC.getInstance().getClassesIconImg(r.classes);
		Image battleImg = r.bstate == BATLESTATE.FIGHT ? fightImg : unFightImg;
		battleImg.setPosition(80, 120);
		infoGroup.addActor(battleImg);
		infoGroup.addActor(lockedImg);
		infoGroup.addActor(name);
		infoGroup.addActor(lv);
		infoGroup.addActor(hp);
		infoGroup.addActor(exp);
		infoGroup.addActor(attack);
		infoGroup.addActor(defend);
		RoleInfoStage.addActor(infoGroup);
		addListenerForActor(0, null, null, battleImg, null, "fight");
		addListenerForActor(0, null, null, lockedImg, null, "locked");
	}

	// 设置出战与休整
	public void changeFightState(Role r) {
		if (r.bstate == BATLESTATE.FIGHT) {
			r.bstate = BATLESTATE.IDLE;
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerRole, quality));
			showRoleBaseInfo(r);
		} else {
			if (Player.getInstance().getPlayerFightRole().size >= 5) {// 出战人员已满
				TipsWindows.getInstance().showTips("队伍人员已满", RoleInfoStage,
						Color.GREEN);
			} else {
				r.bstate = BATLESTATE.FIGHT;
				showQualityRole(Player.getInstance().getQualityRole(
						Player.getInstance().playerRole, QUALITY.green));
				showRoleBaseInfo(r);
			}
		}
	}

	// 改变锁定状态
	public void changeLockState(Role r) {
		r.locked = !r.locked;
		String s = r.locked ? "锁定卡片" : "解锁卡片";
		TipsWindows.getInstance().showTips(s, RoleInfoStage, Color.GREEN);
		showRoleBaseInfo(r);
	}

	/**
	 * 设置显示技能树
	 */
	public void showSkillTree(final Role r) {
		for (SkillIcon si : skillTreeIconArray) {
			si.remove();
		}
		skillTreeIconArray.clear();
		int numsGreen = 0;
		int numsBlue = 0;
		int numsPur = 0;
		int numsOra = 0;
		int ix = 200, iy = 125, height = 40, sw = 45;
		Vector2 vs = null;
		for (final Skill s : r.skill_tree) {
			SkillIcon se = null;
			if (s.quality == QUALITY.green) {
				vs = new Vector2(ix + numsGreen * sw, iy);
				numsGreen++;
			}
			if (s.quality == QUALITY.blue) {
				vs = new Vector2(ix + numsBlue * sw, iy + height);
				numsBlue++;
			}
			if (s.quality == QUALITY.purple) {
				vs = new Vector2(ix + numsPur * sw, iy + height * 2);
				numsPur++;
			}
			if (s.quality == QUALITY.orange) {
				vs = new Vector2(ix + numsOra * sw, iy + height * 3);
				numsOra++;
			}
			se = new SkillIcon(s, RoleInfoStage, vs);
			skillTreeIconArray.add(se);
			addListenerForActor(1, r, s, se.skillImg, vs, "tree_skill");
		}
		if (selectSkill != null)
			showRoleTreeSkill(r, selectSkill);
	}

	// 选择技能树上某一技能
	public void selectTreeSkill(Skill s, Role r, Vector2 vs) {
		if (!s.enable) {
			selectSkill = s;
			isReadyToUp(s);
		} else {
			showRoleTreeSkill(r, s);
			TipsWindows.getInstance().showSkillInfo(s, vs, RoleInfoStage);
			if ((selectSkill == null) || (selectSkill != s)
					|| (selectSkill.skill_index >= 0)) {
				selectSkill = s;
			} else {
				setAnotherSkill(r, skillIndex, s);
			}
			isReadyToUp(s);
		}
		particleVec.x = vs.x + s.skillIcon.skillImg.getWidth() / 2;
		particleVec.y = vs.y + s.skillIcon.skillImg.getWidth() / 2;
		showSkillTree(r);
	}

	// 绘制玩家拥有的碎片
	public void showCrystal() {
		for (int j = 0; j < skillCysLabelArray.size; j++) {
			skillCysLabelArray.get(j).remove();
			skillCysImgArray.get(j).remove();
		}
		skillCysImgArray.clear();
		skillCysLabelArray.clear();
		int spx = 400, spy = 220, sph = 30, spw = 40;
		for (int i = 0; i < 3; i++) {
			Image spImg = new Image(GTC.getInstance().getSkillIcon(0));
			spImg.setPosition(spx, spy - i * sph);
			String s = "";
			Color c = null;
			Label ln = null;
			if (i == 0) {
				s = Player.getInstance().crystal_blue + "";
				c = new Color(0, 0, 255, 1);
			}
			if (i == 1) {
				s = Player.getInstance().crystal_purple + "";
				c = new Color(255, 0, 255, 1);
			}
			if (i == 2) {
				s = Player.getInstance().crystal_orange + "";
				c = Color.ORANGE;
			}
			ln = WidgetFactory.getInstance().makeLabel(s, 1, spx + spw,
					spy - i * sph, c);
			RoleInfoStage.addActor(spImg);
			RoleInfoStage.addActor(ln);
			skillCysLabelArray.add(ln);
			skillCysImgArray.add(spImg);
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
		if (((s.quality == QUALITY.green || s.quality == QUALITY.blue)
				&& (Player.getInstance().crystal_blue >= 6)
				|| (s.quality == QUALITY.purple && Player.getInstance().crystal_purple >= 6) || (s.quality == QUALITY.orange && Player
				.getInstance().crystal_orange >= 6)) && s.lev < 6) {
			if (s.enable) {
				RoleInfoStage.addActor(up);
			} else {
				RoleInfoStage.addActor(use);
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
		selectSkill.enable = true;
		String tipsString = "";
		showEnabledSkill(selectSkill);
		if (!b) {
			tipsString = "开启新技能" + selectSkill.name;
		} else {
			tipsString = "技能" + selectSkill.name + "升级";
			selectSkill.levUp();
		}
		MyParticle mpe = new MyParticle(GTC.getInstance().particleEffect,
				particleVec);
		ro.updateSkill(selectRole);
		RoleInfoStage.addActor(mpe);
		TipsWindows.getInstance().showTips(tipsString, RoleInfoStage,
				U.getQualityColor(selectSkill.quality));
		isReadyToUp(selectSkill);
	}

	/**
	 * 显示刚开启的技能
	 */
	private void showEnabledSkill(Skill ss) {
		for (Skill s : selectRole.skill_tree) {
			if (s != ss) {
				U.setAlpha(s.skillIcon.skillImg, 0.5f);
			} else {
				U.setAlpha(ss.skillIcon.skillImg, 1);
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
		r.getUseSkill().get(index).skill_index = -1;
		s.skill_index = index;
		ro.changeSkill(r);
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

	// 添加按钮监听
	private void setListener() {
		addListenerForActor(0, null, null, up, null, "up_skill");
		addListenerForActor(0, null, null, use, null, "enable_skill");
		addListenerForActor(0, null, null, ib_back, null, "back");
		addListenerForActor(0, null, null, allImg, null, "all");
		addListenerForActor(0, null, null, greenImg, null, "green");
		addListenerForActor(0, null, null, blueImg, null, "blue");
		addListenerForActor(0, null, null, purpleImg, null, "purple");
		addListenerForActor(0, null, null, orangeImg, null, "orange");
	}

	/**
	 * 添加一个监听 无role参数等。基本按钮监听
	 * 
	 * @param actor
	 *            被监听者
	 * @param s
	 *            信息，根据信息调用相应函数（函数无法直接当参数使用）
	 */
	public void addListenerForActor(int index, final Role r, final Skill skill,
			Actor actor, final Vector2 v, final String s) {
		final String as = s;
		Actor a = null;
		if (index == 0) {// 基本按钮，品质按钮，返回按钮
			a = actor;
		}
		if (index == 1) {// 技能按钮
			a = skill.skillIcon;
		}
		if (index == 2) {// 卡片头像按钮
			a = r.roleIcon;
		}
		a.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (as == "all" || as == "green" || as == "blue_"
						|| as == "purple" || as == "orange") {
					for (Image img : bImg)
						U.setAlpha(img, 0.5f);
					U.setAlpha(event.getListenerActor(), 1.0f);
				}
				if (as == "back") {
					ib_back.setScale(0.95f);
				}
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if (as == "all") {
					addRoleToStage(QUALITY.all);
				}
				if (as == "green") {
					addRoleToStage(QUALITY.green);
				}
				if (as == "blue") {
					addRoleToStage(QUALITY.blue);
				}
				if (as == "purple") {
					addRoleToStage(QUALITY.purple);
				}
				if (as == "orange") {
					addRoleToStage(QUALITY.orange);
				}
				if (as == "up_skill") {
					upSkill(true);
				}
				if (as == "enable_skill") {
					upSkill(false);
				}
				if (as == "fight") {
					ro.changeFight(selectRole);
				}
				if (as == "locked") {
					ro.changeLock(selectRole);
				}
				if (as == "back") {
					setChanged();
					notifyObservers(GC.button_back);
					ib_back.setScale(1f);
				}
				if (as == "tree_skill") {
					selectTreeSkill(skill, r, v);
				}
				if (as == "current_skill") {
					if (skill.enable) {
						TipsWindows.getInstance().showSkillInfo(skill, v,
								RoleInfoStage);
					}
					skillIndex = skill.skill_index;
					showRoleSkill(r);
				}
				if (as == "role_info") {
					showRoleInfo(r);
				}
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}

	// 显示当前选择的技能树技能，使其高亮，其他暗淡
	public void showRoleTreeSkill(Role r, Skill s) {
		for (Skill skill : r.skill_tree) {
			if (skill != s) {
				U.setAlpha(skill.skillIcon.skillImg, 0.5f);
			} else {
				U.setAlpha(skill.skillIcon.skillImg, 1);
			}
		}
	}
}
