package com.example.androidstudy.hencoder.draw.draw1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.hencoder.draw.draw1
 * ClassName: SamplePathView
 * CreateDate: 2021/7/1 11:18 上午
 * Author: zjy
 * Description: java类作用描述
 */
public class SamplePathView extends View {
    private Paint mPaint;
    private Path mPath;

    public SamplePathView(Context context) {
        this(context, null);
    }

    public SamplePathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SamplePathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
//        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.addArc(100, 100, 200, 200, 140, 220);
        mPath.arcTo(200, 100, 300, 200, 180, 220, false);
        mPath.lineTo(200, 300);
        canvas.drawPath(mPath, mPaint);
    }
}
