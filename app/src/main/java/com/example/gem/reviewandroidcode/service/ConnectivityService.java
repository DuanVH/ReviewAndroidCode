package com.example.gem.reviewandroidcode.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.gem.reviewandroidcode.pojo.dto.BusEvent;

import org.greenrobot.eventbus.EventBus;


public class ConnectivityService extends Service {

  static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

  private Handler mHandler;

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    mHandler = new Handler();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    // Let it continue running until it is stopped.
    IntentFilter filter = new IntentFilter();
    filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    BroadcastReceiver receiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (CONNECTIVITY_CHANGE_ACTION.equals(action)) {
          //check internet connection
          if (!ConnectionHelper.isConnectedOrConnecting(context)) {
            if (context != null) {
              boolean show = true;
              if (ConnectionHelper.getInstance().getLastNoConnectionTs() == -1) {//first time
                show = true;
                ConnectionHelper.getInstance().setLastNoConnectionTs(System.currentTimeMillis());
              } else {
                if (System.currentTimeMillis() - ConnectionHelper.getInstance().getLastNoConnectionTs() > 1000) {
                  show = true;
                  ConnectionHelper.getInstance().setLastNoConnectionTs(System.currentTimeMillis());
                }
              }

              if (show && ConnectionHelper.getInstance().isOnline()) {
                ConnectionHelper.getInstance().setOnline(false);
                Log.i("NETWORK123", "Connection lost");
                mHandler.post(postOfflineMessageTask);
              }
            }
          } else {
            Log.i("NETWORK123", "Connected");
            ConnectionHelper.getInstance().setOnline(true);
            if (ConnectionHelper.getInstance().getLastNoConnectionTs() != -1)
              mHandler.post(postOfflineMessageTask);
            // Perform your actions here
          }
        }
      }
    };
    registerReceiver(receiver, filter);
    return START_STICKY;
  }

  private Runnable postOfflineMessageTask = () -> {
    ConnectionHelper.getInstance().setNetworkChangeNotified(false);
    EventBus.getDefault().post(new BusEvent(BusEvent.TYPE.CONNECTION_STATUS, ConnectionHelper.getInstance().isOnline()));
  };

  @Override
  public void onDestroy() {
    super.onDestroy();
    mHandler.removeCallbacks(postOfflineMessageTask);

  }
}
