package com.example.utrixapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class full_image extends AppCompatActivity {
    TouchImageView fullim;
    ScaleGestureDetector scaleGestureDetector;
    float scaleFactor =1.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        fullim=new TouchImageView(this);
        fullim.setMaxZoom(4f);
        Glide.with(getApplicationContext()).load(MyAdapter.transim).into(fullim);
        setContentView(fullim);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(),clicked_item.class);
        startActivity(i);
        finish();
    }



    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *=scaleGestureDetector.getScaleFactor();
            fullim.setScaleX(scaleFactor);
            fullim.setScaleY(scaleFactor);
            return true;
        }
    }
}