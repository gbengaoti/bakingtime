package com.example.bakingtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.example.bakingtime.Utils.ProjectConstants;
import com.example.bakingtime.model.Step;

import java.util.ArrayList;
import java.util.List;

public class StepActivity extends AppCompatActivity{
    private static final String TAG = StepActivity.class.getSimpleName();
    private List<Step> mStepsDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if (savedInstanceState != null){
            mStepsDetails = savedInstanceState.getParcelableArrayList(ProjectConstants.stepsKey);
            StepFragment contentFragment = new StepFragment();

            contentFragment.setStepDescriptionList(mStepsDetails);

            // add the fragment to its container using a fragmentmanager and a transaction
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.content_container, contentFragment)
                    .commit();

        }else {

            Intent intent = getIntent();
            if (intent == null) {
                closeOnError();
            } else {
                Log.v(TAG, "Intent not null");
                Bundle recipeStepsDetails = intent.getExtras();

                assert recipeStepsDetails != null;

                mStepsDetails = recipeStepsDetails.getParcelableArrayList(ProjectConstants.stepsKey);


                StepFragment contentFragment = new StepFragment();

                contentFragment.setStepDescriptionList(mStepsDetails);

                // add the fragment to its container using a fragmentmanager and a transaction
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.content_container, contentFragment)
                        .commit();

            }
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, "Failed to get ingredient", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(ProjectConstants.stepsKey, (ArrayList<? extends Parcelable>) mStepsDetails);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}