package com.example.gem.reviewandroidcode.pojo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.gem.reviewandroidcode.pojo.dto.CoordDTO;

public class CoordModel implements Parcelable {

  private double lon;
  private double lat;

  public CoordModel() {
  }

  protected CoordModel(Parcel in) {
    lon = in.readDouble();
    lat = in.readDouble();
  }

  public static final Creator<CoordModel> CREATOR = new Creator<CoordModel>() {
    @Override
    public CoordModel createFromParcel(Parcel in) {
      return new CoordModel(in);
    }

    @Override
    public CoordModel[] newArray(int size) {
      return new CoordModel[size];
    }
  };

  public void convert(CoordDTO dto) {
    lon = dto.lon;
    lat = dto.lat;
  }

  public double getLon() {
    return lon;
  }

  public void setLon(double lon) {
    this.lon = lon;
  }

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeDouble(lon);
    parcel.writeDouble(lat);
  }
}
