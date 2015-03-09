package net.gogo98901.pong.player;

import java.awt.Graphics;

public class Players {
	private Player player1, player2;

	public Players() {
		player1 = new Player().setKeys(Player.keySet.LEFT);
		player2 = new Player().setKeys(Player.keySet.RIGHT);
	}

	public void update() {
		player1.update();
		player2.update();
	}

	public void render(Graphics g) {
		player1.render(g);
		player2.render(g);
	}
}
