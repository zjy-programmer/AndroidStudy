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

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.customview.shader
 * ClassName: RadialGradientView
 * CreateDate: 2021/6/9 3:22 下午
 * Author: zjy
 * Description: RadialGradient shader练习 环形渲染
 */
public class RadialGradientView extends View {
    private Paint mPaint;
    private RadialGradient mRadialGradient;
    private int[] mColors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};

    public RadialGradientView(Context context) {
        this(context, null);
    }

    public RadialGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadialGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);

        mRadialGradient = new RadialGradient(300, 300, 100, mColors, null, Shader.TileMode.CLAMP);
        mPaint.setShader(mRadialGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(300, 300, 300, mPaint);
    }
}
