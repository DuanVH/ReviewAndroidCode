package com.example.gem.reviewandroidcode.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.example.gem.reviewandroidcode.R;
import com.example.gem.reviewandroidcode.utils.FontManager;

public class MbEditText extends AppCompatEditText {

  private String mFontPath;

  public MbEditText(Context context) {
    this(context, null);
  }

  public MbEditText(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MbEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    if (attrs != null) {
      TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MbText);
      mFontPath = typedArray.getString(R.styleable.MbText_font_path);
      typedArray.recycle();
    }
    if (TextUtils.isEmpty(mFontPath))
      mFontPath = "fonts/SFUIText-Regular.otf";
    setTypeface(FontManager.getInstance().getFont(getContext(), mFontPath));
  }

  public void setFont(String fontPath) {
    mFontPath = fontPath;
    setTypeface(FontManager.getInstance().getFont(getContext(), mFontPath));
  }
}
