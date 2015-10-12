package bench;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import node.Assign;
import node.Peer;
import node.Server;
import util.DistributedHashtable;

public class Benchmarking {
	private static ArrayList<String> peerList;
	//put
	public static Boolean put(String key, String value, int pId) throws Exception{
		if(key.length() > 24) return false;
		if(value.length() > 1000) return false;
			
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
	public static String get(String key, int pId) throws IOException {
		if(key.length() > 24) return null;
			
		String[] peerAddress = peerList.get(pId).split(":");
		Socket socket = new Socket(peerAddress[0], Integer.parseInt(peerAddress[1]));
		DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
			
		//get option
		dOut.writeByte(1);
		dOut.flush();
			
		//key
		dOut.writeUTF(key);
		dOut.flush();
			
		DataInputStream dIn = new DataInputStream(socket.getInputStream());
		String value = dIn.readUTF();
			
		socket.close();
			
		return value;
	}
		
	//delete
	public static Boolean delete(String key, int pId) throws Exception{
		if(key.length() > 24) return false;
			
		String[] peerAddress = peerList.get(pId).split(":");
		Socket socket = new Socket(peerAddress[0], Integer.parseInt(peerAddress[1]));
		DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
			
		//put option
		dOut.writeByte(2);
		dOut.flush();
			
		//key, value
		dOut.writeUTF(key);
		dOut.flush();
			
		DataInputStream dIn = new DataInputStream(socket.getInputStream());
		boolean ack = dIn.readBoolean();
			
		socket.close();
			
		return ack;
	}
	
	public static void main(String[] args) throws IOException{
		
		peerList = DistributedHashtable.readConfigFile();
		
		int id;
    	
    	String address = InetAddress.getLocalHost().getHostAddress();
    	address = args[1];
    	
    	int port = 13000;
    	
    	//Creating servers
    	for(id = 0; id < peerList.size(); id++, port++){
    		Peer peer = new Peer(id, address, port);
        	
        	ServerSocket serverSocket = new ServerSocket(port);
        	
        	//start server
        	Server server = new Server(serverSocket, peer);
        	server.start();
        	
        	//start assign
        	Assign assign = new Assign(peer);
        	assign.start();
    	}
    	
    	//checking if all are open
    	for(id = 0; id < peerList.size(); id++, port++){
    		try {
    			System.out.println("Connecting to server " + address + ":" + port);
    			Socket s = new Socket(address, port);
    			s.close();
    			System.out.println("Connected to server " + address + ":" + port);
    		} catch (Exception e){
    			id--;
    			port--;
    			System.out.println("Not connected to server " + address + ":" + port);
    			System.out.println("Trying again");
    		}
    	}
    	System.out.println("All servers running");
		
	}

}
