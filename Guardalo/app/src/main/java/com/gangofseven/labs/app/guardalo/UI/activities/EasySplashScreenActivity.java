package com.gangofseven.labs.app.guardalo.UI.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gangofseven.labs.app.guardalo.R;
import com.gangofseven.labs.app.guardalo.UI.activities.MainActivity;

import gr.net.maroulis.library.EasySplashScreen;

public class EasySplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View easySplashScreenView = new EasySplashScreen(this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(1000)
                .withBackgroundResource(R.color.colorPrimary)
                //.withHeaderText("Header")
                //.withFooterText("Copyright 2016")
                //.withBeforeLogoText("My cool company")
                .withLogo(R.mipmap.ic_chancho)
                //.withAfterLogoText("Some more details")
                .create();

        setContentView(easySplashScreenView);
/*        setContentView(R.layout.activity_easy_splash_screen);*/
    }
}
