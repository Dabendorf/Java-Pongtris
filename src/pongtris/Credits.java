package pongtris;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Diese Klasse stellt in einer Uebersicht alle Mitarbeiter des Projektes vor.
 * 
 * @author Bianca, Christoph, Erik, Lukas, Tobias, Paul
 * @version 1.1
 *
 */
public class Credits {
    
    public Credits() {
    	JFrame frame = new JFrame("Credits");
		frame.setPreferredSize(new Dimension(370,200));
		frame.setResizable(false);
		frame.setLayout(new GridLayout(7,1));
		frame.addWindowListener(new WindowAdapter() {
			@Override
            public void windowClosing(WindowEvent e) {
				new MainMenue();
				frame.dispose();
            }
        });
		
		ArrayList<JLabel> labelliste = new ArrayList<JLabel>();
		
		JLabel label1 = new JLabel(" Bianca: Corporate Identitiy, Innovation, CEO of Design");
        labelliste.add(label1);
        JLabel label2 = new JLabel(" Christoph: Menüs, Credits");
        labelliste.add(label2);
        JLabel label3 = new JLabel(" Erik: CEO mit Zeitungshut");
        labelliste.add(label3);
        JLabel label4 = new JLabel(" Lukas: Arrays, Kontrollbehörde, Zentralkomitee");
        labelliste.add(label4);
        JLabel label5 = new JLabel(" Tobias: Master of Soundtrack, Inspirator");
        labelliste.add(label5);
        JLabel label6 = new JLabel(" Paul: Genie, Support");
        labelliste.add(label6);
        JLabel label7 = new JLabel(" PoliGames Limited Potsdam");
        labelliste.add(label7);
        
        for(JLabel jl:labelliste) {
        	frame.add(jl);
        	jl.setHorizontalAlignment(SwingConstants.LEFT);
        }
        
        frame.pack();
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);
    }
}