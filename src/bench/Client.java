package bench;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import util.DistributedHashtable;

public class Client extends Thread{
	
	//put
	public static Boolean put(String key, String value, int pId) throws Exception{
		if(key.length() > 24) return false;
		if(value.length() > 1000) return false;
			
		Socket socket = OpenBench.socketList.get(pId);
		
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
			
		Socket socket = OpenBench.socketList.get(pId);
		
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
			
		Socket socket = OpenBench.socketList.get(pId);
		
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
	
	public void run(){
		long start, stop, time;
    	int pId;
    	String key;
    	
    	start = time = System.currentTimeMillis();
    	
    	for(int i = 0; i < OpenBench.operations; i++){
    		key = Integer.toString(i);
    		pId = DistributedHashtable.hash(key, OpenBench.numPeers);
    		
    		try {
				put(key,UUID.randomUUID().toString(),pId);
			}catch (Exception e){
				System.out.println("Couldn't put the key-value pair in the system.");
			}
    	}
    	
    	stop = System.currentTimeMillis();
    	
    	System.out.println("Running time to "+ OpenBench.operations + " put operations: " + (stop-start) + "ms.");
    	
    	start = System.currentTimeMillis();
    	
    	for(int i = 0; i < OpenBench.operations; i++){
    		key = Integer.toString(i);
    		pId = DistributedHashtable.hash(key, OpenBench.numPeers);
    		
    		try {
				get(key,pId);
				//System.out.println(value);
			}catch (Exception e){
				System.out.println("Couldn't get the value pair from the system.");
			}
    	}
    	
    	stop = System.currentTimeMillis();
    	
    	System.out.println("Running time to "+ OpenBench.operations + " get operations: " + (stop-start) + "ms.");
    	
    	start = System.currentTimeMillis();
    	
    	for(int i = 0; i < OpenBench.operations; i++){
    		key = Integer.toString(i);
    		pId = DistributedHashtable.hash(key, OpenBench.numPeers);
    		
    		try {
				delete(key,pId);
			}catch (Exception e){
				System.out.println("Couldn't delte the key-value pair in the system.");
			}
    	}
    	
    	stop = System.currentTimeMillis();
    	
    	System.out.println("Running time to "+ OpenBench.operations + " del operations: " + (stop-start) + "ms.");
    	
    	System.out.println("Overall time: " + (System.currentTimeMillis() - time) + "ms.");
    	
    	for(Socket sock : OpenBench.socketList){
    		try {
				sock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
	}

}
