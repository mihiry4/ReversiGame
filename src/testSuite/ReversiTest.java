package testSuite;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
		System.out.print(controller.getLegalMoves('W').keySet());
	}
}
