package cn.com.animationlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.AnimRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Create by Zhangty on 2018/8/10
 */
public class AnimateTextView extends android.support.v7.widget.AppCompatTextView {

    private Animation animation;
    private String content = "这是我在代码里面设置的内容";
    private int animateRes = 0;
    private long duration;
    private boolean isStarted = false;

    public AnimateTextView(Context context) {
        super(context);
    }

    public AnimateTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttribute(context, attrs);
    }

    public AnimateTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttribute(context,attrs);
    }

    private void getAttribute(Context context, @Nullable AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AnimateTextView);
        duration = typedArray.getInteger(R.styleable.AnimateTextView_animaDuration, 1500);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //animation 不生效，必须根据Res重新生成，好奇怪
        setAnimations(animateRes);
        animation.setDuration(duration);
        setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if(isStarted){
                    invalidate();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void start() {

        isStarted = true;
        invalidate();
    }

    public void stop() {
        isStarted = false;
    }

    public void setAnimations(@AnimRes int animRes) {
        animateRes = animRes;
        this.animation = AnimationUtils.loadAnimation(getContext(), animRes);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isStarted = false;
    }

    public boolean isStarted() {
        return isStarted;
    }

}
