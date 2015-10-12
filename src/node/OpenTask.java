package node;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class OpenTask extends Thread{
	
	private int option;
	private DataInputStream dIn;
	private DataOutputStream dOut;
	private Peer peer;
	
	public OpenTask(int option, DataInputStream dIn, DataOutputStream dOut, Peer peer){
		this.option = option;
		this.dIn = dIn;
		this.dOut = dOut;
		this.peer = peer;
	}
	
	public void run(){
		try{
			
			String key, value;
			
			switch(option){
				case 0:
					key = dIn.readUTF();
					value = dIn.readUTF();
					//TODO: check if this is not to quickly
					dOut.writeBoolean(peer.put(key, value));
					dOut.flush();
					//socket.close();
					break;
				case 1:
					//get
					key = dIn.readUTF();
					//TODO: check if this is not to quickly
					value = peer.get(key);
					dOut.writeUTF((value != null) ? value : "");
					dOut.flush();
					//socket.close();
					break;
				case 2:
					//delete
					key = dIn.readUTF();
					//TODO: check if this is not to quickly
					dOut.writeBoolean(peer.delete(key));
					dOut.flush();
					//socket.close();
					break;
				default:
					System.out.println("Not an option");
				
			}
		}catch (Exception e){
			//System.out.println("Nothing happened");
		}
	}

}
