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
 * ClassName: SampleOvalView
 * CreateDate: 2021/7/1 11:15 上午
 * Author: zjy
 * Description: java类作用描述
 */
public class SampleOvalView extends View {
    private Paint mPaint;

    public SampleOvalView(Context context) {
        this(context, null);
    }

    public SampleOvalView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SampleOvalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(200, 200, 550, 400, mPaint);
    }
}
