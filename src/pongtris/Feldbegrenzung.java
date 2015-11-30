package pongtris;

import java.awt.Toolkit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Diese Klasse wurde programmiert, um bei der Eingabe von Strings die Laenge sowie die Zusammensetzung selbiger zu begrenzen.<br>
 * Diese Klasse ist aus dem Internet uebernommen und wurde leicht abgewandelt.<br>
 * Sie nimmt einen int entgegen, der die Laenge des Strings begrenzt.<br>
 * <br>
 * <b>Quelle:</b> http://javawiki.sowas.com/doku.php?id=swing:jtextfield-size
 * 
 * @author javawiki.sowas.com
 * @version 1.0
 * 
 */
public class Feldbegrenzung extends PlainDocument {
	
	private int maxlaenge;
	
	public Feldbegrenzung(int maxlaenge) {
		this.maxlaenge = maxlaenge;
	}
	
	/**
	 * Uebernimmt den String, wenn laengeokay() sagt, dass er richtig sei. Andererseits wird ein Ton wiedergegeben.
	 */
	@Override
	public void insertString(final int offset, final String text, final AttributeSet attributeSet) throws BadLocationException {
		if(laengeokay(text)) {
			super.insertString(offset, text, attributeSet);
		} else {
			Toolkit.getDefaultToolkit().beep();
		}
	}
	
	/**
	 * @param text Nimmt den Text entgegen, der eingegeben wurde.
	 * @return Gibt einen Boolean zurueck, der aufzeigt, ob die Zusammensetzung korrekt oder falsch ist.
	 */
	private boolean laengeokay(final String text) {
		if((getLength() + text.length() <= maxlaenge) && text.matches("[a-zA-ZÄÖÜäöü]*")) {
			return true;
		} else { 
			return false;
		}
	}
}