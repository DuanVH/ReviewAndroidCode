package com.example.gem.reviewandroidcode.pojo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.gem.reviewandroidcode.pojo.dto.SysDTO;

public class SysModel implements Parcelable {
  private int type;
  private long id;
  private float message;
  private String country;
  private long sunrise;
  private long sunset;

  public SysModel() {
  }

  protected SysModel(Parcel in) {
    type = in.readInt();
    id = in.readLong();
    message = in.readFloat();
    country = in.readString();
    sunrise = in.readLong();
    sunset = in.readLong();
  }

  public static final Creator<SysModel> CREATOR = new Creator<SysModel>() {
    @Override
    public SysModel createFromParcel(Parcel in) {
      return new SysModel(in);
    }

    @Override
    public SysModel[] newArray(int size) {
      return new SysModel[size];
    }
  };

  public void convert(SysDTO dto) {
    type = dto.type;
    id = dto.id;
    message = dto.message;
    country = dto.country;
    sunrise = dto.sunrise;
    sunset = dto.sunset;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public float getMessage() {
    return message;
  }

  public void setMessage(float message) {
    this.message = message;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public long getSunrise() {
    return sunrise;
  }

  public void setSunrise(long sunrise) {
    this.sunrise = sunrise;
  }

  public long getSunset() {
    return sunset;
  }

  public void setSunset(long sunset) {
    this.sunset = sunset;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(type);
    parcel.writeLong(id);
    parcel.writeFloat(message);
    parcel.writeString(country);
    parcel.writeLong(sunrise);
    parcel.writeLong(sunset);
  }
}
