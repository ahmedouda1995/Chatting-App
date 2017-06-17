

import java.io.*;
import java.net.*;

public class Server_AS_Client extends Socket {
//static Socket socket;
 String name;
	public  void join(String name) throws IOException{
	PrintStream ps = new PrintStream(this.getOutputStream());
	ps.println(name);this.name = name;
	InputStreamReader IR= new InputStreamReader(this.getInputStream());
	BufferedReader BR = new BufferedReader(IR);
	
	String message = BR.readLine();
	System.out.println(message);
	if(message.equals("please choose a different name ")){
		InputStreamReader IR1= new InputStreamReader(System.in);
	BufferedReader BR1= new BufferedReader(IR1);
	//String name= BR1.readLine();
	//System.out.println("el name "+name);
	this.join(BR1.readLine());}

		return;
	
	
	}
	public  void quit() throws IOException{
		PrintStream ps1 = new PrintStream(this.getOutputStream());
		ps1.println("QUIT");
		this.close();
		System.exit(0);
	}
	public  void getMemberList() throws IOException{
		PrintStream ps = new PrintStream(this.getOutputStream());	
		ps.println("MemberList");
	}
	public Server_AS_Client(String host, String name, int port) throws IOException {
		super(host, port);
		this.join(name);
		// TODO Auto-generated constructor stub
	}
    
//	public static void main(String[] args) throws IOException {
//		
//		System.out.println("Please enter your name");
//		InputStreamReader IR1= new InputStreamReader(System.in);
//		BufferedReader BR1= new BufferedReader(IR1);
//		String name= BR1.readLine();
//		Server_AS_Client c = new Server_AS_Client("localhost",name);
//		c.start();
//	}
	
}
