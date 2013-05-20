package com.bsu.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bsu.make.ButtonFactory;
import com.bsu.obj.Role;
import com.bsu.tools.Configure.STATE;

/**
 * 描述战斗场景中role信息
 * 
 * @author zhangyongchen
 * 
 */
public class FightRoleUI {
	Stage stage;
	public Role role;
	int role_index;// role 所在数组索引
	Texture photo_texture;
	
	Image roleBack;
	public Image role_photo;// role的头像
	Label label_name;
	Image skillImg0;
	Image skillImg1;

	RoleHP rhp;// 血条类对象
	public Image role_hp;// 血条
	int margin = 2; // 血条和人物之间的间隔

	public FightRoleUI(Stage s, Role r, int index) {
		// TODO Auto-generated constructor stub
		stage = s;
		role = r;
		role_index = index;
		TextureRegion tr = r.roleTexture;
		role_photo = new Image(tr);
		skillImg0 = new Image(new Texture(
				Gdx.files.internal("data/game/ui/jewel_dea.png")));
		skillImg1 = new Image(new Texture(
				Gdx.files.internal("data/game/ui/jewel_dead2.png")));
		roleBack=ButtonFactory.getInstance().makeImageButton(Configure.Img_head_back);
		rhp = new RoleHP();
		role_hp = rhp.get_hp_image();
		Configure.get_sytle().font.setScale(0.5f);
		label_name = new Label(role.name, Configure.get_sytle());
		role_photo.setScale(0.5f);
		show_role_state();
	}

	private void show_role_state() {
		stage.addActor(roleBack);
		stage.addActor(role_photo);
		stage.addActor(skillImg0);
		stage.addActor(skillImg1);
		stage.addActor(label_name);
		stage.addActor(role_hp);
		skillImg0.setPosition(54 + role_index * 96, 0);
		skillImg1.setPosition(54 + role_index * 96, 44);
		role_photo.setPosition(role_index * 96, 0);
		role_hp.setPosition(50 + role_index * 96, 0);
		label_name.setPosition(0 + role_index * 96, 49);
		roleBack.setPosition(0+role_index*96, 0);
	}
}
