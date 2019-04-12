package com.example.gem.reviewandroidcode.webservice.base;

import com.google.gson.annotations.SerializedName;

public class BaseArContentResponse<T> {
  @SerializedName("status")
  private String status;
  @SerializedName("message")
  private String message;
  @SerializedName("data")
  private T mData;
  @SerializedName("list_deleted")
  private String listDelete;
  @SerializedName("list_expired")
  private String listExpired;

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public T getData() {
    return mData;
  }

  public void setData(T mData) {
    this.mData = mData;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public String getListDelete() {
    return listDelete;
  }

  public String getListExpired() {
    return listExpired;
  }
}