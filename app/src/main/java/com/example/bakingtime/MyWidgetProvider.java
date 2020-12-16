// Code adapted from
// https://www.vogella.com/tutorials/AndroidWidgets/article.html#android-widgets

package com.example.bakingtime;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.RemoteViews;

import com.example.bakingtime.Utils.ProjectConstants;
import com.example.bakingtime.model.Recipe;

import java.util.ArrayList;

public class MyWidgetProvider extends AppWidgetProvider {

    private static final String NO_RECIPE = "No Recipe Selected";

    public static void updateWidgetIngredientList(Context context,
                                                  Recipe myCurrentRecipe, String ingredientList, AppWidgetManager appWidgetManager, int[] allWidgetIds) {
        for (int widgetId : allWidgetIds){

            // get layout file
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.ingredient_widget_layout);

            if (ingredientList != null){
                remoteViews.setTextViewText(R.id.ingredient_list, ingredientList);
                if (ingredientList.equals("")){
                    remoteViews.setTextViewText(R.id.ingredient_list, NO_RECIPE);
                }
            }else{
                remoteViews.setTextViewText(R.id.ingredient_list, NO_RECIPE);
            }

            Intent intent = new Intent(context, DetailActivity.class);
            Bundle recipeDetails = new Bundle();
            recipeDetails.putParcelableArrayList(ProjectConstants.ingredientKey, (ArrayList<? extends
                    Parcelable>) myCurrentRecipe.getIngredientList());
            recipeDetails.putParcelableArrayList(ProjectConstants.stepsKey, (ArrayList<? extends
                    Parcelable>) myCurrentRecipe.getBakingSteps());

            intent.putExtras(recipeDetails);

            // create pendingintent
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // register onclick
            remoteViews.setOnClickPendingIntent(R.id.ingredient_list, pendingIntent);

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }

    }


    @Override
    public void onUpdate(Context context,
                         AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        WidgetService.startActionUpdateRecipe(context);

    }
}



