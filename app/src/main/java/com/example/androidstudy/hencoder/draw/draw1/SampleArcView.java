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
 * ClassName: SampleArcView
 * CreateDate: 2021/6/29 6:59 下午
 * Author: zjy
 * Description: java类作用描述
 */
public class SampleArcView extends View {
    private Paint mPaint;

    public SampleArcView(Context context) {
        this(context, null);
    }

    public SampleArcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SampleArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawArc(100, 100, 472, 352, 180, 60, false, mPaint);
//        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawArc(100, 100, 472, 352, 250, 100, true, mPaint);
//        mPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawArc(100, 200, 472, 400, 0, 180, false, mPaint);

        mPaint.setStyle(Paint.Style.FILL); // 填充模式
        canvas.drawArc(200, 100, 800, 500, -110, 100, true, mPaint); // 绘制扇形
        canvas.drawArc(200, 100, 800, 500, 20, 140, false, mPaint); // 绘制弧形
        mPaint.setStyle(Paint.Style.STROKE); // 画线模式
        canvas.drawArc(200, 100, 800, 500, 180, 60, false, mPaint); // 绘制不封口的弧形
    }
}
