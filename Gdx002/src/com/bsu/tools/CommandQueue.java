package com.bsu.tools;

import java.util.LinkedList;
import java.util.Queue;

import com.bsu.obj.CommandTask;

public class CommandQueue {
	private static CommandQueue instance = null;
	private CommandQueue(){}
	public static CommandQueue getInstance(){
		if(instance == null)
			instance = new CommandQueue();
		return instance;
	}
	
	private Queue<CommandTask> queue = new LinkedList<CommandTask>();
	
	public void put(CommandTask ct){
		queue.offer(ct);
	}
	
	boolean taskCompleted = true;
	public void runTask(){
		//当任务队列中有任务，或上一任务完成了，继续下一个任务
		if(queue.isEmpty() || !taskCompleted)
			return;
		System.out.println("poll task");
		taskCompleted = false;
		CommandTask ct = queue.poll();
		ct.opTask(new BsuEvent(){
			@Override
			public void notify(Object obj, String msg) {
				System.out.println("taskCompleted");
				taskCompleted = true;
			}
		});
	}
}
