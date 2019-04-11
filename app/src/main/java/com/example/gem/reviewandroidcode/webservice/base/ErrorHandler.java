package com.example.gem.reviewandroidcode.webservice.base;

import android.content.Context;
import android.os.Build;
import android.system.ErrnoException;

import com.example.gem.reviewandroidcode.lang.LangKey;
import com.example.gem.reviewandroidcode.lang.Languages;
import com.example.gem.reviewandroidcode.utils.JsonHelper;
import com.example.gem.reviewandroidcode.utils.Logger;
import com.example.gem.reviewandroidcode.utils.NetworkUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

public class ErrorHandler {
  private static final int DEFAULT_CODE = -1;
  private static String DEFAULT_MESSAGE = "";
  private static final String STATUS_TAG = "status";
  private static final String MESSAGE_TAG = "message";
  private static final String ERROR = "error";
  public static String OFFLINE_MSG = "offline";

  private Context context;

  private static ErrorHandler sInstance;

  private int mCode = DEFAULT_CODE;
  private String mMessage = DEFAULT_MESSAGE;

  public ErrorHandler() {

  }

  public static ErrorHandler getInstance() {
    if (sInstance == null) {
      synchronized (ErrorHandler.class) {
        if (sInstance == null)
          sInstance = new ErrorHandler();
      }
    }
    return sInstance;
  }

  public void init(Context context) {
    this.context = context;
    DEFAULT_MESSAGE = Languages.getString(context, LangKey.kLocalizeKeyErrorTryAgain);
    OFFLINE_MSG = Languages.getString(context, LangKey.kLocalizeKeyOffline);
  }

  public int getCode() {
    return mCode;
  }

  public String getMessage() {
    return mMessage;
  }

  public void handleError(Throwable throwable, ErrorHandlerCallback callback) {
    mCode = -1;
    if (NetworkUtil.checkNetwork(context) == NetworkUtil.NetworkType.DISCONNECT) {
      mMessage = Languages.getString(context, LangKey.kLocalizeKeyOffline);
      if (callback != null) callback.onNetworkFailed();
    } else {
      if (throwable instanceof SocketTimeoutException ||
          throwable instanceof ConnectException ||
          throwable instanceof UnknownHostException || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
          throwable instanceof ErrnoException)) {
        mMessage = OFFLINE_MSG;
        if (callback != null) callback.onNetworkFailed();
      } else {
        if (throwable instanceof HttpException) {
          HttpException httpException = (HttpException) throwable;
          try {
            String errorResponse = httpException.response().errorBody().string();
            if (!JsonHelper.isJson(errorResponse)) {
              mMessage = DEFAULT_MESSAGE;
            } else {
              JsonObject errorObject = new JsonParser().parse(errorResponse).getAsJsonObject();
              if (errorObject.has(MESSAGE_TAG)) {
                mMessage = errorObject.get(MESSAGE_TAG).getAsString();
              } else {
                mMessage = DEFAULT_MESSAGE;
              }
            }
          } catch (IOException e) {
            Logger.logException(e);
            mMessage = DEFAULT_MESSAGE;
          } finally {
            if (callback != null) callback.onErrorResponse(mMessage);
          }
        } else {
          mMessage = throwable.getMessage();
        }
        if (callback != null) callback.onErrorResponse(mMessage);
      }
    }
  }

  public interface ErrorHandlerCallback {
    void onNetworkFailed();

    void onErrorResponse(String error);
  }
}
