package com.ssf.chen.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ssf.chen.customview.widget.DragPointView;
import com.ssf.chen.customview.widget.SpeedometerView;

public class MainActivity extends AppCompatActivity {
    private SpeedometerView speedometerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speedometerView = findViewById(R.id.speedometerView);
        speedometerView.setCurrent(2000);
        speedometerView.setMaxSize(5000);

    }
}
