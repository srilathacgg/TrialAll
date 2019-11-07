package cgg.gov.in.trialall.viewmodel2.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import cgg.gov.in.trialall.R;
import cgg.gov.in.trialall.viewmodel2.fragments.VideoFragment;

public class VideoActivity extends AppCompatActivity {
    int position;
    VideoView videoView;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        position = getIntent().getIntExtra("POSITION", -1);
        videoView = findViewById(R.id.videoview);
        path = VideoFragment.fileList.get(position).getPath();
        Log.d("video", path);
        videoView.setVideoPath(String.valueOf(path));
        MediaController mediaController = new MediaController(VideoActivity.this);
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        videoView.setVisibility(View.VISIBLE);
        videoView.start();
    }
}
