package com.example.androidstudy.hencoder.draw.draw1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.hencoder.draw.draw1
 * ClassName: SampleCircleView
 * CreateDate: 2021/6/30 6:15 下午
 * Author: zjy
 * Description: java类作用描述
 */
public class SampleCircleView extends View {
    private Paint mPaint;

    public SampleCircleView(Context context) {
        this(context, null);
    }

    public SampleCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SampleCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(200, 200, 100, mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(200, 450, 100, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(450, 200, 100, mPaint);
        mPaint.setStrokeWidth(50);
        canvas.drawCircle(450, 450, 100, mPaint);
    }
}
