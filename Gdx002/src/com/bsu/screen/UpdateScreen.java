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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.bsu.effect.MyParticle;
import com.bsu.effect.RoleHead;
import com.bsu.head.CubocScreen;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.obj.TipsWindows;
import com.bsu.tools.GC;
import com.bsu.tools.GC.QUALITY;
import com.bsu.tools.GTC;
import com.bsu.tools.MessageObject;
import com.bsu.tools.U;

public class UpdateScreen extends CubocScreen implements Observer,
		GestureListener {
	private Stage stage;
	private Stage sRoleStage;
	private Stage upRoleStage;
	private Image background;
	private Image ib_back;
	private Image allImg;
	private Image greenImg;
	private Image blueImg;
	private Image purpleImg;
	private Image orangeImg;
	private Image eatImg;
	private Image eatAllImg;// 一键吞噬所有某种品质
	private Image upButton;// 升级按钮
	private Role suRole;
	private Array<Role> eatRoles = new Array<Role>();
	private QUALITY quality;// 当前选择显示的品质
	private Array<Image> bImg = new Array<Image>();
	private Label infos;
	private boolean initFlag;

	public UpdateScreen(Game game) {
		super(game);
		stage = new Stage(GC.rect_width, GC.rect_height, false);
		sRoleStage = new Stage(GC.rect_width, GC.rect_height,
				false);
		upRoleStage = new Stage(GC.rect_width, GC.rect_height,
				false);
	}
	public void initScreen(){
		if(!initFlag){
			background = new Image(GTC.getInstance().updatePanel);
			infos = WidgetFactory.getInstance().makeLabel("", 1, 135, 280);
			upButton = WidgetFactory.getInstance().makeImageButton(
						GC.button_up, 300, 274, 1);
			ib_back = WidgetFactory.getInstance().makeImageButton(
						GC.button_back, 366, 267, 1f);
			allImg = WidgetFactory.getInstance().makeImageButton(
						GC.button_all, 135, 50, 0.5f);
			greenImg = WidgetFactory.getInstance().makeImageButton(
						GC.button_green, 198, 50, 0.5f);
			blueImg = WidgetFactory.getInstance().makeImageButton(
						GC.button_blue,  261, 50, 0.5f);
			purpleImg = WidgetFactory.getInstance().makeImageButton(
						GC.button_purple,324, 50, 0.5f);
			orangeImg = WidgetFactory.getInstance().makeImageButton(
						GC.button_orange, 387, 50, 0.5f);
			eatImg = WidgetFactory.getInstance().makeImageButton(
						GC.button_eat, 250, 20, 1f);
			eatAllImg = WidgetFactory.getInstance().makeImageButton(
						GC.button_eatall,  350, 15, 1);
			setListener();												
			initFlag=true;
		}
		stage.clear();
		bImg.clear();
		stage.addActor(background);
		stage.addActor(infos);
		stage.addActor(upButton);
		stage.addActor(ib_back);
		stage.addActor(allImg);
		stage.addActor(greenImg);
		stage.addActor(blueImg);
		stage.addActor(purpleImg);
		stage.addActor(orangeImg);
		stage.addActor(eatImg);
		stage.addActor(eatAllImg);
		bImg.add(allImg);
		bImg.add(greenImg);
		bImg.add(blueImg);
		bImg.add(purpleImg);
		bImg.add(orangeImg);
		getRoles();
		addRoleToStage(QUALITY.all);
	}
	/**
	 * 显示要强化角色列表
	 */
	private void getRoles() {
		eatRoles.clear();
		upRoleStage.clear();
		final Array<Role> fightRole = Player.getInstance().getPlayerFightRole();	
		for (int i = 0; i < fightRole.size; i++) {
			final Role r = fightRole.get(i);
			Vector2 v = new Vector2(48, 246 - 55 * i);
			RoleHead photo = new RoleHead(r,false);
			photo.setPosition(v.x, v.y);
			upRoleStage.addActor(photo);
			r.roleIcon=photo;
			photo.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					U.showRoleSelect(fightRole, r);
					suRole = r;
					showUpRoleInfo();
					return super.touchDown(event, x, y, pointer, button);
				}
			});
		}
		suRole = fightRole.get(0);
		U.showRoleSelect(fightRole, suRole);
		showUpRoleInfo();
	}

	/**
	 * 显示要强化角色信息
	 */
	private void showUpRoleInfo() {
		infos.setFontScale(0.7f);
		infos.setText(suRole.name + "  lv:" + suRole.level
				+ "  exp:" + suRole.exp + "/"
				+ U.getUpExp(suRole,suRole.level));
		upButton.setColor(upButton.getColor().r, upButton.getColor().g,
				upButton.getColor().b,
				(suRole.exp >= U.getUpExp(suRole,suRole.level) ? 1 : 0f));
	}

	/**
	 * 当点击卡片按钮时添加背包中卡片到舞台，并根据当前所选类型显示
	 */
	private void addRoleToStage(QUALITY q) {
		quality = q;
		Image simg = null;
		Array<Role> idleRole = Player.getInstance().getPlayerIdelRole();
		if (quality == QUALITY.all) {
			showQualityRole(idleRole);
			simg = allImg;
		}
		if (quality == QUALITY.green) {
			showQualityRole(Player.getInstance().getQualityRole(
					idleRole, QUALITY.green));
			simg = greenImg;
		}
		if (quality == QUALITY.blue) {
			showQualityRole(Player.getInstance().getQualityRole(
					idleRole, QUALITY.blue));
			simg = blueImg;
		}
		if (quality == QUALITY.purple) {
			showQualityRole(Player.getInstance().getQualityRole(
					idleRole, QUALITY.purple));
			simg = purpleImg;
		}
		if (quality == QUALITY.orange) {
			showQualityRole(Player.getInstance().getQualityRole(
					idleRole, QUALITY.orange));
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
		int x = 150;
		int y = 70;
		int value=5;
		Table table = new Table();
		ScrollPane sp = new ScrollPane(table, U.get_skin().get(ScrollPaneStyle.class));
		sp.setWidth(300);
		sp.setHeight(180);
		sp.setPosition(x, y);
		sp.setScrollingDisabled(true, false);
		sp.setupFadeScrollBars(0f, 0f);
		sRoleStage.addActor(sp);
		table.align(Align.top);
		for (int i = 0; i < roleArray.size; i++) {
			final Role r = roleArray.get(i);
			if(r.locked){
				continue;
			}
			RoleHead photo = new RoleHead(r,false);
			r.roleIcon = photo;
			if(i%value==0&&i>0){
				table.row();
			}
			table.add(photo).width(photo.img_frame.getWidth())
					.height(photo.img_frame.getHeight()) // 设置photo宽度和高度
					.padTop(2f).padBottom(2)
					.spaceLeft(10f).spaceRight(10f); // 设置各photo之间的边距
			final Vector2 v = new Vector2(x+10 + i % 5 * 58, y+2 - i / 5 * 50);
			photo.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					resetEatRole(r);
					TipsWindows.getInstance().showEatInfo(r, v, sRoleStage);
					super.touchUp(event, x, y, pointer, button);
				}
			});
		}
		if (roleArray.size < 1) {
			TipsWindows.getInstance().showTips("没有相应品质的卡片，请通关来收集", sRoleStage,
					Color.GRAY);
		}
		showUpRoleInfo();
	}

	/**
	 * 吞噬卡片升级
	 * 
	 * @param flag
	 *            是否一键吞噬
	 */
	private void eatRolesUpdate(boolean flag) {
		if (flag) {
			Array<Role> idleRole = Player.getInstance().getPlayerIdelRole();
			eatRoles.clear();
			if (quality == QUALITY.all) {
				eatRoles = new Array<Role>(idleRole);
			}
			if (quality == QUALITY.green) {
				eatRoles = new Array<Role>(Player.getInstance().getQualityRole(
						idleRole, QUALITY.green));
			}
			if (quality == QUALITY.blue) {
				eatRoles = new Array<Role>(Player.getInstance().getQualityRole(
						idleRole, QUALITY.blue));
			}
			if (quality == QUALITY.purple) {
				eatRoles = new Array<Role>(Player.getInstance().getQualityRole(
						idleRole, QUALITY.purple));
			}
			if (quality == QUALITY.orange) {
				eatRoles = new Array<Role>(Player.getInstance().getQualityRole(
						idleRole, QUALITY.orange));
			}
		}
		for (Role e : eatRoles) {
			suRole.exp += e.classes.equals(suRole.classes)?e.exp*1.2f:e.exp;
			Player.getInstance().getRole().removeValue(e, false);
		}
		if(suRole.exp>=U.getUpExp(suRole,suRole.level)){
			TipsWindows.getInstance().showTips("可以进行等级提升", sRoleStage, Color.ORANGE);
		}
		eatRoles.clear();
		addRoleToStage(quality);
	}

	/**
	 * 设置被吞噬或者取消吞噬
	 * 
	 * @param r
	 */
	private void resetEatRole(Role r) {
		boolean flag = false;
		for (Role e : eatRoles) {
			if (e.equals(r)) {
				flag = true;
			}
		}
		if (flag) {
			eatRoles.removeValue(r, false);

		} else {
			eatRoles.add(r);
		}
		U.showRolesSelect(Player.getInstance().getPlayerIdelRole(), eatRoles);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(null);
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(upRoleStage);// 必须先加这个。。。。
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
		upRoleStage.act(Gdx.graphics.getDeltaTime());
		upRoleStage.draw();
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		sRoleStage.clear();
		upRoleStage.clear();
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
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QUALITY.purple);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		orangeImg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QUALITY.orange);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		blueImg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QUALITY.blue);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		greenImg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QUALITY.green);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		allImg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				ib_back.setScale(0.95f);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addRoleToStage(QUALITY.all);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		eatImg.addListener(new InputListener() {
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
		eatAllImg.addListener(new InputListener() {
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
		upButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if (suRole.exp >= U.getUpExp(suRole,suRole.level)) {
					MyParticle mpe = new MyParticle(GTC.getInstance().particleEffect,
							new Vector2(suRole.roleIcon.getX()+20,suRole.roleIcon.getY()+20));
					upRoleStage.addActor(mpe);
					TipsWindows.getInstance().showRoleLevelUp(suRole, upRoleStage);
					suRole.levelUp();
					showUpRoleInfo();
				}
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
