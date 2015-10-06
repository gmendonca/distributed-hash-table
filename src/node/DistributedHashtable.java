package node;

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
	
	//
	public Boolean put(String key, String value, Peer peer){
		hash.put(key, value);
		
		for(Peer p : peerList){
			//update their hashtables
			p.hashtable.put(key, peer);
		}
		
		return false;
		
	}

}
