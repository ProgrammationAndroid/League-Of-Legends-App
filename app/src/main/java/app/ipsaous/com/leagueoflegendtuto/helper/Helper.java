package app.ipsaous.com.leagueoflegendtuto.helper;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import app.ipsaous.com.leagueoflegendtuto.R;
import app.ipsaous.com.leagueoflegendtuto.request.ApiRequest;

/**
 * Created by ipsaous on 05/09/2015.
 */
public class Helper {

    public static void setImageItems(Context context, int item, ImageView image){

        if(item != 0){
            Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/5.16.1/img/item/"+item+".png ").error(R.drawable.empty).into(image);

        }else{
            Picasso.with(context).load(R.drawable.empty).into(image);
        }

    }

    public static String convertDate(long creation){
        Date date = new Date(creation);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public static String convertDuration(long duration) {


        double total = duration / 60f;
        String formattedDuration;

        //Faire une fonction
        if (total < 60) {
            double minute = total;
            int minuteInt = (int) minute;
            String finalMinute = String.valueOf(minuteInt);
            if (minuteInt < 10) {
                finalMinute = "0" + minuteInt;
            }
            double seconde = minute - Math.floor(minute);
            seconde = Math.round(seconde * 60f);
            int secondeInt = (int) seconde;
            String finalSeconde = String.valueOf(secondeInt);
            if (secondeInt < 10) {
                finalSeconde = "0" + secondeInt;
            }
            formattedDuration = finalMinute + ":" + finalSeconde;

        } else {
            double heure = total / 60f;
            int heureInt = (int) heure;
            double minute = total - (heureInt * 60f);
            int minuteInt = (int) minute;
            String finalMinute = String.valueOf(minuteInt);
            if (minuteInt < 10) {
                finalMinute = "0" + minuteInt;
            }
            double seconde = minute - Math.floor(minute);
            seconde = Math.round(seconde * 60f);
            int secondeInt = (int) seconde;
            String finalSeconde = String.valueOf(secondeInt);
            if (secondeInt < 10) {
                finalSeconde = "0" + secondeInt;
            }
            formattedDuration = String.valueOf(heureInt) + ":" + finalMinute + ":" + finalSeconde;
        }
        return formattedDuration;
    }

    public static void setImagePortraits(Context context, int champId, ImageView image, ApiRequest request){

        String portrait = null;

        try {
            portrait = request.getChampionName(champId);
            Picasso.with(context).load("http://ddragon.leagueoflegends.com/cdn/5.16.1/img/champion/"+portrait).into(image, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d("APP", "C'est tout bon");
                }

                @Override
                public void onError() {
                    Log.d("APP", "Il y a une erreur");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
