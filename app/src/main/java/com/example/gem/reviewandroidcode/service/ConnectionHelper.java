package com.example.gem.reviewandroidcode.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionHelper {

  private static ConnectionHelper instance;

  public static ConnectionHelper getInstance() {
    if (instance == null)
      instance = new ConnectionHelper();
    return instance;
  }

  private long lastNoConnectionTs = -1;
  private boolean isOnline = true;
  private boolean networkChangeNotified = true;

  public static boolean isConnected(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

    return activeNetwork != null && activeNetwork.isConnected();
  }

  public static boolean isConnectedOrConnecting(Context context) {
    ConnectivityManager cm = (ConnectivityManager)
            context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    return activeNetwork != null &&
            activeNetwork.isConnectedOrConnecting();
  }

  public boolean isNotified() {
    boolean notified = networkChangeNotified;
    if (!networkChangeNotified) networkChangeNotified = true;
    return notified;
  }

  public long getLastNoConnectionTs() {
    return lastNoConnectionTs;
  }

  public void setLastNoConnectionTs(long lastNoConnectionTs) {
    this.lastNoConnectionTs = lastNoConnectionTs;
  }

  public boolean isOnline() {
    return isOnline;
  }

  public void setOnline(boolean online) {
    isOnline = online;
  }

  public boolean isNetworkChangeNotified() {
    return networkChangeNotified;
  }

  public void setNetworkChangeNotified(boolean networkChangeNotified) {
    this.networkChangeNotified = networkChangeNotified;
  }
}
