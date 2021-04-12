package testSuite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javafx.util.Pair;


import org.junit.jupiter.api.Test;

import controller.ReversiController;
import model.ReversiModel;
import myExceptions.ReversiGameOverException;
import myExceptions.ReversiIllegalMoveException;

//  start here
public class ReversiTest {
	@Test
	void testGetAllMoves(){
		ReversiModel model = new ReversiModel();

		ReversiController controller = new ReversiController(model);
		assertEquals(controller.getLegalMoves('W').size(),4);
		System.out.println(controller.getLegalMoves('W').keySet());
	}
	
	@Test
	void testgetWinner1(){
		ReversiModel model = new ReversiModel();
		ReversiController controller = new ReversiController(model);
		assertTrue(controller.getWinner().equals("d"));
	}
	
	@Test
	void testgetWinner2(){
		ReversiModel model = new ReversiModel();

		model.setPiece('w', 5, 3);
		ReversiController controller = new ReversiController(model);
		String res = controller.getWinner();
		assertTrue(res.equals("w")); 
	}
	
	@Test
	void testgetWinner3(){
		ReversiModel model = new ReversiModel();
		model.setPiece('b', 4, 5);
		ReversiController controller = new ReversiController(model);
		assertTrue(controller.getWinner().equals("b"));
	}
	
	@Test
	void testIsGameOver() {
		ReversiModel model = new ReversiModel();

		ReversiController controller = new ReversiController(model);
		assertFalse(controller.isGameOver());
	}
	
	@Test
	void testGetBoard() {
		ReversiModel model = new ReversiModel();

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
		model.setPiece('w', 5, 3);
		ReversiController controller = new ReversiController(model);
		int i = controller.playerWin();
		assertEquals(i,-1);
	}
	
	@Test
	void testIsLegalMove() {
		ReversiModel model = new ReversiModel();
		
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
	
	@Test
	void testUpdateView(){
		ReversiModel model = new ReversiModel();
		ReversiController controller = new ReversiController(model);
		controller.updateView();
	}
	
	@Test
	void testHumanTurn() {
		ReversiModel model = new ReversiModel();
		ReversiController controller = new ReversiController(model);
		try {
			controller.humanTurn(5, 3);
		} catch (ReversiIllegalMoveException | ReversiGameOverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			controller.humanTurn(5, 3);
		} catch (ReversiIllegalMoveException e) {
			System.out.println("IllegalMove exception");
		} catch (ReversiGameOverException e) {
			System.out.println("Game over exception");
		}
		controller.computerTurn();
	}
	
	@Test
	void testWriteToFile() {
		ReversiModel model = new ReversiModel();
		ReversiController controller = new ReversiController(model);
		ObjectOutputStream oos;
		try {
			FileOutputStream fout = new FileOutputStream("hello.dat");
			oos = new ObjectOutputStream(fout);
			controller.writeToFile(oos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	@Test
	void testComputerTurn() {
		ReversiController controller = new ReversiController();
		controller.computerTurn();
	}
	
	@Test
	void testPlayBestMove() {
		ReversiController controller = new ReversiController();
		controller.playBestMove();
	}
	
	@Test
	void testGetLegalMoves() {
		ReversiModel model = new ReversiModel();
		model.setPiece('w', 5, 3);
		ReversiController controller = new ReversiController(model);
		controller.getLegalMoves('w');
		controller.getMovesInAllDirections('w', 3, 3);
		controller.flipColorsInAllDirections('w',5,3);
	}
	
	
	@Test 
	void testConsecutiveTurns1() {
		ReversiModel model = new ReversiModel();
		model.setPiece('b', 5, 4);
		model.setPiece('b', 3, 5);
		model.setPiece('b', 2, 4);
		model.setPiece('b', 5, 3);
		model.setPiece('b', 4, 2);
		model.setPiece('b', 5, 5);
		model.setPiece('b', 6, 4);
		model.setPiece('b', 4, 5);
		model.setPiece('b', 4, 6);
		ReversiController controller = new ReversiController(model);
		controller.computerTurn();
	}
	
	@Test 
	void testConsecutiveTurns2() {
		
		ReversiModel model = new ReversiModel();
		model.setPiece('w', 5, 4);
		model.setPiece('w', 3, 5);
		model.setPiece('w', 2, 4);
		model.setPiece('w', 5, 3);
		model.setPiece('w', 4, 2);
		model.setPiece('w', 5, 5);
		model.setPiece('w', 6, 4);
		model.setPiece('w', 4, 5);
		model.setPiece('w', 4, 6);
		ReversiController controller = new ReversiController(model);
		controller.computerTurn();
	}
	
	@Test 
	void testOnlyOneTurn() {
		ReversiModel model = new ReversiModel();
		model.setPiece('w', 3, 4);
		model.setPiece('w', 4, 3);
		model.setPiece('w', 5, 3);
		model.setPiece('w', 6, 3);
		ReversiController controller = new ReversiController(model);
		controller.computerTurn();
	}
	
	@Test 
	void testLToR() {
		ReversiModel model = new ReversiModel();
		ReversiController controller = new ReversiController(model);
		try {
			controller.humanTurn(5, 3);
		} catch (ReversiIllegalMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReversiGameOverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		controller.computerTurn();
	}
	
	@Test 
	void testLBToRT() {
		ReversiModel model1 = new ReversiModel();
		model1.setPiece('w', 2, 5);
		ReversiController controller = new ReversiController(model1);
		try {
			controller.humanTurn(5, 2);
		} catch (ReversiIllegalMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReversiGameOverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		controller.computerTurn();
	}
	
	@Test 
	void testRBToLT() {
		ReversiModel model1 = new ReversiModel();
		model1.setPiece('b', 5, 5);
		ReversiController controller = new ReversiController(model1);
		controller.computerTurn();
	}
	
}
