import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUI_draft implements ActionListener,ListSelectionListener {
	
private  Client_m3 client;
public  String username;
public  String rec;
public String hostname;
int port;
//login window
public  JFrame loginwindow;
public  JTextField usernamebox;
private JButton enter;
private JLabel enterUserName;
private JPanel loginPanel;
private JLabel chooseServer;
private JComboBox servers;

//main window
public  JFrame mainwindow;
private JButton disconnect;
private JButton send;
private JButton refresh;
public static JTextArea conversation =new JTextArea();
public  JTextField message;
private JLabel enteredMessage;
private  JLabel convLabel;
private  JScrollPane sc_conversation;
private  JScrollPane sc_online ;
public static  JList jl_online =new JList();
public  JLabel online_users;

public static void main(String[] args) {
	new GUI_draft();
}
public GUI_draft(){
	username="anonymous";
	 rec="anonymous";
     hostname="";
     port=0;
	//login window
	loginwindow=new JFrame();
	usernamebox=new JTextField(20);
	enter=new JButton("Enter");
	enterUserName=new JLabel("Enter username: ");
	chooseServer= new JLabel("Choose a server: ");
	servers = new JComboBox();
	 servers.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "","SERVER1", "SERVER2","SERVER3","SERVER4"}));
	loginPanel=new JPanel();

	//main window
	mainwindow=new JFrame();
	disconnect =new JButton();
	send=new JButton();
	 refresh=new JButton();
	conversation =new JTextArea();
	message=new JTextField(20);
	 enteredMessage=new JLabel("Message: ");
	convLabel=new JLabel();
	 sc_conversation =new JScrollPane();
	sc_online =new JScrollPane();
	 jl_online =new JList();
	online_users =new JLabel();
	buildMainWindow();
	buildLoginWindow();
	initialize();
}
private  void initialize() {
	send.setEnabled(false);
}

