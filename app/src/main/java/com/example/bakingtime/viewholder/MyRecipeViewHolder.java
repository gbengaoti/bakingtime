package com.example.bakingtime.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.R;
import com.example.bakingtime.RecipeAdapter;
import com.example.bakingtime.model.Recipe;

public class MyRecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final ImageView mRecipeImageView;
    private final TextView mRecipeNameView;
    private final RecipeAdapter.RecipeAdapterOnClickHandler mClickHandler;
    private final Recipe[] mRecipeList;

    public MyRecipeViewHolder(@NonNull View view, Recipe[] mRecipeList, RecipeAdapter.RecipeAdapterOnClickHandler mClickHandler) {
        super(view);
        mRecipeImageView = view.findViewById(R.id.recipe_image_box);
        mRecipeNameView = view.findViewById(R.id.recipe_name);
        view.setOnClickListener(this);
        this.mClickHandler = mClickHandler;
        this.mRecipeList = mRecipeList;


    }

    @Override
    public void onClick(View view) {
        int adapterPosition = getAdapterPosition();
        Recipe myCurrentRecipe = mRecipeList[adapterPosition];
        mClickHandler.onClick(myCurrentRecipe);
    }

    public ImageView getmRecipeImageView(){return this.mRecipeImageView;}

    public TextView getmRecipeNameView() {return this.mRecipeNameView;}
}
