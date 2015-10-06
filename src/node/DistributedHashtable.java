package node;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

public class DistributedHashtable {
	private Hashtable<String,String> hash;
	private Hashtable<String, Peer> serverHash;
	private ArrayList<Peer> peerList;
	
	public DistributedHashtable(Hashtable<String, Peer> serverHash, ArrayList<Peer> peerList){
		this.serverHash = serverHash;
		this.peerList = peerList;
		
		hash = new Hashtable<String, String>();
	}
	
	public Boolean put(String key, String value, Peer peer) throws IOException{
		hash.put(key, value);
		
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