private  void buildLoginWindow(){
	loginwindow.setTitle("What's your name?");
	loginwindow.setSize(400,100);
	loginwindow.setLocation(440, 180);
	loginwindow.setResizable(false);
	loginPanel=new JPanel();
	
	loginPanel.add(enterUserName);
	loginPanel.add(usernamebox);
	loginPanel.add(chooseServer);
	loginPanel.add(servers);
	loginPanel.add(enter);
	loginwindow.add(loginPanel);
	login_action();
	loginwindow.setVisible(true);
}
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	if(e.getSource() instanceof JButton ){
		if(e.getSource()== this.enter){
		try {
			enter_action();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}}
		else{
			if(e.getSource()== this.send){
             try {
				this.send_action();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}else{
				if(e.getSource()==this.disconnect){
					try {
						this.disconnect_action();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else{
					if(e.getSource()==this.refresh){
						try {
							this.client.getMemberList();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			
			}
		}
	}else{
		if(servers.getSelectedItem().toString().equals("SERVER1")){
			hostname="localhost";
			port=6011;
		}else{if(servers.getSelectedItem().toString().equals("SERVER2")){
			hostname="localhost";
			port=6012;
		}else{
			if(servers.getSelectedItem().toString().equals("SERVER3")){
				hostname="localhost";
				port=6013;
			}else{if(servers.getSelectedItem().toString().equals("SERVER4")){
				hostname="localhost";
				port=6014;
			}
		}
			
		}
	}}
}

private  void login_action() {
	enter.addActionListener(this);
	servers.addActionListener(this);
}

private  void enter_action() throws IOException{
	if(!usernamebox.getText().equals("")){
		username=usernamebox.getText().trim();
		
		connect();
		
	}
	else {
		JOptionPane.showMessageDialog(null,"Please enter a name !!!");
	}
}
private  void buildMainWindow() {
	mainwindow.setTitle(username+"'s ChatBox");
	mainwindow.setSize(500, 320);
	mainwindow.getContentPane().setLayout(null);
	mainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	mainwindow.getContentPane().setBackground(new java.awt.Color(0,0,200));
	mainwindow.validate();
	mainwindow.setResizable(false);
	mainwindow.setLocation(440,180);
	
	online_users.setText("Online users");
	online_users.setForeground(new java.awt.Color(255,255,255));
	mainwindow.getContentPane().add(online_users);
	online_users.setBounds(365,10,100,10);
	
	send.setBackground(new java.awt.Color(0,255,0));
	send.setForeground(new java.awt.Color(0,0,255));
	send.setText("Send");
	mainwindow.getContentPane().add(send);
	send.setBounds(250,240,80,25);
	
	refresh.setBackground(new java.awt.Color(44,174,255));
	refresh.setForeground(new java.awt.Color(0,0,255));
	refresh.setText("Refresh");
	mainwindow.getContentPane().add(refresh);
	refresh.setBounds(350,240,90,25);
	
	disconnect.setBackground(new java.awt.Color(255,0,0));
	disconnect.setForeground(new java.awt.Color(0,0,255));
	disconnect.setText("Disconnect");
	mainwindow.getContentPane().add(disconnect);
	disconnect.setBounds(10,240,110,25);
	
	enteredMessage.setText("Message: ");
	mainwindow.getContentPane().add(enteredMessage);
	enteredMessage.setBounds(10,210,60,20);
	enteredMessage.setForeground(new java.awt.Color(255,255,255));
	
	message.setForeground(new java.awt.Color(0,0,255));
	message.setBackground(new java.awt.Color(230,230,230));
	message.requestFocus();
	mainwindow.getContentPane().add(message);
	message.setBounds(70,210,260,25);
	
	convLabel.setHorizontalAlignment(SwingConstants.CENTER);
	convLabel.setText("Conversation");
	mainwindow.getContentPane().add(convLabel);
	convLabel.setBounds(100,3,140,16);
	convLabel.setForeground(new java.awt.Color(255,255,255));
	
	conversation.setColumns(20);
	conversation.setBackground(new java.awt.Color(230,230,230));
	conversation.setFont(new java.awt.Font("Tahoma",0,12));
	conversation.setForeground(new java.awt.Color(0,0,255));
	conversation.setLineWrap(true);
	conversation.setRows(5);
	conversation.setEditable(false);
	
	sc_conversation.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	sc_conversation.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	sc_conversation.setViewportView(conversation);
	mainwindow.getContentPane().add(sc_conversation);
	sc_conversation.setBounds(10,20,330,180);
	
	jl_online.setForeground(new java.awt.Color(0,0,255));
	jl_online.setBackground(new java.awt.Color(230,230,230));
	sc_online.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	sc_online.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	sc_online.setViewportView(jl_online);
	mainwindow.getContentPane().add(sc_online);
	sc_online.setBounds(350,24,130,210);
	
	mainwindow_action();
	
	
}

private  void mainwindow_action() {
	send.addActionListener(this);
	
	disconnect.addActionListener(this);
	
	refresh.addActionListener(this);
	
	jl_online.addListSelectionListener(this);
}

public  void send_action() throws IOException{
	if(!message.getText().equals("")){
		String txt=message.getText();
		conversation.append("you: "+txt+"\n");
		PrintStream ps=new PrintStream(client.getOutputStream());
		ps.println(client.name+"!#!2!#!"+rec+"!#!"+txt);
		message.requestFocus();
		message.setText("");
	}
}

public  void disconnect_action() throws IOException{
	client.quit();
	System.exit(0);
}

public  void connect() throws IOException{
	try{
	client=new Client_m3(hostname,username,port);
	if(client.join(username).equals("please choose a different name ")){
	JOptionPane.showMessageDialog(null,"Please enter a different name !!!");
	loginwindow.dispose();
	mainwindow.dispose();
	new GUI_draft();
}else{ client.start();
client.getMemberList();
mainwindow.setTitle(username+"'s Text Box");
loginwindow.setVisible(false);
send.setEnabled(true);
mainwindow.setVisible(true);
	
}}catch(Exception e){
	JOptionPane.showMessageDialog(null,"This Server is offline.Please Choose a different server !!!");
}
}
@Override
public void valueChanged(ListSelectionEvent arg0) {
	// TODO Auto-generated method stub
	 if(jl_online.getSelectedValue()!=null)
	    	rec=jl_online.getSelectedValue().toString();
	 
}


}
