package com.example.gem.reviewandroidcode.webservice.ar;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.gem.reviewandroidcode.db.ArContentDatabase;
import com.example.gem.reviewandroidcode.pojo.dto.ArContentDTO;
import com.example.gem.reviewandroidcode.pojo.dto.ArItemDTO;
import com.example.gem.reviewandroidcode.pojo.dto.ArLayerDTO;
import com.example.gem.reviewandroidcode.webservice.WebserviceBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("CheckResult")
public class ArRepository {
  public static final String TAG = "ArRepository";

  public static final String TYPE_SPOT = "spot";
  public static final String TYPE_AR_CONTENT = "ar_content";

//  public static Single<List<ArContentDTO>> getArContentList(String ime, double lat, double lng) {
//    return WebserviceBuilder.getInstance().getArService().getArContentList(ime, lat, lng)
//            .flatMap(baseResponse -> {
//              String listDelelte = baseResponse.getListDelete();
//              List<Integer> deletedIds = new ArrayList<>();
//              if (listDelelte != null && !listDelelte.isEmpty()) {
//                String[] list = listDelelte.split(",");
//                for (int i = 0; i < list.length; i++)
//                  deletedIds.add(Integer.parseInt(list[i]));
//              }
//
//              String listExpired = baseResponse.getListExpired();
//              if (listExpired != null && !listExpired.isEmpty()) {
//                String[] list = listExpired.split(",");
//                for (int i = 0; i < list.length; i++)
//                  deletedIds.add(Integer.parseInt(list[i]));
//              }
//
//              if (!deletedIds.isEmpty())
//                ArContentDatabase.removeDeletedArContent(deletedIds.toArray(new Integer[deletedIds.size()]));
//
//              //Generate Ids for layer items and their children items
//              List<ArContentDTO> list = baseResponse.getData();
//              if (list != null)
//                for (ArContentDTO arContentDTO : list) {
//                  if (arContentDTO.arLayerDTOs != null)
//                    for (ArLayerDTO arLayerDTO : arContentDTO.arLayerDTOs) {
//                      arLayerDTO.id = arContentDTO.id + "-" + arLayerDTO.index;
//                      for (ArItemDTO arItemDTO : arLayerDTO.arItemDTOs)
//                        arItemDTO.mId = arContentDTO.id + "-" + arLayerDTO.index + "-" + arItemDTO.itemId + "-"
//                                + arLayerDTO.arItemDTOs.indexOf(arItemDTO);
//                    }
//                }
//              return Single.just(list);
//            });
//  }
//
//  public static void postStatistics(String type, int id) {
//    StatisticsModel statisticsModel = new StatisticsModel(AppUtils.getInstance().getCurrentTimeInString(),
//            id, type, AppUtils.getInstance().getDeviceId());
//    List<StatisticsModel> list = new ArrayList<>();
//    list.add(statisticsModel);
//    postStatistics(type, list, false);
//  }
//
//  public static void postStatistics(String type, List<StatisticsModel> list, boolean isOfflineData) {
//    WebServiceBuilder.getInstance().getArService().postStatistics(type, JsonHelper.getGson().toJson(list))
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(baseResponse -> {
//              if (isOfflineData) StatisticsDatabase.updateStatistics(list);
//            }, throwable -> {
//              if (!isOfflineData) StatisticsDatabase.insertStatistics(list);
//            });
//  }
//
//  public static void postPendingStats() {
//    Log.i(TAG, "post pending stats");
//    StatisticsDatabase.getPendingStats(TYPE_SPOT, data -> {
//      if (!data.isEmpty())
//        postStatistics(TYPE_SPOT, data, true);
//    });
//
//    StatisticsDatabase.getPendingStats(TYPE_AR_CONTENT, data -> {
//      if (!data.isEmpty())
//        postStatistics(TYPE_AR_CONTENT, data, true);
//    });
//  }
}