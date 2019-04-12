package com.example.gem.reviewandroidcode.db;

import android.content.Context;
import android.os.Environment;

import com.example.gem.reviewandroidcode.utils.Logger;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by maboy on 11/10/2017.
 */

public class RealmManager {
  private static RealmManager sInstance;
  private Realm mRealm;
  private RealmConfiguration realmConfiguration;
  private Context mContext;

  private RealmManager() {
  }

  public static RealmManager getInstance() {
    if (sInstance == null) {
      synchronized (RealmManager.class) {
        if (sInstance == null) {
          sInstance = new RealmManager();
        }
      }
    }
    return sInstance;
  }

  public void init(Context context) {
    this.mContext = context;
    Realm.init(context);
    realmConfiguration = new RealmConfiguration.Builder()
            .name(Realm.DEFAULT_REALM_NAME)
            .schemaVersion(0)
            .deleteRealmIfMigrationNeeded()
            .build();
    Realm.setDefaultConfiguration(realmConfiguration);
    mRealm = Realm.getDefaultInstance();
  }

  public RealmConfiguration getRealmConfiguration() {
    return realmConfiguration;
  }

  public Realm getDefaultRealm() {
    if (mRealm == null || mRealm.isClosed())
      mRealm = Realm.getDefaultInstance();
    if (mRealm.isInTransaction()) mRealm.cancelTransaction();
    return mRealm;
  }

  public void deleteArContent(){
    Realm realm = getDefaultRealm();
    realm.beginTransaction();
//    realm.where(ArContentDTO.class).findAll().deleteAllFromRealm();
    realm.commitTransaction();
    try {
      File file = new File(getSavedFolderPath());
      if (file.exists()) {
        deleteArFolder(file);
      }
    } catch (Exception e) {
      Logger.logException(e);
    }
  }

  public void deleteAll() {
    //Delete database content but keep the schema
    Realm realm = getDefaultRealm();
    realm.beginTransaction();
    realm.deleteAll();
    realm.commitTransaction();
    //Delete ArContent
    try {
      File file = new File(getSavedFolderPath());
      if (file.exists()) {
        deleteArFolder(file);
      }
    } catch (Exception e) {
      Logger.logException(e);
    }
  }

  private void deleteArFolder(File fileOrDirectory) {
    if (fileOrDirectory.isDirectory())
      for (File child : fileOrDirectory.listFiles())
      deleteArFolder(child);
    fileOrDirectory.delete();
  }

  private String getSavedFolderPath() {
    return Environment.getExternalStorageDirectory() + "/Android/data/"
            + mContext.getPackageName() + "/files/ar/";
  }
}
