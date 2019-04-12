package com.example.gem.reviewandroidcode.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by dong on 10/5/17.
 */

public class DateTimeUtils {
  public static String getAudioTimeString(int time) {
    int totalSeconds = (int) Math.floor(time / 1000);
    int minute = totalSeconds / 60;
    int second = totalSeconds - minute * 60;
    String minuteString = minute < 10 ? "0" + minute : minute + "";
    String secondString = second < 10 ? "0" + second : second + "";
    return minuteString + ":" + secondString;
  }

  public static String getVideoTimeString(int time) {
    int totalSeconds = (int) Math.floor(time / 1000);
    int hour = (totalSeconds / 60) / 60;
    if (hour == 0)
      return getAudioTimeString(time);
    int minute = (totalSeconds - (hour * 60 * 60)) / 60;
    int second = totalSeconds - (minute + hour * 60) * 60;
    String hourString = hour < 10 ? "0" + hour : "" + hour;
    String minuteString = minute < 10 ? "0" + minute : "" + minute;
    String secondString = second < 10 ? "0" + second : "" + second;
    return hourString + ":" + minuteString + ":" + secondString;
  }

  public static long getTime(String date, String format) throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
    return simpleDateFormat.parse(date).getTime();
  }
}
