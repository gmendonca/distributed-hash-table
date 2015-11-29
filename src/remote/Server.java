package remote;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import util.DistributedHashtable;
import node.Peer;

public class Server extends Thread {

	public static void main(String[] args) throws IOException {
		
		ArrayList<String> peerList = DistributedHashtable.readConfigFile();
		
		if(args.length < 1){
			System.out.println("Usage: java -jar build/RemoteServer.jar <Id>");
			return;
		}
		
		int id = Integer.parseInt(args[0]);
		if(id < 0 || id > peerList.size()){
			System.out.println("Id should be positive and shouldn't be greater than the number provided in the config file!");
			return;
		}
		
		String[] peerAddress;
		String address;
		int port;
		
		peerAddress = peerList.get(id).split(":");
		address =  peerAddress[0];
		port = Integer.parseInt(peerAddress[1]);
		
		Peer peer = new Peer(id, address, port);
		
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(port);

		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Task t = new Task(socket, peer);
			t.start();
		}

	}
}