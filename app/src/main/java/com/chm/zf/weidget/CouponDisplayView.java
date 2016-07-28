package com.chm.zf.weidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.chm.zf.custemview.R;

/**
 * Created by c on 2016/7/26 0026.
 */
public class CouponDisplayView extends LinearLayout {


    private Paint mPaint;

    /**
     * 半径
     */
    private float radius = 10;
    /**
     * 圆间距
     */
    private float gap = radius;

    int bgRes;


    /**
     * 圆数量
     */
    private int circleNum;

    public CouponDisplayView(Context context) {
        super(context);
    }

    public CouponDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        circleNum = (int) ((w - gap) / (2 * radius + gap));
    }

    public CouponDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CouponDisplayView);
        gap = array.getDimension(R.styleable.CouponDisplayView_gat, 10);
        radius = array.getDimension(R.styleable.CouponDisplayView_radius, 10);
        bgRes = array.getColor(R.styleable.CouponDisplayView_backres, Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < circleNum; i++) {
            float x = gap + radius + (radius * 2 + gap) * i;
            canvas.drawCircle(x, 0, radius, mPaint);
            canvas.drawCircle(x, getHeight(), radius, mPaint);
        }
        this.setBackgroundColor(bgRes);
    }
}
