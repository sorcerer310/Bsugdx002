package com.bsu.effect;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.bsu.make.WidgetFactory;
import com.bsu.obj.Commander;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.obj.Role.Type;
import com.bsu.obj.skilltree.Skill;
import com.bsu.screen.GameScreen;
import com.bsu.tools.GC;
import com.bsu.tools.MessageObject;
import com.bsu.tools.U;

/**
 * GAME游戏战斗场景UI
 * 
 * @author zhangyongchen
 */
public class UIRoleEffect implements Observer {

	Stage stage;
	TextButton bt_endround;
	Commander c;
	GameScreen g;
	Array<roleUIInfo> hpArray = new Array<roleUIInfo>();

	public UIRoleEffect() {
		// TODO Auto-generated constructor stub

	}

	public void initUI(Stage s, GameScreen game) {
		stage = s;
		stage.clear();
		c = Commander.getInstance();
		g = game;
		show_hero_state();
	}

	/**
	 * 绘画每个角色状态
	 * 
	 * @param roleIndex
	 */
	public void show_hero_state() {
		stage.clear();
		hpArray.clear();
		bt_endround = WidgetFactory.getInstance().makeOneTextButton("end", 200,
				90);
		stage.addActor(bt_endround);
		bt_endround.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
//				EffectTool.shockScreen(g.getStage());
				c.roundEnd();
			}
		});
		Array<Role> fightPlayer = Player.getInstance().getPlayerFightRole();
		for (int i = 0; i < 5; i++) {
			int x = 4 + i * 96;
			int y = 10;
			Vector2 v = new Vector2(x, y);
			if (i >= fightPlayer.size) {
				RoleIcon icon = new RoleIcon();
				icon.setPosition(x, y);
				stage.addActor(icon);
				for (int sj = 0; sj < 2; sj++) {
					stage.addActor(new SkillIcon(new Vector2(x + 50, y + 32
							- sj * 33)));
				}
				continue;
			}

			final Role r = fightPlayer.get(i);
			RoleIcon photo = new RoleIcon(r, false);
			stage.addActor(photo);
			photo.setPosition(v.x, v.y);
			roleUIInfo rui = new roleUIInfo(r, stage, x, y);
			rui.photoImg = photo.img_head;
			rui.role_classes = photo.img_classes;
			hpArray.add(rui);
			photo.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (!r.isDead) {
						if (!r.isSelected()) {
							g.heroSelected(r);
							if (!r.isControlled()) {
								g.heroControllor(r);
							}
						}
						g.set_map_value(r);
					}
				}
			});
			final Array<Image> imgArray = new Array<Image>();
			final Array<Skill> skillArray = r.getUseSkill();
			for (int j = 0; j < skillArray.size; j++) {
				final Skill skill = skillArray.get(j);
				final int tempIndex = j;
				SkillIcon se = new SkillIcon(skill, new Vector2(x + 50, y + 32
						- j * 33));
				final Image skillImg = se.skillImg;
				stage.addActor(se);
				imgArray.add(skillImg);
				if (r.cskill == skill) {
					U.setAlpha(skillImg, 1);
				}
				if (skillArray.get(tempIndex).enable) {
					skillImg.addListener(new InputListener() {
						@Override
						public boolean touchDown(InputEvent event, float x,
								float y, int pointer, int button) {
							return true;
						}

						@Override
						public void touchUp(InputEvent event, float x, float y,
								int pointer, int button) {
							if (!g.isAction_start()
									&& !(skill.ani_self == null
											&& skill.ani_object == null && skill.ani_continue == null)) {
								r.cskill = skillArray.get(tempIndex);
								U.setSelectImg(imgArray, skillImg);
							}
							super.touchUp(event, x, y, pointer, button);
						}
					});
				}
			}
		}
	}

	/**
	 * 显示正在执行动作的role
	 * 
	 * @param r
	 */
	public void actingRole(Role r) {
		Array<Role> fightPlayer = Player.getInstance().getPlayerFightRole();
		for (Role e : fightPlayer) {
			e.roleIcon.showEffect(false);
		}
		if (r != null && r.type == Type.HERO) {
			r.roleIcon.showEffect(true);
		}
	}

	/**
	 * 设置UI HP变化
	 * 
	 * @param r
	 */
	public void changeRoleHp(Role r) {
		Array<Role> fightRole = Player.getInstance().getPlayerFightRole();
		for (int i = 0; i < fightRole.size; i++) {
			if (fightRole.get(i).equals(r)) {
				roleUIInfo rui = hpArray.get(i);
				if (r.getCurrentHp() > 0) {
					rui.hpImg.setScaleX((float) (r.getCurrentHp())
							/ (float) (r.getCurrentBaseHp()));
				} else {
					// 头像变暗
					rui.hpImg.setScaleX(0);
					U.setAlpha(rui.photoImg, 0.2f);
					U.setAlpha(rui.role_classes, 0.2f);
					U.setAlpha(rui.nameLabel, 0.5f);
				}
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		MessageObject mo = (MessageObject) arg;
		if (mo.message.equals(mo.o.changeHp)) {
			changeRoleHp(mo.o);
		}
	}
}

class roleUIInfo {
	Image hpImg;
	Image photoImg, role_classes;
	Label nameLabel;

	public roleUIInfo(Role r, Stage stage, int x, int y) {
		TextureRegion hpBack = WidgetFactory.getInstance().getTextureFill(48,
				4, Color.BLACK, 1);
		TextureRegion hp = WidgetFactory.getInstance().getTextureFill(48, 4,
				new Color(255, 0, 0, 1), 1);
		Image img = WidgetFactory.getInstance().makeImg(hpBack, 1, x, y + 49);
		hpImg = WidgetFactory.getInstance().makeImg(hp, 1, x, y + 49);
		nameLabel = WidgetFactory.getInstance().makeLabel(r.name, 0.5f, x,
				y + 50);
		stage.addActor(img);
		stage.addActor(hpImg);
		stage.addActor(nameLabel);
	}
}
