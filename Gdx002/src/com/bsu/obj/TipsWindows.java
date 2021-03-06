package com.bsu.obj;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.bsu.make.ItemFactory;
import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.GC;
import com.bsu.tools.GC.QUALITY;
import com.bsu.tools.GTC;
import com.bsu.tools.U;

public class TipsWindows extends WidgetGroup implements Observer {

	private static TipsWindows instance = null;
	private Skin skin;
	private int windowWidth = 120;
	public Window tipsWindows;

	public static TipsWindows getInstance() {
		if (instance == null)
			instance = new TipsWindows();
		return instance;
	}

	private TipsWindows() {
		skin = new Skin();
		skin.add("draw", new TextureRegion(GTC.getInstance().tipsPanel, 40, 40));
		Window.WindowStyle ws = new WindowStyle(U.get_font(), Color.BLACK,
				skin.getDrawable("draw"));
		tipsWindows = new Window("", ws);
		tipsWindows.align(Align.left);
		tipsWindows.setWidth(500);
		this.addActor(tipsWindows);
	}

	/**
	 * 显示角色升级详细信息
	 * 
	 * @param r
	 */
	public void showRoleLevelUp(Role r, Stage stage) {
		removeFromStage();
		tipsWindows.clear();
		tipsWindows.defaults().align(Align.center);
		tipsWindows.padTop(10);
		tipsWindows.padBottom(10);
		tipsWindows.defaults().padLeft(10);
		tipsWindows.defaults().padRight(10);
		tipsWindows.setPosition(170, 100);
		showUpInfo(r,"hp");
		showUpInfo(r,"attack");
		showUpInfo(r,"defend");
		showUpInfo(r,"exp");
		tipsWindows.pack();
		tipsWindows.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				removeFromStage();
			}
		});
		addToStage(stage, 5);
	}

	/**
	 * 显示更换的人物信息，位置动态
	 * 
	 * @param r
	 * @param s
	 */
	public void showRoleInfo(Role r, Vector2 v, Stage stage) {
		removeFromStage();
		tipsWindows.align(Align.center);
		tipsWindows.padTop(10);
		tipsWindows.padBottom(10);
		tipsWindows.defaults().padLeft(10);
		tipsWindows.defaults().padRight(10);
		tipsWindows.add(new Label("" + r.name, U.get_Label_sytle()));
		tipsWindows.row();
		tipsWindows.add(new Label("" + U.getClasses(r), U.get_Label_sytle()));
		tipsWindows.row();
		tipsWindows.add(new Label("lv:" + r.level, U.get_Label_sytle()));
		tipsWindows.row();
		tipsWindows.add(new Label("" + r.exp + "/" + U.getUpExp(r,r.level), U.get_Label_sytle()));
		tipsWindows.row();
		Table t = new Table();
		for (Skill skill : r.getUseSkill()) {
			t.defaults().align(Align.center);
			t.add(new Image(skill.enable ? skill.icon : GTC.getInstance()
					.getSkillIcon(0)));
			String lvs = skill.enable ? skill.lev + "" : "  ";
			Label l = new Label(lvs, U.get_Label_sytle());
			l.setFontScale(0.5f);
			t.defaults().align(Align.bottom);
			t.add(l);
		}
		tipsWindows.add(t);
		tipsWindows.pack();
		tipsWindows.setPosition(getPosition(v, 50).x, getPosition(v, 50).y);
		tipsWindows.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				removeFromStage();
			}
		});
		addToStage(stage, 5);
	}

	/**
	 * 显示技能描述信息 位置动态
	 * 
	 * @param s
	 */
	public void showSkillInfo(Skill s, Vector2 v, Stage stage) {
		removeFromStage();
		tipsWindows.defaults().align(Align.center);
		tipsWindows.padTop(10);
		tipsWindows.padBottom(10);
		tipsWindows.defaults().padLeft(10);
		tipsWindows.defaults().padRight(10);
		Label nameLabel = new Label(s.name, U.get_Label_sytle());
		nameLabel.setColor(U.getQualityColor(s.quality));
		tipsWindows.add(nameLabel);
		tipsWindows.defaults().align(Align.left);
		Array<String> infoArray = new Array<String>();
		float scaleValue = 0.6f;
		infoArray = U.getMuLabel(s.info, scaleValue, windowWidth);
		for (int i = 0; i < infoArray.size; i++) {
			tipsWindows.row();
			Label label = new Label(infoArray.get(i), U.get_Label_sytle());
			label.setFontScale(scaleValue);
			tipsWindows.add(label);
		}
		tipsWindows.pack();
		tipsWindows.setPosition(getPosition(v, 32).x, getPosition(v, 32).y);
		tipsWindows.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				removeFromStage();
			}
		});
		addToStage(stage, 5);
	}

	/**
	 * 显示一条基本信息，tips 固定位置，屏幕中间
	 */
	public void showTips(String s, Stage stage, Color r) {
		removeFromStage();
		tipsWindows.clear();
		tipsWindows.defaults().align(Align.center);
		tipsWindows.padTop(10);
		tipsWindows.padBottom(10);
		tipsWindows.setPosition(200, 140);
		Array<String> tipsArray = new Array<String>();
		float scaleValue = 1f;
		tipsArray = U.getMuLabel(s, scaleValue, windowWidth-20);
		for (String as : tipsArray) {
			tipsWindows.padLeft(10);
			tipsWindows.padRight(10);
			Label l = new Label(as, U.get_Label_sytle());
			l.setFontScale(scaleValue);
			l.setColor(r);
			tipsWindows.add(l);
			tipsWindows.row();
		}
		tipsWindows.pack();
		tipsWindows.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				removeFromStage();
			}
		});
		addToStage(stage, 2);
	}

	/**
	 * 设置吞噬界面，被选择吞噬的role信息
	 * 
	 * @param r
	 * @param stage
	 */
	public void showEatInfo(Role r, Vector2 v, Stage stage) {
		removeFromStage();
		tipsWindows.align(Align.center);
		tipsWindows.defaults().padLeft(10);
		tipsWindows.defaults().padRight(10);
		tipsWindows.padTop(10);
		tipsWindows.padBottom(10);
		Table to = new Table();
		to.align(Align.center);
		Table tp = new Table();
		tp.align(Align.center);
		Table tb = new Table();
		tb.align(Align.center);
		Table tg = new Table();
		tg.align(Align.center);
		float fonts = 0.5f;
		for (final Skill s : r.skill_tree) {
			Image skill_img = null;
			Table t = null;
			if (s.quality == QUALITY.green) {
				skill_img = new Image(s.enable ? s.icon : GTC.getInstance()
						.getSkillIcon(0));
				t = tg;
			}
			if (s.quality == QUALITY.blue) {
				skill_img = new Image(s.enable ? s.icon : GTC.getInstance()
						.getSkillIcon(0));
				t = tb;
			}
			if (s.quality == QUALITY.purple) {
				skill_img = new Image(s.enable ? s.icon : GTC.getInstance()
						.getSkillIcon(0));
				t = tp;
			}
			if (s.quality == QUALITY.orange) {
				skill_img = new Image(s.enable ? s.icon : GTC.getInstance()
						.getSkillIcon(0));
				t = to;
			}
			String lvs = s.enable ? s.lev + "" : "  ";
			Label l = new Label(lvs, U.get_Label_sytle());
			l.setFontScale(fonts);
			t.add(skill_img);
			t.defaults().align(Align.bottom);
			t.add(l);
		}
		tipsWindows.add(new Label("lv:" + r.level, U.get_Label_sytle()));
		tipsWindows.row();
		tipsWindows.add(new Label("exp:" + r.exp, U.get_Label_sytle()));
		tipsWindows.row();
		tipsWindows.add(to);
		tipsWindows.row();
		tipsWindows.add(tp);
		tipsWindows.row();
		tipsWindows.add(tb);
		tipsWindows.row();
		tipsWindows.add(tg);
		tipsWindows.pack();
		tipsWindows.setPosition(getPosition(v, 50).x, getPosition(v, 50).y);
		addToStage(stage, 5);
		tipsWindows.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				removeFromStage();
			}
		});
	}
	private void showUpInfo(Role r,String s){
		Label name=null;
		Label a=null;
		Label b=null;
		Label con=new Label("---",U.get_Label_sytle());
		String sn=null;
		if(s=="hp"){
			sn="生命";
			a=new Label(U.getCurrentBaseHp(r,r.level)+"",U.get_Label_sytle());
			b=new Label(U.getCurrentBaseHp(r,r.level+1)+"",U.get_Label_sytle());
		}
		if(s=="attack"){
			sn="攻击";
			a=new Label(U.getCurrentBaseAttack(r,r.level)+"",U.get_Label_sytle());
			b=new Label(U.getCurrentBaseAttack(r,r.level+1)+"",U.get_Label_sytle());
		}
		if(s=="defend"){
			sn="防御";
			a=new Label(U.getCurrentBaseDefend(r,r.level)+"",U.get_Label_sytle());
			b=new Label(U.getCurrentBaseDefend(r,r.level+1)+"",U.get_Label_sytle());
		}
		if(s=="exp"){
			sn="经验";
			a=new Label(U.getUpExp(r,r.level)+"",U.get_Label_sytle());
			b=new Label(U.getUpExp(r,r.level+1)+"",U.get_Label_sytle());
		}
		name=new Label(sn,U.get_Label_sytle());
		name.setColor(Color.ORANGE);
		tipsWindows.add(name);
		tipsWindows.add(a);
		tipsWindows.add(con);
		tipsWindows.add(b);
		tipsWindows.row();
	}
	/**
	 * 显示宝箱内道具
	 * 
	 * @param s
	 */
	public void showBoxItem(Stage s) {
		removeFromStage();
		tipsWindows.clear();
		tipsWindows.defaults().align(Align.center);
		tipsWindows.setPosition(200, 140);
		Image icon = new Image(
				GTC.getInstance().hm_headItemIcon.get(ItemFactory.getInstance()
						.getItemById(1).tr_item));
		tipsWindows.add(icon);
		tipsWindows.pack();
		tipsWindows.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				removeFromStage();
			}
		});
		addToStage(s, 2);
	}

	/**
	 * 根据传入坐标，返回tips应该所在位置
	 * 
	 * @param f
	 * @param g
	 */
	private Vector2 getPosition(Vector2 tv, int w) {
		Vector2 v = new Vector2();
		int ax = 0, ay = 0;
		if (tv.x <= GC.rect_width / 2) {
			ax = -1;
		}
		if (tv.y <= GC.rect_height / 2) {
			ay = -1;
		}
		v.x = ax < 0 ? tv.x + w : tv.x - tipsWindows.getWidth();
		v.y = ay < 0 ? tv.y + w : tv.y - tipsWindows.getHeight();
		return v;
	}

	/**
	 * 移除tips及其自身
	 */
	public void removeFromStage() {
		tipsWindows.clear();
		tips_flag = false;
		start_time = 0;
		remove();

	}

	/**
	 * 添加自身及tips到相应位置
	 */
	private void addToStage(Stage s, float ds) {
		dua_time = ds;
		tips_flag = true;
		start_time = 0;
		s.addActor(this);
	}

	float start_time, dua_time, current_time;
	boolean tips_flag;

	@Override
	public void act(float delta) {
		if (tips_flag) {
			start_time += delta;
			float a = (dua_time - start_time) / (dua_time / 2);
			a = a >= 1 ? 1 : a;
		//	U.setAlpha(tipsWindows, a);
			if (start_time >= dua_time) {
				removeFromStage();
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
}
