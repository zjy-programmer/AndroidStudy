package com.example.androidstudy.any.customview.shader;

import android.content.Context;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.example.androidstudy.R;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.customview.shader
 * ClassName: ComposeShaderView
 * CreateDate: 2021/6/9 3:34 下午
 * Author: zjy
 * Description: ComposeShader shader练习 组合渲染
 */
public class ComposeShaderView extends View {
    private Paint mPaint;
    private BitmapShader mBitmapShader;
    private LinearGradient mLinearGradient;
    private ComposeShader mComposeShader;
    private int[] mColors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};

    public ComposeShaderView(Context context) {
        this(context, null);
    }

    public ComposeShaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ComposeShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);

        BitmapDrawable drawable = (BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.mipmap.icon_amg_1, getContext().getTheme());
        if (drawable == null) {
            return;
        }
        mBitmapShader = new BitmapShader(drawable.getBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mLinearGradient = new LinearGradient(0, 0, 400, 400, mColors, null, Shader.TileMode.CLAMP);
        mComposeShader = new ComposeShader(mBitmapShader, mLinearGradient, PorterDuff.Mode.MULTIPLY);
        mPaint.setShader(mComposeShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, 1000, 1000, mPaint);
    }
}
