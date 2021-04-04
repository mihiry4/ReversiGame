/**
 * 
 */
package controller;

import java.util.HashMap;

import javafx.util.Pair;
import model.ReversiModel;

/**
 * @author Shreyas Khandekar
 * @author Mihir Yadav
 */
public class ReversiController {

	
	private ReversiModel model;
	private char currentTurn;
	private HashMap<Pair<Integer, Integer>, Integer> legalMoves;
	
	public ReversiController() {
		// TODO Auto-generated constructor stub
	}
	
	private void nextTurn() {
		
	}
	
	private HashMap<Pair<Integer, Integer>, Integer> getLegalMoves(char c){
		return legalMoves;
	}
	
	private boolean isLegalMove(Pair<Integer, Integer> p) {
		return false;
	}
	
	public void playMove(int x, int y) {
		
	}
	
	public void playBestMove() {
		
	}

}
