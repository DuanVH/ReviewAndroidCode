package com.example.gem.reviewandroidcode.pojo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.gem.reviewandroidcode.pojo.dto.WindDTO;

public class WindModel implements Parcelable {
  private double speed;
  private int deg;

  public WindModel() {
  }

  protected WindModel(Parcel in) {
    speed = in.readDouble();
    deg = in.readInt();
  }

  public static final Creator<WindModel> CREATOR = new Creator<WindModel>() {
    @Override
    public WindModel createFromParcel(Parcel in) {
      return new WindModel(in);
    }

    @Override
    public WindModel[] newArray(int size) {
      return new WindModel[size];
    }
  };

  public void convert(WindDTO dto) {
    speed = dto.speed;
    deg = dto.deg;
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

  public int getDeg() {
    return deg;
  }

  public void setDeg(int deg) {
    this.deg = deg;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeDouble(speed);
    parcel.writeInt(deg);
  }
}
