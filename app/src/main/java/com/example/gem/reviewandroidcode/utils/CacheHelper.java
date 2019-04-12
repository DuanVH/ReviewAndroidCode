package com.example.gem.reviewandroidcode.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

import com.example.gem.reviewandroidcode.service.ConnectionHelper;


/**
 * Created by dongc on 11/5/2017.
 */

public class CacheHelper {
  private static final String PREF_NAME = "jp.pamz.pam";
  public static final String VALUE_NAME_GOOGLE = "VALUE_NAME_GOOGLE";
  public static final String VALUE_TAB_SELECTED = "VALUE_TAB_SELECTED";
  public static final String IS_CONFIRM = "IS_CONFIRM";
  public static final String FIRST_SETTING = "FIRST_SETTING";
  public static final String VALUE_LANGUAGE_FIRST = "VALUE_LANGUAGE_FIRST";
  public static final String VALUE_LANGUAGE_SECOND = "VALUE_LANGUAGE_SECOND";
  public static final String VALUE_NAME_EN = "VALUE_NAME_EN";
  public static final String LAT_SPOT_DETAIL = "LAT_SPOT_DETAIL";
  public static final String LNG_SPOT_DETAIL = "LNG_SPOT_DETAIL";
  public static final String LIST_LANGUAGE = "LIST_LANGUAGE";
  public static final String LIST_COUNTRY = "LIST_COUNTRY";
  public static final String IS_RTL = "IS_RTL";
  public static final String LAST_UPDATE_AR_LATITUDE = "LAST_UPDATE_AR_LATITUDE";
  public static final String LAST_UPDATE_AR_LONGITUDE = "LAST_UPDATE_AR_LONGITUDE";
  public static final String FIRST_LAT_LOCATION = "FIRST_LAT_LOCATION";
  public static final String FIRST_LNG_LOCATION = "FIRST_LNG_LOCATION";
  public static final String COUNT_LOCATION_CHANGE = "COUNT_LOCATION_CHANGE";
  public static final String TRANSLATE_X = "TRANSLATE_X";
  public static final String TRANSLATE_Y = "TRANSLATE_Y";

  private static CacheHelper sInstance;
  private Context mContext;
  private SharedPreferences mSharedPreferences;

  private CacheHelper() {
  }

  public static CacheHelper getInstance() {
    if (sInstance == null) {
      synchronized (CacheHelper.class) {
        if (sInstance == null)
          sInstance = new CacheHelper();
      }
    }
    return sInstance;
  }

  public void init(Context context) {
    this.mContext = context;
    mSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
  }

  // ======================== UTILITY FUNCTIONS ========================

  /**
   * Save a long integer to SharedPreferences
   *
   * @param key
   * @param n
   */
  public void putLongValue(String key, long n) {
    mSharedPreferences.edit().putLong(key, n).apply();
  }

  /**
   * Read a long integer to SharedPreferences
   *
   * @param key
   * @return
   */
  public long getLongValue(String key) {
    return mSharedPreferences.getLong(key, 0);
  }

  /**
   * Save an integer to SharedPreferences
   *
   * @param key
   * @param n
   */
  public void putIntValue(String key, int n) {
    mSharedPreferences.edit().putInt(key, n).apply();
  }

  /**
   * Read an integer to SharedPreferences
   *
   * @param key
   * @return
   */
  public int getIntValue(String key) {
    return mSharedPreferences.getInt(key, 0);
  }


  /**
   * Save an string to SharedPreferences
   *
   * @param key
   * @param s
   */
  public void putStringValue(String key, String s) {
    mSharedPreferences.edit().putString(key, s).apply();
  }

  /**
   * Save an string to SharedPreferences
   *
   * @param key
   * @param s
   * @param sync
   */
  public void putStringValue(String key, String s, boolean sync) {
    if (!sync)
      putStringValue(key, s);
    else
      mSharedPreferences.edit().putString(key, s).commit();
  }

  /**
   * Read an string to SharedPreferences
   *
   * @param key
   * @return
   */
  public String getStringValue(String key) {
    return mSharedPreferences.getString(key, "");
  }

  /**
   * Read an string to SharedPreferences
   *
   * @param key
   * @param defaultValue
   * @return
   */
  public String getStringValue(String key, String defaultValue) {
    return mSharedPreferences.getString(key, defaultValue);
  }

  /**
   * Save an boolean to SharedPreferences
   *
   * @param key
   * @param key,b
   */
  public void putBooleanValue(String key, Boolean b) {
    mSharedPreferences.edit().putBoolean(key, b).apply();
  }

  /**
   * Read an boolean to SharedPreferences
   *
   * @param key
   * @return
   */
  public boolean getBooleanValue(String key) {
    return mSharedPreferences.getBoolean(key, false);
  }

