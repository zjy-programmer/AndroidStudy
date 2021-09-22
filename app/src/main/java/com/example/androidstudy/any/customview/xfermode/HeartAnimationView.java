package com.example.androidstudy.any.customview.xfermode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.androidstudy.R;
import com.example.baselibrary.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.customview.xfermodeQ111`
 * ClassName: HeartAnimationView
 * CreateDate: 2021/8/24 9:15 下午
 * Author: zjy
 * Description: java类作用描述
 */
public class HeartAnimationView extends FrameLayout {
    private HeartFillAnimationView mHeartFillAnimationView;
    private List<BitmapDrawable> bigHeartList = new ArrayList<>();
    private int index;
    private GestureDetector gestureDetector;
    private long doubleTapTime;
    private long lastDoubleTapTime;

    public HeartAnimationView(@NonNull Context context) {
        this(context, null);
    }

    public HeartAnimationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartAnimationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setClickable(true);

        // 绘制填充动画View
        mHeartFillAnimationView = new HeartFillAnimationView(getContext());
        FrameLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        addView(mHeartFillAnimationView, layoutParams);

        // 准备放大动画view数组
        bigHeartList.add(getBitmapDrawable(R.mipmap.icon_heart_red));
        bigHeartList.add(getBitmapDrawable(R.mipmap.icon_heart_yellow));
        bigHeartList.add(getBitmapDrawable(R.mipmap.icon_heart_purple));
        bigHeartList.add(getBitmapDrawable(R.mipmap.icon_heart_blue));

        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                LogUtil.i("zjy", "单击");
                long now = System.currentTimeMillis();
                if (now - doubleTapTime > 1000) {
                    if (mHeartFillAnimationView.isPlayComplete()) {
                        LogUtil.i("zjy", "单击 反转动画");
                        mHeartFillAnimationView.reverse();
                    } else {
                        LogUtil.i("zjy", "单击 正向动画");
                        mHeartFillAnimationView.tapAnimator(true);
                    }
                }
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                LogUtil.i("zjy", "双击");
//                if (!mHeartFillAnimationView.isPlayComplete()) {
//                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                    LogUtil.i("zjy", "双击 正向动画");
//                            mHeartFillAnimationView.tapAnimator(false);
//                        }
//                    }, 300);
//                }
                if (doubleTapTime - lastDoubleTapTime > 300 ||
                        (doubleTapTime == 0 && lastDoubleTapTime == 0) ||
                        System.currentTimeMillis() - doubleTapTime > 1000) {
                    continuousClickAnimator();
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            continuousClickAnimator();
                        }
                    }, 300);
                    lastDoubleTapTime = System.currentTimeMillis();
                }
                LogUtil.i("zjy", "双击 正向动画");
                doubleTapTime = System.currentTimeMillis();
                if (!mHeartFillAnimationView.isPlayComplete()) {
                    mHeartFillAnimationView.tapAnimator(false);
                }
                return true;
            }
        });
    }

    private BitmapDrawable getBitmapDrawable(@DrawableRes int id) {
        return (BitmapDrawable) ResourcesCompat.getDrawable(getResources(), id, getContext().getTheme());
    }

    /**
     * 连续点击
     */
    private void continuousClickAnimator() {
        if (index >= bigHeartList.size()) {
            index = 0;
//            return;
        }
        FrameLayout.LayoutParams layoutParams = new LayoutParams(800, 800);
        layoutParams.gravity = Gravity.CENTER;

        ImageView iv = new ImageView(getContext());
        iv.setBackground(bigHeartList.get(index));
        addView(iv, layoutParams);

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv, "scaleX", 0.0f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv, "scaleY", 0.0f, 1.0f);
        Keyframe keyframe1 = Keyframe.ofFloat(0, 1.0f);
        Keyframe keyframe2 = Keyframe.ofFloat(0.9f, 1.0f);
        Keyframe keyframe3 = Keyframe.ofFloat(1, 0f);
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("alpha", keyframe1, keyframe2, keyframe3);
        ObjectAnimator alpha = ObjectAnimator.ofPropertyValuesHolder(iv, holder);
        animatorSet.playTogether(scaleX, scaleY, alpha);
        animatorSet.setDuration(1000);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                removeView(iv);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(iv);
            }
        });
        animatorSet.start();
        index++;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
