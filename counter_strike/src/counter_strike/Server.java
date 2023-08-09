package counter_strike;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.TimerTask;

public class Server {
	public static Spaceship s1, s2;
	public static int height = 15, width = 30;
	public static int[][] t = new int[height][width];
	private static final int port = 9000;
	static int sq = 0, bullet = 1;

	public static void main(String[] arg) throws IOException {
		System.out.println("waiting for connection..");
		ServerSocket socket = new ServerSocket(port);
		setBoard(); // making the board so as to send it to the client
		setWall(); // random walls
		setPlayer(); // putting players on random tiles
		setLife(); // putting life on random tiles every 20 s
		try {
			System.out.println("Waiting for the first player...");
			Socket socket1 = socket.accept();
			System.out.println("player 1 connected");
			System.out.println("waiting for the second player...");
			Socket socket2 = socket.accept();
			System.out.println("player 2 connected");
			STH th1 = new STH(socket1, 1);
			th1.start();
			STH th2 = new STH(socket2, 2);
			th2.start();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

// making the border of the board
	public static void setBoard() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (i == 0 || j == 0 || i == (height - 1) || j == (width - 1)) {
					t[i][j] = 3;
				}
			}
		}
	}

// arranging players on random tiles
	public static void setPlayer() {
		Random rand = new Random();
		int x1 = rand.nextInt(height - 2); // x of s1
		int y1 = rand.nextInt(width - 2); // y of s1
		int dir1 = rand.nextInt(4); // direction of s1
		int x2 = rand.nextInt(height - 2); // x of s2
		int y2 = rand.nextInt(width - 2); // y of s2
		int dir2 = rand.nextInt(4); // direction ofs2
		while (t[x1][y1] != 0) { // checking if the tile is empty
			x1 = rand.nextInt(height - 2);
			y1 = rand.nextInt(width - 2);
		}
		s1 = new Spaceship(x1, y1, dir1); // making a new player
		t[x1][y1] = 1;
		while (t[x2][y2] != 0) {
			x2 = rand.nextInt(height - 2);
			y2 = rand.nextInt(width - 2);
		}
		s2 = new Spaceship(x2, y2, dir2); // making a new player
		t[x2][y2] = 2;
	}

// setting walls on random tiles (40%)
	public static void setWall() {
		while (sq < (0.4 * (height - 2) * (width - 2))) {
			Random rand = new Random();
			int rand_i = rand.nextInt(height);
			int rand_j = rand.nextInt(width);
			if (rand_i == 0 || rand_j == 0 || rand_i == (height - 1) || rand_j == (width - 1)
					|| (rand_i == (height - 2) && rand_j == (1))) {
			} else {
				t[rand_i][rand_j] = 3;
				sq++;
			}

		}
	}

// setting life every 20 s
	public static void setLife() {
		Random rand = new Random();
		java.util.Timer timer = new java.util.Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				int x = rand.nextInt(height);
				int y = rand.nextInt(width);
				while (t[x][y] != 0) {
					x = rand.nextInt(height);
					y = rand.nextInt(width);

				}
				t[x][y] = 4;
			}
		}, 0, 20000);
	}

//socket thread
	static class STH extends Thread {
		Socket socket;
		BufferedReader in;
		PrintWriter out;
		ObjectOutputStream oos;
		private int spaceship;

		public STH(Socket socket, int spaceship) {
			this.socket = socket;
			this.spaceship = spaceship;

		}

		// sending
		public void send(String s) {
			out.println(s);

		}

		// reading
		public String read() {
			String s = null;
			try {
				s = in.readLine();
			} catch (IOException e) {
				System.out.println(e.toString());
			}
			return s;
		}

// writing s1 and s2 and tile numbers (20 frames per s)
		public void tools() {
			java.util.Timer timer = new java.util.Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						oos = new ObjectOutputStream(socket.getOutputStream());
						Ontools tools = new Ontools(t, s1, s2);
						oos.writeObject(tools);
						oos.flush();
					} catch (IOException e) {
						try {
							socket.close();
						} catch (IOException e1) {
						}
					}
				}

			}, 0, 50);
		}

