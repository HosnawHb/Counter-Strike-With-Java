package counter_strike;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Menu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] arg) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu frame = new Menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Menu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		// starting the offline mode
		JButton btnNewButton = new JButton("OFFLINE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Game.main(null);
			}
		});
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 0, 102));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnNewButton.setBounds(118, 62, 190, 44);
		contentPane.add(btnNewButton);
		// starting the online mode
		class online extends Thread {
			public void run() {
				Ongame.main(null);
			}
		}
		JButton btnClientOnlie = new JButton("Client Online");
		btnClientOnlie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				online online = new online();
				online.start();
			}
		});
		btnClientOnlie.setForeground(new Color(255, 255, 255));
		btnClientOnlie.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnClientOnlie.setBackground(new Color(0, 0, 102));
		btnClientOnlie.setBounds(118, 116, 190, 44);
		contentPane.add(btnClientOnlie);
		// connecting to the server
		JButton btnServerOnline = new JButton("Server Online");
		class server extends Thread {
			public void run() {
				try {
					Server.main(null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		btnServerOnline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server s = new server();
				s.start();
			}
		});
		btnServerOnline.setForeground(new Color(255, 255, 255));
		btnServerOnline.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnServerOnline.setBackground(new Color(0, 0, 102));
		btnServerOnline.setBounds(118, 170, 190, 44);
		contentPane.add(btnServerOnline);
	}
}
