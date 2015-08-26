package app.ipsaous.com.leagueoflegendtuto;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import app.ipsaous.com.leagueoflegendtuto.request.ApiRequest;


public class SearchActivity extends AppCompatActivity{

    EditText etJoueur;
    Button btnRechercher;
    ProgressBar pbLoader;
    ListView lvRecent;
    private RequestQueue queue;
    private ApiRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        queue = MySingleton.getInstance(this).getRequestQueue();
        request = new ApiRequest(queue, this);

        request.checkPlayerName("Test");


        etJoueur = (EditText) findViewById(R.id.et_player);
        btnRechercher = (Button) findViewById(R.id.btn_send);
        pbLoader = (ProgressBar) findViewById(R.id.pb_search);
        lvRecent = (ListView) findViewById(R.id.lv_recent);

        btnRechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

}
