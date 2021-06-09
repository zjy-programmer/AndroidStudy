package com.example.androidstudy.any.nestedscroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.example.baselibrary.util.LogUtil;
import com.example.baselibrary.util.ScreenUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewParentCompat;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.nestedscroll
 * ClassName: NestedScrollParent
 * CreateDate: 2021/6/7 10:47 上午
 * Author: zjy
 * Description: 自定义嵌套滑动父类
 */
public class NestedScrollParent extends LinearLayout implements NestedScrollingParent3 {
    public NestedScrollParent(Context context) {
        this(context, null);
    }

    public NestedScrollParent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedScrollParent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 当子view滑动时回调 NestedScrollingParent3接口里面的
     * 优先调用顺序 NestedScrollingParent3 -> NestedScrollingParent2 -> NestedScrollingParent
     * 实现了NestedScrollingParent3的onNestedScroll 就不会调用NestedScrollingParent2和NestedScrollingParent的onNestedScroll 以此类推
     * {@link ViewParentCompat#onNestedScroll(ViewParent, View, int, int, int, int, int, int[])}
     * @param target
     * @param dxConsumed x轴消费的距离
     * @param dyConsumed y轴消费的距离 dxConsumed和dyConsumed并不是累加值 而是实时消费的距离 向上滑dyConsumed是负的 向下滑dyConsumed是正的 上滑或者下滑到头了就是0了
     * @param dxUnconsumed
     * @param dyUnconsumed
     * @param type TYPE_TOUCH 0, TYPE_NON_TOUCH 1
     * @param consumed
     */
    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        LogUtil.i("zjy",
                "NestedScrollingParent3接口里面的 onNestedScroll === "
                        + "dxConsumed: " + dxConsumed
                        + " dyConsumed: " + dyConsumed
                        + " dxUnconsumed: " + dxUnconsumed
                        + " dyUnconsumed: " + dyUnconsumed
                        + " type: " + type);
    }

    /**
     * NestedScrollingParent2里的
     * 当子view滑动时 这个方法返回true则表示parent可以和子view嵌套滑动 否则parent不嵌套滑动 并且不会回调后续任何方法
     * @param child
     * @param target
     * @param axes SCROLL_AXIS_NONE 0, SCROLL_AXIS_HORIZONTAL 1, SCROLL_AXIS_VERTICAL 2
     * @param type TYPE_TOUCH 0, TYPE_NON_TOUCH 1
     * @return
     */
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        LogUtil.i("zjy",
                "NestedScrollingParent2里的 onStartNestedScroll === "
                        + "axes: " + axes
                        + " type: " + type);
        return true;
    }

    /**
     * NestedScrollingParent2接口里面的
     * onStartNestedScroll()返回的是true
     * onNestedScrollAccepted会在onStartNestedScroll之后立马回调
     * @param child
     * @param target
     * @param axes
     * @param type
     */
    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        LogUtil.i("zjy",
                "onNestedScrollAccepted === "
                        + "axes: " + axes
                        + " type: " + type);
    }

    /**
     * NestedScrollingParent2接口里面的
     * 手指离开屏幕 并且完全停止滚动的时候回调
     * @param target
     * @param type
     */
    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        LogUtil.i("zjy",
                "NestedScrollingParent2接口里面的 onStopNestedScroll === "
                        + " type: " + type);
    }

    /**
     * NestedScrollingParent2接口里面的
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     * @param type
     */
    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        LogUtil.i("zjy",
                "NestedScrollingParent2接口里面的 onNestedScroll === "
                        + "dxConsumed: " + dxConsumed
                        + " dyConsumed: " + dyConsumed
                        + " dxUnconsumed: " + dxUnconsumed
                        + " dyUnconsumed: " + dyUnconsumed
                        + " type: " + type);
    }

    /**
     * NestedScrollingParent2接口里面的
     * @param target
     * @param dx onTouchEvent move的时候产生的原始偏移值，NestedScrollView处理它自己的滑动之前先调用这个方法
     * @param dy onTouchEvent move的时候产生的原始偏移值，NestedScrollView处理它自己的滑动之前先调用这个方法
     * @param consumed
     * @param type
     */
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        LogUtil.i("zjy",
                "NestedScrollingParent2接口里面的 onNestedPreScroll === "
                        + "dx: " + dx
                        + " dy: " + dy
                        + " type: " + type);
        boolean hiddenTop = dy > 0 && getScrollY() < ScreenUtil.dp2px(150);
        boolean showTop = dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1);

        if (hiddenTop || showTop)
        {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    /**
     * NestedScrollingParent接口里的
     * 实现了NestedScrollingParent2里的方法 这里就不调用了
     * @param child
     * @param target
     * @param axes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes) {
        LogUtil.i("zjy",
                "NestedScrollingParent里的 onStartNestedScroll === "
                        + "axes: " + axes);
        return false;
    }

    /**
     * NestedScrollingParent接口里面的
     * 实现了NestedScrollingParent2接口的onNestedScrollAccepted 这里就不会回调了
     * @param child
     * @param target
     * @param axes
     */
    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {
        LogUtil.i("zjy",
                "NestedScrollingParent接口里面的 onNestedScrollAccepted === "
                        + "axes: " + axes);
    }

    /**
     * NestedScrollingParent接口里面的
     * 实现了NestedScrollingParent2接口里面的 这里就不会回调了
     * @param target
     */
    @Override
    public void onStopNestedScroll(@NonNull View target) {
        LogUtil.i("zjy",
                "NestedScrollingParent接口里面的 onStopNestedScroll === ");
    }

    /**
     * NestedScrollingParent接口里面的
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        LogUtil.i("zjy",
                "NestedScrollingParent接口里面的 onNestedScroll === "
                        + "dxConsumed: " + dxConsumed
                        + " dyConsumed: " + dyConsumed
                        + " dxUnconsumed: " + dxUnconsumed
                        + " dyUnconsumed: " + dyUnconsumed);
    }

    /**
     * NestedScrollingParent接口里面的
     * 实现了NestedScrollingParent2的onNestedPreScroll则不会再回调这个方法
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        LogUtil.i("zjy",
                "NestedScrollingParent2接口里面的 onNestedPreScroll === "
                        + "dx: " + dx
                        + " dy: " + dy);
    }

    /**
     * NestedScrollingParent接口里面的
     * @param target
     * @param velocityX
     * @param velocityY
     * @param consumed
     * @return
     */
    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        LogUtil.i("zjy",
                "NestedScrollingParent接口里面的 onNestedFling === "
                        + "velocityX: " + velocityX
                        + " velocityY: " + velocityY
                        + " consumed: " + consumed);
        return false;
    }

    /**
     * NestedScrollingParent接口里面的
     * @param target
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        LogUtil.i("zjy",
                "NestedScrollingParent接口里面的 onNestedFling === "
                        + "velocityX: " + velocityX
                        + " velocityY: " + velocityY);
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return 0;
    }
}
