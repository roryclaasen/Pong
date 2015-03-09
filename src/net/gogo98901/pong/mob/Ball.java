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

	private int dir, yAngle;
	private int speed = 2;

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
				dir = rand.nextInt(2);
				yAngle = rand.nextInt(360);
				if (yAngle > 90 - 20 && yAngle < 90 + 20) yAngle += rand.nextInt(90);
				if (yAngle > 270 - 20 && yAngle < 270 + 20) yAngle += rand.nextInt(90);
			}
		}
		if (currentMode == mode.FLYING) {
			if (dir == 0) x -= speed;
			if (dir == 1) x += speed;
			if (y <= 10 && dir == 0) yAngle = yAngle + 360;
			if (y <= 10 && dir == 1) yAngle = yAngle - 540;
			if (y >= pong.getHeight() - 10) yAngle = yAngle - 180;
			double dy = speed * Math.sin(yAngle);
			GOLog.info("a:" + yAngle + ", y:" + y);
			y += (int) Math.floor(dy);
			Player p;
			if (isLeft()) {
				p = pong.players.getPlayers()[0];
				if (x <= p.getX() + p.getWidth()) {
					if (y >= p.getY() - p.getHeightHalf() && y <= p.getY() + p.getHeightHalf()) {
						if (dir == 1) dir = 0;
						else dir = 1;
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
					if (y >= p.getY() - p.getHeightHalf() && y <= p.getY() + p.getHeightHalf()) {
						if (dir == 1) dir = 0;
						else dir = 1;
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
			g.fillOval(x - (size / 2), y - (size / 2), size, size);
		}
		// g.drawLine(0, 10, pong.getWidth(), 10);
		// g.drawLine(0, pong.getHeight() - 10, pong.getWidth(), pong.getHeight() - 10);
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

	public void reset() {
		if (!isStill()) {
			GOLog.info("Ball Stoped");
			currentMode = mode.START;
			x = pong.getWidth() / 2;
			y = pong.getHeight() / 2;
			dir = 0;
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
