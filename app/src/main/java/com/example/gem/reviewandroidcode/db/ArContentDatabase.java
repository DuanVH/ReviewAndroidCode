package com.example.gem.reviewandroidcode.db;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ArContentDatabase {
//  public static final int EXPIRED_PERIOD = BuildConfig.AR_EXPIRED_PERIOD * 86400; //7 days expired duration
//
//  public static void insertOrUpdate(final ArContentDTO arContentDTO) {
//    Realm realm = Realm.getInstance(RealmManager.getInstance().getRealmConfiguration());
//    realm.executeTransaction(
//            realm1 -> realm1.copyToRealmOrUpdate(arContentDTO));
//  }
//
//  public static int getArContentSize(){
//    Realm realm = Realm.getInstance(RealmManager.getInstance().getRealmConfiguration());
//    try {
//      realm.beginTransaction();
//      RealmResults<ArContentDTO> results = realm.where(ArContentDTO.class).findAll();
//      realm.commitTransaction();
//      return results.size();
//    } catch (Exception e) {
//      e.printStackTrace();
//      return 0;
//    }
//  }
//
//  public static void getArContents(RealmCallback<List<ArContentDTO>> callback) {
//    Realm realm = Realm.getInstance(RealmManager.getInstance().getRealmConfiguration());
//    try {
//      realm.executeTransaction(realm1 -> {
//        RealmResults<ArContentDTO> arContents = realm1.where(ArContentDTO.class).findAll();
//        callback.onResult(realm.copyFromRealm(arContents));
//      });
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
//
//  public static void updateDetectedItem(int id) {
//    Realm realm = Realm.getInstance(RealmManager.getInstance().getRealmConfiguration());
//    realm.executeTransactionAsync(realm1 -> {
//      ArContentDTO arContentDTO = realm1.where(ArContentDTO.class).equalTo("id", id).findFirst();
//      if (arContentDTO != null) {
//        arContentDTO.detectedDate = System.currentTimeMillis() / 1000;
//        realm1.copyToRealmOrUpdate(arContentDTO);
//      }
//    });
//  }
//
//  public static boolean checkIfDownloadNeeded(int id, long modified) {
//    Realm realm = Realm.getInstance(RealmManager.getInstance().getRealmConfiguration());
//    try {
//      realm.beginTransaction();
//      ArContentDTO arContent = realm.where(ArContentDTO.class).equalTo("id", id).findFirst();
//      realm.commitTransaction();
//      return arContent == null || modified > arContent.modified;
//    } catch (Exception e) {
//      e.printStackTrace();
//      return false;
//    }
//  }
//
//  public static void scanArContent(ScannerCallback callback) {
//    Realm realm = Realm.getInstance(RealmManager.getInstance().getRealmConfiguration());
//    try {
//      realm.executeTransaction(realm1 -> {
//        long currentTime = System.currentTimeMillis() / 1000;
//        Log.i("AR_CONTENT_DATABASE", (currentTime - EXPIRED_PERIOD + ""));
//        RealmResults<ArContentDTO> arContents = realm1.where(ArContentDTO.class)
//                .lessThanOrEqualTo("modified", currentTime - EXPIRED_PERIOD).findAll();
//        if (arContents != null && !arContents.isEmpty()) {
//          Log.i("AR_CONTENT_DATABASE", "Size delete: " + arContents.size());
//          callback.scanComplete(arContents.size());
//          arContents.deleteAllFromRealm();
//        } else
//          callback.scanComplete(0);
//      });
//    } catch (Exception e) {
//      e.printStackTrace();
//      callback.scanComplete(0);
//    }
//  }
//
//  public static void removeDeletedArContent(Integer[] listDelete){
//    Realm realm = Realm.getInstance(RealmManager.getInstance().getRealmConfiguration());
//    try {
//      realm.executeTransactionAsync(realm1 -> {
//        RealmResults<ArContentDTO> arContents = realm1.where(ArContentDTO.class)
//                .in("id", listDelete)
//                .findAll();
//        if (arContents != null && !arContents.isEmpty()) {
//          Log.i("DELETED_AR_CONTENT", "Size delete: " + arContents.size());
//          arContents.deleteAllFromRealm();
//        }
//      });
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
//
//  public interface ScannerCallback {
//    void scanComplete(int removedItems);
//  }
}