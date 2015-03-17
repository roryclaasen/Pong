package net.gogo98901;

import java.awt.Color;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import net.gogo98901.pong.Pong;
import net.gogo98901.pong.page.Start;
import net.gogo98901.util.Data;
import net.gogo98901.util.GOLog;

public class Bootstrap {
	private static JFrame frame;
	private static JLayeredPane pane;
	private static Pong pong;
	private static Start start;
	private static String[] arguments;

	private static int width = 700, height = 500;
	public static final String font = "assets/ARCADECLASSIC.TTF";

	public static void main(String[] args) {
		GOLog.init();
		GOLog.info("Program Started");
		Data.setDefultLookAndFeel();
		arguments = args;
		try {
			frame = new JFrame();
			frame.setSize(new Dimension(width, height));
			frame.setBackground(Color.BLACK);
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setIconImage(ImageIO.read(Pong.class.getClassLoader().getResourceAsStream("assets/icon.png")));
			frame.setTitle("Pong");
			pane = new JLayeredPane();
			start = new Start();
			start.setSize(new Dimension(width - 6, height - 29));
			start.init();
			pane.add(start, new Integer(0), 0);
			frame.add(pane);
			frame.setVisible(true);
		} catch (Exception e) {
			GOLog.severe(e);
			e.printStackTrace();
		}
	}

	public static void start(int players, int maxRounds) {
		if (pong != null) {
			pong.stop();
			pong = null;
			pane.remove(new Integer(1));
		}
		pong = new Pong();
		pong.setSize(new Dimension(width - 6, height - 29));
		pane.add(pong, new Integer(1), 0);

		pong.setData(players, maxRounds);
		pong.start();
		pong.setVisible(true);
		start.setVisible(false);
		checkArgs(arguments);
	}

	public static void goToStart() {
		start.setVisible(true);
		pong.setVisible(false);
		if (pong != null) {
			pong.stop();
			pong = null;
			pane.remove(new Integer(1));
		}
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
