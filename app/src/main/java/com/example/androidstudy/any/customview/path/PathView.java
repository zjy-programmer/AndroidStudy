package com.example.androidstudy.any.customview.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.example.baselibrary.util.WidgetUtil;

import androidx.annotation.Nullable;

/**
 * ProjectName: AndroidStudy
 * Package: com.example.androidstudy.any.customview.path
 * ClassName: PathView
 * CreateDate: 2021/6/11 10:01 上午
 * Author: zjy
 * Description: path 相关联系
 */
public class PathView extends View {
    private Path mPath = new Path();
    private Paint mPaint;
    private Path mPath1 = new Path();
    private Path mPath2 = new Path();

    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(WidgetUtil.dp2px(1));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // path基本方法
//        // 圆
//        mPath.addCircle(300, 400, 240, Path.Direction.CW);
//        // 弧
//        mPath.addArc(200, 700, 300, 800, 0, -90);
//        // 弧
//        mPath.arcTo(300, 900, 400, 1000, 0, 90, true);
//        // 椭圆
//        mPath.addOval(300, 1100, 600, 1300, Path.Direction.CW);
//        // 矩形
//        mPath.addRect(300, 1400, 500, 1500, Path.Direction.CW);
//        // 圆角矩形
//        mPath.addRoundRect(300, 1600, 500, 1700, 20, 20, Path.Direction.CW);
//        canvas.drawPath(mPath, mPaint);

        // path运算 只影响调用op方法的path
//        mPath1.addCircle(200, 200, 100, Path.Direction.CW);
//        mPath2.addCircle(300, 300, 100, Path.Direction.CW);
//
//        mPaint.setStrokeWidth(20);
//        // mPath1会减掉 mPath1和mPath2相交的部分
////        mPath1.op(mPath2, Path.Op.DIFFERENCE);
//        // mPath1会减掉 mPath1和mPath2不相交的部分
////        mPath1.op(mPath2, Path.Op.REVERSE_DIFFERENCE);
//        // mPath1只保留 mPath1和mPath2相交的部分
////        mPath1.op(mPath2, Path.Op.INTERSECT);
//        // mPath1只保留 全集形状减去交集部分 ？？ 这个不太对！
//        mPath1.op(mPath2, Path.Op.XOR);
//        // mPath1会变成 mPath1和mPath2的并集路径
////        mPath1.op(mPath2, Path.Op.UNION);
//        canvas.drawPath(mPath1, mPaint);
//        mPaint.setColor(Color.GREEN);
//        mPaint.setStrokeWidth(10);
//        canvas.drawPath(mPath2, mPaint);

        // （0，0）到（100，100）画一条线
//        mPath.lineTo(100, 100);
//        // 在前一个点的基础上接着画 所以是（100，100）到（300，200）画一条线
//        mPath.rLineTo(200,100);
//        canvas.drawPath(mPath, mPaint);

        // 先把画笔移动到（200，300）的位置
//        mPath.moveTo(200, 300);
//        // 从（200，300）到（100，0）画一条线
//        mPath.lineTo(100, 0);
//        canvas.drawPath(mPath, mPaint);

        // 先把画笔移动到（100，100）的位置
//        mPath.moveTo(100, 100);
//        // （100，100）到（200，200）画一条线
//        mPath.lineTo(200, 200);
//        // 再把画笔基于上一个点移动到（300，200）的位置
//        mPath.rMoveTo(100, 0);
//        // （300，200）到（400，200）画一条线
//        mPath.rLineTo(100, 0);
//        canvas.drawPath(mPath, mPaint);

//        mPath.moveTo(100, 100);
//        mPath.lineTo(200, 200);
//        mPath.rLineTo(150, 0);
//        // 闭合路径
//        mPath.close();
//        canvas.drawPath(mPath, mPaint);

        // 二阶贝塞尔曲线 x1和y1是控制点 x2和y2是终止数据点 没有调用path.moveTo方法 起始数据点则是（0，0）调用了path.moveTo方法 起始数据点则是moveTo的坐标
//        mPath.quadTo(400, 200, 50, 500);
//        mPath.moveTo(100, 100);
//        mPath.quadTo(400, 200, 50, 500);
//        mPath.rQuadTo(400, 600, 50, 1000);
//        canvas.drawPath(mPath, mPaint);

        // 三阶贝塞尔曲线 和二阶贝塞尔曲线差不多
        mPath.cubicTo(400, 200, 10, 500, 300, 700);
        canvas.drawPath(mPath, mPaint);
    }
}
