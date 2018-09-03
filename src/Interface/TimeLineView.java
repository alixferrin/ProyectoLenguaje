/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Threads.TimeLineThread;
import ToolKit.Tools;
import classes.MainController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import twitter4j.TwitterException;
import twitter4j.User;

public class TimeLineView {
	
	private final BorderPane root;
	private Button close,show;
	private ScrollPane pane;
	private Stage stage;
	private Scene scene;
	private HBox titleContainer, buttonContainer;
	private Label title;
	private TimeLineThread t;
	
	public TimeLineView() throws TwitterException{
		this.root = new BorderPane();
		setupPanes();
	}
	
	private void setupPanes(){
		User user;
		try {
			user = MainController.twitter.verifyCredentials();
		} catch (TwitterException ex) {
			Tools.showAlert("No internet Connection", "Error", null, 0);
			return;
		}
		
		titleContainer = new HBox();
		titleContainer.setPadding(new Insets(5,5,5,5));
		title = new Label("Showing @" + user.getScreenName() + "'s home timeline");
		title.setFont(new Font("Comic Sans",20));
		titleContainer.getChildren().add(title);
		root.setTop(titleContainer);
		
		pane = new ScrollPane();
		pane.setPadding(new Insets(20,20,20,20));
		pane.setFitToWidth(true);
		pane.setFitToHeight(true);
		root.setCenter(pane);
		
		show = new Button("Show");
		show.setPrefSize(80,40);
		show.setOnAction(e->{
			t = new TimeLineThread(this.pane);
			t.start();
		});
			
		close = new Button("Close");
		close.setPrefSize(80, 40);
		close.setOnAction(e->{
			t.flag = false;
			this.stage.close();
		});
		
		buttonContainer = new HBox(20);
		buttonContainer.setAlignment(Pos.CENTER);
		buttonContainer.setPadding(new Insets(10,10,10,10));
		
		buttonContainer.getChildren().addAll(show,close);
		root.setBottom(buttonContainer);
		
		setupStage();
	}
	
	private void setupStage(){
		
		stage = new Stage();
		scene = new Scene(this.root, 600,400);
		stage.setScene(scene);
		stage.setTitle("Timeline");
		stage.showAndWait();
	}
	
}
