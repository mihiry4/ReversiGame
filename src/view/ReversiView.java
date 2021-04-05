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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.ReversiModel;

/**
 * @author Shreyas Khandekar
 * @author Mihir Yadav
 */
@SuppressWarnings("deprecation")
public class ReversiView extends Application implements Observer {

	private ReversiModel model;
	private ReversiController controller;
	
	
	public ReversiView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane pane = new BorderPane();
		GridPane masterGridPane = new GridPane();
	    pane.setCenter(masterGridPane);
	    masterGridPane.setStyle("-fx-background-color: green");
		
	    for(int i = 0;i<8;i++) {
	    	for(int j = 0; j<8;j++) {
	    		masterGridPane.add(addStackPane(), i, j);
	    	}
	    }
	    masterGridPane.setPadding(new Insets(40, 10, 40, 16));
	    
	     // stage setup
	     Scene scene = new Scene(pane, 400, 430);
		 stage.setScene(scene);
		 stage.setTitle("Reversi");
		 stage.show();
	}
	
	private StackPane addStackPane() {
		//Rectangle(double x, double y, double width, double height)
		Rectangle s = new Rectangle(0,0,45,45);
		Circle c = new Circle(20);
		StackPane stackPane = new StackPane();
		s.setStyle("-fx-fill: green; -fx-stroke: black; -fx-stroke-width: 1;");
		stackPane.getChildren().add(s);
		stackPane.getChildren().add(c);
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
