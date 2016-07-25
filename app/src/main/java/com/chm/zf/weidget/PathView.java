package com.chm.zf.weidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by a9951 on 2016/6/22.
 */

public class PathView extends View {

    private static final int ANIM_NULL = 0;         //动画状态-没有
    private static final int ANIM_CHECK = 1;        //动画状态-开启
    private static final int ANIM_UNCHECK = 2;      //动画状态-结束

    private Context mContext;           // 上下文
    private int mWidth, mHeight;        // 宽高
    private Paint mPaint;

    private float[] pos;
    private float[] tan;
    private int animCurrentPage = -1;       // 当前页码


    public PathView(Context context) {
        super(context, null);
        init();
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mPaint = new Paint();
//        mPaint.setColor(0xffFF5317);
//        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setAntiAlias(true);

        mPaint.setColor(Color.BLACK);           // 画笔颜色 - 黑色
        mPaint.setStyle(Paint.Style.STROKE);    // 填充模式 - 描边
        mPaint.setStrokeWidth(10);              // 边框宽度 - 10
        mPaint.setAntiAlias(true);
    }


    /**
     * View大小确定
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    /**
     * 绘制内容
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.isHardwareAccelerated();

        Path path1 = new Path();

        path1.lineTo(mWidth, mHeight);

        path1.lineTo(50, 50);

        path1.lineTo(200, -50);

//        PathMeasure measure = new PathMeasure();
//        measure.setPath(path1, false);
//
//        measure.nextContour();

        canvas.drawPath(path1, mPaint);
    }
}