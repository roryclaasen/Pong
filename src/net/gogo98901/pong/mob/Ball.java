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
	private Color LastColor = Color.WHITE;

	private int dirX, dirY;
	private int size = 19;

	private int yAngle;
	private double speed = 4;

	private int coolDownTop, coolDownDown, coolDownPaddle;

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
				yAngle = rand.nextInt(360);
			}
		}
		if (currentMode == mode.FLYING) {
			if (yAngle > 360) yAngle = yAngle - 360;
			if (yAngle < 0) yAngle = yAngle + 360;
			double dy = Math.sin(Math.toRadians(yAngle));
			double dx = Math.cos(Math.toRadians(yAngle));
			y += dy * speed;
			x += dx * speed;
			if (dx * speed < 0) dirX = -1;
			if (dx * speed > 0) dirX = 1;
			if (dy * speed < 0) dirY = -1;
			if (dy * speed > 0) dirY = 1;
			if (y - (getSize() / 2) <= 0) {
				if (coolDownTop == 0) {
					GOLog.info("called 1");
					if (dirX == -1) yAngle = yAngle - 90;
					if (dirX == 1) yAngle = yAngle + 90;
				}
				coolDownTop++;
			} else coolDownTop = 0;
			if (y + (getSize() / 2) >= pong.getHeight()) {
				if (coolDownDown == 0) {
					GOLog.info("called 2");
					if (dirX == -1) yAngle = yAngle + 90;
					if (dirX == 1) yAngle = yAngle - 90;
				}
				coolDownDown++;
			} else coolDownDown = 0;
			Player p;
			if (isLeft()) {
				p = pong.players.getPlayers()[0];
				if (x <= p.getX() + p.getWidthHalf()) {
					if (y >= p.getY() - p.getHeightHalf() && y <= p.getY() + p.getHeightHalf()) {
						if (coolDownPaddle == 0) {
							if (dirY == -1) yAngle = yAngle + 90;
							if (dirY == 1) yAngle = yAngle - 90;
							lastColor = p.getColor();
						}
						coolDownPaddle++;
					}
				}else coolDownPaddle = 0;
				if (x <= p.getGoal()) {
					pong.players.getPlayers()[1].addPoint();
					pong.reset();
				}
			}
			if (isRight()) {
				p = pong.players.getPlayers()[1];
				if (x >= p.getX() - p.getWidthHalf()) {
					if (y >= p.getY() - p.getHeightHalf() && y <= p.getY() + p.getHeightHalf()) {
						if (coolDownPaddle == 0) {
							if (dirY == -1) yAngle = yAngle - 90;
							if (dirY == 1) yAngle = yAngle + 90;
							lastColor = p.getColor();
						}
						coolDownPaddle++;
					}
				} else coolDownPaddle = 0;
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
			g.setColor(lastColor);
			g.fillOval((int) x - (size / 2), (int) y - (size / 2), size, size);
			g.setColor(pong.getColors()[1]);
		}
		// g.drawLine(0, 10, pong.getWidth(), 10);
		// g.drawLine(0, pong.getHeight() - 10, pong.getWidth(), pong.getHeight() - 10);
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
