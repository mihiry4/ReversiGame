/**
 * 
 */
package model;

import java.io.Serializable;

/**
 * @author Shreyas Khandekar
 * @author Mihir Yadav
 *
 */
public class ReversiBoard implements Serializable{
	
	private char[][] board;
	
	//creates a new board
	public ReversiBoard() {
		board = new char[8][8];
		for(int i =0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				board[i][j] = ' ';
			}
		}
	}

	// takes char c and puts c at (x,y)
	public void setValue(char c, int x, int y) {
		board[x][y] = c;
	}
	
	//gets val at (x,y)
	public char getValue(int x, int y) {
		return board[x][y];
	}
	
	
	private static final long serialVersionUID = 1L;

}
