package net.gogo98901.pong.mob.player;

import net.gogo98901.pong.Pong;
import net.gogo98901.util.GOLog;

public class PlayerAI extends Player {

	private enum AI {
		follow, guess, random
	}

	private AI modeAI, modeAIlast;
	private int time, randDir;

	public PlayerAI(Pong pong, keySet set, int playerNumber) {
		super(pong, set, playerNumber);
	}

	public PlayerAI(Pong pong, keySet set) {
		this(pong, set, -1);
	}

	public void update() {
		moving = false;
		if (!pong.ball.isStill()) {
			time++;
			if (modeAI == null || time % 60 + rand.nextInt(60) == 0) changeAI();
			if (modeAI == AI.follow) follow();
			if (modeAI == AI.guess) guess();
			if (modeAI == AI.random) random();
		}
	}

	private void changeAI() {
		GOLog.info("Ai changing from " + modeAI);
		modeAIlast = modeAI;
		AI temp = AI.values()[rand.nextInt(AI.values().length)];
		while (temp == modeAIlast) {
			temp = AI.values()[rand.nextInt(AI.values().length)];
			if (temp != modeAIlast) return;
		}
		modeAI = temp;
		GOLog.info("Ai set to " + modeAI);
	}

	private void follow() {
		if (set == keySet.LEFT && pong.ball.isLeft()) {
			if (pong.ball.getY() > y) move(1);
			if (pong.ball.getY() < y) move(-1);
		}
		if (set == keySet.RIGHT && pong.ball.isRight()) {
			if (pong.ball.getY() > y) move(1);
			if (pong.ball.getY() < y) move(-1);
		}
	}

	private void guess() {
		if (time % 60 < 30) follow();
		else if (time % 60 < 30) random();
	}

	private void random() {
		if (time % 10 == 0) randDir = rand.nextInt(3) - 1;
		move(randDir);
	}

	public void hit() {
		changeAI();
	}

	public void reset() {
		y = pong.getHeight() / 2;
		modeAI = null;
		time = 0;
	}
}
