package net.gogo98901;

import java.awt.Dimension;

import javax.swing.JFrame;

import net.gogo98901.pong.Pong;
import net.gogo98901.util.GOLog;

public class Bootstrap {
	private static JFrame frame;
	private static Pong pong;

	public static void main(String[] args) {
		GOLog.init();
		GOLog.info("Started Pong");

		frame = new JFrame();
		frame.setSize(new Dimension(700, 500));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Pong");

		pong = new Pong();

		frame.add(pong);
		checkArgs(args);

		frame.setVisible(true);
		pong.start();
	}

	public static JFrame getFrame() {
		return frame;
	}

	private static void checkArgs(String[] args) {
		if (args != null) {
			for (String arg : args) {
				if (arg.startsWith("-")) {
					arg = arg.replaceFirst("-", "");
					if (arg.equals("debug") || arg.equals("dev")) pong.debug = true;
				}
			}
		}
	}
}
