package net.gogo98901.pong.mob.player;

import java.awt.Graphics;

import net.gogo98901.pong.Pong;

public class Players {
	private Player player1, player2;

	public Players(Pong pong) {
		player1 = new Player(pong, Player.keySet.LEFT);
		player2 = new Player(pong, Player.keySet.RIGHT);
	}

	public void update() {
		player1.update();
		player2.update();
	}

	public void render(Graphics g) {
		player1.render(g);
		player2.render(g);
	}

	public void setLevel(int player, int level) {
		if (player == 1) player1.setLevel(level);
		if (player == 2) player1.setLevel(level);
	}

	public void setLevelBoth(int level) {
		setLevel(1, level);
		setLevel(2, level);
	}
	
	public Player[] getPLayers(){
		Player[] p = new Player[2];
		p[0] = player1;
		p[1] = player2;
		return p;
	}
}