  /**
   * Save an float to SharedPreferences
   *
   * @param key
   * @param key,f
   */
  public void putFloatValue(String key, float f) {
    mSharedPreferences.edit().putFloat(key, f).apply();
  }

  /**
   * Read an float to SharedPreferences
   *
   * @param key
   * @return
   */
  public float getFloatValue(String key) {
    return mSharedPreferences.getFloat(key, 0.0f);
  }

//  public void saveListLanguage(int key, List<LanguageModel> list) {
//    mSharedPreferences.edit().putString(LIST_LANGUAGE + key, JsonHelper.getGson().toJson(list)).commit();
//  }
//
//  public void saveListCountry(int key, List<CountryModel> list) {
//    mSharedPreferences.edit().putString(LIST_COUNTRY + key, JsonHelper.getGson().toJson(list)).commit();
//  }
//
//  public List<LanguageModel> getListLanguage(int key) {
//    if (mSharedPreferences.contains(LIST_LANGUAGE + key)) {
//      String menus = mSharedPreferences.getString(LIST_LANGUAGE + key, "");
//      if (JsonHelper.isJson(menus))
//        return JsonHelper.getGson().fromJson(menus, new TypeToken<List<LanguageModel>>() {
//        }.getType());
//    }
//    return null;
//  }
//
//  public List<CountryModel> getListCountry(int key) {
//    if (mSharedPreferences.contains(LIST_COUNTRY + key)) {
//      String menus = mSharedPreferences.getString(LIST_COUNTRY + key, "");
//      if (JsonHelper.isJson(menus))
//        return JsonHelper.getGson().fromJson(menus, new TypeToken<List<CountryModel>>() {
//        }.getType());
//    }
//    return null;
//  }

  public double getLastKnownLatitude() {
    return CacheHelper.getInstance().getFloatValue(CacheHelper.LAT_SPOT_DETAIL);
  }

  public double getLastKnownLongitude() {
    return CacheHelper.getInstance().getFloatValue(CacheHelper.LNG_SPOT_DETAIL);
  }

  public void saveLastKnownLocation(Location myLocation) {
    putFloatValue(CacheHelper.LAT_SPOT_DETAIL, (float) myLocation.getLatitude());
    putFloatValue(CacheHelper.LNG_SPOT_DETAIL, (float) myLocation.getLongitude());
  }

  public void saveLastUpdateArContentLocation(double lat, double lng) {
    putFloatValue(LAST_UPDATE_AR_LATITUDE, (float) lat);
    putFloatValue(LAST_UPDATE_AR_LONGITUDE, (float) lng);
  }

  public void saveFirstLocation(Location firstLocation) {
    putFloatValue(FIRST_LAT_LOCATION, (float) firstLocation.getLatitude());
    putFloatValue(FIRST_LNG_LOCATION, (float) firstLocation.getLongitude());
  }

  public double getFirstLatLocation() {
    return CacheHelper.getInstance().getFloatValue(CacheHelper.FIRST_LAT_LOCATION);
  }

  public double getFirstLngLocation() {
    return CacheHelper.getInstance().getFloatValue(CacheHelper.FIRST_LNG_LOCATION);
  }

  public void saveCountLocationChange(int count) {
    putIntValue(COUNT_LOCATION_CHANGE, count);
  }

  public int getCountLocationChange() {
    return CacheHelper.getInstance().getIntValue(CacheHelper.COUNT_LOCATION_CHANGE);
  }

//  public boolean isDownloadArContentNeeded(double currentLat, double currentLng) {
//    if(!ConnectionHelper.isConnectedOrConnecting(mContext)) return false;
//    float lastDownloadLat = getFloatValue(LAST_UPDATE_AR_LATITUDE);
//    float lastDownloadLng = getFloatValue(LAST_UPDATE_AR_LONGITUDE);
//    boolean check = ArContentDatabase.getArContentSize() == 0 ||(lastDownloadLat == 0f && lastDownloadLng == 0f)
//            || AppUtils.calculateDistance(currentLat, currentLng, lastDownloadLat, lastDownloadLng)
//            > Constants.AR_CONFIG.DISTANCE_TO_UPDATE;
//    if (check) saveLastUpdateArContentLocation(currentLat, currentLng);
//    return check;
//  }

  public void resetArContentLocation() {
    putFloatValue(LAST_UPDATE_AR_LATITUDE, 0f);
    putFloatValue(LAST_UPDATE_AR_LONGITUDE, 0f);
  }

  public void saveTranslate(float x, float y) {
    CacheHelper.getInstance().putFloatValue(TRANSLATE_X, x);
    CacheHelper.getInstance().putFloatValue(TRANSLATE_Y, y);
  }

  public float getTranslateX() {
    return CacheHelper.getInstance().getFloatValue(TRANSLATE_X);
  }

  public float getTranslateY() {
    return CacheHelper.getInstance().getFloatValue(TRANSLATE_Y);
  }
}