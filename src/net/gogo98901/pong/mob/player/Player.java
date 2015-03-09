package net.gogo98901.pong.mob.player;

import java.awt.Graphics;

import net.gogo98901.pong.Pong;
import net.gogo98901.pong.handler.Keyboard;
import net.gogo98901.pong.mob.Mob;
import net.gogo98901.util.Data;
import net.gogo98901.util.GOLog;

public class Player extends Mob {
	private final int bound = 100, speed = 3;
	private int level = 12;
	private int width = 10;
	private int height = width * level;

	private int score;

	public enum keySet {
		RIGHT, LEFT, ONLINE
	}

	private Keyboard key;
	private keySet set;

	public Player(Pong pong, keySet set) {
		super(pong);
		key = pong.handler.keyboard;
		if (set == null) {
			GOLog.severe("keySet not set");
			return;
		}
		this.set = set;
		y = pong.getHeight() / 2;
		if (set == keySet.LEFT) x = bound;
		if (set == keySet.RIGHT) x = pong.getWidth() - bound;
	}

	public void update() {
		if (!pong.ball.isStill()) {
			if (set == keySet.LEFT) {
				if (key.w) move(-1);
				if (key.s) move(1);
			}
			if (set == keySet.RIGHT) {
				if (key.arrowUp) move(-1);
				if (key.arrowDown) move(1);
			}
		}
	}

	private void move(int dir) {
		if (dir == -1) if (y - (height / 2) - speed > 0) y -= speed;
		if (dir == 1) if (y + (height / 2) + speed < pong.getHeight()) y += speed;
	}

	public void render(Graphics g) {
		// g.drawLine(getGoal(), 0, getGoal(), pong.getHeight());
		g.fillRect(x - (width / 2), y - (height / 2), width, height);
	}

	public void reset() {
		y = pong.getHeight() / 2;
	}

	public void renderScore(Graphics g) {
		if (set == keySet.LEFT) Data.centerText(0, 0, pong.getWidth() / 2, 50, "" + score, g, pong.font.deriveFont(65F));
		if (set == keySet.RIGHT) Data.centerText(pong.getWidth() / 2, 0, pong.getWidth() / 2, 50, "" + score, g, pong.font.deriveFont(65F));
	}

	public void setLevel(int level) {
		this.level = level;
		this.height = this.width * level;
	}

	public int getWidth() {
		return width;
	}

	public int getWidthHalf() {
		return width / 2;
	}

	public int getHeight() {
		return height;
	}

	public int getHeightHalf() {
		return height / 2;
	}

	public int getGoal() {
		if (set == keySet.LEFT) return x - width;
		if (set == keySet.RIGHT) return x + width;
		return 0;
	}

	public void addPoint() {
		addScore(1);
	}

	public void addScore(int point) {
		score += point;
	}

	public int getScore() {
		return score;
	}
}
