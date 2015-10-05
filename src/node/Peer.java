package node;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import util.PeerQueue;
import util.Util;


public class Peer {
	
	private int peerId;
	private int numFiles;
	private ArrayList<String> fileNames;
	private String directory;
	private String address;
	private int port;
	private PeerQueue<Connection> peerQueue;
	private Hashtable<String, Peer> hashtable;
	
	
	public Peer(int peerId, String directory, ArrayList<String> fileNames, int numFiles, String address, int port) throws IOException{
		this.peerId = peerId;
		this.directory = directory;
		this.fileNames = fileNames;
		this.numFiles = numFiles;
		this.address = address;
		this.port = port;
		
		peerQueue = new PeerQueue<Connection>();
		hashtable = Util.readHashtableFromFile();
	}
	
	//getters
		public int getPeerId(){
			return peerId;
		}
		
		public int getNumFiles(){
			return numFiles;
		}
		
		public ArrayList<String> getFileNames(){
			return fileNames;
		}
		
		public String getDirectory(){
			return directory;
		}
		
		public String getAddress(){
			return address;
		}
		
		public int getPort(){
			return port;
		}
		
		public PeerQueue<Connection> getPeerQueue(){
			return peerQueue;
		}
		
		public Hashtable<String, Peer> getHashtable(){
			return hashtable;
		}
		
		//setters
		public void setPeerId(int peerId){
			this.peerId = peerId;
		}
		
		public void setNumFiles(int numFiles){
			this.numFiles = numFiles;
		}
		
		public void setFileNames(ArrayList<String> fileNames){
			this.fileNames.addAll(fileNames);
		}
		
		public void addFileName(String fileName){
			this.fileNames.add(fileName);
		}
		
		public void setDirectory(String directory){
			this.directory = directory;
		}
		
		public void setAddress(String address){
			this.address = address;
		}
		
		public void setPort(int port){
			this.port = port;
		}
		
		public void setPeerQueue(PeerQueue<Connection> peerQueue){
			this.peerQueue = peerQueue;
		}
		
		public void setHashtable(Hashtable<String, Peer> hashtable){
			this.hashtable = hashtable;
		}
		
		public void addToPeerQueue(Connection conn){
			peerQueue.add(conn);
		}
		
		public Connection peekPeerQueue(Connection conn){
			return peerQueue.peek();
		}
		
		public void pollPeerQueue(Connection conn){
			peerQueue.add(conn);
		}
		
}

	
