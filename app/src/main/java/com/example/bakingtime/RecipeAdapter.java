package com.example.bakingtime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.viewholder.MyRecipeViewHolder;
import com.squareup.picasso.Picasso;

public class RecipeAdapter extends RecyclerView.Adapter<MyRecipeViewHolder> {

    private Recipe[] mRecipeList;

    private final RecipeAdapterOnClickHandler mClickHandler;

    public RecipeAdapter(RecipeAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    void setRecipeData(Recipe[] recipes){
        this.mRecipeList = recipes;
        notifyDataSetChanged();
    }

    public interface RecipeAdapterOnClickHandler{
        void onClick(Recipe myCurrentRecipe);
    }
    @NonNull
    @Override
    public MyRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new MyRecipeViewHolder(view, mRecipeList, mClickHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecipeViewHolder holder, int position) {
        ImageView imageView = holder.getmRecipeImageView();
        TextView textView = holder.getmRecipeNameView();
        Recipe myCurrentRecipe = mRecipeList[position];
        //set recipe name and image
        textView.setText(myCurrentRecipe.getName());

        //error checking for image

        if (myCurrentRecipe.getImageUrl().equals("") || myCurrentRecipe.getImageUrl() == null){
            Picasso.get()
                    .load(R.mipmap.recipe_placer)
                    .into(imageView);
        }else{
            Picasso.get()
                    .load(myCurrentRecipe.getImageUrl())
                    .placeholder(R.mipmap.recipe_placer)
                    .error(R.mipmap.recipe_placer)
                    .into(imageView);
        }


    }

    @Override
    public int getItemCount() {
        if (null == mRecipeList) return 0;
        return mRecipeList.length;
    }
}