// moving up
		public void up() {
			if (spaceship == 1) {
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						if (t[i][j] == 1 && (t[i - 1][j] == 0 || t[i - 1][j] == 4)) {
							t[i][j] = 0;
							if (t[i - 1][j] == 4) {
								if (s1.life < 4)
									s1.life++;
							}
							t[i - 1][j] = 1;
							s1.x--;
							s1.dir = 0;
							break;
						}
					}
				}
			}
			if (spaceship == 2) {
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						if (t[i][j] == 2 && (t[i - 1][j] == 0 || t[i - 1][j] == 4)) {
							t[i][j] = 0;

							if (t[i - 1][j] == 4) {

								if (s2.life < 4)
									s2.life++;
							}
							t[i - 1][j] = 2;
							s2.x--;
							s2.dir = 0;
							break;
						}
					}
				}
			}
		}

// moving down
		public void down() {
			int p = 0;
			if (spaceship == 1) {
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						if (t[i][j] == 1 && (t[i + 1][j] == 0 || t[i + 1][j] == 4)) {
							t[i][j] = 0;
							if (t[i + 1][j] == 4) {
								if (s1.life < 4)
									s1.life++;
							}
							t[i + 1][j] = 1;
							s1.x++;
							s1.dir = 1;
							p = 1;
						}
					}
					if (p == 1)
						break;
				}
			}
			if (spaceship == 2) {
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						if (t[i][j] == 2 && (t[i + 1][j] == 0 || t[i + 1][j] == 4)) {
							t[i][j] = 0;

							if (t[i + 1][j] == 4) {

								if (s2.life < 4)
									s2.life++;
							}
							t[i + 1][j] = 2;
							s2.x++;
							s2.dir = 1;
							p = 1;
						}
					}
					if (p == 1)
						break;
				}

			}
		}

// moving left
		public void left() {
			if (spaceship == 1) {
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						if (t[i][j] == 1 && (t[i][j - 1] == 0 || t[i][j - 1] == 4)) {
							t[i][j] = 0;
							if (t[i][j - 1] == 4) {
								if (s1.life < 4)
									s1.life++;
							}
							t[i][j - 1] = 1;
							s1.y--;
							s1.dir = 2;
							break;
						}
					}
				}
			}
			if (spaceship == 2) {
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						if (t[i][j] == 2 && (t[i][j - 1] == 0 || t[i][j - 1] == 4)) {
							t[i][j] = 0;
							if (t[i][j - 1] == 4) {
								if (s2.life < 4)
									s2.life++;
							}
							t[i][j - 1] = 2;
							s2.y--;
							s2.dir = 2;
							break;
						}
					}
				}
			}
		}

// moving right
		public void right() {
			if (spaceship == 1) {
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						if (t[i][j] == 1 && (t[i][j + 1] == 0 || t[i][j + 1] == 4)) {
							t[i][j] = 0;
							if (t[i][j + 1] == 4) {
								if (s1.life < 4)
									s1.life++;
							}
							t[i][j + 1] = 1;
							s1.y++;
							s1.dir = 3;
							break;
						}
					}
				}
			}
			if (spaceship == 2) {
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						if (t[i][j] == 2 && (t[i][j + 1] == 0 || t[i][j + 1] == 4)) {
							t[i][j] = 0;

							if (t[i][j + 1] == 4) {

								if (s2.life < 4)
									s2.life++;
							}
							t[i][j + 1] = 2;
							s2.y++;
							s2.dir = 3;
							break;
						}
					}
				}
			}
		}

// recreating player 5 seconds after death
		public void rebirth(int spaceship) {
			Random rand = new Random();
			int x = rand.nextInt(height - 2);
			int y = rand.nextInt(width - 2);
			while (t[x][y] != 0) {
				x = rand.nextInt(height - 2);
				y = rand.nextInt(width - 2);
			}
			if (spaceship == 1) {
				s1.x = x;
				s1.y = y;
				s1.life = 3;
				t[x][y] = 1;
			}
			if (spaceship == 2) {
				s2.x = x;
				s2.y = y;
				s2.life = 3;
				t[x][y] = 2;
			}
		}

		public class kill1 extends Thread {
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
				rebirth(1);
			}
		}
		public class kill2 extends Thread {
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
				rebirth(2);
			}
		}
// killing method
		public void kill(int spaceship) {
			if (spaceship == 1) {
				if (bullet > 0) {
					if (s1.life > 0) {
						s1.life--;
					} else {
						t[s1.x][s1.y] = 0;
						kill1 kill = new kill1();
						kill.start();
					}
					bullet--;
					new java.util.Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							bullet = 1;
						}
					}, 1000);
				}
			}
			if (spaceship == 2) {
				if (bullet > 0) {
					if (s2.life > 0) {
						s2.life--;
					} else {
						t[s2.x][s2.y] = 0;
						kill2 kill = new kill2();
						kill.start();
					}
					bullet--;
					new java.util.Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							bullet = 1;
						}
					}, 1000);
				}
			}

		}

