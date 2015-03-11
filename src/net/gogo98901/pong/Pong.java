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

	private Thread _t;
	private Pong pong;

	public Handler handler;
	public Players players;
	public Ball ball;
	public Font font;

	private int mode = 0;
	private int width, height;

	private int fps, ups;

	public Pong() {
		pong = this;
		width = (int) Math.ceil(Bootstrap.getFrame().getSize().getWidth());
		height = (int) Math.ceil(Bootstrap.getFrame().getSize().getHeight());
	}

	private void init() {
		try {
			InputStream fontData = Pong.class.getClassLoader().getResourceAsStream("assets/8-BIT WONDER.TTF");
			font = Font.createFont(Font.TRUETYPE_FONT, fontData).deriveFont(Font.PLAIN, 12);
		} catch (FontFormatException | IOException e) {
			GOLog.warn("Font not loaded");
			e.printStackTrace();
		}

		handler = new Handler(pong);
		players = new Players(pong);
		ball = new Ball(pong);
		GOLog.info("Initialized Pong");
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
		if (handler.keyboard.esc) reset();
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

			g.setFont(font);
			g.drawString("fps " + fps, 2, 15);
			g.drawString("ups " + ups, 2, 30);

			if (ball.isStill()) {
				players.renderScores(g);
				Data.centerText(0, 0, width, 50, "Pong", g, font.deriveFont(50F));
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
		Color[] c = new Color[2];
		if (mode == 0) {
			c[0] = Color.BLACK;
			c[1] = Color.WHITE;
		}
		return c;
	}

	public static void close() {
		GOLog.off("Shutting down Pong");
		System.exit(0);
	}
}
