
package ToolKit;

import classes.MainController;
import classes.ProgrammedTweet;
import configuration.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import resources.CONSTANTS;
import twitter4j.Status;

public class Tools {
	
	
	public static void saveTweet(ArrayList<Status> tweets) throws FileNotFoundException, IOException{
		
		FileOutputStream fos = new FileOutputStream(CONSTANTS.savedTweetsTxt);
		try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(tweets);
			oos.flush();
		}finally{
			fos.close();
		}
	
	}
	
	public static ArrayList<Status> readSavedTweets() throws FileNotFoundException {
		
		ArrayList<Status> estados = new ArrayList<>();
		try{
			File file = checkFileExists(CONSTANTS.savedTweetsTxt);
			if(file.length()>0){
				FileInputStream fis = new  FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				estados = (ArrayList<Status>) ois.readObject();
				ois.close();
				fis.close();
			}
		}catch(FileNotFoundException e1){
			System.out.println("File couldn't be found");
		}catch(IOException e2){
			System.out.println("IOException while reading saved tweets");
		}catch(ClassNotFoundException e3){
			System.out.println("Class not found exception while reading tweets");
		}
		return estados;
	}
	
	public static void saveUser(ArrayList<User> users) throws IOException{
		
		FileOutputStream fos = new FileOutputStream(CONSTANTS.savedUsersTxt);
		try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(users);
			oos.flush();
		}finally{
			fos.close();
		}
	
	}
	
	public static ArrayList<User> readUsers() throws IOException{ 
		ArrayList<User> users = new ArrayList<>();

		try{
			File file = checkFileExists(CONSTANTS.savedUsersTxt);
			if(file.length()>0){
				FileInputStream fis = new  FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				users = (ArrayList<User>) ois.readObject();
				ois.close();
				fis.close();
			}
		}catch(FileNotFoundException e1){
			System.out.println("File couldn't be found");
		}catch(IOException e2){
			System.out.println("IOException while reading saved users");
		}catch(ClassNotFoundException e3){
			System.out.println("Class not found exception while reading users");
		}
		return users;
	}
	
	public static ArrayList<ProgrammedTweet> readProgTweets(){
		ArrayList<ProgrammedTweet> tweets = new ArrayList<>();
		
		try{
			File file = checkFileExists(CONSTANTS.programmedTweetsTxt);
			if(file.length()>0){
				FileInputStream fis = new  FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				tweets = (ArrayList<ProgrammedTweet>) ois.readObject();
				ois.close();
				fis.close();
			}
		}catch(FileNotFoundException e1){
			System.out.println("File couldn't be found");
		}catch(IOException e2){
			System.out.println("IOException while reading saved users");
		}catch(ClassNotFoundException e3){
			System.out.println("Class not found exception while reading users");
		}
		return tweets;
	}
	
	public static void programTweet(ArrayList<ProgrammedTweet> tweets) throws IOException{
		
		FileOutputStream fos = new FileOutputStream(CONSTANTS.programmedTweetsTxt);
		try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(tweets);
			oos.flush();
		}finally{
			fos.close();
		}
	
	}
	
	public static String readLastTweet(){

		String line = null;
		try{
			File file = new File(CONSTANTS.lastTwetTxt);
			if(file.length()>0){
				FileReader filereader = new FileReader(file);
				BufferedReader bf = new BufferedReader(filereader);
				line = bf.readLine();
				bf.close();
				return line;
			}
		}catch(Exception e){
			
		}
		return line;
	}
	
	public static void writeLastTweet(String tuit){
		try{
			FileWriter fw = new FileWriter(CONSTANTS.lastTwetTxt);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(tuit);
			
			bw.close();
			
		}catch(Exception e){
			
		}
	}
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date1.getTime() - date2.getTime();
		return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	
	public static void showAlert(String infoMessage, String titleBar, String headMessage,int i){
		Alert alert;
		if(i == 1)
			alert = new Alert(AlertType.INFORMATION);
		else{
			alert = new Alert(AlertType.ERROR);
		}
        alert.setTitle(titleBar);
        alert.setHeaderText(headMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();

	}
	
	public static File checkFileExists(String path) throws IOException{
		File file = new File(path);
		file.createNewFile();
		return file;
	}
	
	public static void showWeb(String url){
		Stage webStage = new Stage();
		WebViewPane web = new WebViewPane(url);
		Scene scene = new Scene(web,800,600);
		webStage.setScene(scene);
		webStage.setTitle("Web View");
		webStage.showAndWait();
		webStage.setOnCloseRequest(e->web.getEng().load(null));
	}
	
	public static void saveLocalTweet(Status tuit){
		ArrayList<Status> estados = MainController.savedTweets;
		if(!estados.contains(tuit))
			estados.add(tuit);
		
	}
	
	public static void programLocalTweet(ProgrammedTweet tweet){
		ArrayList<ProgrammedTweet> tuits = MainController.programmedTweets;
		if(!tuits.contains(tweet))
			tuits.add(tweet);
	}
}
