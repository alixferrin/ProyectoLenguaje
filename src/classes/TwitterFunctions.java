/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import ToolKit.GeoRequests;
import ToolKit.Location;
import ToolKit.Tools;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import resources.CONSTANTS;
import twitter4j.GeoLocation;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 *
 * @author gabpa
 */
public class TwitterFunctions {
	
	private final Twitter twitter;
	
	public TwitterFunctions(Twitter twitt){
		this.twitter = twitt;
	}
	
	public List<Status> showTimeline(boolean flag){
		try{
			User user = twitter.verifyCredentials();
			List<Status> statuses = twitter.getHomeTimeline(new Paging(2,CONSTANTS.maxNumberOfTweets));
			return statuses;
		
		}catch(TwitterException te){
			flag = false;
			Tools.showAlert("No internet Connection", "Error", null, 0);
		}
		return null;
	}
	
	public ArrayList<Status> search(String query) throws IOException{
		return searchTweet(query,null);
	}
	
	public ArrayList<Status> search(String query,String place) throws IOException{
		return searchTweet(query,place);
	}
	
	private ArrayList<Status> searchTweet(String query,String place) throws IOException{
		ArrayList<Status> estados = new ArrayList<>();
		try{
			Location location = GeoRequests.requestLocation(place);
			Query qr = new Query(query);
			if(location!= null)
				qr.geoCode(new GeoLocation(location.getLatitud(),location.getLongitud()),
						CONSTANTS.searchRadius, "km");
			
			qr.count(10);
			QueryResult result;
			do {
				result = twitter.search(qr);
				List<Status> tweets = result.getTweets();	
				tweets.forEach((tweet) -> {
					estados.add(tweet);
				});	
			} while ((qr = result.nextQuery()) != null);
			
		}catch(TwitterException te){
			Tools.showAlert("No internet Connection", "Error", null, 0);
			return null;
		}catch(ParserConfigurationException pe){
			System.out.println("Error at parsing configuration.\n" + pe.toString());
		}catch(SAXException sax){
			System.out.println(sax.toString());
		}
		
		return estados;
	}
	
}
