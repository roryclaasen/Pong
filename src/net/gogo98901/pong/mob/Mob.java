package net.gogo98901.pong.mob;

import java.awt.Graphics;
import java.util.Random;

import net.gogo98901.pong.Pong;

public abstract class Mob {

	protected final Random rand = new Random();
	protected double x, y;
	protected Pong pong;

	public Mob(Pong pong) {
		this.pong = pong;
	}

	public abstract void update();
	public abstract void render(Graphics g);

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
