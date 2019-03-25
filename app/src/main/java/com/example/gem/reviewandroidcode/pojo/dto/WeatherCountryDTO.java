package com.example.gem.reviewandroidcode.pojo.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherCountryDTO {
  @SerializedName("coord")
  public CoordDTO coordDTO;

  @SerializedName("weather")
  public List<WeatherDTO> weatherDTOS;

  @SerializedName("base")
  public String base;

  @SerializedName("main")
  public MainDTO mainDTO;

  @SerializedName("visibility")
  public long visibility;

  @SerializedName("wind")
  public WindDTO windDTO;

  @SerializedName("clouds")
  public CloudsDTO cloudsDTO;

  @SerializedName("dt")
  public long dt;

  @SerializedName("sys")
  public SysDTO sysDTO;

  @SerializedName("id")
  public long id;

  @SerializedName("name")
  public String name;

  @SerializedName("cod")
  public int cod;

  public WeatherCountryDTO() {
  }
}
