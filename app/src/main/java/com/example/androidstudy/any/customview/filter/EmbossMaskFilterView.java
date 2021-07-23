package com.example.androidstudy.any.customview.filter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.customview.filter
 * ClassName: EmbossMaskFilterView
 * CreateDate: 2021/6/9 5:01 下午
 * Author: zjy
 * Description: EmbossMaskFilter 浮雕遮罩 ps中叫斜面浮雕 练习
 */
public class EmbossMaskFilterView extends View {
    private Paint mPaint;
    private RectF mRectF;

    public EmbossMaskFilterView(Context context) {
        this(context, null);
    }

    public EmbossMaskFilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmbossMaskFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setMaskFilter(new EmbossMaskFilter(new float[]{1,1,1}, 0.2f, 60, 80));

        mRectF = new RectF(0, 0, 500, 500);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);

        canvas.drawRect(mRectF, mPaint);
    }
}
