/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import ToolKit.Tools;
import classes.MainController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import twitter4j.Status;


public class searchTweetView {
	
	private final BorderPane root;
	private Button search,clear,close;
	private HBox buttonContainer,lowButtonContainer;
	private TextField txt,geolocation;
	private ScrollPane tweetsContainer;
	private Stage stage;
	private Scene scene;
	
	public searchTweetView(){
		root = new BorderPane();
		setupPanes();
		setupStage();
	}
	
	private void setupPanes(){
		
		search = new Button("Search");
		clear = new Button("Clear");
		
		txt = new TextField();
		txt.setPromptText("Escriba algo para buscar");
		txt.setPrefSize(120, 40);
		
		geolocation = new TextField();
		geolocation.setPromptText("Ciudad o Lugar");
		geolocation.setPrefSize(120,40);
		
		search.setPrefSize(80, 40);
		search.setOnAction(e->{
			try {
				searchTweet();
			} catch (IOException ex) {
				Logger.getLogger(searchTweetView.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		
		clear.setPrefSize(80, 40);
		clear.setOnAction(e->{
			txt.clear();
			geolocation.clear();
		});
		
		buttonContainer = new HBox(10);
		buttonContainer.setPadding(new Insets(5,5,5,5));
		buttonContainer.setAlignment(Pos.CENTER);
		buttonContainer.getChildren().addAll(txt,geolocation,search,clear);
		
		close = new Button("Close");
		close.setPrefSize(100,50);
		close.setOnAction(e->this.stage.close());
		
		lowButtonContainer = new HBox(20);
		lowButtonContainer.setAlignment(Pos.CENTER);
		lowButtonContainer.setPadding(new Insets(10,10,10,10));
		lowButtonContainer.getChildren().add(close);
		
		tweetsContainer = new ScrollPane();
		tweetsContainer.fitToWidthProperty().setValue(true);
		tweetsContainer.fitToHeightProperty().setValue(true);
		
		root.setTop(buttonContainer);
		root.setCenter(tweetsContainer);
		root.setBottom(lowButtonContainer);
	}
	
	private void setupStage(){
		stage = new Stage();
		scene = new Scene(this.root,500,340);
		stage.setScene(scene);
		stage.setTitle("Search Tweet");
		stage.showAndWait();
		
	}
	
	private void searchTweet() throws IOException{
		if(this.txt.getText().length() == 0 && 
				this.geolocation.getText().length() ==0)
			return;
		
		if(this.txt.getText().length() == 0 &&
				this.geolocation.getText().length() != 0)
			return;
		
		ArrayList<Status> estados;

		if(this.geolocation.getText().equals("")){
			estados = MainController.tf.search(this.txt.getText());
		}else{
			estados = MainController.tf.search(this.txt.getText(),
			this.geolocation.getText());
		}
		if(estados != null)
			setTweetsResultsPanes(estados);
		
	}
	
	//SE PUEDE REUTILIZAR
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
	
	public BorderPane getRoot(){
		return this.root;
	}
}
