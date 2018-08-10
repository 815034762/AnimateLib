package cn.com.animationlibrary;

import android.content.Context;
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
    private MyRunnable runnable;
    private String content = "这是我在代码里面设置的内容";
    private int animateRes = 0;
    private long delayTime = 2000;
    private boolean isStarted = false;

    public AnimateTextView(Context context) {
        super(context);
    }

    public AnimateTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        runnable = new MyRunnable();
    }

    public AnimateTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void start() {
        isStarted = true;
        post(runnable);
    }

    public void stop() {
        isStarted = false;
        removeCallbacks(runnable);
//        setText(content);
//        setVisibility(VISIBLE);
    }

    public void setAnimations(@AnimRes int animRes) {
        animateRes = animRes;
        this.animation = AnimationUtils.loadAnimation(getContext(), animRes);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                AnimateTextView.this.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                AnimateTextView.this.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isStarted = false;
        removeCallbacks(runnable);
    }

    public class MyRunnable implements Runnable {

        @Override
        public void run() {
            if (null != animation) {
                //animation 不生效，必须根据Res重新生成，好奇怪
                setAnimations(R.anim.right_to_left);
                setAnimation(animation);
                invalidate();
                postDelayed(this, delayTime);
            }
        }
    }

    public boolean isStarted() {
        return isStarted;
    }

}
