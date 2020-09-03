package com.example.bakingtime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.network.APIBakingClient;
import com.example.bakingtime.network.BakingAPIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler{

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecipeRecyclerView;
    private RecipeAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecipeRecyclerView = findViewById(R.id.recipe_recyler_view);
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

        Call<List<Recipe>> call = apiService.getBakingRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.v(TAG, String.valueOf(response.code()));
                if (response.isSuccessful()){
                    assert response.body() != null;
                    List<Recipe> recipes = response.body();
                    // check if list is empty
                    if (!(recipes.isEmpty())){
                        mRecipeAdapter.setRecipeData(recipes.toArray(new Recipe[0]));
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                String ERROR_MESSAGE = "failure to get baking recipes";
                Log.v(TAG, ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void onClick(Recipe myCurrentRecipe) {
        // start new intent with recipe
        Log.v(TAG, myCurrentRecipe.getName());
    }
}