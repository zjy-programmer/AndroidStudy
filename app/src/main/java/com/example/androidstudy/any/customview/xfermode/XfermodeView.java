package com.example.androidstudy.any.customview.xfermode;

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

        BitmapDrawable srcDrawable = (BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.mipmap.icon_green, getContext().getTheme());
        if (srcDrawable == null) {
            return;
        }
        BitmapDrawable desDrawable = (BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.mipmap.icon_red, getContext().getTheme());
        if (desDrawable == null) {
            return;
        }
        srcBitmap = srcDrawable.getBitmap();
        desBitmap = desDrawable.getBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        // 不设置Xfermode
//        canvas.drawBitmap(srcBitmap, 0, 0, mPaint);
//        canvas.drawBitmap(desBitmap, 200, 200, mPaint);

        // 可能是图片的问题 设置模式跟网上说的不一样 好多都不展示了
        canvas.drawBitmap(srcBitmap, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        canvas.drawBitmap(desBitmap, 200, 200, mPaint);
    }
}
