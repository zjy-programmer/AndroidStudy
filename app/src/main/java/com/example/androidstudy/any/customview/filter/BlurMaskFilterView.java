package com.example.androidstudy.any.customview.filter;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.customview.filter
 * ClassName: BlurMaskFilterView
 * CreateDate: 2021/6/9 4:35 下午
 * Author: zjy
 * Description: BlurMaskFilter 模糊遮罩 练习
 */
public class BlurMaskFilterView extends View {
    private Paint mPaint;
    private RectF mRectF;


    public BlurMaskFilterView(Context context) {
        this(context, null);
    }

    public BlurMaskFilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlurMaskFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(Color.parseColor("#eeffffff"));
        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL)); // 全都模糊
//        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.SOLID)); // 内部不变 边界外加一层阴影 阴影颜色和paint颜色一致
//        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.OUTER)); // 内部变成透明 边界外加一层阴影
//        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.INNER)); // 边界不模糊 内部模糊

        mRectF = new RectF(0, 0, 500, 500);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);

        canvas.drawRect(mRectF, mPaint);
    }
}
