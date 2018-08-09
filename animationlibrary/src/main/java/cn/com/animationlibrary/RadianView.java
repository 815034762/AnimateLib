package cn.com.animationlibrary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Create by Zhangty on 2018/8/8
 */
public class RadianView extends View {

    private Paint mPaint;
    /**
     * 圆弧默认颜色
     */
    private int defaultColor = Color.parseColor("#ff0099cc");

    /**
     * 圆弧宽度
     */
    private int radianWidth = 10;

    /**
     * 开始动画
     */
    private ValueAnimator startAnagleAnimator;

    /**
     * 结束动画
     */
    private ValueAnimator endAnagleAnimator;

    private RectF rectF = new RectF();


    public RadianView(Context context) {
        super(context);
        initPaint();
        initAnimate();
    }

    public RadianView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initAnimate();
    }

    public RadianView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initAnimate();
    }

    private void initPaint() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(defaultColor);
        mPaint.setStrokeWidth(radianWidth);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rectF.left = 5;
        rectF.top = 5;
        rectF.right = getWidth() - 5;
        rectF.bottom = getHeight() - 5;

        int startAngle = (int) startAnagleAnimator.getAnimatedValue();
        int sweepAngle = (int) endAnagleAnimator.getAnimatedValue();
        //画圆弧的时候，外层的View宽度和高度必须一致才能旋转的时候是圆弧
        canvas.drawArc(rectF, startAngle, sweepAngle, false, mPaint);
    }

    private void startAnimate() {

    }

    private void stopAnimate() {

    }

    private void initAnimate() {

        startAnagleAnimator = ValueAnimator.ofInt(0, 360);
        startAnagleAnimator.setDuration(1500);
        startAnagleAnimator.setRepeatCount(ValueAnimator.INFINITE);
        startAnagleAnimator.setInterpolator(new LinearInterpolator());
        startAnagleAnimator.start();

        endAnagleAnimator = ValueAnimator.ofInt(20, 60, 120, 90, 60, 20);
        endAnagleAnimator.setDuration(1500);
        endAnagleAnimator.setRepeatCount(ValueAnimator.INFINITE);
        endAnagleAnimator.setInterpolator(new LinearInterpolator());
        endAnagleAnimator.start();

        startAnagleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                invalidate();
            }
        });
    }

}