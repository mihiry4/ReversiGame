/**
 * File: ReversiGameOverException.java
 * @author Shreyas Khandekar
 * Purpose: Create an exception for illegal move in reversi
 */
package myExceptions;

/**
 * Class: ReversiGameOverException
 * @author Shreyas Khandekar
 * Purpose: Thrown when user tries to play a move when the game has already ended
 */
public class ReversiGameOverException extends Exception {

	/**
	 * @param message
	 */
	public ReversiGameOverException(String message) {
		super(message);
	}

	/**
	 * String representation of the exception
	 */
	public String toString() {
		return "Game is over you cannot play more";
	}



}
