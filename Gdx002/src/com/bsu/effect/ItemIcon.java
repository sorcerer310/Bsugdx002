package com.bsu.effect;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.bsu.obj.Item;

public class ItemIcon extends WidgetGroup{
	public Image img_item = null;			//物品图片
	public Image img_frame = null;			//边框图片
	
	public ItemIcon(Item i){
		img_item = new Image(i.tr_item);	//物品图片
		img_frame = new Image(i.tr_frame);	//边框图片
		
		this.addActor(img_item);
		this.addActor(img_frame);
	}
}
