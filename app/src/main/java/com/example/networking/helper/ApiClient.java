package com.example.networking.helper;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient implements ServerUrl {
    private static Retrofit retrofit = null;
   // private static NetworkService mInstance;
    private static final String BASE_URL = "https://demonuts.com/Demonuts/JsonTest/Tennis/";
    private Retrofit mRetrofit;

    public static Retrofit getRetrofitInstance(){


        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        if (retrofit == null)
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

/*
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://demonuts.com/Demonuts/JsonTest/Tennis/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }*/
        return retrofit;
    }
 /*   public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public JSONPlaceHolderApi getJSONApi() {
        return retrofit.create(JSONPlaceHolderApi.class);
    }
*/
}
