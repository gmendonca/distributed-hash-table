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
    		int optpeer;
    		
			if(option == 1){
    			System.out.println("Enter file name:");
    			fileName = scanner.next();
				peerAddress = peer.lookup(fileName, new Socket(serverAddress, serverPort), 1);
    		}
    		else if (option == 2){
    			if(peerAddress.length == 0){
    				System.out.println("Lookup for the peer first.");
    			}else if(peerAddress.length == 1 && Integer.parseInt(peerAddress[0].split(":")[2]) == peer.getPeerId()){
    				System.out.println("This peer has the file already, not downloading then.");
    			}else if(peerAddress.length == 1){
    				String[] addrport = peerAddress[0].split(":");
    				System.out.println("Downloading from peer " + addrport[2] + ": " + addrport[0] + ":" + addrport[1]);
    				peer.download(addrport[0], Integer.parseInt(addrport[1]), fileName, -1);
    			}else {
    				System.out.println("Select from which peer you want to Download the file:");
    				for(int i = 0; i < peerAddress.length; i++){
    					String[] addrport = peerAddress[i].split(":");
    					System.out.println((i+1) + " - " + addrport[0] + ":" + addrport[1]);
    				}
    				optpeer = scanner.nextInt();
    				while(optpeer > peerAddress.length || optpeer < 1){
    					System.out.println("Select a valid option:");
    					optpeer = scanner.nextInt();
    				}
    				String[] addrport = peerAddress[optpeer-1].split(":");
    				peer.download(addrport[0], Integer.parseInt(addrport[1]), fileName, -1);
    			}
    		}else{
    			scanner.close();
    			System.out.println("Peer desconnected!");
    			return;
    		}
    	}
	}

}
