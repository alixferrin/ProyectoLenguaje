package espol.gabpa.proyectolenguajes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TuitListAdapter extends BaseAdapter {


    private Context nContext;
    private List<TweetScienceEvent> mProductsList;

    //Constructor


    public TuitListAdapter(Context nContext, List<TweetScienceEvent> mProductsList) {
        this.nContext = nContext;
        this.mProductsList = mProductsList;
    }

    @Override
    public int getCount() {
        return mProductsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(nContext, R.layout.list_tweet,null);
        TextView congresoText = (TextView)v.findViewById(R.id.tv_name);
        TextView cantidad = (TextView) v.findViewById(R.id.tv_cant);
        //seteo todo

        congresoText.setText(mProductsList.get(position).getTexto());
        cantidad.setText(String.valueOf(mProductsList.get(position).getCantidadOcurrencias())+" veces nombrado.");

        //save product id to tag
        v.setTag(mProductsList.get(position).getId());
        return v;
    }

}
