package net.gogo98901.pong;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import net.gogo98901.Bootstrap;
import net.gogo98901.pong.player.Players;

public class Pong extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private boolean running = false;
	private Thread _t;

	private int width, height;
	private Players players;

	private int fps, ups;

	public Pong() {
		width = (int) Math.ceil(Bootstrap.getFrame().getSize().getWidth());
		height = (int) Math.ceil(Bootstrap.getFrame().getSize().getHeight());
	}

	private void init() {
		players = new Players();
	}

	public void start() {
		running = true;
		_t = new Thread(this, "Pong_display");
		_t.start();
	}

	public void stop() {
		running = false;
		try {
			_t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		init();
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

	private void update() {
		players.update();
	}

	public void render() {
		try {
			BufferStrategy bs = getBufferStrategy();
			if (bs == null) {
				createBufferStrategy(3);
				return;
			}
			Graphics g = bs.getDrawGraphics();
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, width, height);
			g.setColor(Color.WHITE);
			g.drawString("fps: " + fps, 2, 10);
			g.drawString("ups: " + ups, 2, 21);
			players.render(g);
			g.dispose();
			bs.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
