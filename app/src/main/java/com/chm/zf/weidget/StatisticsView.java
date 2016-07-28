package com.chm.zf.weidget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.chm.zf.UIutils.UIUtils;
import com.chm.zf.custemview.R;

/**
 * Created by c on 2016/7/26.
 */
public class StatisticsView extends View {

    // 画 纵 横 轴
    private Paint mBorderPaint;
    //画坐标点的圆心
    private Paint circlePaint;
    //画折线图
    private Paint mPathPaint;
    private Paint mReactPaint;
    private Path mPath;
    //纵轴最大值
    private int maxValue = 100;
    //纵轴分割数量
    private int dividerCount = 10;
    private String title = "七日学习情况（单位节）";
    //纵轴每个单位值
    private int perValue = maxValue / dividerCount;
    //底部显示String
    private String[] bottomStr = {};
    //具体的值
    private float[] values = {};
    //底部横轴单位间距
    private float bottomGap;
    //左边纵轴间距
    private float leftGap;

    private TextPaint textPaint;

    public void setValues(float[] values) {
        this.values = values;
        invalidate();
    }

    public void setBottomStr(String[] bottomStr) {
        this.bottomStr = bottomStr;
        requestLayout();
    }

    public StatisticsView(Context context) {
        super(context);
    }

    public StatisticsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatisticsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.StatisticsView);
        maxValue = array.getInt(R.styleable.StatisticsView_maxValue, 100);
        dividerCount = array.getInt(R.styleable.StatisticsView_dividerCount, 10);
        title = array.getString(R.styleable.StatisticsView_title);

        int lineColor = array.getColor(R.styleable.StatisticsView_lineColor, Color.BLACK);
        int textColor = array.getColor(R.styleable.StatisticsView_textColor, Color.BLACK);

        mBorderPaint = new Paint();
        circlePaint = new Paint();
        mPathPaint = new Paint();
        mReactPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(lineColor);
        mBorderPaint.setStrokeWidth(1);
        mBorderPaint.setStyle(Paint.Style.STROKE);

        mReactPaint.setAntiAlias(true);
        mReactPaint.setStrokeWidth(1);
        mReactPaint.setStyle(Paint.Style.FILL);

        mPathPaint.setAntiAlias(true);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(3);

        textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(UIUtils.dip2px((12)));        // dp2px
        mPath = new Path();
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

        array.recycle();
    }

    /**
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize = UIUtils.getScreentWidth((Activity) getContext())/3*2;
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        bottomGap = getWidth() / (bottomStr.length + 1);
        leftGap = getHeight() / (dividerCount + 2);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (bottomStr == null || bottomStr.length == 0) {
            return;
        }

        //画左边的线
        canvas.drawLine(bottomGap, getHeight() - leftGap, bottomGap, leftGap, mBorderPaint);


        float fontHeight = (textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent);
        //画下边线
        canvas.drawLine(bottomGap, getHeight() - leftGap, getWidth() - bottomGap, getHeight() - leftGap, mBorderPaint);
        for (int i = 1; i <= bottomStr.length; i++) {
            canvas.drawCircle(i * bottomGap, getHeight() - leftGap, 6, circlePaint);
            canvas.drawText(bottomStr[i - 1], i * bottomGap - (textPaint.measureText(bottomStr[i - 1]) / 2), getHeight() - leftGap / 2 + fontHeight / 2, textPaint);
        }

        canvas.drawText(title, bottomGap, leftGap / 2, textPaint);
        for (int i = 1; i <= dividerCount + 1; i++) {
            //画左边的字
            canvas.drawText(perValue * (i - 1) + "", bottomGap / 2 - (textPaint.measureText(perValue * (i - 1) + "") / 2), (((dividerCount + 2 - i))) * leftGap + fontHeight / 2, textPaint);

            //画横线
            canvas.drawLine(bottomGap, getHeight() - ((i) * leftGap), getWidth() - bottomGap, getHeight() - ((i) * leftGap), mBorderPaint);
        }


        /**
         * 画轨迹
         * y的坐标点根据 y/leftGap = values[i]/perValue 计算
         */
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                mPath.moveTo(bottomGap, (dividerCount + 1) * leftGap - (values[i] * leftGap / perValue));
            } else {
                mPath.lineTo((i + 1) * bottomGap, (dividerCount + 1) * leftGap - (values[i] * leftGap / perValue));
            }
            /**
             * 画轨迹圆点
             */

            if (i % 2 == 0) {
                mReactPaint.setColor(Color.RED);
            } else {
                mReactPaint.setColor(Color.BLUE);
            }
            canvas.drawRect((i + 1) * bottomGap + (bottomGap / 4), (dividerCount + 1) * leftGap - (values[i] * leftGap / perValue)
                    , (i + 1) * bottomGap - (bottomGap / 4) + bottomGap, getHeight() - leftGap, mReactPaint);

            canvas.drawCircle((i + 1) * bottomGap, (dividerCount + 1) * leftGap - (values[i] * leftGap / perValue), 6, circlePaint);
        }
        canvas.drawPath(mPath, mPathPaint);
    }
}