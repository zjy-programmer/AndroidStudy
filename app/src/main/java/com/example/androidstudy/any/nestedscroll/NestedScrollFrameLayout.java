package com.example.androidstudy.any.nestedscroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent3;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.nestedscroll
 * ClassName: NestedScrollFrameLayout
 * CreateDate: 2021/7/24 1:03 上午
 * Author: zjy
 * Description: java类作用描述
 */
public class NestedScrollFrameLayout extends FrameLayout implements NestedScrollingParent3 {
    public NestedScrollFrameLayout(@NonNull Context context) {
        super(context);
    }

    public NestedScrollFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NestedScrollFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {

    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return false;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {

    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {

    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {

    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {

    }
}
