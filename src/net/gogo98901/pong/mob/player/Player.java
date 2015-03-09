package net.gogo98901.pong.mob.player;

import java.awt.Graphics;

import net.gogo98901.pong.Pong;
import net.gogo98901.pong.handler.Keyboard;
import net.gogo98901.pong.mob.Mob;
import net.gogo98901.util.GOLog;

public class Player extends Mob{
	private final int bound = 100, speed = 3;
	private int level = 10;
	private int width = 10;
	private int height = width * level;

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
		if (set == keySet.LEFT) {
			if (key.w) move(-1);
			if (key.s) move(1);
		}
		if (set == keySet.RIGHT) {
			if (key.arrowUp) move(-1);
			if (key.arrowDown) move(1);
		}
	}

	private void move(int dir) {
		if (dir == -1) if (y - (height / 2) - speed > 0) y -= speed;
		if (dir == 1) if (y + (height / 2) + speed < pong.getHeight()) y += speed;

	}

	public void render(Graphics g) {
		if (set == keySet.LEFT) g.drawLine(x - width, 0, x - width, pong.getHeight());
		if (set == keySet.RIGHT) g.drawLine(x + width, 0, x + width, pong.getHeight());
		g.fillRect(x - (width / 2), y - (height / 2), width, height);
	}

	public void setLevel(int level) {
		this.level = level;
		this.height = this.width * level;
	}
}
