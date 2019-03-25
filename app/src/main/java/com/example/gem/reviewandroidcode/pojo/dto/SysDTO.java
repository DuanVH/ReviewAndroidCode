package com.example.gem.reviewandroidcode.pojo.dto;

import com.google.gson.annotations.SerializedName;

public class SysDTO {
  @SerializedName("type")
  public int type;

  @SerializedName("id")
  public long id;

  @SerializedName("message")
  public float message;

  @SerializedName("country")
  public String country;

  @SerializedName("sunrise")
  public long sunrise;

  @SerializedName("sunset")
  public long sunset;

  public SysDTO() {
  }
}
