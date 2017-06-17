


import java.io.*;
import java.net.*;
import java.util.*;
class CListenThread implements Runnable{
	Socket socket;
	public CListenThread(Socket socket){
		this.socket = socket;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
		InputStreamReader IR= new InputStreamReader(socket.getInputStream());
	    BufferedReader BR = new BufferedReader(IR);
	    String request;
	    while(true && (request = BR.readLine())!=null){
	    	if(request.startsWith("#$%")){
	    		
	    		Center.addClient(socket,request);
			}
	    	if(request.startsWith("!@#")&&Center.allClients.contains(request.substring(3))){
	    		String name = request.substring(3);
	    		Center.allClients.remove(name);
	    		for(Socket s:Center.socketArray){
	    		PrintStream ps = new PrintStream(s.getOutputStream());
	    		ps.println("!@#"+name);}
	    		

	    	}
	    	
	    	
	    if(request.equals("MemberList")){
	    
	    	Center.memberListResponse(socket,request);
	    }
	    else{
	    	if(request.contains("!#!"))
	    	{
	    	Center.route(request);
	    		
	    	}
//	    	else{
//	    		PrintStream ps = new PrintStream(socket.getOutputStream());
//	    		ps.println("Please stick to the rules!!!");
//	    	}
	    }
	    }
	    }catch(Exception e){
		 e.printStackTrace();
	}
		}
	
}
class CJoinThread implements Runnable{

	ServerSocket ServSocket;
	public CJoinThread(ServerSocket ServSocket){
		this.ServSocket= ServSocket;
	}
	
	@Override
	public void run() {
		while(true){
			Socket socket;
			try {
				socket = ServSocket.accept();
			Center.joinResponse( socket);
			Thread t1 = new Thread(new CListenThread(socket));
			t1.start();
			//Server_m2.check();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
}

public class Center {

	static ArrayList<Socket> socketArray = new ArrayList<Socket>();
	static ArrayList<String> allClients = new ArrayList<String>();
	static ArrayList<ArrayList<String>> socketClient = new ArrayList<ArrayList<String>>();
	public static void joinResponse(Socket c_socket) throws IOException{
		
		//InputStreamReader IR= new InputStreamReader(c_socket.getInputStream());
		//BufferedReader BR = new BufferedReader(IR);
		//String name= BR.readLine();
		

			PrintStream ps1=new PrintStream(c_socket.getOutputStream());
			ps1.println("connectedserver ");
			
			socketArray.add(c_socket);
			socketClient.add(new ArrayList<String>());
			
						
		
	}
	public static void addClient(Socket sockets,String name) throws IOException{
		
		if(!allClients.contains(name.substring(3) )){
			allClients.add(name.substring(3));
			int index = socketArray.indexOf(sockets);
			if(index!=-1)
			socketClient.get(index).add(name.substring(3));
			for(Socket socket: socketArray){
				
				if(socket!=sockets){
		PrintStream ps2=new PrintStream(socket.getOutputStream());
		ps2.println(name);}}
		}
	}
	public static void route( String request) throws IOException {
		// TODO Auto-generated method stub
		String [] s = request.split("!#!");
    	String name = s[0];
    	int ttl = Integer.parseInt(s[1]);
    	String destination =s[2];
    	for(int i = 0;i<socketClient.size();i++){
    	if(socketClient.get(i).contains(destination)){
    		PrintStream ps2=new PrintStream(socketArray.get(i).getOutputStream());
    		ps2.println(request);
    	}}
	}
	public static void memberListResponse(Socket cl_socket, String request) throws IOException{
		PrintStream ps = new PrintStream(cl_socket.getOutputStream());
		
		ps.print("The Online Members:");
		for(String s :Center.allClients){
			if(!s.startsWith("SERVER"))
			ps.print(s+",");
			   }
		ps.println();
	   
	}
	public Center() throws UnknownHostException, IOException {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		ServerSocket ServSocket = new ServerSocket(6010);
		
		Thread t1 = new Thread(new CJoinThread(ServSocket));
		t1.start();
		
		
	}
}
