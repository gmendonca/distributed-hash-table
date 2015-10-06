package node;

import java.net.Socket;
import java.util.Scanner;

public class Task extends Thread{
	
	private Socket socket;
	private Peer peer;

	public Task(Socket socket, Peer peer) {
		this.socket = socket;
		this.peer = peer;
	}
	
	public void run() {
		
		int option;
		String fileName;
		String [] peerAddress = new String[0];
		
		Scanner scanner = new Scanner(System.in);
    	while(true){
    		System.out.println("\n\nSelect the option:");
    		System.out.println("1 - Lookup for a file");
    		System.out.println("2 - Download file");
    		
    		option = scanner.nextInt();
    		if(option == 1){
    			
    		}
			scanner.close();
    	}
	}

}
