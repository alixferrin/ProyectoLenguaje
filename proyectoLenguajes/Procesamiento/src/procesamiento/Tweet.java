/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procesamiento;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JuanJose FS
 */
public class Tweet {
    private String tweet;
    private int repeticiones;

    public Tweet(String tweet, int repeticiones) {
        this.tweet = tweet;
        this.repeticiones = repeticiones;
    }
    
    public static List<Tweet> cargarTweets() throws FileNotFoundException, IOException{
        List<Tweet> lista = new ArrayList<>();
        String cadena;
        FileReader f = new FileReader("tweets.csv");
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {
            String a[] = cadena.split("\\|");
            String tuit = a[0];
            int repeticiones = Integer.parseInt(a[1]);
            if(tuit.contains("RT ")){
                tuit = tuit.replace("RT ","");
            }
            Tweet t = new Tweet(tuit,repeticiones);
            System.out.println(t);
            lista.add(t);
        }
        b.close();
        return lista;
    }

    @Override
    public String toString() {
        return "tweet=" + tweet + ", repeticiones=" + repeticiones;
    }
    
    
   
}