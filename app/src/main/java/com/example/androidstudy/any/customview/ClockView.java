package com.example.androidstudy.any.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.example.baselibrary.util.WidgetUtil;

import androidx.annotation.Nullable;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.customview
 * ClassName: ClockView
 * CreateDate: 2021/6/10 3:24 下午
 * Author: zjy
 * Description: 练习画一个钟表View
 */
public class ClockView extends View {
    private Paint mPaint;
    private int mScreenWidth;
    private int mScreenHeight;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);

        mScreenWidth = WidgetUtil.getScreenWidth();
        mScreenHeight = WidgetUtil.getScreenHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOuterCircle(canvas);
        drawTimeScale(canvas);
        drawTime(canvas);
        drawPointer(canvas);
    }

    /**
     * 绘制表盘的圆形
     */
    private void drawOuterCircle(Canvas canvas) {
        mPaint.setStrokeWidth(6);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mScreenWidth / 2.0f,
                mScreenHeight / 2.0f,
                mScreenWidth / 2.0f,
                mPaint);
    }

    /**
     * 绘制时间刻度
     */
    private void drawTimeScale(Canvas canvas) {
        canvas.save();
        for (int i = 0; i < 12; i++) {
            if (i == 0 || i == 3 || i == 6 || i == 9) {
                canvas.drawLine(mScreenWidth / 2.0f, mScreenHeight / 2.0f - mScreenWidth / 2.0f, mScreenWidth / 2.0f, mScreenHeight / 2.0f - mScreenWidth / 2.0f + WidgetUtil.dp2px(15), mPaint);
            } else {
                canvas.drawLine(mScreenWidth / 2.0f, mScreenHeight / 2.0f - mScreenWidth / 2.0f, mScreenWidth / 2.0f, mScreenHeight / 2.0f - mScreenWidth / 2.0f + WidgetUtil.dp2px(5), mPaint);
            }
            canvas.rotate(30, mScreenWidth / 2.0f, mScreenHeight / 2.0f);
        }
        canvas.restore();
    }

    /**
     * 绘制时间文字
     */
    private void drawTime(Canvas canvas) {
        canvas.save();
        for (int i = 0; i < 12; i++) {
            mPaint.setTextSize(WidgetUtil.sp2px(16));
            mPaint.setTypeface(Typeface.MONOSPACE);
            String time = i == 0 ? "12" : "" + i;
            canvas.drawText(time, mScreenWidth / 2.0f - mPaint.measureText(time) / 2.0f, mScreenHeight / 2.0f - mScreenWidth / 2.0f + WidgetUtil.dp2px(30), mPaint);
            canvas.rotate(30, mScreenWidth / 2.0f, mScreenHeight / 2.0f);
        }
        canvas.restore();
    }

    /**
     * 画时针 分针
     */
    private void drawPointer(Canvas canvas) {
        // 时针
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);
        canvas.drawLine(mScreenWidth / 2.0f, mScreenHeight / 2.0f, mScreenWidth / 2.0f + WidgetUtil.dp2px(60), mScreenHeight / 2.0f, mPaint);
        mPaint.setStrokeWidth(6);
        // 分针
        canvas.drawLine(mScreenWidth / 2.0f, mScreenHeight / 2.0f, mScreenWidth / 2.0f, mScreenHeight / 2.0f + WidgetUtil.dp2px(90), mPaint);
    }
}
