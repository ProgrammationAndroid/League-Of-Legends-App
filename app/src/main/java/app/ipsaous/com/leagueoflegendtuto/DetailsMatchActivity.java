package app.ipsaous.com.leagueoflegendtuto;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Iterator;

import app.ipsaous.com.leagueoflegendtuto.entity.MatchEntity;
import app.ipsaous.com.leagueoflegendtuto.helper.Helper;
import app.ipsaous.com.leagueoflegendtuto.request.ApiRequest;

public class DetailsMatchActivity extends AppCompatActivity {

    private RelativeLayout rlInfosJoueur;
    private TextView typeMatch, level, gold, cs, kda, duration, creation;
    private ImageView portrait, sum1, sum2, item1, item2, item3, item4, item5, item6, item7,vainqueur1,vainqueur2,
    vainqueur3, vainqueur4, vainqueur5, perdant1, perdant2, perdant3, perdant4, perdant5;
    private TableLayout statistiques;
    private RequestQueue queue;
    private ApiRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_match);

        initialize();

        Intent i = getIntent();
        if(i.hasExtra("ONE_MATCH")){
            MatchEntity oneMatch = (MatchEntity) i.getSerializableExtra("ONE_MATCH");

            queue = MySingleton.getInstance(this).getRequestQueue();
            request = new ApiRequest(queue, this);
            if(oneMatch.isWinner()){
                rlInfosJoueur.setBackgroundColor(getResources().getColor(R.color.win_row_bg));
            }else{
                rlInfosJoueur.setBackgroundColor(getResources().getColor(R.color.lose_row_bg));
            }

            Picasso.with(this).load("http://ddragon.leagueoflegends.com/cdn/5.16.1/img/champion/"+oneMatch.getChampName()).into(portrait);
            if(oneMatch.getSum1().equals("Default")){
                Picasso.with(this).load(R.drawable.empty).into(sum1);
            }else{
                Picasso.with(this).load("http://ddragon.leagueoflegends.com/cdn/5.16.1/img/spell/"+oneMatch.getSum1()).into(sum1);
            }
            if(oneMatch.getSum2().equals("Default")){
                Picasso.with(this).load(R.drawable.empty).into(sum2);
            }else{
                Picasso.with(this).load("http://ddragon.leagueoflegends.com/cdn/5.16.1/img/spell/" + oneMatch.getSum2()).into(sum2);
            }

            //Rajout des items

            Helper.setImageItems(this, oneMatch.getItems()[0], item1);
            Helper.setImageItems(this, oneMatch.getItems()[1], item2);
            Helper.setImageItems(this, oneMatch.getItems()[2], item3);
            Helper.setImageItems(this, oneMatch.getItems()[3], item4);
            Helper.setImageItems(this, oneMatch.getItems()[4], item5);
            Helper.setImageItems(this, oneMatch.getItems()[5], item6);
            Helper.setImageItems(this, oneMatch.getItems()[6], item7);

            //Rajout des infos
            typeMatch.setText(oneMatch.getTypeMatch());
            level.setText("Level " + String.valueOf(oneMatch.getChampLevel()));
            gold.setText(String.valueOf(Math.round(oneMatch.getGold() / 1000.0)) + "K");
            cs.setText(String.valueOf(oneMatch.getCs()));
            kda.setText(oneMatch.getKills() + "/" + oneMatch.getDeaths() + "/" + oneMatch.getAssists());
            duration.setText(Helper.convertDuration(oneMatch.getMatchDuration()));
            creation.setText(Helper.convertDate(oneMatch.getMatchCreation()));

            //Rajout des Vainqueurs
            float density = getResources().getDisplayMetrics().density;
            int size = (int) (70 * density);

            Helper.setImagePortraits(this, oneMatch.getTeamWinner().get(0), vainqueur1, request);
            Helper.setImagePortraits(this, oneMatch.getTeamWinner().get(1), vainqueur2, request);
            Helper.setImagePortraits(this, oneMatch.getTeamWinner().get(2), vainqueur3, request);
            Helper.setImagePortraits(this, oneMatch.getTeamWinner().get(3), vainqueur4, request);

            if(oneMatch.getChampId() == oneMatch.getTeamWinner().get(4) && oneMatch.isWinner() == true){
                vainqueur5.getLayoutParams().height = size;
                vainqueur5.getLayoutParams().width = size;
            }
            Helper.setImagePortraits(this, oneMatch.getTeamWinner().get(4), vainqueur5, request);

            //Rajout des perdants

            if(oneMatch.getTeamLoser().size() > 0) { // Si c'est un match contre l'ordinateur cette liste sera vide. On fait donc une vérification
                Helper.setImagePortraits(this, oneMatch.getTeamLoser().get(0), perdant1, request);
                Helper.setImagePortraits(this, oneMatch.getTeamLoser().get(1), perdant2, request);
                Helper.setImagePortraits(this, oneMatch.getTeamLoser().get(2), perdant3, request);
                Helper.setImagePortraits(this, oneMatch.getTeamLoser().get(3), perdant4, request);

                if (oneMatch.getChampId() == oneMatch.getTeamLoser().get(4) && oneMatch.isWinner() == false) {
                    perdant5.getLayoutParams().height = size;
                    perdant5.getLayoutParams().width = size;
                }
                Helper.setImagePortraits(this, oneMatch.getTeamLoser().get(4), perdant5, request);
            }
            //Affichage des statistiques

            Iterator iterator = oneMatch.getStats().entrySet().iterator();
            while(iterator.hasNext()){

                HashMap.Entry key = (HashMap.Entry) iterator.next();

                TableRow row = new TableRow(this);
                TextView tv_key = new TextView(this);
                tv_key.setTextSize(15);
                TextView tv_value = new TextView(this);
                tv_value.setTextSize(15);
                row.addView(tv_key);
                row.addView(tv_value);

                LinearLayout.LayoutParams key_params = (LinearLayout.LayoutParams) tv_key.getLayoutParams();
                key_params.width = 0;
                key_params.weight = 1;
                key_params.height = (int)(20*density);
                tv_key.setLayoutParams(key_params);

                LinearLayout.LayoutParams value_params = (LinearLayout.LayoutParams) tv_value.getLayoutParams();
                value_params.gravity = Gravity.RIGHT;
                value_params.height = (int)(20*density);
                tv_value.setLayoutParams(value_params);

                tv_key.setText(key.getKey().toString());
                tv_value.setText(key.getValue().toString());

                statistiques.addView(row, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            }
        }

    }

    public void initialize(){

        rlInfosJoueur = (RelativeLayout) findViewById(R.id.rl_infos_joueur);
        portrait = (ImageView) findViewById(R.id.iv_details_portrait);
        sum1 = (ImageView) findViewById(R.id.iv_details_sum1);
        sum2 = (ImageView) findViewById(R.id.iv_details_sum2);
        typeMatch = (TextView) findViewById(R.id.tv_details_type_match);
        level = (TextView) findViewById(R.id.tv_details_level);
        gold = (TextView) findViewById(R.id.tv_details_gold);
        cs = (TextView) findViewById(R.id.tv_details_cs);
        kda = (TextView) findViewById(R.id.tv_details_kda);
        duration = (TextView) findViewById(R.id.tv_details_duration);
        creation = (TextView) findViewById(R.id.tv_details_creation);
        item1 = (ImageView) findViewById(R.id.iv_details_item1);
        item2 = (ImageView) findViewById(R.id.iv_details_item2);
        item3 = (ImageView) findViewById(R.id.iv_details_item3);
        item4 = (ImageView) findViewById(R.id.iv_details_item4);
        item5 = (ImageView) findViewById(R.id.iv_details_item5);
        item6 = (ImageView) findViewById(R.id.iv_details_item6);
        item7 = (ImageView) findViewById(R.id.iv_details_item7);
        vainqueur1 = (ImageView) findViewById(R.id.iv_vainqueur1);
        vainqueur2 = (ImageView) findViewById(R.id.iv_vainqueur2);
        vainqueur3 = (ImageView) findViewById(R.id.iv_vainqueur3);
        vainqueur4 = (ImageView) findViewById(R.id.iv_vainqueur4);
        vainqueur5 = (ImageView) findViewById(R.id.iv_vainqueur5);
        perdant1 = (ImageView) findViewById(R.id.iv_perdants1);
        perdant2 = (ImageView) findViewById(R.id.iv_perdants2);
        perdant3 = (ImageView) findViewById(R.id.iv_perdants3);
        perdant4 = (ImageView) findViewById(R.id.iv_perdants4);
        perdant5 = (ImageView) findViewById(R.id.iv_perdants5);
        statistiques = (TableLayout) findViewById(R.id.tl_details_stats);


    }

}
