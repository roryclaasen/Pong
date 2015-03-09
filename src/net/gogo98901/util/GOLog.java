package net.gogo98901.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GOLog {
	private static final Logger log = Logger.getLogger("net.gogo98901");

	private GOLog() {
		severe("Something went wrong here!");
	}

	public static void init() {
		// java.util.logging.SimpleFormatter.format=%4$s: %5$s [%1$tc]%n
		System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s "/* [%1$tc] */+ "%n");
	}

	public static void log(Level level, Object data) {
		log.log(level, data.toString());
	}

	public static void all(Object data) {
		log.log(Level.ALL, data.toString());
	}

	public static void config(Object data) {
		log.log(Level.CONFIG, data.toString());
	}

	public static void fine(Object data) {
		log.log(Level.FINE, data.toString());
	}

	public static void finer(Object data) {
		log.log(Level.FINER, data.toString());
	}

	public static void finest(Object data) {
		log.log(Level.FINEST, data.toString());
	}

	public static void info(Object data) {
		log.log(Level.INFO, data.toString());
	}

	public static void off(Object data) {
		log.log(Level.OFF, data.toString());
	}

	public static void warn(Object data) {
		log.log(Level.WARNING, data.toString());
	}

	public static void severe(Object data) {
		log.log(Level.SEVERE, data.toString());
	}
}
