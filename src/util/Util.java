package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

public class Util {

	public static void copy(InputStream in, OutputStream out)
			throws IOException {
		byte[] buffer = new byte[1024];
		int count = 0;
		long start = System.currentTimeMillis();
		while ((count = in.read(buffer)) != -1) {
			out.write(buffer, 0, count);
		}
		System.out.println("Download took "
				+ (System.currentTimeMillis() - start) + " ms.");
	}

	public static long toSeconds(long start, long end) {
		return (end - start) / 1000;
	}

	public static String getExternalIP() throws IOException {
		URL url = new URL("http://checkip.amazonaws.com/");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				url.openStream()));
		return br.readLine();
	}
}
