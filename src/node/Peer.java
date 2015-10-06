package node;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

import util.PeerQueue;
import util.Util;


public class Peer {
	
	private int peerId;
	private String address;
	private int port;
	
	private PeerQueue<Socket> peerQueue;
	
	private Hashtable<String, Peer> hashtable;
	private ArrayList<Peer> peerList;
	private DistributedHashtable distributedHashtable;
	
	
	public Peer(int peerId, String address, int port) throws IOException{
		this.peerId = peerId;
		this.address = address;
		this.port = port;
		
		peerQueue = new PeerQueue<Socket>();
		hashtable = Util.readHashtableFromFile();
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
	
	public Hashtable<String, Peer> getHashtable(){
		return hashtable;
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
	
	public void setHashtable(Hashtable<String, Peer> hashtable){
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
	
	//serialize
	
	public byte[] serialize(Peer peer) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(peer);
		return baos.toByteArray();
	}
	
	//deserialize
	
	public Peer deserialize(byte[] bytes) throws Exception{
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		return (Peer)ois.readObject();
	}
	
	//put
	public Boolean put(String key, String value){
		return false;
	}
	
	//get
	public String get(String key) throws IOException {
		String value = null;
		
		Peer p = hashtable.get(key);
		
		if(p == null)
			return null;
		
		Socket socket = new Socket(p.address, p.port);
		DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
    	
    	//Option to look for a file
    	dOut.writeByte(1);
    	
    	
    	//Reading the peer Address that has the file
    	DataInputStream dIn = new DataInputStream(socket.getInputStream());
    	byte found = dIn.readByte();
    	
    	
    	
    	dOut.close();
    	dIn.close();
    	socket.close();
		return value;
	}
	
	//delete
	public Boolean delete(String key){
		return false;
	}
		
}

	
