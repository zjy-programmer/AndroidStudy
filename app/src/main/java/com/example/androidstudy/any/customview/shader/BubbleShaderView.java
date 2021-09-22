package com.example.androidstudy.any.customview.shader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.customview.shader
 * ClassName: BubbleShaderView
 * CreateDate: 2021/9/2 8:01 下午
 * Author: zjy
 * Description: java类作用描述
 */
public class BubbleShaderView extends View {
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public BubbleShaderView(Context context) {
        this(context, null);
    }

    public BubbleShaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);
        final float radius = 200;
        mPaint.setShader(getGradient(radius, 0.5f, 0.05f));
        canvas.drawCircle(500, 500, radius, mPaint);
        mPaint.setShader(getGradient(radius, 0.7f, 0.08f));
        canvas.drawCircle(500, 500, radius, mPaint);
        mPaint.setShader(getGradient(radius, 0.8f, 0.1f));
        canvas.drawCircle(500, 500, radius, mPaint);
    }

    private Shader getGradient(float radius, float start, float alpha) {
        final int endColor = ColorUtils.setAlphaComponent(Color.WHITE, (int) (0xff * alpha));
        return new RadialGradient(500, 500, radius, new int[]{0x00ffffff, endColor}, new float[]{start, 1f}, Shader.TileMode.CLAMP);
    }
}
