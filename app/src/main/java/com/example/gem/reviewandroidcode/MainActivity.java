package com.example.gem.reviewandroidcode;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaActionSound;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Printer;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getName();

  @BindView(R.id.ivMenu)
  ImageView mMenuIv;
  @BindView(R.id.ivBack)
  ImageView mBackIv;
  @BindView(R.id.ivPlay)
  ImageView mPlayIv;
  @BindView(R.id.ivPause)
  ImageView mPauseIv;
  @BindView(R.id.ivCircle)
  ImageView mCircleIv;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.e(TAG, "onCreate: ");
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    // TODO
    Animal dog = new Dog();  /*Con cho la Dong vat*/
    ((Dog) dog).makeNoise();
    Animal dog1 = new Dog();  // Up casting
    dog1.run();
    dog1.runAnimal();
    ((Dog) dog1).runDog();  // down casting
    /* Chi */
  }

  interface Action {
    int a = 0;
    String str = "";
    void makeNoise();
    void makeNoise1();
    void makeNoise2();
  }

  abstract class Animal {
    abstract void eat();

    public void run() {
      Log.e("DUAN_LOG", "run animal");
    }

    public void runAnimal() {
      Log.e("DUAN_LOG", "run...aaa");
    }
  }

  class Dog extends Animal implements Action {

    @Override
    public void run() {
      Log.e("DUAN_LOG", "run dog");
    }

    @Override
    void eat() {
      Log.e("DUAN_LOG", "eat: ");
    }

    public void runDog() {
      Log.e("DUAN_LOG", "run...ddd");
    }

    @Override
    public void makeNoise() {
      Log.e("DUAN_LOG", "makeNoise " + a);
    }

    @Override
    public void makeNoise1() {

    }

    @Override
    public void makeNoise2() {

    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    Log.e(TAG, "onStart: ");
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.e(TAG, "onResume: ");
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.e(TAG, "onPause: ");
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.e(TAG, "onStop: ");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.e(TAG, "onDestroy: ");
  }

  @OnClick(R.id.ivMenu)
  public void onMenuClick() {
    Log.e(TAG, "onMenuClick: ");
  }

  @OnClick(R.id.ivBack)
  public void onBackClick() {
    Log.e(TAG, "onBackClick: ");
  }

  @OnClick(R.id.ivPlay)
  public void onPlayClick() {
    Log.e(TAG, "onPlayClick: ");
  }

  @OnClick(R.id.ivPause)
  public void onPauseClick() {
    Log.e(TAG, "onPauseClick: ");
  }

  @OnClick(R.id.ivCircle)
  public void onCircleClick() {
    Log.e(TAG, "onCircleClick: ");
  }

}
