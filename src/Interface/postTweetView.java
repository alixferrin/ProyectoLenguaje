/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import ToolKit.Tools;
import classes.MainController;
import classes.ProgrammedTweet;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import twitter4j.TwitterException;

/**
 *
 * @author gabpa
 */
public class postTweetView {
	
	private final VBox root;
	private HBox labelContainer;
	private HBox buttonContainer, programContainer;
	private HBox textContainer;
	private TextArea text;
	private TextField minutos;
	private Button post, program, close;
	private Label title;
	private Stage postStage;
	private Scene postScene;
	private final MainController controller;
	
	public postTweetView(MainController controlador){
		this.controller = controlador;
		root = new VBox(10);
		setupPanes();
		setupStage();
	}
	
	private void setupPanes(){
		labelContainer = new HBox(10);
		buttonContainer = new HBox(20);
		textContainer = new HBox(10);
		programContainer = new HBox(20);
		
		text = new TextArea();
		text.setPrefSize(300, 120);
		text.setPromptText("Write a tweet here...");
		
		minutos = new TextField();
		minutos.setPrefSize(60, 40);
		
		textContainer.setPadding(new Insets(10,10,10,10));
		labelContainer.setPadding(new Insets(10,10,10,10));
		buttonContainer.setPadding(new Insets(10,10,10,10));
		
		title = new Label("Post a tweet!");
		title.setFont(new Font("Comic Sans",20));
		labelContainer.getChildren().add(title);
		labelContainer.setAlignment(Pos.CENTER);
		
		post = new Button("Post");
		program = new Button("Program");
		close = new Button("Close");
		
		post.setPrefSize(80, 40);
		close.setPrefSize(80, 40);
		program.setPrefSize(80, 40);
		
		buttonContainer.getChildren().addAll(post,close);
		buttonContainer.setAlignment(Pos.CENTER);
		
		programContainer.setAlignment(Pos.CENTER);
		programContainer.setPadding(new Insets(5,5,5,5));
		programContainer.getChildren().addAll(program,minutos);
		
		textContainer.getChildren().add(text);
		textContainer.setAlignment(Pos.CENTER);
		root.getChildren().addAll(labelContainer,textContainer,buttonContainer,programContainer);
		
		post.setOnAction(e->{
				updateStatus(text.getText());
				postStage.close();
		});
		program.setOnAction(e->{
			try {
				programTweet(text.getText());
				postStage.close();
			} catch (IOException ex) {
				Logger.getLogger(postTweetView.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		close.setOnAction(e-> {
			postStage.close();
			try {
				Tools.programTweet(MainController.programmedTweets);
			} catch (IOException ex) {
				Logger.getLogger(postTweetView.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		
	}
	
	private void setupStage(){
		postStage = new Stage();
		postScene = new Scene(this.root, 400, 300);
		postStage.setScene(postScene);
		postStage.setTitle("Update Status");
		postStage.showAndWait();
	}
	
	private void programTweet(String tweet) throws IOException{
		if(tweet.length()>0 && minutos.getText().length()>0){
			try{
				Integer ti = Integer.parseInt(minutos.getText());
				Long tiempo = new Long(ti*60*1000);
				if(ti<5 || ti>30){
					Tools.showAlert("Time values not correct.", "Error", null, 0);
					return;
				}
				ProgrammedTweet progTuit = new ProgrammedTweet(tweet,tiempo);
				Tools.programLocalTweet(progTuit);////PRIMERO GUARDO EL TWEET PROGRAMADO EN ARRAYLIST LOCAL
				
				Tools.programTweet(MainController.programmedTweets);//LUEGO ACTUALIZO EL ARRAYLIST DEL ARCHIVO
				//ESTO SE HACE PARA QUE NO SE PUEDA AGREGAR TUITS PROGRAMADOS REPETIDOS
			}catch(NumberFormatException e){
				Tools.showAlert("Verify your input fields.", "Error", null, 0);
			}
		}else{
			Tools.showAlert("Escriba un tweet y un tiempo en minutos primero", "Error", null, 0);
		}
	}
	
	private void updateStatus(String tweet){
		
		if( tweet.length()>0 && !tweet.equals(Tools.readLastTweet())){
			try {
				this.controller.getTwitter().updateStatus(tweet);
			} catch (TwitterException ex) {
				Tools.showAlert("No Internet Connection", "Error", null, 0);
				return;
			}
			
			Tools.writeLastTweet(tweet);
			Tools.showAlert(null, "Success", "Tweet Sent!", 1);
		}else{
			Tools.showAlert(null,"Error","No se puede enviar tweet.",0);
		}
	}
	
}
