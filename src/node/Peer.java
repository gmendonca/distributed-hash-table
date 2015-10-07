package node;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

import util.DistributedHashtable;
import util.PeerQueue;


public class Peer {
	
	private int peerId;
	private String address;
	private int port;
	
	private PeerQueue<Socket> peerQueue;
	
	private Hashtable<String, String> hashtable;
	private ArrayList<String> peerList;
	
	
	public Peer(int peerId, String address, int port) throws IOException{
		this.peerId = peerId;
		this.address = address;
		this.port = port;
		
		peerQueue = new PeerQueue<Socket>();
		hashtable = new Hashtable<String, String>();
		peerList = DistributedHashtable.readConfigFile();
	}
	
	//getters
	public int getPeerId(){
		return peerId;
	}
	
	public String getAddress(){
		return address;
	}
	
	public int getPort(){
		return port;
	}
	
	public PeerQueue<Socket> getPeerQueue(){
		return peerQueue;
	}
	
	public Hashtable<String, String> getHashtable(){
		return hashtable;
	}
	public ArrayList<String> getPeerList(){
		return peerList;
	}
	
	//setters
	public void setPeerId(int peerId){
		this.peerId = peerId;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public void setPort(int port){
		this.port = port;
	}
	
	public void setPeerQueue(PeerQueue<Socket> peerQueue){
		this.peerQueue = peerQueue;
	}
	
	public void setHashtable(Hashtable<String, String> hashtable){
		this.hashtable = hashtable;
	}
	
	
	//Queue methods
	public void addToPeerQueue(Socket sock){
		peerQueue.add(sock);
	}
	
	public Socket peekPeerQueue(){
		return peerQueue.peek();
	}
	
	public Socket pollPeerQueue(){
		return peerQueue.poll();
	}
	
	//put
	public Boolean put(String key, String value) throws Exception{
		return false;
	}
	
	//get
	public String get(String key) throws IOException {
		return null;
	}
	
	//delete
	public Boolean delete(String key){
		return false;
	}
		
}

	
