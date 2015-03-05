package com.tencent.timluo.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.tencent.timluo.demo.R;


/**
 * 完成一个可以拖过界的容器view <br>
 * 1.这个view本身继承于linearLayout。<br>
 * 2.这个类是个虚基类
 */
public abstract class ScrollView<T extends View> extends LinearLayout {

    protected ScrollMode mScrollMode = ScrollMode.BOTH; // 当前view支持的滚动方式
    protected ScrollDirection mScrollDirection = ScrollDirection.SCROLL_DIRECTION_VERTICAL;
    private int mTouchSlop;
    private T mScrollContentView;
    private FrameLayout mContentViewWrapper;
    private PointF mLastMotionPointF = new PointF(0, 0);
    private PointF mInitialMotionPointF = new PointF(0, 0);
    private boolean mIsBeingDragged = false;

    public ScrollView(Context context, ScrollDirection scrollDirection, ScrollMode mode) {
        super(context);

        this.mScrollDirection = scrollDirection;
        this.mScrollMode = mode;
    }

    public ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 从 attrs 中读取 mode
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScrollView);
        if (typedArray != null) {
            // 得到滚动方向是水平还是垂直
            int scrollDirection = typedArray.getInt(R.styleable.ScrollView_ScrollDirection, 0);
            this.mScrollDirection = ScrollDirection.mapIntToValue(scrollDirection);
            // 得到下拉支持的方向，[默认都不支持]
            int scrollMode = typedArray.getInt(R.styleable.ScrollView_ScrollMode, 3);
            this.mScrollMode = ScrollMode.mapIntToValue(scrollMode);
            typedArray.recycle();
        }
    }

    protected void initView(Context context) {
        //设置水平布局 or 垂直布局
        if (mScrollDirection == ScrollDirection.SCROLL_DIRECTION_HORIZONTAL) {
            setOrientation(LinearLayout.HORIZONTAL);
        } else {
            setOrientation(LinearLayout.VERTICAL);
        }
        setGravity(Gravity.CENTER);

        // 获取滑动的最小阈值
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();

        // 获取实际的内容现实View，放入本地
        mScrollContentView = createScrollContentView(context);

        // 做一层包裹,方便在sizechange的时候做些处理
        mContentViewWrapper = new FrameLayout(context);
        mContentViewWrapper.addView(mScrollContentView, ViewGroup.LayoutParams.FILL_PARENT,
                                    ViewGroup.LayoutParams.FILL_PARENT);

        // 再将包裹结构插入到当前实际的layout
        LayoutParams params =
                new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        super.addView(mContentViewWrapper, -1, params);
    }

    /*---------------------自定义方法 begin ---------------------*/


    /**
     * 真实的viewgroup的移动是由这个重载函数来负责处理的
     */
    public final boolean onTouchEvent(MotionEvent event) {
        // 按下的时候按在了屏幕边缘
        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getEdgeFlags() != 0) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mLastMotionPointF.x = mInitialMotionPointF.x = event.getX();
                mLastMotionPointF.y = mInitialMotionPointF.y = event.getY();
                return true;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                return onTouchEventCancelAndUp();
            }
            case MotionEvent.ACTION_MOVE: {
                if (mIsBeingDragged == true) {
                    mLastMotionPointF.x = event.getX();
                    mLastMotionPointF.y = event.getY();
                    // 执行拖动动作核心是 scrollTo

                    scrollMoveEvent();
                    return true;
                }
            }
            break;

            default:
                break;
        }

        return false;
    }

    private void scrollMoveEvent() {
        float moveStart = 0;
        float moveEnd = 0;
        //水平移动
        if (mScrollDirection == ScrollDirection.SCROLL_DIRECTION_HORIZONTAL) {
            moveStart = mInitialMotionPointF.x;
            moveEnd = mLastMotionPointF.x;
        } else {
            //垂直移动
            moveStart = mInitialMotionPointF.y;
            moveEnd = mLastMotionPointF.y;
        }

    }

    // 刷新动作的子类需要覆盖此函数
    protected boolean onTouchEventCancelAndUp() {
        if (mIsBeingDragged == true) {
            mIsBeingDragged = false;
            // 弹回到初始的位置
//            smoothScrollTo(0);
            return true;
        }
        return false;
    }

    /*---------------------自定义方法 end ---------------------*/

    // 返回是否已经准备好开始位置的滚动
    protected abstract boolean isReadyForScrollStart();

    // 返回是否已经准备好结束位置的滚动
    protected abstract boolean isReadyForScrollEnd();

    /*---------------------抽象方法 begin ---------------------*/

    /**
     * 创建一个真实的放在内部滚动的view
     * @param context
     * @return
     */
    protected abstract T createScrollContentView(Context context);
    /*---------------------抽象方法 end ---------------------*/






    /*---------------------自定义枚举变量---------------------*/

    /**
     * 定义支持的工作模式
     */
    public static enum ScrollMode {
        /**
         * 头部下拉
         */
        PULL_FROM_START,

        /**
         * 底部上拉
         */
        PULL_FROM_END,

        /**
         * 都支持
         */
        BOTH,

        /**
         * 只支持弹簧，不支持刷新
         */
        NONE;

        static ScrollMode mapIntToValue(int modeIntValue) {
            switch (modeIntValue) {
                case 0:
                    return PULL_FROM_START;
                case 1:
                    return PULL_FROM_END;
                case 2:
                    return BOTH;
                case 3:
                    return NONE;

                default:
                    break;
            }
            return BOTH;
        }
    }

    /**
     * 内部枚举定义当前类支持的滚动方向
     * @author jaren
     */
    public static enum ScrollDirection {
        /**
         * 垂直滚动
         */
        SCROLL_DIRECTION_VERTICAL,

        /**
         * 水平滚动
         */
        SCROLL_DIRECTION_HORIZONTAL;

        static ScrollDirection mapIntToValue(int modeIntValue) {
            switch (modeIntValue) {
                case 0:
                    return SCROLL_DIRECTION_VERTICAL;
                case 1:
                    return SCROLL_DIRECTION_HORIZONTAL;
                case 2:

                default:
                    break;
            }
            return SCROLL_DIRECTION_VERTICAL;
        }
    }

    public static enum ScrollState {
        /**
         * 在鼠标按下和抬起的时候，会进入这个状态
         */
        ScrollState_Initial,

        /**
         * 从开始位置滑动
         */
        ScrollState_FromStart,

        /**
         * 从结束的位置开始滑动
         */
        ScrollState_FromEnd,
    }
}
