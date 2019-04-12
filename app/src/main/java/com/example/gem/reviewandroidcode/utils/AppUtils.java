package com.example.gem.reviewandroidcode.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by gem on 11/1/18.
 */

public class AppUtils {

  private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
  public static AppUtils mInstance;
  private Context mContext;
  private SimpleDateFormat sdfFull = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

  public static AppUtils getInstance() {
    if (mInstance == null) mInstance = new AppUtils();
    return mInstance;
  }

  public void init(Context context) {
    this.mContext = context;
  }

  public void setLanguageForMap(String codeLanguage) {
    String languageToLoad = codeLanguage;
    Locale locale = new Locale(languageToLoad);
    Locale.setDefault(locale);
    Configuration config = new Configuration();
    config.locale = locale;
    mContext.getResources().updateConfiguration(config, mContext.getResources().getDisplayMetrics());
  }

  public String getDeviceId() {
    return Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
  }

  public static boolean isLocationEnabled(Context context) {
    int locationMode = 0;
    String locationProviders;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      try {
        locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

      } catch (Settings.SettingNotFoundException e) {
        e.printStackTrace();
        return false;
      }
      return locationMode != Settings.Secure.LOCATION_MODE_OFF;
    } else {
      locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
      return !TextUtils.isEmpty(locationProviders);
    }
  }

  public boolean checkPermission(String permission) {
    return ContextCompat.checkSelfPermission(mContext, permission)
            == PackageManager.PERMISSION_GRANTED;
  }

  public static boolean isLocationServiceEnabled(Context context) {
    LocationManager locationManager = null;
    boolean gps_enabled = false, network_enabled = false;

    if (locationManager == null)
      locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    try {
      gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    } catch (Exception ex) {
      //do nothing...
    }

    try {
      network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    } catch (Exception ex) {
      //do nothing...
    }
    return gps_enabled || network_enabled;
  }

//  public static boolean checkPlayServices(Activity activity) {
//    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
//    if (resultCode != ConnectionResult.SUCCESS) {
//      if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
//        GooglePlayServicesUtil.getErrorDialog(resultCode, activity, PLAY_SERVICES_RESOLUTION_REQUEST).show();
//      } else {
//        Toast.makeText(activity,
//                "This device is not supported by Google Play Service.", Toast.LENGTH_SHORT)
//                .show();
//        activity.finish();
//      }
//      return false;
//    }
//    return true;
//  }

  public static boolean checkLastActivity(Context context, String className) {
    ActivityManager mngr = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);

    List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);

    if (taskList.get(0).numActivities == 1 &&
            taskList.get(0).topActivity.getClassName().equals(className)) {
      return true;
    }
    return false;
  }

  public boolean isFlashLightAvailable() {
    return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
  }

  public boolean checkCameraFront() {
    if (mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT))
      return true;
    return false;
  }

  public static Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
    double minDiff = Double.MAX_VALUE;
    double surfaceRatio = (double) w / h;
    // Cannot find the one match the aspect ratio, ignore the requirement
    Camera.Size bestSize = null;
    for (Camera.Size size : sizes) {
      Log.i("PREVIEW_SIZE", size.width + "-" + size.height);
      double diff = Math.abs((double) size.width / size.height - surfaceRatio);
      if (diff < minDiff) {
        minDiff = diff;
        bestSize = size;
      }
    }
    return bestSize;
  }

  public static double calculateDistance(double lat1, double lon1, double lat2,
                                         double lon2) {
    final int R = 6371; // Radius of the earth

    double latDistance = Math.toRadians(lat2 - lat1);
    double lonDistance = Math.toRadians(lon2 - lon1);
    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double distance = R * c; // result in kilometer

    double height = 0;

    distance = Math.pow(distance, 2) + Math.pow(height, 2);
    return Math.sqrt(distance);
  }

  public String getCurrentTimeInString(){
    return sdfFull.format(new Date());
  }
}
