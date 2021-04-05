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
		board.setValue('w', 3, 3);
		board.setValue('w', 4, 4);
		board.setValue('b', 3, 4);
		board.setValue('b', 4, 3);
	}
	
	// get piece at x,y
	public char getPiece(int x, int y) {
		return board.getValue(x, y);
	}
	
	// sets piece at x,y
	public void setPiece(char c, int x, int y) {
		board.setValue(c, x, y);
	}
	
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
}
