package com.example.androidstudy.any.customview.shader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.customview.shader
 * ClassName: SweepGradientView
 * CreateDate: 2021/6/9 2:31 下午
 * Author: zjy
 * Description: SweepGradient shader练习 扫描渲染 跟雷达扫描似的
 */
public class SweepGradientView extends View {
    private Paint mPaint;
    private SweepGradient mSweepGradient;
    private int[] mColors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};

    public SweepGradientView(Context context) {
        this(context, null);
    }

    public SweepGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SweepGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);

        // 范围 0-1 要和颜色数组长度一致 指定每个颜色的位置
        float[] position = {0.5f, 0.75f, 0.875f, 1f};
        mSweepGradient = new SweepGradient(400, 400, mColors, position);
        mPaint.setShader(mSweepGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(400, 400, 300, mPaint);

//        canvas.drawArc(0, 0, 800, 800, 180, 180, true, mPaint);
    }
}
