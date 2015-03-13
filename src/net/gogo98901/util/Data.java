package net.gogo98901.util;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Data {
	private Data() {
		GOLog.severe("Something went wrong here!");
	}

	public static Rectangle2D getStringBounds(String t, Graphics g, Font f) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(f);
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D r = fm.getStringBounds(t, g2d);
		return r;
	}

	public static FontMetrics getFontMatrics(String t, Graphics g, Font f) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(f);
		FontMetrics fm = g2d.getFontMetrics();
		return fm;
	}

	public static void centerText(int x, int y, int width, int height, String t, Graphics g, Font f) {
		g.setFont(f);
		Rectangle2D r = getStringBounds(t, g, f);
		FontMetrics fm = getFontMatrics(t, g, f);
		int posX = x + (width - (int) r.getWidth()) / 2;
		int posY = y + (height - (int) r.getHeight()) / 2 + fm.getAscent();
		g.drawString(t, posX, posY);
	}

	public static void setDefultLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			GOLog.severe(e);
		}
	}

	public static boolean isNumeric(String str) {
		try {
			@SuppressWarnings("unused")
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
