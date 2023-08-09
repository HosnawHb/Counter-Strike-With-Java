package counter_strike;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Game extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JPanel contentPane, board;
	public int height = 15, width = 30;
	public static Tile[][] tile;
	public static ImageIcon space, wall, playeru, playerd, playerl, playerr, enemyu, enemyd, enemyl, enemyr, pet;
	static int sq = 0, p;
	public static Spaceship[] enemies;
	public static Spaceship player;
	public static int enemy_num = 3;
	public static String life;
	public int bullet = 1;
	public static boolean move = true, iswall = false, win;
	public long startTime, endTime, totalTime;
	private Timer timer = new Timer();

	public static void main(String[] arg) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game frame = new Game();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

// making the game
	public Game() throws IOException {
		setTitle("COUNTER STRIKE");
		startTime = System.currentTimeMillis();
		space = new ImageIcon(new ImageIcon("Pic/space.png").getImage().getScaledInstance(100, -1, Image.SCALE_SMOOTH));
		wall = new ImageIcon(new ImageIcon("Pic/wall.png").getImage().getScaledInstance(100, -1, Image.SCALE_SMOOTH));
		playeru = new ImageIcon(new ImageIcon("Pic/p_up.png").getImage().getScaledInstance(80, -1, Image.SCALE_SMOOTH));
		playerd = new ImageIcon(
				new ImageIcon("Pic/p_down.png").getImage().getScaledInstance(80, -1, Image.SCALE_SMOOTH));
		playerl = new ImageIcon(
				new ImageIcon("Pic/p_left.png").getImage().getScaledInstance(65, -1, Image.SCALE_SMOOTH));
		playerr = new ImageIcon(
				new ImageIcon("Pic/p_right.png").getImage().getScaledInstance(65, -1, Image.SCALE_SMOOTH));
		enemyu = new ImageIcon(new ImageIcon("Pic/e_up.png").getImage().getScaledInstance(100, -1, Image.SCALE_SMOOTH));
		enemyd = new ImageIcon(
				new ImageIcon("Pic/e_down.png").getImage().getScaledInstance(100, -1, Image.SCALE_SMOOTH));
		enemyl = new ImageIcon(
				new ImageIcon("Pic/e_left.png").getImage().getScaledInstance(70, -1, Image.SCALE_SMOOTH));
		enemyr = new ImageIcon(
				new ImageIcon("Pic/e_right.png").getImage().getScaledInstance(70, -1, Image.SCALE_SMOOTH));
		pet = new ImageIcon(new ImageIcon("Pic/life.png").getImage().getScaledInstance(90, -1, Image.SCALE_SMOOTH));
		setIconImage(Toolkit.getDefaultToolkit().getImage(("pic/e_up.png")));
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		addKeyListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 788, 454);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 20.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
		JPanel menu = new JPanel();
		menu.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(menu, gbc_panel);
		menu.setLayout(new GridLayout(1, 4, 0, 0));
		board = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		contentPane.add(board, gbc_panel_1);
		board.setLayout(new GridLayout(height, width, 0, 0));
		tile = new Tile[height][width];
// creating the board and setting the player on it
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				tile[i][j] = new Tile();
				board.add(tile[i][j].label);
				if (i == 0 || j == 0 || i == (height - 1) || j == (width - 1)) {
					tile[i][j].stone();
				}
				if (i == (height - 2) && j == (1)) {
					tile[i][j].player("up");
					tile[i][j].label.setText(String.valueOf(Spaceship.player_life));
					tile[i][j].label.setForeground(Color.RED);
					tile[i][j].label.setFont(new Font("Thames", Font.BOLD, 20));
					tile[i][j].label.setHorizontalTextPosition(tile[i][j].label.CENTER);
					tile[i][j].label.setVerticalTextPosition(tile[i][j].label.CENTER);
					player = new Spaceship(i, j, 0);
				}
			}
		}
		// making random walls
		while (sq < (0.4 * (height - 2) * (width - 2))) {
			Random rand = new Random();
			int rand_i = rand.nextInt(height);
			int rand_j = rand.nextInt(width);
			if (rand_i == 0 || rand_j == 0 || rand_i == (height - 1) || rand_j == (width - 1)
					|| (rand_i == (height - 2) && rand_j == (1))) {

			} else {
				tile[rand_i][rand_j].stone();
				sq++;
			}

		}

		enemy_setter();
		shooter();
		enemy_move();
		life();
	}

