package app.ipsaous.com.leagueoflegendtuto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;


public class HistoriqueActivity extends AppCompatActivity {

    private String playerName;
    private Long playerId;
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        test = (TextView) findViewById(R.id.test);

        if(extras.getString("NAME") != null && extras.getLong("ID") > 0){

            playerName = extras.getString("NAME");
            playerId = extras.getLong("ID");

            test.setText(playerName + "/" + String.valueOf(playerId));

        }

    }



}
