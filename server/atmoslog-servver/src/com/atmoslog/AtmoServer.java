package com.atmoslog;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AtmoServer implements Runnable{

	protected int serverPort = 8191;
	protected ServerSocket socket = null;
	protected boolean running = true;
	protected Thread runningThread = null;
	protected ExecutorService threadPool = Executors.newFixedThreadPool(10);
	
	public AtmoServer(int port){
		this.serverPort = port;
	}

	public void run(){
		synchronized(this){
			this.runningThread = Thread.currentThread();
		}
		openServerSocket();
		while(running){
			Socket client = null;
			try{
				client = this.socket.accept();
			} catch (IOException e){
				if(!running){
					System.out.println("Server stopped.");
					return;
				}
				throw new RuntimeException(
						"Error accepting client connection", e);
			}
			this.threadPool.execute(new WorkerRunnable(client, "Atmos-Server"));
		}
		this.threadPool.shutdown();
		System.out.println("Server stopped.");
	}
	
	private synchronized boolean running(){
		return this.running;
	}
	
	public synchronized void stop(){
		this.running = false;
		try{
			this.socket.close();
		} catch (IOException e){
			throw new RuntimeException("Error closing server", e);
		}
	}
	
	public void openServerSocket(){
		try{
			this.socket = new ServerSocket(this.serverPort);
		} catch (IOException e){
			throw new RuntimeException("Cannot open port " + this.serverPort, e);
		}
	}
}
