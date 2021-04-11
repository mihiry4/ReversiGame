/**
 * File: ReversiIllegalMoveException.java
 * @author Shreyas Khandekar
 * Purpose: Create an exception for illegal move in reversi
 */
package myExceptions;

/**
 * Class: ReversiIllegalMoveException
 * @author Shreyas Khandekar
 * Purpose: Thrown when user tries to play a move which is illegal
 */
public class ReversiIllegalMoveException extends Exception {

	/**
	 * @param message
	 */
	public ReversiIllegalMoveException(String message) {
		super(message);
	}

	/**
	 * String representation of the exception
	 */
	public String toString() {
		return "Your move at this location is not acceptable: " + getMessage();
	}



}
