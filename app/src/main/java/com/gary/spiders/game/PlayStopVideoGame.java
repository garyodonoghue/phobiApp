package com.gary.spiders.game;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.gary.spiders.R;
import com.gary.spiders.util.AlertUtility;

public class PlayStopVideoGame extends Game {

    private boolean started = false;
    private Handler handler = new Handler();
    ProgressBar progressBar = null;
    int videoResourceId;

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

        if(initialAssessment){
            ImageButton giveUpBtn = (ImageButton) findViewById(R.id.giveUpButton);
            giveUpBtn.setVisibility(View.INVISIBLE);
        }

        GameResourceLoader resourceLoader = new GameResourceLoader(this);
        videoResourceId = resourceLoader.getResource(super.category);

        final VideoView videoView = (VideoView) findViewById(R.id.videoView);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_playVideo);
        progressBar.setMax(1000);

        String path = "android.resource://" + getPackageName() + "/" + videoResourceId;
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
        this.progressBar.incrementProgressBy(5);

        String s = getIntent().getStringExtra("category");
        final GameCategory category = GameCategory.valueOf(s);

        if(progressBar.getProgress() >= progressBar.getMax()){
            stop();
            this.progressBar.setProgress(0);
            AlertDialog alertDialog = AlertUtility.createGameCompletedAlert(PlayStopVideoGame.this, category);
            alertDialog.show();

        }
        else{
            handler.postDelayed(runnable, 1);
        }
    }
}
