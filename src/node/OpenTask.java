package node;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class OpenTask extends Thread{
	
	private int option;
	private Peer peer;
	private Socket socket;
	
	public OpenTask(int option, Socket socket, Peer peer){
		this.option = option;
		this.socket = socket;
		this.peer = peer;
	}
	
	public void run(){
		try{
			
			String key, value;
			DataInputStream dIn = new DataInputStream(socket.getInputStream());
			DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
			
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
			e.printStackTrace();
			try { Thread.sleep(2); }catch(Exception e1){ }
		}
	}

}
