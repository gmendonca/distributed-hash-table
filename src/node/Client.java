package node;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import util.DistributedHashtable;

public class Client extends Thread{
	
	//put
	public Boolean put(String key, String value) throws Exception{
		if(key.length() > 24) return false;
		if(value.length() > 1000) return false;
		int pId = DistributedHashtable.hash(key, peerList.size());
			
		String[] peerAddress = peerList.get(pId).split(":");
		Socket socket = new Socket(peerAddress[0], Integer.parseInt(peerAddress[1]));
		DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
			
		//put option
		dOut.writeByte(0);
		dOut.flush();
			
		//key, value
		dOut.writeUTF(key);
		dOut.writeUTF(value);
		dOut.flush();
			
		DataInputStream dIn = new DataInputStream(socket.getInputStream());
		boolean ack = dIn.readBoolean();
			
		socket.close();
			
		return ack;
	}
		
	//get
	public String get(String key) throws IOException {
		return null;
	}
		
	//delete
	public Boolean delete(String key){
		return false;
	}
	
	public static void main(String[] args) throws IOException{
		
		int id = 0;
		try{
			id = Integer.parseInt(args[0]);
		}catch(Exception e){
			System.out.println("Put a valid Id");
		}
		
		String dir = args[1];
    	File folder = new File(dir);
    	
    	if(!folder.isDirectory()){
			System.out.println("Put a valid directory name");
			return;
    	}
    	
    	String address = InetAddress.getLocalHost().getHostAddress();
    	int port = 0;
    	try{
    		port = Integer.parseInt(args[1]);
    	} catch (Exception e){
    		System.out.println("Put a valid port number");
    	}
    	
    	
    	Peer peer = new Peer(id, address, port);
    	
    	ServerSocket serverSocket = new ServerSocket(port);
    	
    	Server server = new Server(serverSocket, peer);
    	server.start();
    	
    	
    	int option;
		
		Scanner scanner = new Scanner(System.in);
    	while(true){
    		System.out.println("\n\nSelect the option:");
    		System.out.println("1 - Lookup for a file");
    		System.out.println("2 - Download file");
    		
    		option = scanner.nextInt();
    		if(option == 1){
    			
    		}
			scanner.close();
    	}
		
		
	}
}
