package open;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

import util.DistributedHashtable;

public class OpenClient extends Thread {

	private int num;
	public static ArrayList<Socket> socketList;

	public OpenClient(int num, ArrayList<Socket> socketList) {
		this.num = num;
		OpenClient.socketList = socketList;
	}

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

	public void run() {
		long start, stop, time;
		int pId;
		String key;
		//String value;
		//boolean result;

		start = time = System.currentTimeMillis();

		for (int i = OpenBench.operations * (num + 1); i < OpenBench.operations
				* (num + 2); i++) {
			key = Integer.toString(i);
			pId = DistributedHashtable.hash(key, OpenBench.numPeers);

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
				+ OpenBench.operations + " put operations: " + (stop - start)
				+ "ms.");

		start = System.currentTimeMillis();

		for (int i = OpenBench.operations * (num + 1); i < OpenBench.operations
				* (num + 2); i++) {
			key = Integer.toString(i);
			pId = DistributedHashtable.hash(key, OpenBench.numPeers);

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
				+ OpenBench.operations + " get operations: " + (stop - start)
				+ "ms.");

		start = System.currentTimeMillis();

		for (int i = OpenBench.operations * (num + 1); i < OpenBench.operations
				* (num + 2); i++) {
			key = Integer.toString(i);
			pId = DistributedHashtable.hash(key, OpenBench.numPeers);

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
				+ OpenBench.operations + " del operations: " + (stop - start)
				+ "ms.");

		System.out.println("Client " + num + ": Overall time: "
				+ (System.currentTimeMillis() - time) + "ms.");

	}

}