// check for the winner
	public int checkWin() {
		int num = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (tile[i][j].enemy_check())
					num++;
			}
		}
		return num;
	}

// enemies shooting method
	public void shooter() {

		java.util.Timer t = new java.util.Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				move = true;
				for (int i = 0; i < enemies.length; i++) {
					int dir = enemies[i].dir;
					if (dir == 0) {
						for (int j = enemies[i].x; j > 0; j--) {
							if (tile[j][enemies[i].y].player_check() && !wall(i)) {
								move = false;
								killplayer();
								break;
							}
						}
					} else if (dir == 1) {
						for (int j = enemies[i].x; j < height; j++) {
							if (tile[j][enemies[i].y].player_check() && !wall(i)) {
								move = false;
								killplayer();
								break;
							}
						}
					} else if (dir == 2) {
						for (int j = enemies[i].y; j > 0; j--) {
							if (tile[enemies[i].x][j].player_check() && !wall(i)) {
								move = false;
								killplayer();
								break;
							}
						}
					} else if (dir == 3) {
						for (int j = enemies[i].y; j < height; j++) {
							if (tile[enemies[i].x][j].player_check() && !wall(i)) {
								move = false;
								killplayer();
								break;
							}
						}
					}

				}
			}
		}, 0, 1000);

	}

// setting enemies on random tiles
	public void enemy_setter() {
		String dir = null;
		enemies = new Spaceship[enemy_num];
		for (int i = 0; i < enemy_num;) {
			Random rand = new Random();
			int rand_i = rand.nextInt(height);
			int rand_j = rand.nextInt(width);
			int rand_d = rand.nextInt(4);
			switch (rand_d) {
			case 0:
				dir = "up";
				break;
			case 1:
				dir = "down";
				break;
			case 2:
				dir = "left";
				break;
			case 3:
				dir = "right";
				break;
			}
			if (tile[rand_i][rand_j].space_check() == true) {
				tile[rand_i][rand_j].enemy(dir);
				enemies[i] = new Spaceship(rand_i, rand_j, rand_d);
				i++;
			}

		}

	}

// making enemies have random moves
	public void enemy_move() {
		Random rand = new Random();
		java.util.Timer t = new java.util.Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				checkWin();
				int d0 = rand.nextInt(4);
				for (int i = 0; i < 3; i++) {
					while (!checkempty(i, d0)) {
						d0 = rand.nextInt(4);
						break;
					}
					if (!enemies[i].check_death(i) && move) {
						switch (d0) {
						case 0:
							moveeup(i);
							break;
						case 1:
							moveedown(i);
							break;
						case 2:
							moveeleft(i);
							break;
						case 3:
							moveeright(i);
							break;
						}
					}

				}

			}
		}, 0, 1000);

	}

// move the enemy up
	public void moveeup(int i) {
		int x = enemies[i].x;
		int y = enemies[i].y;
		if (checkempty(i, 0)) {
			tile[x][y].clear();
			if (tile[x - 1][y].life_check())
				enemies[i].inclife(i, Game.this);
			tile[x - 1][y].enemy_up();
			tile[x][y].label.setText(null);
			tile[x - 1][y].label.setText(String.valueOf(enemies[i].life));
			tile[x - 1][y].label.setForeground(Color.BLACK);
			tile[x - 1][y].label.setFont(new Font("Thames", Font.BOLD, 20));
			tile[x - 1][y].label.setHorizontalTextPosition(tile[x - 1][y].label.CENTER);
			tile[x - 1][y].label.setVerticalTextPosition(tile[x - 1][y].label.CENTER);
			enemies[i].x--;
			enemies[i].dir = 0;

		}

	}

// moving enemy down
	public void moveedown(int i) {
		int x = enemies[i].x;
		int y = enemies[i].y;
		if (checkempty(i, 1)) {
			tile[x][y].clear();
			if (tile[x + 1][y].life_check())
				enemies[i].inclife(i, Game.this);
			tile[x + 1][y].enemy_down();
			tile[x][y].label.setText(null);
			tile[x + 1][y].label.setText(String.valueOf(enemies[i].life));
			tile[x + 1][y].label.setForeground(Color.BLACK);
			tile[x + 1][y].label.setFont(new Font("Thames", Font.BOLD, 20));
			tile[x + 1][y].label.setHorizontalTextPosition(tile[x + 1][y].label.CENTER);
			tile[x + 1][y].label.setVerticalTextPosition(tile[x + 1][y].label.CENTER);
			enemies[i].x++;
			enemies[i].dir = 1;
		}

	}

