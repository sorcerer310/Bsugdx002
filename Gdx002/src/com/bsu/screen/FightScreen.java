package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Array;
import com.bsu.head.CubocScreen;
import com.bsu.make.TipsWindows;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.LevelState;
import com.bsu.tools.GC;
import com.bsu.tools.GTC;
import com.bsu.tools.U;
import com.bsu.tools.GC.LEVELYTPE;
import com.bsu.tools.GC.QUALITY;
import com.sun.java.swing.plaf.gtk.GTKConstants.ArrowType;

public class FightScreen extends CubocScreen implements Observer {
	private Image background;
	private Stage stage;
	private Image ib_back;
	private Array<LevelState> lvArray_one = new Array<LevelState>();
	private Array<LevelState> lvArray_two = new Array<LevelState>();
	private Array<LevelState> lvArray_three = new Array<LevelState>();
	private Array<LevelState> lvArray_four = new Array<LevelState>();
	private Array<Image> lvArray_chapter = new Array<Image>();
	private LEVELYTPE state;

	public FightScreen(Game game) {
		super(game);
		stage = new Stage(GC.rect_width, GC.rect_height, false);
		background = new Image(GTC.getInstance().fightPanel);
		stage.addActor(background);
		ib_back = WidgetFactory.getInstance().makeImageButton(GC.button_back,
				stage, 360, 262, 1);
		initFight();
		setListener();
	}

	public void initFight() {
		//滑动容器
		Skin skin = new Skin(Gdx.files.internal("data/skin/bsuuiskin.json"));
//		Skin skin = new Skin(Gdx.files.internal("data/skin/uiskin.json"));
		Table table = new Table();
		ScrollPane sp = new ScrollPane(table,skin.get(ScrollPaneStyle.class));
		sp.setWidth(102);
		sp.setHeight(280);
		
		sp.setPosition(20, 20);
		sp.setScrollingDisabled(true, false);
		sp.setupFadeScrollBars(0f, 0f);
		stage.addActor(sp);
		ButtonGroup bg = new ButtonGroup();
		
		int x = 35, y = 250, h = 50, max = 16;// 4大章位置
		for (int i = 0; i < 20; i++) {
			final int index = i;
			final Image chapterImg = new Image(
					GTC.getInstance().atlas_button.findRegion("level"));
			lvArray_chapter.add(chapterImg);
			if (i == 0) {
				U.setAlpha(chapterImg, 1);
			} else {
				U.setAlpha(chapterImg, 0.7f);
			}
//			stage.addActor(chapterImg);
			//将区域按钮增加至滑动容器中
//			table.add(chapterImg)
//				.width(chapterImg.getWidth()).height(chapterImg.getHeight())
//				.space(20f);
//			table.row();
			
			/*
			 * 测试代码
			 */
//			CheckBox button = new CheckBox("bar"+i,skin.get(CheckBoxStyle.class));
//			button.setName("ahaha");
			TextButtonStyle textButtonStyle = new TextButtonStyle();
			textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
			textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
			textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
			textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
			textButtonStyle.font = U.get_font();
			textButtonStyle.font.setScale(0.8f);
			skin.add("bsutoggle", textButtonStyle);			
			TextButton button = new TextButton("第一关", skin.get("bsutoggle",TextButtonStyle.class));
			
			
			button.setWidth(50f);
			table.add(button)
					.width(button.getWidth()).height(button.getHeight())
					.pad(10.0f);
			table.row();
//			button.setChecked(true);
			bg.add(button);
			bg.setChecked("bar1");
			
			chapterImg.setPosition(x, y - i * h);
			chapterImg.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					U.setSelectImg(lvArray_chapter, chapterImg);
					show_chapter_info(getType(index));
					super.touchUp(event, x, y, pointer, button);
				}
			});
		}
		for (int i = 0; i < max; i++) {
			lvArray_one.add(new LevelState(LEVELYTPE.chapter1, i, false));
			lvArray_two.add(new LevelState(LEVELYTPE.chapter2, i + max, false));
			lvArray_three.add(new LevelState(LEVELYTPE.chapter3, i + max * 2,
					false));
			lvArray_four.add(new LevelState(LEVELYTPE.chapter4, i + max * 3,
					false));
		}
	}

	/**
	 * 返回目标的类型
	 * 
	 * @param index
	 *            索引
	 * @return 关卡类型（第几章）
	 */
	private LEVELYTPE getType(int index) {
		LEVELYTPE lt = null;
		if (index == 0) {
			lt = LEVELYTPE.chapter1;
		}
		if (index == 1) {
			lt = LEVELYTPE.chapter2;
		}
		if (index == 2) {
			lt = LEVELYTPE.chapter3;
		}
		if (index == 3) {
			lt = LEVELYTPE.chapter4;
		}
		return lt;
	}

	private void show_chapter_info(LEVELYTPE type) {
		if (state == type) {
			return;
		}
		clearAllImg();
		TipsWindows.getInstance().removeFromStage();
		state = type;
		Array<LevelState> ls = null;
		if (type == LEVELYTPE.chapter1) {
			ls = lvArray_one;
		}
		if (type == LEVELYTPE.chapter2) {
			ls = lvArray_two;
		}
		if (type == LEVELYTPE.chapter3) {
			ls = lvArray_three;
		}
		if (type == LEVELYTPE.chapter4) {
			ls = lvArray_four;
		}
		int index = 0;
		int x = 140, y = 220, w = 80, h = 45;
		for (final LevelState l : ls) {
			stage.addActor(l.icon);
			if (l.level <= GameScreen.lv) {
				l.enableLevel();
			}
			l.icon.setPosition(x + index % 4 * w, y - index / 4 * h);
			index++;
			l.icon.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					if (l.enable) {
						GameScreen.lv = l.level;
						setChanged();
						notifyObservers(GC.screen_game);
					}else{
						TipsWindows.getInstance().showTips("关卡未开启，请通关上一关卡", stage, Color.RED);
					}
					super.touchUp(event, x, y, pointer, button);
				}
			});

		}
	}

	/**
	 * 更改章节时情况所有章节关卡
	 */
	private void clearAllImg() {
		for (LevelState l : lvArray_one) {
			if (l.icon.getParent() != null) {
				l.icon.getParent().removeActor(l.icon);
			}
		}
		for (LevelState l : lvArray_two) {
			if (l.icon.getParent() != null) {
				l.icon.getParent().removeActor(l.icon);
			}
		}
		for (LevelState l : lvArray_three) {
			if (l.icon.getParent() != null) {
				l.icon.getParent().removeActor(l.icon);
			}
		}
		for (LevelState l : lvArray_four) {
			if (l.icon.getParent() != null) {
				l.icon.getParent().removeActor(l.icon);
			}
		}
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(null);
		Gdx.input.setInputProcessor(stage);
		show_chapter_info(LEVELYTPE.chapter1);
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
		state=null;
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
