package bench;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import node.Assign;
import node.Peer;
import node.Server;
import util.DistributedHashtable;

public class Benchmarking {

	public static ArrayList<String> peerList;
	public static int numPeers;
	public static int operations;

	public static void main(String[] args) throws IOException {
		
		if(args.length < 2){
			System.out.println("Usage: java -jar build/OpenBench.jar <Number of operations> <Number of Clients>");
			return;
		}
		
		peerList = DistributedHashtable.readConfigFile();

		numPeers = peerList.size();

		int id;

		operations = Integer.parseInt(args[0]);
		if(operations < 0){
			System.out.println("Number of operations should be a positive number!");
			return;
		}

		int numClients = Integer.parseInt(args[1]);
		if(numClients > numPeers){
			System.out.println("Number of Clients shouldn't be greater than the number provided in the config file!");
			return;
		}
		
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
			Server server = new Server(serverSocket, peer);
			server.start();

			// start assign
			Assign assign = new Assign(peer);
			assign.start();
		}

		// checking if all are open
		for (id = 0; id < peerList.size(); id++) {
			peerAddress = peerList.get(id).split(":");
			address =  peerAddress[0];
			port = Integer.parseInt(peerAddress[1]);
			
			try {
				System.out.println("Testing connection to server " + address + ":"
						+ port);
				Socket s = new Socket(address, port);
				// System.out.println("Getting Remote Server " +
				// s.getRemoteSocketAddress());
				s.close();
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
		System.out.println("All servers running");

		for (int i = 0; i < numClients; i++) {
			new Client(i).start();
		}

	}

}
