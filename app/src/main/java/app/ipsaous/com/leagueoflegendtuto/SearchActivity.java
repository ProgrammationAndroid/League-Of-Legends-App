package app.ipsaous.com.leagueoflegendtuto;


import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import app.ipsaous.com.leagueoflegendtuto.request.ApiRequest;


public class SearchActivity extends AppCompatActivity{

    private EditText etJoueur;
    private Button btnRechercher;
    private ProgressBar pbLoader;
    private TextView tv_recent;
    private RequestQueue queue;
    private ApiRequest request;
    private Handler handler;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String recentSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Initialisation des sharedPreferences

        pref = this.getSharedPreferences("lolPrefs", 0);
        editor = pref.edit();
        recentSearch = pref.getString("RECENT", null);


        queue = MySingleton.getInstance(this).getRequestQueue();
        request = new ApiRequest(queue, this);
        handler = new Handler();


        etJoueur = (EditText) findViewById(R.id.et_player);
        btnRechercher = (Button) findViewById(R.id.btn_send);
        pbLoader = (ProgressBar) findViewById(R.id.pb_search);
        tv_recent = (TextView) findViewById(R.id.tv_recent);

        if(recentSearch != null){
            tv_recent.setText(recentSearch);

            tv_recent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    request.checkPlayerName(recentSearch, new ApiRequest.CheckPlayerCallback() {
                        @Override
                        public void onSuccess(String name, long id) {
                            pbLoader.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(getApplicationContext(), HistoriqueActivity.class);
                            Bundle extras = new Bundle();
                            extras.putString("NAME", name);
                            extras.putLong("ID", id);
                            intent.putExtras(extras);
                            startActivity(intent);

                        }

                        @Override
                        public void dontExist(String message) {
                            pbLoader.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String message) {
                            pbLoader.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        btnRechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              final String recherche = etJoueur.getText().toString().trim();

                if(recherche.length() > 0){
                    pbLoader.setVisibility(View.VISIBLE);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            request.checkPlayerName(recherche, new ApiRequest.CheckPlayerCallback() {
                                @Override
                                public void onSuccess(String name, long id) {
                                    pbLoader.setVisibility(View.INVISIBLE);
                                    Intent intent = new Intent(getApplicationContext(), HistoriqueActivity.class);
                                    Bundle extras = new Bundle();
                                    extras.putString("NAME", name);
                                    extras.putLong("ID", id);
                                    intent.putExtras(extras);

                                    //enregistrement de la derniere recherche
                                    editor.putString("RECENT", recherche);
                                    editor.commit();
                                    startActivity(intent);


                                }

                                @Override
                                public void dontExist(String message) {
                                    pbLoader.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(String message) {
                                    pbLoader.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }, 500);



                }else{
                    Toast.makeText(getApplicationContext(), "Vous devez taper un nom de joueur", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

}
