package remote;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import util.DistributedHashtable;

public class Client extends Thread {

	public static ArrayList<Socket> socketList;


	// put
	public static Boolean put(String key, String value, int pId)
			throws Exception {
		if (key.length() > 24)
			return false;
		if (value.length() > 1000)
			return false;

		Socket socket = socketList.get(pId);
		boolean ack;
		synchronized(socket){
			DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
			// put option
			dOut.writeByte(0);
			dOut.flush();
	
			// key, value
			dOut.writeUTF(key);
			dOut.flush();
			dOut.writeUTF(value);
			dOut.flush();
	
			DataInputStream dIn = new DataInputStream(socket.getInputStream());
			ack = dIn.readBoolean();
		}

		return ack;
	}

	// get
	public static String get(String key, int pId) throws IOException {
		if (key.length() > 24)
			return null;

		Socket socket = socketList.get(pId);
		
		String value;
		synchronized(socket){
			DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
	
			// get option
			dOut.writeByte(1);
			dOut.flush();
	
			// key
			dOut.writeUTF(key);
			dOut.flush();
	
			DataInputStream dIn = new DataInputStream(socket.getInputStream());
			value = dIn.readUTF();
		}

		return value;
	}

	// delete
	public static Boolean delete(String key, int pId) throws Exception {
		if (key.length() > 24)
			return false;

		Socket socket = socketList.get(pId);
		boolean ack;
		synchronized(socket){
			DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
	
			// put option
			dOut.writeByte(2);
			dOut.flush();
	
			// key
			dOut.writeUTF(key);
			dOut.flush();
	
			DataInputStream dIn = new DataInputStream(socket.getInputStream());
			ack = dIn.readBoolean();
		}

		return ack;
	}

	public static void main(String[] args) throws IOException {
		
		ArrayList<String> peerList = DistributedHashtable.readConfigFile();
		int numPeers = peerList.size();
		
		if(args.length < 1){
			System.out.println("Usage: java -jar build/RemoteClient.jar <Number of Operations>");
			return;
		}
		
		String[] peerAddress;
		String address;
		int port;
		
		
		int num = 0;
		
		for(int i = 0; i < peerList.size(); i++){
			peerAddress = peerList.get(i).split(":");
			if(Inet4Address.getLocalHost().getHostAddress().equals(peerAddress[0]))
				num = i;
		}
		
		int operations = Integer.parseInt(args[0]);
		if(operations < 0){
			System.out.println("Number of operations should be a positive number!");
			return;
		}
		
		ArrayList<Socket> socketList = new ArrayList<Socket>();
		
		System.out.println("Running as Client " + num);

		int id;
		// checking if all are open
		for (id = 0; id < peerList.size(); id++) {
			peerAddress = peerList.get(id).split(":");
			address =  peerAddress[0];
			port = Integer.parseInt(peerAddress[1]);
			
			try {
				System.out.println("Testing connection to server " + address + ":"
						+ port);
				Socket s = new Socket(address, port);
				socketList.add(s);
				System.out.println("Server " + address + ":"
						+ port + " is running.");
			} catch (Exception e) {
				// System.out.println("Not connected to server " + address + ":"
				// + port);
				// System.out.println("Trying again");
				id--;
				port--;
			}
		}
		
		Client.socketList = socketList;
		
		long start, stop, time;
		int pId;
		String key;
		//String value;
		//boolean result;

		start = time = System.currentTimeMillis();

		for (int i = operations * (num + 1); i < operations
				* (num + 2); i++) {
			key = Integer.toString(i);
			pId = DistributedHashtable.hash(key, numPeers);

			try {
				put(key, UUID.randomUUID().toString(), pId);
				//result = put(key, UUID.randomUUID().toString(), pId);
				//System.out.println("put " + i + " " + result);
			} catch (Exception e) {
				System.out
						.println("Couldn't put the key-value pair in the system.");
			}
		}

		stop = System.currentTimeMillis();

		System.out.println("Client " + num + ": Running time to "
				+ operations + " put operations: " + (stop - start)
				+ "ms.");

		start = System.currentTimeMillis();

		for (int i = operations * (num + 1); i < operations
				* (num + 2); i++) {
			key = Integer.toString(i);
			pId = DistributedHashtable.hash(key, numPeers);

			try {
				get(key, pId);
				//value = get(key, pId);
				//System.out.println(value);
			} catch (Exception e) {
				System.out
						.println("Couldn't get the value pair from the system.");
			}
		}

		stop = System.currentTimeMillis();

		System.out.println("Client " + num + ": Running time to "
				+ operations + " get operations: " + (stop - start)
				+ "ms.");

		start = System.currentTimeMillis();

		for (int i = operations * (num + 1); i < operations
				* (num + 2); i++) {
			key = Integer.toString(i);
			pId = DistributedHashtable.hash(key, numPeers);

			try {
				delete(key, pId);
				//result = delete(key, pId);
				//System.out.println("deleted " + i + " " + result);
			} catch (Exception e) {
				//e.printStackTrace();
				System.out
						.println("Couldn't delete the key-value pair in the system.");
			}
		}

		stop = System.currentTimeMillis();

		System.out.println("Client " + num + ": Running time to "
				+ operations + " del operations: " + (stop - start)
				+ "ms.");

		System.out.println("Client " + num + ": Overall time: "
				+ (System.currentTimeMillis() - time) + "ms.");

	}

}
