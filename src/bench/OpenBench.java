package bench;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import node.OpenServer;
import node.Peer;
import util.DistributedHashtable;

public class OpenBench {
	
	private static ArrayList<String> peerList;
	private static ArrayList<Socket> socketList;
	
	//put
	public static Boolean put(String key, String value, int pId) throws Exception{
		if(key.length() > 24) return false;
		if(value.length() > 1000) return false;
			
		Socket socket = socketList.get(pId);
		
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
			
		return ack;
	}
		
	//get
	public static String get(String key, int pId) throws IOException {
		if(key.length() > 24) return null;
			
		Socket socket = socketList.get(pId);
		
		DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
			
		//get option
		dOut.writeByte(1);
		dOut.flush();
			
		//key
		dOut.writeUTF(key);
		dOut.flush();
			
		DataInputStream dIn = new DataInputStream(socket.getInputStream());
		String value = dIn.readUTF();
			
		return value;
	}
		
	//delete
	public static Boolean delete(String key, int pId) throws Exception{
		if(key.length() > 24) return false;
			
		Socket socket = socketList.get(pId);
		
		DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
			
		//put option
		dOut.writeByte(2);
		dOut.flush();
			
		//key, value
		dOut.writeUTF(key);
		dOut.flush();
			
		DataInputStream dIn = new DataInputStream(socket.getInputStream());
		boolean ack = dIn.readBoolean();
			
		return ack;
	}
	
	public static void main(String[] args) throws IOException{
		
		peerList = DistributedHashtable.readConfigFile();
		socketList = new ArrayList<Socket>();
		
		int numPeers = peerList.size();
		
		int id;
    	
    	String address = InetAddress.getLocalHost().getHostAddress();
    	//address = args[1];
    	
    	int port;
    	
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
    	
    	long start, stop, time;
    	int pId;
    	String key, value;
    	
    	start = time = System.currentTimeMillis();
    	
    	for(int i = 0; i < 10; i++){
    		key = Integer.toString(i);
    		pId = DistributedHashtable.hash(key, numPeers);
    		
    		try {
				put(key,UUID.randomUUID().toString(),pId);
			}catch (Exception e){
				System.out.println("Couldn't put the key-value pair in the system.");
			}
    	}
    	
    	stop = System.currentTimeMillis();
    	
    	System.out.println("Running time to 100K put operations: " + (stop-start) + "ms.");
    	
    	start = time = System.currentTimeMillis();
    	
    	for(int i = 0; i < 10; i++){
    		key = Integer.toString(i);
    		pId = DistributedHashtable.hash(key, numPeers);
    		
    		try {
				value = get(key,pId);
				System.out.println(value);
			}catch (Exception e){
				System.out.println("Couldn't get the value pair from the system.");
			}
    	}
    	
    	stop = System.currentTimeMillis();
    	
    	System.out.println("Running time to 100K get operations: " + (stop-start) + "ms.");
    	
    	start = time = System.currentTimeMillis();
    	
    	for(int i = 0; i < 10; i++){
    		key = Integer.toString(i);
    		pId = DistributedHashtable.hash(key, numPeers);
    		
    		try {
				delete(key,pId);
			}catch (Exception e){
				System.out.println("Couldn't delte the key-value pair in the system.");
			}
    	}
    	
    	stop = System.currentTimeMillis();
    	
    	System.out.println("Running time to 100K del operations: " + (stop-start) + "ms.");
    	
    	System.out.println("Overall time: " + (System.currentTimeMillis() - time) + "ms.");
    	
    	for(Socket sock : socketList){
    		sock.close();
    	}
		
	}

}
