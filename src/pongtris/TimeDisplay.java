package pongtris;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

/**
 * Diese Klasse beherbergt einen Timer, der die abgelaufende Zeit mitschneidet.
 * 
 * @author Bianca, Christoph, Erik, Lukas, Tobias, Paul
 * @version 1.0
 *
 */
public class TimeDisplay implements ActionListener {
	
	private Timer timer = new Timer(1000, this);
	private int t = 0;
		
	public TimeDisplay(){
		timer.start();
	}
	
	public void actionPerformed(ActionEvent evt) {
		t++;
	}
	
	public int getTime() {
		return t;
	}
}