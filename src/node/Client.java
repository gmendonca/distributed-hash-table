package node;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

import util.Util;

public class Client extends Thread{
	
	public static void main(String[] args) throws IOException{
		
		int id = 0;
		try{
			id = Integer.parseInt(args[0]);
		}catch(Exception e){
			System.out.println("Put a valid Id");
		}
		
		String dir = args[1];
    	File folder = new File(dir);
    	
    	if(!folder.isDirectory()){
			System.out.println("Put a valid directory name");
			return;
    	}
    	
    	String address = InetAddress.getLocalHost().getHostAddress();
    	int port = 0;
    	try{
    		port = Integer.parseInt(args[1]);
    	} catch (Exception e){
    		System.out.println("Put a valid port number");
    	}
    	
    	ArrayList<String> fileNames = Util.listFilesForFolder(folder);
    	
    	Peer peer = new Peer(id, dir, fileNames, fileNames.size(), address, port);
    	
    	ServerSocket serverSocket = new ServerSocket(port);
    	
    	Server server = new Server(serverSocket, peer);
    	server.start();
    	
    	
    	
		
		
	}
}
