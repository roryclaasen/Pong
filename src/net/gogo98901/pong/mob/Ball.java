package net.gogo98901.pong.mob;

import java.awt.Graphics;

import net.gogo98901.pong.Pong;

public class Ball extends Mob {

	private int size = 19;

	private int dir;

	public Ball(Pong pong) {
		super(pong);
		x = pong.getWidth() / 2;
		y = pong.getHeight() / 2;
	}

	public void update() {
	}

	public void render(Graphics g) {
		g.fillOval(x - (size / 2), y - (size / 2), size, size);
	}

	public int getXLeft() {
		return x - (size / 2);
	}

	public int getXRight() {
		return x + (size / 2);
	}

	public int getSize() {
		return size;
	}

}
