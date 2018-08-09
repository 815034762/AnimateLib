package cn.com.animationlibrary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 两个球体左右反复旋转的View
 * Create by Zhangty on 2018/8/7
 */
public class LeftToRightInfinite extends View {

    private Paint mPaint = new Paint();
    private Paint mPaint2 = new Paint();
    private MyRunnable myRunnable;
    private ValueAnimator valueAnimator;
    private int firstCircleColor;
    private int secondCircleColor;
    private boolean isStop = true;

    public LeftToRightInfinite(Context context) {
        super(context);
        initValueAnimate();
    }

    public LeftToRightInfinite(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.leftToRightInfinite);
        firstCircleColor = typedArray.getColor(R.styleable.leftToRightInfinite_firstCircleColor, Color.parseColor("#ff0099cc"));
        secondCircleColor = attrs.getAttributeIntValue(R.styleable.leftToRightInfinite_secondCircleColor, Color.parseColor("#ff669900"));
        typedArray.recycle();
        initValueAnimate();
    }

    public LeftToRightInfinite(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initValueAnimate();
    }

//    private int getViewSize(int defaultSize, int measureSpec) {
//        int mySize = defaultSize;
//
//        int mode = MeasureSpec.getMode(measureSpec);
//        int size = MeasureSpec.getSize(measureSpec);
//
//        switch (mode) {
//            //如果没有指定大小，就设置为默认大小
//            case MeasureSpec.UNSPECIFIED: {
//                mySize = defaultSize;
//                break;
//            }
//            //如果测量模式是最大取值为size
//            case MeasureSpec.AT_MOST: {
//                //我们将大小取最大值,你也可以取其他值
//                mySize = size;
//                break;
//            }
//            //如果是固定的大小，那就不要去改变它
//            case MeasureSpec.EXACTLY: {
//                mySize = size;
//                break;
//            }
//        }
//        return mySize;
//    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        int width = getViewSize(0, widthMeasureSpec);
//        int height = getViewSize(0, heightMeasureSpec);
//
//        if (width < height) {
//            height = width;
//        } else {
//            width = height;
//        }
//
//        setMeasuredDimension(width, height);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float value = (float) valueAnimator.getAnimatedValue();
        //  -1  --> 0 ---> 1 ---> -1 ---> 0 ---> 1
        float blueDistance;
        float greenDistance;
        if (value < 0) {
            greenDistance = getMeasuredWidth() * 7 / 8 * (1 - Math.abs(value)) + getMeasuredWidth() / 16;
            blueDistance = getMeasuredWidth() * 7 / 8 * (Math.abs(value)) + getMeasuredWidth() / 16;
        } else {
            greenDistance = getMeasuredWidth() * 7 / 8 * (Math.abs(1 - value)) + getMeasuredWidth() / 16;
            blueDistance = getMeasuredWidth() * 7 / 8 * (Math.abs(value)) + getMeasuredWidth() / 16;
        }
        //蓝色的球
        mPaint.setColor(firstCircleColor);
        canvas.drawCircle(blueDistance, getMeasuredHeight() / 2, getMeasuredWidth() / 16, mPaint);
        //绿色的球
        mPaint2.setColor(secondCircleColor);
        canvas.drawCircle(greenDistance, getMeasuredHeight() / 2, getMeasuredWidth() / 16, mPaint2);
    }

    /**
     * 开始动画
     */
    public void start() {

        if (isStop) {
            isStop = false;
            if (null == myRunnable) {
                myRunnable = new MyRunnable();
            }
            initValueAnimate();
            postDelayed(myRunnable, valueAnimator.getDuration());
        }
    }

    /**
     * 结束动画
     */
    public void stop() {

        isStop = true;
        removeCallbacks(myRunnable);
        if (valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
    }

    public boolean isStop() {
        return isStop;
    }

    public class MyRunnable implements Runnable {
        @Override
        public void run() {
            start();
            //每一秒刷新一次
            invalidate();
        }
    }

    private void initValueAnimate() {

        if (null == valueAnimator) {
            valueAnimator = ValueAnimator.ofFloat(-1f, 1f);
            valueAnimator.setDuration(1500);
            valueAnimator.setRepeatMode(ValueAnimator.RESTART);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    invalidate();
                }
            });
            valueAnimator.start();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }
}