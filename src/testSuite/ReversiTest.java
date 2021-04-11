package testSuite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import javafx.util.Pair;


import org.junit.jupiter.api.Test;

import controller.ReversiController;
import model.ReversiModel;

//  start here
public class ReversiTest {
	@Test
	void testGetAllMoves(){
		ReversiModel model = new ReversiModel();
		model.setPiece('W', 3, 3);
		model.setPiece('W', 4, 4);
		model.setPiece('B', 4, 3);
		model.setPiece('B', 3, 4);
		ReversiController controller = new ReversiController(model);
		assertEquals(controller.getLegalMoves('W').size(),4);
		System.out.println(controller.getLegalMoves('W').keySet());
	}
	
	@Test
	void testgetWinner1(){
		ReversiModel model = new ReversiModel();
		model.setPiece('w', 3, 3);
		model.setPiece('w', 4, 4);
		model.setPiece('b', 4, 3);
		model.setPiece('b', 3, 4);
		ReversiController controller = new ReversiController(model);
		assertTrue(controller.getWinner().equals("d"));
	}
	
	@Test
	void testgetWinner2(){
		ReversiModel model = new ReversiModel();
		model.setPiece('w', 3, 3);
		model.setPiece('w', 4, 4);
		model.setPiece('b', 4, 3);
		model.setPiece('b', 3, 4);
		model.setPiece('w', 5, 3);
		ReversiController controller = new ReversiController(model);
		String res = controller.getWinner();
		assertTrue(res.equals("w")); 
	}
	
	@Test
	void testgetWinner3(){
		ReversiModel model = new ReversiModel();
		model.setPiece('w', 3, 3);
		model.setPiece('w', 4, 4);
		model.setPiece('b', 4, 3);
		model.setPiece('b', 3, 4);
		model.setPiece('b', 4, 5);
		ReversiController controller = new ReversiController(model);
		assertTrue(controller.getWinner().equals("b"));
	}
	
	@Test
	void testIsGameOver() {
		ReversiModel model = new ReversiModel();
		model.setPiece('w', 3, 3);
		model.setPiece('w', 4, 4);
		model.setPiece('b', 4, 3);
		model.setPiece('b', 3, 4);
		ReversiController controller = new ReversiController(model);
		assertFalse(controller.isGameOver());
	}
	
	@Test
	void testGetBoard() {
		ReversiModel model = new ReversiModel();
		model.setPiece('w', 3, 3);
		model.setPiece('w', 4, 4);
		model.setPiece('b', 4, 3);
		model.setPiece('b', 3, 4);
		ReversiController controller = new ReversiController(model);
		String s = "  |   |   |   |   |   |   |   | \n"
				+ "--------------------------------\n"
				+ "  |   |   |   |   |   |   |   | \n"
				+ "--------------------------------\n"
				+ "  |   |   |   |   |   |   |   | \n"
				+ "--------------------------------\n"
				+ "  |   |   | w | b |   |   |   | \n"
				+ "--------------------------------\n"
				+ "  |   |   | b | w |   |   |   | \n"
				+ "--------------------------------\n"
				+ "  |   |   |   |   |   |   |   | \n"
				+ "--------------------------------\n"
				+ "  |   |   |   |   |   |   |   | \n"
				+ "--------------------------------\n"
				+ "  |   |   |   |   |   |   |   | \n"
				+ "--------------------------------\n";
		assertTrue(controller.getBoard().equals(s));
	}
	
	@Test
	void testPlayerWin() {
		ReversiModel model = new ReversiModel();
		model.setPiece('w', 3, 3);
		model.setPiece('w', 4, 4);
		model.setPiece('b', 4, 3);
		model.setPiece('b', 3, 4);
		model.setPiece('w', 5, 3);
		ReversiController controller = new ReversiController(model);
		int i = controller.playerWin();
		assertEquals(i,-1);
	}
	
	@Test
	void testIsLegalMove() {
		ReversiModel model = new ReversiModel();
		model.setPiece('w', 3, 3);
		model.setPiece('w', 4, 4);
		model.setPiece('b', 4, 3);
		model.setPiece('b', 3, 4);
		ReversiController controller = new ReversiController(model);
		Pair p1 = new Pair(6,3);
		Pair p2 = new Pair(0,0);
		Pair p3 = new Pair(2,4);
		Pair p4 = new Pair(5,3);
		assertFalse(controller.isLegalMove(p1));
		assertFalse(controller.isLegalMove(p2));
		assertTrue(controller.isLegalMove(p3)); 
		assertTrue(controller.isLegalMove(p4)); 
	}
}
