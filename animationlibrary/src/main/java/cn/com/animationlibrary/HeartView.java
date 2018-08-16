package cn.com.animationlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 参考资料< href="https://www.cnblogs.com/Sharley/p/5783342.html"></>
 * Create by Zhangty on 2018/8/11
 */
public class HeartView extends View {

    private Paint paint;

    public HeartView(Context context) {
        super(context);
        initPaint();
    }

    public HeartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public HeartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint(){

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(10);
        paint.setColor(Color.parseColor("#ff0099cc"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float angle = 10;
        while (angle < 180) {
            Point p = getHeartPoint(angle);

            canvas.drawPoint(p.x, p.y, paint);
            angle = angle + 0.02f;
        }

    }

    public Point getHeartPoint(float angle) {

        int offsetX = getWidth() / 2;
        int offsetY = getHeight() / 2 - 55;
        float t = (float) (angle / Math.PI);
        float x = (float) (19.5 * (16 * Math.pow(Math.sin(t), 3)));
        float y = (float) (-20 * (13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t)));
        return new Point(offsetX + (int) x, offsetY + (int) y);
    }

}
