

import java.io.*;
import java.net.*;

class MiddleThread extends Thread{
	Server_AS_Client sender;
	Server_AS_Client receiver;
	public MiddleThread(Server_AS_Client server1, Server_AS_Client server2){
		this.sender = server1;
		this.receiver = server2;
	}
	public void run(){
		try {BufferedReader reader = new BufferedReader(new InputStreamReader(sender.getInputStream()));
		PrintStream send;
		
			send = new PrintStream(receiver.getOutputStream());
		
		while(true){
			send.println(reader.readLine());
		}} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
public class Servers {
	Server_AS_Client server1;
	Server_AS_Client server2;
	static int count=1;; 
public Servers(int port) throws UnknownHostException, IOException{
	 server1= new Server_AS_Client("localhost","SERVER"+count,6010);
	
	 server2= new Server_AS_Client("localhost","SERVERCenter"+count,port);
	count++;
	MiddleThread t1 = new MiddleThread(server1, server2);
	t1.start();
	MiddleThread t2 = new MiddleThread(server2, server1);
	t2.start();
	server1.getMemberList();
} public static void main(String[] args) {
	try {
		new Servers(56);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}
