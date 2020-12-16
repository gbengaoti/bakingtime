package com.example.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.model.Step;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

/**
 * A fragment representing a list of Items.
 */
public class StepFragment extends Fragment implements StepDescriptionAdapter.StepAdapterOnClickListener {

    private static final String STEP_DESCRIPTION_LIST = "steps-list";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     *
     */
    public StepFragment() {
    }

    @Override
    public void onStepClick(Step currentStep) {
        Intent intentToStartVideoActivity = new Intent(getActivity(), VideoActivity.class);
        final String currentStepKey = "currentStepKey";
        Bundle stepBundle = new Bundle();
        stepBundle.putParcelable(currentStepKey, currentStep);
        intentToStartVideoActivity.putExtras(stepBundle);
        startActivity(intentToStartVideoActivity);
    }


    //variables to hold steps list and index of step position
    private List<Step> mStepsList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null){
            mStepsList = savedInstanceState.getParcelableArrayList(STEP_DESCRIPTION_LIST);
        }

        View rootView = inflater.inflate(R.layout.fragment_step_list, container, false);

        RecyclerView stepDescriptionRecyclerView = rootView.findViewById(R.id.step_description_recyclerview);

        StepDescriptionAdapter mAdapter = new StepDescriptionAdapter();

        mAdapter.setmClickListener(this);
        // Set the adapter

        DividerItemDecoration trailerDecoration =
                new DividerItemDecoration(rootView.getContext(), VERTICAL);
        stepDescriptionRecyclerView.addItemDecoration(trailerDecoration);

        stepDescriptionRecyclerView.setAdapter(mAdapter);

        mAdapter.setDescriptionList(mStepsList);

        return rootView;
    }

    public void setStepDescriptionList(List<Step> stepDescriptionList){
        mStepsList = stepDescriptionList;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        currentState.putParcelableArrayList(STEP_DESCRIPTION_LIST, (ArrayList<? extends Parcelable>) mStepsList);
    }

}