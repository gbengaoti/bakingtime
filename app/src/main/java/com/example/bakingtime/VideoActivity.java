package com.example.bakingtime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.bakingtime.model.Step;

public class VideoActivity extends AppCompatActivity {

    private static final String TAG = VideoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Intent intent = getIntent();
        if (intent == null){
            closeOnError();
        }else {
            Log.v(TAG, "Intent not null");
            Bundle currentStepBundle =  intent.getExtras();
            final String currentStepKey = "currentStepKey";
            //String videoLinkKey = "VideoLink";
            assert currentStepBundle != null;
            Step currentStep = currentStepBundle.getParcelable(currentStepKey);

            VideoFragment videoFragment = new VideoFragment();

            videoFragment.setCurrentStep(currentStep);

            //videoFragment.setVideoURLLink(videoURLLink);


            // add the fragment to its container using a fragmentmanager and a transaction
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.video_container, videoFragment)
                    .commit();
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, "Failed to get video link", Toast.LENGTH_SHORT).show();
    }
}