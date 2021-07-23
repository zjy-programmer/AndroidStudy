package com.example.androidstudy.any.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.baselibrary.util.LogUtil;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.view
 * ClassName: EventViewGroup
 * CreateDate: 2021/7/16 6:08 下午
 * Author: zjy
 * Description: 测试事件传递ViewGroup
 */
public class EventViewGroup extends FrameLayout {
    public EventViewGroup(Context context) {
        super(context);
    }

    public EventViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.i("zjy", "EventViewGroup dispatchTouchEvent " + ev.getAction());
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            return true;
//        }
        return super.dispatchTouchEvent(ev);
//        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.i("zjy", "EventViewGroup onInterceptTouchEvent " + ev.getAction());
//        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
//            return true;
//        }
        return super.onInterceptTouchEvent(ev);
//       return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.i("zjy", "EventViewGroup onTouchEvent " + event.getAction());
        return super.onTouchEvent(event);
//        return true;
    }
}
