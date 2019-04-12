package com.example.gem.reviewandroidcode.service;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.gem.reviewandroidcode.R;
import com.example.gem.reviewandroidcode.db.ArContentDatabase;
import com.example.gem.reviewandroidcode.pojo.dto.ArContentDTO;
import com.example.gem.reviewandroidcode.pojo.dto.BusEvent;
import com.example.gem.reviewandroidcode.utils.AppUtils;
import com.example.gem.reviewandroidcode.utils.CacheHelper;
import com.example.gem.reviewandroidcode.utils.JsonHelper;
import com.example.gem.reviewandroidcode.utils.Logger;
import com.example.gem.reviewandroidcode.webservice.ar.ArRepository;
import com.example.gem.reviewandroidcode.webservice.base.BaseResponse;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;


public class DownloadService extends IntentService {

  private final String TAG = "DownloadService";
  private final int DOWNLOAD_NOTIFICATION_ID = 10001;
  private final String FILE_PROTOCOL = "file://";
  private NotificationManagerCompat mNotificationManagerCompat;
  private NotificationCompat.Builder mNotificationBuilder;
  private int mPrevProgress, mCurrentProgress;
  public static boolean isDownloadingData;
  private Handler mHandler = new Handler();

  public DownloadService() {
    super("Download Service");
  }

  private long mTotalDownloadSize, mCurrentDownloadSize = 0;

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @SuppressLint("CheckResult")
  @Override
  protected void onHandleIntent(Intent intent) {
    double lat = CacheHelper.getInstance().getFloatValue(CacheHelper.LAT_SPOT_DETAIL);
    double lng = CacheHelper.getInstance().getFloatValue(CacheHelper.LNG_SPOT_DETAIL);
    isDownloadingData = true;
    createNotification();
    Log.i(TAG, "request arcontent api");
//    ArRepository.getArContentList(AppUtils.getInstance().getDeviceId(), lat, lng)
//            .subscribe(list -> {
//                      Log.i(TAG, "request arcontent api success");
//                      if (list.isEmpty()) {
//                        onDownloadCompleted();
//                        return;
//                      }
//                      mTotalDownloadSize = calculateTotalDownloadSize(list);
//                      for (ArContentDTO arContentDTO : list) {
//                        if (arContentDTO.isDownloadNeeded()) {
//                          //clear map to store a new ArContent object
//                          if (downloadContent(arContentDTO.getDownloadMap()))
//                            ArContentDatabase.insertOrUpdate(arContentDTO);
//                        }
//                      }
//                      ArContentDatabase.getArContents(this::writeArJsonFile);
//                    },
//                    throwable -> {
//                      Log.i(TAG, "request arcontent api failed");
//                      Logger.logException(throwable);
//                      isDownloadingData = false;
//                      onDownloadCompleted();
//                    });
  }

  private long calculateTotalDownloadSize(List<ArContentDTO> list) {
    long totalSize = 0;
    for (ArContentDTO arContentDTO : list) {
      totalSize += arContentDTO.getDownloadSize();
    }
    return totalSize;
  }

  private void writeArJsonFile(List<ArContentDTO> list) {
    Log.i(TAG, "write json file");
    File outFile = new File(getSavedFolderPath() + "ar.txt");
    if (outFile.exists()) outFile.delete();
    BaseResponse baseResponse = new BaseResponse();
    baseResponse.setStatus("SUCCESS");
    baseResponse.setMessage("");
    baseResponse.setData(list);
    String json = JsonHelper.getGson().toJson(baseResponse);
    try {
      outFile.createNewFile();
      try (OutputStream output = new FileOutputStream(outFile)) {
        output.write(json.getBytes());
        output.flush();
      }
    } catch (Exception e) {
      Logger.logException(e);
    }finally {
      onDownloadCompleted();
    }
  }

  private boolean downloadContent(Map<String, String> mFileMap) {
    createFolders();
    for (Map.Entry<String, String> entry : mFileMap.entrySet()) {
      String outputFileName = entry.getKey().replace(FILE_PROTOCOL, "");
      String url = entry.getValue();

      File outputFile = new File(outputFileName);
      Log.i(TAG, "outputField: " + outputFile.getAbsolutePath());
      if (!outputFile.exists()) {
        try {
          outputFile.createNewFile();
          processDownload(url, outputFile);
        } catch (IOException e) {
          e.printStackTrace();
          if (outputFile.exists())
            outputFile.delete();
//          onDownloadCompleted();
          return false;
        }
      } else {
        mCurrentDownloadSize += outputFile.length();
      }
    }
    return true;
  }

