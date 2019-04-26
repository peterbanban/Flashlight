package com.taptap.flashlight;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hqx on 2019/4/25 17:13.
 */
public class FlashlightView extends FrameLayout {
  @BindView(R.id.container)
  RelativeLayout mContainer;
  @BindView(R.id.primary_bg)
  FrameLayout mPrimaryBg;
  ImageView mTouchBtn;

  private static final Interpolator FAST_OUT_SLOW_IN_INTERPOLATOR = new FastOutSlowInInterpolator();
  private View mRoot;
  private ValueAnimator mAlphaAnimator;
  private ValueAnimator mMoveAnimator;
  private boolean lightFlag = false;
  private boolean isFirstMeasure = true;
  private long mPreClickTime = System.currentTimeMillis();
  private float  mTouchDownY;
  private float mTouchUpY;
  private int mMoveHeight;

  public FlashlightView(Context context) {
    super(context);
    initView();
  }

  public FlashlightView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  public FlashlightView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView();
  }

  public void initView (){
    mRoot = inflate(getContext(), R.layout.flashlight_view, this);
    ButterKnife.bind(mRoot);
    mRoot.setBackgroundResource(R.drawable.primary_bg);
    mTouchBtn  = new ImageView(getContext());
    mTouchBtn.setImageResource(R.drawable.touch_btn);
    mTouchBtn.setClickable(true);
    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(dip2px(getContext(), 220), dip2px(getContext(), 220));
    lp.setMargins(0,  dip2px(getContext(), 160), 0 , 0);
    lp.gravity = Gravity.CENTER_HORIZONTAL;
    mPrimaryBg.setAlpha(0);
    addView(mTouchBtn, lp);
//    mTouchBtn.setOnTouchListener(this);
  }

  public void btnMoveAnimation(boolean light) {
    if (light == lightFlag) {
      return;
    }
    if (mMoveAnimator == null) {
      mMoveAnimator = new ValueAnimator();
    }
    if (mMoveAnimator.isRunning()) {
      lightFlag = !lightFlag;
      return;
    }
    mMoveAnimator.setDuration(300);
    mMoveAnimator.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR);
    if (lightFlag) {
      mMoveAnimator.setFloatValues(mTouchBtn.getY(), mTouchBtn.getY() - mMoveHeight);
    } else {
      mMoveAnimator.setFloatValues(mTouchBtn.getY(), mTouchBtn.getY() + mMoveHeight);
    }
    mMoveAnimator.addUpdateListener(new AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        mTouchBtn.setY((float)animation.getAnimatedValue());
      }
    });
    mMoveAnimator.start();
  }

  public void backgroundAlphaAnimation(boolean light) {
    if (light == lightFlag) {
      return;
    }
    if (mAlphaAnimator == null) {
      mAlphaAnimator = new ValueAnimator();
    }
    if (mAlphaAnimator.isRunning()) {
      lightFlag = !lightFlag;
      return;
    }
    mAlphaAnimator.setDuration(300);
    mAlphaAnimator.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR);
    if (!lightFlag) {
      mAlphaAnimator.setFloatValues(0f, 1f);
    } else {
      mAlphaAnimator.setFloatValues(1f, 0f);
    }

    mAlphaAnimator.addUpdateListener(new AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        mPrimaryBg.setAlpha((float)animation.getAnimatedValue());
      }
    });
    mAlphaAnimator.start();
  }

  public static int dip2px(Context context, float dipValue){
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int)(dipValue * scale + 0.5f);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    mMoveHeight = mContainer.getHeight() - mTouchBtn.getHeight() + dip2px(getContext(), 41);
    if (isFirstMeasure && mContainer.getTop() != 0) {
      int paddingTop = mContainer.getTop() - dip2px(getContext(), 20);
      FrameLayout.LayoutParams layoutParams = (LayoutParams) mTouchBtn.getLayoutParams();
      layoutParams.setMargins(0, paddingTop, 0 , 0);
      isFirstMeasure = false;
      requestLayout();
    }
    super.onDraw(canvas);

  }

