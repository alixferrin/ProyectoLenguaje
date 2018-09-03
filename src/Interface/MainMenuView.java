/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import classes.MainController;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import resources.CONSTANTS;
import twitter4j.TwitterException;

/**
 *
 * @author gabpa
 */
public class MainMenuView {
	
	private final MainController controlador;
	private final VBox root;
	private Button searchTweets,signOut;
	
	public MainMenuView(MainController control){
		root = new VBox(20);
		this.controlador = control;
		setupPanes();
	}
	
	private void setupPanes(){
		
		root.setPadding(new Insets(20,20,20,20));
		searchTweets = new Button("Search Tweets");
		signOut = new Button("Sign Out");
		
		searchTweets.setPrefSize(CONSTANTS.prefButtonWidthMenu, CONSTANTS.prefButtonHeightMenu);
		signOut.setPrefSize(CONSTANTS.prefButtonWidthMenu, CONSTANTS.prefButtonHeightMenu);
		
		root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: honeydew");
		root.getChildren().addAll(searchTweets,signOut);
		
		searchTweets.setOnAction(e-> new searchTweetView());
		
		
		signOut.setOnAction(e->{
			try {
				SceneOrganizer.setupLoginScene();
			} catch (TwitterException | IOException ex) {
				Logger.getLogger(MainMenuView.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		
	}
	
	
	public VBox getRoot(){
		return this.root;
	}
}
