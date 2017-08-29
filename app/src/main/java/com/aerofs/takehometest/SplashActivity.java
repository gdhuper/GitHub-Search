package com.aerofs.takehometest;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.AnimatorRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by gurpreet on 8/28/17.
 */

public class SplashActivity extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;
    final int sleepTime = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        //Get view element for splash screen for animation effects
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transition);
        tv.setAnimation(animation);
        iv.setAnimation(animation);
        //MainActivity intent to inject after splash screen loads up
        final Intent intent = new Intent(this, MainActivity.class);
        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(sleepTime); //sleep thread for specified number of seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                startActivity(intent); //starting main activity after splash screen is done loading
                    finish();
                }
            }
        };
        thread.start();
    }
}
