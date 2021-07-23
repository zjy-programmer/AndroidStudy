package com.example.androidstudy.any.nestedscroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


import com.example.androidstudy.R;
import com.example.baselibrary.util.LogUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

/** 
* ProjectName: AndroidStudy
* Package: com.example.androidstudy.any.nestedscroll
* ClassName: StickyNavNestedScrollView
* CreateDate: 2021/7/23 10:33 上午
* Author: zjy
* Description: 吸顶嵌套滑动
*/public class StickyNavNestedScrollView extends NestedScrollView {
    public static final String TAG = StickyNavNestedScrollView.class.getSimpleName();
    private int maxScrollHeight;

    public StickyNavNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public StickyNavNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StickyNavNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        View rv = findViewById(R.id.rv);
        View tab = findViewById(R.id.tv_tab);
        View head = findViewById(R.id.tv_head);
        LogUtil.i(TAG, "tab.getMeasuredHeight(): " + tab.getMeasuredHeight() + " getMeasuredHeight(): " + getMeasuredHeight());
        ViewGroup.LayoutParams layoutParams = rv.getLayoutParams();
        layoutParams.height = getMeasuredHeight() - tab.getMeasuredHeight();
        rv.setLayoutParams(layoutParams);

        maxScrollHeight = head.getMeasuredHeight();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
//        LogUtil.i(TAG, "onStartNestedScroll  axes: " + axes + " type: " + type + " getScrollY: " + getScrollY() + " maxScrollHeight: " + maxScrollHeight);
        return true;
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        LogUtil.i(TAG, "onNestedPreScroll  dx: " + dx + " dy: " + dy + " getScrollY: " + getScrollY());
        /*
        两种情况需要父View消费滑动事件
        1. 上滑 并且 父View已滑动的距离小于传入的最大可滑动距离
        2. 下滑 并且 父View已滑动的距离大于0 并且 子View不能再下滑了
         */
        boolean needConsumed = (dy > 0 && getScrollY() < maxScrollHeight) || (dy < 0 && getScrollY() > 0 && !target.canScrollVertically(-1));
        if (needConsumed) {
            LogUtil.i(TAG, "消费");
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        if (velocityY > 0) {

        }
    }
}
