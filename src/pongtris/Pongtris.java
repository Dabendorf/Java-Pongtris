package pongtris;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Diese Klasse steuert die gesamte Spiellogik und den graphischen Aufbau.<br>
 * Sie enthaelt noch ein paar unschoene Fehler, die aufgrund ihrer Komplexitaet noch nicht behoben werden konnten.
 * 
 * @author Bianca, Christoph, Erik, Lukas, Tobias, Paul
 * @version 1.1
 *
 */
public class Pongtris implements KeyListener {
	
	/**Der Frame in dem sich alles abspielt.*/
	private JFrame frame = new JFrame("Pongtris");
	/**Malt das gesamte Spielfeld*/
	private JPanel spielfeldPanel = new JPanel() {
        public void paint(Graphics gr) {
            zeichne(gr);
        }
    };
    /**Die Richtung in die sich Schlaeger 1 bewegt*/
	private int player1Direction = 0;
	/**Die Richtung in die sich Schlaeger 2 bewegt*/
    private int player2Direction = 0;
    /**Die Breite des Spielfeldes mit Rand.*/
    private final int tilesWidth = 84 + 3;
    /**Die Hoehe des Spielfeldes mit Rand.*/
    private final int tilesHeight = 42 + 3;
    /**Die y-Position des Schlaegers von Spieler 1.*/
    private int player1Pos = (int)(tilesHeight/2 + 0.5f);
    /**Die y-Position des Schlaegers von Spieler 2.*/
    private int player2Pos = (int)(tilesHeight/2 + 0.5f);
    /**Die maximale y-Position der Schlaeger.*/
    private final int playerPosMax = tilesHeight - 6;
    /**Die minimale y-Position der Schlaeger.*/
    private final int playerPosMin = 5;
    /**Wie weit der Schlaeger von Spieler 1 vom Ursprungspunkt weggeschoben wurde (x-Richtung).*/
    private int player1Moved = 5;
    /**Wie weit der Schlaeger von Spieler 2 vom Ursprungspunkt weggeschoben wurde (x-Richtung).*/
    private int player2Moved = 5;
    /**Der Timer des laufenden Spiels*/
    private TimeDisplay td = new TimeDisplay();
    /**Das gesamte Spielfeld mit Breite*Hoehe.*/
    private int[][] spielfeld = new int[tilesWidth][tilesHeight];
    private BufferedImage render = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    /**Die x-Position des Balls*/
    private int ballX = -1;
    /**Die y-Position des Balls*/
    private int ballY = -1;
    /**Die Richtung des Balls, links:(0,1,2), rechts:(3,4,5)*/
    private int ballDirection = getStartDirection();
    /**Der Tetrissteintyp des Balls*/
    private int ballType = getRandomBall();
    /**Die Startgeschwindigkeit des Balls*/
    private float startBallSpeed = 90f;
    /**Die Geschwindigkeit des Balls*/
    private float ballSpeed = startBallSpeed;
    /**Boolean ob der Ball sich bewegt.*/
    private boolean ballIsMoving = false;
    /**Das Spielfeld mit Tetrissteinen mit Breite*Hoehe.*/
    private int[][] blockfeld = new int[tilesWidth][tilesHeight];
    /**Irgendein synchronisiertes Objekt.*/
    private final Object tetrisPointsLock = new Object();
    /**Boolean ob das Spiel beendet wurde.*/
    private boolean ende = false;
    /**Menge an Punkten fuer den aktuellen Tetrisstein.*/
    private ArrayList<Point> tetrisPoints = new ArrayList<Point>();
    /**Die Menge an laufenden Threads.*/
    private ArrayList<Thread> threadliste = new ArrayList<Thread>();
    
