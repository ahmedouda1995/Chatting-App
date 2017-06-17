


import java.io.*;
import java.net.*;
import java.util.*;
class ListenThread implements Runnable{
	Socket socket;
	public ListenThread(Socket socket){
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
	    		Server_m3.addClient(request);
			}
	    	if(request.startsWith("!@#")&&Server_m3.allClients.contains(request.substring(3))){
	    		String name = request.substring(3);
	    		Server_m3.allClients.remove(name);
	    		PrintStream ps = new PrintStream(Server_m3.socketArray.get(0).getOutputStream());
	    		ps.println("!@#"+name);
	    		

	    	}
	    	if(request!=null && request.startsWith("The Online Members:")){
				
				request=request.substring(19);
				String [] names=request.split(",");
				for(int i=0;i<names.length;i++){
					Server_m3.allClients.add(names[i]);
				}}
				
	    	if(request.equals("QUIT")){
	    		 int index = Server_m3.socketArray.indexOf(socket);
	 		    Server_m3.socketArray.remove(index);
	 		    
	 		    String name =Server_m3.clientsArray.remove(index);
	    		Server_m3.allClients.remove(name);
	    		PrintStream ps = new PrintStream(Server_m3.socketArray.get(0).getOutputStream());
	    		ps.println("!@#"+name);
	    		socket.close();
	    		break;
	    		
	    	}
	    	
	    if(request.equals("MemberList")){
	    
	    	Server_m3.memberListResponse(socket,request);
	    }
	    else{
	    	if(request.contains("!#!"))
	    	{
	    	Server_m3.route(request);
	    		
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
class JoinThread implements Runnable{

	ServerSocket ServSocket;
	public JoinThread(ServerSocket ServSocket){
		this.ServSocket= ServSocket;
	}
	
	@Override
	public void run() {
		while(true){
			Socket socket;
			try {
				socket = ServSocket.accept();
			Server_m3.joinResponse( socket);
			Thread t1 = new Thread(new ListenThread(socket));
			t1.start();
			//Server_m2.check();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
}

public class Server_m3 {

	static ArrayList<Socket> socketArray = new ArrayList<Socket>();
	static ArrayList<String> clientsArray = new ArrayList<String>();
	static ArrayList<String> allClients = new ArrayList<String>();
	public static void joinResponse(Socket c_socket) throws IOException{
		
		InputStreamReader IR= new InputStreamReader(c_socket.getInputStream());
		BufferedReader BR = new BufferedReader(IR);
		String name= BR.readLine();
		
		if(allClients.contains(name)){
			PrintStream ps1=new PrintStream(c_socket.getOutputStream());
			ps1.println("please choose a different name ");
		   
		}
		else{
			PrintStream ps1=new PrintStream(c_socket.getOutputStream());
			ps1.println("connected ");
			allClients.add(name);
			socketArray.add(c_socket);
			clientsArray.add(name);
			
			PrintStream ps2=new PrintStream(socketArray.get(0).getOutputStream());
			ps2.println("#$%"+name);
			
		}
	}
	public static void addClient(String name) throws IOException{
		
		if(!allClients.contains(name.substring(3) )){
			allClients.add(name.substring(3));
		PrintStream ps2=new PrintStream(socketArray.get(0).getOutputStream());
		ps2.println(name);
		}
	}
	public static void route( String request) throws IOException {
		// TODO Auto-generated method stub
		//System.out.println(request);
		String [] s = request.split("!#!");
    	String name = s[0];
    	int ttl = Integer.parseInt(s[1]);
    	String destination =s[2];
    	String message=s[3];
    	if(allClients.contains(destination)){
    	if(ttl>0){
	int index = Server_m3.clientsArray.indexOf(destination);
	if(index != -1){
	Socket reciever = Server_m3.socketArray.get(index);
	PrintStream ps = new PrintStream(reciever.getOutputStream());
	ps.println(name+": "+message);
	}else{
		ttl--;
		request = name+"!#!"+ttl+"!#!"+destination+"!#!"+message;
	PrintStream ps = new PrintStream(socketArray.get(0).getOutputStream());
	ps.println(request);
	}}}
    	else{
    		PrintStream ps = new PrintStream(socketArray.get(clientsArray.indexOf(name)).getOutputStream());
    		ps.println("The person you are trying to reach is offline");
    	}
	}
	public static void memberListResponse(Socket cl_socket, String request) throws IOException{
		PrintStream ps = new PrintStream(cl_socket.getOutputStream());
		
		ps.print("The Online Members:");
		for(String s :Server_m3.allClients){
			if(!s.startsWith("SERVER"))
			ps.print(s+",");
			   }
		ps.println();
	   
	}
	public Server_m3() throws UnknownHostException, IOException {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		int port =6011;
		ServerSocket ServSocket = new ServerSocket(port);
		
		Thread t1 = new Thread(new JoinThread(ServSocket));
		t1.start();
		Servers s = new Servers(port);
	}
}
