package com.chm.zf.weidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.chm.zf.custemview.R;

import java.util.ArrayList;

/**
 * Created by c on 2016/7/27 0027.
 */
public class RoundMenu extends View {

    // 中心
    private int mPointX;
    private int mPointY;
    private Paint mPaint;          //画笔
    private Rect mRect;           // 中心图片的大小
    private int radius;         // 半径为宽度的一半
    private Bitmap centBitmap;      //中心图片
    private int bitmapHeight;       //图片的高度和宽度
    private int bitmapWidth;

    int isMoving = -1;

    private int menuSize = 4;

    private ArrayList<MenuItem> menuList = new ArrayList<>();

    public RoundMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        centBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        bitmapHeight = centBitmap.getHeight();
        bitmapWidth = centBitmap.getWidth();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mPointX = getMeasuredWidth() / 2;
        mPointY = getMeasuredHeight() / 2;
        if (mPointX < mPointY) {
            radius = getMeasuredWidth() / 2 - bitmapHeight;
        } else {
            radius = getMeasuredHeight() / 2 - bitmapHeight;
        }
        mRect = new Rect();
        mRect.set(-mPointX, -mPointX, mPointX, mPointX);

//        圆点坐标：(x0,y0)
//        半径：r
//        角度：a0
//        则圆上任一点为：（x1,y1）
//        x1   =   x0   +   r   *   cos(ao   *   3.14   /180   )
//        y1   =   y0   +   r   *   sin(ao   *   3.14   /180   )

//  添加四个菜单
        for (int i = 0; i < menuSize; i++) {
            MenuItem m = new MenuItem();
            m.bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            m.describe = "des" + i;
            m.anger = (360 / menuSize) * i;
            m.x = (float) (radius * Math.cos((m.anger * 3.14) / 180));
            m.y = (float) (radius * Math.sin((m.anger * 3.14) / 180));
            menuList.add(m);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initDraw(canvas);
    }

    private void initDraw(Canvas canvas) {
        canvas.translate(mPointX, mPointY);        //
        //中心图片
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
                android.R.mipmap.sym_def_app_icon), 0 - bitmapWidth / 2, 0 - bitmapHeight / 2, mPaint);


        for (int i = 0; i < menuList.size(); i++) {
            Bitmap bitmap = menuList.get(i).bitmap;
            String describe = menuList.get(i).describe;
            int anger = menuList.get(i).anger;
            float x = menuList.get(i).x;
            float y = menuList.get(i).y;
            canvas.drawBitmap(bitmap, x - bitmapHeight / 2, y - bitmapHeight / 2, mPaint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float x, y;
        float moveX ;
        float moveY ;
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                isMoving = -1;
                break;
            case MotionEvent.ACTION_MOVE:
                // deal move  event  to let the menu move whith figer  no ideal
                moveX = (int) event.getX() - mPointX;
                moveY = (int) event.getY() - mPointY;



                break;
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getX() - mPointX;
                y = (int) event.getY() - mPointY;
                isMoving = getIntCircle(x, y);
                break;
        }
        return true;
    }

    public int getIntCircle(float x, float y) {

        for (int i = 0; i < menuList.size(); i++) {
            MenuItem item = menuList.get(i);
            if (bitmapWidth > Math.abs(x - item.x) && bitmapHeight > Math.abs(y - item.y)) {
                return i;
            }
        }
        return -1;
    }

    public class MenuItem {
        public Bitmap bitmap;

        public String describe;

        public int anger;

        public float x;

        public float y;
    }
}
