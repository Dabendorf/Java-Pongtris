package pongtris;

/**
 * Diese Klasse fasst Highscores mit Spielername, Rekordzeit und Systemzeit zusammen.
 * 
 * @author Lukas Schramm
 * @version 1.1
 * 
 */
public class Highscore implements Comparable<Highscore> {
	
	private long systemzeit;
	private long rekordzeit;
	private String name;
	
	public Highscore(long systemzeit, long rekordzeit, String name) {
		this.systemzeit = systemzeit;
		this.rekordzeit = rekordzeit;
		this.name = name;
	}

	public long getSystemzeit() {
		return systemzeit;
	}

	public long getRekordzeit() {
		return rekordzeit;
	}

	public String getName() {
		return name;
	}

	/**
	 * Diese compareTo-Methode vergleicht die Highscores.
	 * Es wird verglichen welcher Spieler weniger Zeit benoetigt hat.
	 */
	public int compareTo(Highscore o) {
		int rueckgabe = ((Long)rekordzeit).compareTo((Long)o.rekordzeit);
		return rueckgabe;
	}
}