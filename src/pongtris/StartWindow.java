package pongtris;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Diese Klasse stellt zum Start des Spiels ein kurzes Ladefenster dar, welches das Titelbild des Spiels anzeigt.
 * 
 * @author Bianca, Christoph, Erik, Lukas, Tobias, Paul
 * @version 1.1
 *
 */
public class StartWindow {
	
	private JFrame frame = new JFrame("Pongtris");
    private BufferedImage bi;
    private JPanel draufmalen = new JPanel() {
    	@Override
    	protected void paintComponent(Graphics gr) {
    		super.paintComponent(gr);
    		if(bi!=null) {
    	    	gr.drawImage(bi, 0, 0, getWidth(), getHeight(), null);
    	    }
    	}
    };
    
    public StartWindow() {
    	anzeigenBild();
        try {
            Thread.sleep(2000);
        } catch(Exception e) {}
        frame.setVisible(false);
    }
	
    /**
     * Diese Methode laedt den Frame und stellt das Bild darauf dar.
     */
	private void anzeigenBild() {
        frame = new JFrame("Anleitung");
        frame.setSize(new Dimension(491,492));
        Container cp = frame.getContentPane();
		cp.setLayout(new GridLayout(1,1));
        try {
        	if(bi==null) {
        		URL url = new URL(BaseURL.getJarBase(StartWindow.class),"./starticon.png");
        		bi = ImageIO.read(url);
        	}
        	frame.add(draufmalen);

        } catch(Exception ex) {
            JLabel ersatzLabel = new JLabel("PONGTRIS STARTUP");
            JPanel ersatzPanel = new JPanel();
            ersatzPanel.add(ersatzLabel);
            frame.add(ersatzPanel);
        }
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);
    }
}