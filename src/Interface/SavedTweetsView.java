/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import ToolKit.Tools;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import twitter4j.Status;

/**
 *
 * @author gabpa
 */
public class SavedTweetsView {
	
	private final BorderPane root;
	private ScrollPane tweetsContainer;
	private HBox titleContainer, buttonContainer;
	private Button close;
	private Stage stage;
	private Scene scene;
	
	
	public SavedTweetsView() throws FileNotFoundException{
		this.root = new BorderPane();
		setupPane();
		setTweetsResultsPanes(Tools.readSavedTweets());
		setupStage();
	}
	
	private void setupPane(){
		
		titleContainer = new HBox();
		Label titulo = new Label("Saved Tweets");
		titulo.setFont(new Font("Comic Sans",20));
		titleContainer.setPadding(new Insets(5,5,5,5));
		titleContainer.getChildren().add(titulo);
		titleContainer.setAlignment(Pos.CENTER);
		
		tweetsContainer = new ScrollPane();
		tweetsContainer.fitToWidthProperty().setValue(true);
		tweetsContainer.fitToHeightProperty().setValue(true);
		
		buttonContainer = new HBox();
		buttonContainer.setAlignment(Pos.CENTER);
		buttonContainer.setPadding(new Insets(5,5,5,5));
		
		close = new Button("Close");
		close.setPrefSize(80, 40);
		close.setOnAction(e->this.stage.close());
		
		buttonContainer.getChildren().add(close);
		
		root.setTop(titleContainer);
		root.setCenter(tweetsContainer);
		root.setBottom(buttonContainer);
		
	}
	
	
	private void setTweetsResultsPanes(ArrayList<Status> estados){
		
		if(estados.size()>0){
			VBox vbox = new VBox(5);
			vbox.setPadding(new Insets(5,5,5,5));
			int n = 0;
			while(n < 10 && n < estados.size()){
				TweetView tweet = new TweetView(estados.get(n));
				vbox.getChildren().add(tweet.getRoot());
				n++;
			}
			
			this.tweetsContainer.setContent(vbox);	
			
		}else
			Tools.showAlert("No hay informacion que mostrar", "Lo siento", null, 1);
		
	}
	
	private void setupStage(){
		stage = new Stage();
		scene = new Scene(this.root,600,400);
		stage.setScene(scene);
		stage.setTitle("Saved Tweets");
		stage.showAndWait();
	}
}
