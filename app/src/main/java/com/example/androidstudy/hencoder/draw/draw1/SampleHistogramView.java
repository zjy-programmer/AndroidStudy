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
 * ClassName: SampleHistogramView
 * CreateDate: 2021/7/1 10:21 上午
 * Author: zjy
 * Description: java类作用描述
 */
public class SampleHistogramView extends View {
    private Paint mPaint;
    private int x;

    public SampleHistogramView(Context context) {
        this(context, null);
    }

    public SampleHistogramView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SampleHistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.LTGRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        x = 100;
        canvas.drawRect(x, 995, x + 100, 1000, mPaint);
        mPaint.setColor(Color.GREEN);
        x += 130;
        canvas.drawRect(x, 950, x + 100, 1000, mPaint);
        x += 130;
        canvas.drawRect(x, 950, x + 100, 1000, mPaint);
        x += 130;
        canvas.drawRect(x, 750, x + 100, 1000, mPaint);
        x += 130;
        canvas.drawRect(x, 650, x + 100, 1000, mPaint);
        x += 130;
        canvas.drawRect(x, 600, x + 100, 1000, mPaint);
        x += 130;
        canvas.drawRect(x, 770, x + 100, 1000, mPaint);
    }
}
