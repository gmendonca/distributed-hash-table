package node;
import java.io.IOException;
import java.net.ServerSocket;
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
	private PeerQueue<Peer> peerQueue;
	public Hashtable<String, Peer> hashtable;
	
	
	public Peer(String directory, ArrayList<String> fileNames, int numFiles, String address, int port) throws IOException{
		this.directory = directory;
		this.fileNames = fileNames;
		this.numFiles = numFiles;
		this.address = address;
		this.port = port;
		
		peerQueue = new PeerQueue<Peer>();
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
		
		public PeerQueue<Peer> getPeerQueue(){
			return peerQueue;
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
		
		public void getPeerQueue(PeerQueue<Peer> peerQueue){
			this.peerQueue = peerQueue;
		}
		
		public static void main(String[] args){
			Peer p = new Peer(address, fileNames, numFiles, address, numFiles);
			p.hashtable = Util.readHashtableFromFile();
		}
}

	
