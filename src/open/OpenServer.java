package open;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import node.Peer;

public class OpenServer extends Thread {

	private ServerSocket serverSocket;
	private Peer peer;

	public OpenServer(ServerSocket serverSocket, Peer peer) {
		this.serverSocket = serverSocket;
		this.peer = peer;
	}

	public void run() {

		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			synchronized (peer.getPeerQueue()) {
				peer.addToPeerQueue(socket);
			}
		}

	}
}