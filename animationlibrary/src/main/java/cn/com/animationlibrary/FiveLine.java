package cn.com.animationlibrary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 研究重点，关于对称的的应该怎么实现
 * Create by Zhangty on 2018/8/8
 *
 * @author Zhangty
 */
public class FiveLine extends View {
    private Paint paint;
    private boolean init = false;
    private float width = 0;
    private float height = 0;
    private ValueAnimator valueAnimator;
    private float numb = 0;
    private boolean stop = false;
    private float[] initLine = new float[]{0.6f, 0.3f, 0, 0.3f, 0.6f};

    public FiveLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(10);
        paint.setColor(Color.parseColor("#ff0099cc"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!init) {
            width = getWidth();
            height = getHeight();
            init = true;
            start();
        }
        numb = (float) valueAnimator.getAnimatedValue();
        //三
        canvas.drawLine(width / 2, 0f + fx(numb + initLine[2]) * width, width / 2, height - fx(numb) * width, paint);
        //二
        canvas.drawLine(width / 3, 0f + fx(numb + initLine[1]) * width, width / 3, height - fx(numb + 0.3f) * width, paint);
        //四
        canvas.drawLine(width / 3 * 2, 0f + fx(numb + initLine[3]) * width, width / 3 * 2, height - fx(numb + 0.3f) * width, paint);
        //一
        canvas.drawLine(width / 6, 0f + fx(numb + initLine[0]) * width, width / 6, height - fx(numb + 0.6f) * width, paint);
        //startY  0f + fx(numb + initLine[0]) * width
        //endY    height - fx(numb + 0.6f) * width
        //第五根
        canvas.drawLine(width / 6 * 5, 0f + fx(numb + initLine[4]) * width, width / 6 * 5, height - fx(numb + 0.6f) * width, paint);
    }


    /**
     * 分段周期函数，做为偏移量
     *
     * @param numb
     * @return
     */
    private float fx(float numb) {
        //[-1,0]取绝对值
        if (numb <= 0 && numb > -1) {
            numb = Math.abs(numb);
        }
        if (numb > 1) {
            numb = -numb + 2;
        }
        return numb;
    }

    public void start() {
        if (valueAnimator == null) {
            valueAnimator = getValueAnimator();
        } else {
            valueAnimator.start();
        }
        if (stop == false) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    start();
                    invalidate();
                }
            }, valueAnimator.getDuration());
        }
    }

    private ValueAnimator getValueAnimator() {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(-1f, 1f);
        valueAnimator.setDuration(1500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                invalidate();
            }
        });

        return valueAnimator;
    }
}
