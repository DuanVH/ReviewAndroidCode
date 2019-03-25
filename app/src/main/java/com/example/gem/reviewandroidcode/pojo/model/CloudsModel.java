package com.example.gem.reviewandroidcode.pojo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.gem.reviewandroidcode.pojo.dto.CloudsDTO;

public class CloudsModel implements Parcelable {

  private int all;

  public CloudsModel() {
  }

  public void convert(CloudsDTO dto) {
    all = dto.all;
  }

  protected CloudsModel(Parcel in) {
    all = in.readInt();
  }

  public static final Creator<CloudsModel> CREATOR = new Creator<CloudsModel>() {
    @Override
    public CloudsModel createFromParcel(Parcel in) {
      return new CloudsModel(in);
    }

    @Override
    public CloudsModel[] newArray(int size) {
      return new CloudsModel[size];
    }
  };

  public int getAll() {
    return all;
  }

  public void setAll(int all) {
    this.all = all;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(all);
  }
}
