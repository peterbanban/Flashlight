package com.taptap.flashlight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by hqx on 2019/4/26 10:36.
 */
public class ScreenUtil {
  public static int height = 0;
  public static int width = 0;
  public static int statusHeight;

  /**
   * 获得状态栏高度
   *
   * @param activity
   * @return
   */
  public static void setStatusHeight(Activity activity) {
    Rect rect = new Rect();
    activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
    statusHeight = rect.top ;
  }

  /**
   * 获得屏幕高度
   *
   * @param context
   * @return
   */
  public static int getScreenWidth(Context context)
  {
    if (width == -1) {
      WindowManager wm = (WindowManager) context
          .getSystemService(Context.WINDOW_SERVICE);
      DisplayMetrics outMetrics = new DisplayMetrics();
      wm.getDefaultDisplay().getMetrics(outMetrics);
      width = outMetrics.widthPixels;
      height = outMetrics.heightPixels;
      return outMetrics.widthPixels;
    } else {
      return width;
    }

  }

  /**
   * 获得屏幕宽度
   *
   * @param context
   * @return
   */
  public static int getScreenHeight(Context context)
  {
    if (height == -1) {
      WindowManager wm = (WindowManager) context
          .getSystemService(Context.WINDOW_SERVICE);
      DisplayMetrics outMetrics = new DisplayMetrics();
      wm.getDefaultDisplay().getMetrics(outMetrics);
      width = outMetrics.widthPixels;
      height = outMetrics.heightPixels;
      return height;
    } else {
      return height;
    }

  }
}
