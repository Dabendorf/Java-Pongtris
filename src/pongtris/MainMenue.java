package pongtris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Diese Klasse beeinhaltet die Main-Methode des Spiels. Sie startet den Startbildschirm und zeigt das Spielmenue an.
 * 
 * @author Bianca, Christoph, Erik, Lukas, Tobias, Paul
 * @version 1.1
 *
 */
public class MainMenue implements ActionListener {
	
	private JFrame frame1 = new JFrame("Menü");
	private JButton start = new JButton("Start");
	private JButton anleitung = new JButton("Anleitung");
	private JButton credits = new JButton("Credits");
	private JButton highscores = new JButton("Highscores");
	private JButton ende = new JButton("Beenden");
    
    public static void main(String aegs[]) {
    	new StartWindow();
        new MainMenue();
    }
    
    public MainMenue() {
    	frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setPreferredSize(new Dimension(400,100));
		frame1.setResizable(false);
        frame1.setLayout(new GridLayout(1,2));
        
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.YELLOW);
        
        start.addActionListener(this);
        anleitung.addActionListener(this);
        credits.addActionListener(this);
        highscores.addActionListener(this);
        ende.addActionListener(this);
        panel1.add(start);
        panel1.add(anleitung);
        panel1.add(credits);
        panel1.add(highscores);
        panel1.add(ende);
        frame1.add(panel1);
        
        frame1.pack();
        frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
    } 

    /**
     * Diese Methode liest ein, welchen Button der Spieler angeklickt hat.
     */
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == this.highscores) {
        	Highscorelist hsl = new Highscorelist();
        	frame1.dispose();
        	hsl.sortiere();
        	hsl.anzeigen(true);
        } else if(ae.getSource() == this.credits) {
            new Credits();
            frame1.dispose();
        } else if (ae.getSource() == this.start) {
            new Pongtris();
            frame1.dispose();
        } else if (ae.getSource() == this.anleitung) {
            new Anleitung();
            frame1.dispose();
        } else if (ae.getSource() == this.ende) {
            System.exit(0);
        }
    }
}