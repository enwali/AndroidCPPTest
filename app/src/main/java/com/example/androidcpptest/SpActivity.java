package com.example.androidcpptest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.core.splashscreen.SplashScreen;

public class SpActivity extends ComponentActivity {

    private static boolean isReady = true;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        SplashScreen splash = SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_sp);

        splash.setKeepOnScreenCondition(() -> {
            startActivity(new Intent(context, MainActivity.class));
            return isReady;
        });

//        View contentView = findViewById(android.R.id.content);
//        contentView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){
//            @Override
//            public boolean onPreDraw() {
//                //Return true to proceed with the current drawing pass, or false to cancel.
//                System.out.println("onPreDraw been called! ");
//                return isReady;
//            }
//        });
    }
}