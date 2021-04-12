/**
 * File: Reversi.java
 * @author Shreyas Khandekar
 * @author Mihir Yadav
 * Purpose: Mode for Reversi
 */
package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.util.Pair;
import view.ReversiView;

/**
 * Class: ReversiModel
 * @author Shreyas Khandekar
 * @author Mihir Yadav
 * Purpose: The model for the Reversi Game which contains the board
 *
 */
@SuppressWarnings("deprecation")
public class ReversiModel extends Observable {

	/**
	 * The board for Reversi. Implementation does not matter
	 */
	private ReversiBoard board;
	

	/**
	 * Constructor
	 * creates new reversi board or loads from save_game.dat
	 * Places the starting 4 pieces on the board too
	 */
	public ReversiModel() {
		try {
			FileInputStream fin = new FileInputStream("save_game.dat");
			ObjectInputStream ois = new ObjectInputStream(fin);
			this.board = (ReversiBoard) ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			board = new ReversiBoard();
			setPiece('w', 3, 3);
			setPiece('w', 4, 4);
			setPiece('b', 3, 4);
			setPiece('b', 4, 3); 
		} 

		
	}
	
	/**
	 * Get piece at x,y
	 * @param x row
	 * @param y col
	 * @return the piece: w if white and b if black or ' ' if empty
	 */
	public char getPiece(int x, int y) {
		return board.getValue(x, y);
	}
	
	/**
	 * Sets piece at x,y
	 * @param c The color we want to set the value to
	 * @param x row 
	 * @param y col
	 */
	public void setPiece(char c, int x, int y) {
		board.setValue(c, x, y);
		this.manualNotify();
	}
	
	/**
	 * Notifies the observers that the model has changed
	 */
	public void manualNotify() {
		setChanged();
		notifyObservers(this.board);
	}
	
	/**
	 * String representation of the model
	 * Which is just the board string
	 */
	public String toString() {
		String retval = "";
		for(int j=0 ; j<8 ; ++j) {
			for(int i=0 ; i<8 ; ++i) {
				retval+=this.board.getValue(i, j) + " | ";				
			}
			retval+="\n--------------------------------\n"; 
		}
		return retval;
	}
	
	/**
	 * We get an integer array of size 2 with the number
	 * of black pieces in index 0 and white in index 1
	 * @return the counts
	 */
	public int[] getCount(){
		return board.getCount();
	}
	
	/**
	 * Save the representation of the board in the object output stream
	 * passed
	 * @param oos in which we save to board
	 * @throws IOException
	 */
	public void saveBoard(ObjectOutputStream oos) throws IOException {
		// TODO Auto-generated method stub
		oos.writeObject(this.board);
	}
}
