package node;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Task extends Thread {

	private Socket socket;
	private Peer peer;

	public Task(Socket socket, Peer peer) {
		this.socket = socket;
		this.peer = peer;
	}

	public void run() {

		try {
			DataInputStream dIn = new DataInputStream(socket.getInputStream());
			DataOutputStream dOut = new DataOutputStream(
					socket.getOutputStream());

			byte option = dIn.readByte();
			String key, value;

			switch (option) {
			case 0:
				key = dIn.readUTF();
				value = dIn.readUTF();
				// TODO: check if this is not to quickly
				dOut.writeBoolean(peer.put(key, value));
				dOut.flush();
				socket.close();
				break;
			case 1:
				// get
				key = dIn.readUTF();
				// TODO: check if this is not to quickly
				value = peer.get(key);
				dOut.writeUTF((value != null) ? value : "");
				dOut.flush();
				socket.close();
				break;
			case 2:
				// delete
				key = dIn.readUTF();
				// TODO: check if this is not to quickly
				dOut.writeBoolean(peer.delete(key));
				dOut.flush();
				socket.close();
				break;
			default:
				System.out.println("Not an option");

			}
		} catch (Exception e) {
			//System.out.println("Nothing happened");
		}

	}

}
