package net.gogo98901.pong.mob;

import java.awt.Graphics;

import net.gogo98901.pong.Pong;
import net.gogo98901.pong.mob.player.Player;
import net.gogo98901.util.Data;
import net.gogo98901.util.GOLog;

public class Ball extends Mob {

	private enum mode {
		START, FLYING
	}

	private mode currentMode;

	private int size = 19;

	private int yAngle;
	private int speed = 4;

	private double startX, startY;

	public Ball(Pong pong) {
		super(pong);
		currentMode = mode.START;

		x = pong.getWidth() / 2;
		y = pong.getHeight() / 2;
	}

	public void update() {
		if (currentMode == mode.START) {
			if (pong.handler.keyboard.space) {
				currentMode = mode.FLYING;
				GOLog.info("Ball rleased");
				yAngle = rand.nextInt(360);
				recalculate();
			}
		}
		if (currentMode == mode.FLYING) {
			double dy = speed * Math.sin(yAngle);
			y += dy;
			if (dy < 0) x -= speed;
			if (dy > 0) x += speed;
			GOLog.info("" + yAngle);
			if (y - (getSize() / 2) <= 1) yAngle = -(yAngle/2);
			if (y + (getSize() / 2) >= pong.getHeight() - 1) yAngle = -(yAngle/2);
			Player p;
			if (isLeft()) {
				p = pong.players.getPlayers()[0];
				if (x + (getSize() / 2) <= p.getX() + p.getWidth()) {
					if (y + (getSize() / 2) >= p.getY() - p.getHeightHalf() && y - (getSize() / 2) <= p.getY() + p.getHeightHalf()) {
						recalculate();
					}
				}
				if (x <= p.getGoal()) {
					pong.players.getPlayers()[1].addPoint();
					reset();
				}
			}
			if (isRight()) {
				p = pong.players.getPlayers()[1];
				if (x >= p.getX() - p.getWidth()) {
					if (y + (getSize() / 2) >= p.getY() - p.getHeightHalf() && y - (getSize() / 2) <= p.getY() + p.getHeightHalf()) {
						recalculate();
					}
				}
				if (x >= p.getGoal()) {
					pong.players.getPlayers()[0].addPoint();
					reset();
				}
			}
		}
	}

	public void render(Graphics g) {
		if (currentMode == mode.START) {
			Data.centerText(0, 0, pong.getWidth(), pong.getHeight(), "Press space to start", g, pong.font.deriveFont(25F));
		}
		if (currentMode == mode.FLYING) {
			g.fillOval((int) x - (size / 2), (int) y - (size / 2), size, size);
		}
		// g.drawLine(0, 10, pong.getWidth(), 10);
		// g.drawLine(0, pong.getHeight() - 10, pong.getWidth(), pong.getHeight() - 10);
	}

	private void setPosition() {
		startX = x;
		startY = y;
	}

	private void recalculate() {
		setPosition();
	}

	public int getXLeft() {
		return (int) x - (size / 2);
	}

	public int getXRight() {
		return (int) x + (size / 2);
	}

	public int getSize() {
		return size;
	}

	public void reset() {
		if (!isStill()) {
			GOLog.info("Ball Stoped");
			currentMode = mode.START;
			x = pong.getWidth() / 2;
			y = pong.getHeight() / 2;
			setPosition();
		}
	}

	public boolean isLeft() {
		return (x < (pong.getWidth() / 2));
	}

	public boolean isRight() {
		return (x > (pong.getWidth() / 2));
	}

	public boolean isStill() {
		if (currentMode == mode.START) return true;
		return false;
	}
}
