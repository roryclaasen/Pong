package net.gogo98901.pong.page;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.gogo98901.Bootstrap;
import net.gogo98901.pong.Pong;
import net.gogo98901.pong.util.JFormattedTextField;
import net.gogo98901.util.GOLog;

public class Start extends JPanel {
	private static final long serialVersionUID = 1L;

	public Start() {
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setFont();
		setLayout(null);
	}

	public void init() {
		final JLabel title = new JLabel("Pong", SwingConstants.CENTER);
		title.setFont(getFont().deriveFont(100F));
		title.setForeground(getForeground());
		title.setBounds(0, 50, getWidth(), 50);
		add(title);

		final JLabel gameMode = new JLabel("Game Mode");
		gameMode.setFont(getFont().deriveFont(50F));
		gameMode.setForeground(getForeground());
		gameMode.setBounds(95, getHeight() / 2 - 100, 300, 20);
		add(gameMode);

		final ButtonGroup button = new ButtonGroup();
		final JRadioButton playerTwo = new JRadioButton();
		playerTwo.setBackground(getBackground());
		playerTwo.setBounds(100, getHeight() / 2 - 75, 20, 20);
		button.add(playerTwo);
		add(playerTwo);

		final JLabel playerTwotext = new JLabel("Player vs Player");
		playerTwotext.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent event) {
			}

			public void mousePressed(MouseEvent event) {
			}

			public void mouseExited(MouseEvent event) {
			}

			public void mouseEntered(MouseEvent event) {
			}

			public void mouseClicked(MouseEvent event) {
				playerTwo.setSelected(true);
			}
		});
		playerTwotext.setFont(getFont().deriveFont(25F));
		playerTwotext.setForeground(getForeground());
		playerTwotext.setBounds(playerTwo.getX() + 25, playerTwo.getY(), getWidth() - (playerTwo.getX() + 25), playerTwo.getHeight());
		add(playerTwotext);

		final JRadioButton playerAI = new JRadioButton();
		playerAI.setBackground(getBackground());
		playerAI.setBounds(100, playerTwo.getY() + 25, 20, 20);
		button.add(playerAI);
		add(playerAI);
		JLabel playerAItext = new JLabel("Player vs Computer");
		playerAItext.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent event) {
			}

			public void mousePressed(MouseEvent event) {
			}

			public void mouseExited(MouseEvent event) {
			}

			public void mouseEntered(MouseEvent event) {
			}

			public void mouseClicked(MouseEvent event) {
				playerAI.setSelected(true);
			}
		});
		playerAItext.setFont(getFont().deriveFont(25F));
		playerAItext.setForeground(getForeground());
		playerAItext.setBounds(playerAI.getX() + 25, playerAI.getY(), getWidth() - (playerAI.getX() + 25), playerAI.getHeight());
		add(playerAItext);

		final JLabel gameOption = new JLabel("Options");
		gameOption.setFont(getFont().deriveFont(50F));
		gameOption.setForeground(getForeground());
		gameOption.setBounds(95, getHeight() / 2, 300, 20);
		add(gameOption);

		final JTextField maxRounds = new JTextField();
		maxRounds.setDocument(new JFormattedTextField(2, true));
		maxRounds.setFont(getFont().deriveFont(20F));
		maxRounds.setBounds(gameOption.getX() + 5, gameOption.getY() + 25, 30, 25);
		maxRounds.setText("10");
		add(maxRounds);

		final JLabel maxRoundsText = new JLabel("max rounds");
		maxRoundsText.setFont(getFont().deriveFont(25F));
		maxRoundsText.setForeground(getForeground());
		maxRoundsText.setBounds(maxRounds.getX() + maxRounds.getWidth() + 5, maxRounds.getY(), getWidth() - (maxRounds.getX() + 25), maxRounds.getHeight());
		add(maxRoundsText);

		final JButton start = new JButton("START");
		start.setBackground(getBackground());
		start.setFont(getFont().deriveFont(50F));
		start.setBounds(getWidth() / 4, getHeight() - 100, getWidth() / 2, 50);
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (playerAI.isSelected() || playerTwo.isSelected()) {
					if (maxRounds.getText().length() != 0) {
						if (maxRounds.getText().equals("0") || maxRounds.getText().equals("00")) {
							JOptionPane.showMessageDialog(null, "You need to enter a maximum number of rounds", "Pong", JOptionPane.ERROR_MESSAGE);
							return;
						}
						int rounds = Integer.parseInt(maxRounds.getText());
						int players = 0;
						if (playerAI.isSelected()) players = 1;
						if (playerTwo.isSelected()) players = 2;
						Bootstrap.start(players, rounds);
					}else JOptionPane.showMessageDialog(null, "You need to enter a maximum number of rounds", "Pong", JOptionPane.ERROR_MESSAGE);
				} else JOptionPane.showMessageDialog(null, "You need to select a game mode", "Pong", JOptionPane.ERROR_MESSAGE);
			}
		});
		add(start);
	}

	private void setFont() {
		try {
			InputStream fontData = Pong.class.getClassLoader().getResourceAsStream(Bootstrap.font);
			setFont(Font.createFont(Font.TRUETYPE_FONT, fontData).deriveFont(Font.PLAIN, 12));
		} catch (FontFormatException | IOException e) {
			GOLog.warn("Font not loaded");
			e.printStackTrace();
		}
	}
}
