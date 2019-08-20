package com.chen.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.customview.view.SortActivity;
import com.chen.customview.widget.DragPointView;
import com.chen.customview.widget.RotateOvlView;
import com.chen.customview.widget.SideBar;
import com.chen.customview.widget.Snacker;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private Button btnReturn;
    private LinearLayout llCustom;          //自定义

    private LinearLayout llSnacker;

    private RotateOvlView rotateOvlView;    //旋转表盘
    private DragPointView dragPointView;
    private SideBar sideBar;

    private Button btnDragPoint;
    private Button btnRotateOvl;
    private Button btnSideBar;
    private Button btnSnacker;
    private Button btnSort;

    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnReturn = findViewById(R.id.btnReturn);
        llCustom = findViewById(R.id.llCustom);
        llSnacker = findViewById(R.id.llSnacker);
        rotateOvlView = findViewById(R.id.rotateOvlView);
        dragPointView = findViewById(R.id.dragPointView);
        sideBar = findViewById(R.id.sideBar);
        btnDragPoint = findViewById(R.id.btnDragPoint);
        btnRotateOvl = findViewById(R.id.btnRotateOvl);
        btnSideBar = findViewById(R.id.btnSideBar);
        btnSnacker = findViewById(R.id.btnSnacker);
        btnSort = findViewById(R.id.btnSort);
        tvMessage = findViewById(R.id.tvMessage);
        findViewById(R.id.btnSuccess).setOnClickListener(this);
        findViewById(R.id.btnError).setOnClickListener(this);
        findViewById(R.id.btnWarning).setOnClickListener(this);
        findViewById(R.id.btnCustom).setOnClickListener(this);


        btnReturn.setOnClickListener(this);

        btnDragPoint.setOnClickListener(this);
        btnRotateOvl.setOnClickListener(this);
        btnSideBar.setOnClickListener(this);
        btnSnacker.setOnClickListener(this);
        btnSort.setOnClickListener(this);

        initListener();
    }


    /**
     * 初始化控件的方法
     */
    private void initListener() {
        /**
         * DragPointView
         */
        dragPointInterface();
        /**
         * RotateOvlView
         */
        rotateOvlInterface();
    }


    /**
     * 隐藏按钮
     */
    private void btnVisibility() {
        btnDragPoint.setVisibility(View.GONE);
        btnRotateOvl.setVisibility(View.GONE);
        btnSideBar.setVisibility(View.GONE);
        btnSnacker.setVisibility(View.GONE);
        btnSort.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReturn:
                llSnacker.setVisibility(View.GONE);
                rotateOvlView.setVisibility(View.GONE);
                dragPointView.setVisibility(View.GONE);
                sideBar.setVisibility(View.GONE);
                tvMessage.setVisibility(View.GONE);

                btnDragPoint.setVisibility(View.VISIBLE);
                btnRotateOvl.setVisibility(View.VISIBLE);
                btnSideBar.setVisibility(View.VISIBLE);
                btnSnacker.setVisibility(View.VISIBLE);
                btnSort.setVisibility(View.VISIBLE);
                break;
            case R.id.btnSuccess:       //Snacker
                Snacker.with(this).setMessage("这是默认的Success!", Color.WHITE).sneakSuccess();
                break;
            case R.id.btnError:         //Snacker
                Snacker.with(this).setMessage("这是默认的Error!", Color.WHITE).sneakError();
                break;
            case R.id.btnWarning:        //Snacker
                Snacker.with(this).setMessage("这是默认的Warning!", Color.WHITE).sneakWarning();
                break;
            case R.id.btnCustom:         //Snacker
                snack();
                break;
            case R.id.btnDragPoint:         //DragPointView
                btnVisibility();
                dragPointView.setVisibility(View.VISIBLE);
                break;
            case R.id.btnRotateOvl:         //DragPointView
                btnVisibility();
                rotateOvlView.setVisibility(View.VISIBLE);
                tvMessage.setVisibility(View.VISIBLE);
                break;
            case R.id.btnSideBar:         //SideBar
                btnVisibility();
                sideBar.setVisibility(View.VISIBLE);
                break;
            case R.id.btnSnacker:         //Snacker
                btnVisibility();
                llSnacker.setVisibility(View.VISIBLE);
                break;
            case R.id.btnSort:         //sort
                startActivity(new Intent(MainActivity.this, SortActivity.class));
                break;
        }
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

    /**
     * DragPointView
     */
    private void dragPointInterface() {
        dragPointView.setBackgroundColor(Color.RED);
        dragPointView.setDragListencer(new DragPointView.OnDragListencer() {
            @Override
            public void onDragOut() {
                Toast.makeText(MainActivity.this, "DragPointView：拉断了", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * RotateOvlView
     */
    private void rotateOvlInterface() {
        rotateOvlView.setWeight(100);  //初始默认值
        rotateOvlView.setOnWeightListener(new RotateOvlView.OnWeightListener() {
            @Override
            public void onWeightSet(int weight) {
                tvMessage.setText(" weight ：" + weight);
            }
        });
    }
}
