package org.twinnation.doodle.controller;

import android.content.Intent;
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

    private TextView drawTextBtn;
    private TextView quitTextBtn;
    private ImageView doodlepen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        drawTextBtn = (TextView)findViewById(R.id.drawBtn);
        quitTextBtn = (TextView)findViewById(R.id.quitBtn);
        doodlepen = (ImageView)findViewById(R.id.doodlepen);

        drawTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                stopMovingPen();
                startActivity(new Intent(SplashActivity.this, DrawActivity.class));
            }
        });

        quitTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                stopMovingPen();
                SplashActivity.this.finish();
                System.exit(0);
            }
        });
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
    protected void onRestart() {
        super.onRestart();
        this.onResume();
    }


    private void startMovingPen() {
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                doodlepen.setRotation(doodlepen.getRotation() == -25 ? -5 : -25);
                handler.postDelayed(this, 200);
            }
        };
        runnable.run();
    }


    private void stopMovingPen() {
        handler.removeCallbacks(runnable);
    }

}
