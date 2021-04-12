/**
 * 
 */
package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javafx.util.Pair;
import model.ReversiModel;
import myExceptions.ReversiGameOverException;
import myExceptions.ReversiIllegalMoveException;

/**
 * @author Shreyas Khandekar
 * @author Mihir Yadav
 */
public class ReversiController {

	
	private ReversiModel model;
	private char cpuColor;
	private char playerColor;
	private boolean gameOver;
	private HashMap<Pair<Integer, Integer>, Integer> legalMoves;
	
	public ReversiController() {
		model = new ReversiModel();
		legalMoves = new HashMap<Pair<Integer, Integer>, Integer>();
		cpuColor = 'b';
		playerColor = 'w';
		gameOver = false;
		this.getLegalMoves(this.playerColor);
	}
	
	public ReversiController(ReversiModel model) {
		this.model = model;
		legalMoves = new HashMap<Pair<Integer, Integer>, Integer>();
		cpuColor = 'b';
		playerColor = 'w';
		gameOver = false;
		this.getLegalMoves(this.playerColor);
	}
	
	public void writeToFile(ObjectOutputStream oos) throws IOException {
		model.saveBoard(oos);
	}
	
	public void updateView() {
		model.manualNotify();
	}
	
	public void humanTurn(int x, int y) throws ReversiIllegalMoveException, ReversiGameOverException {
		if(gameOver)
			throw new ReversiGameOverException("Cannot play after game over");
		this.getLegalMoves(this.playerColor);
		if(isLegalMove(new Pair<Integer, Integer>(x,y)))
			this.model.setPiece(this.playerColor, x, y);
		else {
			throw new ReversiIllegalMoveException("" + x +"," + y);
		}
		
		this.flipColorsInAllDirections(this.playerColor, x, y);
		legalMoves.clear();
		//System.out.println(this.getBoard());

		// Now its the CPU's turn to play after player
		for(int i=0 ; i< 99999 ;++i) {
			i = i + 1 -1 ;
			
		}
		computerTurn();
		//System.out.println(this.getBoard());
	}
	
	public boolean isGameOver() { 
		return this.gameOver;
	}
	
	public String getWinner() {
		int[] count = model.getCount();
		if(count[0]==count[1]) {
			return "d";
		} if(count[0]>count[1]) {
			return "b";
		} else
			return "w";
	}
	
	public String getBoard() {
		return model.toString();
	}
	
	public int playerWin() {
		int playerCount=0;
		int cpuCount=0;
		for(int i=0 ; i<8 ; ++i) {
			for(int j=0 ; j<8 ; ++j) {
				if(model.getPiece(i, j)==this.cpuColor)
					playerCount++;
				if(model.getPiece(i, j)==this.playerColor)
					cpuCount++;
			}
		}
		return playerCount>cpuCount? 1: (playerCount==cpuCount? 0 : -1);
	}
	public void computerTurn() {
		this.getLegalMoves(this.cpuColor);
		if(this.legalMoves.isEmpty()) {
			this.getLegalMoves(this.playerColor);
			if(this.legalMoves.isEmpty()) {
				// TODO GAME OVER HANDLING
				this.gameOver= true;
			} else {
				return; // Player turn 
			}
				
		} else {
			playBestMove();
			if(this.getLegalMoves(this.playerColor).isEmpty())
				this.computerTurn();
			else
				return; // Player turn
		}
	}
	
	public void playBestMove() {
		int highestScore = 0;
		ArrayList<Pair<Integer, Integer>> bestMoves = new ArrayList<>();
		for(Pair<Integer, Integer> p : this.legalMoves.keySet()) {
			int currScore = this.legalMoves.get(p);
			if(currScore>highestScore) {
				highestScore = currScore;
			}
		}
		for(Pair<Integer, Integer> p : this.legalMoves.keySet()) {
			int currScore = this.legalMoves.get(p);
			if(currScore == highestScore) {
				bestMoves.add(p);
			}
		}
		Pair<Integer, Integer> bestMove = null;
		Random rand = new Random();
		bestMove = bestMoves.get(rand.nextInt(bestMoves.size()));
		
		model.setPiece(cpuColor, bestMove.getKey(), bestMove.getValue());
		this.flipColorsInAllDirections(this.cpuColor, bestMove.getKey(), bestMove.getValue());
		legalMoves.clear();
		
	}
	
	public boolean isLegalMove(Pair<Integer, Integer> p) {
		return legalMoves.containsKey(p);
	}
	
