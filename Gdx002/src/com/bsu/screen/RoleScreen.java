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
import com.badlogic.gdx.utils.Array;
import com.bsu.effect.MyParticle;
import com.bsu.effect.RoleIcon;
import com.bsu.effect.SkillIcon;
import com.bsu.gdx002.BsuGame;
import com.bsu.head.CubocScreen;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.obj.Role.BATLESTATE;
import com.bsu.obj.RoleView;
import com.bsu.obj.TipsWindows;
import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.GC;
import com.bsu.tools.GC.QUALITY;
import com.bsu.tools.GTC;
import com.bsu.tools.MessageObject;
import com.bsu.tools.U;

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
	private int skillIndex;// 希望改变的技能
	private Array<Image> bImg = new Array<Image>();
	private Vector2 particleVec = new Vector2();// 粒子应该出现位置
	public QUALITY quality;// 当前选择显示的品质
	private TextButton up, use;
	private WidgetGroup roleInfoGroup, roleLockGroup, roleBatleGroup,
			roleCurrentSkillGroup, roleSkillTreeGroup, crystalGroup;// 基本信息，锁定,状态，携带技能，技能树，技能碎片
	private boolean initFlag;

	public RoleScreen(Game game) {
		super(game);
		stage = new Stage(GC.rect_width, GC.rect_height, false);
		sRoleStage = new Stage(GC.rect_width, GC.rect_height, false);
		RoleInfoStage = new Stage(GC.rect_width, GC.rect_height, false);
	}

	public void initScreen() {
		addBaseToStage();
		quality = null;
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
		showQualityRole(q);
		U.setSelectImg(bImg, simg);
	}

	/**
	 * 显示某一品质的role
	 * 
	 * @param imgArray
	 *            某一品质的role Image数组
	 */
	public void showQualityRole(QUALITY q) {
		sRoleStage.clear();
		Array<Role> roleArray = Player.getInstance().getQualityRole(
				Player.getInstance().getRole(), q);
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
		for (int i = 0; i < roleArray.size; i++) {
			final Role r = roleArray.get(i);
			final RoleIcon photo = new RoleIcon(r, true);
			table.add(photo).width(photo.img_frame.getWidth())
					.height(photo.img_frame.getHeight()) // 设置photo宽度和高度
					.padTop(2f).align(Align.top)// 没起作用。。。
					.spaceLeft(10f).spaceRight(10f); // 设置各photo之间的边距
			addListenerForActor(2, r, null, photo, null, "role_info");
		}
		sRoleStage.addActor(sp);
		if (roleArray.size <= 0) {// 此品质无role
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
		if (r == null) {// 显示为空对象，返回
			return;
		}
		if (selectRole == r) {// 显示的与之前相同，返回，并设置选择效果，防止切换品质时无显示
			selectRole.roleIcon.showEffect(true);
			return;
		}
		RoleInfoStage.clear();
		// selectRole.ro.deleteObserver(this);//无法删除观察者。
		selectRole = r;
		selectRole.ro.addObserver(this);
		up.remove();// 技能升级按钮消除
		use.remove();// 启动技能按钮消除
		skillIndex=0;//每次选择查看一新role，使其显示携带的第一个技能
		selectSkill = null;// 当前选择的技能树技能
		set_role_info(selectRole);
		set_role_batle(selectRole);
		set_role_lock(selectRole);
		set_role_skill_use(selectRole);
		set_role_skill_tree(selectRole);
		set_player_crystal();
		U.showRoleSelect(Player.getInstance().getRole(), selectRole);
	}

	// 设置出战与休整
	public void changeFightState(Role r) {
		if (r.bstate == BATLESTATE.FIGHT) {
			r.bstate = BATLESTATE.IDLE;
			r.ro.notifyRoleObservers(new MessageObject(r, role_idle));
			TipsWindows.getInstance().showTips("角色休整", RoleInfoStage,
					Color.GREEN);
		} else {
			if (Player.getInstance().getPlayerFightRole().size >= 5) {// 出战人员已满
				TipsWindows.getInstance().showTips("队伍人员已满", RoleInfoStage,
						Color.GREEN);
			} else {
				r.bstate = BATLESTATE.FIGHT;
				r.ro.notifyRoleObservers(new MessageObject(r, role_fight));
				TipsWindows.getInstance().showTips("角色出战", RoleInfoStage,
						Color.GREEN);
			}
		}
	}

	// 改变锁定状态
	public void changeLockState(Role r) {
		r.locked = !r.locked;
		TipsWindows.getInstance().showTips(r.locked ? "锁定卡片" : "解锁卡片",
				RoleInfoStage, Color.GREEN);
		r.ro.notifyRoleObservers(new MessageObject(r, r.locked ? role_locked : role_unlock));
	}

	// 选择技能树上某一技能
	public void selectTreeSkill(Skill s) {
		Vector2 vs=new Vector2(s.skillIcon.skillImg.getX(),s.skillIcon.skillImg.getY());
		if (!s.enable) {
			selectSkill = s;
		} else {
			if ((selectSkill == null) || (selectSkill != s)
					|| (selectSkill.skill_index >= 0)) {
				selectSkill = s;
			} else {
				setAnotherSkill(skillIndex, s);
			}
		}
		isReadyToUp(s);
		 particleVec.x = vs.x + s.skillIcon.getWidth() / 2;
		 particleVec.y = vs.y + s.skillIcon.getWidth() / 2;
		selectRole.ro.notifyRoleObservers(new MessageObject(selectRole, role_enable_skill));
		if(s.enable)
			 TipsWindows.getInstance().showSkillInfo(s,vs, RoleInfoStage);
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
		if (!b) {
			tipsString = "开启新技能" + selectSkill.name;
		} else {
			tipsString = "技能" + selectSkill.name + "升级";
			selectSkill.levUp();
		}
		// 开启或者升级了一个技能，通知观察者
		selectRole.ro.notifyRoleObservers(new MessageObject(selectRole, !b ? role_enable_skill
				: role_level_skill));
		TipsWindows.getInstance().showTips(tipsString, RoleInfoStage,
				U.getQualityColor(selectSkill.quality));
		add_particle();
		isReadyToUp(selectSkill);
	}

	/**
	 * 重新给指定技能更换新技能
	 * 
	 * @param s
	 * @param img
	 */
	private void setAnotherSkill(int index, Skill s) {
		selectRole.getUseSkill().get(index).skill_index = -1;
		s.skill_index = index;
		selectRole.ro.notifyRoleObservers(new MessageObject(selectRole, role_change_skill));
	}

	// 绘制玩家拥有的碎片
	private WidgetGroup showCrystal() {
		WidgetGroup crystalGroup = new WidgetGroup();
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
			crystalGroup.addActor(spImg);
			crystalGroup.addActor(ln);
		}
		return crystalGroup;
	}
	//某处添加一个用来表示升级或者开启技能的粒子
	public void add_particle(){
		MyParticle mpe = new MyParticle(
				GTC.getInstance().particleEffect, particleVec);
		RoleInfoStage.addActor(mpe);
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
					changeFightState(selectRole);
				}
				if (as == "locked") {
					changeLockState(selectRole);
				}
				if (as == "back") {
					setChanged();
					notifyObservers(GC.button_back);
					ib_back.setScale(1f);
				}
				if (as == "tree_skill") {
					selectTreeSkill(skill);
				}
				if (as == "current_skill") {
					if (skill.enable) {
						TipsWindows.getInstance().showSkillInfo(skill, v,
								RoleInfoStage);
					}
					skillIndex = skill.skill_index;
					set_role_skill_use(r);
				}
				if (as == "role_info") {
					showRoleInfo(r);
				}
				super.touchUp(event, x, y, pointer, button);
			}
		});
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

	// 添加基本ACTOR到舞台
	private void addBaseToStage() {
		if (!initFlag) {
			up = WidgetFactory.getInstance().makeOneTextButton("update..", 400,
					120);
			use = WidgetFactory.getInstance().makeOneTextButton("used..", 400,
					120);
			background = new Image(GTC.getInstance().rolePanel);
			ib_back = WidgetFactory.getInstance().makeImageButton(
					GC.button_back, 375, 272, 1);
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
			bImg.add(allImg);
			bImg.add(greenImg);
			bImg.add(blueImg);
			bImg.add(purpleImg);
			bImg.add(orangeImg);
			stage.addActor(background);
			stage.addActor(ib_back);
			stage.addActor(allImg);
			stage.addActor(purpleImg);
			stage.addActor(blueImg);
			stage.addActor(orangeImg);
			stage.addActor(greenImg);
			setListener();
			initFlag = true;
		}
	}
	//设置人物基本信息
	public void set_role_info(Role r){
		if(roleInfoGroup!=null)
		roleInfoGroup.remove();
		roleInfoGroup = RoleView.showRoleBaseInfo(r);
		RoleInfoStage.addActor(roleInfoGroup);
	}
	//设置出战状态
	public void set_role_batle(Role r){
		if(roleBatleGroup!=null)
		roleBatleGroup.remove();
		roleBatleGroup = RoleView.showBatleImg(r);
		RoleInfoStage.addActor(roleBatleGroup);
		addListenerForActor(0, null, null, roleBatleGroup, null, "fight");
	
	}
	//设置锁定状态
	public void set_role_lock(Role r){
		if(roleLockGroup!=null)
		roleLockGroup.remove();
		roleLockGroup = RoleView.showLockImg(selectRole);
		RoleInfoStage.addActor(roleLockGroup);
		addListenerForActor(0, null, null, roleLockGroup, null, "locked");
	}
	//设置技能树
	public void set_role_skill_tree(Role r){
		if(roleSkillTreeGroup!=null)
		roleSkillTreeGroup.remove();
		roleSkillTreeGroup = RoleView
				.showSkillTree(selectRole, selectSkill);
		RoleInfoStage.addActor(roleSkillTreeGroup);
		for (Skill skill:selectRole.skill_tree) {
			addListenerForActor(1, r, skill,
					skill.skillIcon, new Vector2(skill.skillIcon.skillImg.getX(),skill.skillIcon.skillImg.getY()), "tree_skill");
		}
	}
	//设置当前携带技能
	public void set_role_skill_use(Role r){
		if(roleCurrentSkillGroup!=null)
		roleCurrentSkillGroup.remove();
		roleCurrentSkillGroup = RoleView.showRoleSkill(r,
				skillIndex);
		RoleInfoStage.addActor(roleCurrentSkillGroup);
		for (Skill skill:r.getUseSkill()) {
			addListenerForActor(1, r, skill,
					skill.skillIcon, new Vector2(skill.skillIcon.skillImg.getX(),skill.skillIcon.skillImg.getY()), "current_skill");
		}
	}
	//设置玩家的技能碎片
	public void set_player_crystal(){	
		if(crystalGroup!=null)
		crystalGroup.remove();
		crystalGroup = showCrystal();
		RoleInfoStage.addActor(crystalGroup);
	}
	public String role_fight = "fight";// 出战
	public String role_idle = "idle";// 休整
	public String role_locked = "locked";// 锁定
	public String role_unlock = "unlock";// 解锁
	public String role_change_skill = "change_skill";// 更换携带技能
	public String role_level_skill = "up_skill";// 升级技能树技能
	public String role_enable_skill = "enable_skill";// 开启技能树新技能

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
}
