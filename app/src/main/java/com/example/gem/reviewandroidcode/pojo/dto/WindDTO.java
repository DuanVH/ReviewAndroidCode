package com.example.gem.reviewandroidcode.pojo.dto;

import com.google.gson.annotations.SerializedName;

public class WindDTO {
  @SerializedName("speed")
  public double speed;

  @SerializedName("deg")
  public int deg;

  public WindDTO() {
  }
}
