package com.example.gadsleaderboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.Resource;

import java.util.concurrent.Executor;

public class LoaderActivity extends Activity {

    private Thread mSplashThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);

        ImageView splashScreenImage = findViewById(R.id.splash_img);
        splashScreenImage.setImageResource(R.drawable.gads);

        final LoaderActivity sPlashScreen = this;

        // The thread to wait for splash screen events
        mSplashThread =  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        // Wait given period of time or exit on touch
                        wait(3000);
                    }
                }
                catch(InterruptedException ex){
                }
                finish();
                // Run next activity which is your GameActivity
                Intent intent = new Intent();
                intent.setClass(sPlashScreen, MainActivity.class);

                startActivity(intent);
            }
        };
        mSplashThread.start();
    }
}
