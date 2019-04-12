package com.example.gem.reviewandroidcode.webservice.base;

import com.google.gson.annotations.SerializedName;

public class RemoteError {

  @SerializedName("status")
  int status;

  @SerializedName("code")
  String errorCode;

  @SerializedName("message")
  String message;

  @SerializedName("developerMessage")
  String developerMessage;

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getDeveloperMessage() {
    return developerMessage;
  }

  public void setDeveloperMessage(String developerMessage) {
    this.developerMessage = developerMessage;
  }
}
