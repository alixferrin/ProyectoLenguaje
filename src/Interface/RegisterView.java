/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import ToolKit.Tools;
import classes.MainController;
import configuration.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 *
 * @author gabpa
 */
public final class RegisterView {
	
	private final VBox root;
	private HBox contenedorUsuario;
	private HBox contenedorContraseña;
	private HBox contenedorBotones;
	private TextField usuarioText;
	private TextField contraseñaText;
	private TextField pinText;
	private Stage registerStage;
	private Scene registerScene;
	private Button register, close;
	private Label userName, passName;
	private final MainController mainController;
	private AccessToken token;
	private RequestToken reqToken;
	
	public RegisterView(MainController controller) throws TwitterException, IOException{
		this.mainController = controller;
		root = new VBox(20);
		startRequest();
		setupPanes();
		setupStages();
	}
	
	private void setupPanes(){
		
		root.setPadding(new Insets(20,20,20,20));
		contenedorUsuario = new HBox(20);
		contenedorContraseña = new HBox(20);
		contenedorBotones = new HBox(20);
		
		usuarioText = new TextField();
		contraseñaText = new  TextField();
		
		contenedorUsuario.setAlignment(Pos.CENTER);
		contenedorContraseña.setAlignment(Pos.CENTER);
		contenedorBotones.setAlignment(Pos.CENTER);
		
		contenedorUsuario.setPrefSize(260, 100);
		contenedorContraseña.setPrefSize(260, 100);
		contenedorBotones.setPrefSize(260, 100);
		
		contenedorUsuario.setPadding(new Insets(10,10,10,10));
		contenedorContraseña.setPadding(new Insets(10,10,10,10));
		contenedorBotones.setPadding(new Insets(10,10,10,10));
		
		userName = new Label("Username:");
		passName = new Label("Password:");
		
		register = new Button("Register");
		close = new Button("Close");
		
		register.setPrefSize(200, 100);
		close.setPrefSize(200, 100);
		
		contenedorUsuario.getChildren().addAll(userName, usuarioText);
		contenedorContraseña.getChildren().addAll(passName, contraseñaText);
		contenedorBotones.getChildren().addAll(register,close);
		
		register.setOnAction(e->{
			try {
				registerUser();
			} catch (IOException ex) {
				Logger.getLogger(RegisterView.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		
		close.setOnAction(e->closeWindow());
		
		root.setStyle("-fx-background-color: honeydew");
        root.getChildren().addAll(contenedorUsuario,contenedorContraseña,contenedorBotones);
		
	}
	
	private void setupStages(){
		registerStage = new Stage();
		registerScene = new Scene(this.root,400,200);
		registerStage.setTitle("Register Menu");
		registerStage.setScene(registerScene);
		registerStage.show();	
	}
	
	private void accessBox(){
		
		Stage accessTokenStage = new Stage();
		accessTokenStage.centerOnScreen();
		
		VBox rootAccess = new VBox(10);
		HBox buttonContainer = new HBox(20);
		pinText = new TextField();
		pinText.setPromptText("Write PIN code in here.");
		pinText.setPrefSize(100, 60);
		
		Button aceptar = new Button("OK");
		Button cerrar = new Button("CLOSE");
		buttonContainer.setPadding(new Insets(5,5,5,5));
		
		aceptar.setPrefSize(60, 30);
		cerrar.setPrefSize(60, 30);
		buttonContainer.getChildren().addAll(aceptar,cerrar);
		
		rootAccess.getChildren().addAll(pinText,buttonContainer);
		
		aceptar.setOnAction(e->validatePin(accessTokenStage));
		cerrar.setOnAction(e-> accessTokenStage.close());
		
		showPinStage(rootAccess,accessTokenStage);
		
	}
	
	private void showPinStage(VBox rootAccess,Stage stage){
		Stage accessTokenStage = stage;
		Scene accessTokenScene = new Scene(rootAccess, 300, 150);
		accessTokenStage.setTitle("Access Token");
		accessTokenStage.setScene(accessTokenScene);
		accessTokenStage.showAndWait();
		
	}
	
	private void validatePin(Stage stage){
		String pin = pinText.getText();
		
		while(token == null){
			try{
				if(pin.length() > 0){
				  token = mainController.getTwitter().getOAuthAccessToken(reqToken, pin);
				  stage.close();
				  
				}else{		//ESTO SE LANZA EN CASO DE QUE HAYAS APLASTADO OK SIN HABER INGRESADO UN PIN
					Tools.showAlert("No dejes el campo pin vacio.", 
					   "Pin Received", "Error", 0);
					pinText.clear();
				}
			}catch(TwitterException e){		//ESTO SE LANZA EN CASO DE QUE EL PIN SEA INCORRECTO
				System.out.println("PIN INCORRECTO.");
				pinText.clear();
			}
		}
	}
	
	
	public  void startRequest() throws TwitterException, IOException{
		mainController.getTwitter().setOAuthAccessToken(null);
	
		reqToken = mainController.getTwitter().getOAuthRequestToken();
		token = null;
		
		String url = reqToken.getAuthenticationURL();
		Tools.showWeb(url);
		
		accessBox();
	}
	
	private void registerUser() throws IOException{
		String user = usuarioText.getText();
		String contra = contraseñaText.getText();
		if(user.length()==0 || contra.length() == 0){
			Tools.showAlert("NO DEJES CAMPOS VACIOS", "ERROR", null, 1);
			usuarioText.clear();
			contraseñaText.clear();
			return;
		}
		
		User nuevoUsuario = new User(user.toUpperCase(),contra.toUpperCase(),this.token);
		if(MainController.users.contains(nuevoUsuario)){
			Tools.showAlert("Por favor utilice otro usuario y contraseña", "Error","Usuario ya existe", 0);
			usuarioText.clear();
			contraseñaText.clear();
			return;
		}
		this.mainController.getUsers().add(nuevoUsuario);
		Tools.saveUser(this.mainController.getUsers());
		registerStage.close();
		SceneOrganizer.setupMainMenuScene();
		
	}
	
	
	private void closeWindow(){
		usuarioText.clear();
		contraseñaText.clear();
		registerStage.close();
	}
	public VBox getRoot(){
		return this.root;
	}
	
}
