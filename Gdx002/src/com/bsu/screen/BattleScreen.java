package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Array;
import com.bsu.head.CubocScreen;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.LevelState;
import com.bsu.obj.TipsWindows;
import com.bsu.tools.GC;
import com.bsu.tools.GTC;
import com.bsu.tools.U;
import com.bsu.tools.GC.LEVELYTPE;

public class BattleScreen extends CubocScreen implements Observer {
	private Image background;
	private Stage stage;
	private Image ib_back;
	private boolean initFlag;
	private WidgetGroup lvButtonGroup;

	public BattleScreen(Game game) {
		super(game);
		stage = new Stage(GC.rect_width, GC.rect_height, false);
	}

	public void initScreen() {
		stage.clear();
		if (!initFlag) {
			background = new Image(GTC.getInstance().fightPanel);

			ib_back = WidgetFactory.getInstance().makeImageButton(
					GC.button_back, 360, 262, 1);
			lvButtonGroup = new WidgetGroup();
			setListener();
			initFlag = true;
		}
		stage.addActor(background);
		stage.addActor(ib_back);
		initFight();
		show_chapter_level(0);
	}

	public void initFight() {
		// 滑动容器
		Table table = new Table();
		ScrollPane sp = new ScrollPane(table, U.get_skin().get(
				ScrollPaneStyle.class));
		sp.setWidth(102);
		sp.setHeight(280);
		sp.setPosition(20, 20);
		sp.setScrollingDisabled(true, false);
		sp.setupFadeScrollBars(0f, 0f);
		stage.addActor(sp);
		ButtonGroup bg = new ButtonGroup();
		for (int i = 0; i < 10; i++) {
			final int index=i;
			TextButton button = new TextButton("第" + (i + 1) + "章",
					U.get_text_button_style());
			button.setWidth(50f);
			button.getLabel().setFontScale(0.8f);
			table.add(button).width(button.getWidth())
					.height(button.getHeight()).pad(10.0f);
			table.row();
			bg.add(button);
			button.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
						show_chapter_level(index);
					super.touchUp(event, x, y, pointer, button);
				}
			});
			bg.setChecked("bar1");
		}
	}

	private void show_chapter_level(int index) {
		if (lvButtonGroup != null) {
			lvButtonGroup.remove();
			lvButtonGroup.clear();
		}
		int lv_index=index*20;
		int x = 140, y = 220, w = 80, h = 45;
		for (int i = 0; i < 20; i++) {
			final LevelState ls = new LevelState(lv_index+i, false);
			if (ls.level <= GameScreen.LvMax) {
				ls.enableLevel();
			}
			lvButtonGroup.addActor(ls.icon);
			ls.icon.setPosition(x + i % 4 * w, y - i / 4 * h);
			ls.icon.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					if (ls.enable) {
						GameScreen.lv = ls.level;
						setChanged();
						notifyObservers(GC.screen_game);
					} else {
						TipsWindows.getInstance().showTips("关卡未开启，请通关上一关卡",
								stage, Color.RED);
					}
					super.touchUp(event, x, y, pointer, button);
				}
			});
		}
		stage.addActor(lvButtonGroup);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(null);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
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
				notifyObservers(GC.button_back);
				ib_back.setScale(1f);
				super.touchUp(event, x, y, pointer, button);
			}
		});
	}
}
