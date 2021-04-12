/**
 * File: Reversi.java
 * @author Shreyas Khandekar
 * @author Mihir Yadav
 * Purpose: Board for Reversi
 */
package model;

import java.io.Serializable;

/**
 * Class: ReversiBoard
 * @author Shreyas Khandekar
 * @author Mihir Yadav
 * Purpose: Board for Reversi which is Serializable
 */
public class ReversiBoard implements Serializable{
	
	/**
	 * 2D array 8x8 size which is the Reversi Board
	 */
	private char[][] board;
	
	/**
	 * Constructor
	 * Creates a new ReversiBoard with all places being empty
	 */
	public ReversiBoard() {
		board = new char[8][8];
		for(int i =0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				board[i][j] = ' '; 
			}
		}
	}

	/**
	 * Takes char c and puts c at (x,y)
	 * @param c The character to put
	 * @param x row
	 * @param y column
	 */
	public void setValue(char c, int x, int y) {
		board[x][y] = c;
	}
	
	/**
	 * Gets val at (x,y)
	 * @param x row
	 * @param y col
	 * @return The val of the char at (x,y)
	 */
	public char getValue(int x, int y) { 
		return board[x][y];
	}
	
	/**
	 * It returns the count of black pieces (b) in index 0
	 * and count of white(w) in index 1 in a two dimensional array
	 * @return the counts
	 */
	public int[] getCount() {
		int[] retval = {0,0};
		
		for(int i =0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				if(board[i][j] == 'b')
					retval[0]++;
				else if(board[i][j] == 'w')
					retval[1]++;
				
			}
		}
		return retval;
	}
	
	/**
	 * Serializable ID
	 */
	private static final long serialVersionUID = 1L;

}
