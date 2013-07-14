package com.bsu.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.badlogic.gdx.utils.Array;
import com.bsu.make.RoleFactory;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.obj.data.RoleData;
import com.bsu.obj.data.SkillData;
import com.bsu.screen.GameScreen;
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
		kryo.register(SkillData.class);
		kryo.register(Array.class);
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
		try{
			File file = new File("save.bin");
			if(!file.exists())
				return;			
			Input input = new Input(new FileInputStream("save.bin"));
			playerData = kryo.readObject(input,PlayerData.class);
			loadPlayer();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	private void savePlayer(){
		Player player = Player.getInstance();
		playerData.playerRole.clear();
		//转换所有角色数据为存档数据
		int size = player.getRole().size;
		for(int i=0;i<size;i++)
			playerData.playerRole.add(player.getRole().get(i).toRoleData());
		
		playerData.crystal_blue = player.crystal_blue;
		playerData.crystal_orange = player.crystal_orange;
		playerData.crystal_purple = player.crystal_purple;
		playerData.levelIndex = GameScreen.LvMax;
	}
	
	public void loadPlayer(){
		Player player = Player.getInstance();
		player.crystal_blue = playerData.crystal_blue;
		player.crystal_orange = playerData.crystal_orange;
		player.crystal_purple = playerData.crystal_purple;
		GameScreen.LvMax  = playerData.levelIndex;
		player.getRole().clear();
		
		int size = playerData.playerRole.size;
		for(int i=0;i<size;i++)
			player.getRole().add(RoleFactory.getInstance().getHeroRole(playerData.playerRole.get(i)));
	}
}

class PlayerData{
	public Array<RoleData> playerRole = new Array<RoleData>();
	public int crystal_blue=0;
	public int crystal_purple=0;
	public int crystal_orange=0;
	public int levelIndex = 0;						//关卡开发到的索引
}


