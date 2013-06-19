package com.bsu.effect;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.bsu.obj.Item;
import com.bsu.tools.GTC;

public class ItemIcon extends WidgetGroup{
	public Image img_item = null;			//物品图片
	public Image img_frame = null;			//边框图片
	public Image img_class=null;			//职业图标
	
	public ItemIcon(Item i){
		img_item = new Image(i.tr_item);	//物品图片
		img_item.setScale(0.5f);
		img_frame = GTC.getInstance().getImageFrame(i.q);	//边框图片
		img_class=GTC.getInstance().getClassesIconImg(i.classes);
		img_class.setPosition(img_item.getWidth()/2-img_class.getWidth()-2, 2);
		this.addActor(img_item);
		this.addActor(img_class);
		this.addActor(img_frame);
	}
}
