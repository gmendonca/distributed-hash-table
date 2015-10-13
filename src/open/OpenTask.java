package open;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import node.Peer;

public class OpenTask extends Thread {

	private Socket socket;
	private Peer peer;

	public OpenTask(Socket socket, Peer peer) {
		this.socket = socket;
		this.peer = peer;
	}

	public void run() {
		
		try {
		
			DataInputStream dIn = new DataInputStream(socket.getInputStream());
			DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
			
			while(true){
			
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	
				byte option = dIn.readByte();
				//System.out.println(option);
				String key, value;
	
				switch (option) {
				case 0:
					key = dIn.readUTF();
					//System.out.println(key);
					value = dIn.readUTF();
					dOut.writeBoolean(peer.put(key, value));
					dOut.flush();
					break;
				case 1:
					// get
					key = dIn.readUTF();
					value = peer.get(key);
					dOut.writeUTF((value != null) ? value : "");
					dOut.flush();
					break;
				case 2:
					// delete
					key = dIn.readUTF();
					//System.out.println(key);
					peer.delete(key);
					dOut.writeBoolean(peer.delete(key));
					dOut.flush();
					break;
				default:
					System.out.println("Not an option");
	
				}
			}
		} catch (Exception e) {
			//System.out.println("Nothing happened");
		}

	}

}
