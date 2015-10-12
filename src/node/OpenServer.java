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
				
				new OpenTask(option, dIn, dOut, peer).start();
					

			}catch (Exception e){
				//System.out.println("Nothing happened");
			}
		}
		
	}
}