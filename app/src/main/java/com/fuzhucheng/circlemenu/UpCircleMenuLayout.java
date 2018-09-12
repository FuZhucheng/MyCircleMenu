package com.fuzhucheng.circlemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ${符柱成} on 2016/10/24.
 */

public class UpCircleMenuLayout extends ViewGroup {

    private int mRadius;//半径

    public static final float DEFAULT_BANNER_WIDTH = 750.0f;        //中间显示的图标大小
    public static final float DEFAULT_BANNER_HEIGTH = 420.0f;       //其余图标大小

    /**
     * 该容器内child item的默认尺寸
     */
    private static final float RADIO_DEFAULT_CHILD_DIMENSION = 60.0f;

    private static final float RADIO_TOP_CHILD_DIMENSION = 100.0f;

    /**
     * 菜单的中心child的默认尺寸
     */
    private float RADIO_DEFAULT_CENTERITEM_DIMENSION = 1 / 3f;
    /**
     * 该容器的内边距,无视padding属性，如需边距请用该变量
     */
    private static final float RADIO_PADDING_LAYOUT = 20;


    private static final int RADIO_MARGIN_LAYOUT = 20;
    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private static final int FLINGABLE_VALUE = 300;

    /**
     * 如果移动角度达到该值，则屏蔽点击
     */
    private static final int NOCLICK_VALUE = 3;

    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private int mFlingableValue = FLINGABLE_VALUE;
    /**
     * 该容器的内边距,无视padding属性，如需边距请用该变量
     */
    private float mPadding;

    /**
     * 布局时的开始角度
     */
    private double mStartAngle = -360;



    private double mAngleInterval = 50;
    /**
     * 菜单项的图标
     */
    private int[] mItemImgs;
    /**
     * 菜单项的文本
     */
    private String[] mItemTexts;

    /**
     * 菜单的个数
     */
    private int mMenuItemCount;

    /**
     * 检测按下到抬起时旋转的角度
     */
    private float mTmpAngle;
    /**
     * 检测按下到抬起时使用的时间
     */
    private long mDownTime;

    /**
     * 判断是否正在自动滚动
     */
    private boolean isTouchUp = true;


    /**
     * 当前中间顶部的那个
     */
    private int mCurrentPosition = 0;


    public UpCircleMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 无视padding
        setPadding(0, 0, 0, 0);
    }
    int mResHeight = 0;


    /**
     * 设置布局的宽高，并策略menu item宽高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int resWidth = 0;
        int resHeight = 0;
        double startAngle = mStartAngle;

        double angle = mAngleInterval;
        /**
         * 根据传入的参数，分别获取测量模式和测量值
         */
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        /**
         * 如果宽或者高的测量模式非精确值
         */
        if (widthMode != MeasureSpec.EXACTLY
                || heightMode != MeasureSpec.EXACTLY) {
            // 主要设置为背景图的高度

            resWidth = getDefaultWidth();

            resHeight = (int) (resWidth * DEFAULT_BANNER_HEIGTH /
                    DEFAULT_BANNER_WIDTH);

        } else {
            // 如果都设置为精确值，则直接取小值；
            resWidth=width;
            resHeight = height;

//          resWidth  = resHeight = Math.min(width, height);
        }
        resHeight = (int) (resWidth/2);


        System.out.println("11111111111112222222resHeight:"+resHeight);
//        resHeight = resWidth/2;
        mResHeight = resHeight;
//        mResHeight = resHeight;
        setMeasuredDimension(resWidth, resHeight);

        // 获得直径
        mRadius = Math.max(getMeasuredWidth(), getMeasuredHeight());
//        mRadius=DensityUtil.dip2px(getContext(), 300);

        System.out.println("1111111111111mRadius:"+mRadius);
        System.out.println("1111111111111getMeasuredWidth():"+getMeasuredWidth());


        // menu item数量
        final int count = getChildCount();
        // menu item尺寸
        int childSize;

        // menu item测量模式
        int childMode = MeasureSpec.EXACTLY;

        // 迭代测量：根据孩子的数量进行遍历，为每一个孩子测量大小，设置监听回调。
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            startAngle = startAngle % 360;
            if (startAngle == 90 && isTouchUp) {
                if(mCurrentPosition == i){
//                    return;
                }else {

                    if(mOnMenuItemClickListener!=null){
                        mOnMenuItemClickListener.itemClick(count - i-1);              //设置监听回调。
                    }

                }
                mCurrentPosition = i;                       //本次使用mCurrentPosition，只是把他作为一个temp变量。可以有更多的使用，比如动态设置每个孩子相隔的角度
                //childSize = DensityUtil.dip2px(getContext(), RADIO_TOP_CHILD_DIMENSION);            //设置大小
            } else {
                // childSize = DensityUtil.dip2px(getContext(), RADIO_DEFAULT_CHILD_DIMENSION);
            }

            if (startAngle >= 90-mAngleInterval/2 &&startAngle <= 90+mAngleInterval/2) {

                childSize = DensityUtil.dip2px(getContext(), RADIO_TOP_CHILD_DIMENSION);
            } else {
                childSize = DensityUtil.dip2px(getContext(), RADIO_DEFAULT_CHILD_DIMENSION);
            }

