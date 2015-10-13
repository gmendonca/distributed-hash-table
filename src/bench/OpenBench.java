package bench;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import node.OpenServer;
import node.Peer;
import util.DistributedHashtable;

public class OpenBench {
	
	public static ArrayList<String> peerList;
	public static ArrayList<Socket> socketList;
	public static int numPeers;
	public static int operations;
	
	
	
	public static void main(String[] args) throws IOException{
		
		peerList = DistributedHashtable.readConfigFile();
		socketList = new ArrayList<Socket>();
		
		numPeers = peerList.size();
		
		int id;
    	
    	String address = InetAddress.getLocalHost().getHostAddress();
    	//address = args[1];
    	
    	int port;
    	
    	operations = Integer.parseInt(args[0]);
    	
    	//Creating servers
    	for(id = 0, port = 13000; id < peerList.size(); id++, port++){
    		Peer peer = new Peer(id, address, port);
        	
        	ServerSocket serverSocket = new ServerSocket(port);
        	
        	//start server
        	OpenServer server = new OpenServer(serverSocket, peer);
        	server.start();
        	
    	}
    	
    	//checking if all are open
    	for(id = 0, port = 13000; id < peerList.size(); id++, port++){
    		try {
    			System.out.println("Connecting to server " + address + ":" + port);
    			Socket s = new Socket(address, port);
    			socketList.add(s);
    			System.out.println("Connected to server " + address + ":" + port);
    		} catch (Exception e){
    			//System.out.println("Not connected to server " + address + ":" + port);
    			//System.out.println("Trying again");
    			id--;
    			port--;
    		}
    	}
    	System.out.println("All servers running");
    	
    	new Client().start();
    	
    	
		
	}

}
