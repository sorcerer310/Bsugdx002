package com.bsu.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.obj.Role.Type;
import com.bsu.obj.skilltree.Skill;
import com.bsu.tools.GC.CLASSES;
import com.bsu.tools.GC.QUALITY;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Saver {
	private static Saver instance = null;
	public static Saver getInstance(){
		if(instance == null)
			instance = new Saver();
		return instance;
	}
	private Kryo kryo = new Kryo();
	private PlayerData playerData = new PlayerData();
	private Saver(){
		kryo.register(PlayerData.class);
		kryo.register(Saver.class);
		kryo.register(Skill.class);
	}

	public void save(){
		savePlayer();
		try {
			Output output = new Output(new FileOutputStream("save.bin"));
			kryo.writeObject(output, playerData);
			output.close();
			System.out.println("save success");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void load(){
		loadPlayer();
		try{
			File file = new File("save.bin");
			if(!file.exists())
				return;			
			Input input = new Input(new FileInputStream("save.bin"));
			playerData = kryo.readObject(input,PlayerData.class);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	private void savePlayer(){
		Player player = Player.getInstance();
//		playerData.playerRole = player.playerRole;
		playerData.playerFightRole = player.playerFightRole;
//		playerData.playerIdelRole = player.playerIdelRole;
		playerData.crystal_blue = player.crystal_blue;
		playerData.crystal_orange = player.crystal_orange;
		playerData.crystal_purple = player.crystal_purple;
		
	}
	
	public void loadPlayer(){
		Player player = Player.getInstance();
//		Player.loadPlayer(playerData);
		player.playerRole = playerData.playerRole;
		player.playerFightRole = playerData.playerFightRole;
		player.playerIdelRole = playerData.playerIdelRole;
		player.crystal_blue = playerData.crystal_blue;
		player.crystal_orange = playerData.crystal_orange;
		player.crystal_purple = playerData.crystal_purple;
	}
	

}

class PlayerData{
	public Array<Role> playerRole = null;
	public Array<Role> playerFightRole = null;
	public Array<Role> playerIdelRole = null;
	public int crystal_blue=0;
	public int crystal_purple=0;
	public int crystal_orange=0;
}

class RoleData{
	public String roleIcon;						//人物头像图标名
	public String weapon;						//武器索引
	public String armor;						//护甲索引
	
	public String name;							//人物名称
	public QUALITY quality;						//人物品质
	public Type type;							//人物身份类型
	public CLASSES classes;						//人物职业
	public int level;							//等级
	public String roleTexture;					//人物纹理
	public int maxHp;							//最大血量
	public int extMaxHp;						//额外最大血量
	public int currentHp;						//当前血量
	public int attack;							//攻击力
	public int extAttack;						//额外的攻击力
	public int defend;							//防御力
	public int extDefend;						//额外的防御力
	public int exp;								//经验值
	public int expUp;							//下级升级的经验值
//	public Array<SkillData> skill_tree;			//英雄的技能树
//	public Array<SkillData> skill_array;		//英雄本关卡携带的技能
}

class SkillData{
	public int id;								//技能索引
	
}