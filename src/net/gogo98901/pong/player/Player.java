package net.gogo98901.pong.player;

import java.awt.Graphics;

public class Player {
	public enum keySet {
		RIGHT, LEFT
	}

	private keySet set;

	public Player setKeys(keySet set) {
		this.set = set;
		return this;
	}

	public void update() {

	}

	public void render(Graphics g) {

	}
}
