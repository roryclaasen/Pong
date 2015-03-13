package net.gogo98901.pong;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.io.InputStream;

import net.gogo98901.Bootstrap;
import net.gogo98901.pong.handler.Handler;
import net.gogo98901.pong.mob.Ball;
import net.gogo98901.pong.mob.player.Players;
import net.gogo98901.util.Data;
import net.gogo98901.util.GOLog;

public class Pong extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private boolean running = false;
	public boolean debug = false;

	private Thread _t;
	private Pong pong;

	public Handler handler;
	public Players players;
	public Ball ball;
	public Font font;
	public Sound sound;

	public int playerNo, maxRounds;
	private int mode = 0;
	private int width, height;

	private int fps, ups;

	public Pong() {
		pong = this;
		width = (int) Math.ceil(Bootstrap.getFrame().getSize().getWidth());
		height = (int) Math.ceil(Bootstrap.getFrame().getSize().getHeight());
		try {
			InputStream fontData = Pong.class.getClassLoader().getResourceAsStream(Bootstrap.font);
			font = Font.createFont(Font.TRUETYPE_FONT, fontData).deriveFont(Font.PLAIN, 24);
		} catch (FontFormatException | IOException e) {
			GOLog.warn("Font not loaded");
			e.printStackTrace();
		}
	}

	private void init() {
		handler = new Handler(pong);
		players = new Players(pong);
		ball = new Ball(pong);
		sound = new Sound();
		GOLog.info("Initialized Pong");
	}

	public void setData(int players, int maxRounds) {
		this.playerNo = players;
		this.maxRounds = maxRounds;
	}

	public void start() {
		init();
		GOLog.info("Starting Thread");
		requestFocus();
		requestFocusInWindow();

		running = true;
		_t = new Thread(this, "Pong_display");
		_t.start();
	}

	public void stop() {
		GOLog.warn("Stoping Thread");
		running = false;
		try {
			_t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0.0;
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta -= 1;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				ups = updates;
				fps = frames;
				timer += 1000L;
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}

	public void reset() {
		ball.reset();
		players.reset();
	}

	private void update() {
		handler.update();
		players.update();
		ball.update();
		sound.update();
		if (players.getScoreTotal() >= maxRounds) {
			gameOver();
			reset();
			if (handler.keyboard.esc) Bootstrap.goToStart();
		} else {
			if (handler.keyboard.esc) reset();
			if (handler.keyboard.space) {
				reset();
				ball.start();
			}
		}
	}

	public void render() {
		try {
			BufferStrategy bs = getBufferStrategy();
			if (bs == null) {
				createBufferStrategy(3);
				return;
			}
			Graphics g = bs.getDrawGraphics();
			g.setColor(getColors()[0]);
			g.fillRect(0, 0, width, height);

			g.setColor(getColors()[1]);
			g.setFont(font.deriveFont(20F));
			if (debug) {
				g.drawString("fps " + fps, 2, 15);
				g.drawString("ups " + ups, 2, 30);
				g.drawString("sob " + (int) ball.getRallySpeed(), 2, 45);
			}
			if (ball.isStill()) {
				players.renderScores(g);
				Data.centerText(0, 0, width, 50, "Pong", g, font.deriveFont(75F));
				if (players.getScoreTotal() < maxRounds) Data.centerText(0, 0, pong.getWidth(), pong.getHeight(), "Press  space  to  start", g, pong.font.deriveFont(45F));
				else  Data.centerText(0, 0, pong.getWidth(), pong.getHeight(), "Press esc to go to menu", g, pong.font.deriveFont(45F));

				
				Data.centerText(0, pong.getHeight() - 50, pong.getWidth(), 25, "Game  by  Rory  Claasen", g, pong.font.deriveFont(45F));
				Data.centerText(0, pong.getHeight() - 25, pong.getWidth(), 25, "Music  by  Kevin  Macleod", g, pong.font.deriveFont(35F));
			} else {
				if (players.getPlayers().length == 2) {
					for (int i = 0; i < getHeight(); i++) {
						if (i % 2 == 0) g.drawLine(getWidth() / 2, i * 10, getWidth() / 2, i * 10 + 10);
					}
				}
			}

			players.render(g);
			ball.render(g);

			g.dispose();
			bs.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Color[] getColors() {
		Color[] c = new Color[5];
		if (mode == 0) {
			c[0] = Color.BLACK;
			c[1] = Color.WHITE;
			c[2] = Color.RED;
			c[3] = Color.BLUE;
			c[4] = Color.CYAN;
		}
		return c;
	}

	private void gameOver() {
		//TODO winner music
	}

	public static void close() {
		GOLog.off("Shutting down Pong");
		System.exit(0);
	}
}
