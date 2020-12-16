package com.example.bakingtime.network;

import com.example.bakingtime.model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BakingAPIInterface {
    String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    @GET("baking.json")
    Call<ArrayList<Recipe>> getBakingRecipes();
}
