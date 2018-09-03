
package classes;

import Threads.ProgrammedTweetThread;
import ToolKit.Tools;
import configuration.Credentials;
import configuration.User;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import resources.CONSTANTS;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public final class MainController{
	
	public static ArrayList<Status> savedTweets;
	public static ArrayList<User> users;
	public static ArrayList<ProgrammedTweet> programmedTweets;
	public static Twitter twitter;
	public static TwitterFunctions tf;
	private ProgrammedTweetThread pthread;
	
	public MainController() throws FileNotFoundException, IOException{
		savedTweets = Tools.readSavedTweets();
		users = Tools.readUsers();
		programmedTweets = Tools.readProgTweets();
		initialize();
		
	}
	
	private Twitter buildTwitter(){
		Credentials credenciales = new Credentials(CONSTANTS.consumerKey,CONSTANTS.consumerSecret);
		return new TwitterFactory(credenciales.getCb().build()).getInstance();
	}
	
	public ArrayList<User> getUsers(){
		return MainController.users;
	}
	
	public ArrayList<Status> getTweets(){
		return MainController.savedTweets;
	}
	
	public Twitter getTwitter(){
		return MainController.twitter;
	}

	public static ArrayList<ProgrammedTweet> getProgrammedTweets() {
		return MainController.programmedTweets;
	}
	
	private void initialize(){
		twitter = buildTwitter();
		tf = new TwitterFunctions(twitter);
		
		pthread = new ProgrammedTweetThread();
		pthread.start();
	}
	
}
