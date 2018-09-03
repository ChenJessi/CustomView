package com.ssf.chen.customview.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;

/**
 * Created by lufadong on 2018/1/16.
 * 简易仪表盘
 */

public class SpeedometerView extends View {
    private int maxSize = 10000;
    private double m;
    WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
    int mWidth = wm.getDefaultDisplay().getWidth();
    int mHeight = wm.getDefaultDisplay().getHeight() / 2;

    public ValueAnimator animator;
    private float animatedValue;
    public void setCurrent(double current) {
        m = current;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public SpeedometerView(Context context) {
        super(context);
    }

    public SpeedometerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SpeedometerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(30);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        canvas.drawText("0", 120 - paint.measureText("0") / 2, mHeight / 2 + 30, paint);
        canvas.drawText(maxSize+"", mWidth - 120 - paint.measureText(maxSize+"") / 2, mHeight / 2 + 30, paint);
        canvas.rotate(2f, mWidth / 2, mHeight / 2);

        paint.setTextSize(40);
        canvas.drawText("本月", mWidth / 2 - paint.measureText("本月") / 2, mHeight / 2 - (mWidth / 2 - 120) + 100, paint);
        canvas.drawText("已完成里程数(公里)", mWidth / 2 - paint.measureText("已完成里程数(公里)") / 2, mHeight / 2 - (mWidth / 2 - 120) + 170, paint);
        paint.setTextSize(60);
        paint.setColor(Color.RED);
        canvas.drawText(m + "", mWidth / 2 - paint.measureText(m + "") / 2, mHeight / 2 - (mWidth / 2 - 120) + 260, paint);

        //画刻度线
        Paint paint1 = new Paint();
        paint1.setStrokeWidth(8);
        paint1.setTextSize(15);
        paint1.setAntiAlias(true);
        paint1.setColor(Color.parseColor("#0b93d4"));
        for (int i = 0; i < 48; i++) {
            canvas.drawLine(100, mHeight / 2, 140, mHeight / 2 , paint1);
            canvas.rotate(3.75f, mWidth / 2, mHeight / 2);
        }
        canvas.rotate(180, mWidth / 2, mHeight / 2);

        Paint paint3 = new Paint();
        paint3.setColor(Color.RED);
        paint3.setAntiAlias(true);
        paint3.setTextSize(30);
        paint3.setStrokeWidth(8);

        Paint paint2 = new Paint();
        paint2.setStrokeWidth(8);
        paint2.setTextSize(15);
        paint2.setAntiAlias(true);
        paint2.setColor(Color.RED);

        for (int i = 0; i < (int)animatedValue; i++) {
            if (i == (int)animatedValue - 1) {
                paint2.setColor(Color.parseColor("#ff8500"));
            }else {
                paint2.setColor(Color.RED);
            }
            canvas.drawLine(100, mHeight / 2, 140, mHeight / 2 + 2, paint2);
            canvas.rotate(3.75f, mWidth / 2, mHeight / 2);
        }
        canvas.drawLine(200, mHeight / 2, mWidth/2, mHeight / 2 + 2, paint3);
        canvas.rotate(3.75f, mWidth / 2, mHeight / 2);

    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        setAnimator();
    }

    private void setAnimator(){
        if(animator!=null&&animator.isRunning()){
            animator.cancel();
            animator.start();
        }else{
            final int max =  m > maxSize ? 48 : (int) ((m / maxSize) * 48);

            animator = ValueAnimator.ofFloat(0,5).setDuration(2000);
            animator.setInterpolator(new BounceInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                     animatedValue =(float) animation.getAnimatedValue();
                    if (animatedValue == max || animatedValue > max){
                        animator.cancel();
                        return;
                    }
                    invalidate();
                }
            });
            animator.setStartDelay(500);
            animator.start();
        }

    }
}
