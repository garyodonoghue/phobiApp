package com.gary.spiders.activity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.VideoView;

import com.gary.spiders.R;
import com.gary.spiders.util.AlertUtility;

public class PlayStopVideoActivity extends AppCompatActivity implements ISpiderExercise  {

    private boolean started = false;
    private Handler handler = new Handler();
    ProgressBar progressBar = null;

    SharedPreferences ratings;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(started) {
                start();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_stop_video);

        final VideoView videoView = (VideoView) findViewById(R.id.videoView);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_playVideo);
        progressBar.setMax(1000);

        String path = "android.resource://" + getPackageName() + "/" + R.raw.sp1;
        videoView.setVideoURI(Uri.parse(path));

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        Button playButton = (Button) findViewById(R.id.playVideo);
        playButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    videoView.start();
                    start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    videoView.pause();
                    stop();
                }
                return true;
            }
        });

    }

    public void stop() {
        started = false;
        handler.removeCallbacks(runnable);
    }

    public void start() {
        started = true;
        this.progressBar.incrementProgressBy(2);

        if(progressBar.getProgress() >= progressBar.getMax()){
            AlertDialog alertDialog = AlertUtility.createAlert(PlayStopVideoActivity.this);
            alertDialog.show();
        }

        handler.postDelayed(runnable, 1);
    }

    @Override
    public void ratingClicked(View view) {
        RadioButton radioButton = (RadioButton) view;

        ratings = getSharedPreferences("Ratings", 0);
        SharedPreferences.Editor editor = ratings.edit();
        editor.putString(this.getLocalClassName() + "_" + System.currentTimeMillis(), radioButton.getText().toString());

        editor.commit();
    }
}