package node;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

public class DistributedHashtable {
	private Hashtable<String, Peer> hash;
	private ArrayList<Peer> peerList;
	
	public DistributedHashtable(Hashtable<String, Peer> hash, ArrayList<Peer> peerList){
		this.hash = hash;
		this.peerList = peerList;
	}
	
	public Boolean put(String key, String value, Peer peer) throws IOException{
		//hash.put(key, value);
		//TODO: add method to local has table
		
		for(Peer p : peerList){
			//update their hash tables
			//p.hashtable.put(key, peer);
			Socket socket = new Socket(p.getAddress(), p.getPort());
			
			DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
	    	
	    	//Option to update the hash table
	    	dOut.writeByte(3);
	    	
	    	//Sending Key and Peer Object to update hash table of all other peers
	    	dOut.writeUTF(key);
	    	dOut.write(peer.serialize(peer));
	    	
	    	
			
		}
		
		return false;
	}

}
