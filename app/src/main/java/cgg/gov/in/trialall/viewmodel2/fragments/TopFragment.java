package cgg.gov.in.trialall.viewmodel2.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import cgg.gov.in.trialall.R;

/**
 * Created by user on 19-02-2019.
 */

public class TopFragment extends Fragment {
    static VideoView videoView;
    String path = "/storage/emulated/0/WhatsApp/Media/.Statuses/e66a10969e0040699e09fd883c38062e.mp4";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoView = view.findViewById(R.id.videoview);
        videoView.setVideoPath(String.valueOf(path));
        MediaController mediaController = new MediaController(getActivity());
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        videoView.setVisibility(View.VISIBLE);
        videoView.start();

//        if(MainActivity.isOpened) {
//            videoView.start();
//        }else {
//            videoView.pause();
//        }
    }
}
