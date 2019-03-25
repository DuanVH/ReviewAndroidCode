package com.example.gem.reviewandroidcode.pojo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.gem.reviewandroidcode.pojo.dto.MainDTO;

public class MainModel implements Parcelable {
  private double temp;
  private long pressure;
  private int humidity;
  private double tempMin;
  private double tempMax;

  public MainModel() {
  }

  protected MainModel(Parcel in) {
    temp = in.readDouble();
    pressure = in.readLong();
    humidity = in.readInt();
    tempMin = in.readDouble();
    tempMax = in.readDouble();
  }

  public static final Creator<MainModel> CREATOR = new Creator<MainModel>() {
    @Override
    public MainModel createFromParcel(Parcel in) {
      return new MainModel(in);
    }

    @Override
    public MainModel[] newArray(int size) {
      return new MainModel[size];
    }
  };

  public void convert(MainDTO dto) {
    temp = dto.temp;
    pressure = dto.pressure;
    tempMin = dto.tempMin;
    tempMax = dto.tempMax;
  }

  public double getTemp() {
    return temp;
  }

  public void setTemp(double temp) {
    this.temp = temp;
  }

  public long getPressure() {
    return pressure;
  }

  public void setPressure(long pressure) {
    this.pressure = pressure;
  }

  public int getHumidity() {
    return humidity;
  }

  public void setHumidity(int humidity) {
    this.humidity = humidity;
  }

  public double getTempMin() {
    return tempMin;
  }

  public void setTempMin(double tempMin) {
    this.tempMin = tempMin;
  }

  public double getTempMax() {
    return tempMax;
  }

  public void setTempMax(double tempMax) {
    this.tempMax = tempMax;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeDouble(temp);
    parcel.writeLong(pressure);
    parcel.writeInt(humidity);
    parcel.writeDouble(tempMin);
    parcel.writeDouble(tempMax);
  }
}
