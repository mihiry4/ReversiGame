/**
 * 
 */
package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.util.Pair;

/**
 * @author Shreyas Khandekar
 * @author Mihir Yadav
 *
 */
@SuppressWarnings("deprecation")
public class ReversiModel extends Observable {

	private ReversiBoard board;
	
	// creates new reversi board
	public ReversiModel() {
		board = new ReversiBoard();
	}
	
	// get piece at x,y
	public char getPiece(int x, int y) {
		return board.getValue(x, y);
	}
	
	// sets piece at x,y
	public void setPiece(char c, int x, int y) {
		board.setValue(c, x, y);
	}

}
