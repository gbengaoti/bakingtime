package com.example.bakingtime;

/** Reference for setting up exoplayer - https://cloudinary.com/blog/
// exoplayer_android_tutorial_easy_video_delivery_and_editing
 **/

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bakingtime.model.Step;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Objects;

public class VideoFragment extends Fragment {

    private static final String TAG = VideoFragment.class.getSimpleName();

    private static final String CURRENT_STEP_KEY = "current-step-key";

    private static final String VIDEO_DESCRIPTION_KEY = "video-description-key";
    private static final String VIDEO_URL_KEY = "video-url-key";
    private static final String CURRENT_WINDOW_KEY = "window-index";
    private static final String PLAY_BACK_POSITION_KEY = "video-position";
    private static final String PLAY_WHEN_READY_KEY = "play-when-ready";

    long mPlaybackPosition;
    boolean mPlayWhenReady = true;
    int mCurrentWindowIndex;


    // C: Rename and change types of parameters
    private String mVideoURLLink;
    private String mVideoDescription;
    private Step mCurrentStep;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private ImageView mVideoPlaceholderImageView;
    private TextView mTextDescriptionView;
    private DefaultBandwidthMeter mBandwidthMeter;
    private TrackSelection.Factory mVideoTrackSelectionFactory;
    private TrackSelector mTrackSelector;
    private DataSource.Factory mDataSourceFactory;
    private MediaSource mVideoSource;

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_video, container, false);
        mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.player_view);
        mVideoPlaceholderImageView = (ImageView) view.findViewById(R.id.no_video_placeholder);
        mTextDescriptionView = (TextView) view.findViewById(R.id.text_video_step);

        if (savedInstanceState == null) {
            Log.v(TAG, "SavedInstanceState is null");
            mPlayWhenReady = true;
            mCurrentWindowIndex = 0;
            mPlaybackPosition = 0;


        }else{
            mPlaybackPosition = savedInstanceState.getLong(PLAY_BACK_POSITION_KEY);
            mCurrentWindowIndex = savedInstanceState.getInt(CURRENT_WINDOW_KEY);
            mVideoURLLink = savedInstanceState.getString(VIDEO_URL_KEY);
            mPlayWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY);
            mVideoDescription = savedInstanceState.getString(VIDEO_DESCRIPTION_KEY);
            mCurrentStep = savedInstanceState.getParcelable(CURRENT_STEP_KEY);

        }
        mTextDescriptionView.setText(mVideoDescription);
        if (mVideoURLLink.equals("")) {
            Log.v(TAG, "video url is empty");
            mVideoPlaceholderImageView.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.INVISIBLE);
        } else {
            initializePlayer(mVideoURLLink);
            mVideoPlaceholderImageView.setVisibility(View.INVISIBLE);
            mPlayerView.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.v(TAG, "Saving instance state called");

        updatePlaybackPosition();
        outState.putParcelable(CURRENT_STEP_KEY, mCurrentStep);
        outState.putString(VIDEO_DESCRIPTION_KEY, mVideoDescription);
        outState.putString(VIDEO_URL_KEY, mVideoURLLink);
        outState.putBoolean(PLAY_WHEN_READY_KEY, mPlayWhenReady);
        outState.putInt(CURRENT_WINDOW_KEY, mCurrentWindowIndex);
        outState.putLong(PLAY_BACK_POSITION_KEY, mPlaybackPosition);
        super.onSaveInstanceState(outState);

    }

    public void setVideoURLLink(String videoURLLink) {
        mVideoURLLink = videoURLLink;
    }

    public void setVideoDescription(String videoDescription){
        mVideoDescription = videoDescription;
    }

    public void setCurrentStep(Step currentStep){
        mCurrentStep = currentStep;
        if (mCurrentStep != null){
            setVideoURLLink(currentStep.getVideoURL());
            setVideoDescription(currentStep.getDescription());
        }
    }

    @Override
    public void onStart() {
        Log.v(TAG, "OnStart called");
        super.onStart();
        if (!(mVideoURLLink.equals(""))){
            initializePlayer(mVideoURLLink);
        }

    }

    private void initializePlayer(String videoURL) {
        Uri videoUri = null;
        if (!(videoURL.equals(""))) {
            videoUri = Uri.parse(videoURL);

            mBandwidthMeter = new DefaultBandwidthMeter();
            mVideoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(mBandwidthMeter);
            mTrackSelector = new DefaultTrackSelector(mVideoTrackSelectionFactory);

            LoadControl loadControl = new DefaultLoadControl();

            if (mExoPlayer == null) {


                mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), mTrackSelector, loadControl);

                mPlayerView.setPlayer(mExoPlayer);


                mDataSourceFactory =
                        new DefaultDataSourceFactory
                                (Objects.requireNonNull(getContext()), Util.getUserAgent(getContext(),
                                        "BakingApp"));

                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

                mVideoSource = new ExtractorMediaSource(videoUri,
                        mDataSourceFactory, extractorsFactory, null, null);

                if (mPlaybackPosition != C.TIME_UNSET){ // meaning
                    mExoPlayer.seekTo(mPlaybackPosition);
                }

                mExoPlayer.prepare(mVideoSource);
                mExoPlayer.setPlayWhenReady(mPlayWhenReady);
            }
        }
    }

    // Release player
    private void releasePlayer() {
        if (mExoPlayer != null) {
            updatePlaybackPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
            mDataSourceFactory = null;
            mVideoSource = null;
            mTrackSelector = null;
        }
        mPlayerView.setPlayer(null);
    }

    @Override
    public void onPause() {
        Log.v(TAG, "OnPause called");
        super.onPause();
        if (mExoPlayer != null) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        Log.v(TAG, "OnStop called");
        super.onStop();
        if (mExoPlayer != null){
            releasePlayer();
        }

    }

    @Override
    public void onDetach() {
        Log.v(TAG, "OnDetach called");
        super.onDetach();
        if (mExoPlayer != null){
            releasePlayer();
        }

    }

    @Override
    public void onDestroyView() {
        Log.v(TAG, "OnDestroyView called");
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onResume() {
        Log.v(TAG, "OnResume called");
        super.onResume();
        if (mExoPlayer == null & (!mVideoURLLink.equals(""))){
            initializePlayer(mVideoURLLink);
        }

        if (mExoPlayer != null){ // start from where you stopped
            mExoPlayer.setPlayWhenReady(mPlayWhenReady);
            mExoPlayer.seekTo(mPlaybackPosition);
        }
    }

    private void updatePlaybackPosition(){
        if (mExoPlayer != null){
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mCurrentWindowIndex = mExoPlayer.getCurrentWindowIndex();
            mPlaybackPosition = mExoPlayer.getCurrentPosition();
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.v(TAG, "OnViewStateRestored called");
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null){
            mExoPlayer.seekTo(savedInstanceState.getLong(PLAY_BACK_POSITION_KEY));
            mExoPlayer.setPlayWhenReady(savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY));
        }
    }
}