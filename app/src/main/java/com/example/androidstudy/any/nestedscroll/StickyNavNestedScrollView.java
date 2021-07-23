package com.example.androidstudy.any.nestedscroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


import com.example.androidstudy.R;
import com.example.baselibrary.util.LogUtil;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

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
    private int mVelocityY;
    private FlingHelper mFlingHelper;

    public StickyNavNestedScrollView(@NonNull Context context) {
        this(context, null);
    }

    public StickyNavNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyNavNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mFlingHelper = new FlingHelper(getContext());
        setOnScrollChangeListener((OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
//            LogUtil.i(TAG, "scrollX: " + scrollX + " scrollY: " + scrollY + " oldScrollX: " + oldScrollX + " oldScrollY: " + oldScrollY);
            // 头部view滑出屏幕 把fling传给子view
            if (scrollY == maxScrollHeight) {
                LogUtil.i(TAG, "头部view滑出屏幕 把fling传给子view");
                dispatchChildFling();
            }
        });
    }

    private void dispatchChildFling() {
        if (mVelocityY != 0) {
            //将惯性滑动速度转化成距离
            double distance = mFlingHelper.getSplineFlingDistance(mVelocityY);
            //计算子控件应该滑动的距离 = 惯性滑动距离 - 已滑距离
            if (distance > maxScrollHeight) {
                RecyclerView recyclerView = getChildRecyclerView(this);
                if (recyclerView != null) {
                    //将剩余滑动距离转化成速度交给子控件进行惯性滑动
                    int velocityY = mFlingHelper.getVelocityByDistance(distance - maxScrollHeight);
                    LogUtil.i(TAG, "velocityY: " + velocityY);
                    recyclerView.fling(0, velocityY);
                }
            }
        }
        mVelocityY = 0;
    }

    //递归获取子控件RecyclerView
    private RecyclerView getChildRecyclerView(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (!(view instanceof ConflictRecyclerView) && view instanceof RecyclerView && Objects.requireNonNull(((RecyclerView) view).getLayoutManager()).canScrollVertically()) {
                return (RecyclerView) view;
            } else if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                RecyclerView childRecyclerView = getChildRecyclerView((ViewGroup) viewGroup.getChildAt(i));
                if (!(view instanceof ConflictRecyclerView) && childRecyclerView != null && Objects.requireNonNull((childRecyclerView).getLayoutManager()).canScrollVertically()) {
                    return childRecyclerView;
                }
            }
        }
        return null;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View rv = findViewById(R.id.rv);
        View tab = findViewById(R.id.tv_tab);
//        View head = findViewById(R.id.tv_head);
        View rvHead = findViewById(R.id.rv_head);
//        LogUtil.i(TAG, "tab.getMeasuredHeight(): " + tab.getMeasuredHeight() + " getMeasuredHeight(): " + getMeasuredHeight());
        ViewGroup.LayoutParams layoutParams = rv.getLayoutParams();
        layoutParams.height = getMeasuredHeight() - tab.getMeasuredHeight();
        rv.setLayoutParams(layoutParams);

        maxScrollHeight = rvHead.getMeasuredHeight();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
//        LogUtil.i(TAG, "onStartNestedScroll  axes: " + axes + " type: " + type + " getScrollY: " + getScrollY() + " maxScrollHeight: " + maxScrollHeight);
        return true;
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
//        LogUtil.i(TAG, "onNestedPreScroll  dx: " + dx + " dy: " + dy + " getScrollY: " + getScrollY());
        /*
        两种情况需要父View消费滑动事件
        1. 上滑 并且 父View已滑动的距离小于传入的最大可滑动距离
        2. 下滑 并且 父View已滑动的距离大于0 并且 子View不能再下滑了
         */
        boolean needConsumed = dy > 0 && getScrollY() < maxScrollHeight;
        if (needConsumed) {
//            LogUtil.i(TAG, "消费");
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        LogUtil.i(TAG, "fling  velocityY: " + velocityY);
        if (velocityY <= 0) {
            mVelocityY = 0;
        } else {
            mVelocityY = velocityY;
        }
    }
}
