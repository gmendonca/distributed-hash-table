package remote;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import util.DistributedHashtable;
import node.Peer;

public class Server extends Thread {

	public static void main(String[] args) throws IOException {
		
		ArrayList<String> peerList = DistributedHashtable.readConfigFile();
		
		if(args.length > 1){
			System.out.println("Usage: java -jar build/RemoteServer.jar");
			return;
		}
		
		String[] peerAddress;
		String address;
		int port;
		
		
		int id = 0;
		
		for(int i = 0; i < peerList.size(); i++){
			peerAddress = peerList.get(i).split(":");
			if(Inet4Address.getLocalHost().getHostAddress().equals(peerAddress[0]))
				id = i;
		}
		
		System.out.println("Running as Server " + id);
		
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