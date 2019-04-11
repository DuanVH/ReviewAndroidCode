package com.example.gem.reviewandroidcode.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Patterns;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String Utils
 */
public class StringUtils {
  public static boolean isEmpty(String s) {
    return s == null || s.trim().isEmpty();
  }

  public static boolean isEmpty(CharSequence s) {
    return s == null || s.length() == 0;
  }

  public static boolean isNumeric(String s) {
    return s.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
  }

  public static void openLink(Context context, String link) {
    Intent i = new Intent(Intent.ACTION_VIEW);
    i.setData(Uri.parse(link));
    context.startActivity(i);
  }

  /**
   * method is used for checking valid email id format.
   *
   * @param email
   * @return boolean true for valid false for invalid
   */
  public static boolean isEmailValid(String email) {
    if (null == email) return false;

    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }

  /**
   * Format number to thousands comma (,) separator
   */
  public static String getNumberFormatted(long number) {
    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
    DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

    symbols.setGroupingSeparator(',');
    formatter.setDecimalFormatSymbols(symbols);

    return formatter.format(number);
  }

  public static int compareLowerCase(String string1, String string2) {
    if (StringUtils.isEmpty(string1)) {
      if (StringUtils.isEmpty(string2)) {
        return 0;
      }
      return 1;
    } else if (StringUtils.isEmpty(string2)) {
      return -1;
    } else {
      return string1.toLowerCase().compareTo(string2.toLowerCase());
    }
  }

  public static boolean equalsIgnoreCase(String s1, String s2) {
    return s1 != null && s1.equalsIgnoreCase(s2);
  }

  public static String getNameDisplay(String... values) {
    if (values != null && values.length > 0) {
      for (String v : values) {
        if (v != null && !v.isEmpty()) {
          return v;
        }
      }
    }
    return "";
  }


  @NonNull
  public static String getPercent(String percent) {
    return " - ".concat(percent).concat("%");
  }

  @NonNull
  public static String getTotalPhotos(int total) {

    if (total <= 1) {
      return String.valueOf(total).concat(" photo");
    } else {
      return String.valueOf(total).concat(" photos");
    }
  }

  public static String setPercent(Double percent) {
    if (percent == null) {
      percent = 0d;
    }
    return String.valueOf(percent).concat("%");
  }

  public static boolean checkEmail(String email) {
    Pattern pattern = Patterns.EMAIL_ADDRESS;
    return !isEmpty(email) && pattern.matcher(email).matches();
  }

  public static boolean checkPhone(String phone) {
    Pattern pattern = Patterns.PHONE;
    return !isEmpty(phone) && pattern.matcher(phone).matches();
  }
}
