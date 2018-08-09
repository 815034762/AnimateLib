package cn.com.animationlibrary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 移动的线条的View
 * Create by Zhangty on 2018/8/8
 */
public class MoveLineView extends View {

    private Paint mPaint;
    private int lineColor;
    private int lineWidth;
    private ValueAnimator valueAnimator;
    /**
     * 动画延时
     */
    private int duration;

    /**
     * 动画类型
     */
    private int type;
    /**
     * 左右晃动
     */
    public static final int SWAP = 1;
    /**
     * 对称改变
     */
    public static final int SYMMETRIC_CHANGE = 2;

    public MoveLineView(Context context) {
        super(context);
        initPaint();
        initAnimate();
    }

    public MoveLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttribute(context,attrs);
        initPaint();
        initAnimate();
    }

    public MoveLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttribute(context,attrs);
        initPaint();
        initAnimate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startX = (float) valueAnimator.getAnimatedValue();
        int width = getWidth();
        int end = width/2;
        switch (type)
        {
            case SWAP:
                canvas.drawLine(divideInterval(startX)*end, getHeight() / 2, (divideInterval(startX)*end + 100), getHeight() / 2, mPaint);
                break;
            case SYMMETRIC_CHANGE:
                canvas.drawLine(divideInterval(startX)*end, getHeight() / 2, (width - divideInterval(startX)*end), getHeight() / 2, mPaint);
                break;
        }
    }

    /**
     * 获取属性
     */
    private void getAttribute(Context context,AttributeSet attrs){

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MoveLineView);
        lineColor = typedArray.getColor(R.styleable.MoveLineView_lineColor, Color.parseColor("#ff0099cc"));
        lineWidth = typedArray.getDimensionPixelSize(R.styleable.MoveLineView_lineWidth, 10);
        type = typedArray.getInteger(R.styleable.MoveLineView_type, 2);
        duration = typedArray.getInteger(R.styleable.MoveLineView_duration, 1000);
        typedArray.recycle();
    }

    /**
     * 将值分区间0到1 然后再  1到0
     * @param num
     * @return
     */
    private float divideInterval(float num){
        if(num < 0){
            num = 1-Math.abs(num);
        }else {
            num = Math.abs(1-num);
        }
        return num;
    }

    private void initPaint() {

        mPaint = new Paint();
        mPaint.setColor(lineColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(lineWidth);
    }

    private void initAnimate() {

        valueAnimator = ValueAnimator.ofFloat(-1f, 1f);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                invalidate();
            }
        });
        valueAnimator.start();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}