//moving enemy to the left
	public void moveeleft(int i) {
		int x = enemies[i].x;
		int y = enemies[i].y;
		if (checkempty(i, 2)) {
			tile[x][y].clear();
			if (tile[x][y - 1].life_check())
				enemies[i].inclife(i, Game.this);
			tile[x][y - 1].enemy_left();
			tile[x][y].label.setText(null);
			tile[x][y - 1].label.setText(String.valueOf(enemies[i].life));
			tile[x][y - 1].label.setForeground(Color.BLACK);
			tile[x][y - 1].label.setFont(new Font("Thames", Font.BOLD, 20));
			tile[x][y - 1].label.setHorizontalTextPosition(tile[x][y - 1].label.CENTER);
			tile[x][y - 1].label.setVerticalTextPosition(tile[x][y - 1].label.CENTER);
			enemies[i].y--;
			enemies[i].dir = 2;
		}

	}

// moving enemy to the right
	public void moveeright(int i) {
		int x = enemies[i].x;
		int y = enemies[i].y;
		if (checkempty(i, 3)) {
			tile[x][y].clear();
			if (tile[x][y + 1].life_check())
				enemies[i].inclife(i, Game.this);
			tile[x][y + 1].enemy_right();
			tile[x][y].label.setText(null);
			tile[x][y + 1].label.setText(String.valueOf(enemies[i].life));
			tile[x][y + 1].label.setForeground(Color.BLACK);
			tile[x][y + 1].label.setFont(new Font("Thames", Font.BOLD, 20));
			tile[x][y + 1].label.setHorizontalTextPosition(tile[x][y + 1].label.CENTER);
			tile[x][y + 1].label.setVerticalTextPosition(tile[x][y + 1].label.CENTER);
			enemies[i].y++;
			enemies[i].dir = 3;
		}
	}

// check if around a spaceship is empty
	public boolean checkempty(int i, int direction) {
		boolean empty = false;
		int x = enemies[i].x;
		int y = enemies[i].y;
		switch (direction) {
		case 0:
			if (tile[x - 1][y].space_check())
				empty = true;
			break;
		case 1:
			if (tile[x + 1][y].space_check())
				empty = true;
			break;
		case 2:
			if (tile[x][y - 1].space_check())
				empty = true;
			break;
		case 3:
			if (tile[x][y + 1].space_check())
				empty = true;
			break;
		}
		return empty;
	}

// move player up
	public void up() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (tile[i][j].player_check() && (tile[i - 1][j].space_check())) {
					tile[i][j].clear();
					if (tile[i - 1][j].life_check())
						if (Spaceship.player_life < 4)
							Spaceship.player_life++;
					tile[i - 1][j].player_up();
					tile[i][j].label.setText(null);
					tile[i - 1][j].label.setText(String.valueOf(Spaceship.player_life));
					tile[i - 1][j].label.setForeground(Color.RED);
					tile[i - 1][j].label.setFont(new Font("Thames", Font.BOLD, 20));
					tile[i - 1][j].label.setHorizontalTextPosition(tile[i - 1][j].label.CENTER);
					tile[i - 1][j].label.setVerticalTextPosition(tile[i - 1][j].label.CENTER);
					player.x--;
					player.dir = 0;
					break;
				}
			}
		}
	}

// move player down
	public void down() {
		int p = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (tile[i][j].player_check() && tile[i + 1][j].space_check()) {
					tile[i][j].clear();
					if (tile[i + 1][j].life_check())
						if (Spaceship.player_life < 4)
							Spaceship.player_life++;
					tile[i + 1][j].player_down();
					player.x++;
					tile[i][j].label.setText(null);
					tile[i + 1][j].label.setText(String.valueOf(Spaceship.player_life));
					tile[i + 1][j].label.setForeground(Color.RED);
					tile[i + 1][j].label.setFont(new Font("Thames", Font.BOLD, 20));
					tile[i + 1][j].label.setHorizontalTextPosition(tile[i + 1][j].label.CENTER);
					tile[i + 1][j].label.setVerticalTextPosition(tile[i + 1][j].label.CENTER);
					player.dir = 1;
					p = 1;
				}
			}
			if (p == 1) {
				break;
			}
		}
	}

