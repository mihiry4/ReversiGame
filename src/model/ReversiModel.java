/**
 * 
 */
package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javafx.util.Pair;

/**
 * @author Shreyas Khandekar
 *
 */
@SuppressWarnings("deprecation")
public class ReversiModel extends Observable {

	Map<Pair<Integer, Integer>, Integer> map = new HashMap<Pair<Integer, Integer>, Integer>();
	/**
	 * 
	 */
	public ReversiModel() {
		
	}
	
	

}