//  @Override
//  public boolean onTouch(View v, MotionEvent event) {
//    switch (event.getAction()) {
//      case MotionEvent.ACTION_DOWN :
//        mMoveFlag = true;
//        mTouchDownY = event.getRawY() - ScreenUtil.getStatusBarHeight(getContext());
//        mPreClickTime = System.currentTimeMillis();
//        break;
//      case MotionEvent.ACTION_MOVE:
//        if (mMoveFlag) {
//          return caculateFlashlightChange(event);
//        }
//      case MotionEvent.ACTION_CANCEL:
//      case MotionEvent.ACTION_UP:
//        mMoveFlag = false;
//        mTouchUpY = event.getRawY() - ScreenUtil.getStatusBarHeight(getContext());
//        if (mTouchDownY > mContainer.getTop() - dip2px(getContext(), 10)
//            && mTouchDownY < mContainer.getBottom() - dip2px(getContext(), 10)
//            && mTouchUpY > mContainer.getTop() - dip2px(getContext(), 10)
//            && mTouchUpY < mContainer.getBottom() - dip2px(getContext(), 10)) {
//          if (Math.abs(mTouchDownY - mTouchUpY) <= 20 && mPreClickTime - System.currentTimeMillis() < 300) {
//            if (!lightFlag) {
//              FlashlightHelper.openFalshlight();
//            } else {
//              FlashlightHelper.shutdownFalshlight();
//            }
//            mPreClickTime = System.currentTimeMillis();
//            btnMoveAnimation(!lightFlag);
//            backgroundAlphaAnimation(!lightFlag);
//            lightFlag = !lightFlag;
//          }
//        }
//        return true;
//    }
//    return false;
//  }

  public boolean caculateFlashlightChange(MotionEvent event) {
    mTouchUpY = event.getRawY() - ScreenUtil.getStatusBarHeight(getContext());
    if (mTouchDownY > mContainer.getTop()- dip2px(getContext(), 10)
        && mTouchDownY < mContainer.getBottom() + dip2px(getContext(), 10)
        && mTouchUpY > mContainer.getTop()- dip2px(getContext(), 10)
        && mTouchUpY < mContainer.getBottom() + dip2px(getContext(), 10)) {
      if (mTouchUpY - mTouchDownY > 20) {
        if (!lightFlag) {
          btnMoveAnimation(true);
          backgroundAlphaAnimation(true);
          lightFlag = !lightFlag;
          return true;
        }
      } else if ( mTouchUpY - mTouchDownY < - 20) {
        if (lightFlag) {
          btnMoveAnimation(false);
          backgroundAlphaAnimation(false);
          lightFlag = !lightFlag;
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN :
        mTouchDownY = event.getRawY() - ScreenUtil.getStatusBarHeight(getContext());
        mPreClickTime = System.currentTimeMillis();
        break;
      case MotionEvent.ACTION_MOVE:
        int top = mContainer.getTop() + dip2px(getContext(), 10);
        int bottom = mContainer.getBottom() - dip2px(getContext(), 10);
        if (event.getY() >= top && event.getY() <= bottom) {
          mTouchBtn.setY(event.getY());
          float a = (mTouchBtn.getTop() - dip2px(getContext(), 30) - top) / (bottom - top);
          mPrimaryBg.setAlpha(a);
        }
           break;
      case MotionEvent.ACTION_CANCEL:
      case MotionEvent.ACTION_UP:
        btnMoveAutoScrollAnimation();
        bgAlphaAutoChangeAnimation();
        return true;
    }
    return super.onInterceptTouchEvent(event);
  }

  public void bgAlphaAutoChangeAnimation() {
    if (mAlphaAnimator == null) {
      mAlphaAnimator = new ValueAnimator();
    }
    if (mAlphaAnimator.isRunning()) {
      mAlphaAnimator.cancel();
      mAlphaAnimator.setDuration(300 - mAlphaAnimator.getCurrentPlayTime());
    } else {
      mAlphaAnimator.setDuration(300);
    }

    mAlphaAnimator.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR);
    int top = mContainer.getTop() + dip2px(getContext(), 10);
    int bottom = mContainer.getBottom() - dip2px(getContext(), 10);
    float a = (mTouchBtn.getTop() - dip2px(getContext(), 30) - top) / (bottom - top);
    if (mTouchBtn.getTop() -  dip2px(getContext(), 30) < (top + bottom) / 2) {
      mAlphaAnimator.setFloatValues(a, 0f);
    } else {
      mAlphaAnimator.setFloatValues(a, 1f);
    }
    mAlphaAnimator.addUpdateListener(new AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        mPrimaryBg.setAlpha((float)animation.getAnimatedValue());
      }
    });
    mAlphaAnimator.start();
  }

  public void btnMoveAutoScrollAnimation() {
    if (mMoveAnimator == null) {
      mMoveAnimator = new ValueAnimator();
    }
    if (mMoveAnimator.isRunning()) {
      mMoveAnimator.cancel();
      mMoveAnimator.setDuration(300 - mAlphaAnimator.getCurrentPlayTime());
    } else {
      mMoveAnimator.setDuration(300);
    }
    mMoveAnimator.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR);
    int top = mContainer.getTop() + dip2px(getContext(), 10);
    int bottom = mContainer.getBottom() - dip2px(getContext(), 10);
    if (mTouchBtn.getTop() - dip2px(getContext(), 30) < (top + bottom) / 2) {
      mMoveAnimator.setFloatValues(mTouchBtn.getTop() - dip2px(getContext(), 30), mContainer.getTop() + dip2px(getContext(), 10));
    } else {
      mMoveAnimator.setFloatValues(mTouchBtn.getBottom() - dip2px(getContext(), 30), mContainer.getBottom() - dip2px(getContext(), 10));
    }

    mMoveAnimator.addUpdateListener(new AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        mTouchBtn.setY((float)animation.getAnimatedValue());
      }
    });
    mMoveAnimator.start();
  }
}
