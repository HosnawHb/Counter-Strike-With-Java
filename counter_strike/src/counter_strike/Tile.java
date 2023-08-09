package counter_strike;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tile {
	public JLabel label;
	public JPanel panel;
	public String dir;
	public int position;

	public Tile() {
		label = new JLabel(Game.space);
		label.setIcon(Game.space);

	}

// putting stone 
	public void stone() {
		this.label.setIcon(Game.wall);

	}

// putting life
	public void life() {
		this.label.setIcon(Game.pet);
	}

// putting player based on the direction
	public void player(String dir) {
		if (dir == "up") {
			this.label.setIcon(Game.playeru);
			this.position = 0;

		} else if (dir == "down") {
			this.label.setIcon(Game.playerd);
			this.position = 1;

		} else if (dir == "left") {
			this.label.setIcon(Game.playerl);
			this.position = 2;

		} else if (dir == "right") {
			this.label.setIcon(Game.playerr);
			this.position = 3;

		}
	}

// putting enemies based on directions
	public void enemy(String dir) {
		this.dir = dir;
		if (dir == "up") {
			this.label.setIcon(Game.enemyu);
			this.position = 0;
		} else if (dir == "down") {
			this.label.setIcon(Game.enemyd);
			this.position = 1;
		} else if (dir == "left") {
			this.label.setIcon(Game.enemyl);
			this.position = 2;
		} else if (dir == "right") {
			this.label.setIcon(Game.enemyr);
			this.position = 3;
		}
	}

// check if the tile has a wall
	public boolean wall_check() {
		if (this.label.getIcon().equals(Game.wall)) {
			return true;
		} else {
			return false;
		}
	}

	// check if the tile has a life
	public boolean life_check() {
		if (this.label.getIcon().equals(Game.pet))
			return true;
		else
			return false;
	}

// check if the tile is empty
	public boolean space_check() {
		if (this.label.getIcon().equals(Game.space) || this.label.getIcon().equals(Game.pet)) {
			return true;
		} else
			return false;
	}

// checking for the player
	public boolean player_check() {
		if (this.label.getIcon().equals(Game.playerd) || this.label.getIcon().equals(Game.playeru)
				|| this.label.getIcon().equals(Game.playerl) || this.label.getIcon().equals(Game.playerr)) {
			return true;
		} else {
			return false;
		}
	}

// checking for enemies
	public boolean enemy_check() {
		if (this.label.getIcon().equals(Game.enemyd) || this.label.getIcon().equals(Game.enemyu)
				|| this.label.getIcon().equals(Game.enemyl) || this.label.getIcon().equals(Game.enemyr)) {
			return true;
		} else {
			return false;
		}
	}

	public void clear() {
		this.label.setIcon(Game.space);
	}

	public void player_up() {
		this.label.setIcon(Game.playeru);
	}

	public void player_down() {
		this.label.setIcon(Game.playerd);
	}

	public void player_left() {
		this.label.setIcon(Game.playerl);
	}

	public void player_right() {
		this.label.setIcon(Game.playerr);
	}

	public void enemy_up() {
		this.label.setIcon(Game.enemyu);
	}

	public void enemy_down() {
		this.label.setIcon(Game.enemyd);
	}

	public void enemy_left() {
		this.label.setIcon(Game.enemyl);
	}

	public void enemy_right() {
		this.label.setIcon(Game.enemyr);
	}
}