	public Pongtris() {
		try {
			frame.setIconImage(Toolkit.getDefaultToolkit().getImage(new URL(BaseURL.getJarBase(Pongtris.class), "./starticon.png")));
		} catch (MalformedURLException e) {}
		frame.addWindowListener(new WindowAdapter() {
			@SuppressWarnings("deprecation")
			@Override
            public void windowClosing(WindowEvent e) {
				ende = true;
				for(Thread t:threadliste) {
					t.stop();
				}
				new MainMenue();
				frame.dispose();
            }
        });
		spielfeldPanel.addKeyListener(this);
        frame.addKeyListener(this);
        Container cp = frame.getContentPane();
        cp.add(spielfeldPanel);

        spielfeldPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
            	spielfeldPanel.repaint();
            }
        });
        
        for(int y2=0;y2<tilesHeight;y2++) {
            blockfeld[0][y2] = 7;
            blockfeld[tilesWidth-1][y2] = 7;
        }
        refillArray();
        startRepaint();
        startMoving();
        startBallMoving();
        
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * Diese Methode bewegt die Schlaeger der Spieler auf und ab. W und S steuert Spieler 1, Up und Down Spieler 2.
	 * @param e Das KeyEvent auf der Tastatur.
	 */
	public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W) {
            setMoving(true, -1);
        } else if(e.getKeyCode() == KeyEvent.VK_S) {
            setMoving(true, 1);
        } else if(e.getKeyCode() == KeyEvent.VK_UP) {
            setMoving(false, -1);
        } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            setMoving(false, 1);
        }
    }

	/**
	 * Diese Methode bewegt die Schlaeger der Spieler auf und ab. W und S steuert Spieler 1, Up und Down Spieler 2.
	 * @param e Das KeyEvent auf der Tastatur.
	 */
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
            setMoving(true, 0);
        } else if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            setMoving(false, 0);
        }
    }
    
    /**
     * Diese Methode des KeyListeners ist unbenutzt, musste aber implementiert werden.
     * @param e Das KeyEvent auf der Tastatur.
     */
	public void keyTyped(KeyEvent e) {
	}

    /**
     * Diese Methode veraendert die Bewegungsrichtung der Schlaeger.
     * @param player1 true=spieler1,false=spieler2
     * @param direction Nimmt die Richtung entgegen.
     */
    private void setMoving(boolean player1, int direction) {
        if(player1) {
            player1Direction = direction;
        } else {
            player2Direction = direction;
        }
    }
	
	/**
	 * Diese Methode gibt die Nummer eines zufaelligen Tetristeils aus.
	 * @return Gibt Ballnummer zurueck.
	 */
	private int getRandomBall() {
        Random r = new Random();
        return 3 + r.nextInt(5);
    }

	/**
	 * Diese Methode gibt eine Startrichtung des Balls zufaellig aus.
	 * @return Gibt Startrichtung des Balls zurueck.
	 */
    private int getStartDirection() {
        Random r = new Random();
        return r.nextInt(6);
    }

    /**
     * Diese Methode pausiert x Sekunden.
     * @param ms Anzahl der zu pausierenden Sekunden.
     */
    private void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {}
    }
    
    /**
     * Diese Methode laedt das Spielfeld neu und ueberprueft, ob ein Spieler bereits gewonnen hat.
     */
    private void startRepaint() {
        Thread thread = new Thread() {
			public void run() {
                boolean runBool = true;
                while(runBool) {
                    synchronized(tetrisPointsLock) {
                        refillArray();
                    }
                    spielfeldPanel.repaint();
                    if(player1Moved >= 30) {
                        beenden(false);
                    } else if(player2Moved >= 30) {
                    	beenden(true);
                    }
                    try {
                        Thread.sleep(16);
                    } catch (Exception ex) {}
                }
            }
        };
        threadliste.add(thread);
        thread.setDaemon(true);
		thread.start();
    }
    
    /**
     * Diese Methode beendet das Spiel, schliesst den Frame und oeffnet die Highscoreliste.
     * @param player1Won boolean welcher Spieler gewonnen hat.
     */
    @SuppressWarnings("deprecation")
	private void beenden(boolean player1Won) {
    	ende = true;
		ballIsMoving = false;
		new Conclusion(player1Won,1000*td.getTime());
    	frame.setVisible(false);
    	for(Thread t:threadliste) {
			t.stop();
		}
        frame.dispose();
    }
    
    /**
     * Diese Methode gibt eine Farbe je nach Eingabe aus.
     * @param colorCode Nimmt die Farbnummer entgegen.
     * @return Gibt die Farbe zurueck.
     */
    private Color getColor(int colorCode) {
        switch(colorCode) {
            case 0:
                return Color.BLACK;
            case 1:
                return Color.RED;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.YELLOW;
            case 4:
                return Color.CYAN;
            case 5:
                return Color.GREEN;
            case 6:
                return Color.MAGENTA;
            case 7:
                return Color.WHITE;
            default:
                return Color.GRAY;
        }
    }

    /**
     * Diese Methode fuellt den Spieldesignarray mit Informationen.
     */
    private void refillArray() {
        for(int x=0;x<tilesWidth;x++) {
            for(int y=0;y<tilesHeight;y++) {
            	/**Weisse Raender an den Seiten.*/
                if (x == 0 || x == tilesWidth - 1 || y == 0 || y == tilesHeight - 1) {
                    spielfeld[x][y] = 7;
                } else {
                	/**Schwarzes Feld und weisse Mittellinie.*/
                    spielfeld[x][y] = 0;
                    if(x == (int)(tilesWidth/2) && y%2 == 1) {
                        spielfeld[x][y] = 7;
                    }
                    
                    /**Schlaeger von Spieler 1.*/
                    if((x == 2 + player1Moved || x == 3 + player1Moved)
                            && (y >= player1Pos - 4 && y <= player1Pos + 4)) {
                        spielfeld[x][y] = 1;
                    }
                    /**Schlaeger von Spieler 2.*/
                    if((x == tilesWidth - 4 - player2Moved || x == tilesWidth - 3 - player2Moved)
                            && (y >= player2Pos - 4 && y <= player2Pos + 4)) {
                        spielfeld[x][y] = 2;
                    }
                    /**Der farbige Ball.*/
                    if(x == ballX && y == ballY) {
                        spielfeld[x][y] = ballType;
                    }
                    /**Der Tetrisstein an den Raendern.*/
                    for(int i=0;i<tetrisPoints.size();i++) {
                    	try {
	                        if(x == tetrisPoints.get(i).x && y == tetrisPoints.get(i).y) {
	                            spielfeld[x][y] = ballType;
	                        }
                    	}catch(Exception e) {}
                    } 
                    if(blockfeld[x][y] != 0) {
                        spielfeld[x][y] = blockfeld[x][y];
                    }
                }
            }
        }
    }

    /**Diese Methode regelt die Bewegung der Spielerschlaeger.*/
    private void startMoving() {
        Thread thread = new Thread() {
            public void run() {
                while(true) {
                    player1Pos += player1Direction;
                    if (player1Pos > playerPosMax) {
                        player1Pos = playerPosMax;
                    } else if (player1Pos < playerPosMin) {
                        player1Pos = playerPosMin;
                    }
                    player2Pos += player2Direction;
                    if (player2Pos > playerPosMax) {
                        player2Pos = playerPosMax;
                    } else if (player2Pos < playerPosMin) {
                        player2Pos = playerPosMin;
                    }
                    try {
                        Thread.sleep(50);
                    } catch (Exception ex) {

                    }
                }
            }
        };
        threadliste.add(thread);
        thread.setDaemon(true);
		thread.start();
    }
    
    /**
     * Diese Methode zeichnet auf dem Feld.
     * @param gr Nimmt das GraphicsElement entgegen.
     */
    private void zeichne(Graphics gr) {
        Graphics2D g2 = (Graphics2D) gr;
        g2.drawImage(render, 0, 0, null);
        Thread thread = new Thread() {
            public void run() {
                int w = spielfeldPanel.getWidth();
                int h = spielfeldPanel.getHeight();
                BufferedImage buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = buffer.createGraphics();
                float rasterWidth = (float) w / (float) tilesWidth;
                float rasterHeight = (float) h / (float) tilesHeight;

                for (int x=0;x<tilesWidth;x++) {
                    for (int y=0;y<tilesHeight;y++) {
                        g.setColor(getColor(spielfeld[x][y]));
                        g.fillRect((int) Math.ceil(x * rasterWidth - 0.5f), (int) Math.ceil(y * rasterHeight - 0.5f), (int) Math.ceil(rasterWidth), (int) Math.ceil(rasterHeight));
                    }
                }
                g.setColor(Color.GREEN);
                g.drawString("Time: " + td.getTime() + "s", rasterWidth + 2, rasterHeight * 2);
                render = buffer;
            }
        };
        threadliste.add(thread);
        thread.setDaemon(true);
		thread.start();
    }
    
    /**Diese Methode bewegt je nach aktueller Ballrichtung den Ball auf sein naechstes Feld.*/
    private void startBallMoving() {
        Thread thread = new Thread() {
            public void run() {
            	ballIsMoving = true;
                ballX = (int) ((tilesWidth / 2) + 0.5f);
                ballY = (int) ((tilesHeight / 2) + 0.5f);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                while(ballIsMoving && !ende) {
                    switch(ballDirection) {
                        case 0:
                            ballX++;
                            ballY--;
                            break;
                        case 1:
                            ballX++;
                            break;
                        case 2:
                            ballX++;
                            ballY++;
                            break;
                        case 3:
                            ballX--;
                            ballY++;
                            break;
                        case 4:
                            ballX--;
                            break;
                        case 5:
                            ballX--;
                            ballY--;
                    }

                    /**Die Spielfeldgrenze oben.*/
                    if(ballY == 1) {
                        if(ballDirection == 0) {
                            ballDirection = 2;
                        } else if(ballDirection == 5) {
                            ballDirection = 3;
                        }
                    }
                    /**Die Spielfeldgrenze unten.*/
                    if(ballY == tilesHeight - 2) {
                        if(ballDirection == 2) {
                            ballDirection = 0;
                        } else if(ballDirection == 3) {
                            ballDirection = 5;
                        }
                    }
                    /**Die Interaktion mit dem Schlaeger von Spieler 1.*/
                    if(ballX == 3 + player1Moved) {
                    	ballSpeed *= 0.9f;
                        //System.out.println("Ballgeschwindigkeit: "+ballSpeed);
                        if(player1Pos > ballY + 4) {
                            /**Spieler ist unter dem Ball.*/
                            createTetris(true);
                        } else if(player1Pos > ballY + 1) {
                        	/**Ball geht schraeg nach oben.*/
                            ballDirection = 0;
                        } else if(player1Pos > ballY - 1) {
                        	/**Ball geht geradeaus nach vorn.*/
                            ballDirection = 1;
                        } else if(player1Pos > ballY - 4) {
                        	/**Ball geht schraeg nach unten.*/
                            ballDirection = 2;
                        } else {
                        	/**Spieler ist ueber dem Ball.*/
                            createTetris(true);
                        }
                    }
                    /**Die Interaktion mit dem Schlaeger von Spieler 2.*/
                    if(ballX == tilesWidth - 4 - player2Moved) {
                        ballSpeed *= 0.9f;
                        //System.out.println("Ballgeschwindigkeit: "+ballSpeed);
                        if(player2Pos > ballY + 4) {
                        	/**Spieler ist unter dem Ball.*/
                            createTetris(false);
                        } else if(player2Pos > ballY + 1) {
                        	/**Ball geht schraeg nach oben.*/
                            ballDirection = 5;
                        } else if(player2Pos > ballY - 1) {
                        	/**Ball geht geradeaus nach vorn.*/
                            ballDirection = 4;
                        } else if(player2Pos > ballY - 4) {
                        	/**Ball geht schraeg nach unten.*/
                            ballDirection = 3;
                        } else {
                        	/**Spieler ist ueber dem Ball.*/
                            createTetris(false);
                        }
                    }
                    try {
                        Thread.sleep((int) Math.ceil(ballSpeed));
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };
        threadliste.add(thread);
        thread.setDaemon(true);
		thread.start();
    }
    
    /**
     * Diese Methode generiert einen vollstaendigen neuen Tetrisblock fuer den Spieler der den Ball nicht zurueckgeschlagen hat.
     * @param player1 boolean um welchen Spieler es geht.
     */
    private void createTetris(boolean player1) {
    	/**Setzt Ballbewegung und Ballrichtung zurueck.*/
        ballIsMoving = false;
        ballX = -1;

        /**Erstellt ein neues Tetristeil.*/
        tetrisPoints.clear();
        ArrayList<Point> block = new Block().returnBlock(ballType);

        synchronized(tetrisPointsLock) {
            for(int i=0;i<block.size();i++) {
                Point p = new Point(block.get(i).x, block.get(i).y);
                p.y += ballY;
                if(player1) {
                    p.x *= -1;
                }
                tetrisPoints.add(p);
            }
            if(player1) {
                for(int i=0;i<tetrisPoints.size();i++) {
                    tetrisPoints.get(i).x += 4 + player1Moved;
                }
            } else {
                for(int i=0;i<tetrisPoints.size();i++) {
                    tetrisPoints.get(i).x += tilesWidth - 5 - player2Moved;
                }
            }

            int shift;
            do {
                shift = 0;
                try {
                	for(int i=0;i<tetrisPoints.size();i++) {
                        if(tetrisPoints.get(i).y < 1) {
                            shift = 1;
                        } else if(tetrisPoints.get(i).y > tilesHeight - 2) {
                            shift = -1;
                        }
                    }
                }catch(Exception e) {}
                
                for(int i=0;i<tetrisPoints.size();i++) {
                    tetrisPoints.get(i).y += shift;
                }
            } while(shift != 0);
        }

        while(canMoveTetris(player1)) {
            synchronized(tetrisPointsLock) {
                if(player1) {
                    for(int i=0;i<tetrisPoints.size();i++) {
                        tetrisPoints.get(i).x--;
                    }
                } else {
                    for(int i=0;i<tetrisPoints.size();i++) {
                        tetrisPoints.get(i).x++;
                    }
                }
            }
            wait(200);
        }
        synchronized(tetrisPointsLock) {
            for (int i=0;i<tetrisPoints.size();i++) {
                try {
                    blockfeld[tetrisPoints.get(i).x][tetrisPoints.get(i).y] = ballType;
                } catch(Exception ex) {
                }
            }
        }

        /**Spalte pruefen.*/
        int verschiebung = 5;
        if(player1) {
            for(int i=0;i<tilesWidth/2;i++) {
                /**Fuer jede Spalte.*/
                boolean frei = true;
                for (int j=0;j<tilesHeight;j++) {
                    if (blockfeld[i][j] != 0) {
                        frei = false;
                    }
                }
                if(frei) {
                    break;
                } else {
                    verschiebung++;
                }
            }
            player1Moved = verschiebung;
        } else {
            for(int i=tilesWidth-1;i>tilesWidth/2;i--) {
            	/**Fuer jede Spalte.*/
                boolean frei = true;
                for(int j=0;j<tilesHeight;j++) {
                    if(blockfeld[i][j] != 0) {
                    	frei = false;
                    }
                }
                if(frei) {
                    break;
                } else {
                    verschiebung++;
                }
            }
            player2Moved = verschiebung;
        }

        /**Gibt eine neue Ballrichtung mit einem neuen Ball aus und startet die naechste Runde.*/
        ballDirection = getStartDirection();
        ballType = getRandomBall();
        ballSpeed = startBallSpeed;
        ballX = (int) ((tilesWidth / 2) + 0.5f);
        ballY = (int) ((tilesHeight / 2) + 0.5f);
        wait(300);
        startBallMoving();
    }

    /**
     * Diese Methode stellt fest, ob der Spielerschlaeger sich noch weiter bewegen kann oder ob er verloren hat.
     * @param player1 boolean um welchen Spieler es geht.
     * @return Gibt boolean zurueck, ob eine weitere Bewegung moeglich ist.
     */
    private boolean canMoveTetris(boolean player1) {
        boolean erg = true;
        try {
            if(player1) {
                for(int i=0;i<tetrisPoints.size();i++) {
                    if(blockfeld[tetrisPoints.get(i).x - 1][tetrisPoints.get(i).y] != 0) {
                        erg = false;
                    }
                }
            } else {
                for(int i=0;i<tetrisPoints.size();i++) {
                    if(blockfeld[tetrisPoints.get(i).x + 1][tetrisPoints.get(i).y] != 0) {
                        erg = false;
                    }
                }
            }
            return erg;
        } catch (Exception ex) {
            return false;
        }
    }
}