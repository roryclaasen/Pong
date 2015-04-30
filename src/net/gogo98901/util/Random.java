package net.gogo98901.util;

public class Random {
	private static java.util.Random random = new java.util.Random();

	private Random() {

	}

	/**
	 * This Class is here as my friend told me to add it
	 * The Functions can be related to python
	 */
	public static int randint(int i1, int i2) {
		return random.nextInt(i2 - i1) + i1;
	}
}
