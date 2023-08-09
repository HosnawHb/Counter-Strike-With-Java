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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Ongame extends JFrame implements KeyListener, WindowListener {
	private static final long serialVersionUID = 1L;
	public static JPanel contentPane, board;
	public int height = 15, width = 30;
	public static Tile[][] tile;
	public static ImageIcon space, wall, playeru, playerd, playerl, playerr, pet;
	public int bullet = 1;
	public long startTime, endTime, totalTime;

// making a client and starting the game
	public static void main(String[] args) {
		try {
			Client client = new Client();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Server not found");
			return;
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ongame frame = new Ongame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Ongame() throws IOException {
		setTitle("COUNTER STRIKE (ONLINE)");
		space = new ImageIcon(new ImageIcon("Pic/space.png").getImage().getScaledInstance(100, -1, Image.SCALE_SMOOTH));
		wall = new ImageIcon(new ImageIcon("Pic/wall.png").getImage().getScaledInstance(100, -1, Image.SCALE_SMOOTH));
		playeru = new ImageIcon(new ImageIcon("Pic/p_up.png").getImage().getScaledInstance(80, -1, Image.SCALE_SMOOTH));
		playerd = new ImageIcon(
				new ImageIcon("Pic/p_down.png").getImage().getScaledInstance(80, -1, Image.SCALE_SMOOTH));
		playerl = new ImageIcon(
				new ImageIcon("Pic/p_left.png").getImage().getScaledInstance(65, -1, Image.SCALE_SMOOTH));
		playerr = new ImageIcon(
				new ImageIcon("Pic/p_right.png").getImage().getScaledInstance(65, -1, Image.SCALE_SMOOTH));
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
		// making the board
		tile = new Tile[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				tile[i][j] = new Tile();
				board.add(tile[i][j].label);
			}
		}
		// starting the game
		java.util.Timer timer = new java.util.Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// clearing texts from the board
				clearNum();
				// TODO Auto-generated method stub
				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						if (Client.t[i][j] == 0) {
							tile[i][j].label.setIcon(space);
						} // clearing the tile
						if (Client.t[i][j] == 3) {
							tile[i][j].label.setIcon(wall);
						} // making a wall
						if (Client.t[i][j] == 1) {
							// putting life num on the player
							tile[i][j].label.setText(String.valueOf(Client.s1.life));
							tile[i][j].label.setForeground(Color.RED);
							tile[i][j].label.setFont(new Font("Thames", Font.BOLD, 25));
							tile[i][j].label.setHorizontalTextPosition(tile[i][j].label.CENTER);
							tile[i][j].label.setVerticalTextPosition(tile[i][j].label.CENTER);
							switch (Client.s1.dir) {
							case 0:
								tile[i][j].label.setIcon(playeru);
								break;
							case 1:
								tile[i][j].label.setIcon(playerd);
								break;
							case 2:
								tile[i][j].label.setIcon(playerl);
								break;
							case 3:
								tile[i][j].label.setIcon(playerr);
								break;

							}
						}
						if (Client.t[i][j] == 2) {
							// putting life num on the player
							tile[i][j].label.setText(String.valueOf(Client.s2.life));
							tile[i][j].label.setForeground(Color.BLUE);
							tile[i][j].label.setFont(new Font("Thames", Font.BOLD, 25));
							tile[i][j].label.setHorizontalTextPosition(tile[i][j].label.CENTER);
							tile[i][j].label.setVerticalTextPosition(tile[i][j].label.CENTER);
							switch (Client.s2.dir) {
							case 0:
								tile[i][j].label.setIcon(playeru);
								break;
							case 1:
								tile[i][j].label.setIcon(playerd);
								break;
							case 2:
								tile[i][j].label.setIcon(playerl);
								break;
							case 3:
								tile[i][j].label.setIcon(playerr);
								break;

							}
						}
						// setting life on a tile
						if (Client.t[i][j] == 4) {
							tile[i][j].label.setIcon(pet);
						}
					}
				}
			}

		}, 0, 50);
	}

	public void clearNum() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				tile[i][j].label.setText("");
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_UP) {
			Client.send("MOVEUP");
		}
		if (code == KeyEvent.VK_DOWN) {
			Client.send("MOVEDOWN");
		}
		if (code == KeyEvent.VK_LEFT) {
			Client.send("MOVELEFT");

		}
		if (code == KeyEvent.VK_RIGHT) {
			Client.send("MOVERIGHT");

		}
		if (code == KeyEvent.VK_SPACE) {
			Client.send("SHOOT");
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		try {
			Client.socket.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dispose();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
