package com.example.bakingtime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.model.Step;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Step}.
 * C: Replace the implementation with code for your data type.
 */
public class StepDescriptionAdapter extends RecyclerView.Adapter<StepDescriptionAdapter.ViewHolder> {

    private static final String TAG = StepDescriptionAdapter.class.getSimpleName();
    private List<Step> mValues;

    void setDescriptionList(List<Step> descriptionList){
        this.mValues = descriptionList;
        notifyDataSetChanged();
    }

    private StepAdapterOnClickListener mClickListener;

    public interface StepAdapterOnClickListener{
        void onStepClick(Step currentStep);
    }

    public void setmClickListener(StepAdapterOnClickListener listener){
        this.mClickListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_step, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Step currentStep = mValues.get(position);
        holder.mItem = mValues.get(position);
        // error checking here
        holder.mIdView.setText(String.valueOf(mValues.get(position).getId() + 1));
        holder.mContentView.setText(mValues.get(position).getShortDescription());
        holder.itemView.setOnClickListener(view -> {
            if(mClickListener != null){
                mClickListener.onStepClick(currentStep);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView mIdView;
        private final TextView mContentView;
        private Step mItem;

        public ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);

        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

    }


}