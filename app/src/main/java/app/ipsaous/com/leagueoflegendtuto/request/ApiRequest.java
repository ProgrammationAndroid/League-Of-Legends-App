package app.ipsaous.com.leagueoflegendtuto.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiRequest {

    private RequestQueue queue;
    private Context context;
    private static final String API_KEY = "69119440-2add-4d9a-bbb3-dabba6e1329c";
    private String region = "euw";

    public ApiRequest(RequestQueue queue, Context context){
        this.queue = queue;
        this.context = context;
    }

    public void checkPlayerName(final String name, final CheckPlayerCallback callback){

        String url = "https://"+region+".api.pvp.net/api/lol/"+region+"/v1.4/summoner/by-name/"+name+"?api_key="+API_KEY;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("APP", response.toString());

                try {
                    JSONObject json = response.getJSONObject(name.toLowerCase());
                    String name = json.getString("name");
                    long id = json.getLong("id");
                    callback.onSuccess(name, id);

                } catch (JSONException e) {
                    Log.d("APP", "EXCEPTION =" + e);
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error instanceof NetworkError){
                    callback.onError("Impossible de se connecter");
                }else if(error instanceof ServerError){
                    callback.dontExist("Ce joueur n'existe pas");
                }
                Log.d("APP", "ERROR = " + error);

            }
        });

        queue.add(request);


    }

    public interface CheckPlayerCallback{
        void onSuccess(String name, long id);
        void dontExist(String message);
        void onError(String message);
    }

}
