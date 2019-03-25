package com.example.gem.reviewandroidcode.pojo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.gem.reviewandroidcode.pojo.dto.WeatherDTO;

public class WeatherModel implements Parcelable {

  private long id;
  private String main;
  private String description;
  private String icon;

  public WeatherModel() {
  }

  protected WeatherModel(Parcel in) {
    id = in.readLong();
    main = in.readString();
    description = in.readString();
    icon = in.readString();
  }

  public static final Creator<WeatherModel> CREATOR = new Creator<WeatherModel>() {
    @Override
    public WeatherModel createFromParcel(Parcel in) {
      return new WeatherModel(in);
    }

    @Override
    public WeatherModel[] newArray(int size) {
      return new WeatherModel[size];
    }
  };

  public void convert(WeatherDTO dto) {
    id = dto.id;
    main = dto.main;
    description = dto.description;
    icon = dto.icon;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getMain() {
    return main;
  }

  public void setMain(String main) {
    this.main = main;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeLong(id);
    parcel.writeString(main);
    parcel.writeString(description);
    parcel.writeString(icon);
  }
}
