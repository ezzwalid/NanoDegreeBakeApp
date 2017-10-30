package com.ezz.bakeappudacity.steps.ui.fragments;


import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ezz.bakeappudacity.R;
import com.ezz.bakeappudacity.helpers.Utils;
import com.ezz.bakeappudacity.recipe.model.Step;
import com.ezz.bakeappudacity.steps.ui.listener.ControlStepPager;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailsPagerFragment extends Fragment implements View.OnClickListener{

    SimpleExoPlayer exoPlayer;

    private final String PLAY_BACK_POSITION_KEY = "playBackPositionKey";
    long playbackPosition;
    private final String CURRENT_WINDOW_KEY = "currentWindowKey";
    int currentWindow;

    @BindView(R.id.step_details_exo_player)
    SimpleExoPlayerView simpleExoPlayerView;
    @BindView(R.id.step_instructions)
    TextView instructions_tv;
    @BindView(R.id.step_details_image)
    ImageView imageView;
    @BindView(R.id.instructions_container)
    View instructionsContainer;
    @BindView(R.id.next_page)
    ImageButton nextPage;
    @BindView(R.id.previous_page)
    ImageButton previousPage;

    private static final String STEP_KEY = "STEP_KEY";
    Step step;


    public static Fragment newInstance(Step step){
        StepDetailsPagerFragment fragment = new StepDetailsPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STEP_KEY, step);
        fragment.setArguments(bundle);
        return fragment;
    }



    public StepDetailsPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step_details_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        step = getArguments().getParcelable(STEP_KEY);
        restoreInstanceState(savedInstanceState);
        setBtnsOnclick();
        bindData();
    }

    private void setBtnsOnclick(){
        nextPage.setOnClickListener(this);
        previousPage.setOnClickListener(this);
    }

    private void bindData(){
        instructions_tv.setText(step.getDescription());
        if (step.getVideoURL().isEmpty()){
            simpleExoPlayerView.setVisibility(View.GONE);
            if (step.getThumbnailURL().isEmpty()){
                imageView.setVisibility(View.GONE);
                instructionsContainer.setVisibility(View.VISIBLE);
            }
            else {
                Glide.with(getContext()).load(step.getThumbnailURL()).into(imageView);
            }
        }
        else {
            imageView.setVisibility(View.GONE);
        }
    }

    private void initExoPlayer(){
        exoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        simpleExoPlayerView.setPlayer(exoPlayer);

        exoPlayer.setPlayWhenReady(true);
        exoPlayer.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse(step.getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);
        exoPlayer.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initExoPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Utils.isLandscape(getContext()) && !Utils.isTablet(getContext())) {
            hideSystemUi();
        }
        if ((Util.SDK_INT <= 23 || exoPlayer == null)) {
            initExoPlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        simpleExoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STEP_KEY, step);
        outState.putLong(PLAY_BACK_POSITION_KEY, playbackPosition);
        outState.putInt(CURRENT_WINDOW_KEY, currentWindow);
    }

    private void restoreInstanceState(Bundle bundle){
        if (bundle != null){
            step = bundle.getParcelable(STEP_KEY);
            playbackPosition = bundle.getLong(PLAY_BACK_POSITION_KEY);
            currentWindow = bundle.getInt(CURRENT_WINDOW_KEY);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == nextPage){
            ((ControlStepPager)getParentFragment()).scrollToNextPage();
        }
        else if (view == previousPage){
            ((ControlStepPager)getParentFragment()).scrollToPreviousPage();
        }
    }
}
