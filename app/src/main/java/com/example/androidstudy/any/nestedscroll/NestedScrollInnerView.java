package com.example.androidstudy.any.nestedscroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingChildHelper;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.nestedscroll
 * ClassName: NestedScrollInnerView
 * CreateDate: 2021/7/24 1:05 上午
 * Author: zjy
 * Description: java类作用描述
 */
public class NestedScrollInnerView extends View implements NestedScrollingChild3 {
    private NestedScrollingChildHelper mNestedScrollingChildHelper;

    public NestedScrollInnerView(Context context) {
        super(context);
        init();
    }

    public NestedScrollInnerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedScrollInnerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public NestedScrollInnerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        mNestedScrollingChildHelper.setNestedScrollingEnabled(true);
    }

    @Override
    public void dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type, @NonNull int[] consumed) {
        mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type, consumed);
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        return mNestedScrollingChildHelper.startNestedScroll(axes, type);
    }

    @Override
    public void stopNestedScroll(int type) {
        mNestedScrollingChildHelper.stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return mNestedScrollingChildHelper.hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        return mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        return mNestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }
}
