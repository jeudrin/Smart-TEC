package com.nfc.redes.nfc_smarttags;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity
{
    private static int splashInterval = 3000;

    ImageView rotate_image;
    RotateAnimation rotate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        rotate_image =(ImageView) findViewById(R.id.ivGift);
        rotate = new RotateAnimation(30, 360, Animation.RELATIVE_TO_SELF, 0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null)
        {
            actionBar.hide();
        }

        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent i = new Intent(SplashScreen.this, SmartTEC.class);

                startActivity(i);

                this.finish();
            }

            private void finish()
            {

            }
        }, splashInterval);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //rotate.setDuration(rotate.INFINITE);
        //rotate_image.startAnimation(rotate);
    }


}