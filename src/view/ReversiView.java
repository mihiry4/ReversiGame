/**
 * 
 */
package view;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;

import controller.ReversiController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.ReversiBoard;
import model.ReversiModel;
import myExceptions.ReversiGameOverException;
import myExceptions.ReversiIllegalMoveException;

/**
 * @author Shreyas Khandekar
 * @author Mihir Yadav
 */
@SuppressWarnings("deprecation")
public class ReversiView extends Application implements Observer {

	private ReversiModel model;
	private ReversiController controller;
	private BorderPane bp;
	
	public ReversiView() {
		model = new ReversiModel();
		// Construct the controller, passing in the model
		controller = new ReversiController(model);
		model.addObserver(this);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		bp = new BorderPane();
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
        MenuItem newgame = new MenuItem("New Game");
        menuFile.getItems().addAll(newgame);
        menuBar.getMenus().addAll(menuFile);
		bp.setTop(menuBar);
		GridPane masterGridPane = new GridPane();
	    bp.setCenter(masterGridPane);
	    masterGridPane.setStyle("-fx-background-color: green");
		
	    for(int j = 0;j<8;j++) {
	    	for(int i = 0; i<8;i++) {
	    		masterGridPane.add(addStackPane(i, j), i, j);
	    	}
	    }
	    masterGridPane.setPadding(new Insets(8, 8, 8, 8));
	    
	    setInitialColors(); 
	    
	    Label label = new Label("White: 2 - Black: 2");
	    bp.setBottom(label);
	    
	    // stage setup
	    Scene scene = new Scene(bp, 384, 424);
		stage.setScene(scene);
		stage.setTitle("Reversi");
		
		stage.show();
		
	}
	
	private void saveGameOnClose(Stage stage) {
		stage.setOnCloseRequest(event -> {
			try {
				FileOutputStream fout = new FileOutputStream("save_game.dat");
				ObjectOutputStream oos = new ObjectOutputStream(fout);
				controller.writeToFile(oos);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Alert a = new Alert(Alert.AlertType.INFORMATION);
				a.setTitle("Message");
				a.setContentText("AAAAAAAAAAAAAAAHHHHHHHHHH");
				a.setHeaderText("Unable to Save game");
				a.showAndWait();
			}
		});

	}
	
	private void setInitialColors() {
		GridPane gp = (GridPane) bp.getCenter();
		StackPane sp = (StackPane) getNodeByRowColumn(gp, 3, 3);
	    Circle circle = (Circle) sp.getChildren().get(1);
	    circle.setFill(Color.WHITE);		
	    
	    sp = (StackPane) getNodeByRowColumn(gp, 4, 4);
	    circle = (Circle) sp.getChildren().get(1);
	    circle.setFill(Color.WHITE);
	    
	    sp = (StackPane) getNodeByRowColumn(gp, 3, 4);
	    circle = (Circle) sp.getChildren().get(1);
	    circle.setFill(Color.BLACK);	
	    
	    sp = (StackPane) getNodeByRowColumn(gp, 4, 3);
	    circle = (Circle) sp.getChildren().get(1);
	    circle.setFill(Color.BLACK);		
	}

	private StackPane addStackPane(int i, int j) {
		//Rectangle(double x, double y, double width, double height)
		Rectangle s = new Rectangle(0,0,45,45);
		Circle c = new Circle(20);
		c.setFill(Color.GREEN);
		StackPane stackPane = new StackPane();
		s.setStyle("-fx-fill: green; -fx-stroke: black; -fx-stroke-width: 1;");
		stackPane.getChildren().add(s);
		stackPane.getChildren().add(c);
		c.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent me) -> {
			try {
				controller.playMove(i, j);
				if(controller.isGameOver())
					gameOverMessage();
			} catch (ReversiIllegalMoveException e) {
				Alert a = new Alert(Alert.AlertType.INFORMATION);
				a.setTitle("Message");
				a.setContentText("AAAAAAAAAAAAAAAHHHHHHHHHH");
				a.setHeaderText("Illegal Move");
				a.showAndWait();
				return;
			} catch(ReversiGameOverException e) {
				gameOverMessage();
			}
			//c.setFill(Color.WHITE);
		});
		return stackPane;
		
	}


	private void gameOverMessage() {
		Alert a = new Alert(Alert.AlertType.INFORMATION);
		a.setTitle("Message");
		a.setContentText(getGameResult());
		a.setHeaderText("Game Over");
		a.showAndWait();
		return;
		
	}

	private String getGameResult() {
		String winner = controller.getWinner();
		if(winner.equals("w"))
			return "White wins";
		if(winner.equals("b"))
			return "Black wins";
		else
			return "Game Draw";
	}

	private Paint getPaint(String s) {
		Paint paint1 = Paint.valueOf(s);
		return paint1;
	}

	@Override
	public void update(Observable o, Object arg) {
		ReversiBoard board = (ReversiBoard) arg;
		GridPane gp = (GridPane) bp.getCenter();
		for(int j = 0;j<8;j++) {
	    	for(int i = 0; i<8;i++) {
	    		Color color; 
	    		if(board.getValue(i, j) == 'w')
	    			color = Color.WHITE;
	    		else if(board.getValue(i, j) == 'b')
	    			color = Color.BLACK;
	    		else
	    			color = Color.GREEN;
	    		StackPane sp = (StackPane) getNodeByRowColumn(gp, j, i);
	    		Circle circle = (Circle) sp.getChildren().get(1);
	    		circle.setFill(color);
	    		//System.out.println("C");
	    	}
	    }
		int[] count = board.getCount();
		Label l = new Label("White: " + count[1] + " - Black: " + count[0]);
		bp.setBottom(l);
	}
	
	private Node getNodeByRowColumn(GridPane gp, int row, int col) {
		
		ObservableList<Node> childrenList = gp.getChildren();
		
		for(Node n : childrenList) {
			if(GridPane.getRowIndex(n)==row && GridPane.getColumnIndex(n)==col) {
				//System.out.println("");
				return n;
			}
		}
		
		return null;
	}

}
