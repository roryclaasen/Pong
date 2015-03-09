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
		for (int i = 0; i < 2; i++) {
			Player p = getPlayers()[i];
			p.update();
			if (p.getScore() == 2) p.setLevel(10);
			if (p.getScore() == 4) p.setLevel(8);
			if (p.getScore() == 6) p.setLevel(6);
			if (p.getScore() == 10) p.setLevel(4);
			if (p.getScore() == 14) p.setLevel(2);
		}
	}

	public void render(Graphics g) {
		player1.render(g);
		player2.render(g);
	}
	
	public void reset(){
		player1.reset();
		player2.reset();
	}

	public void setLevel(int player, int level) {
		if (player == 1) player1.setLevel(level);
		if (player == 2) player2.setLevel(level);
	}

	public void setLevelBoth(int level) {
		setLevel(1, level);
		setLevel(2, level);
	}

	public Player[] getPlayers() {
		Player[] p = new Player[2];
		p[0] = player1;
		p[1] = player2;
		return p;
	}

	public void renderScores(Graphics g) {
		player1.renderScore(g);
		player2.renderScore(g);
	}

	public int getScoreTotal() {
		return player1.getScore() + player2.getScore();
	}
}
