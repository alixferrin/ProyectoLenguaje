
package Interface;

import ToolKit.Tools;
import classes.MainController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import resources.CONSTANTS;
import twitter4j.TwitterException;

/**
 *
 * @author gabpa
 */
public final class SceneOrganizer {
	
	private static Stage mainStage;
	private static Scene loginScene,mainMenuScene;
	private static MainController controladorPrincipal;
	
	public SceneOrganizer(Stage primaryStage) throws TwitterException, IOException{
		mainStage = primaryStage;
		controladorPrincipal = new MainController();
		setupLoginScene();
		
		mainStage.setOnCloseRequest((WindowEvent e) -> {
			try {
				guardarInfo();
			} catch (IOException ex) {
				Logger.getLogger(SceneOrganizer.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
	}
	
	public Stage getStage(){
		return SceneOrganizer.mainStage;
	}
	
	public static void guardarInfo() throws IOException{
		Tools.saveTweet(controladorPrincipal.getTweets());
		Tools.saveUser(controladorPrincipal.getUsers());
		Tools.programTweet(MainController.getProgrammedTweets());
	}
	
	public static void setupLoginScene() throws TwitterException, IOException{
		loginScene = new Scene(new MainLoginView(controladorPrincipal).getRoot(),
				CONSTANTS.MAX_WIDTH, CONSTANTS.MAX_HEIGTH);
		mainStage.setTitle("Login Menu");
		mainStage.setScene(loginScene);
	}
	
	public static void setupMainMenuScene(){
		mainMenuScene = new Scene(new MainMenuView(controladorPrincipal).getRoot(),
			CONSTANTS.MAX_WIDTH, CONSTANTS.MAX_HEIGTH);
		mainStage.setTitle("Main Menu");
		mainStage.setScene(mainMenuScene);
	}
	
	
	
}
