/**
 * 
 */
package view;

import java.util.Observable;
import java.util.Observer;

import controller.ReversiController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
	
	
	public ReversiView() {
		model = new ReversiModel();
		// Construct the controller, passing in the model
		controller = new ReversiController(model);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		BorderPane pane = new BorderPane();
		GridPane masterGridPane = new GridPane();
	    pane.setCenter(masterGridPane);
	    masterGridPane.setStyle("-fx-background-color: green");
		
	    for(int i = 0;i<8;i++) {
	    	for(int j = 0; j<8;j++) {
	    		masterGridPane.add(addStackPane(i, j), i, j);
	    	}
	    }
	    masterGridPane.setPadding(new Insets(40, 10, 40, 16));
	    
	    // stage setup
	    Scene scene = new Scene(pane, 400, 430);
		stage.setScene(scene);
		stage.setTitle("Reversi");
		stage.show();
	}
	
	private StackPane addStackPane(int i, int j) {
		//Rectangle(double x, double y, double width, double height)
		Rectangle s = new Rectangle(0,0,45,45);
		Circle c = new Circle(20);
		StackPane stackPane = new StackPane();
		s.setStyle("-fx-fill: green; -fx-stroke: black; -fx-stroke-width: 1;");
		stackPane.getChildren().add(s);
		stackPane.getChildren().add(c);
		c.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent me) -> {
			try {
				controller.playMove(i, j);
			} catch (ReversiIllegalMoveException | ReversiGameOverException e) {
				Alert a = new Alert(Alert.AlertType.INFORMATION);
				a.setTitle("Message");
				a.setContentText("AAAAAAAAAAAAAAAHHHHHHHHHH");
				a.setHeaderText("Illegal Move");
				a.showAndWait();
				return;
			}
			c.setFill(Color.WHITE);
		});
		return stackPane;
		
	}
	
	private Paint getPaint(String s) {
		Paint paint1 = Paint.valueOf(s);
		return paint1;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
