
package classes;

import Interface.MainLoginView;
import Interface.SceneOrganizer;
import ToolKit.Tools;
import configuration.User;
import java.util.ArrayList;

/**
 *
 * @author gabpa
 */
public class LoginController {
	
	private final MainLoginView loginView;
	private User userActual;
	
	public LoginController(MainLoginView loginView){
		this.loginView = loginView;
	}
	
	public void validateCredentials(){
		ArrayList<User> systemUsers = this.loginView.getControladorPrincipal().getUsers();
		String user = this.loginView.getUsuario().getText().toUpperCase();
		String password = this.loginView.getContraseña().getText().toUpperCase();
		
		if(systemUsers.isEmpty()){
			Tools.showAlert("NO EXISTE NINGUN USUARIO REGISTRADO", "ERROR", "Access denied.",0);
			return;
		}
		
		for(User u: systemUsers){
			if(u.getUsername().equals(user)){
				if(u.getPassword().equals(password)){
					Tools.showAlert("User identified", "SUCCESS", "Permission for Access granted.",1);
					userActual = u;
					setCredentials();
					SceneOrganizer.setupMainMenuScene();
					return;
				}
			}
		}
		Tools.showAlert("User doesn't exist", "ERROR", "Access denied.",0);
	}
	
	public void setCredentials(){
		this.loginView.getControladorPrincipal().getTwitter().
				setOAuthAccessToken(userActual.getTokenDeAcceso());
	}
}
