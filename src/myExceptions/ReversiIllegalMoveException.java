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
