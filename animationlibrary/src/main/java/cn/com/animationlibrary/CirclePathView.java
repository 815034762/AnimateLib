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
 * Create by Zhangty on 2018/8/16
 */
public class CirclePathView extends View {

    private Paint mPaint;
    private Path path;

    public CirclePathView(Context context) {
        super(context);
    }

    public CirclePathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CirclePathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //初始化Paint
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2f);
        mPaint.setTextSize(40f);
        //初始化Path
        path = new Path();
        //以（600,600）为圆心，300为半径绘制圆
        //Path.Direction.CW顺时针绘制圆 Path.Direction.CCW逆时针绘制圆
        //CCW沿逆时针方向绘制   CW:沿顺时针方向绘制
        path.addCircle(getWidth()/2, getHeight()/2, 300, Path.Direction.CW);
        //沿path绘制文字

        canvas.drawTextOnPath("文字沿着Path绘制测试", path, 0, 0, mPaint);
        canvas.drawPath(path, mPaint);
    }
}
