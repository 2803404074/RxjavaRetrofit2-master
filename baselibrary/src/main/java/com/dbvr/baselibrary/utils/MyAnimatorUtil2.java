package com.dbvr.baselibrary.utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;

import java.util.List;

public class MyAnimatorUtil2 {
    private Context mContext;
    private ValueAnimator valueAnimator;
    private static MyAnimatorUtil2 myAnimatorUtil2;

    public static MyAnimatorUtil2 getInstance(Context mContext){
        if (myAnimatorUtil2 == null){
            myAnimatorUtil2 = new MyAnimatorUtil2(mContext);
        }
        return myAnimatorUtil2;
    }

    public MyAnimatorUtil2(Context mContext) {
        this.mContext = mContext;
    }


    /**
     * 开始动画
     * @param view 动画布局
     * @param position 0左右动画。1上下动画
     * @param length 动画位移量
     */
    public void startAnimatorHorizontal(final View view, final int position, int length){
        if (view.getVisibility() == View.VISIBLE)return;
        view.measure(0, 0);
        valueAnimator = ValueAnimator.ofInt(0, DensityUtil.dip2px(mContext,length)).setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (position == 0){
                    view.getLayoutParams().width = (int) animation.getAnimatedValue();
                }
                if (position == 1){
                    view.getLayoutParams().height = (int) animation.getAnimatedValue();
                }
                view.requestLayout();
            }
        });
        valueAnimator.start();
        view.setVisibility(View.VISIBLE);
    }


    public void startAnimatorHorizontal(final List<View> view, final int position, int length){
        for (int i = 0; i < view.size() ; i++) {
            if (view.get(i).getVisibility() == View.VISIBLE)continue;
            view.get(i).measure(0, 0);
            valueAnimator = ValueAnimator.ofInt(0, DensityUtil.dip2px(mContext,length)).setDuration(300);
            final int finalI = i;
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (position == 0){
                        view.get(finalI).getLayoutParams().width = (int) animation.getAnimatedValue();
                    }
                    if (position == 1){
                        view.get(finalI).getLayoutParams().height = (int) animation.getAnimatedValue();
                    }
                    view.get(finalI).requestLayout();
                }
            });
            valueAnimator.start();
            view.get(finalI).setVisibility(View.VISIBLE);
        }
    }


    public void stopAnimator(final View view, final int position, int length){
        if (view.getVisibility() == View.INVISIBLE)return;
        view.measure(0, 0);
        valueAnimator = ValueAnimator.ofInt(DensityUtil.dip2px(mContext,length),0).setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (position == 0){
                    view.getLayoutParams().width = (int) animation.getAnimatedValue();
                }
                if (position == 1){
                    view.getLayoutParams().height = (int) animation.getAnimatedValue();
                }
                view.requestLayout();
            }
        });
        valueAnimator.start();
        view.setVisibility(View.INVISIBLE);
    }

    public void stopAnimator(final List<View> view, final int position, int length){
        for (int i = 0; i <view.size() ; i++) {
            if (view.get(i).getVisibility() == View.INVISIBLE)continue;
            view.get(i).measure(0, 0);
            valueAnimator = ValueAnimator.ofInt(DensityUtil.dip2px(mContext,length),0).setDuration(300);
            final int finalI = i;
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    if (position == 0){
                        view.get(finalI).getLayoutParams().width = (int) animation.getAnimatedValue();
                    }
                    if (position == 1){
                        view.get(finalI).getLayoutParams().height = (int) animation.getAnimatedValue();
                    }
                    view.get(finalI).requestLayout();
                }
            });
            valueAnimator.start();
            view.get(finalI).setVisibility(View.INVISIBLE);
        }

    }
}
