package com.example.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.Utils.ProjectConstants;
import com.example.bakingtime.model.Ingredient;
import com.example.bakingtime.model.Step;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        RecyclerView mIngredientRecyclerView = findViewById(R.id.ingredients_recyler_view);
        mIngredientRecyclerView.setHasFixedSize(true);

        final Button startCookingButton = findViewById(R.id.start_cooking_btn);


        RecyclerView.LayoutManager layoutManager =
                (new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        mIngredientRecyclerView.setLayoutManager(layoutManager);
        // adapter initialized here
        IngredientAdapter mIngredientAdapter = new IngredientAdapter();

        DividerItemDecoration trailerDecoration =
                new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mIngredientRecyclerView.addItemDecoration(trailerDecoration);

        //set adapter
        mIngredientRecyclerView.setAdapter(mIngredientAdapter);

        Intent intent = getIntent();
        if (intent == null){
            closeOnError();
        }else{
            Bundle recipeDetails =  intent.getExtras();

            assert recipeDetails != null;
            List<Ingredient> ingredients = recipeDetails.getParcelableArrayList(ProjectConstants.ingredientKey);

            List<Step> bakingSteps = recipeDetails.getParcelableArrayList(ProjectConstants.stepsKey);


            if (ingredients != null){
                mIngredientAdapter.setIngredientData(ingredients.toArray(new Ingredient[0]));
            }


            final Intent intentToStartStepActivity = new Intent(this, StepActivity.class);
            final Bundle recipeStepDetails = new Bundle();
            recipeStepDetails.putParcelableArrayList(ProjectConstants.stepsKey, (ArrayList<? extends Parcelable>) bakingSteps);
            intentToStartStepActivity.putExtras(recipeStepDetails);


          startCookingButton.setOnClickListener(view -> startActivity(intentToStartStepActivity));

        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, "Failed to get ingredient", Toast.LENGTH_SHORT).show();
    }


}