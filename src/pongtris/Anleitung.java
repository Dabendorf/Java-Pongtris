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
 * Diese Klasse fungiert als Anleitung und zeigt dem DAU, wie das Spiel funktioniert.
 * 
 * @author Bianca, Christoph, Erik, Lukas, Tobias, Paul
 * @version 1.1
 *
 */
public class Anleitung {
    
    public Anleitung() {
    	JFrame frame = new JFrame("Anleitung");
		frame.setPreferredSize(new Dimension(700,200));
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
		
		JLabel label1 = new JLabel(" Du hast dir Pongtris zugelegt! Toll, eine gute Wahl. Das Spiel funktioniert folgendermaßen:");
		labelliste.add(label1);
        JLabel label2 = new JLabel(" Die beiden „Schläger“ werden mit [↑] und [↓] (S1) bzw. [W] und [S] (S2) nach oben und unten gesteuert.");
        labelliste.add(label2);
        JLabel label3 = new JLabel(" Wird der Spielstein nicht zurückgeschlagen, wird ein TETRIS® Stein hinter dem jeweiligen Spieler eingefügt,");
        labelliste.add(label3);
        JLabel label4 = new JLabel(" und sein Schläger bewegt sich in Richtung der Mittellinie. Hat einer der Schläger diese Mittellinie");
        labelliste.add(label4);
        JLabel label5 = new JLabel(" erreicht, hat der Spieler das Spiel verloren. Der Punktestand wird über dem Spielfeld angezeigt.");
        labelliste.add(label5);
        JLabel label6 = new JLabel(" Am Ende werden die besten Ergebnisse in die Highscore-Liste eingetragen.");
        labelliste.add(label6);
        JLabel label7 = new JLabel(" Viel Spaß beim Spielen wünscht das Team von Pongtris.");
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