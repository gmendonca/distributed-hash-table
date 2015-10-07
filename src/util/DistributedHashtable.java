package util;

import java.io.IOException;
import java.util.ArrayList;

public class DistributedHashtable {
	
	public static ArrayList<String> readConfigFile() throws IOException{
		//read from a JSON
		return null;
	}
	
	public static int hash(String key, int numPeers){
		int sum = 0;
		for(int i = 0; i < key.length(); i++)
			sum += key.charAt(i);
			
		return sum%numPeers;
	}
}
