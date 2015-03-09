package net.gogo98901.pong.handler;

import net.gogo98901.pong.Pong;

public class Handler {
	public Keyboard keyboard;
	public Window window;

	public Handler(Pong pong) {
		keyboard = new Keyboard(pong);
		window = new Window();
	}

	public void update() {
		keyboard.update();
	}
}
