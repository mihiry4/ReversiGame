/**
 * 
 */
package view;

import java.util.HashMap;
import java.util.Map;

import javafx.util.Pair;

/**
 * @author Shreyas Khandekar
 *
 */
public class Reversi {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<Pair<Integer, Integer>, Integer> map = new HashMap<Pair<Integer, Integer>, Integer>();

		Pair<Integer, Integer> one = new Pair<Integer, Integer>(1, 2);
		Pair<Integer, Integer> two = new Pair<Integer, Integer>(1, 2);
		map.put(one, 10);
		map.put(two, 20);
		System.out.println(one.equals(two));
		for(Pair<Integer, Integer> p : map.keySet()) {
			System.out.println(map.get(p));
		}

	}

}
