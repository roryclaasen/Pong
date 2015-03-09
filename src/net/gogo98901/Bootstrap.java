package net.gogo98901;

import java.awt.Dimension;

import javax.swing.JFrame;

import net.gogo98901.pong.Pong;

public class Bootstrap {
	private static JFrame frame;
	private static Pong pong;

	public static void main(String[] arg) {
		frame = new JFrame();
		frame.setSize(new Dimension(700, 500));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Pong");
		pong = new Pong();
		frame.add(pong);
		frame.setVisible(true);
		pong.start();
	}

	public static JFrame getFrame() {
		return frame;
	}
}