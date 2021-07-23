package com.example.androidstudy.any.customview.shader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.customview.shader
 * ClassName: LinearGradientView
 * CreateDate: 2021/6/9 2:12 下午
 * Author: zjy
 * Description: LinearGradient Shader练习 线性渲染
 */
public class LinearGradientView extends View {
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int[] mColors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
    private LinearGradient linearGradient;

    public LinearGradientView(Context context) {
        this(context, null);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        // 当shader的x，y小于绘制的大小 则Shader.TileMode会起作用
//        linearGradient = new LinearGradient(0, 0, 400, 400, mColors, null, Shader.TileMode.CLAMP);
        linearGradient = new LinearGradient(0, 0, 400, 400, mColors, null, Shader.TileMode.REPEAT);
        mPaint.setShader(linearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, 1000, 1000, mPaint);
    }
}
