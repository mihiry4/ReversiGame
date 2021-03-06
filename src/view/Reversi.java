/**
 * File: Reversi.java
 * @author Shreyas Khandekar
 * @author Mihir Yadav
 * Purpose: Driver for Reversi in either text or GUI View
 */
package view;

import java.util.Scanner;

import javafx.application.Application;
import javafx.util.Pair;
import controller.ReversiController;
import model.ReversiModel;
import myExceptions.ReversiGameOverException;
import myExceptions.ReversiIllegalMoveException;

/**
 * @author Shreyas Khandekar
 * @author Mihir Yadav
 */
public class Reversi {

	/**
	 * Main function to run Reversi.
	 * This launches the GUI application
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(ReversiView.class, args);
		//textReversi(args);

	}
	
	/**
	 * Function to run Reversi in the text View
	 * @param args
	 */
	public static void textReversi(String[] args) {
		
		// This class represents the view, it should be how uses play the game
		System.out.println("Welcome to Reversi!");
				
		boolean play = askPlay();
		// while the user wants to play:
		//while(play) {
			ReversiModel model = new ReversiModel();
			// Construct the controller, passing in the model
			ReversiController controller = new ReversiController(model);
			while(!controller.isGameOver()) {
				System.out.println(controller.getBoard());
				
		        	int[] playerMove = getPlayerMove();
		        	// Check whether or not the input is correct (by asking the controller)
		        	try {
						controller.humanTurn(playerMove[0], playerMove[1]);
					} catch (ReversiIllegalMoveException | ReversiGameOverException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
							
			}
		   // Win/Loss determination
		   printWin();
		   printLose();
		   System.out.println(controller.playerWin());
		//}

	}
	
	/**
	 * Asks the user for a guess.
	 * This will take in any guess but an exception will be thrown
	 * from the controller class if the length of the guess is incorrect.
	 * 
	 * @param loop Denoted which guess the user is on. Prints a warning
	 * 				for the last guess.
	 * @return The guess from the user
	 */
	private static int[] getPlayerMove() {
		System.out.print("Enter x position : ");
		Scanner in = new Scanner(System.in);
		int x = in.nextInt();
		System.out.print("Enter y position : ");
		int y = in.nextInt();
		//in.close();
		return new int[]{x,y};
	}
	
	/**
	 * Simply prints a message informing the user they lost.
	 */
	private static void printLose() {
		System.out.println("You Lose ( *^-^) (*???^???)");
	}
	
	/**
	 * Simply prints a message informing the user they lost.
	 */
	private static void printWin() {
		// Look at these graphics man I don't even need JavaFX
		System.out.println("You Win ???(^???^*)))");		
	}
	
	/**
	 * Asks the user if they want to play. Only way to get true is if
	 * the user inputs the string "yes"
	 * @return true if the user wants to play; false otherwise
	 */
	private static boolean askPlay() {
		System.out.print("Would you like to play? ");
		Scanner in = new Scanner(System.in);
		String choice = in.nextLine();
		//	in.close();
		if(choice.equals("y")) return true; 
		return false;
	}
}
