package com.example.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.Utils.ProjectConstants;
import com.example.bakingtime.model.Ingredient;
import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.model.Step;
import com.example.bakingtime.network.APIBakingClient;
import com.example.bakingtime.network.BakingAPIInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler{

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecipeAdapter mRecipeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView mRecipeRecyclerView = findViewById(R.id.recipe_recyler_view);
        mRecipeRecyclerView.setHasFixedSize(true);

        int NUMBER_OF_COLUMNS = 1;
        RecyclerView.LayoutManager layoutManager =
                (new GridLayoutManager(this, NUMBER_OF_COLUMNS));

        mRecipeRecyclerView.setLayoutManager(layoutManager);
        // adapter initialized here
        mRecipeAdapter = new RecipeAdapter(this);

        //set adapter
        mRecipeRecyclerView.setAdapter(mRecipeAdapter);
        getBakingRecipes();

    }

    public void getBakingRecipes(){
        Retrofit retrofit = APIBakingClient.getRetrofitInstance();

        BakingAPIInterface apiService = retrofit.create(BakingAPIInterface.class);

        Call<ArrayList<Recipe>> call = apiService.getBakingRecipes();
        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Recipe>> call, @NonNull Response<ArrayList<Recipe>> response) {
                Log.v(TAG, String.valueOf(response.code()));
                if (response.isSuccessful()){
                    if (response.body() != null){
                        List<Recipe> recipes = response.body();
                        // check if list is empty
                        if (!(recipes.isEmpty())){
                            mRecipeAdapter.setRecipeData(recipes.toArray(new Recipe[0]));
                        }
                    }


                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                String ERROR_MESSAGE = "failure to get baking recipes";
                Log.v(TAG, ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void onClick(Recipe myCurrentRecipe) {
        // start new intent with recipe
        // start activity with recipe details
        Intent intentToStartDetailActivity = new Intent(this, DetailActivity.class);
        if (myCurrentRecipe != null){
            List<Ingredient> ingredientArrayList = myCurrentRecipe.getIngredientList();
            // save recipe to device
            SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            String CURRENT_RECIPE = "current recipe";
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(myCurrentRecipe);
            editor.putString(CURRENT_RECIPE, json);
            editor.apply();

            WidgetService.startActionUpdateRecipe(this);

            List<Step> bakingStepsArrayList = myCurrentRecipe.getBakingSteps();
            Bundle recipeDetails = new Bundle();
            recipeDetails.putParcelableArrayList(ProjectConstants.ingredientKey, (ArrayList<? extends Parcelable>) ingredientArrayList);
            recipeDetails.putParcelableArrayList(ProjectConstants.stepsKey, (ArrayList<? extends Parcelable>) bakingStepsArrayList);

            intentToStartDetailActivity.putExtras(recipeDetails);
            startActivity(intentToStartDetailActivity);
        }



    }
}