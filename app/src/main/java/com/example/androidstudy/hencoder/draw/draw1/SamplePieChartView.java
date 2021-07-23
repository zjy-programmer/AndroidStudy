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
 * ClassName: SamplePieChartView
 * CreateDate: 2021/7/1 4:49 下午
 * Author: zjy
 * Description: java类作用描述
 */
public class SamplePieChartView extends View {
    private Paint mPaint;

    public SamplePieChartView(Context context) {
        this(context, null);
    }

    public SamplePieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SamplePieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);
        canvas.drawArc(100, 100, 600, 600, -60, 60, true, mPaint);
        mPaint.setColor(Color.CYAN);
        canvas.drawArc(100, 100, 600, 600, 2, 10, true, mPaint);
        mPaint.setColor(Color.DKGRAY);
        canvas.drawArc(100, 100, 600, 600, 14, 10, true, mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawArc(100, 100, 600, 600, 26, 50, true, mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(100, 100, 600, 600, 78, 95, true, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawArc(90, 90, 590, 590, 174, 120, true, mPaint);
    }

}
