package com.example.bakingtime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bakingtime.model.Ingredient;
import com.example.bakingtime.model.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();
    private RecyclerView mIngredientRecyclerView;
    private IngredientAdapter mIngredientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        mIngredientRecyclerView = findViewById(R.id.ingredients_recyler_view);
//        mIngredientRecyclerView.setHasFixedSize(true);
//
//        RecyclerView.LayoutManager layoutManager =
//                (new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//
//        mIngredientRecyclerView.setLayoutManager(layoutManager);
//        // adapter initialized here
//        mIngredientAdapter = new IngredientAdapter();
//
//        DividerItemDecoration trailerDecoration =
//                new DividerItemDecoration(getApplicationContext(), VERTICAL);
//        mIngredientRecyclerView.addItemDecoration(trailerDecoration);
//
//        //set adapter
//        mIngredientRecyclerView.setAdapter(mIngredientAdapter);

        Intent intent = getIntent();
        if (intent == null){
            closeOnError();
        }else{
            Log.v(TAG, "Intent not null");
            intent.setExtrasClassLoader(Ingredient.class.getClassLoader());
            ArrayList<Ingredient> ingredients =  intent.getParcelableArrayListExtra(Intent.EXTRA_TEXT);
            assert ingredients != null;
            for (Ingredient i : ingredients){
                System.out.println("Quantity "+ i.getQuantity());
                System.out.println("Measure "+ i.getMeasure());
                System.out.println("Ingredient "+ i.getIngredient());
            }

//            mIngredientAdapter.setIngredientData(ingredients);

        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, "Failed to get ingredient", Toast.LENGTH_SHORT).show();
    }
}