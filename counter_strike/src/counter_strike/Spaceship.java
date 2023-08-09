package counter_strike;

import java.io.Serializable;
import java.util.TimerTask;

public class Spaceship implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	int x, y, X, Y;
	public Tile[][] tile;
	public Tile nexttile;
	public boolean move = true;
	int life = 3;
	public static int player_life = 3;
	private int bullet = 1;
	int dir;
	public Spaceship[] enemies;

	public Spaceship(int X, int Y, int Direction) {
		super();
		this.x = X;
		this.y = Y;
		this.dir = Direction;
		life = 3;
	}

// players shooting method
	public void shoot(int dir, int x, int y, Game game) {
		enemies = Game.enemies;
		int p = 1;
		if (bullet > 0) {
			switch (dir) {
			case 0:
				for (int j = x; j > 0; j--) {
					for (int i = 0; i < enemies.length; i++) {
						if (y == enemies[i].y) {
							if (!wall(i, x, y, game, dir)) {
								killenemy(game, i);
								p = 1;
								break;
							}

						}

					}
					if (p == 1)
						break;
				}
				break;
			case 1:
				for (int j = y; j < game.height; j++) {
					for (int i = 0; i < enemies.length; i++) {
						if (y == enemies[i].y) {
							if (!wall(i, x, y, game, dir)) {
								killenemy(game, i);
								p = 1;
								break;
							}

						}

					}
					if (p == 1)
						break;
				}
				break;
			case 2:
				for (int j = x; j > 0; j--) {
					for (int i = 0; i < enemies.length; i++) {
						if (x == enemies[i].x) {
							if (!wall(i, x, y, game, dir)) {
								killenemy(game, i);
								p = 1;
								break;
							}
						}

					}
					if (p == 1)
						break;
				}
				break;
			case 3:
				for (int j = x; j < game.width; j++) {
					for (int i = 0; i < enemies.length; i++) {
						if (x == enemies[i].x) {
							if (!wall(i, x, y, game, dir)) {
								killenemy(game, i);
								p = 1;
								break;
							}
						}

					}
					if (p == 1)
						break;
				}
				break;
			}
			bullet--;
			new java.util.Timer().schedule(new TimerTask() { // reload time
				@Override
				public void run() {
					bullet = 1; // ammo rounds
				}
			}, 1000);
		}

	}

	// killing enemy
	public void killenemy(Game game, int i) {
		enemies = Game.enemies;
		if (enemies[i].life > 0) {
			enemies[i].life--;
		} else {
			tile[enemies[i].x][enemies[i].y].label.setIcon(game.space);
			tile[enemies[i].x][enemies[i].y].label.setText(null);
			Game.enemy_num--;

		}
	}

// checking for dead enemies
	public boolean check_death(int i) {
		enemies = Game.enemies;
		if (enemies[i].life > 0)
			return false;
		else
			return true;
	}

// checking if there is a wall on the way
	public boolean wall(int i, int x, int y, Game game, int dir) {

		enemies = Game.enemies;
		tile = Game.tile;
		boolean wall = false;
		switch (dir) {
		case 0:
			for (int p = x; p > enemies[i].x; p--) {
				if (tile[p][y].wall_check()) {
					wall = true;
					break;
				}
			}
			break;
		case 1:
			for (int p = x; p < enemies[i].x; p++) {
				if (tile[p][y].wall_check()) {
					wall = true;
					break;
				}
			}
			break;
		case 2:
			for (int p = y; p > enemies[i].y; p--) {
				if (tile[x][p].wall_check()) {
					wall = true;
					break;
				}
			}
			break;
		case 3:
			for (int p = y; p < enemies[i].y; p++) {
				if (tile[x][p].wall_check()) {
					wall = true;
					break;
				}
			}
			break;
		}
		return wall;
	}

// increasing life of enemies
	public void inclife(int i, Game game) {
		enemies = Game.enemies;
		if (enemies[i].life < 4)
			enemies[i].life++;

	}

// increasing life of the player
	public void incplayerlife(Game game) {
		if (Game.player.life < 4)
			Game.player.life++;
	}
}
