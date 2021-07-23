package com.example.androidstudy.any.customview.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.example.androidstudy.R;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.customview.filter
 * ClassName: ColorFilterView
 * CreateDate: 2021/6/9 4:03 下午
 * Author: zjy
 * Description: ColorFilter 颜色矩阵练习
 */
public class ColorFilterView extends View {
    private Paint mPaint;
    private RectF mRectF;
    private Bitmap mBitmap;

    public ColorFilterView(Context context) {
        this(context, null);
    }

    public ColorFilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mBitmap = drawable.getBitmap();
//        mRectF = new RectF(0, 0, 1000, 1000);
        mRectF = new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 关闭硬件加速
//        setLayerType(LAYER_TYPE_SOFTWARE, mPaint);

        // 保留rgba的像素值 但是g要额外加100
//        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
//                1, 0, 0, 0, 0,
//                0, 1, 0, 0, 100,
//                0, 0, 1, 0, 0,
//                0, 0, 0, 1, 0,
//        });

        // 反相效果 -- 底片效果
//        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
//                -1, 0, 0, 0, 255,
//                0, -1, 0, 0, 255,
//                0, 0, -1, 0, 255,
//                0, 0, 0, 1, 0,
//        });

        // 缩放运算---乘法 -- 颜色增强
//        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
//                1.2f, 0, 0, 0, 0,
//                0, 1.2f, 0, 0, 0,
//                0, 0, 1.2f, 0, 0,
//                0, 0, 0, 1.2f, 0,
//        });

        /* 黑白照片
         * 是将我们的三通道变为单通道的灰度模式
         * 去色原理：只要把R G B 三通道的色彩信息设置成一样，那么图像就会变成灰色，
         * 同时为了保证图像亮度不变，同一个通道里的R+G+B =1
         */
//        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
//                0.213f, 0.715f, 0.072f, 0, 0,
//                0.213f, 0.715f, 0.072f, 0, 0,
//                0.213f, 0.715f, 0.072f, 0, 0,
//                0, 0, 0, 1, 0,
//        });

        // 蓝色和绿色交换
//        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
//                1, 0, 0, 0, 0,
//                0, 0, 1, 0, 0,
//                0, 1, 0, 0, 0,
//                0, 0, 0, 1, 0,
//        });

        // 复古效果
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                0.5f, 0.5f, 0.5f, 0, 0,
                0.33f, 0.33f, 0.33f, 0, 0,
                0.25f, 0.25f, 0.25f, 0, 0,
                0, 0, 0, 1, 0,
        });

        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(mBitmap, null, mRectF, mPaint);
    }
}
