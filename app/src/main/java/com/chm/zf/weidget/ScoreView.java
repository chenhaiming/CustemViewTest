package com.chm.zf.weidget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewTreeObserver;

import com.chm.zf.UIutils.UIUtils;


/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class ScoreView extends View {

    private Paint mPaint_while;
    private Paint mPaint_black;
    //    private float unitage;
    private RectF mRectf;
    private int score;
    private float arc_y = 0f;
    private int score_text;

    public ScoreView(Context context, int score) {
        super(context);
        this.score = score;
        init();
    }

    private void init() {
        //以10dp作为单位量
        // unitage = res.getDimension(R.dimen.unitage);
        // 初始化黑色
        mPaint_black = new Paint();
        mPaint_black.setAntiAlias(true);//抗锯齿
        mPaint_black.setDither(true);       //抖动处理
        mPaint_black.setColor(Color.BLACK); // 颜色
        mPaint_black.setStyle(Paint.Style.STROKE); //设置画笔的风格为空心
        mPaint_black.setStrokeWidth(UIUtils.dip2px(2));//设置“空心”的外框宽度为2dp

        //初始白色笔
        mPaint_while = new Paint();
        mPaint_while.setAntiAlias(true);
        mPaint_while.setStyle(Paint.Style.STROKE);
        mPaint_while.setStrokeWidth(UIUtils.dip2px(2));
        mPaint_while.setDither(true);

        //设置文本的字号大小
        mPaint_while.setTextSize(UIUtils.dip2px(50));
        //设置文本的对其方式为水平居中
        mPaint_while.setTextAlign(Paint.Align.CENTER);
        mPaint_while.setColor(Color.RED);

        mRectf = new RectF();

        float textWidth = mPaint_while.getTextSize();
        mRectf.set(-textWidth, -textWidth, textWidth, textWidth);
         new DrawThread();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        float result = (float) score_text / score;

        if (result != 0)
            canvas.drawArc(mRectf, 0, result * 360, false, mPaint_black);
        //绘制文本
        canvas.drawText(score_text + "", 0, 0 + mPaint_while.getTextSize() / 4, mPaint_while);
    }

    public class DrawThread implements Runnable {
        //2.开启子线程,并在绘制监听的回调方法中实现实时更新绘制数据
        private final Thread mDrawThread;
        private int statek;
        int count;

        public DrawThread() {
            mDrawThread = new Thread(this);
            mDrawThread.start();
        }

        @Override
        public void run() {
            while (true) {
                switch (statek) {
                    case 0://给一点点缓冲的时间
                        try {
                            Thread.sleep(200);
                            statek = 1;
                        } catch (InterruptedException e) {

                        }
                        break;
                    case 1:
                        try {//更新显示的数据
                            Thread.sleep(20);
                            arc_y += 3.6f;
                            score_text++;
                            count++;
                            postInvalidate();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                if (count >= score)//满足该条件就结束循环
                    break;
            }

        }
    }
}