package com.example.gem.reviewandroidcode.webservice.base;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {
  @SerializedName("status")
  private String status;

  @SerializedName("message")
  private String message;

  @SerializedName("data")
  private T mData;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return mData;
  }

  public void setData(T mData) {
    this.mData = mData;
  }
}
