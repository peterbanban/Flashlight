package com.taptap.flashlight;

import android.hardware.Camera;

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
//
//  public static FlashlightHelper getInstance() {
//    if (mInstance == null) {
//      synchronized (FlashlightHelper.class) {
//        if (mInstance == null) {
//          mInstance = new FlashlightHelper();
//        }
//      }
//    }
//    return mInstance;
//  }

  public static void openFalshlight() {
    try {
      Camera.Parameters mParameters;
      mParameters = getCamera().getParameters();
      mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
      getCamera().setParameters(mParameters);
    } catch (Exception ex) {
    }
  }

  public static void shutdownFalshlight() {
    try {
      Camera.Parameters mParameters;
      mParameters = getCamera().getParameters();
      mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
      getCamera().setParameters(mParameters);
      getCamera().release();
    } catch (Exception ex) {
    }
  }
}
