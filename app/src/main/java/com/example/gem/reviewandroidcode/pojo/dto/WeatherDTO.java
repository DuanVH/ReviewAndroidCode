package com.example.gem.reviewandroidcode.pojo.dto;

import com.google.gson.annotations.SerializedName;

public class WeatherDTO {
  @SerializedName("id")
  public long id;

  @SerializedName("main")
  public String main;

  @SerializedName("description")
  public String description;

  @SerializedName("icon")
  public String icon;

  public WeatherDTO() {
  }
}
