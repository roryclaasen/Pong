package net.gogo98901.pong.handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.gogo98901.pong.Pong;

public class Keyboard implements KeyListener {

	private boolean[] keys = new boolean[65536];

	public boolean esc, F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12;
	public boolean no1, no2, no3, no4, no5, no6, no7, no8, no9, no0;
	public boolean q, w, e, r, t, y, u, i, o, p;
	public boolean a, s, d, f, g, h, j, k, l;
	public boolean z, x, c, v, b, n, m;
	public boolean arrowUp, arrowDown, arrowLeft, arrowRight;
	public boolean ctrl, alt, space, enter;

	public boolean up, down, right, left;

	private int pressTime = 0;

	public Keyboard(Pong pong) {
		pong.addKeyListener(this);
	}

	public void update() {
		esc = keys[KeyEvent.VK_ESCAPE];
		F1 = keys[KeyEvent.VK_F1];
		F2 = keys[KeyEvent.VK_F2];
		F3 = keys[KeyEvent.VK_F3];
		F4 = keys[KeyEvent.VK_F4];
		F5 = keys[KeyEvent.VK_F5];
		F6 = keys[KeyEvent.VK_F6];
		F7 = keys[KeyEvent.VK_F7];
		F8 = keys[KeyEvent.VK_F8];
		F9 = keys[KeyEvent.VK_F9];
		F10 = keys[KeyEvent.VK_F10];
		F11 = keys[KeyEvent.VK_F11];
		F12 = keys[KeyEvent.VK_F12];

		no0 = keys[KeyEvent.VK_0];
		no1 = keys[KeyEvent.VK_1];
		no2 = keys[KeyEvent.VK_2];
		no3 = keys[KeyEvent.VK_3];
		no4 = keys[KeyEvent.VK_4];
		no5 = keys[KeyEvent.VK_5];
		no6 = keys[KeyEvent.VK_6];
		no7 = keys[KeyEvent.VK_7];
		no8 = keys[KeyEvent.VK_8];
		no9 = keys[KeyEvent.VK_9];

		q = keys[KeyEvent.VK_Q];
		w = keys[KeyEvent.VK_W];
		e = keys[KeyEvent.VK_E];
		r = keys[KeyEvent.VK_R];
		t = keys[KeyEvent.VK_T];
		y = keys[KeyEvent.VK_Y];
		u = keys[KeyEvent.VK_U];
		i = keys[KeyEvent.VK_I];
		o = keys[KeyEvent.VK_O];
		p = keys[KeyEvent.VK_P];

		a = keys[KeyEvent.VK_A];
		s = keys[KeyEvent.VK_S];
		d = keys[KeyEvent.VK_D];
		f = keys[KeyEvent.VK_F];
		g = keys[KeyEvent.VK_G];
		h = keys[KeyEvent.VK_H];
		j = keys[KeyEvent.VK_J];
		k = keys[KeyEvent.VK_K];
		l = keys[KeyEvent.VK_L];

		z = keys[KeyEvent.VK_Z];
		x = keys[KeyEvent.VK_X];
		c = keys[KeyEvent.VK_C];
		v = keys[KeyEvent.VK_V];
		b = keys[KeyEvent.VK_B];
		n = keys[KeyEvent.VK_N];
		m = keys[KeyEvent.VK_M];

		arrowUp = keys[KeyEvent.VK_UP];
		arrowDown = keys[KeyEvent.VK_DOWN];
		arrowLeft = keys[KeyEvent.VK_LEFT];
		arrowRight = keys[KeyEvent.VK_RIGHT];

		ctrl = keys[KeyEvent.VK_CONTROL];
		alt = keys[KeyEvent.VK_ALT];
		space = keys[KeyEvent.VK_SPACE];
		enter = keys[KeyEvent.VK_ENTER];

		up = (w || arrowUp || space);
		down = (s || arrowDown);
		left = (a || arrowLeft);
		right = (d || arrowRight);
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
	}

	public boolean singlePress(boolean key) {
		boolean toReturn = false;
		if (key) {
			if (pressTime == 0) toReturn = true;
			pressTime++;
		} else {
			pressTime = 0;
			toReturn = false;
		}
		return toReturn;
	}

	public boolean setKey(int key, boolean state) {
		return keys[key] = state;
	}

	public boolean[] getkeys() {
		return keys;
	}
}
