package com.example.gem.reviewandroidcode.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.example.gem.reviewandroidcode.R;
import com.example.gem.reviewandroidcode.utils.FontManager;

public class MbTextView extends AppCompatTextView {

  private String mFontPath;

  public MbTextView(Context context) {
    this(context, null);
  }

  public MbTextView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MbTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  public void init(Context context, AttributeSet attributeSet) {
    if (attributeSet != null) {
      TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.MbText);
      mFontPath = typedArray.getString(R.styleable.MbText_font_path);
      typedArray.recycle();
    }
    if (TextUtils.isEmpty(mFontPath))
      mFontPath = "fonts/SFUIText-Regular.otf";

    setTypeface(FontManager.getInstance().getFont(getContext(), mFontPath));
    setIncludeFontPadding(false);
  }

  public void setFont(String path) {
    mFontPath = path;
    setTypeface(FontManager.getInstance().getFont(getContext(), mFontPath));
  }

}
