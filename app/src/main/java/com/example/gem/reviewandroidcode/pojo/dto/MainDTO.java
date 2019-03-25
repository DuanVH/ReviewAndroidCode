package com.example.gem.reviewandroidcode.pojo.dto;

import com.google.gson.annotations.SerializedName;

public class MainDTO {

  @SerializedName("temp")
  public double temp;

  @SerializedName("pressure")
  public long pressure;

  @SerializedName("humidity")
  public int humidity;

  @SerializedName("temp_min")
  public double tempMin;

  @SerializedName("temp_max")
  public double tempMax;

  public MainDTO() {
  }
}
