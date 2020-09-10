package com.example.bakingtime.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bakingtime.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyIngredientViewHolder extends RecyclerView.ViewHolder {

    private final TextView mIngredientNameTextView;
    private final TextView mIngredientQuantityTextView;
    private final ImageView mIngredientMeasureImageView;



    public MyIngredientViewHolder(@NonNull View view) {
        super(view);
        this.mIngredientNameTextView = view.findViewById(R.id.ingredient_name);
        this.mIngredientQuantityTextView = view.findViewById(R.id.ingredient_quantity);
        this.mIngredientMeasureImageView = view.findViewById(R.id.ingredient_measure);

    }


    public TextView getmIngredientNameTextView() {
        return mIngredientNameTextView;
    }

    public TextView getmIngredientQuantityTextView() {
        return mIngredientQuantityTextView;
    }

    public ImageView getmIngredientMeasureImageView() {
        return mIngredientMeasureImageView;
    }
}
