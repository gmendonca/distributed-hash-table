package bench;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import node.Assign;
import node.Peer;
import node.Server;
import util.DistributedHashtable;

public class Benchmarking {
	
	public static ArrayList<String> peerList;
	public static int numPeers;
	public static int operations;
	

	
	public static void main(String[] args) throws IOException{
		
		peerList = DistributedHashtable.readConfigFile();
		
		int numPeers = peerList.size();
		
		int id;
    	
    	String address = InetAddress.getLocalHost().getHostAddress();
    	//address = args[1];
    	
    	int port;
    	
    	operations = Integer.parseInt(args[0]);
    	
    	int numClients = Integer.parseInt(args[1]);
    	
    	//Creating servers
    	for(id = 0, port = 13000; id < peerList.size(); id++, port++){
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
    	for(id = 0, port = 13000; id < peerList.size(); id++, port++){
    		try {
    			System.out.println("Connecting to server " + address + ":" + port);
    			Socket s = new Socket(address, port);
    			//System.out.println("Getting Remote Server " + s.getRemoteSocketAddress());
    			s.close();
    			System.out.println("Connected to server " + address + ":" + port);
    		} catch (Exception e){
    			//System.out.println("Not connected to server " + address + ":" + port);
    			//System.out.println("Trying again");
    			id--;
    			port--;
    		}
    	}
    	System.out.println("All servers running");
    	
    	
    	for(int i = 0; i < numClients; i++){
    		new Client(i).start();
    	}
		
	}

}
