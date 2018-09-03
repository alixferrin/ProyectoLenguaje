
package classes;

import Interface.SceneOrganizer;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import twitter4j.TwitterException;

public class TwitterDesktop extends Application {
	
	@Override
	public void start(Stage primaryStage) throws TwitterException, IOException{
		
		SceneOrganizer sceneOrganizer = new SceneOrganizer(primaryStage);
		sceneOrganizer.getStage().setTitle("Main Menu");
		sceneOrganizer.getStage().setResizable(false);
		sceneOrganizer.getStage().show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
