package cn.com.animationlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 圆柱形的View
 * Create by Zhangty on 2018/8/9
 */
public class CylinderView extends View {

    private Paint mPaint;
    private int cylinderColor = Color.parseColor("#ff0099cc");

    public CylinderView(Context context) {
        super(context);
    }

    public CylinderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CylinderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSemicircle(canvas);
        drawRectangle(canvas);
    }

    private void initPaint() {

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(cylinderColor);
    }

    /**
     * 画半圆
     *
     * @param canvas
     */
    private void drawSemicircle(Canvas canvas) {

        RectF rectF = new RectF(0, 0, getWidth(), getWidth());
        canvas.drawArc(rectF, 0, -180, true, mPaint);
    }

    /**
     * 画方形
     *
     * @param canvas
     */
    private void drawRectangle(Canvas canvas) {

        RectF rectF = new RectF(0, getWidth() / 2, getWidth(), getHeight());
        LinearGradient gradient = new LinearGradient(0, (getHeight()-getWidth()/2), 0, 0, new int[]{Color.BLUE, Color.YELLOW}, new float[]{0.5f,1.0f}, Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);
        canvas.drawRect(rectF, mPaint);
    }

}