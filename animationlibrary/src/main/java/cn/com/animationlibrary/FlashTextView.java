package cn.com.animationlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * 文字闪烁的TextView
 * Create by Zhangty on 2018/8/9
 */
public class FlashTextView extends android.support.v7.widget.AppCompatTextView {

    private Paint mPaint;
    private int mWidth;
    private LinearGradient gradient;
    private Matrix matrix;
    /**
     * 左右平移的变化值
     */
    private int deltaX;

    public FlashTextView(Context context) {
        super(context);
    }

    public FlashTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlashTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (matrix != null) {
            if (deltaX <= mWidth) {
                deltaX = deltaX + mWidth / 7;
            } else {
                deltaX = -mWidth / 4;
            }
        } else {
            matrix = new Matrix();
            mWidth = getMeasuredWidth();
            mPaint = getPaint();
            mPaint.setColor(getResources().getColor(android.R.color.holo_blue_dark));
            mPaint.setStyle(Paint.Style.FILL);
            //参数解说 x0  X轴开始点   y0  Y轴开始点    x1 X轴结束点 y1 Y轴结束点
            //颜色渐变器
            gradient = new LinearGradient(0, 0, mWidth, 0, new int[]{Color.BLUE, Color.GREEN, Color.RED}, null, Shader.TileMode.CLAMP);
            mPaint.setShader(gradient);
        }
        //关键代码通过矩阵的平移实现
        matrix.setTranslate(deltaX, 0);
        gradient.setLocalMatrix(matrix);
        postInvalidateDelayed(300);
    }

}
