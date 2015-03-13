package net.gogo98901.pong.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import net.gogo98901.util.Data;

public class JFormattedTextField extends PlainDocument {
	private static final long serialVersionUID = 1L;

	private boolean numbersOnly = false;
	private int maxNumber = -1;

	public JFormattedTextField() {
		this(Integer.MAX_VALUE, false);
	}

	public JFormattedTextField(int maxNumber) {
		this(maxNumber, false);
	}

	public JFormattedTextField(boolean numbersOnly) {
		this(Integer.MAX_VALUE, numbersOnly);
	}

	public JFormattedTextField(int maxNumber, boolean numbersOnly) {
		this.maxNumber = maxNumber;
		this.numbersOnly = numbersOnly;
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null) return;
		if (numbersOnly) if (!Data.isNumeric(str)) return;

		if ((getLength() + str.length()) <= maxNumber) {
			super.insertString(offset, str, attr);
		}
	}
}
