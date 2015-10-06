package node;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Task extends Thread{
	
	private Socket socket;
	private Peer peer;

	public Task(Socket socket, Peer peer) {
		this.socket = socket;
		this.peer = peer;
	}
	
	public void run() {
		try{
			
			DataInputStream dIn = new DataInputStream(socket.getInputStream());
			DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
			
			byte option = dIn.readByte();
			
			switch(option){
				case 0:
					//put
					socket.close();
					break;
				case 1:
					//get
					socket.close();
					break;
				case 2:
					//delete
					socket.close();
					break;
				case 3:
					//update
					
					socket.close();
				default:
					System.out.println("Not an option");
				
			}
		}catch (IOException ioe){
			ioe.printStackTrace();
		}
		
	}

}
