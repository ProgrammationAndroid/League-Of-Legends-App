package app.ipsaous.com.leagueoflegendtuto.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.ipsaous.com.leagueoflegendtuto.R;
import app.ipsaous.com.leagueoflegendtuto.entity.Champion;

/**
 * Created by ipsaous on 16/09/2015.
 */
public class GridAdapter extends BaseAdapter {

    private Context context;
    private List<Champion> championList = new ArrayList<>();
    private LayoutInflater inflater;

    public GridAdapter(Context context, List<Champion> championList) {
        this.context = context;
        this.championList = championList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return championList.size();
    }

    @Override
    public Object getItem(int position) {
        return championList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyViewHolder myviewHolder;

        if(convertView == null){

            convertView = inflater.inflate(R.layout.gallery_row, parent, false);
            myviewHolder = new MyViewHolder();
            myviewHolder.image = (ImageView) convertView.findViewById(R.id.iv_champ);
            myviewHolder.name = (TextView) convertView.findViewById(R.id.tv_champ_name);
            convertView.setTag(myviewHolder);

        }else{
            myviewHolder = (MyViewHolder) convertView.getTag();
        }

        final Champion champion = championList.get(position);

        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/5.16.1/img/champion/"+champion.getImageName()).into(myviewHolder.image);
        myviewHolder.name.setText(champion.getImageName().replace(".png", ""));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String champName = champion.getImageName().replace(".png", "");
                int id = champion.getId();
                Toast.makeText(context, champName + " / " + id, Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;
    }

    public class MyViewHolder{
        ImageView image;
        TextView name;
    }
}
