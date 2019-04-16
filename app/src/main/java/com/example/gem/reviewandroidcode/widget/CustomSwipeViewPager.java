package com.example.gem.reviewandroidcode.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class CustomSwipeViewPager extends ViewPager {

  private boolean mSwipeEnabled = true;

  public CustomSwipeViewPager(Context context) {
    super(context);
  }

  public CustomSwipeViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void setSwipeEnabled(boolean enabled){
    this.mSwipeEnabled = enabled;
  }
}