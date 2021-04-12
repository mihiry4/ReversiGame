/**
 * File: Reversi.java
 * @author Shreyas Khandekar
 * @author Mihir Yadav
 * Purpose: Controller for Reversi
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
 * Class: ReversiController
 * @author Shreyas Khandekar
 * @author Mihir Yadav
 * Purpose: Controller for the Reversi Game
 */
public class ReversiController {

	/**
	 * model with which the controller is associated
	 */
	private ReversiModel model;
	/**
	 * Color of the computer player
	 * Always black in this version
	 */
	private char cpuColor;
	/**
	 * Color of the human player
	 * Always black in this version
	 */
	private char playerColor;
	/**
	 * True if the game is over
	 */
	private boolean gameOver;
	/**
	 * Map of legal moves for whoever's turn it is
	 */
	private HashMap<Pair<Integer, Integer>, Integer> legalMoves;
	
	/**
	 * Constructor which sets up a new model
	 */
	public ReversiController() {
		model = new ReversiModel();
		legalMoves = new HashMap<Pair<Integer, Integer>, Integer>();
		cpuColor = 'b';
		playerColor = 'w';
		gameOver = false;
		this.getLegalMoves(this.playerColor);
	}
	
	/**
	 * Constructor which uses the passed model
	 * @param model to associate with the constructor
	 */
	public ReversiController(ReversiModel model) {
		this.model = model;
		legalMoves = new HashMap<Pair<Integer, Integer>, Integer>();
		cpuColor = 'b';
		playerColor = 'w';
		gameOver = false;
		this.getLegalMoves(this.playerColor);
	}
	
	/**
	 * Used to write the model/board to an object output stream
	 * @param oos Object output stream to which we write
	 * @throws IOException
	 */
	public void writeToFile(ObjectOutputStream oos) throws IOException {
		model.saveBoard(oos);
	}
	
	/**
	 * Manually tells the model to notify to view to update
	 */
	public void updateView() {
		model.manualNotify();
	}
	
	/**
	 * Called when the player plays this game they chose the x and y
	 * positions of where they want to place their piece
	 * @param x row of piece position
	 * @param y col of piece position
	 * @throws ReversiIllegalMoveException
	 * @throws ReversiGameOverException
	 */
	public void humanTurn(int x, int y) throws ReversiIllegalMoveException, ReversiGameOverException {
		if(gameOver) {
			throw new ReversiGameOverException("Cannot play after game over");
		}
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
	
	/**
	 * Tells us if the game is over
	 * @return if game is over
	 */
	public boolean isGameOver() { 
		return this.gameOver;
	}
	
	/**
	 * Query who has the most pieces after game over
	 * to check who won
	 * @return w for white wins b for black wins d for draw
	 */
	public String getWinner() {
		int[] count = model.getCount();
		if(count[0]==count[1]) {
			return "d";
		} if(count[0]>count[1]) {
			return "b";
		} else
			return "w";
	}
	
	/**
	 * Get a string of the board for the text view
	 * @return printable board
	 */
	public String getBoard() {
		return model.toString();
	}
	
	/**
	 * This function is not used in the GUI view
	 * It was replaces by the combination of getWinner and
	 * isGameOver
	 * @return 1 if player wins, -1 if cpu wins 0 if draw
	 */
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

	
	/**
	 * Computer uses this function to play its turn
	 * Checks the legal moves and plays the one which maximised score
	 * for this turn
	 */
	public void computerTurn() {
		this.getLegalMoves(this.cpuColor);
		if(this.legalMoves.isEmpty()) {
			this.getLegalMoves(this.playerColor);
			if(this.legalMoves.isEmpty()) {
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
	

	/**
	 * Calculates and plays the best move which maximises the immediate
	 * score for the computer
	 */
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
	
	/**
	 * Checks if a certain move is legal
	 * @param p pair with x and y coordinates for the move
	 * @return true if the move is legal false otherwise
	 */
	public boolean isLegalMove(Pair<Integer, Integer> p) {
		return legalMoves.containsKey(p);
	}
	
	/**
	 * Gets the lgal moves for a certain player (color)
	 * c can be b or w in this version
	 * @param c b or w denoting the player we want to get legalMoves for
	 * @return the Hashmap with the legalmoves and the score we would gain
	 * 			if we play this move
	 */
	public HashMap<Pair<Integer, Integer>, Integer> getLegalMoves(char c){
		legalMoves.clear();
		for(int i = 0;i<8;i++) {
			for(int j = 0;j<8;j++) {
				if(model.getPiece(i,j) == c) {
					getMovesInAllDirections(c,i,j);
				}
			}
		}
		return legalMoves;
	}
	

	/**
	 * Playing a certain move means we need to flip the appropriate pieces
	 * This function does that for a given move 
	 * @param c Color of the player
	 * @param i row 
	 * @param j col
	 */
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


	/**
	 * Flips the color in a certain direction. The number of pieces flipped
	 * in that direction is dictated by the score associated with that 
	 * move in that direction
	 * @param c The color to change the pieces to
	 * @param i The starting place row
	 * @param j The starting place column
	 * @param score the score associated with the direction and that move
	 * @param idiff This combined with jdiff specifies the direction
	 * @param jdiff This combined with idiff specifies the direction
	 */
	private void flipColor(char c, int i, int j, int score, int idiff, int jdiff) {
		while(score >= 0) {
			model.setPiece(c, i, j);
			score--;
			i+=idiff;
			j+=jdiff;
		}
		
	}


	/**
	 * Gets all moves for current piece denoted by the row i and col j
	 * We check all directions for that piece.
	 * @param c The piece color
	 * @param i the row of the piece
	 * @param j the col of the piece
	 */
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
			legalMoves.put(p,legalMoves.containsKey(p)? legalMoves.get(p)+LToRScore:LToRScore); // Can't get
			// full coverage for this as as this is the first case so a preexisting key would not be
			// present in the legalMoves Map
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
