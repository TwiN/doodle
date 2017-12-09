package org.twinnation.doodle.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.twinnation.doodle.R;


public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;

    private ImageView doodlePen;
    private ImageView soundIcon;

    private MediaPlayer mp;

    private boolean isSoundOn;
    private SharedPreferences prefs;


    /////////////////////
    // EVENT LISTENERS //
    /////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        prefs = getApplicationContext().getSharedPreferences("CONFIG", 0);
        isSoundOn = prefs.getBoolean("isSoundOn", false);
        initComponentsAndListeners();
        startMovingPen();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (handler != null) {
            stopMovingPen();
        }
        startMovingPen();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        isSoundOn = prefs.getBoolean("isSoundOn", false);
        if (isSoundOn) {
            if (mp == null) {
                mp = MediaPlayer.create(this, R.raw.background_music);
                mp.setLooping(true);
                mp.seekTo(prefs.getInt("music_time", 0));
            }
            if (hasFocus) {
                mp.start();
            } else {
                prefs.edit().putInt("music_time", mp.getCurrentPosition()).apply();
                mp.stop();
                mp.release();
                mp = null;
            }
        }
    }


    //////////
    // MISC //
    //////////


    private void initComponentsAndListeners() {
        TextView drawBtn = (TextView)findViewById(R.id.drawBtn);
        TextView quitBtn = (TextView)findViewById(R.id.quitBtn);
        doodlePen = (ImageView)findViewById(R.id.doodlepen);
        soundIcon = (ImageView)findViewById(R.id.sound);
        mp = MediaPlayer.create(this, R.raw.background_music);
        mp.setLooping(true);

        soundIcon.setImageResource(isSoundOn ? R.mipmap.music_on : R.mipmap.music_off);

        drawBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                stopMovingPen();
                prefs.edit().putInt("music_time", mp.getCurrentPosition()).apply();
                Intent intent = new Intent(SplashActivity.this, DrawActivity.class);
                startActivity(intent);
            }
        });
        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                stopMovingPen();
                mp.stop();
                mp.release();
                SplashActivity.this.finish();
                System.exit(0);
            }
        });
        soundIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSoundOn = !isSoundOn;
                prefs.edit().putBoolean("isSoundOn", isSoundOn).apply();
                if (isSoundOn) {
                    soundIcon.setImageResource(R.mipmap.music_on);
                    mp.start();
                } else {
                    soundIcon.setImageResource(R.mipmap.music_off);
                    mp.pause();
                }
            }
        });
    }


    private void startMovingPen() {
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                doodlePen.setRotation(doodlePen.getRotation() == -25 ? -5 : -25);
                handler.postDelayed(this, 200);
            }
        };
        runnable.run();
    }


    private void stopMovingPen() {
        handler.removeCallbacks(runnable);
    }

}