//            if (child.getVisibility() == GONE) {
//                continue;
//            }
            // 计算menu item的尺寸；以及和设置好的模式，去对item进行测量
            int makeMeasureSpec = -1;

            makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize,
                    childMode);
            child.measure(makeMeasureSpec, makeMeasureSpec);
            startAngle += angle;
        }
        //item容器内边距
//        mPadding = DensityUtil.dip2px(getContext(), RADIO_MARGIN_LAYOUT);

    }

    /**
     * MenuItem的点击事件接口
     */
    private OnMenuItemClickListener mOnMenuItemClickListener;

    /**
     * MenuItem的点击事件接口
     *
     * @author zhy
     */
    public interface OnMenuItemClickListener {
        void itemClick(int pos);
        void itemClick(View view, int pos);
        void itemCenterClick(View view);
    }

    /**
     * 设置MenuItem的点击事件接口
     *
     * @param mOnMenuItemClickListener
     */
    public void setOnMenuItemClickListener(
            OnMenuItemClickListener mOnMenuItemClickListener) {
        this.mOnMenuItemClickListener = mOnMenuItemClickListener;
    }


    /**
     * 主要为了action_down时，返回true
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return true;
    }

    /**
     * 设置menu item的位置
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //布置圆的半径
        int layoutRadius = mRadius;
//        mStartAngle = mStartAngle ;
        double tampStartAngle = mStartAngle;
        // Laying out the child views
        final int childCount = getChildCount();

        int left, top;
        // menu item 的尺寸
        int cWidth;

        // 根据menu item的个数，计算角度
        double angleDelay = mAngleInterval;
        // 遍历去设置menuitem的位置
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            //根据孩子遍历，设置中间顶部那个的大小以及其他图片大小。

            if (tampStartAngle >= 90-mAngleInterval/2 && tampStartAngle <= 90+mAngleInterval/2) {
                cWidth = DensityUtil.dip2px(getContext(), RADIO_TOP_CHILD_DIMENSION);
                child.setSelected(true);
            } else {
                cWidth = DensityUtil.dip2px(getContext(), RADIO_DEFAULT_CHILD_DIMENSION);
                child.setSelected(false);
            }
//            if (child.getVisibility() == GONE) {
//                continue;
//            }
//            System.out.println("11111112222tampStartAngle:"+tampStartAngle+"::cWidth+"+cWidth+"11isTouchUp:"+isTouchUp);
            //大于360就取余归于小于360度
//            tampStartAngle = tampStartAngle % 360;
//            System.out.println("111111111mmStartAngleSSSS:"+mStartAngle);
            float tmp = 0;
            //计算图片布置的中心点的圆半径。就是tmp
            tmp = layoutRadius / 2f - cWidth / 2 - mPadding+(200-(int)(Math.abs(Math.sin(Math.toRadians(tampStartAngle)))*200));
            // tmp cosa 即menu item中心点的横坐标。计算的是item的位置，是计算位置！！！


//            System.out.println("bbbbbbbbbbbbbbbsss11tampStartAngle:"+tampStartAngle);
            System.out.println("bbbbbbbbbbbbbbbsss:"+(int)(Math.abs(Math.sin(Math.toRadians(tampStartAngle)))*50)+":tampStartAngle:"+tampStartAngle);

            left = layoutRadius
                    / 2
                    + (int) Math.round(tmp
                    * Math.cos(Math.toRadians(tampStartAngle)) - 1 / 2f
                    * cWidth) + DensityUtil
                    .dip2px(getContext(), 1);
            // tmp sina 即menu item的纵坐标
            top = layoutRadius
                    / 2
                    + (int) Math.round(tmp
                    * Math.sin(Math.toRadians(tampStartAngle)) - 1 / 2f * cWidth - DensityUtil
                    .dip2px(getContext(), 10));
            //接着当然是布置孩子的位置啦，就是根据小圆的来布置的

            int s=  DensityUtil
                    .dip2px(getContext(), 8);
            int ss = (int) Math.abs(Math.sin(Math.toRadians(tampStartAngle))*s);

            System.out.println("33333333sssss:"+s);
            System.out.println("333333333zzzzz:"+ss);

            child.layout(left, top-mRadius/2-ss , left + cWidth, top + cWidth-mRadius/2 -ss);//-getHeight()/2 -mRadius/2 -mRadius/2-ss
//            int s= (int) (tampStartAngle/180);
//
//            int s1= (int) ( mStartAngle/180);
//
//
//            if(tampStartAngle>=0&&tampStartAngle<=180){
//                child.setVisibility(VISIBLE);
//            }else {
//                child.setVisibility(INVISIBLE);
//            }



            // 叠加尺寸
            tampStartAngle += angleDelay;

        }
    }

    private void backOrPre() {     //缓冲的角度。即我们将要固定几个位置，而不是任意位置。我们要设计一个可能的角度去自动帮他选择。
        isTouchUp = true;
        if(mTmpAngle ==0){
            return;
        }
//        System.out.println("11111112222tampStartAngle:"+mStartAngle);
//        float angleDelay = 50;              //这个是每个图形相隔的角度
//        if ((mStartAngle+10)%angleDelay==0){
//            return;
//        }
//        float angle = (float)((mStartAngle+10)%50);                 //angle就是那个不是18度开始布局，然后是36度的整数的多出来的部分角度
//        if (angleDelay/2 > angle){
//            mStartAngle -= angle;
//        }else if (angleDelay/2<angle){
//            mStartAngle = mStartAngle - angle + angleDelay;         //mStartAngle就是当前角度啦，取余36度就是多出来的角度，拿这个多出来的角度去数据处理。
//        }
//        if(mStartAngle<-50*10+180-45&&mStartAngle>-50*10+180-55){
//
//            mStartAngle = -50*10+180-50;
//
//        }
        //-360 和 到 90之间最近的点

        double temp =mStartAngle;
        double tempStart=mStartAngle;
        boolean f = true;
        for(int i=-(int)mAngleInterval*(getChildCount()-1)+90;i<=90;i+=mAngleInterval){
            double temp1 =  Math.abs(mStartAngle-i);
            if(f){
                temp =  temp1;
                f = false;
            }
            if(temp1<=temp){
                tempStart  = i;
                temp = temp1;
            }

        }
//        System.out.println("temp11111111111:"+temp);
        mStartAngle = tempStart;


        //这是第几个点？

        requestLayout();
    }

    /**
     * 记录上一次的x，y坐标
     */
    private float mLastX;
    private float mLastY;


    //dispatchTouchEvent是处理触摸事件分发,事件(多数情况)是从Activity的dispatchTouchEvent开始的。执行super.dispatchTouchEvent(ev)，事件向下分发。
    //onTouchEvent是View中提供的方法，ViewGroup也有这个方法，view中不提供onInterceptTouchEvent。view中默认返回true，表示消费了这个事件。
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

