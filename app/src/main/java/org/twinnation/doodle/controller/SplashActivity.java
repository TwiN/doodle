package org.twinnation.doodle.controller;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.twinnation.doodle.R;
import org.twinnation.doodle.view.CanvasView;


public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;

    private ImageView doodlePen;


    /////////////////////
    // EVENT LISTENERS //
    /////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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
    protected void onRestart() {
        super.onRestart();
        this.onResume();
    }


    //////////
    // MISC //
    //////////


    private void initComponentsAndListeners() {
        TextView drawBtn = (TextView)findViewById(R.id.drawBtn);
        TextView quitBtn = (TextView)findViewById(R.id.quitBtn);
        doodlePen = (ImageView)findViewById(R.id.doodlepen);

        drawBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                stopMovingPen();
                Intent intent = new Intent(SplashActivity.this, DrawActivity.class);
                intent.putExtra("date_drawing_start", System.currentTimeMillis());
                startActivity(intent);
            }
        });
        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                stopMovingPen();
                SplashActivity.this.finish();
                System.exit(0);
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
