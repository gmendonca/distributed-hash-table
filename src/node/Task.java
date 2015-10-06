package node;

import java.net.Socket;

public class Task extends Thread{
	
	private Socket socket;
	private Peer peer;

	public Task(Socket socket, Peer peer) {
		this.socket = socket;
		this.peer = peer;
	}
	
	public void run() {
		
		
	}

}
