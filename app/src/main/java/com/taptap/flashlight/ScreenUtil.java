package com.taptap.flashlight;

import android.content.Context;
import android.os.Build;

/**
 * Created by hqx on 2019/4/26 10:36.
 */
public class ScreenUtil {
  public static int statusHeight;

  /**
   * 获得状态栏高度
   */

  public static int getStatusBarHeight(Context context) {
    if (statusHeight != 0) {
      return statusHeight;
    }
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
      if (resourceId > 0) {
        statusHeight = context.getResources().getDimensionPixelSize(resourceId);
      }
      return statusHeight;
    }
    return 0;
  }
}
