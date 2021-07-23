package com.example.androidstudy.any.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.baselibrary.util.LogUtil;

import androidx.annotation.Nullable;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.view
 * ClassName: EventView
 * CreateDate: 2021/7/16 6:20 下午
 * Author: zjy
 * Description: 测试事件传递View
 */
public class EventView extends View {
    public EventView(Context context) {
        super(context);
    }

    public EventView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtil.i("zjy", "EventView dispatchTouchEvent " + event.getAction());
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            return true;
//        }
        return super.dispatchTouchEvent(event);
//        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.i("zjy", "EventView onTouchEvent " + event.getAction());
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            getParent().requestDisallowInterceptTouchEvent(true);
            return true;
        }
//        return super.onTouchEvent(event);
        return false;
    }
}
