package application;
//The main program
//responsible for launching the window with GUI arrangement (.fxml) and control (Controller.java)
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		Application.launch(args); //launches the application
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("myScene.fxml")); //launches GUI instructions
		primaryStage.setTitle("NetAna");  //name of the window
		primaryStage.setScene(new Scene(root, 600, 600)); //size of the window
		primaryStage.show(); //show window
	}
}