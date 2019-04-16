package com.example.gem.reviewandroidcode.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.gem.reviewandroidcode.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by maboy on 16/4/19.
 */

public class HeaderEmergencyView extends LinearLayout {

  @BindView(R.id.iv_icon_emergency)
  ImageView mIcon;
  @BindView(R.id.tv_emergency)
  MbTextView mTitle;
  @BindView(R.id.iv_line)
  ImageView mLineIv;

  private Unbinder mUnbinder;

  public HeaderEmergencyView(Context context) {
    this(context, null);
  }

  public HeaderEmergencyView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, -1);
  }

  public HeaderEmergencyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    View view = inflate(context, R.layout.item_header_emergency, this);
    mUnbinder = ButterKnife.bind(this, view);
    TypedArray input = getContext().obtainStyledAttributes(attrs, R.styleable.HeaderEmergencyView, 0, 0);

    mIcon.setImageDrawable(input.getDrawable(R.styleable.HeaderEmergencyView_icon_emergency));
    mTitle.setText(input.getString(R.styleable.HeaderEmergencyView_text_header));
    mTitle.setTextColor(input.getColor(R.styleable.HeaderEmergencyView_text_color, Color.RED));
    mTitle.setTextSize(input.getInt(R.styleable.HeaderEmergencyView_text_size, 20));
    mLineIv.setImageDrawable(input.getDrawable(R.styleable.HeaderEmergencyView_icon_line));
  }

  public void setIcon(int resId) {
    mIcon.setImageResource(resId);
  }

  public void setText(String text) {
    mTitle.setText(text);
  }

  public void setTextColor(int color) {
    mTitle.setTextColor(getContext().getResources().getColor(color));
  }

  public void setTextDirection(int isRightLeft) {
    if (isRightLeft == 0) {
      mTitle.setTextDirection(TEXT_DIRECTION_LTR);
    } else if (isRightLeft == 1) {
      mTitle.setTextDirection(TEXT_DIRECTION_RTL);
    }
  }

  public void setTextDirection(String isRTL) {
    if (isRTL.equals("0")) {
      mTitle.setTextDirection(TEXT_DIRECTION_LTR);
    } else if (isRTL.equals("1")) {
      mTitle.setTextDirection(TEXT_DIRECTION_RTL);
    }
  }


  @Override
  protected void onDetachedFromWindow() {
    mUnbinder.unbind();
    super.onDetachedFromWindow();
  }
}
