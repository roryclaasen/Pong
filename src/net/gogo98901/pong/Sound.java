package net.gogo98901.pong;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import net.gogo98901.util.GOLog;

public class Sound {
	private String impact1 = "assets/impact1.wav";
	private String impact2 = "assets/impact2.wav";

	private String back1 = "assets/backloop_intro.wav";
	private String back2 = "assets/backloop_main.wav";
	@SuppressWarnings("unused")
	private String win = "assets/winscreen.wav";

	private AudioInputStream in1, in2;
	private AudioInputStream intro, main;

	private Clip clip1, clip2, background1, background2;

	private boolean success = false, backgroundQ = false;

	private int lastplayed;

	private float gain = -5F;

	public Sound() {
		try {
			in1 = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource(impact1));
			in2 = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource(impact2));
			intro = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource(back1));
			main = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource(back2));
			clip1 = AudioSystem.getClip();
			clip2 = AudioSystem.getClip();
			background1 = AudioSystem.getClip();
			background2 = AudioSystem.getClip();
			success = true;

			playBackground();
		} catch (Exception e) {
			GOLog.severe(e);
			e.printStackTrace();
		}
	}

	public void playBackground() {
		try {
			background1.open(intro);
			background1.start();
		} catch (Exception e) {
			GOLog.severe("Background: " + e);
		}
	}

	public void loopBackground() {
		background1 = null;
		try {
			main = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource(back2));
			background2 = AudioSystem.getClip();
			background2.open(main);
			background2.loop(-1);
		} catch (Exception e) {
			GOLog.severe("Background: " + e);
		}
	}

	private void quietBackground() {
		if (!backgroundQ) {
			backgroundQ = true;
			try {
				if (background1 != null) if (background1.isOpen()) {
					FloatControl gainControl = (FloatControl) background1.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(gain);
				}

				if (background2.isOpen()) {
					FloatControl gainControl = (FloatControl) background2.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(gain);
				}
			} catch (Exception e) {
				e.printStackTrace();
				GOLog.severe("Background: " + e);
			}
		}
	}

	public void update() {
		if (background1 != null) {
			if (!background1.isRunning()) loopBackground();
		}
	}

	public void play(final int id) {
		if (success) {
			if (id == 1 || id == 2) {
				try {
					quietBackground();
					reset(id);
					if (id == 1) {
						clip1.open(in1);
						clip1.stop();
						clip1.setFramePosition(0);
						clip1.start();
					}
					if (id == 2) {
						clip2.open(in2);
						clip1.stop();
						clip2.setFramePosition(0);
						clip2.start();
					}
					lastplayed = id;
				} catch (Exception e) {
					GOLog.severe(e);
				}
			} else GOLog.severe("Invlaid sound id");
		} else GOLog.severe("System failed");
	}

	public void reset(int id) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		if (id == 1) {
			in1 = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource(impact1));
			clip1 = AudioSystem.getClip();
		}
		if (id == 2) {
			in2 = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource(impact2));
			clip2 = AudioSystem.getClip();
		}
	}

	public void playOther() {
		if (lastplayed == 1) {
			play(2);
			return;
		}
		if (lastplayed == 2) {
			play(1);
			return;
		}
		if (lastplayed == 0) {
			play(1);
			return;
		}

	}
}