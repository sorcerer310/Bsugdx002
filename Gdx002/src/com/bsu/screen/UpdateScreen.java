package com.bsu.screen;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.bsu.head.CubocScreen;
import com.bsu.make.ButtonFactory;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.tools.Configure;
import com.bsu.tools.Configure.QUALITY;
import com.bsu.tools.GameTextureClass;

public class UpdateScreen extends CubocScreen implements Observer {
	private Texture timg;
	private Image background;
	private Stage stage;
	private Image ib_back;
	private Array<Image> RoleImg = new Array<Image>();
	private Array<Image> roleWhiteImg = new Array<Image>();
	private Array<Image> roleGreenImg = new Array<Image>();
	private Array<Image> roleBlueImg = new Array<Image>();
	private Array<Image> rolePurpleImg = new Array<Image>();
	private Array<Image> roleOrangeImg = new Array<Image>();
	
	private Role selectUpdateRole;
	private Array<Role> eatRoles=new Array<Role>();
	private QualityS quality;//当前选择显示的品质
	public enum QualityS{
		wselect,gselect,bselect,pselect,oselect
	}

	public UpdateScreen(Game game) {
		super(game);
		stage = new Stage(Configure.rect_width, Configure.rect_height, false);
		background = new Image(GameTextureClass.getInstance().updatePanel);
		quality=QualityS.wselect;
		ib_back = ButtonFactory.getInstance().makeImageButton(
				Configure.button_back);
		ib_back.setPosition(360, 262);
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
		stage.addActor(background);
		stage.addActor(ib_back);
		Array<Role> playerRols = Player.getInstance().getPlayerRole();
		for (int i = 0; i < playerRols.size; i++) {
			Image roleImg = new Image(playerRols.get(i).roleTexture);
			Image backImg = ButtonFactory.getInstance().makeImageButton(
					Configure.Img_head_back);
			roleImg.setScale(0.5f);
			stage.addActor(backImg);
			stage.addActor(roleImg);
			roleImg.setPosition(48, 246 - 55 * i);
			backImg.setPosition(40, 240 - 55 * i);
			RoleImg.add(roleImg);
			addToRoleQuality(playerRols.get(i),roleImg);
			roleImg.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					getSelectRole((Image) event.getListenerActor());
					return super.touchDown(event, x, y, pointer, button);
				}
			});
		}
	}

	/**
	 * 通过点击Image取得相应的role
	 * 
	 * @param img
	 */
	private void getSelectRole(Image img) {
		for (int i = 0; i < RoleImg.size; i++) {
			if (RoleImg.get(i).equals(img)) {

			}
		}
	}
/**
 * 添加不同品质card img 到不同img数组
 * @param r
 * @param img
 */
	private void addToRoleQuality(Role r, Image img) {
		if (r.quality == QUALITY.white) {
			roleWhiteImg.add(img);
		}
		if (r.quality == QUALITY.green) {
			roleGreenImg.add(img);
		}
		if (r.quality == QUALITY.blue) {
			roleBlueImg.add(img);
		}
		if (r.quality == QUALITY.purple) {
			rolePurpleImg.add(img);
		}
		if (r.quality == QUALITY.orange) {
			roleOrangeImg.add(img);
		}
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

}
