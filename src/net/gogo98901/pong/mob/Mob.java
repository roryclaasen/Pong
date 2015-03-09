package net.gogo98901.pong.mob;

import java.awt.Graphics;

import net.gogo98901.pong.Pong;

public abstract class Mob {

	protected int x, y;
	protected Pong pong;

	public Mob(Pong pong) {
		this.pong = pong;
	}

	public abstract void update();
	public abstract void render(Graphics g);

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
