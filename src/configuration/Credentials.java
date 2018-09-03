/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author gabpa
 */
public class Credentials {
	private String consumerKey;
	private String consumerSecret;
	private ConfigurationBuilder cb;
	
	public Credentials(String key, String secret){
		this.consumerKey = key;
		this.consumerSecret = secret;
		this.initializeConfig();
		
	}
	
	private void initializeConfig(){
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
        .setOAuthConsumerKey(consumerKey)
        .setOAuthConsumerSecret(consumerSecret)
        .setOAuthAccessToken(null)
        .setOAuthAccessTokenSecret(null);
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public ConfigurationBuilder getCb() {
		return cb;
	}

	public void setCb(ConfigurationBuilder cb) {
		this.cb = cb;
	}
	
	
}
