package bench;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

import util.DistributedHashtable;

public class Client extends Thread {
	private int num;

	public Client(int num) {
		this.num = num;
	}

	// put
	public static Boolean put(String key, String value, int pId)
			throws Exception {
		if (key.length() > 24)
			return false;
		if (value.length() > 1000)
			return false;

		String[] peerAddress = Benchmarking.peerList.get(pId).split(":");
		Socket socket = new Socket(peerAddress[0],
				Integer.parseInt(peerAddress[1]));
		DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());

		// put option
		dOut.writeByte(0);
		dOut.flush();

		// key, value
		dOut.writeUTF(key);
		dOut.writeUTF(value);
		dOut.flush();

		socket.close();

		return true;
	}

	// get
	public static String get(String key, int pId) throws IOException {
		if (key.length() > 24)
			return null;

		String[] peerAddress = Benchmarking.peerList.get(pId).split(":");
		Socket socket = new Socket(peerAddress[0],
				Integer.parseInt(peerAddress[1]));
		DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());

		// get option
		dOut.writeByte(1);
		dOut.flush();

		// key
		dOut.writeUTF(key);
		dOut.flush();

		socket.close();

		return "";
	}

	// delete
	public static Boolean delete(String key, int pId) throws Exception {
		if (key.length() > 24)
			return false;

		String[] peerAddress = Benchmarking.peerList.get(pId).split(":");
		Socket socket = new Socket(peerAddress[0],
				Integer.parseInt(peerAddress[1]));
		DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());

		// put option
		dOut.writeByte(2);
		dOut.flush();

		// key, value
		dOut.writeUTF(key);
		dOut.flush();

		socket.close();

		return true;
	}

	public void run() {
		long start, stop, time;
		int pId;
		String key;

		start = time = System.currentTimeMillis();

		for (int i = Benchmarking.operations * (num + 1); i < Benchmarking.operations
				* (num + 2); i++) {
			key = Integer.toString(i);
			pId = DistributedHashtable.hash(key, Benchmarking.numPeers);

			try {
				put(key, UUID.randomUUID().toString(), pId);
				//System.out.println("put " + i);
			} catch (Exception e) {
			}
		}

		stop = System.currentTimeMillis();

		System.out.println("Client " + num + ": Running time to "
				+ Benchmarking.operations + " put operations: "
				+ (stop - start) + "ms.");

		start = System.currentTimeMillis();

		for (int i = Benchmarking.operations * (num + 1); i < Benchmarking.operations
				* (num + 2); i++) {
			key = Integer.toString(i);
			pId = DistributedHashtable.hash(key, Benchmarking.numPeers);

			try {
				get(key, pId);
				// System.out.println(value);
			} catch (Exception e) {
			}
		}

		stop = System.currentTimeMillis();

		System.out.println("Client " + num + ": Running time to "
				+ Benchmarking.operations + " get operations: "
				+ (stop - start) + "ms.");

		start = System.currentTimeMillis();

		for (int i = Benchmarking.operations * (num + 1); i < Benchmarking.operations
				* (num + 2); i++) {
			key = Integer.toString(i);
			pId = DistributedHashtable.hash(key, Benchmarking.numPeers);

			try {
				delete(key, pId);
			} catch (Exception e) {
			}
		}

		stop = System.currentTimeMillis();

		System.out.println("Client " + num + ": Running time to "
				+ Benchmarking.operations + " del operations: "
				+ (stop - start) + "ms.");

		System.out.println("Client " + num + ": Overall time: "
				+ (System.currentTimeMillis() - time) + "ms.");

	}
}