// move player to the left
	public void left() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (tile[i][j].player_check() && tile[i][j - 1].space_check()) {
					tile[i][j].clear();
					if (tile[i][j - 1].life_check())
						if (Spaceship.player_life < 4)
							Spaceship.player_life++;
					tile[i][j - 1].player_left();
					tile[i][j].label.setText(null);
					tile[i][j - 1].label.setText(String.valueOf(Spaceship.player_life));
					tile[i][j - 1].label.setForeground(Color.RED);
					tile[i][j - 1].label.setFont(new Font("Thames", Font.BOLD, 20));
					tile[i][j - 1].label.setHorizontalTextPosition(tile[i][j - 1].label.CENTER);
					tile[i][j - 1].label.setVerticalTextPosition(tile[i][j - 1].label.CENTER);
					player.y--;
					player.dir = 2;
					break;
				}
			}
		}
	}

// move player to the right
	public void right() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (tile[i][j].player_check() && tile[i][j + 1].space_check()) {
					tile[i][j].clear();
					if (tile[i][j + 1].life_check())
						if (Spaceship.player_life < 4)
							Spaceship.player_life++;
					tile[i][j + 1].player_right();
					tile[i][j].label.setText(null);
					tile[i][j + 1].label.setText(String.valueOf(Spaceship.player_life));
					tile[i][j + 1].label.setForeground(Color.RED);
					tile[i][j + 1].label.setFont(new Font("Thames", Font.BOLD, 20));
					tile[i][j + 1].label.setHorizontalTextPosition(tile[i][j + 1].label.CENTER);
					tile[i][j + 1].label.setVerticalTextPosition(tile[i][j + 1].label.CENTER);
					player.y++;
					player.dir = 3;
					break;
				}
			}
		}
	}

// players shooting method
	public void shoot() {
		int direction = player.dir;
		player.shoot(direction, player.x, player.y, Game.this);

	}

// making lives on random tiles every 20 s
	public void life() {
		Random rand = new Random();
		java.util.Timer t = new java.util.Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				int x = rand.nextInt(height);
				int y = rand.nextInt(width);
				while (!tile[x][y].space_check()) {
					x = rand.nextInt(height);
					y = rand.nextInt(width);

				}
				tile[x][y].life();
			}
		}, 0, 20000);
	}

// check if there is a wall on the way
	public boolean wall(int i) {
		iswall = false;
		int dir = enemies[i].dir;
		if (dir == 0) {
			for (int p = enemies[i].x; p > player.x; p--) {
				if (tile[p][enemies[i].y].wall_check()) {
					iswall = true;
					break;
				}
			}
		} else if (dir == 1) {
			for (int p = enemies[i].x; p < player.x; p++) {
				if (tile[p][player.y].wall_check()) {
					iswall = true;
					break;
				}
			}
		} else if (dir == 2) {
			for (int p = enemies[i].y; p > player.y; p--) {
				if (tile[player.x][p].wall_check()) {
					iswall = true;
					break;
				}
			}
		} else if (dir == 3) {
			for (int p = enemies[i].y; p < player.y; p++) {
				if (tile[player.x][p].wall_check()) {
					iswall = true;
					break;
				}
			}
		}
		return iswall;
	}

// players death method
	public void killplayer() {
		if (bullet > 0) {
			if (Spaceship.player_life > 0) {
				Spaceship.player_life--;
			} else {
				tile[player.x][player.y].clear();
				tile[player.x][player.y].label.setText(null);
				JOptionPane.showMessageDialog(contentPane, "GAME OVER");
				timer.cancel();
				dispose();
				Menu.main(null);
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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_UP) {
			up();
		}
		if (code == KeyEvent.VK_DOWN) {
			down();
		}
		if (code == KeyEvent.VK_LEFT) {

			left();
		}
		if (code == KeyEvent.VK_RIGHT) {

			right();
		}
		if (code == KeyEvent.VK_SPACE) {
			shoot();
			if (checkWin() == 0) {
				// calculate the time
				endTime = System.currentTimeMillis();
				JOptionPane.showMessageDialog(Game.contentPane, (endTime - startTime) / 1000);
				timer.cancel();
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
				Menu.main(null);
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
