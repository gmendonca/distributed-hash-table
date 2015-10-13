package open;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import node.Peer;

public class OpenServer extends Thread {

	private ServerSocket serverSocket;
	private Peer peer;
	private int numThreads = 4;

	public OpenServer(ServerSocket serverSocket, Peer peer) {
		this.serverSocket = serverSocket;
		this.peer = peer;
	}

	public void run() {
		
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);

		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			OpenTask t = new OpenTask(socket, peer);
			executor.execute(t);
		}

	}
}