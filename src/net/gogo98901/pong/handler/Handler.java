package net.gogo98901.pong.handler;

import net.gogo98901.pong.Pong;

public class Handler {
	public static Window window;
	public Keyboard keyboard;

	public Handler(Pong pong) {
		keyboard = new Keyboard(pong);
	}
	
	public static void addWindow(){
		window = new Window();
	}

	public void update() {
		keyboard.update();
	}
}
