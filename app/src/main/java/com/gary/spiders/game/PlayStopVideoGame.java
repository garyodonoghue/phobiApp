package com.gary.spiders.game;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.gary.spiders.R;
import com.gary.spiders.enums.GameCategory;
import com.gary.spiders.util.AlertUtility;

public class PlayStopVideoGame extends BaseGame {

    private boolean started = false;
    private Handler handler = new Handler();
    ProgressBar progressBar = null;
    int videoResourceId;
    int progress = 0;

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

        final TextView textView = (TextView) findViewById(R.id.videoTimer);
        CountDownTimer timer = super.setupGameTimer(textView, this, 15000);

        super.presentGameInfoPopup(this, "For this game, " +
                "hold down the Play Video button until the progress bar reaches the end", timer);

        GameResourceLoader resourceLoader = new GameResourceLoader(this);

        if(super.category == null){
            super.category = GameCategory.VIDEOS_HIGH;
        }
        videoResourceId = resourceLoader.getResource(super.category);

        final VideoView videoView = (VideoView) findViewById(R.id.videoView);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_playVideo);
        progressBar.setMax(200);

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
        this.progressBar.incrementProgressBy(1);
        progress = progress + 1;

        if(progress > progressBar.getMax()+1){
            stop();
            super.stopTimer();
            AlertDialog alertDialog = AlertUtility.createGameCompletedAlert(PlayStopVideoGame.this);
            alertDialog.show();
        }
        else{
            handler.postDelayed(runnable, 1);
        }
    }

    public void giveUp(View v){
        super.giveUp(v);
    }
}