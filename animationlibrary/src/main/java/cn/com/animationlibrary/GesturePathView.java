package cn.com.animationlibrary;

/**
 * Create by Zhangty on 2018/8/16
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * View实现涂鸦、撤销以及重做功能
 */
public class GesturePathView extends View {

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    //画布的画笔
    private Paint mBitmapPaint;
    //真实的画笔
    private Paint mPaint;
    //临时点坐标
    private float mX, mY;

    private static final float TOUCH_TOLERANCE = 4;

    /**
     * 保存Path路径的集合,用List集合来模拟栈
     */
    private static List<DrawPath> savePath;
    /**
     * 保存Path路径的集合,用List集合来模拟栈
     */
    private static List<DrawPath> originSavePath;
    /**
     * 记录Path路径的对象
     */
    private DrawPath dp;

    private int screenWidth, screenHeight;

    public GesturePathView(Context context) {
        super(context);
    }

    public GesturePathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GesturePathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private class DrawPath {
        // 路径
        public Path path;
        // 画笔
        public Paint paint;
    }

    public void init(int w, int h) {

        screenWidth = w;
        screenHeight = h;

        mBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        // 保存一次一次绘制出来的图形
        mCanvas = new Canvas(mBitmap);

        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        // 设置外边缘
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        // 形状
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        // 画笔宽度
        mPaint.setStrokeWidth(5);

        savePath = new ArrayList<DrawPath>();
        originSavePath = new ArrayList<>();
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(0xFFAAAAAA);
        //将前面已经画过得显示出来
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        if (mPath != null) {
            // 实时的显示
            canvas.drawPath(mPath, mPaint);
        }
    }

    private void touch_start(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(mY - y);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            //从x1,y1到x2,y2画一条贝塞尔曲线，更平滑(直接用mPath.lineTo也是可以的)
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touch_up() {
        mPath.lineTo(mX, mY);
        mCanvas.drawPath(mPath, mPaint);
        //将一条完整的路径保存下来(相当于入栈操作)
        savePath.add(dp);
        originSavePath.add(dp);
        // 重新置空
        mPath = null;
    }

    /**
     * 撤销的核心思想就是将画布清空，
     * 将保存下来的Path路径最后一个移除掉，
     * 重新将路径画在画布上面。
     */
    public void undo() {
        if (savePath != null && savePath.size() > 0) {
            savePath.remove(savePath.size() - 1);
            redrawOnBitmap();
        }
    }

    public void next() {

        if (savePath != null && savePath.size() > 0) {

            mBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
            // 重新设置画布，相当于清空画布
            mCanvas.setBitmap(mBitmap);
            for (int i = 0; i <= savePath.size() && i < originSavePath.size(); i++) {
                DrawPath drawPath = originSavePath.get(i);
                mCanvas.drawPath(drawPath.path, drawPath.paint);
            }
            if (savePath.size() != originSavePath.size()) {
                savePath.add(originSavePath.get(savePath.size()));
            }
            //刷新
            invalidate();
        }
    }

    /**
     * 重做
     */
    public void redo() {
        if (savePath != null && savePath.size() > 0) {
            savePath.clear();
            originSavePath.clear();
            redrawOnBitmap();
        }
    }

    private void redrawOnBitmap() {

        mBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        // 重新设置画布，相当于清空画布
        mCanvas.setBitmap(mBitmap);

        Iterator<DrawPath> iter = savePath.iterator();
        while (iter.hasNext()) {
            DrawPath drawPath = iter.next();
            mCanvas.drawPath(drawPath.path, drawPath.paint);
        }
        //刷新
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 每次down下去重新new一个Path
                mPath = new Path();
                //每一次记录的路径对象是不一样的
                dp = new DrawPath();
                dp.path = mPath;
                dp.paint = mPaint;
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }

    public void saveToSDCard() {
        String fileUrl = Environment.getExternalStorageDirectory().toString() + "/android/data/test.png";
        try {
            FileOutputStream fos = new FileOutputStream(new File(fileUrl));
            mBitmap.compress(CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}