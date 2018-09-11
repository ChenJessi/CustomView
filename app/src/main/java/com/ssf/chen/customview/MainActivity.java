package com.ssf.chen.customview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ssf.chen.customview.widget.DragPointView;
import com.ssf.chen.customview.widget.Snacker;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnSuccess).setOnClickListener(this);
        findViewById(R.id.btnError).setOnClickListener(this);
        findViewById(R.id.btnWarning).setOnClickListener(this);
        findViewById(R.id.btnCustom).setOnClickListener(this);
    }

    /**
     * Snacker
     */
    private void snack() {
        Snacker.with(this)
                .setMessage("这是自定义的信息!", Color.WHITE)
                .setIcon(R.drawable.ic_error)
                .setDuration(3000)
                .setOnSneakerClickListener(new Snacker.OnSneakerClickListener() {
                    @Override
                    public void onSneakerClick(View view) {
                        Snacker.hide();
                    }
                })
                .sneak(R.color.snack);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSuccess:
                Snacker.with(this).setMessage("这是默认的Success!", Color.WHITE).sneakSuccess();
                break;
            case R.id.btnError:
                Snacker.with(this).setMessage("这是默认的Error!", Color.WHITE).sneakError();
                break;
            case R.id.btnWarning:
                Snacker.with(this).setMessage("这是默认的Warning!", Color.WHITE).sneakWarning();
                break;
            case R.id.btnCustom:
                snack();
                break;
        }
    }
}
