package com.example.gem.reviewandroidcode.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by dong on 30/10/2017.
 */

public class FontManager {
  private static FontManager sInstance;
  private HashMap<String, Typeface> fonts;

  private FontManager() {
    fonts = new HashMap<>();
  }

  public static FontManager getInstance() {
    if (sInstance == null) {
      synchronized (FontManager.class) {
        if (sInstance == null) {
          sInstance = new FontManager();
        }
      }
    }
    return sInstance;
  }

  public Typeface getFont(Context context, String fontPath) {
    if (!fonts.containsKey(fontPath)) {
      Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontPath);
      fonts.put(fontPath, typeface);
    }
    return fonts.get(fontPath);
  }

  public void setFonts(Context context, String fontPath, TextView textView) {
    Typeface typeface = getFont(context, fontPath);
    if (typeface != null)
      textView.setTypeface(typeface);
  }
}
