package com.example.gem.reviewandroidcode.pojo.dto;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ArItemDTO extends RealmObject {
  @PrimaryKey
  public String mId;
  @SerializedName("id")
  public int itemId;
  @SerializedName("object_type")
  public String objectType;
  @SerializedName("type")
  public String type;
  @SerializedName("name")
  public String name;
  @SerializedName("width")
  public int width;
  @SerializedName("height")
  public int height;
  @SerializedName("x")
  public int x;
  @SerializedName("y")
  public int y;
  @SerializedName("link")
  public String link;
  @SerializedName("size")
  public long size;
  @SerializedName("modified")
  public long modified;
  @SerializedName("bundleAndroid")
  public String bundleAndroid;
  @SerializedName("bundleIos")
  public String bundleIos;

  public boolean isDownloadable() {
    return objectType.equals("spot") || type.equals("image") || type.equals("gif");
  }

  public boolean isGif(){
    return type.equals("gif");
  }
}
