package app.ipsaous.com.leagueoflegendtuto;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;


public class HistoriqueActivity extends AppCompatActivity {

    private String playerName;
    private Long playerId;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        if(extras.getString("NAME") != null && extras.getLong("ID") > 0){
            playerName = extras.getString("NAME");
            playerId = extras.getLong("ID");
            toolbar.setTitle(playerName);
        }

        setSupportActionBar(toolbar);


    }



}
