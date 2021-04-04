/**
 * 
 */
package view;

import java.util.Observable;
import java.util.Observer;

import controller.ReversiController;
import javafx.application.Application;
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
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
