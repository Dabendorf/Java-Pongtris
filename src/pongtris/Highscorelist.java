package pongtris;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Diese Klasse erstellt die Bestenliste und ordnet selbige in eine primitive Tabelle ein.
 * 
 * @author Lukas Schramm
 * @version 1.1
 */
public class Highscorelist {

	private JFrame frame1 = new JFrame("Highscores");
	private JTable tabelle1 = new JTable();
	private GridLayout gridlayout = new GridLayout();
	private ArrayList<Highscore> highscoreliste = new ArrayList<Highscore>();

	public Highscorelist() {
		frame1.setPreferredSize(new Dimension(600,400));
        frame1.setMinimumSize(new Dimension(300,200));
	    frame1.setResizable(true);
	    frame1.addWindowListener(new WindowAdapter() {
			@Override
            public void windowClosing(WindowEvent e) {
				new MainMenue();
            	frame1.dispose();
            }
        });
	    
	    Container cp = frame1.getContentPane();
	    cp.setLayout(gridlayout);
	    
	    highscoresladen();
	}
	
	/**
	 * Hier werden alle alten Highscores aus der Highscoreliste geladen und in die Highscoreliste integriert.
	 */
	private void highscoresladen() {
		WriteList spst = new WriteList();
		for(Highscore hsc:spst.allesLaden()) {
			highscoreliste.add(hsc);
		}
	}
	
	/**
	 * Hier wird ein neuer Highscore eingefuegt und in die Highscoreliste integriert.
	 * @param systemzeit Dies ist die Zeit zu welcher der Rekord aufgestellt wurde.
	 * @param rekordzeit So lange hat der Spieler zum Gewinnen gebraucht.
	 * @param name Dies ist der Name des Spielers.
	 */
	public void highscorehinzufuegen(long systemzeit,long rekordzeit,String name) {
		highscoreliste.add(new Highscore(systemzeit,rekordzeit,name));
	}
	
	/**
	 * Diese Methode sortiert die Highscores und ueberfuert sie in die Tabelle.
	 */
	public void sortiere() {
		Collections.sort(highscoreliste);
		Vector<Object> eintraege = new Vector<Object>();
		for(Highscore hsc:highscoreliste) {
			if(highscoreliste.indexOf(hsc)<25) {
				Vector<Object> zeile = new Vector<Object>();
				zeile.add(hsc.getName());
				double tempRekord = hsc.getRekordzeit();
				double tempRekord2 = tempRekord/1000;
				DecimalFormat deziformat = new DecimalFormat("###0.##");
				zeile.add(deziformat.format(tempRekord2)+" sek.");
				long tempSystem = hsc.getSystemzeit();
				Date date = new Date(tempSystem);
			    Format format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
				zeile.add(format.format(date));
				eintraege.add(zeile);
				new WriteList().schreiben(hsc,highscoreliste.indexOf(hsc));
			}
		}

		Vector<String> titel = new Vector<String>();
		titel.add("Name");
		titel.add("Zeit");
		titel.add("Erreicht");
		tabelle1 = new JTable(eintraege, titel);
		
		tabelle1.getColumn("Name").setPreferredWidth(47);
	    tabelle1.getColumn("Zeit").setPreferredWidth(23);
	    tabelle1.getColumn("Erreicht").setPreferredWidth(30);
	    tabelle1.getTableHeader().setBackground(Color.lightGray);
	    tabelle1.setEnabled(false);
	    
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
	    for(int x=0;x<tabelle1.getColumnCount();x++) {
	    	tabelle1.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
	    	tabelle1.getTableHeader().getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
	    }
	    
	    tabelle1.setDefaultRenderer(String.class, centerRenderer);
	    
	    frame1.pack();
	    frame1.setLocationRelativeTo(null);
	    tabelle1.setVisible(true);
	    frame1.getContentPane().add(new JScrollPane(tabelle1));
	}
	
	/**
	 * Diese Methode kann die Bestenliste anzeigen oder wieder ausblenden.
	 * @param anzeigen Boolean, ob angezeigt oder ausgeblendet wird.
	 */
	public void anzeigen(boolean anzeigen) {
		frame1.setVisible(anzeigen);
	}
}