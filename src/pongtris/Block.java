package pongtris; 

import java.awt.Point;
import java.util.ArrayList;

/**
 * Diese Klasse generiert einen Tetrisblock.
 * 
 * @author Bianca, Christoph, Erik, Lukas, Tobias, Paul
 * @version 1.1
 *
 */
public class Block {
	
	public ArrayList<Point> returnBlock(int blockNum) {
		switch(blockNum) {
        case 3:
            return block3;
        case 4:
        	return block4;
        case 5:
        	return block5;
        case 6:
        	return block6;
        case 7:
        	return block7;
        default:
        	return block3;
		}
	}

	/**
	 * I
	 */
    private ArrayList<Point> block3 = new ArrayList<Point>() {
        {
            add(new Point(0, 0));
            add(new Point(1, 0));
            add(new Point(0, 1));
            add(new Point(0, 2));
            add(new Point(0, 3));
            add(new Point(0, 4));
            add(new Point(0, 5));
            add(new Point(0, 6));
            add(new Point(0, 7));
            add(new Point(1, 1));
            add(new Point(1, 2));
            add(new Point(1, 3));
            add(new Point(1, 4));
            add(new Point(1, 5));
            add(new Point(1, 6));
            add(new Point(1, 7));
        }
    };
    
    /**
     * O
     */
    private ArrayList<Point> block4 = new ArrayList<Point>() {
        {
            add(new Point(0, 0));
            add(new Point(1, 0));
            add(new Point(0, 1));
            add(new Point(0, 2));
            add(new Point(0, 3));
            add(new Point(1, 1));
            add(new Point(1, 2));
            add(new Point(1, 3));
            add(new Point(2, 0));
            add(new Point(2, 1));
            add(new Point(2, 2));
            add(new Point(2, 3));
            add(new Point(3, 0));
            add(new Point(3, 1));
            add(new Point(3, 2));
            add(new Point(3, 3));
        }
    };
    
    /**
     * -|
     */
    private ArrayList<Point> block5 = new ArrayList<Point>() {
        {
            add(new Point(0, 0));
            add(new Point(1, 0));
            add(new Point(0, 1));
            add(new Point(1, 1));
            add(new Point(2, -2));
            add(new Point(2, -1));
            add(new Point(2, 0));
            add(new Point(2, 1));
            add(new Point(2, 2));
            add(new Point(2, 3));
            add(new Point(3, -2));
            add(new Point(3, -1));
            add(new Point(3, 0));
            add(new Point(3, 1));
            add(new Point(3, 2));
            add(new Point(3, 3));
        }
    };
    
    /**
     * L
     */
    private ArrayList<Point> block6 = new ArrayList<Point>() {
        {
            add(new Point(0, 0));
            add(new Point(1, 0));
            add(new Point(2, 0));
            add(new Point(3, 0));
            add(new Point(0, 1));
            add(new Point(1, 1));
            add(new Point(2, 1));
            add(new Point(3, 1));
            add(new Point(2, 2));
            add(new Point(3, 2));
            add(new Point(2, 3));
            add(new Point(3, 3));
            add(new Point(2, 4));
            add(new Point(3, 4));
            add(new Point(2, 5));
            add(new Point(3, 5));
        }
    };
    
    /**
     * |_
     *   |
     */
    private ArrayList<Point> block7 = new ArrayList<Point>() {
        {
            add(new Point(0, 0));
            add(new Point(1, 0));
            add(new Point(0, 1));
            add(new Point(0, 2));
            add(new Point(0, 3));
            add(new Point(1, 1));
            add(new Point(2, 1));
            add(new Point(3, 1));
            add(new Point(1, 2));
            add(new Point(1, 3));
            add(new Point(2, -2));
            add(new Point(3, -2));
            add(new Point(2, -1));
            add(new Point(3, -1));
            add(new Point(2, 0));
            add(new Point(3, 0));
        }
    };
}