  private void processDownload(String link, File outputFile) throws IOException {
    Log.i(TAG, "download file: " + link);
      int count;
      byte buffer[] = new byte[1024 * 4];
      URL url = new URL(link);
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setRequestMethod("GET");
      urlConnection.setConnectTimeout(15000);
      urlConnection.setReadTimeout(15000);
      urlConnection.connect();

      InputStream bis = urlConnection.getInputStream();

      try (OutputStream output = new FileOutputStream(outputFile)) {
        while ((count = bis.read(buffer)) != -1) {
          mCurrentDownloadSize += count;
          mCurrentProgress = (int) ((mCurrentDownloadSize * 100) / mTotalDownloadSize);
          if (mCurrentProgress != mPrevProgress) {
            mPrevProgress = mCurrentProgress;
            EventBus.getDefault().post(new BusEvent(BusEvent.TYPE.DOWNLOAD_AR, mCurrentProgress));
            mNotificationBuilder.setContentText(mCurrentProgress + "%");
            mNotificationBuilder.setProgress(100, mCurrentProgress, false);
            mNotificationManagerCompat.notify(DOWNLOAD_NOTIFICATION_ID, mNotificationBuilder.build());
          }
          output.write(buffer, 0, count);
        }
        output.flush();
      }
      bis.close();
  }

  private void onDownloadCompleted() {
    mNotificationBuilder.setContentTitle("Update complete");
    mNotificationBuilder.setContentText("100%");
    mNotificationBuilder.setOngoing(false);
    mNotificationBuilder.setAutoCancel(true);
    mNotificationBuilder.setProgress(100, 100, false);
    mNotificationManagerCompat.notify(DOWNLOAD_NOTIFICATION_ID, mNotificationBuilder.build());
    mHandler.postDelayed(() -> mNotificationManagerCompat.cancel(DOWNLOAD_NOTIFICATION_ID), 2000);

    EventBus.getDefault().post(new BusEvent(BusEvent.TYPE.DOWNLOAD_AR, 100));
    isDownloadingData = false;
    EventBus.getDefault().post(new BusEvent(BusEvent.TYPE.DOWNLOAD_AR, true));
  }

  private void createNotification() {
    mNotificationManagerCompat = NotificationManagerCompat.from(this);

    String contentTitle = "Updating data";
    Intent notifyIntent = new Intent();
    PendingIntent notifyPendingIntent = PendingIntent.getActivity(this,
            DOWNLOAD_NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    mNotificationBuilder = createNotificationBuilder("download_channel");
    mNotificationBuilder.setContentIntent(notifyPendingIntent);
    mNotificationBuilder.setTicker("Start downloading from the server");
    mNotificationBuilder.setOngoing(true);
    mNotificationBuilder.setAutoCancel(false);
    mNotificationBuilder.setSmallIcon(android.R.drawable.stat_sys_download);
    mNotificationBuilder.setContentTitle(contentTitle);
    mNotificationBuilder.setContentText("");
    mNotificationBuilder.setProgress(100, 0, true);
    mNotificationManagerCompat.notify(DOWNLOAD_NOTIFICATION_ID, mNotificationBuilder.build());
  }

  private NotificationCompat.Builder createNotificationBuilder(String channelId) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      String channelName = getString(R.string.app_name);
      NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName,
              NotificationManager.IMPORTANCE_LOW);
      notificationChannel.setLightColor(Color.CYAN);
      notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
      NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
      if (notificationManager != null) {
        notificationManager.createNotificationChannel(notificationChannel);
      }
    }
    return new NotificationCompat.Builder(this, channelId);
  }

  @Override
  public void onTaskRemoved(Intent rootIntent) {
    Log.i(TAG, "onTaskRemoved");
  }

  private void createFolders() {
    File fileFolder = new File(Environment.getExternalStorageDirectory() + "/Android/data/"
            + getApplicationContext().getPackageName() + "/files/");
    if (!fileFolder.exists())
      fileFolder.mkdir();

    File file = new File(getSavedFolderPath());
    if (!file.exists()) {
      file.mkdir();
    }
  }

  private String getSavedFolderPath() {
    return Environment.getExternalStorageDirectory() + "/Android/data/"
            + getApplicationContext().getPackageName() + "/files/ar/";
  }
}