package com.example.androidstudy.any.customview.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

import com.example.androidstudy.R;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.customview
 * ClassName: BitmapShaderView
 * CreateDate: 2021/6/9 10:50 上午
 * Author: zjy
 * Description: BitmapShader练习
 */
public class BitmapShaderView extends View {
    private Paint mPaint;
    private Bitmap mBitmap;
    private int mWidth;
    private int mHeight;
    private BitmapShader mBitmapShader;

    public BitmapShaderView(Context context) {
        this(context, null);
    }

    public BitmapShaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BitmapShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        BitmapDrawable drawable = (BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.mipmap.icon_amg_1, getContext().getTheme());
        if (drawable == null) {
            return;
        }
        mBitmap = drawable.getBitmap();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); // ANTI_ALIAS_FLAG抗锯齿
        mPaint.setDither(true); // 防抖动 使图片更平滑
        mWidth = mBitmap.getWidth();
        mHeight = mBitmap.getHeight();
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.MIRROR, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        mPaint.setShader(mBitmapShader);

//        canvas.drawRect(new Rect(0, 0, mWidth, mHeight), mPaint); //绘制图片的大小
        canvas.drawRect(new Rect(0, 0, 1000, 1000), mPaint); // 绘制大一点 用于测试 Shader.TileMode

        canvas.save();
        canvas.translate(200, 1300);
        // 绘制个圆形图片
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setShader(mBitmapShader);
        shapeDrawable.setBounds(0, 0, mWidth, mWidth);
        shapeDrawable.draw(canvas);
        canvas.restore();
    }
}
