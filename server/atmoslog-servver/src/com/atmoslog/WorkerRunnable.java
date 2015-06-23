package com.atmoslog;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;


public class WorkerRunnable implements Runnable{
	
	protected Socket client = null;
	protected String serverText = null;
	
	public WorkerRunnable(Socket client, String serverText){
		this.client = client;
		this.serverText = serverText;
	}
	
	public void run(){
		try{
			InputStream input = client.getInputStream();
			OutputStream output = client.getOutputStream();
			long time = System.currentTimeMillis();
			output.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " +
					this.serverText + " - " +
					time +
					"").getBytes());
			output.close();
			input.close();
			System.out.println("Request processed: " + time);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
