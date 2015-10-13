package open;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import node.Peer;
import util.DistributedHashtable;

public class OpenBench {

	public static ArrayList<String> peerList;
	public static int numPeers;
	public static int operations;

	public static void main(String[] args) throws IOException {

		peerList = DistributedHashtable.readConfigFile();

		numPeers = peerList.size();

		int id;

		operations = Integer.parseInt(args[0]);

		int numClients = Integer.parseInt(args[1]);
		
		String[] peerAddress;
		String address;
		int port;

		// Creating servers
		for (id = 0; id < peerList.size(); id++) {
			peerAddress = peerList.get(id).split(":");
			address =  peerAddress[0];
			port = Integer.parseInt(peerAddress[1]);
			
			Peer peer = new Peer(id, address, port);

			ServerSocket serverSocket = new ServerSocket(port);

			// start server
			OpenServer server = new OpenServer(serverSocket, peer);
			server.start();

		}

		ArrayList<Socket> socketList = new ArrayList<Socket>();
		;

		// checking if all are open
		for (id = 0; id < peerList.size(); id++) {
			peerAddress = peerList.get(id).split(":");
			address =  peerAddress[0];
			port = Integer.parseInt(peerAddress[1]);
			
			try {
				System.out.println("Connecting to server " + address + ":"
						+ port);
				Socket s = new Socket(address, port);
				socketList.add(s);
				System.out.println("Connected to server " + address + ":"
						+ port);
			} catch (Exception e) {
				// System.out.println("Not connected to server " + address + ":"
				// + port);
				// System.out.println("Trying again");
				id--;
				port--;
			}
		}

		System.out.println("All servers running");

		new OpenClient(0, socketList).start();

		for (int i = 1; i < numClients; i++) {
			socketList = new ArrayList<Socket>();
			for (id = 0; id < peerList.size(); id++) {
				peerAddress = peerList.get(i).split(":");
				address =  peerAddress[0];
				port = Integer.parseInt(peerAddress[1]);
				System.out.println("Connecting to server " + address + ":"
						+ port);
				Socket s = new Socket(address, port);
				System.out.println("Connected to server " + address + ":"
						+ port);
				socketList.add(s);
			}
			new OpenClient(i, socketList).start();
		}

	}

}
