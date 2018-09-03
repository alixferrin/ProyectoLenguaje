/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import ToolKit.Tools;
import classes.MainController;
import classes.ProgrammedTweet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.TwitterException;

/**
 *
 * @author gabpa
 */
public class ProgrammedTweetThread extends Thread{
	
	private boolean flag = true;

	@Override
	public void run(){
		
		try {
			Thread.sleep(60000); //ESPERAMOS 1 MINUTO HASTA QUE HAYAN LOGEADO
		} catch (InterruptedException ex) {
			Logger.getLogger(ProgrammedTweetThread.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		while(flag){

			ArrayList<ProgrammedTweet> tuits = MainController.programmedTweets;
			Date currentDate = new Date();
			
			for(ListIterator<ProgrammedTweet> it = tuits.listIterator() ; it.hasNext();){
				ProgrammedTweet tuit = it.next();
				
				if(tuit.getTime()<= currentDate.getTime()){
					try {
						MainController.twitter.updateStatus(tuit.getTuit());
						it.remove();
						Tools.programTweet(MainController.programmedTweets);
						System.out.println("SE ENVIOOO");

					} catch (TwitterException ex) {
						Tools.showAlert("No internet Connection", "Error", null, 0);
						flag = false;
						return;
					} catch (IOException ex) {
						Logger.getLogger(ProgrammedTweetThread.class.getName()).log(Level.SEVERE, null, ex);
					}
				}	
			}
			
			try {
				Thread.sleep(300000);//5 MINUTOS DE ESPERA
			} catch (InterruptedException ex) {
				Tools.showAlert("THREAD INTERRUPTED. SAVING DATA.", "ERROR",null, 0);
				flag = false;
				return;
			}
		}
	}
	
	public boolean getFlag(){
		return this.flag;
	}
	
}
