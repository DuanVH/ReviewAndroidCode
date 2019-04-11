package com.example.gem.reviewandroidcode.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by maboy on 11/4/19.
 */
public class NetworkUtil {
  public static NetworkType checkNetwork(Context context) {

    if (context == null) return NetworkType.DISCONNECT;

    ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo activeNetInfo = manager.getActiveNetworkInfo();
    if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
      return NetworkType.DISCONNECT;
    } else {
//      if (com.alticast.viettelottcommons.WindmillConfiguration.NETWORK_FAKE) {
//        return com.alticast.viettelottcommons.WindmillConfiguration.is3gMode ? NetworkType.MOBILE : NetworkType.WIFI;
//      }
      if (activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
        return NetworkType.MOBILE;
      } else {
        return NetworkType.WIFI;
      }
    }
  }

  public static enum NetworkType {
    DISCONNECT,
    WIFI,
    MOBILE
  }
}
