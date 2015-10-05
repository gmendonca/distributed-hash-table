package node;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Income extends Thread{
	private int numThreads = 4;
	
	ExecutorService executor = Executors.newFixedThreadPool(numThreads);

	while(true){
		if(peerQueue.peek() == null){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			continue;
		}
		synchronized(peerQueue){
			//System.out.println("Added to executor");
			Connection c = peerQueue.poll();
			Server s = new Server(c.getSocket(), c.getDirectory());
			executor.execute(s);
		}
	}
}
