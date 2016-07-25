package com.chm.zf.weidget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class CustemEdittext extends EditText {
    public CustemEdittext(Context context) {
        super(context);
    }

    public CustemEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}