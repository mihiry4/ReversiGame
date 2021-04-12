package testSuite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.util.Pair;


import org.junit.jupiter.api.Test;

import controller.ReversiController;
import model.ReversiBoard;
import model.ReversiModel;
import myExceptions.ReversiGameOverException;
import myExceptions.ReversiIllegalMoveException;

//  start here
public class ReversiTest {
	@Test
	void testGetAllMoves(){
		
		ReversiModel model = new ReversiModel();
		
		ReversiController controller = new ReversiController(model);
		assertEquals(controller.getLegalMoves('w').size(),4);
		System.out.println(controller.getLegalMoves('w').keySet());
	}
	
	@Test
	void testgetWinner1(){
		
		ReversiModel model = new ReversiModel();
		ReversiController controller = new ReversiController(model);
		assertTrue(controller.getWinner().equals("d"));
		
		File f= new File("save_game.dat");           //file to be delete  
		f.delete();
		ReversiModel model2 = new ReversiModel();
		FileInputStream fin;
		try {
			fin = new FileInputStream("save_game_for_testing.dat");
			ObjectInputStream ois = new ObjectInputStream(fin);
			ReversiBoard board = (ReversiBoard) ois.readObject();
			ois.close();
			FileOutputStream fout = new FileOutputStream("save_game.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			controller.writeToFile(oos);
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		model.setPiece('b', 0, 0);
		assertEquals(controller.playerWin(), 0);
		model.setPiece('b', 0, 1);
		assertEquals(controller.playerWin(), 1);
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
	/*
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
			System.out.println("IllegalMove exception why");
		} catch (ReversiGameOverException e) {
			System.out.println("Game over exception");
		}
		controller.computerTurn();
	}
	*/
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
		ReversiController controller = new ReversiController(model);
		model.setPiece('w', 5, 3);
		controller.getLegalMoves('w');
		controller.getMovesInAllDirections('w', 3, 3);
		controller.flipColorsInAllDirections('w',5,3);
		assertThrows(ReversiIllegalMoveException.class, 
				() -> { 
					controller.humanTurn(0, -1);
					}
		);

		model = new ReversiModel();
		ReversiController controller2 = new ReversiController(model);
		controller2.getLegalMoves('x');
		controller2.computerTurn();
		model.setPiece(' ', 3, 3);
		model.setPiece(' ', 4, 4);
		model.setPiece(' ', 3, 4);
		model.setPiece(' ', 4, 3);
		controller2.computerTurn();
		assertThrows(ReversiGameOverException.class, 
				() -> { 
					controller2.humanTurn(0, 0);
					}
		);
		
		
		model.setPiece('w', 0, 0);
		model.setPiece('b', 1, 1);
		controller2.computerTurn();
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
	/*
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
	}*/
	
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
	
	@Test
	void flipColorTest() {
		
		// Lto R
		ReversiModel model = new ReversiModel();
		ReversiController con = new ReversiController(model);
		con.flipColorsInAllDirections('w', 7, 0);
		model.setPiece(' ', 4, 0);
		con.flipColorsInAllDirections('w', 3, 0);
		model.setPiece('w', 4, 0);
		con.flipColorsInAllDirections('w', 3, 0);
		model.setPiece('a', 5, 0);
		con.flipColorsInAllDirections('w', 4, 0);
		model.setPiece('w', 6, 0);
		con.flipColorsInAllDirections('w', 4, 0);
		
		// R to L
		model = new ReversiModel();
		con = new ReversiController(model);
		con.flipColorsInAllDirections('w', 0, 0);
		model.setPiece(' ', 4, 0);
		con.flipColorsInAllDirections('w', 5, 0);
		model.setPiece('w', 4, 0);
		con.flipColorsInAllDirections('w', 5, 0);
		model.setPiece('a', 3, 0);
		con.flipColorsInAllDirections('w', 4, 0);
		
		// U to D
		model = new ReversiModel();
		con = new ReversiController(model);
		con.flipColorsInAllDirections('w', 0, 7);
		model.setPiece(' ', 0, 4);
		con.flipColorsInAllDirections('w', 0, 3);
		model.setPiece('w', 0, 4);
		con.flipColorsInAllDirections('w', 0, 3);
		model.setPiece('a', 0, 5);
		con.flipColorsInAllDirections('w', 0, 4);
		model.setPiece('w', 0, 6);
		con.flipColorsInAllDirections('w', 0, 4);
		
		
		// D to U
		model = new ReversiModel();
		con = new ReversiController(model);
		con.flipColorsInAllDirections('w', 0, 0);
		model.setPiece(' ', 0, 4);
		con.flipColorsInAllDirections('w', 0, 5);
		model.setPiece('w', 0, 4);
		con.flipColorsInAllDirections('w', 0, 5);
		model.setPiece('a', 0, 3);
		con.flipColorsInAllDirections('w', 0, 4);	
		model.setPiece('b', 0, 5);
		con.flipColorsInAllDirections('w', 0, 6);	
		
		// LB to RT
		model = new ReversiModel();
		con = new ReversiController(model);
		con.flipColorsInAllDirections('w', 0, 0);
		con.flipColorsInAllDirections('w', 1, 7);
		model.setPiece(' ', 1, 4);
		con.flipColorsInAllDirections('w', 0, 5);		
		model.setPiece('w', 1, 4);
		con.flipColorsInAllDirections('w', 0, 5);
		model.setPiece('b', 1, 4);
		con.flipColorsInAllDirections('w', 0, 5);
		model.setPiece('a', 2, 3);
		con.flipColorsInAllDirections('w', 0, 5);
		model.setPiece('w', 2, 3);
		con.flipColorsInAllDirections('w', 0, 5);
		
		// RT to LB
		model = new ReversiModel();
		con = new ReversiController(model);
		con.flipColorsInAllDirections('w', 7, 7);
		con.flipColorsInAllDirections('w', 0, 0);
		model.setPiece(' ', 6, 1);
		con.flipColorsInAllDirections('w', 7, 0);		
		model.setPiece('w', 6, 1);
		con.flipColorsInAllDirections('w', 7, 0);
		model.setPiece('b', 6, 1);
		con.flipColorsInAllDirections('w', 7, 0);
		model.setPiece('a', 5, 2);
		con.flipColorsInAllDirections('w', 7, 0);
		model.setPiece('w', 5, 2);
		con.flipColorsInAllDirections('w', 7, 0);
		
		
		// LT to RB
		model = new ReversiModel();
		con = new ReversiController(model);
		con.flipColorsInAllDirections('w', 7, 7);
		con.flipColorsInAllDirections('w', 0, 0);
		model.setPiece(' ', 1, 1);
		con.flipColorsInAllDirections('w', 0, 0);		
		model.setPiece('w', 1, 1);
		con.flipColorsInAllDirections('w', 0, 0);
		model.setPiece('b', 1, 1);
		con.flipColorsInAllDirections('w', 0, 0);
		model.setPiece('a', 1, 1);
		con.flipColorsInAllDirections('w', 0, 0);
		model.setPiece('w', 2, 2);
		con.flipColorsInAllDirections('w', 0, 0);

		// RB to LT
		model = new ReversiModel();
		con = new ReversiController(model);
		con.flipColorsInAllDirections('w', 7, 7);
		con.flipColorsInAllDirections('w', 0, 0);
		model.setPiece(' ', 6, 6);
		con.flipColorsInAllDirections('w', 7, 7);		
		model.setPiece('w', 6, 6);
		con.flipColorsInAllDirections('w', 7, 7);
		model.setPiece('b', 6, 6);
		con.flipColorsInAllDirections('w', 7, 7);
		model.setPiece('a', 6, 6);
		con.flipColorsInAllDirections('w', 7, 7);
		model.setPiece('w', 5, 5);
		con.flipColorsInAllDirections('w', 7, 7);
	}
	
	@Test
	void getMovesTest(){
		
		// Lto R
				ReversiModel model = new ReversiModel();
				ReversiController con = new ReversiController(model);
				con.getMovesInAllDirections('w', 7, 0);
				model.setPiece(' ', 4, 0);
				con.getMovesInAllDirections('w', 3, 0);
				model.setPiece('w', 4, 0);
				con.getMovesInAllDirections('w', 3, 0);
				model.setPiece('a', 5, 0);
				con.getMovesInAllDirections('w', 4, 0);
				model.setPiece('w', 6, 0);
				con.getMovesInAllDirections('w', 4, 0);
				con.getMovesInAllDirections('w', 7, 0);
				model.setPiece(' ', 4, 0);
				con.getMovesInAllDirections('w', 3, 0);
				model.setPiece('w', 4, 0);
				con.getMovesInAllDirections('w', 3, 0);
				model.setPiece('a', 5, 0);
				con.getMovesInAllDirections('w', 4, 0);
				model.setPiece('w', 6, 0);
				con.getMovesInAllDirections('w', 4, 0);
				con.getMovesInAllDirections('w', 4, 0);
		
				model = new ReversiModel();
				con = new ReversiController(model);
				model.setPiece('w', 0, 0);
				model.setPiece('b', 1, 0);
				model.setPiece('b', 2, 0);
				con.getLegalMoves('w');
				con.getMovesInAllDirections('w', 3, 0);
				
				// R to L
				model = new ReversiModel();
				con = new ReversiController(model);
				con.getMovesInAllDirections('w', 0, 0);
				model.setPiece(' ', 4, 0);
				con.getMovesInAllDirections('w', 5, 0);
				model.setPiece('w', 4, 0);
				con.getMovesInAllDirections('w', 5, 0);
				model.setPiece('a', 3, 0);
				con.getMovesInAllDirections('w', 4, 0);
				con.getMovesInAllDirections('w', 0, 0);
				model.setPiece(' ', 4, 0);
				con.getMovesInAllDirections('w', 5, 0);
				model.setPiece('w', 4, 0);
				con.getMovesInAllDirections('w', 5, 0);
				model.setPiece('a', 3, 0);
				con.getMovesInAllDirections('w', 4, 0);
				
				// U to D
				model = new ReversiModel();
				con = new ReversiController(model);
				con.getMovesInAllDirections('w', 0, 7);
				model.setPiece(' ', 0, 4);
				con.getMovesInAllDirections('w', 0, 3);
				model.setPiece('w', 0, 4);
				con.getMovesInAllDirections('w', 0, 3);
				model.setPiece('a', 0, 5);
				con.getMovesInAllDirections('w', 0, 4);
				model.setPiece('w', 0, 6);
				con.getMovesInAllDirections('w', 0, 4);
				con.getMovesInAllDirections('w', 0, 7);
				model.setPiece(' ', 0, 4);
				con.getMovesInAllDirections('w', 0, 3);
				model.setPiece('w', 0, 4);
				con.getMovesInAllDirections('w', 0, 3);
				model.setPiece('a', 0, 5);
				con.getMovesInAllDirections('w', 0, 4);
				model.setPiece('w', 0, 6);
				con.getMovesInAllDirections('w', 0, 4);
				
				
				// D to U
				model = new ReversiModel();
				con = new ReversiController(model);
				con.getMovesInAllDirections('w', 0, 0);
				model.setPiece(' ', 0, 4);
				con.getMovesInAllDirections('w', 0, 5);
				model.setPiece('w', 0, 4);
				con.getMovesInAllDirections('w', 0, 5);
				model.setPiece('a', 0, 3);
				con.getMovesInAllDirections('w', 0, 4);	
				model.setPiece('b', 0, 5);
				con.getMovesInAllDirections('w', 0, 6);	
				con.getMovesInAllDirections('w', 0, 0);
				model.setPiece(' ', 0, 4);
				con.getMovesInAllDirections('w', 0, 5);
				model.setPiece('w', 0, 4);
				con.getMovesInAllDirections('w', 0, 5);
				model.setPiece('a', 0, 3);
				con.getMovesInAllDirections('w', 0, 4);	
				model.setPiece('b', 0, 5);
				con.getMovesInAllDirections('w', 0, 6);	
				
				// LB to RT
				model = new ReversiModel();
				con = new ReversiController(model);
				con.getMovesInAllDirections('w', 0, 0);
				con.getMovesInAllDirections('w', 1, 7);
				model.setPiece(' ', 1, 4);
				con.getMovesInAllDirections('w', 0, 5);		
				model.setPiece('w', 1, 4);
				con.getMovesInAllDirections('w', 0, 5);
				model.setPiece('b', 1, 4);
				con.getMovesInAllDirections('w', 0, 5);
				model.setPiece('a', 2, 3);
				con.getMovesInAllDirections('w', 0, 5);
				model.setPiece('w', 2, 3);
				con.getMovesInAllDirections('w', 0, 5);
				con.getMovesInAllDirections('w', 0, 0);
				con.getMovesInAllDirections('w', 1, 7);
				model.setPiece(' ', 1, 4);
				con.getMovesInAllDirections('w', 0, 5);		
				model.setPiece('w', 1, 4);
				con.getMovesInAllDirections('w', 0, 5);
				model.setPiece('b', 1, 4);
				con.getMovesInAllDirections('w', 0, 5);
				model.setPiece('a', 2, 3);
				con.getMovesInAllDirections('w', 0, 5);
				model.setPiece('w', 2, 3);
				con.getMovesInAllDirections('w', 0, 5);
				
				// RT to LB
				model = new ReversiModel();
				con = new ReversiController(model);
				con.getMovesInAllDirections('w', 7, 7);
				con.getMovesInAllDirections('w', 0, 0);
				model.setPiece(' ', 6, 1);
				con.getMovesInAllDirections('w', 7, 0);		
				model.setPiece('w', 6, 1);
				con.getMovesInAllDirections('w', 7, 0);
				model.setPiece('b', 6, 1);
				con.getMovesInAllDirections('w', 7, 0);
				model.setPiece('a', 5, 2);
				con.getMovesInAllDirections('w', 7, 0);
				model.setPiece('w', 5, 2);
				con.getMovesInAllDirections('w', 7, 0);
				con.getMovesInAllDirections('w', 7, 7);
				con.getMovesInAllDirections('w', 0, 0);
				model.setPiece(' ', 6, 1);
				con.getMovesInAllDirections('w', 7, 0);		
				model.setPiece('w', 6, 1);
				con.getMovesInAllDirections('w', 7, 0);
				model.setPiece('b', 6, 1);
				con.getMovesInAllDirections('w', 7, 0);
				model.setPiece('a', 5, 2);
				con.getMovesInAllDirections('w', 7, 0);
				model.setPiece('w', 5, 2);
				con.getMovesInAllDirections('w', 7, 0);
				
				
				// LT to RB
				model = new ReversiModel();
				con = new ReversiController(model);
				con.getMovesInAllDirections('w', 7, 7);
				con.getMovesInAllDirections('w', 0, 0);
				model.setPiece(' ', 1, 1);
				con.getMovesInAllDirections('w', 0, 0);		
				model.setPiece('w', 1, 1);
				con.getMovesInAllDirections('w', 0, 0);
				model.setPiece('b', 1, 1);
				con.getMovesInAllDirections('w', 0, 0);
				model.setPiece('a', 1, 1);
				con.getMovesInAllDirections('w', 0, 0);
				model.setPiece('w', 2, 2);
				con.getMovesInAllDirections('w', 0, 0);
				con.getMovesInAllDirections('w', 7, 7);
				con.getMovesInAllDirections('w', 0, 0);
				model.setPiece(' ', 1, 1);
				con.getMovesInAllDirections('w', 0, 0);		
				model.setPiece('w', 1, 1);
				con.getMovesInAllDirections('w', 0, 0);
				model.setPiece('b', 1, 1);
				con.getMovesInAllDirections('w', 0, 0);
				model.setPiece('a', 1, 1);
				con.getMovesInAllDirections('w', 0, 0);
				model.setPiece('w', 2, 2);
				con.getMovesInAllDirections('w', 0, 0);

				// RB to LT
				model = new ReversiModel();
				con = new ReversiController(model);
				con.getMovesInAllDirections('w', 7, 7);
				con.getMovesInAllDirections('w', 0, 0);
				model.setPiece(' ', 6, 6);
				con.getMovesInAllDirections('w', 7, 7);		
				model.setPiece('w', 6, 6);
				con.getMovesInAllDirections('w', 7, 7);
				model.setPiece('b', 6, 6);
				con.getMovesInAllDirections('w', 7, 7);
				model.setPiece('a', 6, 6);
				con.getMovesInAllDirections('w', 7, 7);
				model.setPiece('w', 5, 5);
				con.getMovesInAllDirections('w', 7, 7);
				con.getMovesInAllDirections('w', 7, 7);
				con.getMovesInAllDirections('w', 0, 0);
				model.setPiece(' ', 6, 6);
				con.getMovesInAllDirections('w', 7, 7);		
				model.setPiece('w', 6, 6);
				con.getMovesInAllDirections('w', 7, 7);
				model.setPiece('b', 6, 6);
				con.getMovesInAllDirections('w', 7, 7);
				model.setPiece('a', 6, 6);
				con.getMovesInAllDirections('w', 7, 7);
				model.setPiece('w', 5, 5);
				con.getMovesInAllDirections('w', 7, 7);

	}
	
}
