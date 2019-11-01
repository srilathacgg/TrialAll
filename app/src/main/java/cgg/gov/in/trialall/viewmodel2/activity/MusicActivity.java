package cgg.gov.in.trialall.viewmodel2.activity;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cgg.gov.in.trialall.R;
import cgg.gov.in.trialall.viewmodel2.fragments.MusicFragment;

public class MusicActivity extends AppCompatActivity {

    public int oneTimeOnly = 0;
    TextView name, tv_starttime, tv_finaltime;
    ImageView img;
    ImageButton forward, backward, pause, play;
    int position;
    MediaPlayer mp;
    String path, filename;
    SeekBar seekbar;
    int startTime, forwardTime = 5000, backwardTime = 5000, finalTime;
    private Handler myHandler = new Handler();
    Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mp.getCurrentPosition();
            seekbar.setProgress(startTime);
            tv_starttime.setText(getTime(TimeUnit.MILLISECONDS.toMinutes((long) startTime), TimeUnit.MILLISECONDS.toSeconds((long) startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))));
            myHandler.postDelayed(this, 100);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        position = getIntent().getIntExtra("POSITION", -1);
        filename = getIntent().getStringExtra("NAME");

        name = findViewById(R.id.name);
        img = findViewById(R.id.img);
        forward = findViewById(R.id.forward);
        backward = findViewById(R.id.backward);
        pause = findViewById(R.id.pause);
        play = findViewById(R.id.play);
        seekbar = findViewById(R.id.seekbar);
        tv_starttime = findViewById(R.id.tv_starttime);
        tv_finaltime = findViewById(R.id.tv_finaltime);

        name.setText(MusicFragment.fileList.get(position).getName());
        path = MusicFragment.fileList.get(position).getPath();
        mp = new MediaPlayer();
        finalTime = mp.getDuration();
        mp.setLooping(false);

        try {
            mp.setDataSource(path);
            mp.prepare();
            Log.d("duration", String.valueOf(mp.getDuration()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = mp.getCurrentPosition();
                int temp = startTime;
                Log.d("FinalTime", String.valueOf(finalTime));
                Log.d("temp", String.valueOf(temp));
                if ((temp + forwardTime) <= finalTime) {
                    startTime = startTime + forwardTime;
                    mp.seekTo(startTime);
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot jump forward 5 seconds", Toast.LENGTH_SHORT).show();

                }

            }
        });
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = mp.getCurrentPosition();
                int temp1 = startTime;
                if ((temp1 - backwardTime) > 0) {
                    startTime = startTime - backwardTime;
                    mp.seekTo(startTime);

                } else {
                    Toast.makeText(getApplicationContext(), "Cannot jump backward 5 seconds", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.start();

                finalTime = mp.getDuration();
                startTime = mp.getCurrentPosition();
                if (oneTimeOnly == 0) {
                    seekbar.setMax(finalTime);
                    oneTimeOnly = 1;
                }
                tv_finaltime.setText(getTime(TimeUnit.MILLISECONDS.toMinutes((long) finalTime), TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime))));

                tv_starttime.setText(getTime(TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))));
                seekbar.setProgress(startTime);
                myHandler.postDelayed(UpdateSongTime, 100);

            }
        });


        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mp != null && fromUser) {
                    mp.seekTo(progress);
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pause.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);
                mp.pause();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
                mp.start();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mp.stop();
    }

    public String getTime(long min, long sec) {
        String time = " ";

        if (min < 10) time = "0";
        time += min + ":";
        if (sec < 10) time += "0";
        time += sec;

        return time;
    }
}
