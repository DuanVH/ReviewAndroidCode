package com.example.gem.reviewandroidcode.webservice;

import com.example.gem.reviewandroidcode.BuildConfig;
import com.example.gem.reviewandroidcode.webservice.weather.WeatherService;

import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WebserviceBuilder {
  private static final int CONNECTION_TIME_OUT = 15000;
  private static final int READ_TIME_OUT = 15000;

  private static WebserviceBuilder mInstance;

  private WeatherService weatherService;

  public static WebserviceBuilder getInstance() {
    synchronized (WebserviceBuilder.class) {
      if (mInstance == null) mInstance = new WebserviceBuilder();
    }
    return mInstance;
  }

  private String getBaseUrl() {
    return BuildConfig.BASE_URL;
  }

  public void initServices() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    if (BuildConfig.DEBUG)
      interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    Dispatcher dispatcher = new Dispatcher();
    dispatcher.setMaxRequests(1);

    OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS)
        .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
        .addInterceptor(interceptor)
        .build();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(getBaseUrl())
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    // TODO
    weatherService = retrofit.create(WeatherService.class);
  }

  public WeatherService getWeatherService() {
    return weatherService;
  }
}
