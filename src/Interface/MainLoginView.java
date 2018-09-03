
package Interface;

import classes.LoginController;
import classes.MainController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import twitter4j.TwitterException;

/**
 *
 * @author gabpa
 */
public class MainLoginView {
	
	private VBox root;
	private HBox contenedorUsuario;
    private HBox contenedorContraseña;
	private HBox contenedorBotones;
    private Button login,register, close;
    private TextField usuario;
    private PasswordField contraseña;
    private Label user;
    private Label passw;
	private final MainController controlador;
	private final LoginController loginControlador;
	
	public MainLoginView(MainController control) throws TwitterException, IOException{
		root = new VBox(15);	
		controlador = control;
		loginControlador = new LoginController(this);
		setupPanes();
		setupButtons();
	}
	
	private void setupPanes(){
		contenedorUsuario = new HBox(20);
        user = new Label("User:");
        usuario = new TextField();
        usuario.setPrefSize(200, 10);
        contenedorUsuario.setAlignment(Pos.CENTER);
        contenedorUsuario.getChildren().addAll(user,usuario);
        
        contenedorContraseña = new HBox(20);
        passw = new Label("Password:");
        contraseña = new PasswordField();
        contraseña.setPrefSize(200, 10);
        contenedorContraseña.setAlignment(Pos.CENTER);
        contenedorContraseña.getChildren().addAll(passw,contraseña);
        
        login = new Button("Login");
        login.setPrefSize(200, 10);
		
		register = new Button("Register");
		register.setPrefSize(200, 10);
		contenedorBotones = new HBox(login,register);
		contenedorBotones.setSpacing(20);
		contenedorBotones.setPadding(new Insets(5,5,5,5));
		contenedorBotones.setAlignment(Pos.CENTER);
		
		close = new Button("Cerrar");
		close.setPrefSize(200, 10);
		
		root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: honeydew");
        root.getChildren().addAll(contenedorUsuario,contenedorContraseña,contenedorBotones, close);
	}
	
	private void setupButtons(){
		login.setOnAction(e->this.loginControlador.validateCredentials());
		register.setOnAction(e->{
			try {
				RegisterView registerView = new RegisterView(this.controlador);
			} catch (TwitterException | IOException ex) {
				Logger.getLogger(MainLoginView.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		close.setOnAction(e->System.exit(0));
	}
	
	public VBox getRoot() {
        return this.root;  
    }

    public void setRoot(VBox root) {
        this.root = root;
    }
	
	public MainController getControladorPrincipal(){
		return this.controlador;
	}

	public TextField getUsuario() {
		return usuario;
	}

	public PasswordField getContraseña() {
		return contraseña;
	}
	
}