//        getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mDownTime = System.currentTimeMillis();
                mTmpAngle = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                isTouchUp = false;          //注意isTouchUp 这个标记量！！！
                /**
                 * 获得开始的角度
                 */
                float start = getAngle(mLastX, mLastY);
                /**
                 * 获得当前的角度
                 */
                float end = getAngle(x, y);


                System.out.println("11111113333333333end-start:"+(end - start));

//                System.out.println("11111111112222222mStartAngle:"+mStartAngle);
                // 如果是一、四象限，则直接end-start，角度值都是正值
                if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {
                    mStartAngle += end - start;
                    mTmpAngle += end - start;
                } else
                // 二、三象限，色角度值是付值
                {
                    mStartAngle += start - end;
                    mTmpAngle += start - end;
                }

                if(mStartAngle>90){
//                    isTouchUp=true;
                    mStartAngle = 90;

                }
// else
                if(mStartAngle<-(int)mAngleInterval*(getChildCount()-1)+90){
//                       isTouchUp=true;
                    mStartAngle = -(int)mAngleInterval*(getChildCount()-1)+90;

                }

                System.out.println("11111mStartAngle:"+mStartAngle);

                // 重新布局
                if (mTmpAngle != 0) {
                    requestLayout();
                }

                mLastX = x;
                mLastY = y;

                break;
            case MotionEvent.ACTION_UP:
                backOrPre();
                break;
            case MotionEvent.ACTION_CANCEL:
                System.out.println("xxxxxx1111111111111ACTION_CANCEL");
                break;
        }
        return super.dispatchTouchEvent(event);
    }


    /**
     * 根据触摸的位置，计算角度
     *
     * @param xTouch
     * @param yTouch
     * @return
     */
    private float getAngle(float xTouch, float yTouch) {
        double x = xTouch - (mRadius / 2d);
        double y = yTouch - (mRadius / 2d)+(mRadius / 2d);
        return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
    }

    /**
     * 根据当前位置计算象限
     *
     * @param x
     * @param y
     * @return
     */
    private int getQuadrant(float x, float y) {
        int tmpX = (int) (x - mRadius / 2);
        int tmpY = (int) (y - mRadius / 2);
        if (tmpX >= 0) {
            return tmpY >= 0 ? 4 : 1;
        } else {
            return tmpY >= 0 ? 3 : 2;
        }

    }


    public void setAngle(int position) {
        float angleDelay = 360 / 10;
        if (position > mCurrentPosition) {
            mStartAngle += (mCurrentPosition - position) * angleDelay;
        } else {
            mStartAngle -= (position - mCurrentPosition) * angleDelay;
        }
        requestLayout();
    }

    /**
     * 设置菜单条目的图标和文本
     *
     * @param resIds
     */
    public void setMenuItemIconsAndTexts(int[] resIds, String[] texts) {
//        mItemImgs = resIds;
//
//        // 参数检查
//        if (resIds == null) {
//            throw new IllegalArgumentException("菜单项文本和图片至少设置其一");
//        }
//
//        // 初始化mMenuCount
//        mMenuItemCount = resIds == null ? 0 : resIds.length;



        mItemImgs = resIds;
        mItemTexts = texts;

        // 参数检查
        if (resIds == null && texts == null)
        {
            throw new IllegalArgumentException("菜单项文本和图片至少设置其一");
        }

        // 初始化mMenuCount
        mMenuItemCount = resIds == null ? texts.length : resIds.length;

        if (resIds != null && texts != null)
        {
            mMenuItemCount = Math.min(resIds.length, texts.length);
        }

        addMenuItems();
    }



    public void setAngleInterval(double angleInterval) {
        mAngleInterval = angleInterval;
    }

    /**
     *
     *
     * @param view
     */
    public void addByView(View view) {

        mMenuItemCount+=1;
        addView(view);


    }

    /**
     *
     *
     * @param views
     */
    public void addByAllView(List<View> views) {

        removeAllViews();

        mMenuItemCount=views.size();
        mStartAngle = -(int)mAngleInterval*(mMenuItemCount-1) +90;

        for (View view:views){
            addView(view);
        }



    }


    private int mMenuItemLayoutId = R.layout.circle_menu_item;
    /**
     * 添加菜单项
     */
    private void addMenuItems() {

        removeAllViews();
        LayoutInflater mInflater = LayoutInflater.from(getContext());
//        mStartAngle = -50*(mMenuItemCount-1) +90;
        /**
         * 根据用户设置的参数，初始化view
         */
        for (int i = 0; i < mMenuItemCount; i++)
        {
            final int j = i;
            View view = mInflater.inflate(mMenuItemLayoutId, this, false);
            ImageView iv = (ImageView) view
                    .findViewById(R.id.id_circle_menu_item_image);
            TextView tv = (TextView) view
                    .findViewById(R.id.id_circle_menu_item_text);

            if (iv != null)
            {
                iv.setVisibility(View.VISIBLE);
                iv.setImageResource(mItemImgs[mMenuItemCount-i-1]);
                iv.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if(mTmpAngle !=0){
                            return;
                        }
                        //改变选中的坐标
                        setStartAngle(j);


                        if (mOnMenuItemClickListener != null)
                        {
                            mOnMenuItemClickListener.itemClick(v, mMenuItemCount-j-1);



                        }
                    }
                });
            }
            if (tv != null)
            {
                tv.setVisibility(View.VISIBLE);
                tv.setText(mItemTexts[mMenuItemCount-i-1]);
            }

            // 添加view到容器中
            addView(view);
        }
//        invalidate();

    }



    private void setStartAngle(int i) {     //缓冲的角度。即我们将要固定几个位置，而不是任意位置。我们要设计一个可能的角度去自动帮他选择。


        //-360 和 到 90之间最近的点

        double startAngleTemp = -(int)mAngleInterval*(i)+90;

        if(mStartAngle == startAngleTemp){
            return;
        }

        mStartAngle = startAngleTemp;


        //这是第几个点？

        requestLayout();
    }




    /**
     * 如果每秒旋转角度到达该值，则认为是自动滚动
     *
     * @param mFlingableValue
     */
    public void setFlingableValue(int mFlingableValue) {
        this.mFlingableValue = mFlingableValue;
    }

    /**
     * 设置内边距的比例
     *
     * @param mPadding
     */
    public void setPadding(float mPadding) {
        this.mPadding = mPadding;
    }

    /**
     * 获得默认该layout的尺寸
     *
     * @return
     */
    private int getDefaultWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }

}
