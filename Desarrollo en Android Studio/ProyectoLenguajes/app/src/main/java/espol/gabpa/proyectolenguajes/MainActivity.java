package espol.gabpa.proyectolenguajes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import procesamiento.Tweet;

public class MainActivity extends AppCompatActivity {

    private ListView lvTuits;
    private TuitListAdapter adapter;
    private List<TweetScienceEvent> tuitsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTuits = (ListView) findViewById(R.id.listview_tweets);
        tuitsList = new ArrayList<>();

        //Datos Quemados
        /*tuitsList.add(new TweetScienceEvent(1,"Testaso",10));
        tuitsList.add(new TweetScienceEvent(2,"ONU",10));
        tuitsList.add(new TweetScienceEvent(3,"El Mundial alv",15));
        tuitsList.add(new TweetScienceEvent(4,"Harta Demencia",20));
        tuitsList.add(new TweetScienceEvent(5,"Quieto Conchetu",10));
*/

        try {
            InputStream inputSt = getResources().openRawResource(R.raw.tweets);
            ArrayList<procesamiento.Tweet> tuitsProcesados = (ArrayList) Tweet.cargarTweets(inputSt);
            for(procesamiento.Tweet tw: tuitsProcesados){
                System.out.println(tw);
                tuitsList.add(new TweetScienceEvent(tw.getId(),tw.getTweet(),tw.getRepeticiones()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Instanciar Adapter

        adapter = new TuitListAdapter(getApplicationContext(),tuitsList);
        lvTuits.setAdapter(adapter);


    }
}
