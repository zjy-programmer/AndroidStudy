package com.example.androidstudy.any.customview.xfermode;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.example.androidstudy.R;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.customview.xfermode
 * ClassName: XfermodeView
 * CreateDate: 2021/6/9 5:29 下午
 * Author: zjy
 * Description: Xfermode 练习
 */
public class XfermodeView extends View {
    private Paint mPaint;
    private Bitmap srcBitmap; // 源图片
    private Bitmap desBitmap; // 目标图片
    private PorterDuffXfermode porterDuffXfermode;

    public XfermodeView(Context context) {
        this(context, null);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); // ANTI_ALIAS_FLAG抗锯齿
        mPaint.setDither(true); // 防抖动 使图片更平滑

        BitmapDrawable srcDrawable = (BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.mipmap.icon_heart_white, getContext().getTheme());
        if (srcDrawable == null) {
            return;
        }
        BitmapDrawable desDrawable = (BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.mipmap.icon_heart_blue, getContext().getTheme());
        if (desDrawable == null) {
            return;
        }
        srcBitmap = srcDrawable.getBitmap();
        desBitmap = desDrawable.getBitmap();
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
//        setLayerType(LAYER_TYPE_SOFTWARE, null);

        // 不设置Xfermode
//        canvas.drawBitmap(srcBitmap, 0, 0, mPaint);
//        canvas.drawBitmap(desBitmap, 200, 200, mPaint);

        canvas.drawBitmap(srcBitmap, 0, 0, mPaint);
        mPaint.setXfermode(porterDuffXfermode);
        canvas.drawBitmap(desBitmap, 100, 100, mPaint);
        mPaint.setXfermode(null);

        canvas.restoreToCount(saved);
    }
}
