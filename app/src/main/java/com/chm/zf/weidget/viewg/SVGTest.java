package com.chm.zf.weidget.viewg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public class SVGTest extends View {
    private Path mPath;
    private Paint mPaint;
    private Path mTransformedPath;

    public SVGTest(Context context) {
        super(context);
        mPath = new Path();
        mTransformedPath = new Path();
        String d = "M 30,11 C 30,20 20,25 15,30 C 11,24 1,19 1,11 C 1,3 13,1 15,10 C 15,1 30,3 30,11 z";
        readPath(d, mPath);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xff00aa00);
        mPaint.setStrokeWidth(20);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        RectF src = new RectF();
        mPath.computeBounds(src, true);
        RectF dst = new RectF(30, 30, w-30, h-30);
        Matrix matrix = new Matrix();
        matrix.setRectToRect(src, dst, Matrix.ScaleToFit.FILL);
        mPath.transform(matrix, mTransformedPath);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mTransformedPath, mPaint);
    }

    private void readPath(String data, Path p) {
        try {
            String[] tokens = data.split("[ ,]");
            int i = 0;
            while (i < tokens.length) {
                String token = tokens[i++];
                if (token.equals("M")) {
                    float x = Float.valueOf(tokens[i++]);
                    float y = Float.valueOf(tokens[i++]);
                    p.moveTo(x, y);
                } else
                if (token.equals("L")) {
                    float x = Float.valueOf(tokens[i++]);
                    float y = Float.valueOf(tokens[i++]);
                    p.lineTo(x, y);
                } else
                if (token.equals("C")) {
                    float x1 = Float.valueOf(tokens[i++]);
                    float y1 = Float.valueOf(tokens[i++]);
                    float x2 = Float.valueOf(tokens[i++]);
                    float y2 = Float.valueOf(tokens[i++]);
                    float x3 = Float.valueOf(tokens[i++]);
                    float y3 = Float.valueOf(tokens[i++]);
                    p.cubicTo(x1, y1, x2, y2, x3, y3);
                } else
                if (token.equals("z")) {
                    p.close();
                } else {
                    throw new RuntimeException("unknown command [" + token + "]");
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("bad data ", e);
        }
    }
}
