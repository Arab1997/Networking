package com.example.networking.helper;

import com.example.networking.model.PlayerDatas;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("json_parsing.php")
    Call<PlayerDatas> loadDatas();
}
