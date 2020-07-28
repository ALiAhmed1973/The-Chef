package com.shaghaf.thechef.fragment;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.shaghaf.thechef.R;


public class StepDetailsFragment extends Fragment {
    private static final String VIDEO_STRING_KEY = "video";
    private static final String DESCRIPTION_STRING_KEY = "description";
    private static final String VIDEO_BOOLEAN_KEY = "video-bool";

    private TextView textViewDescription;




    private String videoString;
    private String descriptionString;

    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView exoPlayerView;



    private boolean isVideoOrNot;

    public StepDetailsFragment() {
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);
        textViewDescription = view.findViewById(R.id.tv_description);
        exoPlayerView = view.findViewById(R.id.playerView);

        ImageView imageViewThumbnail = view.findViewById(R.id.image_thumbnail);


        if(savedInstanceState!=null)
        {
            videoString=savedInstanceState.getString(VIDEO_STRING_KEY);
            descriptionString=savedInstanceState.getString(DESCRIPTION_STRING_KEY);
            isVideoOrNot=savedInstanceState.getBoolean(VIDEO_BOOLEAN_KEY);

        }

        if (videoString != null&&!videoString.isEmpty()) {
            if(isVideoOrNot) {
                initializePlayer(videoString);
                imageViewThumbnail.setVisibility(View.GONE);
            }else
            {
                StringBuilder stringBuilder = new StringBuilder(videoString);
                if(videoString.length()>3)
                {
                   CharSequence s = stringBuilder.subSequence(videoString.length()-3,videoString.length());
                   if(s.equals("mp4"))
                   {
                       initializePlayer(videoString);
                       imageViewThumbnail.setVisibility(View.GONE);
                   }
                   else
                   {
                       exoPlayerView.setVisibility(View.GONE);
                       Glide.with(this).load(videoString).into(imageViewThumbnail);
                   }
                }

            }
        } else
        {
            imageViewThumbnail.setVisibility(View.GONE);
            exoPlayerView.setVisibility(View.GONE);
        }

        if (descriptionString!=null)
        {
            textViewDescription.setText(descriptionString);
        }







        return view;
    }

    private void initializePlayer(String videoString) {
        if(exoPlayer==null) {
            Uri uri = Uri.parse(videoString);
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
            exoPlayerView.setPlayer(exoPlayer);

            String userAgent = Util.getUserAgent(getContext(),"The Chef");
            MediaSource mediaSource = new ExtractorMediaSource(uri,new DefaultDataSourceFactory(getContext(),userAgent),
                    new DefaultExtractorsFactory(),null,null);

            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer()
    {
        if(exoPlayer!=null) {
            exoPlayer.stop();
            exoPlayer.release();
        }
        exoPlayer =null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    public void setVideoString(String videoString) {
        this.videoString = videoString;
    }



    public void setDescriptionString(String descriptionString) {
        this.descriptionString = descriptionString;
    }
    public void setVideoOrNot(boolean videoOrNot) {
        isVideoOrNot = videoOrNot;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(VIDEO_STRING_KEY,videoString);
        outState.putString(DESCRIPTION_STRING_KEY,descriptionString);
        outState.putBoolean(VIDEO_BOOLEAN_KEY,isVideoOrNot);
    }
}
