package net.gogo98901.pong.mob;

import java.awt.Color;
import java.awt.Graphics;

import net.gogo98901.pong.Pong;
import net.gogo98901.pong.mob.player.Player;

public class Ball extends Mob {

	private enum mode {
		STILL, FLYING
	}

	private mode currentMode;
	private Color lastColor = Color.WHITE;

	private int dirX, dirY;
	private int size = 19;

	private int yAngle;
	private double startSpeed = 4, speed = startSpeed;
	private int rally;

	private int coolDownTop, coolDownDown, coolDownPaddle;

	public Ball(Pong pong) {
		super(pong);
		reset();
	}

	public void start() {
		if (currentMode == mode.STILL) {
			reset();
			currentMode = mode.FLYING;
			yAngle = rand.nextInt(360);
		}
	}

	public void update() {
		if (currentMode == mode.FLYING) {
			speed = getRallySpeed();
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
					if (dirX == -1) yAngle = yAngle - 90;
					if (dirX == 1) yAngle = yAngle + 90;
					pong.sound.playOther();
				}
				coolDownTop++;
			} else coolDownTop = 0;
			if (y + (getSize() / 2) >= pong.getHeight()) {
				if (coolDownDown == 0) {
					if (dirX == -1) yAngle = yAngle + 90;
					if (dirX == 1) yAngle = yAngle - 90;
					pong.sound.playOther();
				}
				coolDownDown++;
			} else coolDownDown = 0;
			Player p;
			if (isLeft()) {
				p = pong.players.getPlayers()[0];
				if (x - (getHalfSize() / 2) <= p.getX() + p.getWidth()) {
					if (y + getHalfSize() >= p.getY() - p.getHeightHalf() && y - getHalfSize() <= p.getY() + p.getHeightHalf()) {
						if (coolDownPaddle == 0) {
							if (dirY == -1) yAngle = yAngle + 90;
							if (dirY == 1) yAngle = yAngle - 90;
							if (p.isGoingDown()) yAngle -= 10;
							if (p.isGoingUp()) yAngle += 10;
							lastColor = p.getColor();
							p.hit();
							rally++;
							pong.sound.playOther();
						}
						coolDownPaddle++;
					}
				} else coolDownPaddle = 0;
				if (x <= p.getGoal()) {
					pong.players.getPlayers()[1].addPoint();
					pong.reset();
				}
			}
			if (isRight()) {
				p = pong.players.getPlayers()[1];
				if (x + (getHalfSize() / 2) >= p.getX() - p.getWidth()) {
					if (y + getHalfSize() >= p.getY() - p.getHeightHalf() && y - getHalfSize() <= p.getY() + p.getHeightHalf()) {
						if (coolDownPaddle == 0) {
							if (dirY == -1) yAngle = yAngle - 90;
							if (dirY == 1) yAngle = yAngle + 90;
							if (p.isGoingDown()) yAngle += 10;
							if (p.isGoingUp()) yAngle -= 10;
							lastColor = p.getColor();
							p.hit();
							rally++;
							pong.sound.playOther();
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

	public double getRallySpeed() {
		if (startSpeed + (rally / 2) < 10) return startSpeed + (rally / 2);
		else return 20;
	}

	public double getStartSpeed() {
		return startSpeed;
	}

	public void render(Graphics g) {
		if (currentMode == mode.FLYING) {
			g.setColor(lastColor);
			g.fillOval((int) x - (size / 2), (int) y - (size / 2), size, size);
			g.setColor(pong.getColors()[1]);
		}
	}

	public int getSize() {
		return size;
	}

	public int getHalfSize() {
		return size / 2;
	}

	public void reset() {
		if (!isStill()) {
			currentMode = mode.STILL;
			x = pong.getWidth() / 2;
			y = pong.getHeight() / 2;
			rally = 0;
			lastColor = Color.WHITE;
			speed = startSpeed;
		}
	}

	public boolean isLeft() {
		return (x < (pong.getWidth() / 2));
	}

	public boolean isRight() {
		return (x > (pong.getWidth() / 2));
	}

	public boolean isStill() {
		if (currentMode == mode.STILL) return true;
		return false;
	}
}
