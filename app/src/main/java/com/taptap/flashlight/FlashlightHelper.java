package com.taptap.flashlight;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.widget.Toast;

/**
 * Created by hqx on 2019/4/25 16:17.
 */
public class FlashlightHelper {

  private static FlashlightHelper mInstance;
  private static Camera mCamera;

  public static Camera getCamera() {
    if (mCamera == null) {
      synchronized (Camera.class) {
        if (mCamera == null) {
          mCamera = Camera.open();
        }
      }
    }
    return mCamera;
  }

  public static void openFalshlight() {
    try {
      Camera.Parameters mParameters;
      mParameters = getCamera().getParameters();
      mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
      getCamera().setParameters(mParameters);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void shutdownFalshlight() {
    try {
      Camera.Parameters mParameters;
      mParameters = getCamera().getParameters();
      mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
      getCamera().setParameters(mParameters);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static boolean supportFlashlight (Context c) {
    if (!c.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
      Toast.makeText(c, "很抱歉，设备不支持闪光灯", Toast.LENGTH_LONG);
      return false;
    }
    return true;
  }
}
