/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Interface.TweetView;
import ToolKit.Tools;
import classes.MainController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import twitter4j.Status;

/**
 *
 * @author gabpa
 */
public class TimeLineThread extends Thread{
	private static ScrollPane pane;
	public boolean flag = true;
	
	public TimeLineThread(ScrollPane pane){
		TimeLineThread.pane = pane;
	}
	
	@Override
	public void run() {
		
		
		while(flag){
			List<Status> status = MainController.tf.showTimeline(flag);
			if(status.size()>0){
				VBox vbox = new VBox(5);
				vbox.setPadding(new Insets(5,5,5,5));
				vbox.setFillWidth(true);
				int n = 0;
				while(n < 10 && n < status.size()){
					TweetView tweet = new TweetView(status.get(n));
					vbox.getChildren().add(tweet.getRoot());
					n++;
				}
				Platform.runLater(() -> {
					TimeLineThread.pane.setContent(vbox);
				});
				
			}else
				Tools.showAlert("No hay informacion que mostrar", "Lo siento", null, 1);

			try {
				Thread.sleep(60000);
			}catch (InterruptedException ex) {
				Logger.getLogger(TimeLineThread.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
	}
	
}
