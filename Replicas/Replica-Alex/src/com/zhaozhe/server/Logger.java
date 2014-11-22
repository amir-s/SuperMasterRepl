package com.zhaozhe.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class Logger {
	
	/*
	 * Mark - Constructors
	 */

	public Logger() {
		logsPool = new ArrayList<Log>();
		loggerMachine = new LoggerMachine();
		loggerMachine.start();
	}
	
	/*
	 * Mark - Context - Properties
	 */

	private LibraryServer libraryServer;
	
	/*
	 * Mark - Context - Getters & Setters
	 */

	public LibraryServer getLibraryServer() {
		return libraryServer;
	}

	public void setLibraryServer(LibraryServer libraryServer) {
		this.libraryServer = libraryServer;
	}
	
	
	/*
	 * Mark - Log Pool - Properties
	 */
	
	private List<Log> logsPool;
	private Object poolLock = new Object();
	
	/*
	 * Mark - Log Pool - Methods
	 */
	
	public void record(Log log){
		synchronized(poolLock){
			logsPool.add(log);
		}
	}
	

	/*
	 * Mark - Logger - Properties
	 */
	
	private LoggerMachine loggerMachine;

	private static final int _TIME = 1000 * 5  * 1;
	
	/*
	 * Mark - Logger - Inner Class
	 */
	
	private class LoggerMachine extends Thread
	{

		@Override
		public void run() {
			while(true){
				
				// log the existed logs (meanwhile, new logs can still come in)
				List<Log> pool = Logger.this.logsPool;
				int size = pool.size();
				for(int i = 0 ; i < size ; i++ ){
					recordLogOnFile(pool.get(i));
				}
				
				// delete the logs in the logs pool (new logs will still stay in the pool)
				synchronized (poolLock) {
					for(int i = 0 ; i < size ; i ++){
						pool.remove(0);
					}
				}
				
				// take a rest 
				try {
					Thread.sleep(_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		private void recordLogOnFile(Log log){
			recordLogOnAccountFile(log);
			recordLogOnServerFile(log);
		}
		
		private void recordLogOnServerFile(Log log){
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String record = log.getAccount().getUsername() + "\t" + 
							dateFormat.format(log.getDate()) + "\t" + 
							log.getActivity() + "\n";

			String path = logPath();
			path = path + "/" + "log.txt";
			
			File file = new File(path);
			try {
				if (!file.exists()){
					file.createNewFile();
				}
				FileWriter fw = new FileWriter(file, true);
				fw.append(record);
				fw.flush();
				fw.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private void recordLogOnAccountFile(Log log){
			
			String fileName = log.getAccount().getUsername() + ".txt";
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String record = dateFormat.format(log.getDate()) + "\t" + 
							log.getActivity() + "\n";
			
			String path = logPath();
			path = path + "/" + "users";
			path = path + "/" + fileName;
			
			File file = new File(path);
			try {
				if (!file.exists()){
					file.createNewFile();
				}
				FileWriter fw = new FileWriter(file, true);
				fw.append(record);
				fw.flush();
				fw.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		private String logPath() {
			String path = "/Users/Zhaozhe/Documents/study/Concorida/Courses/2014_09_COMP 6231_Distributed_System_Design/Workspace/Assignment_1";
			path = path + "/" + "data";
			path = path + "/" + libraryServer.getServerInfo().getName();
			path = path + "/" + "log";
			return path;
		}
	}
}
