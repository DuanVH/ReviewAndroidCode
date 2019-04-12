package com.example.gem.reviewandroidcode.pojo.dto;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ArLayerDTO  extends RealmObject {
  @PrimaryKey
  public String id;
  @SerializedName("index")
  public int index;
  @SerializedName("items")
  public RealmList<ArItemDTO> arItemDTOs;
}
