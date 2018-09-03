/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import ToolKit.Tools;
import classes.MainController;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.URLEntity;
import twitter4j.User;

/**
 *
 * @author gabpa
 */
public class TweetView{
	
	private Status status;
	private final User actualUser;
	private final BorderPane root;
	private Button guardar, retuit;
	private HBox buttonContainer,infoContainer;
	private URLEntity[] urls;
	private MediaEntity[] media;
	
	public TweetView(Status estado){
		root = new BorderPane();
		this.status = estado;
		this.actualUser = estado.getUser();
		setupPanes();
	}

	
	public final void setupPanes(){
		root.setPrefSize(260, 160);
		root.setPadding(new Insets(5,5,5,5));
		root.autosize();
		
		setupProfilePic();
		setupBody();
		
	}

	private void setupProfilePic(){
		Pane imageContainer = new Pane();
		imageContainer.setPadding(new Insets(2,2,2,2));
		imageContainer.setPrefSize(50, 50);
		
		User user = status.getUser();
		String profileImageUrl = user.getProfileImageURL();
		ImageView img = new ImageView(new Image(profileImageUrl));
		imageContainer.getChildren().add(img);
		root.setLeft(imageContainer);
		
	}
	
	private void setupBody(){
		
		//URLS EN CASO DE EXISTIR
		urls = this.status.getURLEntities();
		//media en caso de tener
		media = this.status.getMediaEntities();
		
		VBox body = new VBox();
		body.setFillWidth(true);
		body.setPadding(new Insets(2,2,2,2));
		body.setSpacing(5);
		setupTitle(body);
		
		TextArea txt = new TextArea(status.getText());
		txt.setPrefSize(220,80);
		txt.setWrapText(true);
		body.setOnMouseClicked(e->{
			if(urls.length>0){
				Tools.showWeb(urls[0].getExpandedURL());
			}
		});
		
		body.getChildren().add(txt);
		if(media.length>0){
			Pane imageContainer = new Pane();
			imageContainer.setPadding(new Insets(2,2,2,2));
			imageContainer.setPrefSize(80, 80);
			
			String ImageUrl = media[0].getMediaURL();
			ImageView img = new ImageView(new Image(ImageUrl,50,50,false,false));
			
			imageContainer.getChildren().add(img);
			
			body.getChildren().add(imageContainer);
		}
		
		root.setCenter(body);
		
		buttonContainer = new HBox(20);
		buttonContainer.setPadding(new Insets(5,5,5,5));
		buttonContainer.setAlignment(Pos.CENTER);
		
		guardar = new Button("Guardar");
		retuit = new Button("Retweet");
		
		guardar.setPrefSize(80, 30);
		retuit.setPrefSize(80, 30);
		
		guardar.setOnAction(e-> {
			Tools.saveLocalTweet(status);//PRIMERO GUARDO TWEET EN ARRAY LOCAL
			try {
				Tools.saveTweet(MainController.savedTweets);//LUEGO ACTUALIZO ARCHIVO
				//ESTO SE HACE PARA NO PODER GUARDAR TWEETS REPETIDOS
			} catch (IOException ex) {
				Logger.getLogger(TweetView.class.getName()).log(Level.SEVERE, null, ex);
			}
				});
		
		retuit.setOnAction(e->
				{
			try {
				MainController.twitter.retweetStatus(status.getId());
				retuit.setDisable(true);
			} catch (TwitterException ex) {
				Logger.getLogger(TweetView.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		infoContainer = new HBox(20);
		Label l1 = new Label("Favoritos: "+this.status.getFavoriteCount());
		Label l2 = new Label("RTs: "+ this.status.getRetweetCount());
		infoContainer.setPadding(new Insets(5,5,5,5));
		infoContainer.setAlignment(Pos.CENTER);
		infoContainer.getChildren().addAll(l1,l2);
		
		buttonContainer.getChildren().addAll(guardar,retuit);
		VBox v = new VBox(10);
		v.setAlignment(Pos.CENTER);
		v.setPadding(new Insets(5,5,5,5));
		v.getChildren().addAll(infoContainer,buttonContainer);
		root.setBottom(v);
		
		
	}
	
	private void setupTitle(VBox body){
		
		HBox upperB = new HBox();
		upperB.setPadding(new Insets(4,4,4,4));
		upperB.setSpacing(20);
		upperB.setAlignment(Pos.BASELINE_LEFT);
		Label userName = new Label(this.actualUser.getScreenName());
			
		Date date = status.getCreatedAt();
		Date currentDate = new Date();
		long timePublished = Tools.getDateDiff(currentDate, date, TimeUnit.MINUTES);
		
		Label tiempo = new Label();
		tiempo.setText("Hace "+ Long.toString(timePublished) + "min.");
		
		upperB.getChildren().addAll(userName,tiempo);
		
		body.getChildren().add(upperB);
	}
	
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public BorderPane getRoot() {
		return root;
	}
	
	public User getUser(){
		return this.actualUser;
	}
	
}
