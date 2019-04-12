package com.example.gem.reviewandroidcode.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by maboy on 12/4/2019.
 */

public class ScreenUtils {
  private static ScreenUtils sInstance;
  private int mScreenWidth;
  private int mScreenHeight;
  private int mHeightBottomMenu;

  private ScreenUtils() {
  }

  public static ScreenUtils getInstance() {
    if (sInstance == null) {
      synchronized (ScreenUtils.class) {
        if (sInstance == null)
          sInstance = new ScreenUtils();
      }
    }
    return sInstance;
  }

  public void init(Context context) {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    windowManager.getDefaultDisplay().getMetrics(displayMetrics);
    mScreenWidth = displayMetrics.widthPixels;
    mScreenHeight = displayMetrics.heightPixels;
//    mHeightBottomMenu = context.getResources().getDimensionPixelSize();
  }

  public int getScreenWidth() {
    return mScreenWidth;
  }

  public int getScreenHeight() {
    return mScreenHeight;
  }

  public int getHeightBottom() {
    return mHeightBottomMenu;
  }

}
