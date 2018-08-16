package cn.com.animationlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 延指定Path绘制文字的View
 * Create by Zhangty on 2018/8/16
 */
public class PathTextView extends View {

    private Paint mPaint;
    private Path path;
    private int cylinderColor = Color.parseColor("#ff0099cc");

    public PathTextView(Context context) {
        super(context);
        initPaint();
    }

    public PathTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public PathTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(cylinderColor);
        mPaint.setStrokeWidth(10f);

        path = new Path();
        path.moveTo(100, 100);
        path.lineTo(400, 400);
        path.setLastPoint(100, 100);

        path.lineTo(400, 800);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,mPaint);
//        canvas.drawTextOnPath("path",path,0,0, mPaint);
    }
}
