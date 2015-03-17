package net.gogo98901.pong.handler;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import net.gogo98901.Bootstrap;

public class Window implements WindowListener {

	public Window() {
		Bootstrap.getFrame().addWindowListener(this);
	}

	public void windowActivated(WindowEvent e) {

	}

	public void windowClosed(WindowEvent e) {

	}

	public void windowClosing(WindowEvent e) {
		Bootstrap.goToStart();
	}

	public void windowDeactivated(WindowEvent e) {

	}

	public void windowDeiconified(WindowEvent e) {

	}

	public void windowIconified(WindowEvent e) {

	}

	public void windowOpened(WindowEvent e) {

	}
}
