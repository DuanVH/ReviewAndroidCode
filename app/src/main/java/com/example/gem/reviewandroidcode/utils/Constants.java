package com.example.gem.reviewandroidcode.utils;

/**
 * Created by dong on 10/4/17.
 */

public class Constants {
  public static final String LOG_TAG = "PAM_Z";
  public static final int TYPE_ANDROID_DEVICE = 1;

  public static class Bundle {
    public static final String KEY_ID = "KEY_ID";

    public static final String TAG = "HomeActivity";

    public static final String TYPE_FIRST_LANGUAGE = "TYPE_FIRST_LANGUAGE";
    public static final String TYPE_SECOND_LANGUAGE = "TYPE_SECOND_LANGUAGE";
    public static final String SPOT_MODEL = "SPOT_MODEL";
    public static final String SPOT_ID = "SPOT_ID";
    public static final String VIDEO_URL = "VIDEO_URL";
    public static final String CURRENT_DURATION = "CURRENT_DURATION";
    public static final String FRAGMENT_STACK = "FRAGMENT_STACK";
    public static final String BUNDLE_STACK = "BUNDLE_STACK";
    public static final String IS_SHOW_ROUTING = "IS_SHOW_ROUTING";
    public static final String CHARACTER = "CHARACTER";
    public static final String LIST_PHONE = "LIST_PHONE";
    public static final String IS_HOSPITAL = "IS_HOSPITAL";
    public static final String IS_ASYLUM = "IS_ASYLUM";
  }

  public static class EMERGENCY {
    public static final String HOSPITAL = "hospital";
    public static final String ASYLUM = "asylum";
    public static final String POLICE = "police";
    public static final String FIRE = "fire";
    public static final String EMBASSY = "embassy";
  }

  public static class SETTING_CODE {
    public static final int LIST_LANGUAGE_CODE = 17;
    public static final int LIST_COUNTRY_CODE = 22;
  }

  public static class SPOT_CONFIG{
    public static final double DISTANCE_TO_UPDATE = 4f; //4km
    public static final double RADIUS_TO_UPDATE = 20f; //20km
  }

  public static class AR_CONFIG{
    public static final double DISTANCE_TO_UPDATE = 4f;//
  }

  public static class SPOT_CODE {
    public static final int MARKER_NORMAL = 16;
    public static final int MARKER_SPECIAL = 22;
  }

  public static class SPOT_TYPE {
    public static final int AR_SPOT = 13;
    public static final int NOT_AR_SPOT = 14;
  }

  public static class CONTACT_TYPE {
    public static final int POLICE_TYPE = 33;
    public static final int FIRE_TYPE = 44;
    public static final int EMBASSY_TYPE = 55;
  }

  public static class MY_LOCATION_TEMP {
    public static final double LAT_TEMP = 35.544853791272345;
    public static final double LNG_TEMP = 139.76720770385737;
  }
}
