package com.advancedandroid.quanlysach_newui.Splash_LoginScreen_ActionSetting;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.advancedandroid.quanlysach_newui.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread couldow = new Thread(){
            public void run(){
                try {
                    sleep(1000);
                }catch (Exception e){

                }finally {
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        };
        couldow.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
