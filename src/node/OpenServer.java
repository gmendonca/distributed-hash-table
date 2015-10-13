package node;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class OpenServer extends Thread{
	
	private ServerSocket serverSocket;
	private Peer peer;
	
	public OpenServer(ServerSocket serverSocket, Peer peer){
		this.serverSocket = serverSocket;
		this.peer = peer;
	}

	public void run(){
		Socket socket = null;
		DataInputStream dIn = null;
		DataOutputStream dOut = null;
		try {
			socket = serverSocket.accept();
			dIn = new DataInputStream(socket.getInputStream());
			dOut = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true){
			try{
				
				byte option = dIn.readByte();
				System.out.println(option);
				String key, value;
				
				//new OpenTask(option, socket, peer).start();
				
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
				//e.printStackTrace();
				//try { Thread.sleep(2); }catch(Exception e1){ }
			}
		}
		
	}
}