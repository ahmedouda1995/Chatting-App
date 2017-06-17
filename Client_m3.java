import java.io.*;
import java.net.*;
import java.util.Arrays;

import javax.swing.JOptionPane;

class InThread implements Runnable{
    Socket socket;
    public InThread(Socket socket){
    	this.socket = socket;
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
		InputStreamReader IR= new InputStreamReader(socket.getInputStream());
		BufferedReader BR = new BufferedReader(IR);
		while(true){
		String message = BR.readLine();
		if(message!=null && message.startsWith("The Online Members:")){
			
			message=message.substring(19);
			String [] names=message.split(",");
			
			GUI_draft.jl_online.setListData(names);
		}	
		else{
			if(message!=null&&!message.startsWith("#$%")&&!message.startsWith("!@#"))
            GUI_draft.conversation.append(message+"\n");}
		
		 	
	}}catch(Exception e){
		e.printStackTrace();
	}
		}
	
}
public class Client_m3 extends Socket {

 String name;

 
 
	public  String join(String name) throws IOException{
	PrintStream ps=new PrintStream(this.getOutputStream());
	 ps.println(name);
	this.name = name;
	InputStreamReader IR= new InputStreamReader(this.getInputStream());
	BufferedReader BR = new BufferedReader(IR);
	
	String message = BR.readLine();
	return (message);
//	if(message.equals("please choose a different name ")){
//		JOptionPane.showMessageDialog(null,"Please enter a different name !!!");
//		
//		InputStreamReader IR1= new InputStreamReader(System.in);
//	BufferedReader BR1= new BufferedReader(IR1);
//	this.join(BR1.readLine());}
//    getMemberList();
	
	}
	public  void quit() throws IOException{
		PrintStream ps=new PrintStream(this.getOutputStream());	
		ps.println("QUIT");
		System.exit(0);
	}
	public  void getMemberList() throws IOException{
		PrintStream ps=new PrintStream(this.getOutputStream());
		ps.println("MemberList");
	}
	public Client_m3(String host, String name1, int port) throws IOException {
		super(host, port);
		 
		 
	}

	public  void start() {
		// TODO Auto-generated method stub
		Thread t1 = new Thread(new InThread(this));
		t1.start();
	}
}
