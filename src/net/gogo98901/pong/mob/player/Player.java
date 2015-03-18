package net.gogo98901.pong.mob.player;

import java.awt.Color;
import java.awt.Graphics;

import net.gogo98901.pong.Pong;
import net.gogo98901.pong.handler.Keyboard;
import net.gogo98901.pong.mob.Mob;
import net.gogo98901.util.Data;
import net.gogo98901.util.GOLog;

public class Player extends Mob {

	public static final int DEFULTLEVEL = 12;
	protected final int bound = 100, speed = 5;
	protected int level = DEFULTLEVEL;
	protected int width = 10;
	protected int height = width * level;

	protected int dir, playerID, score;

	protected boolean moving = false;

	public enum keySet {
		RIGHT, LEFT, ONLINE
	}

	private Keyboard key;
	protected keySet set;

	public Player(Pong pong, keySet set, int playerNumber) {
		super(pong);
		key = pong.handler.keyboard;
		if (set == null) {
			GOLog.severe("keySet not set");
			return;
		}
		this.set = set;
		playerID = playerNumber;
		y = pong.getHeight() / 2;
		if (set == keySet.LEFT) x = bound;
		if (set == keySet.RIGHT) x = pong.getWidth() - bound;
	}

	public Player(Pong pong, keySet set) {
		this(pong, set, -1);
	}

	public void update() {
		moving = false;
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

	public void move(int dir) {
		moving = true;
		this.dir = dir;
		if (dir == -1) if ((y - (height / 2)) - speed > 1) y -= speed;
		if (dir == 1) if ((y + (height / 2)) + speed < pong.getHeight() - 1) y += speed;
	}

	public void render(Graphics g) {
		g.setColor(getColor());
		g.fillRect((int) x - (width / 2), (int) y - (height / 2), width, height);
		g.drawLine(getGoal(), 0, getGoal(), pong.getHeight());
		g.setColor(pong.getColors()[1]);
	}

	public void reset() {
		y = pong.getHeight() / 2;
	}

	public void renderScore(Graphics g) {
		if (set == keySet.LEFT) Data.centerText(0, 0, pong.getWidth() / 2, 50, "" + score, g, pong.font.deriveFont(80F));
		if (set == keySet.RIGHT) Data.centerText(pong.getWidth() / 2, 0, pong.getWidth() / 2, 50, "" + score, g, pong.font.deriveFont(80F));
	}

	public void setLevel(int level) {
		this.level = level;
		this.height = this.width * level;
	}

	public boolean isMoving() {
		return moving;
	}

	public boolean isGoingDown() {
		if (!isMoving()) return false;
		return (dir == -1);
	}

	public boolean isGoingUp() {
		if (!isMoving()) return false;
		return (dir == 1);
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
		if (set == keySet.LEFT) return (int) x - width;
		if (set == keySet.RIGHT) return (int) x + width;
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

	public Color getColor() {
		Color color = Color.WHITE;
		if (playerID != -1) {
			Color col = pong.getColors()[2 + playerID];
			if (col != null) color = col;
		}
		return color;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void hit() {
		// Do Something!
	}
}
