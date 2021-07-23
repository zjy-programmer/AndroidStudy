package com.example.androidstudy.any.nestedscroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.nestedscroll
 * ClassName: ConflictRecyclerView
 * CreateDate: 2021/7/24 12:12 上午
 * Author: zjy
 * Description: java类作用描述
 */
public class ConflictRecyclerView extends RecyclerView {
    public ConflictRecyclerView(@NonNull Context context) {
        super(context);
    }

    public ConflictRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ConflictRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 设置了这个binding.rvHead.setNestedScrollingEnabled(false); 下面两行就可以注释掉 否则打开下面两行注释
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return false;
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent e) {
//        return false;
//    }
}
