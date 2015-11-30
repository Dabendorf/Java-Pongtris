package pongtris;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Diese Klasse stellt den Spielabschluss dar und beleuchtet, welcher Spieler gewonnen hat.
 * 
 * @author Bianca, Christoph, Erik, Lukas, Tobias, Paul
 * @version 1.1
 *
 */
public class Conclusion {
    
    public Conclusion(boolean player1Won,int spieldauer) {
        if(player1Won) {
        	JOptionPane.showMessageDialog(null, "Der rote Spieler hat gewonnen!"+System.getProperty("line.separator")+"Herzlichen Glückwunsch!", "Rot siegt", JOptionPane.INFORMATION_MESSAGE);
        } else {
        	JOptionPane.showMessageDialog(null, "Der blaue Spieler hat gewonnen!"+System.getProperty("line.separator")+"Herzlichen Glückwunsch!", "Blau siegt", JOptionPane.INFORMATION_MESSAGE);
        }
        gewinneintrag(spieldauer);
    }
    
    /**
     * Diese Methode fragt im Falle eines Sieges den Spieler nach seinem Namen und fuegt ihn in die Highscoreliste ein.
     * @param spieldauer Fragt die Dauer des abgelaufenen Spiels auf.
     */
	private void gewinneintrag(int spieldauer) {
		String spielername = "";
		JTextField spielernametf = new JTextField(new Feldbegrenzung(12), "", 0);
		Object[] namensfrage = {"Wie ist der Name des Siegers?", spielernametf};
		JOptionPane pane = new JOptionPane(namensfrage, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
	    pane.createDialog(null,"Siegername").setVisible(true);
	    
	    spielername = spielernametf.getText();
	    if(spielername.equals("")) {
	    	JOptionPane.showMessageDialog(null,"Bitte gib einen Namen ein!","Kein Name eingegeben", JOptionPane.ERROR_MESSAGE);
	    	gewinneintrag(spieldauer);
	    }
	    Highscorelist hsl = new Highscorelist();
	    hsl.highscorehinzufuegen(System.currentTimeMillis(),spieldauer,spielername);
    	hsl.sortiere();
    	hsl.anzeigen(true);
	}
}