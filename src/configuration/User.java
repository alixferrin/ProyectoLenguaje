/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import java.io.Serializable;
import twitter4j.auth.AccessToken;

/**
 *
 * @author gabpa
 */
public class User implements Serializable {
	
	private final String username;
	private final String password;
	private AccessToken tokenDeAcceso;

	public User(String username, String password, AccessToken tokenDeAcceso) {
		this.username = username;
		this.password = password;
		this.tokenDeAcceso = tokenDeAcceso;
	}

	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}

	public AccessToken getTokenDeAcceso() {
		return tokenDeAcceso;
	}

	public void setTokenDeAcceso(AccessToken tokenDeAcceso) {
		this.tokenDeAcceso = tokenDeAcceso;
	}
	
	
}
