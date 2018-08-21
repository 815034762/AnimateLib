# AnimateLib
一个练习动画的自定义View，里面有一些可以根据TextView宽度自动调节文字大小的View，类似广告闪烁的View
![image](https://github.com/815034762/AnimateLib/blob/master/capture/ezgif.com-video-to-gif.gif)

### 用法 | Usage
 1. 宽度固定，字体自适应控件大小的TextView
 ```
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
```
 2. 类似广告灯闪烁的View
 ```
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
 ```
 3.从左到右滚动的View
 ```
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

 ```
当然，上面还有一些需要补充纠正的，后面慢慢完善，也欢迎有想法的小伙伴一个完善
 
### 联系方式及反馈 | Contract & FeedBack

Author: Zhang TianaYang

Email: 815034762@qq.com

GitHub: https://github.com/815034762/

任何缺陷、建议，欢迎给我发邮件，或在GitHub上创建问题单。

Any bugs and recommendations, please send emails for me, or create issues on GitHub.
