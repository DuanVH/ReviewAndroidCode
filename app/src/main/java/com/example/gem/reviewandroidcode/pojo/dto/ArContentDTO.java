package com.example.gem.reviewandroidcode.pojo.dto;

import android.os.Environment;

import com.example.gem.reviewandroidcode.db.ArContentDatabase;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;
import java.util.Map;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ArContentDTO extends RealmObject {
  @PrimaryKey
  @SerializedName("id")
  public int id;
  @SerializedName("name")
  public String name;
  @SerializedName("link")
  public String link;
  @SerializedName("size")
  public long size;
  @SerializedName("width")
  public int width;
  @SerializedName("height")
  public int height;
  @SerializedName("x")
  public int x;
  @SerializedName("y")
  public int y;
  @SerializedName("layers")
  public RealmList<ArLayerDTO> arLayerDTOs;
  @SerializedName("link_kar")
  public String linkKar;
  @SerializedName("name_kar")
  public String nameKar;
  @SerializedName("size_kar")
  public long sizeKar;
  @SerializedName("modified")
  public long modified;
  @SerializedName("start_time")
  public long startTime;
  @SerializedName("end_time")
  public long endTime;
  //store the timestamp when the ar content is scanned
  public long detectedDate;

  public boolean isDownloadNeeded() {
    return ArContentDatabase.checkIfDownloadNeeded(id, modified);
  }

  public long getDownloadSize() {
    if (isDownloadNeeded())
      return sizeKar + getDownloadableArItemsSize();
    return 0;
  }

  public long getDownloadableArItemsSize() {
    long size = 0;
    for (ArLayerDTO arLayerDTO : arLayerDTOs) {
      for (ArItemDTO arItemDTO : arLayerDTO.arItemDTOs)
        if (arItemDTO.isDownloadable()) size += arItemDTO.size;
    }
    return size;
  }

  public Map<String, String> getDownloadMap() {
    Map<String, String> mFileMaps = new LinkedHashMap<>();
    mFileMaps.clear();

    //Download file
//    String url = this.link;
//    String fileName = getSavedFolderPath() + getFileNameFromLink(this.link);
//    this.link = fileName; //convert to local path
//    mFileMaps.put(this.link, url);

    //Download KARMarker file
    String linkKar = this.linkKar;
    this.linkKar = getSavedFolderPath() + this.nameKar + ".KARMarker";
    mFileMaps.put(this.linkKar, linkKar);

    //Download item's image
    for (ArLayerDTO layerDTO : this.arLayerDTOs) {
      for (ArItemDTO item : layerDTO.arItemDTOs) {
        if (item.isDownloadable()) {
          String itemLink = item.link;

          if (itemLink != null && itemLink.contains("54.64.9.141"))
            itemLink = itemLink.replace("54.64.9.141", "app.pamlo.net");
//          if (itemLink != null && itemLink.contains("http://"))
//            itemLink = itemLink.replace("http://", "https://");

          String itemFileName = (item.isGif() ? "" : getFileProtocol()) + getSavedFolderPath() + getFileNameFromLink(itemLink);
          item.link = itemFileName; // convert to local file name
          mFileMaps.put(item.link, itemLink);
        }
      }
    }
    return mFileMaps;
  }

  private String getFileProtocol() {
    return "file://";
  }

  private String getSavedFolderPath() {
    return Environment.getExternalStorageDirectory() + "/Android/data/net.pamz.pamlo/files/ar/";
  }

  private String getFileNameFromLink(String link) {
    String[] parts = link.split("/");
    return parts[parts.length - 1];
  }
}
