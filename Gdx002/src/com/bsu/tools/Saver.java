package com.bsu.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.badlogic.gdx.utils.Array;
import com.bsu.obj.Player;
import com.bsu.obj.Role;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Saver {
	private static Saver instance = null;
	public Saver getInstance(){
		if(instance == null)
			instance = new Saver();
		return instance;
	}
	private Kryo kryo = new Kryo();
	private PlayerData playerData = new PlayerData();
	private Saver(){
		kryo.register(Saver.class);

	}

	public void save(){
		savePlayer();
		try {
			Output output = new Output(new FileOutputStream("save.bin"));
			kryo.writeObject(output, instance);
			output.close();
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
			instance.loadPlayer();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	private void savePlayer(){
		Player player = Player.getInstance();
		playerData.playerRole = player.playerRole;
		playerData.playerFightRole = player.playerFightRole;
		playerData.playerIdelRole = player.playerIdelRole;
		playerData.crystal_blue = player.crystal_blue;
		playerData.crystal_orange = player.crystal_orange;
		playerData.crystal_purple = player.crystal_purple;
	}
	
	public void loadPlayer(){
		Player player = Player.getInstance();
		player.playerRole = playerData.playerRole;
		player.playerFightRole = playerData.playerFightRole;
		player.playerIdelRole = playerData.playerIdelRole;
		player.crystal_blue = playerData.crystal_blue;
		player.crystal_orange = playerData.crystal_orange;
		player.crystal_purple = playerData.crystal_purple;
	}
	
	class PlayerData{
		public Array<Role> playerRole = null;
		public Array<Role> playerFightRole = null;
		public Array<Role> playerIdelRole = null;
		public int crystal_blue=0;
		public int crystal_purple=0;
		public int crystal_orange=0;
	}
}

