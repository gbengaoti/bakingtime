package com.example.bakingtime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.model.Ingredient;
import com.example.bakingtime.viewholder.MyIngredientViewHolder;

public class IngredientAdapter extends RecyclerView.Adapter<MyIngredientViewHolder> {
    private Ingredient[] mIngredientList;

    void setIngredientData(Ingredient[] ingredients){
        this.mIngredientList = ingredients;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyIngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.ingredient_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new MyIngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyIngredientViewHolder holder, int position) {
        TextView ingredientNameTextView = holder.getmIngredientNameTextView();
        TextView ingredientQuantityTextView = holder.getmIngredientQuantityTextView();
        TextView ingredientMeasureTextView = holder.getmIngredientMeasureTextView();
        Ingredient ingredient = mIngredientList[position];
        if (ingredient.getIngredient() != null){
            ingredientNameTextView.setText(ingredient.getIngredient());
        }
       if (ingredient.getQuantity() != null){
           ingredientQuantityTextView.setText(String.valueOf(mIngredientList[position].getQuantity()));
       }

       ingredientMeasureTextView.setText(ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        if(null == mIngredientList) return 0;
        return mIngredientList.length;
    }
}
