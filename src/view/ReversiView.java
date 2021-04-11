/**
 * File: Reversi.java
 * @author Shreyas Khandekar
 * @author Mihir Yadav
 * Purpose: GUI View of Reversi using javafx
 */
package view;

import java.io.File;
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
 * Class: ReversiView
 * @author Shreyas Khandekar
 * @author Mihir Yadav
 * Purpose: Play Reversi in GUI View using java fx
 */
@SuppressWarnings("deprecation")
public class ReversiView extends Application implements Observer {

	/**
	 * The representation of the Reversi model
	 * It contains the board
	 */
	private ReversiModel model;
	
	/**
	 * Controller for the game.
	 */
	private ReversiController controller;
	
	/*
	 * This is the top level board pane on the
	 * Javafx window.
	 */
	private BorderPane bp;
	
	/**
	 * Constructor which creates the model and controllers
	 * and also adds this view as the observer of the model
	 */
	public ReversiView() {
		model = new ReversiModel();
		// Construct the controller, passing in the model
		controller = new ReversiController(model);
		model.addObserver(this);
	}

	/**
	 * Start function which sets up the GUI window
	 * to play Reversi.
	 * @param stage is the stage which is displayed
	 */
	@Override
	public void start(Stage stage) throws Exception {
		
		bp = new BorderPane();
		// Top  Menu
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
        MenuItem newgame = new MenuItem("New Game");
        newgame.setOnAction(event -> {
        	createNewGame();
        });
        menuFile.getItems().addAll(newgame);
        menuBar.getMenus().addAll(menuFile);
		bp.setTop(menuBar);
		
		
		// Main Grid
		GridPane masterGridPane = new GridPane();
	    bp.setCenter(masterGridPane);
	    masterGridPane.setStyle("-fx-background-color: green");
		
	    for(int j = 0;j<8;j++) {
	    	for(int i = 0; i<8;i++) {
	    		masterGridPane.add(addStackPane(i, j), i, j);
	    	}
	    }
	    masterGridPane.setPadding(new Insets(8, 8, 8, 8));
	    
	    //setInitialBoard(); 
	    controller.updateView();
	    
	    
	    // stage setup
	    Scene scene = new Scene(bp, 384, 424);
		stage.setScene(scene);
		stage.setTitle("Reversi");
		saveGameOnClose(stage);
		stage.show();
		
	}
	
	/**
	 * This function creates a new game by
	 * deleting the old save file and setting up
	 * a new model and controller.
	 */
	private void createNewGame() {
		//System.out.println("New Game");
		File f= new File("save_game.dat");           //file to be delete  
		f.delete();
		model = new ReversiModel();
		controller = new ReversiController(model);
		model.addObserver(this);
		controller.updateView();
	}

	/**
	 * When the window is closed, we save the game
	 * into save_game.dat by saving the board object
	 * @param stage the stage in which the game 
	 * 			is being played
	 */
	private void saveGameOnClose(Stage stage) {
		stage.setOnCloseRequest(event -> {
			if(!controller.isGameOver()) {
				try {
					FileOutputStream fout = new FileOutputStream("save_game.dat");
					ObjectOutputStream oos = new ObjectOutputStream(fout);
					controller.writeToFile(oos);
				} catch (IOException e) {
					// catch block
					Alert a = new Alert(Alert.AlertType.INFORMATION);
					a.setTitle("Message");
					a.setContentText("AAAAAAAAAAAAAAAHHHHHHHHHH");
					a.setHeaderText("Unable to Save game");
					a.showAndWait();
				}
			}
		});

	}
	
	/**
	 * This function sets up each of the boxes within the Reversi
	 * Grid.
	 * The circle is originally transparent and the on click 
	 * listener contains the location of the circle in that box
	 * @param i row of the box
	 * @param j column of the box 
	 * @return the stack pane containing the circle and box
	 */
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
				controller.humanTurn(i, j);
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


	/**
	 * When the game is over we delete the save_file and
	 * display an alert with the result of the game
	 */
	private void gameOverMessage() {
		File f= new File("save_game.dat");           //file to be delete  
		f.delete();
		Alert a = new Alert(Alert.AlertType.INFORMATION);
		a.setTitle("Message");
		a.setContentText(getGameResult());
		a.setHeaderText("Game Over");
		a.showAndWait();
		return;
		
	}

	/**
	 * Returns a message which conveys the result of the 
	 * game if either white or black wins or a draw
	 * @return A string with the result
	 */
	private String getGameResult() {
		String winner = controller.getWinner();
		if(winner.equals("w"))
			return "White wins";
		if(winner.equals("b"))
			return "Black wins";
		else
			return "Game Draw";
	}

	/**
	 * Updates the view based on the board
	 * in the model. We update each box color of the
	 * board as well as the counts at the bottom
	 * @param o Observable ReversiModel
	 * @param arg This is always the board
	 */
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
	
	/**
	 * We use this to get a Node from the Grid Pane
	 * in the specified row and col of the Grid Pane
	 * @param gp The top level GridPane
	 * @param row Row of the node which we want
	 * @param col Col of the node which we want
	 * @return The node we want from row and col
	 */
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
