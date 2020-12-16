package com.example.bakingtime;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.example.bakingtime.model.Ingredient;
import com.example.bakingtime.model.Recipe;
import com.google.gson.Gson;

import java.util.List;

import static com.example.bakingtime.Utils.ProjectConstants.CURRENT_RECIPE;
import static com.example.bakingtime.Utils.ProjectConstants.MY_PREFERENCES;


public class WidgetService extends JobIntentService {
    private static final int UPDATE_RECIPE_ID = 1029;
    private static final String TAG = WidgetService.class.getSimpleName();
    private static final String ACTION_UPDATE = "ACTION_UPDATE"; // save to project constants

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        final String action = intent.getAction();
        if (ACTION_UPDATE.equals(action)){
            Log.v(TAG, "service got a match - starting now");
            handleActionUpdateRecipe();
        }
    }

    private void handleActionUpdateRecipe() {
        // Get data from shared preferences file
        // retrieve recipe from shared preferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences
                (MY_PREFERENCES, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString(CURRENT_RECIPE, "");
        Recipe myCurrentRecipe = gson.fromJson(json, Recipe.class);
        StringBuilder ingredientListBuilder = new StringBuilder();
        String ingredientListString = "";

        if (myCurrentRecipe != null){
            Log.v(TAG, "current recipe is null");
            List<Ingredient> ingredientList = myCurrentRecipe.getIngredientList();
            for (Ingredient ingredient: ingredientList){
                String line = ingredient.getIngredient() + "\n";
                ingredientListBuilder.append(line);

            }
        }else{
            ingredientListString = "No Ingredient Found";
        }

        ingredientListString = ingredientListBuilder.toString();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        int[] allWidgetIds = appWidgetManager.getAppWidgetIds
                (new ComponentName(this, MyWidgetProvider.class));

        MyWidgetProvider.updateWidgetIngredientList(this, myCurrentRecipe, ingredientListString,
                appWidgetManager, allWidgetIds);

    }

    public static void startActionUpdateRecipe(Context context){
        Intent intent = new Intent(context, WidgetService.class);
        intent.setAction(ACTION_UPDATE);
        enqueueWork(context, WidgetService.class, UPDATE_RECIPE_ID, intent);
    }

}