// check walls for player 1
		public boolean checkwalls1(int dir) {
			boolean wall = false;
			if (dir == 0) {
				for (int i = s1.x; i > s2.x; i--) {
					if (t[i][s1.y] == 3)
						wall = true;
					else
						wall = false;
				}
			}
			if (dir == 1) {
				for (int i = s1.x; i < s2.x; i++) {
					if (t[i][s1.y] == 3)
						wall = true;
					else
						wall = false;
				}
			}
			if (dir == 2) {
				for (int i = s1.y; i > s2.y; i--) {
					if (t[s1.x][i] == 3)
						wall = true;
					else
						wall = false;
				}
			}
			if (dir == 3) {
				for (int i = s1.y; i < s2.y; i++) {
					if (t[s1.x][i] == 3)
						wall = true;
					else
						wall = false;
				}
			}
			return wall;
		}

// check walls for player 2
		public boolean checkwalls2(int dir) {
			boolean wall = false;
			if (dir == 0) {
				for (int i = s2.x; i > s1.x; i--) {
					if (t[i][s2.y] == 3)
						wall = true;
					else
						wall = false;
				}
			}
			if (dir == 1) {
				for (int i = s2.x; i < s1.x; i++) {
					if (t[i][s2.y] == 3)
						wall = true;
					else
						wall = false;
				}
			}
			if (dir == 2) {
				for (int i = s2.y; i > s1.y; i--) {
					if (t[s2.x][i] == 3)
						wall = true;
					else
						wall = false;
				}
			}
			if (dir == 3) {
				for (int i = s2.y; i < s1.y; i++) {
					if (t[s2.x][i] == 3)
						wall = true;
					else
						wall = false;
				}
			}
			return wall;
		}

// shooting method
		public void shoot() {
			if (spaceship == 1) {
				int dir = s1.dir;
				if (dir == 0) {
					for (int i = s1.x; i > 0; i--) {
						if (s1.y == s2.y && (!checkwalls1(dir))) {
							kill(2);
							break;
						}
					}
				}
				if (dir == 1) {
					for (int i = s1.x; i < height; i++) {
						if (s1.y == s2.y && (!checkwalls1(dir))) {
							kill(2);
							break;
						}
					}
				}
				if (dir == 2) {
					for (int i = s1.y; i > 0; i--) {
						if (s1.x == s2.x && (!checkwalls1(dir))) {
							kill(2);
							break;
						}
					}
				}
				if (dir == 3) {
					for (int i = s1.y; i < width; i++) {
						if (s1.x == s2.x && (!checkwalls1(dir))) {
							kill(2);
							break;
						}
					}
				}
			}
			if (spaceship == 2) {
				int dir = s2.dir;
				if (dir == 0) {
					for (int i = s2.x; i > 0; i--) {
						if (s1.y == s2.y && (!checkwalls2(dir))) {
							kill(1);
							break;
						}
					}
				}
				if (dir == 1) {
					for (int i = s2.x; i < height; i++) {
						if (s1.y == s2.y && (!checkwalls2(dir))) {
							kill(1);
							break;
						}
					}
				}
				if (dir == 2) {
					for (int i = s2.y; i > 0; i--) {
						if (s1.x == s2.x && (!checkwalls2(dir))) {
							kill(1);
							break;
						}
					}
				}
				if (dir == 3) {
					for (int i = s2.y; i < width; i++) {
						if (s1.x == s2.x && (!checkwalls2(dir))) {
							kill(1);
							break;
						}
					}
				}
			}
		}

		public void run() {
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
				send("start");
				tools();
				while (!socket.isClosed()) {
					String line = read();
					if (line.startsWith("MOVE")) {
						if (line.substring(4).startsWith("UP")) {
							up();
						} else if (line.substring(4).startsWith("DOWN")) {
							down();
						} else if (line.substring(4).startsWith("LEFT")) {
							left();
						} else if (line.substring(4).startsWith("RIGHT")) {
							right();
						}
					}
					if (line.startsWith("SHOOT")) {
						shoot();
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}