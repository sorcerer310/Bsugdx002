package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.bsu.effect.RoleIcon;
import com.bsu.head.CubocScreen;
import com.bsu.make.WidgetFactory;
import com.bsu.make.GameScreenFactory;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.obj.TipsWindows;
import com.bsu.tools.GC;
import com.bsu.tools.MessageObject;
import com.bsu.tools.U;
import com.bsu.tools.GC.QUALITY;
import com.bsu.tools.GTC;

public class SelectRoleScreen extends CubocScreen implements Observer,
		GestureListener {

	private Stage stage;
	private Stage sRoleStage;
	private Image background;
	private Image ib_back;
	private Image allImg;
	private Image greenImg;
	private Image blueImg;
	private Image purpleImg;
	private Image orangeImg;
	private Role changeRole;
	private Role selectRole;
	private Array<Image> bImg = new Array<Image>();
	private QUALITY quality;// 当前选择显示的品质

	public SelectRoleScreen(Game game) {
		super(game);
		stage = new Stage(GC.rect_width, GC.rect_height, false);
		sRoleStage = new Stage(GC.rect_width, GC.rect_height, false);
		background = new Image(GTC.getInstance().selectRolePanel);
		stage.addActor(background);
		ib_back = WidgetFactory.getInstance().makeImageButton(GC.button_back,
				stage, 375, 266, 1f);
		allImg = WidgetFactory.getInstance().makeImageButton(GC.button_all,
				stage, 100, 20, 0.5f);
		greenImg = WidgetFactory.getInstance().makeImageButton(GC.button_green,
				stage, 163, 20, 0.5f);
		blueImg = WidgetFactory.getInstance().makeImageButton(GC.button_blue,
				stage, 226, 20, 0.5f);
		purpleImg = WidgetFactory.getInstance().makeImageButton(
				GC.button_purple, stage, 289, 20, 0.5f);
		orangeImg = WidgetFactory.getInstance().makeImageButton(
			GC.button_orange, stage, 352, 20, 0.5f);
		bImg.add(allImg);
		bImg.add(greenImg);
		bImg.add(blueImg);
		bImg.add(purpleImg);
		bImg.add(orangeImg);
		setListener();
	}

	/**
	 * 设置要更改队伍的角色
	 * 
	 * @param r
	 */
	public void setChangeRole(Role r) {
		changeRole = r;
	}

	/**
	 * 当点击卡片按钮时添加背包中卡片到舞台，并根据当前所选类型显示
	 */
	private void addRoleToStage(QUALITY q) {
		if(quality==q){
			return;
		}
		selectRole = null;
		Image simg = null;
		quality=q;
		if (q == QUALITY.green) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerIdelRole, QUALITY.green));
			simg = greenImg;
		}
		if (q == QUALITY.blue) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerIdelRole, QUALITY.blue));
			simg = blueImg;
		}
		if (q == QUALITY.purple) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerIdelRole, QUALITY.purple));
			simg = purpleImg;
		}
		if (q == QUALITY.orange) {
			showQualityRole(Player.getInstance().getQualityRole(
					Player.getInstance().playerIdelRole, QUALITY.orange));
			simg = orangeImg;
		}
		if (q == QUALITY.all) {
			showQualityRole(Player.getInstance().playerIdelRole);
			simg = allImg;
		}
		U.setSelectImg(bImg, simg);
	}

	/**
	 * 显示某一品质的role
	 * 
	 * @param imgArray
	 *            某一品质的role Image数组
	 */
	private void showQualityRole(final Array<Role> roleArray) {
		sRoleStage.clear();
		int x = 60;
		int y = 70;
		int value=6;
		Table table = new Table();
		ScrollPane sp = new ScrollPane(table, U.get_skin().get(ScrollPaneStyle.class));
		sp.setWidth(360);
		sp.setHeight(180);
		sp.setPosition(x, y);
		sp.setScrollingDisabled(true, false);
		sp.setupFadeScrollBars(0f, 0f);
		sRoleStage.addActor(sp);
		table.align(Align.top);
		for (int i = 0; i < roleArray.size; i++) {
			final Role r = roleArray.get(i);
			RoleIcon photo = new RoleIcon(r, false);
			if(i%value==0&&i>0){
				table.row();
			}
			table.add(photo).width(photo.img_frame.getWidth())
					.height(photo.img_frame.getHeight()) // 设置photo宽度和高度
					.padTop(2f).align(Align.top)// 没起作用。。。
					.spaceLeft(10f).spaceRight(10f); // 设置各photo之间的边距
			r.roleIcon=photo;
			final Vector2 v = new Vector2(x+10 + i % value * 58, y+2 - i / value* 50);
			photo.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					if (selectRole != r) {
						selectRole = r;// 选定人物。。
						TipsWindows.getInstance().showRoleInfo(r, v,
								sRoleStage);
						U.showRoleSelect(Player.getInstance().playerIdelRole, r);
						return;
					}
					if (changeRole != null) {
						int index = 0;
						for (int i = 0; i < Player.getInstance().playerFightRole.size; i++) {
							if (Player.getInstance().playerFightRole.get(i) == changeRole) {
								index = i;
								break;
							}
						}
						Player.getInstance().playerFightRole.removeValue(
								changeRole, false);
						Player.getInstance().addRoleToFIght(r, index);
					} else {
						Player.getInstance().addRoleToFIght(r, 100);
					}
					Player.getInstance().getPlayerPackageRole();
					setChanged();
					notifyObservers(GC.button_back);
					ib_back.setScale(1f);
					super.touchUp(event, x, y, pointer, button);
				}
			});
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
		addRoleToStage(QUALITY.all);
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
		quality=null;
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
				notifyObservers(GC.button_back);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
		purpleImg.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
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
