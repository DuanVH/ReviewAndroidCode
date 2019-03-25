package com.example.gem.reviewandroidcode.pojo.dto;

import com.google.gson.annotations.SerializedName;

public class CoordDTO {

  @SerializedName("lon")
  public double lon;

  @SerializedName("lat")
  public double lat;

  public CoordDTO() {
  }
}
