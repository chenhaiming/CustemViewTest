package com.chm.zf.weidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.chm.zf.UIutils.UIUtils;
import com.chm.zf.custemview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class PieChartView extends View {

    /**
     * 饼图半径   默认半径
     */
    private float pieChartCircleRadius = 100;

    /**
     * 饼图所占矩形区域（不包括文字）
     */
    private RectF pieChartCircleRectF = new RectF();

    /**
     * 饼状图信息列表
     */
    private List<PieceDataHolder> pieceDataHolders = new ArrayList<>();


    public PieChartView(Context context) {
        super(context);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PieChartView, defStyle, 0);
        pieChartCircleRadius = a.getDimension(R.styleable.PieChartView_circleRadio,pieChartCircleRadius );
        pieChartCircleRadius = UIUtils.dip2px((int) pieChartCircleRadius);
//        pieChartCircleRadius = a.getDimension(
//                R.styleable.PieChartView_circleRadius,
//                pieChartCircleRadius);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPieChartCircleRectF();
        drawAllSectors(canvas);
    }

    private void drawAllSectors(Canvas canvas) {
        float sum = 0f;
        for (PieceDataHolder pieDataHolder : pieceDataHolders) {
            sum += pieDataHolder.value;
        }

        float sum2 = 0f;
        for (PieceDataHolder pieceDataHolder : pieceDataHolders) {
            float startAngel = sum2 / sum * 360;
            sum2 += pieceDataHolder.value;
            float sweepAngel = pieceDataHolder.value / sum * 360;

            drawSelectot(canvas, pieceDataHolder.color, startAngel, sweepAngel);
        }
    }

    /**
     * @param canvas
     * @param color
     * @param startAngel 开始的度数
     * @param sweepAngel 结束的度数
     */
    private void drawSelectot(Canvas canvas, int color, float startAngel, float sweepAngel) {
        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        canvas.drawArc(pieChartCircleRectF, startAngel, sweepAngel, true, paint);
    }

    /**
     * 初始化边距    用于 draw.drawArc();
     */
    private void initPieChartCircleRectF() {
        pieChartCircleRectF.left = getWidth() / 2 - pieChartCircleRadius;  //左边界
        pieChartCircleRectF.top = getHeight() / 2 - pieChartCircleRadius;
        pieChartCircleRectF.right = getWidth() / 2 + pieChartCircleRadius; //右边界
        pieChartCircleRectF.bottom = getHeight() / 2 + pieChartCircleRadius;
    }

    /**
     * 设置饼状图要显示的数据
     *
     * @param data 列表数据
     */
    public void setData(List<PieceDataHolder> data) {

        if (data != null) {
            pieceDataHolders.clear();
            pieceDataHolders.addAll(data);
        }

        invalidate();
    }


    /**
     * 饼状图每块的信息持有者
     */
    public static final class PieceDataHolder {

        /**
         * 每块扇形的值的大小
         */
        private float value;

        /**
         * 扇形的颜色
         */
        private int color;

        /**
         * 每块的标记
         */
        private String marker;


        public PieceDataHolder(float value, int color, String marker) {
            this.value = value;
            this.color = color;
            this.marker = marker;
        }
    }

}
