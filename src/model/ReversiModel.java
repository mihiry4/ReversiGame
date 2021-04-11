/**
 * 
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
 * @author Shreyas Khandekar
 * @author Mihir Yadav
 *
 */
@SuppressWarnings("deprecation")
public class ReversiModel extends Observable {

	private ReversiBoard board;
	
	// creates new reversi board 
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
	// get piece at x,y
	public char getPiece(int x, int y) {
		return board.getValue(x, y);
	}
	
	// sets piece at x,y
	public void setPiece(char c, int x, int y) {
		board.setValue(c, x, y);
		this.manualNotify();
	}
	
	public void manualNotify() {
		setChanged();
		notifyObservers(this.board);
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
	
	public int[] getCount(){
		return board.getCount();
	}
	
	public void saveBoard(ObjectOutputStream oos) throws IOException {
		// TODO Auto-generated method stub
		oos.writeObject(this.board);
	}
}
