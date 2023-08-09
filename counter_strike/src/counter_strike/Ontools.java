package counter_strike;

import java.io.Serializable;

public class Ontools implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public int[][] tile;
	public Spaceship s1, s2;
	public Ontools(int[][] tile, Spaceship s1, Spaceship s2) {
		super();
		this.tile = tile;
		this.s1 = s1;
		this.s2 = s2;
	}

}
