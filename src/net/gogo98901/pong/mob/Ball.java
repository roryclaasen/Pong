package net.gogo98901.pong.mob;

import java.awt.Color;
import java.awt.Graphics;

import net.gogo98901.pong.Pong;
import net.gogo98901.pong.mob.player.Player;

public class Ball extends Mob {

	@SuppressWarnings("unused")
	private int time;

	private enum mode {
		STILL, FLYING
	}

	private mode currentMode;
	private Color lastColor = Color.WHITE;

	@SuppressWarnings("unused")
	private int dirX, dirY, randomAngle = 20;
	private boolean doAngle = false;
	private int startSize = 19, size = startSize;

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
			/*if (yAngle < 45 || yAngle > 360 - 45) {
				if (rand.nextInt(2) == 0) yAngle -= 60;
				else yAngle += 60;
			}
			if (yAngle > 180 - 45 && yAngle < 180 + 45) {
				if (rand.nextInt(2) == 0) yAngle -= 60;
				else yAngle += 60;
			}*/
		}
	}

	public void update() {
		if (currentMode == mode.FLYING) {
			time++;
			speed = getRallySpeed();
			/*if (time % 30 == 0) {
				if (size != 2000) size++;
				if (speed != 100) speed++;
			}*/
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
					yAngle = 360 - yAngle;
					if (dirX == -1 && doAngle) yAngle -= rand.nextInt(randomAngle);
					if (dirX == 1 && doAngle) yAngle += rand.nextInt(randomAngle);
					pong.sound.playOther();
				}
				coolDownTop++;
			} else coolDownTop = 0;
			if (y + (getSize() / 2) >= pong.getHeight()) {
				if (coolDownDown == 0) {
					yAngle = 360 - yAngle;
					if (dirX == -1 && doAngle) yAngle += rand.nextInt(randomAngle);
					if (dirX == 1 && doAngle) yAngle -= rand.nextInt(randomAngle);
					pong.sound.playOther();
				}
				coolDownDown++;
			} else coolDownDown = 0;
			Player player;
			if (isLeft()) {
				player = pong.players.getPlayers()[0];
				if (x - (getHalfSize() / 2) <= player.getX() + player.getWidth()) {
					if (y + getHalfSize() >= player.getY() - player.getHeightHalf() && y - getHalfSize() <= player.getY() + player.getHeightHalf()) {
						if (coolDownPaddle == 0) {
							yAngle = 180 - yAngle;
							//if (player.isGoingDown()) yAngle -= 15 + rand.nextInt(5);
							//if (player.isGoingUp()) yAngle += 15 + rand.nextInt(5);
							hit(player);
						}
						coolDownPaddle++;
					}
				} else coolDownPaddle = 0;
				if (x <= player.getGoal()) {
					pong.players.getPlayers()[1].addPoint();
					pong.reset();
				}
			}
			if (isRight()) {
				player = pong.players.getPlayers()[1];
				if (x + (getHalfSize() / 2) >= player.getX() - player.getWidth()) {
					if (y + getHalfSize() >= player.getY() - player.getHeightHalf() && y - getHalfSize() <= player.getY() + player.getHeightHalf()) {
						if (coolDownPaddle == 0) {
							yAngle = 180 - yAngle;
							//if (player.isGoingDown()) yAngle += 15 + rand.nextInt(5);
							//if (player.isGoingUp()) yAngle -= 15 + rand.nextInt(5);
							hit(player);
						}
						coolDownPaddle++;
					}
				} else coolDownPaddle = 0;
				if (x >= player.getGoal()) {
					pong.players.getPlayers()[0].addPoint();
					reset();
				}
			}
		}
	}

	public void hit(Player player) {
		lastColor = player.getColor();
		player.hit();
		rally++;
		pong.sound.playOther();
		/*
		 * if (rand.nextInt(2) == 0) { int ran = rand.nextInt(3) - 1; if (ran == -1) yAngle -= rand.nextInt(10); if (ran == 1) yAngle += rand.nextInt(10); }
		 */
	}

	public double getRallySpeed() {
		if (startSpeed + (rally / 2) < 20) return startSpeed + (rally / 2);
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
			time = 0;
			currentMode = mode.STILL;
			x = pong.getWidth() / 2;
			y = pong.getHeight() / 2;
			rally = 0;
			lastColor = Color.WHITE;
			speed = startSpeed;
			size = startSize - (pong.players.getScoreTotal() / 2);
			if (size < 13) size = 13;
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
