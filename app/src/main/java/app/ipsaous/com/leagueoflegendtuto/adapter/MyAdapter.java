package app.ipsaous.com.leagueoflegendtuto.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import app.ipsaous.com.leagueoflegendtuto.R;
import app.ipsaous.com.leagueoflegendtuto.entity.MatchEntity;
import app.ipsaous.com.leagueoflegendtuto.helper.Helper;


public class MyAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<MatchEntity> matches;

    public MyAdapter(Context context, List<MatchEntity> matches){
        this.context = context;
        this.matches = matches;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.match_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MatchEntity oneMatch = matches.get(position);

        if(oneMatch.isWinner()){
            ((MyViewHolder) holder).winOrLose.setBackgroundColor(context.getResources().getColor(R.color.win));
            ((MyViewHolder) holder).cardView.setCardBackgroundColor(context.getResources().getColor(R.color.win_row_bg));
        }else{
            ((MyViewHolder) holder).winOrLose.setBackgroundColor(context.getResources().getColor(R.color.lose));
            ((MyViewHolder) holder).cardView.setCardBackgroundColor(context.getResources().getColor(R.color.lose_row_bg));
        }

        Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/5.2.1/img/champion/"+oneMatch.getChampName()).into(((MyViewHolder) holder).portrait);
        ((MyViewHolder) holder).typeMatch.setText(oneMatch.getTypeMatch());
        ((MyViewHolder) holder).kda.setText(oneMatch.getKills() + " / " + oneMatch.getDeaths() + " / " + oneMatch.getAssists());
        ((MyViewHolder) holder).gold.setText(String.valueOf(Math.round(oneMatch.getGold() / 1000.0)) + "K");
        ((MyViewHolder) holder).cs.setText(String.valueOf(oneMatch.getCs()));

        Helper.setImageItems(context, oneMatch.getItems()[0], ((MyViewHolder) holder).item1);
        Helper.setImageItems(context, oneMatch.getItems()[1], ((MyViewHolder) holder).item2);
        Helper.setImageItems(context, oneMatch.getItems()[2], ((MyViewHolder) holder).item3);
        Helper.setImageItems(context, oneMatch.getItems()[3], ((MyViewHolder) holder).item4);
        Helper.setImageItems(context, oneMatch.getItems()[4], ((MyViewHolder) holder).item5);
        Helper.setImageItems(context, oneMatch.getItems()[5], ((MyViewHolder) holder).item6);
        Helper.setImageItems(context, oneMatch.getItems()[6], ((MyViewHolder) holder).item7);

        ((MyViewHolder) holder).matchDuration.setText(Helper.convertDuration(oneMatch.getMatchDuration()));
        ((MyViewHolder) holder).matchCreation.setText(Helper.convertDate(oneMatch.getMatchCreation()));

        setAnimation(((MyViewHolder) holder).cardView);


    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    private void setAnimation(View toAnimate){

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        animation.setDuration(1000);
        toAnimate.startAnimation(animation);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        View winOrLose;
        ImageView portrait, item1, item2, item3, item4, item5, item6, item7;
        TextView typeMatch, kda, gold, cs, matchDuration, matchCreation;
        CardView cardView;


        public MyViewHolder(View itemView) {
            super(itemView);

            winOrLose = itemView.findViewById(R.id.win_or_lose);
            portrait = (ImageView) itemView.findViewById(R.id.iv_portrait);
            item1 = (ImageView) itemView.findViewById(R.id.iv_item1);
            item2 = (ImageView) itemView.findViewById(R.id.iv_item2);
            item3 = (ImageView) itemView.findViewById(R.id.iv_item3);
            item4 = (ImageView) itemView.findViewById(R.id.iv_item4);
            item5 = (ImageView) itemView.findViewById(R.id.iv_item5);
            item6 = (ImageView) itemView.findViewById(R.id.iv_item6);
            item7 = (ImageView) itemView.findViewById(R.id.iv_item7);
            typeMatch = (TextView) itemView.findViewById(R.id.tv_type_match);
            kda = (TextView) itemView.findViewById(R.id.tv_kda);
            gold = (TextView) itemView.findViewById(R.id.tv_gold);
            cs = (TextView) itemView.findViewById(R.id.tv_cs);
            matchDuration = (TextView) itemView.findViewById(R.id.tv_duration);
            matchCreation = (TextView) itemView.findViewById(R.id.tv_date);
            cardView = (CardView) itemView.findViewById(R.id.cardview);


        }
    }
}
