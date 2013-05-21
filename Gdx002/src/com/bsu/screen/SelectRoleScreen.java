package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.bsu.head.CubocScreen;
import com.bsu.make.ButtonFactory;
import com.bsu.make.RoleFactory;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.tools.Configure;
import com.bsu.tools.Configure.QUALITY;
import com.bsu.tools.Configure.QualityS;
import com.bsu.tools.GameTextureClass;

public class SelectRoleScreen extends CubocScreen implements Observer, GestureListener {
	private Texture timg;
	private Image background;
	private Stage stage;
	private Stage sRoleStage;
	private QualityS quality;
	private Image ib_back;
	private TextButton allButton;
	private TextButton greenButton;
	private TextButton blueButton;
	private TextButton purpleButton;
	private TextButton orangeButton;
	private Role changeRole;
	
	public SelectRoleScreen(Game game) {
		super(game);
		stage = new Stage(Configure.rect_width,Configure.rect_height,false);
		sRoleStage = new Stage(Configure.rect_width, Configure.rect_height,
				false);
		background = new Image(GameTextureClass.getInstance().selectRolePanel);
		
		ib_back = ButtonFactory.getInstance().makeImageButton(Configure.button_back);
		ib_back.setPosition(380,280);
		allButton=ButtonFactory.getInstance().makeOneTextButton("all", 140, 30);
		greenButton=ButtonFactory.getInstance().makeOneTextButton("green", 180, 30);
		blueButton=ButtonFactory.getInstance().makeOneTextButton("blue", 220, 30);
		purpleButton=ButtonFactory.getInstance().makeOneTextButton("purple",260, 30);
		orangeButton=ButtonFactory.getInstance().makeOneTextButton("orange", 300, 30);
		stage.addActor(background);
		stage.addActor(ib_back);
		stage.addActor(allButton);
		stage.addActor(greenButton);
		stage.addActor(blueButton);
		stage.addActor(purpleButton);
		stage.addActor(orangeButton);
		quality=QualityS.allselect;
		addRoleToStage(QualityS.gselect);
		setListener();
	}
	public void setChangeRole(Role r){
		changeRole=r;
	}
	/**
	 * 当点击卡片按钮时添加背包中卡片到舞台，并根据当前所选类型显示
	 */
	private void addRoleToStage(QualityS qs) {
		quality=qs;
		if (quality == QualityS.gselect) {
			showQualityRole(Player.getInstance().playerGreenRole);
		}
		if (quality == QualityS.bselect) {
			showQualityRole(Player.getInstance().playerBlueRole);
		}
		if (quality == QualityS.pselect) {
			showQualityRole(Player.getInstance().playerPurpleRole);
		}
		if (quality == QualityS.oselect) {
			showQualityRole(Player.getInstance().playerOrangeRole);
		}
		if(quality==QualityS.allselect){
			showQualityRole(Player.getInstance().playerIdelRole);
		}
	}

	/**
	 * 显示某一品质的role
	 * 
	 * @param imgArray
	 *            某一品质的role Image数组
	 */
	private void showQualityRole(final Array<Role> roleArray) {
		sRoleStage.clear();
		for (int i = 0; i < roleArray.size; i++) {
			final Role r=roleArray.get(i);
			Image roleImg = new Image(r.roleTexture);
			roleImg.setScale(0.5f);
			sRoleStage.addActor(roleImg);
			roleImg.setPosition(140 + i % 5 * 70, 200 - i / 5 * 70);
			roleImg.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					ib_back.setScale(0.95f);
					return true;
				}
				@Override
				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					Player.getInstance().playerFightRole.removeValue(changeRole,false);
					Player.getInstance().playerFightRole.add(r);
					System.out.println(Player.getInstance().playerFightRole.size);
					setChanged();
					notifyObservers(Configure.button_back);
					ib_back.setScale(1f);
					super.touchUp(event, x, y, pointer, button);
				}
			});
		}
	}
	@Override
	public void show(){
		Gdx.input.setInputProcessor(null);
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
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
	}

	
	@Override
	public void hide(){
		Gdx.input.setInputProcessor(null);
	}
	
	@Override
	public void update(Observable o, Object arg) {
	}
	private void setListener(){
		ib_back.addListener(new InputListener(){
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
		purpleButton.addListener(new InputListener(){
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
		orangeButton.addListener(new InputListener(){
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
		blueButton.addListener(new InputListener(){
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
		greenButton.addListener(new InputListener(){
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
		allButton.addListener(new InputListener(){
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
				ib_back.setScale(1f);
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