	public HashMap<Pair<Integer, Integer>, Integer> getLegalMoves(char c){
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				if(model.getPiece(i,j) == c) {
					getMovesInAllDirections(c,i,j);
				}
			}
		}
		return legalMoves;
	}
	
	// flips all colors for current piece
	public void flipColorsInAllDirections(char c, int i, int j) {
		//  left to right direction
		int LToRScore =0;
		int x = i+1; int y = j+0;
		while(x<8 && model.getPiece(x, y) != ' ' && model.getPiece(x, y) != c) {
			x++;
			LToRScore++;
		}
		if(x<8 && LToRScore!=0 && model.getPiece(x, y) == c) {
			this.flipColor(c, i, j, LToRScore, 1, 0);
		}
		
		// right to left direction
		int RToLScore =0;
		x = i-1; y = j+0;
		while(x>=0 && model.getPiece(x, y) != ' ' && model.getPiece(x, y) != c) {
			x--;
			RToLScore++;
		}
		if(x>=0 && RToLScore!=0 && model.getPiece(x, y) == c) {
			this.flipColor(c, i, j, RToLScore, -1, 0);
		}
		
		// up to down direction
		int UToDScore =0;
		x = i; y = j+1;
		while(y<8 && model.getPiece(x, y) != ' ' && model.getPiece(x, y) != c) {
			y++;
			UToDScore++;
		}
		if(y<8 && UToDScore!=0 && model.getPiece(x, y) == c) {
			this.flipColor(c, i, j, UToDScore, 0, 1);
		}
		
		// down to up direction
		int DToUScore =0;
		x = i; y = j-1;
		while(y>=0 && model.getPiece(x, y) != ' ' && model.getPiece(x, y) != c) {
			y--;
			DToUScore++;
		}
		if(y>=0 && DToUScore!=0 && model.getPiece(x, y) == c) {
			this.flipColor(c, i, j, DToUScore, 0, -1);
		}
		
		// diagonal Left Bottom to Right top
		int LBToRTScore =0;
		x = i+1; y = j-1;
		while(y>=0 && x<8 && model.getPiece(x, y) != ' ' &&  model.getPiece(x, y) != c) {
			y--;
			x++;
			LBToRTScore++;
		}
		if(y>=0 && x<8 &&  LBToRTScore!=0 && model.getPiece(x, y) == c) {
			this.flipColor(c, i, j, LBToRTScore, 1, -1);
		}
		
		// diagonal Right top to Left Bottom 
		int RTToLBScore =0;
		x = i-1; y = j+1;
		while(y<8 && x>=0 && model.getPiece(x, y) != ' ' && model.getPiece(x, y) != c) {
			y++;
			x--;
			RTToLBScore++;
		}
		if(y<8 && x>=0 && RTToLBScore!=0 && model.getPiece(x, y) == c) {
			this.flipColor(c, i, j, RTToLBScore, -1, 1);
		}
		
		// diagonal Left top to Right Bottom 
		int LTToRBScore =0;
		x = i+1; y = j+1;
		while(y<8 && x<8 && model.getPiece(x, y) != ' ' &&  model.getPiece(x, y) != c) {
			y++;
			x++;
			LTToRBScore++;
		}
			if(y<8 && x<8 && LTToRBScore!=0 && model.getPiece(x, y) == c) {
				this.flipColor(c, i, j, LTToRBScore, 1, 1);
		}
		
		// diagonal Right Bottom to Left top 
		int RBToLTScore =0;
		x = i-1; y = j-1;
		while(y>=0 && x>=0 && model.getPiece(x, y) != ' ' &&  model.getPiece(x, y) != c) {
			y--;
			x--;
			RBToLTScore++;
		}
		if(y>=0 && x>=0 &&  RBToLTScore!=0 && model.getPiece(x, y) == c) {
			this.flipColor(c, i, j, RBToLTScore, -1, -1);
		}
		
	}

	public void flipColor(char c, int i, int j, int score, int idiff, int jdiff) {
		while(score >= 0) {
			model.setPiece(c, i, j);
			score--;
			i+=idiff;
			j+=jdiff;
		}
		
	}

	// gets all moves for current piece
	public void getMovesInAllDirections(char c, int i, int j) {
		//  left to right direction
		int LToRScore =0;
		int x = i+1; int y = j+0;
		while(x<8 && model.getPiece(x, y) != ' ' && model.getPiece(x, y) != c) {
			x++;
			LToRScore++;
		}
		if(x<8 && LToRScore!=0 && model.getPiece(x, y) != c) {
			Pair<Integer,Integer> p = new Pair<Integer,Integer>(x,y);
			legalMoves.put(p,legalMoves.containsKey(p)? legalMoves.get(p)+LToRScore:LToRScore);
		}
		
		// right to left direction
		int RToLScore =0;
		x = i-1; y = j+0;
		while(x>=0 && model.getPiece(x, y) != ' ' && model.getPiece(x, y) != c) {
			x--;
			RToLScore++;
		}
		if(x>=0 && RToLScore!=0 && model.getPiece(x, y) != c) {
			Pair<Integer,Integer> p = new Pair<Integer,Integer>(x,y);
			legalMoves.put(p,legalMoves.containsKey(p)? legalMoves.get(p)+RToLScore:RToLScore);
		}
		
		// up to down direction
		int UToDScore =0;
		x = i; y = j+1;
		while(y<8 && model.getPiece(x, y) != ' ' && model.getPiece(x, y) != c) {
			y++;
			UToDScore++;
		}
		if(y<8 && UToDScore!=0 && model.getPiece(x, y) != c) {
			Pair<Integer,Integer> p = new Pair<Integer,Integer>(x,y);
			legalMoves.put(p,legalMoves.containsKey(p)? legalMoves.get(p)+UToDScore:UToDScore);
		}
		
		// down to up direction
		int DToUScore =0;
		x = i; y = j-1;
		while(y>=0 && model.getPiece(x, y) != ' ' && model.getPiece(x, y) != c) {
			y--;
			DToUScore++;
		}
		if(y>=0 && DToUScore!=0 && model.getPiece(x, y) != c) {
			Pair<Integer,Integer> p = new Pair<Integer,Integer>(x,y);
			legalMoves.put(p,legalMoves.containsKey(p)? legalMoves.get(p)+DToUScore:DToUScore);
		}
		
		// diagonal Left Bottom to Right top
		int LBToRTScore =0;
		x = i+1; y = j-1;
		while(y>=0 && x<8 && model.getPiece(x, y) != ' ' &&  model.getPiece(x, y) != c) {
			y--;
			x++;
			LBToRTScore++; 
		}
		if(y>=0 && x<8 &&  LBToRTScore!=0 && model.getPiece(x, y) != c) {
			Pair<Integer,Integer> p = new Pair<Integer,Integer>(x,y);
			legalMoves.put(p,legalMoves.containsKey(p)? legalMoves.get(p)+LBToRTScore:LBToRTScore);
		}
		
		// diagonal Right top to Left Bottom 
		int RTToLBScore =0;
		x = i-1; y = j+1;
		while(y<8 && x>=0 && model.getPiece(x, y) != ' ' && model.getPiece(x, y) != c) {
			y++;
			x--;
			RTToLBScore++;
		}
		if(y<8 && x>=0 && RTToLBScore!=0 && model.getPiece(x, y) != c) {
			Pair<Integer,Integer> p = new Pair<Integer,Integer>(x,y);
			legalMoves.put(p,legalMoves.containsKey(p)? legalMoves.get(p)+RTToLBScore:RTToLBScore);
		}
		
		// diagonal Left top to Right Bottom 
		int LTToRBScore =0;
		x = i+1; y = j+1;
		while(y<8 && x<8 && model.getPiece(x, y) != ' ' &&  model.getPiece(x, y) != c) {
			y++;
			x++;
			LTToRBScore++;
		}
		if(y<8 && x<8 && LTToRBScore!=0 && model.getPiece(x, y) != c) {
			Pair<Integer,Integer> p = new Pair<Integer,Integer>(x,y);
			legalMoves.put(p,legalMoves.containsKey(p)? legalMoves.get(p)+LTToRBScore:LTToRBScore);
		}
		
		// diagonal Right Bottom to Left top 
		int RBToLTScore =0;
		x = i-1; y = j-1;
		while(y>=0 && x>=0 && model.getPiece(x, y) != ' ' &&  model.getPiece(x, y) != c) {
			y--;
			x--;
			RBToLTScore++;
		}
		if(y>=0 && x>=0 &&  RBToLTScore!=0 && model.getPiece(x, y) != c) {
			Pair<Integer,Integer> p = new Pair<Integer,Integer>(x,y);
			legalMoves.put(p,legalMoves.containsKey(p)? legalMoves.get(p)+RBToLTScore:RBToLTScore);
		}
		
	}
	
	
}
