import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Thread{
	
	private ServerSocket serverSocket;
	private Peer peer;
	
	public Server(ServerSocket serverSocket, Peer peer){
		this.serverSocket = serverSocket;
		this.peer = peer;
	}

	public void run(){
		
		while(true){
			Socket socket = null;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) { }
			synchronized(peer.getPeerQueue()){
				peer.getPeerQueue().add(new Connection(socket,peer.getDirectory()));
			}
			/*try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
		}
		
	}
}
