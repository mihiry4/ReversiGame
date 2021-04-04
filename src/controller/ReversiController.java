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
		model = new ReversiModel();
		legalMoves = new HashMap<Pair<Integer, Integer>, Integer>();
		// TODO Auto-generated constructor stub
	}
	
	private void nextTurn() {
		
	}
	
	private HashMap<Pair<Integer, Integer>, Integer> getLegalMoves(char c){
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				if(model.getPiece(i,j) == c) {
					getMovesInAllDirections(c,i,j);
				}
			}
		}
		return legalMoves;
	}
	
	// gets all moves for current piece
	private void getMovesInAllDirections(char c, int i, int j) {
		//  left to right direction
		int LToRScore =0;
		int x = i+1; int y = j+0;
		while(x<8 && model.getPiece(x, y) != c) {
			x++;
			LToRScore++;
		}
		if(x<8 && LToRScore!=0) {
			legalMoves.put(new Pair<Integer,Integer>(x,y),LToRScore);
		}
		
		// right to left direction
		int RToLScore =0;
		x = i-1; y = j+0;
		while(x>=0 && model.getPiece(x, y) != c) {
			x--;
			RToLScore++;
		}
		if(x>=0 && RToLScore!=0) {
			legalMoves.put(new Pair<Integer,Integer>(x,y),RToLScore);
		}
		
		// up to down direction
		int UToDScore =0;
		x = i; y = j+1;
		while(y<8 && model.getPiece(x, y) != c) {
			y++;
			UToDScore++;
		}
		if(y<8 && UToDScore!=0) {
			legalMoves.put(new Pair<Integer,Integer>(x,y),UToDScore);
		}
		
		// down to up direction
		int DToUScore =0;
		x = i; y = j-1;
		while(y>=0 && model.getPiece(x, y) != c) {
			y--;
			DToUScore++;
		}
		if(y>=0 && DToUScore!=0) {
			legalMoves.put(new Pair<Integer,Integer>(x,y),DToUScore);
		}
		
		// diagonal Left Bottom to Right top
		int LBToRTScore =0;
		x = i+1; y = j-1;
		while(y>=0 && x<8 && model.getPiece(x, y) != c) {
			y--;
			x++;
			LBToRTScore++;
		}
		if(y>=0 && x<8 && LBToRTScore!=0) {
			legalMoves.put(new Pair<Integer,Integer>(x,y),LBToRTScore);
		}
		
		// diagonal Right top to Left Bottom 
		int RTToLBScore =0;
		x = i-1; y = j+1;
		while(y<8 && x>=0 && model.getPiece(x, y) != c) {
			y++;
			x--;
			RTToLBScore++;
		}
		if(y<8 && x>=0 && RTToLBScore!=0) {
			legalMoves.put(new Pair<Integer,Integer>(x,y),RTToLBScore);
		}
		
		// diagonal Left top to Right Bottom 
		int LTToRBScore =0;
		x = i+1; y = j+1;
		while(y<8 && x<8 && model.getPiece(x, y) != c) {
			y++;
			x++;
			LTToRBScore++;
		}
		if(y<8 && x<8 && LTToRBScore!=0) {
			legalMoves.put(new Pair<Integer,Integer>(x,y),LTToRBScore);
		}
		
		// diagonal Right Bottom to Left top 
		int RBToLTScore =0;
		x = i-1; y = j-1;
		while(y>=0 && x>=0 && model.getPiece(x, y) != c) {
			y--;
			x--;
			RBToLTScore++;
		}
		if(y>=0 && x>=0 && RBToLTScore!=0) {
			legalMoves.put(new Pair<Integer,Integer>(x,y),RBToLTScore);
		}
		
	}
	
	private boolean isLegalMove(Pair<Integer, Integer> p) {
		return false;
	}
	
	public void playMove(int x, int y) {
		
	}
	
	public void playBestMove() {
		
	}

}
