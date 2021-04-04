/**
 * File: ReversiIllegalMoveException.java
 * @author Shreyas Khandekar
 * Purpose: Create an exception for illegal move in reversi
 */
package myExceptions;

/**
 * @author Shreyas Khandekar
 *
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
