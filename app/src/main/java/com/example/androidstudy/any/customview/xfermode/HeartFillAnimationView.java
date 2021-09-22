package com.example.androidstudy.any.customview.xfermode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.example.androidstudy.R;
import com.example.baselibrary.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.customview.xfermode
 * ClassName: HeartFillAnimationView
 * CreateDate: 2021/8/24 4:27 下午
 * Author: zjy
 * Description: 心形点赞动画
 */
public class HeartFillAnimationView extends View {
    private static final int SCALE_RATIO = 5;
    // 图片宽
    private int mWidth;
    // 图片高
    private int mHeight;
    private Paint mPaint;
    // 源图片 画心
    private Bitmap srcBitmap;
    // 目标图片 画圆
    private Bitmap destBitmap;
    private PorterDuffXfermode porterDuffXfermode;
    private ValueAnimator mValueAnimator;
    private ValueAnimator mCircleRValueAnimator;
    private AnimatorSet mAnimatorSet;
    // 圆形的行进路径
    private Point mPoint = new Point();
    //颜色顺序：白 紫 蓝 黄 红 心形集合
    private List<BitmapDrawable> srcList = new ArrayList<>();
    // 圆形集合 颜色顺序：紫 蓝 黄 红
    private List<BitmapDrawable> destList = new ArrayList<>();
    // 记录当前做动画的src图片下标
    private int srcIndex;
    // 记录当前做动画的det图片下标
    private int destIndex;
    // 圆半径
    private int circleR;
    private boolean isPlayComplete;

    public HeartFillAnimationView(Context context) {
        this(context, null);
    }

    public HeartFillAnimationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartFillAnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 初始化集合
        srcList.add(getBitmapDrawable(R.mipmap.icon_heart_white));
        srcList.add(getBitmapDrawable(R.mipmap.icon_heart_purple));
        srcList.add(getBitmapDrawable(R.mipmap.icon_heart_blue));
        srcList.add(getBitmapDrawable(R.mipmap.icon_heart_yellow));
        srcList.add(getBitmapDrawable(R.mipmap.icon_heart_red));

        destList.add(getBitmapDrawable(R.mipmap.icon_circle_purple));
        destList.add(getBitmapDrawable(R.mipmap.icon_circle_blue));
        destList.add(getBitmapDrawable(R.mipmap.icon_circle_yellow));
        destList.add(getBitmapDrawable(R.mipmap.icon_circle_red));

        // 初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 防抖动 使图片更平滑
        mPaint.setDither(true);

        BitmapDrawable srcDrawable = srcList.get(srcIndex);
        if (srcDrawable == null) {
            return;
        }
        BitmapDrawable desDrawable = destList.get(destIndex);
        if (desDrawable == null) {
            return;
        }
        srcBitmap = srcDrawable.getBitmap();
        destBitmap = desDrawable.getBitmap();

        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

//        tapAnimator();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(srcBitmap, null, new Rect(0, 0, mWidth, mHeight), mPaint);
        mPaint.setXfermode(porterDuffXfermode);
        canvas.drawBitmap(destBitmap, null, new Rect(mPoint.x, mPoint.y, circleR, circleR), mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(saved);
    }

    private BitmapDrawable getBitmapDrawable(@DrawableRes int id) {
        return (BitmapDrawable) ResourcesCompat.getDrawable(getResources(), id, getContext().getTheme());
    }

    public boolean isPlayComplete() {
        return isPlayComplete;
    }

    /**
     * 单击动画
     */
    public void tapAnimator(boolean needAnimation) {
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            return;
        }
        mValueAnimator = ValueAnimator.ofInt(mWidth * 3 / 4, -mWidth / 2);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPoint.x = (int) animation.getAnimatedValue();
                mPoint.y = (int) animation.getAnimatedValue();
            }
        });

        mCircleRValueAnimator = ValueAnimator.ofInt(mWidth * 3 / 4, mWidth);
        mCircleRValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int edgeLength = (int) animation.getAnimatedValue();
                circleR = (int) Math.sqrt(edgeLength * edgeLength + edgeLength * edgeLength);
                invalidate();
            }
        });

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(needAnimation ? 200 : 0);
        mAnimatorSet.setInterpolator(new AccelerateInterpolator());
        mAnimatorSet.playTogether(mValueAnimator, mCircleRValueAnimator);
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!isPlayComplete) {
                    srcIndex++;
                    destIndex++;
                } else {
                    srcIndex--;
                    destIndex--;
                }
                if (srcIndex >= srcList.size() || destIndex >= destList.size()) {
                    srcIndex = srcList.size() - 2;
                    destIndex = destList.size() - 1;
                    isPlayComplete = true;
                    return;
                }
                if (srcIndex < 0 || destIndex < 0) {
                    srcIndex = 0;
                    destIndex = 0;
                    isPlayComplete = false;
                    return;
                }
                BitmapDrawable srcDrawable = srcList.get(srcIndex);
                if (srcDrawable == null) {
                    return;
                }
                BitmapDrawable desDrawable = destList.get(destIndex);
                if (desDrawable == null) {
                    return;
                }
                srcBitmap = srcDrawable.getBitmap();
                destBitmap = desDrawable.getBitmap();
                tapAnimator(needAnimation);
            }
        });
        mAnimatorSet.start();
    }

    public void reverse() {
//        srcIndex = srcList.size() - 2;
//        destIndex = destList.size() - 1;
//        BitmapDrawable srcDrawable = srcList.get(srcIndex);
//        if (srcDrawable == null) {
//            return;
//        }
//        BitmapDrawable desDrawable = destList.get(destIndex);
//        if (desDrawable == null) {
//            return;
//        }
//        srcBitmap = srcDrawable.getBitmap();
//        destBitmap = desDrawable.getBitmap();
//        invalidate();
//        isPlayComplete = true;
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            return;
        }

        mValueAnimator = ValueAnimator.ofInt(-mWidth / 2, mWidth * 3 / 4);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPoint.x = (int) animation.getAnimatedValue();
                mPoint.y = (int) animation.getAnimatedValue();
            }
        });

        mCircleRValueAnimator = ValueAnimator.ofInt(mWidth, mWidth * 3 / 4);
        mCircleRValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int edgeLength = (int) animation.getAnimatedValue();
                circleR = (int) Math.sqrt(edgeLength * edgeLength + edgeLength * edgeLength);
                invalidate();
            }
        });

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(200);
        mAnimatorSet.setInterpolator(new AccelerateInterpolator());
        mAnimatorSet.playTogether(mValueAnimator, mCircleRValueAnimator);
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                srcIndex--;
                destIndex--;
                if (srcIndex >= srcList.size() || destIndex >= destList.size()) {
                    srcIndex = srcList.size() - 2;
                    destIndex = destList.size() - 1;
                    isPlayComplete = true;
                    return;
                }
                if (srcIndex < 0 || destIndex < 0) {
                    srcIndex = 0;
                    destIndex = 0;
                    isPlayComplete = false;
                    return;
                }
                BitmapDrawable srcDrawable = srcList.get(srcIndex);
                if (srcDrawable == null) {
                    return;
                }
                BitmapDrawable desDrawable = destList.get(destIndex);
                if (desDrawable == null) {
                    return;
                }
                srcBitmap = srcDrawable.getBitmap();
                destBitmap = desDrawable.getBitmap();
                reverse();
            }
        });
        mAnimatorSet.start();
    }
}
