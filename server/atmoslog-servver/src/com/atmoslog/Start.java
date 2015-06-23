package com.atmoslog;

public class Start {

	public static void main(String []args){
		System.out.println("Server start");
		
		AtmoServer server = new AtmoServer(8191	);
		new Thread(server).start();

		while(true){
			try {
			    Thread.sleep(20 * 1000);
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
			System.out.println("STATUS: server ON");
		}
	}
}
