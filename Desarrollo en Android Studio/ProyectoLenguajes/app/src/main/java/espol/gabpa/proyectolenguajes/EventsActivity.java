package espol.gabpa.proyectolenguajes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import procesamiento.Tweet;

public class EventsActivity extends AppCompatActivity {

    private ListView lvTuits;
    private TuitListAdapter adapter;
    private List<TweetScienceEvent> tuitsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvTuits = (ListView) findViewById(R.id.listview_tweets);
        tuitsList = new ArrayList<>();


        try {
            InputStream inputSt = getResources().openRawResource(R.raw.tweets);
            ArrayList<procesamiento.Tweet> tuitsProcesados = (ArrayList) Tweet.cargarTweets(inputSt);
            for(procesamiento.Tweet tw: tuitsProcesados){
                tuitsList.add(new TweetScienceEvent(tw.getId(),tw.getTweet(),tw.getRepeticiones()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Instanciar Adapter

        adapter = new TuitListAdapter(getApplicationContext(),tuitsList);
        lvTuits.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if( id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
