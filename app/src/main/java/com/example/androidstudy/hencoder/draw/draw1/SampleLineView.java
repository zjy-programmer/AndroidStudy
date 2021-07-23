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
 * ClassName: SampleLineView
 * CreateDate: 2021/7/1 10:57 上午
 * Author: zjy
 * Description: java类作用描述
 */
public class SampleLineView extends View {
    private Paint mPaint;

    public SampleLineView(Context context) {
        this(context, null);
    }

    public SampleLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SampleLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(200, 100, 500, 300, mPaint);
    }
}
