package com.example.networking.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.networking.R;
import com.example.networking.adapter.PlayerAdapter;
import com.example.networking.helper.ApiClient;
import com.example.networking.helper.ApiInterface;
import com.example.networking.model.Player;
import com.example.networking.model.PlayerDatas;
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;

import static com.example.networking.helper.ServerUrl.SERVER_URL;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();


        //Networking libraries

        //apiUsingAsyncHttp();
        //apiUsingFuel();
        //apiUsingVolley();
        //apiUsingOkHttp();
          apiUsingRetrofit();

      /*  List<Player> player = preparePlayerList();
        refreshAdapter(player);*/
    }


    private void initView() {
        context = this;

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 1));

    }


    private void refreshAdapter(List<Player> players) {
        PlayerAdapter adapter = new PlayerAdapter(context, players);
        recyclerView.setAdapter(adapter);
    }

    private void progressWithResponse(PlayerDatas playerDatas) {
        progressBar.setVisibility(View.GONE);

        String message = playerDatas.getMessage();
        List<Player> players = playerDatas.getData();
        fireToast(message);
        refreshAdapter(players);
    }

    private void fireToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private void apiUsingAsyncHttp() {
        progressBar.setVisibility(View.VISIBLE);
        RequestParams params = new RequestParams();  // empty param
        //params.put("");
        AsyncHttpClient client = new AsyncHttpClient();

        client.post(SERVER_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Test", "onFailure: " + statusCode);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("Test", "onSuccess: " + responseString);

                PlayerDatas playerDatas = new Gson().fromJson(responseString, PlayerDatas.class);
                progressWithResponse(playerDatas);
            }
        });

    }
    private void apiUsingVolley() {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringReq = new StringRequest(SERVER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Test", "onResponse: " + response);

                PlayerDatas playerDatas = new Gson().fromJson(response, PlayerDatas.class);
                progressWithResponse(playerDatas);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Test", "onFailure: " + error.getLocalizedMessage());
                progressBar.setVisibility(View.GONE);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringReq);
    }

  /*  private void apiUsingFuel() {
        progressBar.setVisibility(View.VISIBLE);

        Fuel.get(SERVER_URL).responseString(new Handler<String>() {
            @Override
            public void success(String s) {
                Log.d("Test", "onSuccess: " + s;

                PlayerDatas playerDatas = new Gson().fromJson(s, PlayerDatas.class);
                progressWithResponse(playerDatas);
            }

            @Override
            public void failure(@NotNull FuelError fuelError) {
                Log.d("Test", "onFailure: " + fuelError);
                progressBar.setVisibility(View.GONE);
            }
        });
    }*/



    private void apiUsingOkHttp() {

        progressBar.setVisibility(View.VISIBLE);
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_URL).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Test", "onFailure: " + e.getLocalizedMessage());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                final String resp  = response.body().toString();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Test", "onSuccess: " + resp);

                        PlayerDatas playerDatas = new Gson().fromJson(resp, PlayerDatas.class);
                        progressWithResponse(playerDatas);
                    }
                });

            }
        });


    }


    private void apiUsingRetrofit() {
        progressBar.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = ApiClient.getRetrofitInstance().create(ApiInterface.class);

        retrofit2.Call<PlayerDatas> call = apiInterface.loadDatas();

        call.enqueue(new retrofit2.Callback<PlayerDatas>() {
            @Override
            public void onResponse(retrofit2.Call<PlayerDatas> call, retrofit2.Response<PlayerDatas> response) {

                Log.d("Test", "onSuccess: " + response.message());
                progressWithResponse(response.body());
                /*Post post = response.body();

                        textView.append(post.getId() + "\n");
                        textView.append(post.getUserId() + "\n");
                        textView.append(post.getTitle() + "\n");
                        textView.append(post.getBody() + "\n");*/
            }

            @Override
            public void onFailure(retrofit2.Call<PlayerDatas> call, Throwable t) {
                Log.d("Test", "onFailure: " + t.getLocalizedMessage());
               progressBar.setVisibility(View.GONE);
            }
        });

    }

}
