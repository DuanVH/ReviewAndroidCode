package com.example.gem.reviewandroidcode;

import android.support.multidex.MultiDexApplication;

public class ReviewAndroidCodeApplication extends MultiDexApplication {

  private static ReviewAndroidCodeApplication sInstance;

  public static ReviewAndroidCodeApplication getInstance() {
    return sInstance;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    sInstance = this;

  }
}
