package com.chm.zf.weidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/7/16 0016.
 */
public class FlashTextView extends TextView {
    private Paint mPaint1;
    private Paint mPaint2;
    private Paint mPaint;
    private int mTranslate;
    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private  int mViewWidth;
    public FlashTextView(Context context) {
        super(context);
        init();
    }

    public FlashTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlashTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化用来绘制背景边框的笔
        mPaint1 = new Paint();
        mPaint2 = new Paint();
        mPaint1.setColor(Color.RED);
        mPaint2.setColor(Color.GRAY);
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint2.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewWidth = getMeasuredWidth();
        //闪动效果的对象初始化工作
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                //通过getPaint()方法获取绘制TextView的画笔
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
                        new int[]{Color.BLACK, 0xffffffff, Color.BLACK},
                        null, Shader.TileMode.CLAMP);
                //将该属性赋予给paint对象的shader渲染器
                mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mTranslate = this.getLeft();
        //绘制字体之前
        //我们在这里绘制外矩形
//        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint1);
        //绘制内矩形
        canvas.drawRect(5, 5, getMeasuredWidth() - 5, getMeasuredHeight() - 5, mPaint2);
        //绘制字体之前向右平移5像素
        canvas.translate(5, 0);
        canvas.save();
        //开始绘制字体
        super.onDraw(canvas);
        //绘制字体之后
        canvas.restore();
        if (mGradientMatrix != null) {
            mTranslate += mViewWidth / 5;
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);
        }
    }
}
