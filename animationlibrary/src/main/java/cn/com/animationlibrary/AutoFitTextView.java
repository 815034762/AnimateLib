package cn.com.animationlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * 文字自适应的TextView
 * Create by Zhangty on 2018/8/9
 */
public class AutoFitTextView extends android.support.v7.widget.AppCompatTextView{

    private Paint mPaint;

    public AutoFitTextView(Context context) {
        super(context);
    }

    public AutoFitTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoFitTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //View宽度
        float width = getWidth() - (getPaddingLeft() + getPaddingRight());
        String text = getText().toString();
        //内容个数
        int textCount = text.length();
        //内容宽度
//        float textWidth = getPaint().measureText(text);
        //当前的文字宽度
//        int currentTextWidth = (int) (textWidth / textCount);

        //每个字所需的宽度
        float needWidth = width/textCount;
        mPaint = getPaint();
        mPaint.setTextSize(needWidth);
        setText(text);
    }

}
