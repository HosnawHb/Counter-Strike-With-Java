package counter_strike;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static BufferedReader in;
	public static PrintWriter out;
	public static Socket socket;
	public static ObjectInputStream ois;
	public static int[][] t;
	public static Spaceship s1, s2;

	public Client() throws IOException {
		try { // connecting and reading from the server
			socket = new Socket("127.0.0.1", 9000);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			read();
			CTH c = new CTH();
			c.start();

		} catch (IOException e) {
			socket.close();
		}

	}

	// reading data
	public static String read() {
		String s = null;
		try {
			s = in.readLine();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return s;
	}

	// sending data
	public static void send(String s) {
		out.println(s);

	}

	// a thread to send an object to the server as long as the socket is not closed
	static class CTH extends Thread {
		public void run() {
			while (!socket.isClosed()) {
				try {
					ois = new ObjectInputStream(socket.getInputStream());
					Ontools ot = (Ontools) ois.readObject();
					t = ot.tile;
					s1 = ot.s1;
					s2 = ot.s2;
				} catch (IOException | ClassNotFoundException e) {
				}
			}
		}
	}
}
