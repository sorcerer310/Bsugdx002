package com.bsu.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.bsu.obj.data.RoleData;
import com.bsu.obj.skilltree.Skill;
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
		//转换上场战斗角色数据为存档数据
		for(Role r:player.playerFightRole)
			playerData.playerFightRole.add(r.toRoleData());
		//转换闲置角色数据为存档数据
		for(Role r:player.playerIdelRole)
			playerData.playerIdelRole.add(r.toRoleData());
		//转换所有角色数据为存档数据
		for(Role r:player.playerRole)
			playerData.playerRole.add(r.toRoleData());
		
		playerData.crystal_blue = player.crystal_blue;
		playerData.crystal_orange = player.crystal_orange;
		playerData.crystal_purple = player.crystal_purple;
		
	}
	
	public void loadPlayer(){
		Player player = Player.getInstance();
//		Player.loadPlayer(playerData);
//		player.playerRole = playerData.playerRole;
//		player.playerFightRole = playerData.playerFightRole;
//		player.playerIdelRole = playerData.playerIdelRole;
		
		player.crystal_blue = playerData.crystal_blue;
		player.crystal_orange = playerData.crystal_orange;
		player.crystal_purple = playerData.crystal_purple;
	}
	

}

class PlayerData{
	public Array<RoleData> playerRole = new Array<RoleData>();
	public Array<RoleData> playerFightRole = new Array<RoleData>();
	public Array<RoleData> playerIdelRole = new Array<RoleData>();
	public int crystal_blue=0;
	public int crystal_purple=0;
	public int crystal_orange=0;
}


