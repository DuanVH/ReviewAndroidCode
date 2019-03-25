package com.example.gem.reviewandroidcode;

import android.media.MediaActionSound;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getName();

  @BindView(R.id.ivMenu)
  ImageView mIv1;
  @BindView(R.id.ivBack)
  ImageView mIv2;
  @BindView(R.id.ivPlay)
  ImageView mIv3;
  @BindView(R.id.ivPause)
  ImageView mIv4;
  @BindView(R.id.ivCircle)
  ImageView mIv5;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.e(TAG, "onCreate: ");
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
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